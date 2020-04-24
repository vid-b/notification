package com.sap.iot.ain.notification.services;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.sap.iot.ain.odata.ODataConstants;
import com.sap.iot.ain.reuse.GenericRowMapper;
import com.sap.iot.ain.validation.utils.MsgHelper;
import com.sap.iot.ain.validation.utils.ValidationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.sap.iot.ain.template.payload.AlertPUT;
import com.sap.iot.ain.template.plugin.ACEventTypeIOTAEExtensionPoint;
import com.sap.dsc.ac.iotae.utils.IoTAEToken;
import com.sap.dsc.ac.utility.AINConstants;
import com.sap.iot.ain.notification.dao.NotificationDao;
import com.sap.iot.ain.notification.entities.NotificationHeaderEntity;
import com.sap.iot.ain.notification.payload.AlertID;
import com.sap.iot.ain.notification.payload.NotificationAlertMapping;
import com.sap.iot.ain.notification.payload.NotificationDescription;
import com.sap.iot.ain.notification.payload.NotificationGET;
import com.sap.iot.ain.notification.payload.NotificationGETForAlert;
import com.sap.iot.ain.notification.payload.NotificationGETWithItems;
import com.sap.iot.ain.notification.payload.NotificationIDList;
import com.sap.iot.ain.notification.payload.NotificationList;
import com.sap.iot.ain.notification.payload.NotificationPOST;
import com.sap.iot.ain.notification.payload.NotificationPOSTForAlert;
import com.sap.iot.ain.notification.payload.NotificationPUT;
import com.sap.iot.ain.notification.payload.NotificationProgressStatusCount;
import com.sap.iot.ain.notification.payload.NotificationsForEquipment;
import com.sap.iot.ain.notification.validation.NotificationReadValidations;
import com.sap.iot.ain.queue.kafka.payload.KafkaBase;
import com.sap.iot.ain.queue.kafka.producers.AINKafkaProducer;
import com.sap.iot.ain.queue.kafka.producers.KafkaConstants;
import com.sap.iot.ain.reuse.Strings;
import com.sap.iot.ain.security.AuthenticatedUserDetails;
import com.sap.iot.ain.security.Secure;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * @author I322597
 *
 */
@Component
@Path("/api/v1")
public class NotificationServices {

	@Autowired
	private NotificationDao notificationDao;
	
	@Autowired
	private AINKafkaProducer akp;
	
	@Autowired
	@Lazy(value=true)
	private ACEventTypeIOTAEExtensionPoint eventTypeIOTAE;

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationServices.class);

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/external/notification")
	@Secure(roles = {"EQUIPMENT_READ", "EQUIPMENT_EDIT", "EQUIPMENT_DELETE"})
	public NotificationGET createExternalNotification(@Valid NotificationPOST notificationPost) {

		NotificationGET notificationRead = new NotificationGET();
		NotificationHeaderEntity entity =
				notificationDao.notificationPayloadToEntity(notificationPost);
		entity = notificationDao.createNotification(entity, notificationPost);
		NotificationHeaderEntity readNotificationEntity =
				notificationDao.readNotification(entity.getId());
		if (readNotificationEntity != null) {
			notificationRead = notificationDao.notificationEntityToPayload(readNotificationEntity);
		}
		if (!Strings.isNullOrEmpty(entity.getExternalId())) {
			notificationDao.persistExternalIdMapping(entity.getNotificationId(), entity.getExternalId(), notificationPost.getExternalSystemID());
		}
		return notificationRead;

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	// @Path("/internal/notificaton")
	@Path("/notification")
	@Secure(roles = {"EQUIPMENT_READ", "EQUIPMENT_EDIT", "EQUIPMENT_DELETE"})
	public NotificationGET createInternalNotification(@HeaderParam("Authorization") String authorization, @Valid NotificationPOST notificationPost) {


		String token = notificationDao.getToken(authorization);
			if(notificationPost.getProposedFailureModeID() != null || notificationPost.getSystemProposedFailureModeID() != null || notificationPost.getConfirmedFailureModeID() != null){
				
				validateFMAgainstEquipmentAndAlertType(token,notificationPost);
			}

		
		return createNewNotification(token, notificationPost);
		
//		NotificationGET notificationRead = new NotificationGET();
//		NotificationHeaderEntity entity =
//				notificationDao.notificationPayloadToEntity(notificationPost);
//		entity = notificationDao.createNotification(entity, notificationPost);
//		if (entity != null) {
//			NotificationHeaderEntity readNotificationEntity =
//					notificationDao.readNotification(entity.getId());
//			if (readNotificationEntity != null) {
//				notificationRead =
//						notificationDao.notificationEntityToPayload(readNotificationEntity);
//			}
//		}
//		if (!Strings.isNullOrEmpty(entity.getExternalId())) {
//			notificationDao.persistExternalIdMapping(entity.getNotificationId(), entity.getExternalId(), notificationPost.getExternalSystemID());
//		}
//		return notificationRead;

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/notification")
	@Secure(roles = {"EQUIPMENT_READ", "EQUIPMENT_EDIT", "EQUIPMENT_DELETE"})
	public NotificationGET updateNotification(@Valid NotificationPUT notification) {
		
		
		if(notification.getEquipmentID() != null ){
			if(notification.getProposedFailureModeID() != null || notification.getSystemProposedFailureModeID() != null || notification.getConfirmedFailureModeID() != null){
				validateFMAgainstEquipmentAndAlertTypeForPUT(notification);
			}

		}

		NotificationGET notificationRead = new NotificationGET();
		NotificationHeaderEntity entity = notificationDao.updateNotification(notification);
		NotificationHeaderEntity readNotificationEntity =
				notificationDao.readNotification(entity.getId());
		if (readNotificationEntity != null) {
			notificationRead = notificationDao.notificationEntityToPayload(readNotificationEntity);
		}
		return notificationRead;

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/notification({notificationId})")
	@Secure(roles = {"EQUIPMENT_READ", "EQUIPMENT_EDIT", "EQUIPMENT_DELETE"})
	@NotificationReadValidations
	public NotificationGETWithItems getNotification(
			@PathParam("notificationId") String notificationId) {
		NotificationGETWithItems notificationGET =
				notificationDao.getNotificationById(notificationId);
		return notificationGET;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/notification/remove")
	@Secure(roles = {"EQUIPMENT_READ", "EQUIPMENT_EDIT", "EQUIPMENT_DELETE"})
	public void deleteNotificationDetails(@Valid NotificationIDList notificationList) {
		notificationDao.deleteNotifications(notificationList);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/notification/equipment({equipmentID})")
	@Secure(roles = {"EQUIPMENT_READ", "EQUIPMENT_EDIT", "EQUIPMENT_DELETE"})
	public List<NotificationsForEquipment> getNotifications(
			@PathParam("equipmentID") String equipmentID, @QueryParam("startdate") String startdate,
			@QueryParam("enddate") String enddate) {
		List<NotificationsForEquipment> NotificationsForEquipment =
				notificationDao.getNotifications(equipmentID, startdate, enddate);
		return NotificationsForEquipment;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/notification")
	@Secure(roles = {"EQUIPMENT_READ", "EQUIPMENT_EDIT", "EQUIPMENT_DELETE"})
	public List<NotificationList> getNotifications(@QueryParam("$filter") String filter,
			@QueryParam("$top") Integer top, @QueryParam("$skip") Integer skip,
			@QueryParam("$orderby") String orderBy, @QueryParam("$order") String order ,@Context HttpServletRequest httpServletRequest)
			throws UnsupportedEncodingException {
		return notificationDao.readNotifications(filter, top, skip, orderBy,order);
	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/notification/alert")
	@Secure(roles = {"EQUIPMENT_READ", "EQUIPMENT_EDIT", "EQUIPMENT_DELETE"})
	public NotificationGETForAlert getNotificationForAlert(
			@Valid NotificationPOSTForAlert notificationPostForAlert) {
		List<NotificationAlertMapping> notificationAlertMappings = new ArrayList<NotificationAlertMapping>();
		NotificationGETForAlert notificationRead = new NotificationGETForAlert();
		notificationAlertMappings = notificationDao
				.getNotificationsForAlert(new HashSet<String>(notificationPostForAlert.getAlerts()));

		if (notificationPostForAlert.getMode().toUpperCase(Locale.ENGLISH).equals("COUNT")) {
			notificationRead.setCount(notificationAlertMappings.size());
		} else if (notificationPostForAlert.getMode().toUpperCase(Locale.ENGLISH).equals("GET")) {
			notificationRead.setCount(notificationAlertMappings.size());
			notificationRead.setData(notificationAlertMappings);
		}
		return notificationRead;

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/notificationswithoutexternalid")
	@Secure(roles = {"EQUIPMENT_READ", "EQUIPMENT_EDIT", "EQUIPMENT_DELETE"})
	public List<NotificationList> getNotificationsWithoutexternalId() {
		return notificationDao.getNotificationsWithoutExternalId();
	}

	/* To support backward compatibility */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/notificaton")
	@Secure(roles = {"EQUIPMENT_READ", "EQUIPMENT_EDIT", "EQUIPMENT_DELETE"})
	public NotificationGET updateNotificaton(@Valid NotificationPUT notification) {

		NotificationGET notificationRead = new NotificationGET();
		NotificationHeaderEntity entity = notificationDao.updateNotification(notification);
		NotificationHeaderEntity readNotificationEntity =
				notificationDao.readNotification(entity.getId());
		if (readNotificationEntity != null) {
			notificationRead = notificationDao.notificationEntityToPayload(readNotificationEntity);
		}
		return notificationRead;

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/equipment({equipmentId})/notification/status/count")
	@Secure(roles = {"EQUIPMENT_READ", "EQUIPMENT_EDIT", "EQUIPMENT_DELETE"})
	public NotificationProgressStatusCount getNotificationCountByProgressStatus(
			@PathParam("equipmentId") String equipmentId) {

		return notificationDao.getNotificationCountByStatus(equipmentId);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/location({locationId})/notification/status/count")
	@Secure(roles = {"EQUIPMENT_READ", "EQUIPMENT_EDIT", "EQUIPMENT_DELETE"})
	public NotificationProgressStatusCount getNotificationCountForLocationByProgressStatus(
			@PathParam("locationId") String locationId) {

		return notificationDao.getNotificationCountForLocationByStatus(locationId);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/description/notification({notificationId})")
	@Secure(roles = {"EQUIPMENT_READ", "EQUIPMENT_EDIT", "EQUIPMENT_DELETE"})
	@NotificationReadValidations
	public List<NotificationDescription> getNotificationDescription(@PathParam("notificationId") String notificationId) {
		List<NotificationDescription> descriptions = notificationDao.getNotificationDescription(notificationId);
		return descriptions;
	}
	
	public NotificationGET createNewNotification(String token, NotificationPOST notificationPost){
		logger.debug("createNewNotification: Inside create new notification") ;
		NotificationGET notificationRead = new NotificationGET();
		logger.debug("createNewNotification: notification Read value" + notificationRead);
		NotificationHeaderEntity entity =
				notificationDao.notificationPayloadToEntity(notificationPost);
		logger.debug("createNewNotification: notification Header Entity,got NotificationId" + entity.getNotificationId());
		entity = notificationDao.createNotification(entity, notificationPost);
		logger.debug("createNewNotification: createNotification function called" + entity.getInternalId() + "notificationPost equId is:"+ notificationPost.getEquipmentID());
		if (entity != null) {
			logger.debug("createNewNotification: Inside entity not null check");
			NotificationHeaderEntity readNotificationEntity =
					notificationDao.readNotification(entity.getId());
			logger.debug("createNewNotification: entity is not null and readNotification has notificationId:" + readNotificationEntity.getNotificationId());
			if (readNotificationEntity != null) {
				logger.debug("createNewNotification: Inside readNotificationEntity entity not null check");
				notificationRead =
						notificationDao.notificationEntityToPayload(readNotificationEntity);
				logger.debug("createNewNotification: called notificationEntityToPayload and equId is" + notificationRead.getEquipmentID());
			}
		}
		if (!Strings.isNullOrEmpty(entity.getExternalId())) {
			logger.debug("createNewNotification: getExternalId of entity is false");
			notificationDao.persistExternalIdMapping(entity.getNotificationId(), entity.getExternalId(), notificationPost.getExternalSystemID());
		}
		/*Notification Alert Mapping IOT AE */
		String notificationID = notificationRead.getInternalId();
		List<AlertID> alertIds = notificationPost.getAlertIDs();
		
		if(alertIds!= null && !alertIds.isEmpty()){
			logger.debug("createNewNotification: alert id is not null") ;
			IoTAEToken tokenHelper = new IoTAEToken();
			String auth = tokenHelper.getUserToken(token, AuthenticatedUserDetails.getInstance().getUserDetails().getTenantName(),
				AuthenticatedUserDetails.getInstance().getUserDetails().getUserBpId());
			alertIds.stream().forEach(alertId -> {
				String eventType = eventTypeIOTAE.getEventType(auth, alertId.getId());
				if(eventType == null) {
					logger.error("Could not fetch eventtype information from IOT AE");
					return;
				}
				AlertPUT alertData = new AlertPUT();
				alertData.setAlertId(alertId.getId());
				logger.debug("createNewNotification: alertData is set with alertID" + alertId.getId());
				alertData.setEventType(eventType);
				logger.debug("createNewNotification: alertData is set with eventType" + eventType);
				eventTypeIOTAE.updateNotificationInformationForEvent(token, alertData, notificationID);
				logger.debug("createNewNotification: updateNotificationInformationForEvent called" + alertData + notificationID);
			});
		}
		
		try {
			if (akp.isConnected()) {
				KafkaBase metadata = new KafkaBase();
				metadata.setObjectId(entity.getNotificationId());
				metadata.setObjectVersion(0.0);
				metadata.setObjectType(AINConstants.NOTIFICATION);
				metadata.setObjectOwner(AuthenticatedUserDetails.getInstance().getUserDetails().getUserClientId());
				metadata.setAction(KafkaConstants.KAFKA_OBJ_CREATE);
				metadata.setApplicationContext(KafkaConstants.APPCONTEXT_NOT_CREATE);
	
				akp.pushMessageToKafka(entity.getId(), metadata, AuthenticatedUserDetails.getInstance().getUserDetails().getUserTenantId(),
						KafkaConstants.NOTIFICATION_TOPIC);
			}
		}catch(Exception ex) {
			logger.debug("Error while creating kafka event for notification, {}", ex);
		}
		
		return notificationRead;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/notification/$count")
	@Secure(roles = {"EQUIPMENT_READ", "EQUIPMENT_EDIT", "EQUIPMENT_DELETE"})
	public Long getEquipmentCount(@QueryParam("$filter") String filter,
			@Context HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {

		return notificationDao.getNotificationCount(filter);
	}


	public boolean validateFMAgainstEquipmentAndAlertType(String token,NotificationPOST notificationPOST){
		String sFMNA = "FMNA";
		List<AlertID> alertIds = notificationPOST.getAlertIDs();
		String eventTypeList = new String();
		
		ArrayList<String> FMIdList = new ArrayList<>();
		if(alertIds!= null && !alertIds.isEmpty()){
			logger.debug("createNewNotification: alert id is not null") ;
		    IoTAEToken tokenHelper = new IoTAEToken();
		String auth = tokenHelper.getUserToken(token, AuthenticatedUserDetails.getInstance().getUserDetails().getTenantName(),
				AuthenticatedUserDetails.getInstance().getUserDetails().getUserBpId());
			for(int i = 0 ; i< alertIds.size();i++){
				String eventType = eventTypeIOTAE.getEventType(auth, alertIds.get(i).getId());
				eventTypeList = eventTypeList + "," + eventType;
			}
		}

		String query = "select \"ID\" from \"_SYS_BIC\".\"sap.ain.views/FMListForAlertsAndEquipments\" "
							+ "( 'placeholder' = ( '$$iv_lang$$', '"
							+ AuthenticatedUserDetails.getInstance().getUserDetails().getLocale().getLanguage() + "' ), "
							+ "'placeholder' = ( '$$iv_client$$', '" + AuthenticatedUserDetails.getInstance().getUserDetails().getUserClientId()
							+ "' )," + " 'placeholder' = ( '$$iv_scope$$', '" + AuthenticatedUserDetails.getInstance().getUserDetails().getScope()
							+ "'), 'placeholder' = ( '$$iv_user_bp_id$$', '"
							+ AuthenticatedUserDetails.getInstance().getUserDetails().getUserBpId() + "'), "
							+ "'placeholder' = ( '$$iv_equipment_id$$', '" +notificationPOST.getEquipmentID()  + "'), "
							+ "'placeholder' = ( '$$iv_alerttype_ids$$', '" + eventTypeList + "' )) as " + ODataConstants.ENTITY_ALIAS;

			List<AlertID> failureModeIDList =
					(List<AlertID>) jdbcTemplate.query(query,
							(rs,i) ->{
							AlertID obj = new AlertID();
							obj.setId(rs.getString("ID"));
							return obj;
							});


		failureModeIDList.forEach(failureModeID->{
			FMIdList.add(failureModeID.getId());
		});
		if(notificationPOST.getConfirmedFailureModeID() != null && notificationPOST.getConfirmedFailureModeID() != "" ){
			if (!FMIdList.contains(notificationPOST.getConfirmedFailureModeID())) {
				new ValidationMessage("invalid.confirmedFailureModeID.equMapping").build(true);
				return false;
			}
		}
		if(notificationPOST.getProposedFailureModeID() != null && notificationPOST.getProposedFailureModeID() != "" && !(sFMNA.equals( notificationPOST.getProposedFailureModeID()))){
			if(!FMIdList.contains(notificationPOST.getProposedFailureModeID() )){
				new ValidationMessage("invalid.proposedFailureMode.equMapping").build(true);
				return false;
			}
		}
		if(notificationPOST.getSystemProposedFailureModeID() != null && notificationPOST.getSystemProposedFailureModeID() != "") {
			if (!FMIdList.contains(notificationPOST.getSystemProposedFailureModeID())) {
				new ValidationMessage("invalid.systemProposed.equMapping").build(true);
				return false;
			}
		}
			return true;
		}
	
	public boolean validateFMAgainstEquipmentAndAlertTypeForPUT(NotificationPUT notification) {
		String sFMNA = "FMNA";
		ArrayList<String> FMIdList = new ArrayList<>();
		String query = "select \"ID\" from \"_SYS_BIC\".\"sap.ain.views/FMListForAlertsAndEquipments\" "
				+ "( 'placeholder' = ( '$$iv_lang$$', '"
				+ AuthenticatedUserDetails.getInstance().getUserDetails().getLocale().getLanguage() + "' ), "
				+ "'placeholder' = ( '$$iv_client$$', '"
				+ AuthenticatedUserDetails.getInstance().getUserDetails().getUserClientId() + "' ),"
				+ " 'placeholder' = ( '$$iv_scope$$', '"
				+ AuthenticatedUserDetails.getInstance().getUserDetails().getScope()
				+ "'), 'placeholder' = ( '$$iv_user_bp_id$$', '"
				+ AuthenticatedUserDetails.getInstance().getUserDetails().getUserBpId() + "'), "
				+ "'placeholder' = ( '$$iv_equipment_id$$', '" + notification.getEquipmentID() + "'), "
				+ "'placeholder' = ( '$$iv_alerttype_ids$$', '' )) as " + ODataConstants.ENTITY_ALIAS;

		List<AlertID> failureModeIDList = (List<AlertID>) jdbcTemplate.query(query, (rs, i) -> {
			AlertID obj = new AlertID();
			obj.setId(rs.getString("ID"));
			return obj;
		});

		failureModeIDList.forEach(failureModeID -> {
			FMIdList.add(failureModeID.getId());
		});
		if (notification.getConfirmedFailureModeID() != null && notification.getConfirmedFailureModeID() != "") {
			if (!FMIdList.contains(notification.getConfirmedFailureModeID())) {
				new ValidationMessage("invalid.confirmedFailureModeID.equMapping").build(true);
				return false;
			}
		}
		if (notification.getProposedFailureModeID() != null && notification.getProposedFailureModeID() != ""
				&& !(sFMNA.equals(notification.getProposedFailureModeID()))) {
			if (!FMIdList.contains(notification.getProposedFailureModeID())) {
				new ValidationMessage("invalid.proposedFailureMode.equMapping").build(true);
				return false;
			}
		}
		if (notification.getSystemProposedFailureModeID() != null
				&& notification.getSystemProposedFailureModeID() != "") {
			if (!FMIdList.contains(notification.getSystemProposedFailureModeID())) {
				new ValidationMessage("invalid.systemProposed.equMapping").build(true);
				return false;
			}
		}
		return true;

	}

}
