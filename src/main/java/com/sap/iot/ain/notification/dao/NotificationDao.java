package com.sap.iot.ain.notification.dao;

import java.io.UnsupportedEncodingException;

import java.net.URLDecoder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.olingo.odata2.api.edm.Edm;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.exception.ODataMessageException;
import org.apache.olingo.odata2.api.rt.RuntimeDelegate;
import org.apache.olingo.odata2.api.uri.UriParser;
import org.apache.olingo.odata2.api.uri.expression.ExpressionParserException;
import org.apache.olingo.odata2.api.uri.expression.FilterExpression;
import org.apache.olingo.odata2.core.uri.expression.Token;
import org.apache.olingo.odata2.core.uri.expression.TokenKind;
import org.apache.olingo.odata2.core.uri.expression.TokenList;
import org.apache.olingo.odata2.core.uri.expression.Tokenizer;
import org.apache.olingo.odata2.core.uri.expression.TokenizerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sap.iot.ain.auth.core.AINMyObjectsCircle;
import com.sap.iot.ain.core.AINObjectTypes;
import com.sap.iot.ain.externalidmapping.payload.ExternalIDPayload;
import com.sap.iot.ain.externalidmapping.payload.ExternalIdMapping;
import com.sap.iot.ain.externalidmapping.payload.IDMapping;
import com.sap.iot.ain.externalidmapping.service.MapExternalIDs;
//import com.sap.iot.ain.funclocation.dao.GeojsonJtsConversion;
import com.sap.iot.ain.gen.businesspartner.ErrorMessage;
import com.sap.iot.ain.gen.businesspartner.ErrorMessage.Severity;
import com.sap.iot.ain.group.dao.GroupDao;
import com.sap.iot.ain.group.payload.GroupDetails;
import com.sap.iot.ain.group.payload.ResponseData;
import com.sap.iot.ain.group.payload.UpdateGroupWithBusinessObject;
import com.sap.iot.ain.group.validations.UnassignObjectFromMultipleGroupsValidations;
import com.sap.iot.ain.notification.entities.NotificationAlertMappingEntity;
import com.sap.iot.ain.notification.entities.NotificationCauseCodesEntity;
import com.sap.iot.ain.notification.entities.NotificationDescriptionEntity;
import com.sap.iot.ain.notification.entities.NotificationFailureModesEntity;
import com.sap.iot.ain.notification.entities.NotificationHeaderEntity;
import com.sap.iot.ain.notification.entities.NotificationItemEntity;
import com.sap.iot.ain.notification.entities.NotificationStatusEntity;
import com.sap.iot.ain.notification.payload.AlertID;
import com.sap.iot.ain.notification.payload.NotificationAlertMapping;
import com.sap.iot.ain.notification.payload.NotificationCauseCodes;
import com.sap.iot.ain.notification.payload.NotificationCountByStatus;
import com.sap.iot.ain.notification.payload.NotificationDescription;
import com.sap.iot.ain.notification.payload.NotificationFailureModes;
import com.sap.iot.ain.notification.payload.NotificationGET;
import com.sap.iot.ain.notification.payload.NotificationGETWithItems;
import com.sap.iot.ain.notification.payload.NotificationIDList;
import com.sap.iot.ain.notification.payload.NotificationItems;
import com.sap.iot.ain.notification.payload.NotificationList;
import com.sap.iot.ain.notification.payload.NotificationPOST;
import com.sap.iot.ain.notification.payload.NotificationPUT;
import com.sap.iot.ain.notification.payload.NotificationProgressStatusCount;
import com.sap.iot.ain.notification.payload.NotificationsForEquipment;
import com.sap.iot.ain.notification.validation.NotificationDeleteValidations;
import com.sap.iot.ain.notification.validation.NotificationDeleteValidations.NotificationsDeleteValidation;
import com.sap.iot.ain.notification.validation.NotificationGroupBusinessObjectValidations;
import com.sap.iot.ain.odata.ODataConstants;
import com.sap.iot.ain.odata.QueryParameter;
import com.sap.iot.ain.odata.SQLCustomFilterExpression;
import com.sap.iot.ain.odata.SQLFilterExpression;
import com.sap.iot.ain.odata.core.ODataSQLFilterExpression;
import com.sap.iot.ain.odata.core.processor.ODataSQLContextImpl;
import com.sap.iot.ain.odata.core.processor.edm.ODataSQLEdmProvider;
import com.sap.iot.ain.odata.core.processor.edm.SQLEdmEntity;
import com.sap.iot.ain.reuse.CustomExceptionList;
import com.sap.iot.ain.reuse.DateHelper;
import com.sap.iot.ain.reuse.GenericRowMapper;
import com.sap.iot.ain.reuse.Strings;
import com.sap.iot.ain.reuse.SystemAdministrativeData;
import com.sap.iot.ain.reuse.cache.ApplicationCacheManager;
import com.sap.iot.ain.reuse.payload.AdminData;
import com.sap.iot.ain.reuse.payload.ID;
import com.sap.iot.ain.reuse.publicAPI.SQLFilterMetadata;
import com.sap.iot.ain.reuse.publicAPI.SQLFilterMetadataObjectHandler;
import com.sap.iot.ain.reuse.service.GetInternalIDProcHandler;
import com.sap.iot.ain.reuse.utils.HanaStoredProcedure;
import com.sap.iot.ain.reuse.utils.ObjectUtils;
import com.sap.iot.ain.security.AuthenticatedUserDetails;
import com.sap.iot.ain.util.model.DaoHelper;
import com.sap.core.connectivity.api.authentication.AuthenticationHeader;
import com.sap.dsc.ac.iotae.reuse.DestinationCall;
import com.sap.iot.ain.reuse.utils.EnvironmentUtils;

/**
 * 
 * @author I322597
 *
 */
@Component
public class NotificationDao {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	AuthenticatedUserDetails aud;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private ReloadableResourceBundleMessageSource resourceBundle;

	@Autowired
	private AINMyObjectsCircle ainMyObjectsCircle;

	@Autowired
	private ApplicationCacheManager applicationCacheManager;

	@Autowired
	private MapExternalIDs mapExternalIDs;

	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private DestinationCall dc;

	private static final Logger logger = LoggerFactory.getLogger(NotificationDao.class);

	/**
	 * 
	 * @param entity
	 * @return
	 */
	@Transactional
	public NotificationHeaderEntity createNotification(NotificationHeaderEntity entity,
			NotificationPOST notificationPost) {
		em.persist(entity);
		ainMyObjectsCircle.addObject(entity.getNotificationId(), AINObjectTypes.NTF);
		mapExternalIdToAINObjectId(notificationPost, entity);
		return entity;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public NotificationHeaderEntity readNotification(String id) {
		NotificationHeaderEntity headerEntity = em.find(NotificationHeaderEntity.class, id);
		return headerEntity;
	}

	@Transactional
	public NotificationHeaderEntity updateNotification(NotificationPUT notificationPUT) {
		String notificationUID = this.getNotificationUID(notificationPUT.getNotificationID());
		NotificationHeaderEntity notificationHeaderEntity = this.readNotification(notificationUID);
		if (notificationHeaderEntity != null) {
			notificationHeaderEntity =
					this.notificationPayloadToEntity(notificationPUT, notificationHeaderEntity);
			em.merge(notificationHeaderEntity);
			delinkBusniessObjectsFromGroups(notificationPUT);
			mapExternalIdToAINObjectId(notificationPUT, notificationHeaderEntity);
			upsertExternalIdMapping(notificationHeaderEntity.getNotificationId(),
					notificationHeaderEntity.getExternalId(),
					notificationPUT.getExternalSystemID());
		}
		return notificationHeaderEntity;
	}

	/**
	 * 
	 * @param notificationPost
	 * @return NotificationHeaderEntity
	 */

	public NotificationHeaderEntity notificationPayloadToEntity(NotificationPOST notificationPost) {

		NotificationHeaderEntity headerEntity = new NotificationHeaderEntity();
		boolean isInternalFlag = false;
		if (notificationPost.getExternalID() == null
				|| notificationPost.getExternalID().trim().equals("")) {
			isInternalFlag = true;
		}
		headerEntity.setId(DaoHelper.getUUID());
		headerEntity.setNotificationId(DaoHelper.getUUID());
		headerEntity.setExternalId(notificationPost.getExternalID());
		headerEntity.setEquipmentId(notificationPost.getEquipmentID());
		headerEntity.setLocationId(notificationPost.getLocationID());
		headerEntity.setProposedFailureModeID(notificationPost.getProposedFailureModeID());
		headerEntity.setConfirmedFailureModeID(notificationPost.getConfirmedFailureModeID());
		headerEntity.setSystemProposedFailureModeID(notificationPost.getSystemProposedFailureModeID());
		headerEntity.setCauseID(notificationPost.getCauseID());
		headerEntity.setEffectID(notificationPost.getEffectID());
		headerEntity.setInstructionID(notificationPost.getInstructionID());
		if (notificationPost.getBreakdown() != null) {
			headerEntity.setBreakdown(notificationPost.getBreakdown());
		} else {
			headerEntity.setBreakdown("0");
		}
		headerEntity.setOperator(notificationPost.getOperator());
//		headerEntity
//				.setGeometry(GeojsonJtsConversion.getJtsGeometry(notificationPost.getGeometry()));

		String internalId = generateInternalID();
		headerEntity.setInternalId(internalId);

		List<NotificationStatusEntity> statusEntityList = convertToStatusEntity(
				notificationPost.getStatus(), headerEntity.getNotificationId());
		headerEntity.setStatus(statusEntityList);
		headerEntity.setType(notificationPost.getType());
		if (notificationPost.getPriority() != null
				&& notificationPost.getPriority().trim().length() > 0) {
			headerEntity.setPriority(Integer.parseInt(notificationPost.getPriority()));
		}
		headerEntity.setStartDate(DateHelper.getTimestamp(notificationPost.getStartDate()));
		headerEntity.setEndDate(DateHelper.getTimestamp(notificationPost.getEndDate()));



		headerEntity.setMalfunctionStartDate(
				convertToValidTimeStamp((String) notificationPost.getMalfunctionStartDate()));

		headerEntity.setMalfunctionEndDate(
				convertToValidTimeStamp((String) notificationPost.getMalfunctionEndDate()));

		if (isInternalFlag) {
			headerEntity.setIsInternal("1");
		} else {
			headerEntity.setIsInternal("0");
		}

		headerEntity.setIsMarkedForDeletion("0");
		SystemAdministrativeData sysAdminData = new SystemAdministrativeData();
		if (!isInternalFlag) {
			if (notificationPost.getAdminData() != null) {
				if (notificationPost.getAdminData().getCreatedBy() != null
						&& !notificationPost.getAdminData().getCreatedBy().isEmpty()) {
					sysAdminData.setSystemAdministrativeDataCreatedByUserID(
							notificationPost.getAdminData().getCreatedBy());
				} else {
					sysAdminData.setSystemAdministrativeDataCreatedByUserID(
							aud.getUserDetails().getUserBpId());

				}

				if (notificationPost.getAdminData().getCreatedOn() != null) {
					sysAdminData.setSystemAdministrativeDataCreationDateTime(DateHelper
							.getTimestamp(notificationPost.getAdminData().getCreatedOn()));
				} else {
					sysAdminData.setSystemAdministrativeDataCreationDateTime(
							DateHelper.getCurrentTimestamp());
				}

				if (notificationPost.getAdminData().getChangedBy() != null
						&& !notificationPost.getAdminData().getChangedBy().isEmpty()) {
					sysAdminData.setSystemAdministrativeDataLastChangedByUserID(
							notificationPost.getAdminData().getChangedBy());
				} else {
					sysAdminData.setSystemAdministrativeDataLastChangedByUserID(
							aud.getUserDetails().getUserBpId());
				}

				if (notificationPost.getAdminData().getChangedOn() != null) {
					sysAdminData.setSystemAdministrativeDataLastChangeDateTime(DateHelper
							.getTimestamp(notificationPost.getAdminData().getChangedOn()));
				} else {
					sysAdminData.setSystemAdministrativeDataLastChangeDateTime(
							DateHelper.getCurrentTimestamp());
				}
			} else

			{
				sysAdminData.setSystemAdministrativeDataCreatedByUserID(
						aud.getUserDetails().getUserBpId());
				sysAdminData.setSystemAdministrativeDataCreationDateTime(
						DateHelper.getCurrentTimestamp());
				sysAdminData.setSystemAdministrativeDataLastChangedByUserID(
						aud.getUserDetails().getUserBpId());
				sysAdminData.setSystemAdministrativeDataLastChangeDateTime(
						DateHelper.getCurrentTimestamp());
			}

		} else

		{
			sysAdminData
					.setSystemAdministrativeDataCreatedByUserID(aud.getUserDetails().getUserBpId());
			sysAdminData
					.setSystemAdministrativeDataCreationDateTime(DateHelper.getCurrentTimestamp());
			sysAdminData.setSystemAdministrativeDataLastChangedByUserID(
					aud.getUserDetails().getUserBpId());
			sysAdminData.setSystemAdministrativeDataLastChangeDateTime(
					DateHelper.getCurrentTimestamp());
		}
		headerEntity.setSystemAdministrativeData(sysAdminData);
		
		headerEntity.setDescriptions(
				convertToDescriptionEntity(notificationPost.getDescriptions(), headerEntity));
		if (ObjectUtils.isListEmpty(headerEntity.getDescriptions())) {
			headerEntity.setDescriptions(
					convertToDescriptionEntity(notificationPost.getDescription(), headerEntity));
		}
		
		headerEntity.setNotificationItemEntity(convertToNotificationItemEntities(
				headerEntity.getNotificationId(), notificationPost.getItems()));

//		headerEntity.setNotificationAlertMappingEntity(convertToNotificationAlertMappingEntity(
//				headerEntity.getNotificationId(), notificationPost.getAlertIDs()));
		return headerEntity;
	}

	private void updateBusniessObjectsWithGroup(String ainGroupId,
			NotificationHeaderEntity headerEntity) {
		UpdateGroupWithBusinessObject updateGroupWithBusinessObjectForNTF =
				new UpdateGroupWithBusinessObject();

		updateGroupWithBusinessObjectForNTF.setBusinessObjectId(headerEntity.getNotificationId());
		updateGroupWithBusinessObjectForNTF.setBusinessObjectType(AINObjectTypes.NTF.toString());

		updateGroupWithBusniessObjects(ainGroupId,
				Stream.of(updateGroupWithBusinessObjectForNTF).collect(Collectors.toList()));

	}


	private void mapExternalIdToAINObjectId(NotificationPOST notificationPost,
			NotificationHeaderEntity entity) {

		if (!Strings.isNullOrEmpty(notificationPost.getWorkCenter())) {
			String externalWorkCenterSystemId =
					getSystemId(notificationPost.getWorkCenterSystemId());
			if (!Strings.isNullOrEmpty(externalWorkCenterSystemId)) {
				updateBusniessObjectsWithWorkCenter(notificationPost.getWorkCenter(),
						externalWorkCenterSystemId, entity);
			}
		}

		if (!Strings.isNullOrEmpty(notificationPost.getMaintenancePlannerGroup())) {
			String externalMaintenancePlannerGroupSystemId =
					getSystemId(notificationPost.getMaintenancePlanGroupSystemId());
			if (!Strings.isNullOrEmpty(externalMaintenancePlannerGroupSystemId)) {
				updateBusniessObjectsWithMaintenancePlannerGroup(
						notificationPost.getMaintenancePlannerGroup(),
						externalMaintenancePlannerGroupSystemId, entity);
			}
		}
	}

	private void updateBusniessObjectsWithWorkCenter(String externalWorkCenterId,
			String externalWorkCenterSystemId, NotificationHeaderEntity entity) {
		String ainWorkCenterId =
				getAINObjectIdForExternalId(externalWorkCenterSystemId, externalWorkCenterId);
		if (!Strings.isNullOrEmpty(ainWorkCenterId)) {
			updateBusniessObjectsWithGroup(ainWorkCenterId, entity);
		}
	}

	private void updateBusniessObjectsWithMaintenancePlannerGroup(
			String externalMaintenancePlannerGroupId,
			String externalMaintenancePlannerGroupSystemId, NotificationHeaderEntity entity) {
		String ainMaintenancePlannerGroupId = getAINObjectIdForExternalId(
				externalMaintenancePlannerGroupSystemId, externalMaintenancePlannerGroupId);
		if (!Strings.isNullOrEmpty(ainMaintenancePlannerGroupId)) {
			updateBusniessObjectsWithGroup(ainMaintenancePlannerGroupId, entity);
		}
	}

	private String getSystemId(String externalSystemId) {
		if (Strings.isNullOrEmpty(externalSystemId)) {
			String query =
					"SELECT top 1 \"ID\" FROM \"_SYS_BIC\".\"sap.ain.views/ExternalSystemsList\" (PLACEHOLDER.\"$$iv_client$$\" => ? ,PLACEHOLDER.\"$$iv_lang$$\"=>?)"
							+ " where \"SystemStatusDescription\" = 'Active' and \"SystemType\" = 'SAP ERP'";
			try {
				externalSystemId = jdbcTemplate.queryForObject(query,
						new Object[] {aud.getUserDetails().getUserClientId(), "en"}, String.class);
			} catch (DataAccessException exception) {
				logger.error(
						"Data access exception occurred while getting system id... in NotificationDao.getSystemId: {} "
								+ exception.getMessage());
			}
		}
		return externalSystemId;
	}


	private void delinkBusniessObjectsFromGroups(NotificationPUT notificationPUT) {
		String notificationId = notificationPUT.getNotificationID();
		List<GroupDetails> groupDetails = groupDao.retrieveGroupId(notificationId);
		if (groupDetails == null || groupDetails.isEmpty()) {
			return;
		}
		List<String> groupIds = new ArrayList<String>();
		groupDetails.forEach(group -> {
			groupIds.add(group.getGroupID());
		});
		try {
			updateObjectGroups(notificationId, AINObjectTypes.NTF.toString(), groupIds);
		} catch (SQLException e) {
			logger.error("SQL exception occurred while delinking business object group...");
		}
	}


	private void mapExternalIdToAINObjectId(NotificationPUT notificationPUT,
			NotificationHeaderEntity entity) {

		if (!Strings.isNullOrEmpty(notificationPUT.getWorkCenter())) {
			String externalWorkCenterSystemId =
					getSystemId(notificationPUT.getWorkCenterSystemId());
			if (!Strings.isNullOrEmpty(externalWorkCenterSystemId)) {
				updateBusniessObjectsWithWorkCenter(notificationPUT.getWorkCenter(),
						externalWorkCenterSystemId, entity);
			}
		}

		if (!Strings.isNullOrEmpty(notificationPUT.getMaintenancePlannerGroup())) {
			String externalMaintenancePlannerGroupSystemId =
					getSystemId(notificationPUT.getMaintenancePlanGroupSystemId());
			if (!Strings.isNullOrEmpty(externalMaintenancePlannerGroupSystemId)) {
				updateBusniessObjectsWithMaintenancePlannerGroup(
						notificationPUT.getMaintenancePlannerGroup(),
						externalMaintenancePlannerGroupSystemId, entity);
			}
		}
	}

	public Timestamp convertToValidTimeStamp(String date) {
		if (!Strings.isNullOrEmpty(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			sdf.setLenient(false);
			try {
				return new Timestamp(sdf.parse(date).getTime());
			} catch (ParseException e) {
				try {
					if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
						sdf = new SimpleDateFormat("yyyy-MM-dd");
						sdf.setLenient(false);
					}
					return new Timestamp(sdf.parse(date).getTime());
				} catch (ParseException pe) {
					ErrorMessage message = new ErrorMessage();
					message.setErrorMessage(resourceBundle.getMessage(
							"notification.availability.inValid.dateFormat", null,
							aud.getUserDetails().getLocale()));
					message.setSeverity(Severity.ERROR);
					ArrayList<ErrorMessage> messageList = new ArrayList<ErrorMessage>();
					messageList.add(message);
					throw new CustomExceptionList(Response.Status.BAD_REQUEST.getStatusCode(),
							messageList);
				}
			}
		}
		return null;
	}
	
	public void deleteMigrationRunStatus(String key) {
		String query =
				"DELETE FROM \"AIN_DEV\".\"sap.ain.metaData::Notification.NotificationAlertMapping\" WHERE \"AlertID\"=? AND \"Client\"=?";
		jdbcTemplate.update(query, new Object[] {key, AuthenticatedUserDetails.getInstance().getUserDetails().getUserClientId()});		
	}
	
	@Transactional
	public void saveMigrationRunStatus(String key, String value) {
		NotificationAlertMappingEntity entity = new NotificationAlertMappingEntity();
		entity.setAlertId(key);
		entity.setNotificationId(value);
		em.persist(entity);
	}
	
	public void deleteNotificationAlertMappingEntry(String alertId) {

		String query =
				"DELETE FROM \"AIN_DEV\".\"sap.ain.metaData::Notification.NotificationAlertMapping\" WHERE \"AlertID\"=?";
		jdbcTemplate.update(query, new Object[] {alertId});
	}
	
	/**
	 * Get limited set of alerts
	 * @param top
	 * @param skip
	 * @return
	 */
	public List<NotificationAlertMapping> getAlertWithNotification(int top, int skip) {
		String sql =
				"select \"Alert\".\"NotificationID\", \"Alert\".\"AlertID\",\"Hdr\".\"InternalID\",\"Hdr\".\"EquipmentID\" from \"sap.ain.metaData::Notification.NotificationAlertMapping\" as \"Alert\" join \"sap.ain.metaData::Notification.Header\" as \"Hdr\" "
						+ " on \"Hdr\".\"NotificationID\" = \"Alert\".\"NotificationID\" where \"Alert\".\"Client\" = ? ORDER BY \"NotificationID\" DESC LIMIT ? OFFSET ?;";

		List<NotificationAlertMapping> notificationDataForAlert =
				jdbcTemplate.query(sql, new Object[] { aud.getUserDetails().getUserClientId(), top, skip }, new NotificationAlertMapping());

		return notificationDataForAlert;
	}
	
	/**
	 * Get notification alert count
	 * @return
	 */
	public Long getNotificationAlertCount() {

		Long alertCount = 0L;

		String query =
				"select COUNT(*) from \"sap.ain.metaData::Notification.NotificationAlertMapping\" as \"Alert\" where \"Alert\".\"Client\" = ? ;";

		try {
			alertCount = jdbcTemplate.queryForObject(query, new Object[] { aud.getUserDetails().getUserClientId()}, Long.class);
			return alertCount;
		} catch (DataAccessException dae) {
			logger.error("Data access exception occured while getting notification alert count");
			return null;
		}
	}
	

//	public List<NotificationAlertMappingEntity> convertToNotificationAlertMappingEntity(
//			String notificationId, List<AlertID> alertIds) {
//		if (alertIds == null || alertIds.isEmpty()) {
//			return null;
//		}
//		List<NotificationAlertMappingEntity> notificationAlertMappingEntities = new ArrayList<>();
//		alertIds.stream().filter(alertId -> alertId != null && alertId.getId() != null)
//				.collect(Collectors.toList()).forEach(alertId -> {
//					NotificationAlertMappingEntity notificationAlertEntity =
//							new NotificationAlertMappingEntity();
//					notificationAlertEntity.setAlertId(alertId.getId());
//					notificationAlertEntity.setNotificationId(notificationId);
//					notificationAlertMappingEntities.add(notificationAlertEntity);
//				});
//		return notificationAlertMappingEntities;
//	}


	public List<NotificationItemEntity> convertToNotificationItemEntities(String notificationId,
			List<NotificationItems> payloadNotificationItems) {
		if (payloadNotificationItems == null || payloadNotificationItems.isEmpty()) {
			return null;
		}
		List<NotificationItemEntity> notificationItemEntities =
				new ArrayList<NotificationItemEntity>();
		for (NotificationItems item : payloadNotificationItems) {
			NotificationItemEntity itemEntity = new NotificationItemEntity();
			itemEntity.setItemNumber(item.getItemNumber());
			itemEntity.setNotificationId(notificationId);
			itemEntity.setShortDescription(item.getShortDescription());
			itemEntity.setLongDescription(item.getLongDescription());
			itemEntity.setNotificationCauseCodesEntity(convertToNotificationCauseCodesEntities(
					notificationId, item.getItemNumber(), item.getNotificationCauseCodes()));
			NotificationFailureModes notificationFailureModes = item.getNotificationFailureModes();
			if (notificationFailureModes != null) {
				NotificationFailureModesEntity notificationFailureModesEntity =
						new NotificationFailureModesEntity();
				notificationFailureModesEntity.setNotificationId(notificationId);
				notificationFailureModesEntity.setItemNumber(item.getItemNumber());
				notificationFailureModesEntity
						.setFailureModeId(notificationFailureModes.getFailureModeID());
				itemEntity.setNotificationFailureModesEntity(notificationFailureModesEntity);
			}
			notificationItemEntities.add(itemEntity);
		}
		return notificationItemEntities;
	}

	public List<NotificationCauseCodesEntity> convertToNotificationCauseCodesEntities(
			String notificationId, Integer itemNumber,
			List<NotificationCauseCodes> notificationCauseCodes) {
		if (notificationCauseCodes == null || notificationCauseCodes.isEmpty()) {
			return null;
		}
		List<NotificationCauseCodesEntity> notificationCauseCodesEntities = new ArrayList<NotificationCauseCodesEntity>();
		for (NotificationCauseCodes causeCode : notificationCauseCodes) {
			NotificationCauseCodesEntity notificationCauseCodesEntity =
					new NotificationCauseCodesEntity();
			notificationCauseCodesEntity.setItemNumber(itemNumber);
			notificationCauseCodesEntity.setNotificationId(notificationId);
			notificationCauseCodesEntity.setCauseId(causeCode.getCauseID());
			notificationCauseCodesEntity.setCauseDescription(causeCode.getCauseDescription());
			notificationCauseCodesEntities.add(notificationCauseCodesEntity);
		}
		return notificationCauseCodesEntities;
	}

	/**
	 * 
	 * @param entity
	 * @return NotificationGET
	 */
	public NotificationGET notificationEntityToPayload(NotificationHeaderEntity entity) {

		NotificationGET notificationGet = new NotificationGET();
		notificationGet.setExternalID(entity.getExternalId());
		notificationGet.setNotificationID(entity.getNotificationId());
		notificationGet.setEquipmentID(entity.getEquipmentId());
		notificationGet.setConfirmedFailureModeID(entity.getConfirmedFailureModeID());
		notificationGet.setProposedFailureModeID(entity.getProposedFailureModeID());
		notificationGet.setSystemProposedFailureModeID(entity.getSystemProposedFailureModeID());
		notificationGet.setCauseID(entity.getCauseID());
		notificationGet.setEffectID(entity.getEffectID());
		notificationGet.setInstructionID(entity.getInstructionID());
		notificationGet.setDescription(
				createNotificationDescription(entity.getDescriptions(), entity.getIsInternal()));
		List<NotificationStatusEntity> statusEntityList = entity.getStatus();
		List<String> statusList = new ArrayList<>();
		if (statusEntityList.size() > 0) {
			for (NotificationStatusEntity status : statusEntityList) {
				statusList.add(status.getStatus());
			}
		}
		notificationGet.setStatus(statusList);
		notificationGet.setType(entity.getType());
		notificationGet.setPriority(String.valueOf(entity.getPriority()));
		notificationGet.setStartDate(DateHelper.getDateTime(entity.getStartDate()));
		notificationGet.setEndDate(DateHelper.getDateTime(entity.getEndDate()));
		notificationGet.setMalfunctionStartDate(entity.getMalfunctionStartDate());
		notificationGet.setMalfunctionEndDate(entity.getMalfunctionEndDate());
		notificationGet.setIsInternal(entity.getIsInternal());
		notificationGet.setInternalId(entity.getInternalId());
		notificationGet.setSystemAdministrativeData(entity.getSystemAdministrativeData());
		return notificationGet;

	}

	public NotificationHeaderEntity notificationPayloadToEntity(NotificationPUT notificationPut,
			NotificationHeaderEntity headerEntity) {
		boolean isInternalFlag = false;
		if (headerEntity.getIsInternal().equals("1")) {
			isInternalFlag = true;
		}
		headerEntity.setExternalId(notificationPut.getExternalID());
		headerEntity.setNotificationId(headerEntity.getNotificationId());
		headerEntity.setEquipmentId(notificationPut.getEquipmentID());
		headerEntity.setConfirmedFailureModeID(notificationPut.getConfirmedFailureModeID());
		headerEntity.setProposedFailureModeID(notificationPut.getProposedFailureModeID());
		headerEntity.setSystemProposedFailureModeID(notificationPut.getSystemProposedFailureModeID());
		headerEntity.setCauseID(notificationPut.getCauseID());
		headerEntity.setEffectID(notificationPut.getEffectID());
		headerEntity.setInstructionID(notificationPut.getInstructionID());
		/* Addition in PUT payload */
		headerEntity.setLocationId(notificationPut.getLocationID());
		if (notificationPut.getBreakdown() != null) {
			headerEntity.setBreakdown(notificationPut.getBreakdown());
		} else {
			headerEntity.setBreakdown("0");
		}
		headerEntity.setOperator(notificationPut.getOperator());
//		headerEntity
//				.setGeometry(GeojsonJtsConversion.getJtsGeometry(notificationPut.getGeometry()));
		headerEntity.setInternalId(headerEntity.getInternalId());
		/* Addition in PUT payload */
		headerEntity.setType(notificationPut.getType());
		if (notificationPut.getPriority() != null
				&& notificationPut.getPriority().trim().length() > 0) {
			headerEntity.setPriority(Integer.parseInt(notificationPut.getPriority()));
		}
		headerEntity.setStartDate(DateHelper.getTimestamp(notificationPut.getStartDate()));
		headerEntity.setEndDate(DateHelper.getTimestamp(notificationPut.getEndDate()));
		/* Changed data type of malfunction start date and end date to time stamp in PUT payload */
		headerEntity.setMalfunctionStartDate(
				convertToValidTimeStamp((String) notificationPut.getMalfunctionStartDate()));

		headerEntity.setMalfunctionEndDate(
				convertToValidTimeStamp((String) notificationPut.getMalfunctionEndDate()));
		/* Changed data type of malfunction start date and end date to time stamp in PUT payload */

		headerEntity.setIsInternal(headerEntity.getIsInternal());
		this.deleteNotificationStatus(headerEntity.getNotificationId());

		List<NotificationStatusEntity> statusEntityList = convertToStatusEntity(
				notificationPut.getStatus(), headerEntity.getNotificationId());
		headerEntity.setStatus(statusEntityList);
		SystemAdministrativeData sysAdminData = new SystemAdministrativeData();
		if (headerEntity.getIsInternal().equals("0")) {
			if (headerEntity.getSystemAdministrativeData() != null) {
				sysAdminData.setSystemAdministrativeDataCreatedByUserID(
						headerEntity.getSystemAdministrativeData()
								.getSystemAdministrativeDataCreatedByUserID());
				sysAdminData.setSystemAdministrativeDataCreationDateTime(
						headerEntity.getSystemAdministrativeData()
								.getSystemAdministrativeDataCreationDateTime());
			}
			if (notificationPut.getAdminData() != null) {
				if (notificationPut.getAdminData().getChangedBy() != null
						&& !notificationPut.getAdminData().getChangedBy().isEmpty()) {
					sysAdminData.setSystemAdministrativeDataLastChangedByUserID(
							notificationPut.getAdminData().getChangedBy());
				} else {
					sysAdminData.setSystemAdministrativeDataLastChangedByUserID(
							aud.getUserDetails().getUserBpId());
				}

				if (notificationPut.getAdminData().getChangedOn() != null) {
					sysAdminData.setSystemAdministrativeDataLastChangeDateTime(
							DateHelper.getTimestamp(notificationPut.getAdminData().getChangedOn()));
				} else {
					sysAdminData.setSystemAdministrativeDataLastChangeDateTime(
							DateHelper.getCurrentTimestamp());
				}
			} else {
				sysAdminData.setSystemAdministrativeDataLastChangedByUserID(
						aud.getUserDetails().getUserBpId());
				sysAdminData.setSystemAdministrativeDataLastChangeDateTime(
						DateHelper.getCurrentTimestamp());
			}
		} else {
			if (headerEntity.getSystemAdministrativeData() != null) {
				sysAdminData.setSystemAdministrativeDataCreatedByUserID(
						headerEntity.getSystemAdministrativeData()
								.getSystemAdministrativeDataCreatedByUserID());
				sysAdminData.setSystemAdministrativeDataCreationDateTime(
						headerEntity.getSystemAdministrativeData()
								.getSystemAdministrativeDataCreationDateTime());
			}
			sysAdminData.setSystemAdministrativeDataLastChangedByUserID(
					aud.getUserDetails().getUserBpId());
			sysAdminData.setSystemAdministrativeDataLastChangeDateTime(
					DateHelper.getCurrentTimestamp());

		}
		headerEntity.setSystemAdministrativeData(sysAdminData);
		headerEntity.setDescriptions(
				convertToDescriptionEntity(notificationPut.getDescriptions(), headerEntity));
		if (ObjectUtils.isListEmpty(headerEntity.getDescriptions())) {
			headerEntity.setDescriptions(
					convertToDescriptionEntity(notificationPut.getDescription(), headerEntity));
		}
		/* Items details in PUT payload */
		headerEntity.setNotificationItemEntity(
				convertToNotificationItemEntities(notificationPut.getItems(), headerEntity));
		/* Items details in PUT payload */
		return headerEntity;
	}

	private List<NotificationItemEntity> convertToNotificationItemEntities(
			List<NotificationItems> putPayloadNotificationItems,
			NotificationHeaderEntity headerEntity) {
		if (ObjectUtils.isListEmpty(putPayloadNotificationItems)) {
			return null;
		}
		List<NotificationItemEntity> notificationItemEntities =
				new ArrayList<NotificationItemEntity>();
		for (NotificationItems item : putPayloadNotificationItems) {
			NotificationItemEntity itemEntity = new NotificationItemEntity();
			itemEntity.setClient(headerEntity.getClient());
			itemEntity.setItemNumber(item.getItemNumber());
			itemEntity.setNotificationId(headerEntity.getNotificationId());
			itemEntity.setShortDescription(item.getShortDescription());
			itemEntity.setLongDescription(item.getLongDescription());
			itemEntity.setNotificationCauseCodesEntity(convertToNotificationCauseCodesEntities(
					item.getNotificationCauseCodes(), item.getItemNumber(), headerEntity));
			NotificationFailureModes notificationFailureModes = item.getNotificationFailureModes();
			if (notificationFailureModes != null) {
				NotificationFailureModesEntity notificationFailureModesEntity =
						new NotificationFailureModesEntity();
				notificationFailureModesEntity.setClient(headerEntity.getClient());
				notificationFailureModesEntity.setNotificationId(headerEntity.getNotificationId());
				notificationFailureModesEntity.setItemNumber(item.getItemNumber());
				notificationFailureModesEntity
						.setFailureModeId(notificationFailureModes.getFailureModeID());
				itemEntity.setNotificationFailureModesEntity(notificationFailureModesEntity);
			}
			notificationItemEntities.add(itemEntity);
		}
		return notificationItemEntities;
	}

	public List<NotificationCauseCodesEntity> convertToNotificationCauseCodesEntities(
			List<NotificationCauseCodes> notificationCauseCodes, Integer itemNumber,
			NotificationHeaderEntity headerEntity) {
		if (ObjectUtils.isListEmpty(notificationCauseCodes)) {
			return null;
		}
		List<NotificationCauseCodesEntity> notificationCauseCodesEntities = new ArrayList<>();
		for (NotificationCauseCodes causeCode : notificationCauseCodes) {
			NotificationCauseCodesEntity notificationCauseCodesEntity =
					new NotificationCauseCodesEntity();
			notificationCauseCodesEntity.setClient(headerEntity.getClient());
			notificationCauseCodesEntity.setItemNumber(itemNumber);
			notificationCauseCodesEntity.setNotificationId(headerEntity.getNotificationId());
			notificationCauseCodesEntity.setCauseId(causeCode.getCauseID());
			notificationCauseCodesEntity.setCauseDescription(causeCode.getCauseDescription());
			notificationCauseCodesEntities.add(notificationCauseCodesEntity);
		}
		return notificationCauseCodesEntities;
	}

	private List<NotificationStatusEntity> convertToStatusEntity(List<String> notificationStatus,
			String notificationID) {

		List<NotificationStatusEntity> statusEntityList = new ArrayList<NotificationStatusEntity>();

		if (notificationStatus == null) {
			return null;
		} else {
			for (String status : notificationStatus) {
				NotificationStatusEntity statusEntity = new NotificationStatusEntity();
				statusEntity.setId(notificationID);
				statusEntity.setStatus(status);
				statusEntityList.add(statusEntity);
			}
			return statusEntityList;
		}
	}

	private List<NotificationDescriptionEntity> convertToDescriptionEntity(
			List<NotificationDescription> descriptions, NotificationHeaderEntity notification) {
		
		if (ObjectUtils.isListEmpty(descriptions)) {
			return null;
		}

		List<NotificationDescriptionEntity> descriptionEntities =
				new ArrayList<NotificationDescriptionEntity>();

		for (NotificationDescription description : descriptions) {
			NotificationDescriptionEntity descriptionEntity = new NotificationDescriptionEntity();
			descriptionEntity.setClient(notification.getClient());
			descriptionEntity.setNotificationID(notification.getNotificationId());
			descriptionEntity.setLanguageISOCode(description.getLanguageISoCode());
			descriptionEntity.setShortDescription(description.getShortDescription());
			descriptionEntity.setLongDescription(description.getLongDescription());
			descriptionEntity
					.setSystemAdministrativeData(notification.getSystemAdministrativeData());
			descriptionEntities.add(descriptionEntity);
		}
		return descriptionEntities;
	}
	
	private List<NotificationDescriptionEntity> convertToDescriptionEntity(
			NotificationDescription description, NotificationHeaderEntity notification) {
		
		if(description == null) {
			return null;
		}

		String lang = aud.getUserDetails().getLocale().getLanguage();
		List<NotificationDescriptionEntity> descriptionEntities = new ArrayList<NotificationDescriptionEntity>();

		NotificationDescriptionEntity descriptionEntity = new NotificationDescriptionEntity();
		descriptionEntity.setClient(notification.getClient());
		descriptionEntity.setNotificationID(notification.getNotificationId());
		descriptionEntity.setLanguageISOCode(lang);
		descriptionEntity.setShortDescription(description.getShortDescription());
		descriptionEntity.setLongDescription(description.getLongDescription());
		descriptionEntity
				.setSystemAdministrativeData(notification.getSystemAdministrativeData());
		descriptionEntities.add(descriptionEntity);
		return descriptionEntities;
	}

	private NotificationDescription createNotificationDescription(
			List<NotificationDescriptionEntity> descriptions, String isInternal) {

		NotificationDescriptionEntity descriptionEntity =
				getDescriptionForUserLanguage(descriptions, isInternal);
		NotificationDescription description = new NotificationDescription();
		if (descriptionEntity == null) {
			return description;
		}
		description.setLanguageISoCode(descriptionEntity.getLanguageISOCode());
		description.setShortDescription(descriptionEntity.getShortDescription());
		description.setLongDescription(descriptionEntity.getLongDescription());
		return description;
	}

	private NotificationDescriptionEntity getDescriptionForUserLanguage(
			List<NotificationDescriptionEntity> descriptions, String isInternal) {

		String lang = aud.getUserDetails().getLocale().getLanguage();
		NotificationDescriptionEntity notificatonDescription = null;
		if (isInternal.equals("0")) {
			for (NotificationDescriptionEntity description : descriptions) {
				if (description.getLanguageISOCode().equals(Locale.ENGLISH.getLanguage())) {
					return description;
				}
			}
		}
		for (NotificationDescriptionEntity description : descriptions) {
			if (description.getLanguageISOCode().equals(lang)) {
				return description;
			}

			if (description.getLanguageISOCode().equals(Locale.ENGLISH.getLanguage())) {
				notificatonDescription = description;
			}
		}

		if (notificatonDescription == null) {
			notificatonDescription = new NotificationDescriptionEntity();
		}
		return notificatonDescription;
	}

	private void deleteNotificationStatus(String id) {

		String query =
				"delete from \"AIN_DEV\".\"sap.ain.metaData::Notification.Status\" where \"ID\"=?";
		jdbcTemplate.update(query, new Object[] {id});

	}

	public Set<String> getCauseIds(Collection<String> payloadCauseIds) {
		String getCauseIdQuery =
				"select \"ID\" from \"sap.ain.metaData::FailureMode.CauseEffectDescription\" where \"Type\"='C' and \"Client\" = ? and \"ID\" in ("
						+ ObjectUtils.getQuestionMark(payloadCauseIds.size()) + ");";
		ArrayList<String> idList = new ArrayList<>();
		idList.addAll(payloadCauseIds);
		idList.add(0, AuthenticatedUserDetails.getInstance().getUserDetails().getUserClientId());
		Set<String> dbCauseIds = jdbcTemplate.query(getCauseIdQuery, idList.toArray(),
				new ResultSetExtractor<Set<String>>() {

					@Override
					public Set<String> extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						Set<String> causeId = new HashSet<>();
						while (rs.next()) {
							causeId.add(rs.getString("ID"));
						}
						return causeId;
					}
				});
		return dbCauseIds;
	}



	/**
	 * 
	 * @param id
	 * @return
	 */
	public NotificationGET getNotificatonDetails(String notificationId) {
		String id = this.getNotificationUID(notificationId);
		NotificationHeaderEntity headerEntity = em.find(NotificationHeaderEntity.class, id);
		NotificationGET notification = null;
		if (headerEntity != null) {
			notification = this.notificationEntityToPayload(headerEntity);
		}

		return notification;
	}

	public String getNotificationUID(String notificationId) {
		String notificationUID = null;

		String query =
				"select \"ID\" from \"sap.ain.metaData::Notification.Header\" where \"NotificationID\"= ? and \"isMarkedForDeletion\"='0'";
		notificationUID =
				jdbcTemplate.queryForObject(query, new Object[] {notificationId}, String.class);
		return notificationUID;

	}

	@Transactional
	public void deleteNotifications(final NotificationIDList notificatonIdList) {

		String ids = findCommaSeparatedIDs(notificatonIdList);
		NotificationDeleteValidations valid = new NotificationDeleteValidations(jdbcTemplate);
		NotificationsDeleteValidation validationContext = valid.execute(ids);

		if (!validationContext.getInvalidIds().equals("")) {
			List<ErrorMessage> messageList = new ArrayList<ErrorMessage>();
			ErrorMessage message = new ErrorMessage();
			message.setErrorMessage(resourceBundle.getMessage("invalid.notification.ids",
					new Object[] {validationContext.getInvalidIds()},
					aud.getUserDetails().getLocale()));
			message.setSeverity(Severity.ERROR);
			messageList.add(message);
			throw new CustomExceptionList(Response.Status.BAD_REQUEST.getStatusCode(), messageList);
		}

		String sqlString =
				"update \"sap.ain.metaData::Notification.Header\" set \"isMarkedForDeletion\" = '1' where \"NotificationID\" = ?";
		List<ID> idList = notificatonIdList.getIDs();
		jdbcTemplate.batchUpdate(sqlString, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, idList.get(i).getId());
			}

			@Override
			public int getBatchSize() {
				return idList.size();
			}
		});
		idList.stream().forEach(id -> {
			ainMyObjectsCircle.removeObject(id.getId(), AINObjectTypes.NTF);
		});
	}

	private String findCommaSeparatedIDs(NotificationIDList idList) {

		String ids = "";
		String outString = "";
		List<ID> list = idList.getIDs();
		for (ID object : list) {

			if (!ids.contains(object.getId())) {
				ids = ids + object.getId();
				ids = ids + ",";
			}
		}
		if (ids.length() > 0) {
			outString = ids.substring(0, ids.length() - 1);
		}
		return outString;
	}

	public NotificationGET getNotification(String notificationId) {

		String client = aud.getUserDetails().getUserClientId();
		String lang = aud.getUserDetails().getLocale().getLanguage();

		String sql =
				"SELECT * from \"_SYS_BIC\".\"sap.ain.views/NotificationRead\"  (PLACEHOLDER.\"$$iv_client$$\" => ? ,PLACEHOLDER.\"$$iv_lang$$\"=>?,PLACEHOLDER.\"$$iv_notificationid$$\"=>?)";
		NotificationGET notificationData = jdbcTemplate.queryForObject(sql,
				new Object[] {client, lang, notificationId}, new NotificationGETRowMapper());

		return notificationData;

	}

	public class NotificationGETRowMapper implements RowMapper<NotificationGET> {
		@Override
		public NotificationGET mapRow(ResultSet rs, int rowNum) throws SQLException {
			NotificationGET notification = new NotificationGET();
			notification.setNotificationID(rs.getString("notificationID"));
			notification.setEquipmentID(rs.getString("equipmentID"));
			notification.setEndDate(DateHelper.getDateTime(rs.getTimestamp("endDate")));
			notification.setStartDate(DateHelper.getDateTime(rs.getTimestamp("startDate")));
			notification.setMalfunctionEndDate((rs.getTimestamp("malfunctionEndDate")));
			notification.setMalfunctionStartDate((rs.getTimestamp("malfunctionStartDate")));
			notification.setIsInternal(rs.getString("isInternal"));
			notification.setPriority(rs.getString("priority"));
			notification.setPriorityDescription(rs.getString("priorityDescription"));
			notification.setConfirmedFailureModeID(rs.getString("confirmedFailureModeID"));
			notification.setSystemProposedFailureModeID(rs.getString("systemProposedFailureModeID"));
			notification.setCauseDisplayID(rs.getString("causeID"));
			notification.setEffectID(rs.getString("effectID"));
			notification.setInstructionID(rs.getString("instructionID"));
			notification.setProposedFailureModeID(rs.getString("proposedFailureModeID"));
			notification.setConfirmedFailureModeDisplayID(rs.getString("confirmedFailureModeDisplayID"));
			notification.setConfirmedFailureModeDesc(rs.getString("confirmedFailureModeDesc"));
			notification.setSystemProposedFailureModeDesc(rs.getString("systemProposedFailureModeDesc"));
			notification.setSystemProposedFailureModeDisplayID(rs.getString("systemProposedFailureModeDisplayID"));
			notification.setProposedFailureModeDesc(rs.getString("proposedFailureModeDesc"));
			notification.setProposedFailureModeDisplayID(rs.getNString("proposedFailureModeDisplayID"));
			notification.setProposedFailureModeDesc(rs.getString("systemProposedFailureModeDesc"));
			notification.setPriorityDescription(rs.getString("priorityDescription"));
			notification.setCauseDesc(rs.getString("causeDesc"));
			notification.setEffectDesc(rs.getString("effectDesc"));

			String status = rs.getString("status");
			List<String> statusString = new ArrayList<String>();
			statusString.add(status);

			notification.setStatus(statusString);
			notification.setStatusDescription(rs.getString("statusDescription"));
			notification.setType(rs.getString("notificationType"));
			notification.setTypeDescription(rs.getString("notificationTypeDescription"));

			NotificationDescription desc = new NotificationDescription();
			desc.setShortDescription(rs.getString("shortDescription"));
			desc.setLongDescription(rs.getString("longDescription"));
			notification.setDescription(desc);

			SystemAdministrativeData sysadminData = new SystemAdministrativeData();
			sysadminData.setSystemAdministrativeDataCreatedByUserID(rs.getString("createdBy"));
			sysadminData.setSystemAdministrativeDataCreationDateTime(
					rs.getTimestamp("creationDateTime"));
			sysadminData
					.setSystemAdministrativeDataLastChangedByUserID(rs.getString("lastChangedBy"));
			sysadminData.setSystemAdministrativeDataLastChangeDateTime(
					rs.getTimestamp("lastChangeDateTime"));
			notification.setSystemAdministrativeData(sysadminData);
			return notification;
		}

	}

	public List<NotificationsForEquipment> getNotifications(String equipmentID, String startdate,
			String enddate) {

		String client = aud.getUserDetails().getUserClientId();
		String lang = aud.getUserDetails().getLocale().getLanguage();
		String scope = aud.getUserDetails().getScope();
		String bpid = aud.getUserDetails().getUserBpId();

		String sql =
				"select \"NotificationType\", \"Count\", \"NotificationID\", \"InternalID\", \"ShortDescription\", \"LongDescription\", \"NotificationTypeDescription\", \"Priority\", \"PriorityDescription\", \"ProgressStatus\", \"ProgressStatusDescription\", \"Status\", \"StatusDescription\", \"StartDate\", \"EndDate\" , \"ConfirmedFailureModeID\", \"ProposedFailureModeID\",\"SystemProposedFailureModeID\",\"CauseID\",\"EffectID\",\"InstructionID\" from  \"_SYS_BIC\".\"sap.ain.views/NotificationsForAnEquipment\"(PLACEHOLDER.\"$$iv_client$$\" => ? ,PLACEHOLDER.\"$$iv_lang$$\"=>?,PLACEHOLDER.\"$$iv_equipmentid$$\"=>?, PLACEHOLDER.\"$$iv_scope$$\"=>? ,PLACEHOLDER.\"$$iv_user_bp_id$$\"=>?,PLACEHOLDER.\"$$iv_startdate$$\"=>?,PLACEHOLDER.\"$$iv_enddate$$\"=>?)";
		List<NotificationsForEquipment> notificationDataForEquipment = jdbcTemplate.query(sql,
				new Object[] {client, lang, equipmentID, scope, bpid, startdate, enddate},
				new NotificationsForEquipment());

		return notificationDataForEquipment;
	}


	/* Get All notification implementation for REST API */

	public List<NotificationList> readNotifications(String filter, Integer top, Integer skip,
			String orderBy,String order) throws UnsupportedEncodingException {

		Class<?> entity = null;
		String entityName = "NotificationList";
		ODataSQLContextImpl oDataSQLContextImpl = new ODataSQLContextImpl(null);
		ODataSQLEdmProvider edmProvider = null;
		edmProvider = new ODataSQLEdmProvider(oDataSQLContextImpl, false);
		oDataSQLContextImpl.setEdmProvider(edmProvider);

		if (filter == null) {
			filter = "";
		}

		filter = URLDecoder.decode(filter, "UTF-8");

		if (!Strings.isNullOrEmpty(orderBy)) {
			orderBy = URLDecoder.decode(orderBy, "UTF-8");
		}

		String replacedFilter = getNotificationFilters(filter);

		FilterExpression oDataFilterExpression =
				getODataFilterExpressionForList(replacedFilter, entityName, edmProvider);


		entity = edmProvider.getEntity(entityName);

		ODataSQLFilterExpression filterExpression =
				new ODataSQLFilterExpression(oDataFilterExpression, oDataSQLContextImpl, entity);

		filter = filterExpression.getFilterQuery();

		String orderByColumn = "";
//		String order = "0";
		if(order == null )
		{
			order = "0" ;
		}
		if (Strings.isNullOrEmpty(orderBy)) {

			orderByColumn = "";
		}
		else
			orderByColumn = orderBy;

		if (top == null || skip == null) {
			top = 5000;
			skip = 0;
		}


		String query = "select * from \"_SYS_BIC\".\"sap.ain.views/NotificationList\" "
				+ "( 'placeholder' = ( '$$iv_lang$$', '"
				+ aud.getUserDetails().getLocale().getLanguage() + "' ), "
				+ "'placeholder' = ( '$$iv_client$$', '" + aud.getUserDetails().getUserClientId()
				+ "' )," + " 'placeholder' = ( '$$iv_scope$$', '" + aud.getUserDetails().getScope()
				+ "'), 'placeholder' = ( '$$iv_user_bp_id$$', '"
				+ aud.getUserDetails().getUserBpId() + "'), "
				+ "'placeholder' = ( '$$iv_order_by$$', '" + orderByColumn + "'), "
				+ "'placeholder' = ( '$$iv_order$$', '" + order + "' ), "
			    + "'placeholder' = ('$$iv_equipmentid$$','*')) as "  + ODataConstants.ENTITY_ALIAS;

		// filter
		if (!Strings.isNullOrEmpty(filter)) {
			query = query + ODataConstants.SPACE + ODataConstants.WHERE + ODataConstants.OPEN_BRACE
					+ filter + ODataConstants.CLOSE_BRACE;
		}


//		// orderby
//		if (orderByColumn != null && !orderByColumn.equals("")) {
//			query = query + ODataConstants.SPACE + ODataConstants.ORDER_BY + ODataConstants.DOUBLE_QUOTE
//					+ orderByColumn + ODataConstants.DOUBLE_QUOTE;
//			if (order != null && !order.equals(""))
//				query = query + order + ODataConstants.SPACE;
//		}
		// topskip

		query = query + ODataConstants.SPACE + ODataConstants.LIMIT + top + ODataConstants.OFFSET
				+ skip;

		// queryparameters
		SQLEdmEntity sqlEdmEntity = edmProvider.getSQLEdmEntity(entity);
		SQLFilterExpression sqlFilterExpression =
				new SQLFilterExpression(oDataFilterExpression, sqlEdmEntity.getTable());

		List<QueryParameter> queryParameters = sqlFilterExpression.getQueryParameters();
		Object[] args = new Object[queryParameters.size()];
		int[] argsTypes = new int[queryParameters.size()];
		for (int i = 0; i < queryParameters.size(); i++) {
			args[i] = queryParameters.get(i).getParameter();
			argsTypes[i] = queryParameters.get(i).getType();
		}


		List<NotificationList> notificationList =
				(List<NotificationList>) jdbcTemplate.query(query, args, argsTypes,
						new GenericRowMapper<NotificationList>(NotificationList.class));
		return notificationList;

	}


	private String getNotificationFilters(String filter) {

		Tokenizer tokenizer = new Tokenizer(filter);
		TokenList tokens;
		try {
			tokens = tokenizer.tokenize();
		} catch (ExpressionParserException | TokenizerException e) {
			logger.error("exception occured while getting notification filter...");
			List<ErrorMessage> message = new ArrayList<>();
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setSeverity(Severity.ERROR);
			errorMessage.setErrorMessage(resourceBundle.getMessage("odata.exception", null,
					aud.getUserDetails().getLocale()));
			message.add(errorMessage);
			throw new CustomExceptionList(Response.Status.BAD_REQUEST.getStatusCode(), message);
		}
		String tableFilter = "";
		Map<String, String> columns = getColumnsMap();
		while (tokens.hasNext()) {
			Token token = tokens.next();
			if (token.getKind().equals(TokenKind.LITERAL)) {
				String literal = columns.get(token.getUriLiteral());
				if (!ObjectUtils.isNull(literal)) {
					tableFilter = tableFilter + literal + ODataConstants.SPACE ;
				} else {
					tableFilter =ODataConstants.SPACE + tableFilter + token.getUriLiteral() + ODataConstants.SPACE;
				}
			} else {
				tableFilter = tableFilter + token.getUriLiteral() + ODataConstants.SPACE;
			}
		}

		return tableFilter;
	}

	private Map<String, String> getColumnsMap() {

		Map<String, String> map = new HashMap<String, String>();
		map.put("notificationId", "NotificationID");
		map.put("shortDescription", "ShortDescription");
		map.put("status", "Status");
		map.put("statusDescription", "StatusDescription");
		map.put("notificationType", "NotificationType");
		map.put("notificationTypeDescription", "NotificationTypeDescription");
		map.put("priority", "Priority");
		map.put("priorityDescription", "PriorityDescription");
		map.put("isInternal", "IsInternal");
		map.put("createdBy", "CreatedBy");
		map.put("creationDateTime", "CreationDateTime");
		map.put("lastChangedBy", "LastChangedBy");
		map.put("lastChangeDateTime", "LastChangeDateTime");
		map.put("longDescription", "LongDescription");
		map.put("startDate", "StartDate");
		map.put("endDate", "EndDate");
		map.put("malfunctionStartDate", "MalfunctionStartDate");
		map.put("malfunctionEndDate", "MalfunctionEndDate");
		map.put("progressStatus", "ProgressStatus");
		map.put("progressStatusDescription", "ProgressStatusDescription");
		map.put("equipmentId", "EquipmentID");
		map.put("equipmentName", "EquipmentName");
		map.put("rootEquipmentId", "RootEquipmentID");
		map.put("rootEquipmentName", "RootEquipmentName");
		map.put("locationId", "LocationID");
		map.put("breakdown", "Breakdown");
		map.put("coordinates", "Coordinates");
		map.put("source", "Source");
		map.put("operatorId", "OperatorID");
		map.put("location", "Location");
		map.put("assetCoreEquipmentId", "AssetCoreEquipmentID");
		map.put("operator", "Operator");
//		map.put("noOfAlertsForNotification", "NoOfAlertsForNotification");
		map.put("internalId", "InternalID");
		map.put("modelId", "ModelID");
		map.put("proposedFailureModeID", "ProposedFailureModeID");
		map.put("confirmedFailureModeID", "ConfirmedFailureModeID");
		map.put("proposedFailureModeDesc", "ProposedFailureModeDesc");
		map.put("proposedFailureModeDisplayID", "ProposedFailureModeDisplayID");
		map.put("confirmedFailureModeDisplayID", "ConfirmedFailureModeDisplayID");
		map.put("confirmedFailureModeDesc","ConfirmedFailureModeDesc");
		map.put("systemProposedFailureModeDisplayID", "SystemProposedFailureModeDisplayID");
		map.put("systemProposedFailureModeDesc", "SystemProposedFailureModeDesc");
		map.put("effectID", "EffectID");
		map.put("causeDesc", "CauseDesc");
		map.put("effectDesc", "EffectDesc");

		map.put("effectDisplayID", "EffectDisplayID");
		map.put("causeID", "CauseID");
		map.put("causeDisplayID", "CauseDisplayID");
		map.put("instructionID", "InstructionID");
		map.put("instructionTitle", "InstructionTitle");
		return map;
	}

	public List<NotificationAlertMapping> getNotificationsForAlert(Set<String> alertIds) {
		String sql =
				"select \"Alert\".\"NotificationID\", \"Alert\".\"AlertID\",\"Hdr\".\"InternalID\",\"Hdr\".\"EquipmentID\" from \"sap.ain.metaData::Notification.NotificationAlertMapping\" as \"Alert\" join \"sap.ain.metaData::Notification.Header\" as \"Hdr\" "
						+ " on \"Hdr\".\"NotificationID\" = \"Alert\".\"NotificationID\" where \"Alert\".\"Client\" = ? and \"Alert\".\"AlertID\" in ("
						+ ObjectUtils.getQuestionMark(alertIds.size()) + ");";

		ArrayList<String> idList = new ArrayList<>();
		idList.addAll(alertIds);
		idList.add(0, AuthenticatedUserDetails.getInstance().getUserDetails().getUserClientId());

		List<NotificationAlertMapping> notificationDataForAlert =
				jdbcTemplate.query(sql, idList.toArray(), new NotificationAlertMapping());

		return notificationDataForAlert;
	}


	public String generateInternalID() {
		GetInternalIDProcHandler handler =
				new GetInternalIDProcHandler(AINObjectTypes.NTF.toString());
		String context = null;
		String internalID = null;
		if (handler != null) {
			context = handler.getObject();
		}

		if (context != null) {
			internalID = context.toString();
		}
		return internalID;
	}

	public List<NotificationList> getNotificationsWithoutExternalId() {
		String query = "select * from \"_SYS_BIC\".\"sap.ain.views/Notifications\""
                + "(PLACEHOLDER.\"$$iv_scope$$\" => ? ,PLACEHOLDER.\"$$iv_client$$\"=> ?, "
                + "PLACEHOLDER.\"$$iv_user_bp_id$$\"=> ?,PLACEHOLDER.\"$$iv_lang$$\"=> ?, "
                + "PLACEHOLDER.\"$$iv_equipmentid$$\"=> ?) as \"Notification\" "
		+ " Where \"Notification\".\"NotificationID\" Not IN(Select Distinct \"ExternalID\".\"AINObjectID\" as \"NotificationID\" from"
		+ " \"sap.ain.metaData::Configurations.ExternalIDMapping\" as \"ExternalID\" where \"ExternalID\".\"ObjectType\"='NTF' and \"ExternalID\".\"Client\" = ?);";
		List<NotificationList> notificationList = (List<NotificationList>) jdbcTemplate.query(query,
				new Object[] {aud.getUserDetails().getScope(), aud.getUserDetails().getUserClientId(),
						aud.getUserDetails().getUserBpId(), aud.getUserDetails().getLocale().getLanguage(),
						'*', aud.getUserDetails().getUserClientId()}, new GenericRowMapper<NotificationList>(NotificationList.class));
		return notificationList;
	}

	public void persistExternalIdMapping(String ainObjectId, String externalId, String extSysId) {
		ExternalIdMapping externalIdMapping = new ExternalIdMapping();
		externalIdMapping.setAINObjectID(ainObjectId);
		externalIdMapping.setObjectType(AINObjectTypes.NTF.toString());
		IDMapping idMapping = new IDMapping();
		idMapping.setExternalID(externalId);
		idMapping.setSystemID(extSysId);
		externalIdMapping.getIDMappings().add(idMapping);
		mapExternalIDs.assignExternalIDsToAinObjects(externalIdMapping);
	}

	public void upsertExternalIdMapping(String ainObjectId, String externalId, String extSysId) {
		String queryForConfigId =
				"select  \"ConfigID\" from  \"sap.ain.metaData::Configurations.ExternalIDMapping\""
						+ " where \"AINObjectID\" = ? and \"ObjectType\" = ? and \"Client\" = ?";
		String configId = "";

		try {
			configId = jdbcTemplate.queryForObject(queryForConfigId, new Object[] {ainObjectId,
					AINObjectTypes.NTF.toString(), aud.getUserDetails().getUserClientId()},
					String.class);
		} catch (DataAccessException exception) {
			logger.debug("DataAccessException occured while upserting external id mapping...");
		}

		if (!Strings.isNullOrEmpty(configId)) {
			List<ExternalIDPayload> deleteIds = new ArrayList<>();
			ExternalIDPayload id = new ExternalIDPayload();
			id.setID(configId);
			deleteIds.add(id);
			mapExternalIDs.deleteExternalIDsAssignments(deleteIds);
		}


		if (Strings.isNullOrEmpty(externalId)) {
			return;
		}
		persistExternalIdMapping(ainObjectId, externalId, extSysId);
	}

	public NotificationGETWithItems getNotificationById(String notificationId) {
		String client = aud.getUserDetails().getUserClientId();
		TypedQuery<NotificationHeaderEntity> notificationQuery = em.createQuery(
				"select Notification from NotificationHeaderEntity Notification WHERE Notification.client.client = :client and Notification.notificationId=:notificationId and Notification.isMarkedForDeletion=:isMarkedForDeletion",
				NotificationHeaderEntity.class);
		notificationQuery.setParameter("client", client);
		notificationQuery.setParameter("notificationId", notificationId);
		notificationQuery.setParameter("isMarkedForDeletion", "0");
		List<NotificationHeaderEntity> notifications = notificationQuery.getResultList();
		if (notifications == null || notifications.isEmpty()) {
			return null;
		}
		return convertNotificationDBEntityToNotificationPayload(notifications.get(0));
	}

	public NotificationGETWithItems convertNotificationDBEntityToNotificationPayload(
			NotificationHeaderEntity notificationHeaderEntity) {
		NotificationGETWithItems notificationGETWithItems = new NotificationGETWithItems();
		notificationGETWithItems.setNotificationID(notificationHeaderEntity.getNotificationId());
		notificationGETWithItems.setExternalID(notificationHeaderEntity.getExternalId());
		notificationGETWithItems.setEquipmentID(notificationHeaderEntity.getEquipmentId());
		notificationGETWithItems.setLocationID(notificationHeaderEntity.getLocationId());
		notificationGETWithItems.setBreakdown(notificationHeaderEntity.getBreakdown());
		notificationGETWithItems.setType(notificationHeaderEntity.getType());
		notificationGETWithItems.setProposedFailureModeID(notificationHeaderEntity.getProposedFailureModeID());
		notificationGETWithItems.setConfirmedFailureModeID(notificationHeaderEntity.getConfirmedFailureModeID());
		notificationGETWithItems.setSystemProposedFailureModeID(notificationHeaderEntity.getSystemProposedFailureModeID());
		notificationGETWithItems.setCauseID(notificationHeaderEntity.getCauseID());
		notificationGETWithItems.setEffectID(notificationHeaderEntity.getEffectID());
		notificationGETWithItems.setInstructionID(notificationHeaderEntity.getInstructionID());
		notificationGETWithItems.setPriority(String.valueOf(notificationHeaderEntity.getPriority()));
		List<NotificationStatusEntity> statusEntityList = notificationHeaderEntity.getStatus();
		List<String> statusList = new ArrayList<>();
		if (statusEntityList.size() > 0) {
			for (NotificationStatusEntity status : statusEntityList) {
				statusList.add(status.getStatus());
			}
		}
		notificationGETWithItems.setStatus(statusList);
		notificationGETWithItems
				.setStartDate(DateHelper.getDateTime(notificationHeaderEntity.getStartDate()));
		notificationGETWithItems
				.setEndDate(DateHelper.getDateTime(notificationHeaderEntity.getEndDate()));
		notificationGETWithItems
				.setMalfunctionStartDate(notificationHeaderEntity.getMalfunctionStartDate());
		notificationGETWithItems
				.setMalfunctionEndDate(notificationHeaderEntity.getMalfunctionEndDate());
		notificationGETWithItems.setDescription(
				createNotificationDescription(notificationHeaderEntity.getDescriptions(),
						notificationHeaderEntity.getIsInternal()));
//		notificationGETWithItems.setGeometry(
//				GeojsonJtsConversion.getGeoJsonObject(notificationHeaderEntity.getGeometry()));
		SystemAdministrativeData systemAdministrativeData =
				notificationHeaderEntity.getSystemAdministrativeData();
		if (systemAdministrativeData != null) {
			AdminData adminData = new AdminData();
			adminData.setChangedBy(
					systemAdministrativeData.getSystemAdministrativeDataLastChangedByUserID());
			adminData.setChangedOn(DateHelper.getDateTime(
					systemAdministrativeData.getSystemAdministrativeDataLastChangeDateTime()));
			adminData.setCreatedBy(
					systemAdministrativeData.getSystemAdministrativeDataCreatedByUserID());
			adminData.setCreatedOn(DateHelper.getDateTime(
					systemAdministrativeData.getSystemAdministrativeDataCreationDateTime()));
			notificationGETWithItems.setAdminData(adminData);
		}
		notificationGETWithItems.setOperator(notificationHeaderEntity.getOperator());
//		notificationGETWithItems.setAlertIDs(createPayloadAlertIds(
//				notificationHeaderEntity.getNotificationAlertMappingEntity()));
		notificationGETWithItems
				.setItems(createPayloadItems(notificationHeaderEntity.getNotificationItemEntity()));
		return notificationGETWithItems;
	}

//	private List<AlertID> createPayloadAlertIds(
//			List<NotificationAlertMappingEntity> alertMappingEntity) {
//		if (alertMappingEntity == null || alertMappingEntity.isEmpty()) {
//			return null;
//		}
//		List<AlertID> alertIds = new ArrayList<>();
//		alertMappingEntity.forEach(alertEntity -> {
//			AlertID alertId = new AlertID();
//			alertId.setId(alertEntity.getAlertId());
//			alertIds.add(alertId);
//		});
//		return alertIds;
//	}

	private List<NotificationItems> createPayloadItems(List<NotificationItemEntity> itemEntities) {
		if (itemEntities == null || itemEntities.isEmpty()) {
			return null;
		}
		List<NotificationItems> notificationItems = new ArrayList<>();
		itemEntities.forEach(itemEntity -> {
			NotificationItems item = new NotificationItems();
			item.setItemNumber(itemEntity.getItemNumber());
			item.setShortDescription(itemEntity.getShortDescription());
			item.setLongDescription(itemEntity.getLongDescription());
			item.setNotificationCauseCodes(
					createNotificationItemCauseCodes(itemEntity.getNotificationCauseCodesEntity()));
			if (itemEntity.getNotificationFailureModesEntity() != null) {
				NotificationFailureModes notificationFailureModes = new NotificationFailureModes();
				notificationFailureModes.setFailureModeID(
						itemEntity.getNotificationFailureModesEntity().getFailureModeId());
				item.setNotificationFailureModes(notificationFailureModes);
			}
			notificationItems.add(item);
		});
		return notificationItems;
	}

	private List<NotificationCauseCodes> createNotificationItemCauseCodes(
			List<NotificationCauseCodesEntity> causeCodesEntities) {
		if (causeCodesEntities == null || causeCodesEntities.isEmpty()) {
			return null;
		}
		List<NotificationCauseCodes> causeCodes = new ArrayList<>();
		causeCodesEntities.forEach(causeCodeEntity -> {
			NotificationCauseCodes causeCode = new NotificationCauseCodes();
			causeCode.setCauseID(causeCodeEntity.getCauseId());
			causeCode.setCauseDescription(causeCodeEntity.getCauseDescription());
			causeCodes.add(causeCode);
		});
		return causeCodes;
	}

	public String getAINObjectIdForExternalId(String systemId, String externalId) {
		String ainIdsForExternalIds = "select  \"ExternalIDMapping\".\"AINObjectID\"  from "
				+ "\"sap.ain.metaData::Configurations.ExternalIDMapping\" as \"ExternalIDMapping\" where \"ExternalIDMapping\".\"Client\" = ? "
				+ " and \"ExternalIDMapping\".\"ID\" = ? and \"ExternalIDMapping\".\"ObjectType\" = ? and "
				+ "\"ExternalIDMapping\".\"ExternalID\" = ?";

		String ainId = "";
		try {
			ainId = jdbcTemplate.queryForObject(ainIdsForExternalIds,
					new Object[] {aud.getUserDetails().getUserClientId(), systemId,
							AINObjectTypes.GRP.toString(), externalId},
					String.class);
		} catch (DataAccessException exception) {
			logger.error("{}", exception);
		}
		return ainId;
	}

	@NotificationGroupBusinessObjectValidations
	public ResponseData updateGroupWithBusniessObjects(String groupId,
			List<UpdateGroupWithBusinessObject> businessObjects) {
		return groupDao.addBusinessObjectToGroup(groupId, businessObjects);
	}

	@UnassignObjectFromMultipleGroupsValidations
	public ResponseData updateObjectGroups(String boId, String boType, List<String> groups)
			throws SQLException {
		return groupDao.updateGroupsForObject(boId, boType, groups);
	}

	public NotificationProgressStatusCount getNotificationCountByStatus(String equipmentID) {

		NotificationCountByStatusProcedure proc = new NotificationCountByStatusProcedure();
		return proc.execute(equipmentID);

	}

	private class NotificationCountByStatusProcedure extends HanaStoredProcedure {

		private static final String IN_CLIENT = "iv_client";
		private static final String IN_LANG = "iv_lang";
		private static final String IN_EQUIPMENTID = "iv_equipmentid";
		private static final String IN_SCOPE = "iv_scope";
		private static final String IN_USER_BP_ID = "iv_user_bp_id";

		private static final String OUT_NO_COUNT = "ot_notification_count";
		private static final String OUT_TOTAL_NO_COUNT = "ot_total_no_count";

		public NotificationCountByStatusProcedure() {
			super(jdbcTemplate, "\"sap.ain.proc.notification::NotificationCountByStatus\"");
			declareParameter();
			compile();
		}

		@SuppressWarnings("unchecked")
		public NotificationProgressStatusCount execute(String equipmentID) {

			Map<String, Object> inputs = new HashMap<String, Object>();

			inputs.put(IN_CLIENT,
					AuthenticatedUserDetails.getInstance().getUserDetails().getUserClientId());
			inputs.put(IN_LANG, AuthenticatedUserDetails.getInstance().getUserDetails().getLocale()
					.getLanguage());
			inputs.put(IN_EQUIPMENTID, equipmentID);
			
			inputs.put(IN_SCOPE,AuthenticatedUserDetails.getInstance().getUserDetails().getScope());

			inputs.put(IN_USER_BP_ID,AuthenticatedUserDetails.getInstance().getUserDetails().getUserBpId());


			NotificationProgressStatusCount count = new NotificationProgressStatusCount();

			List<NotificationCountByStatus> notification =
					new ArrayList<NotificationCountByStatus>();

			try {
				Map<String, Object> output = super.execute(inputs);
				notification = (List<NotificationCountByStatus>) (output.get(OUT_NO_COUNT));
				count.setTotalCount((int) output.get(OUT_TOTAL_NO_COUNT));

			} catch (Exception e) {
				logger.debug(
						"Exception occured while calling procedure to get notification status by count...");
			}

			count.setNotificationProgressStatus(notification);

			return count;
		}

		private void declareParameter() {

			declareParameter(new SqlParameter(IN_CLIENT, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_LANG, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_EQUIPMENTID, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_SCOPE, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_USER_BP_ID, Types.NVARCHAR));

			declareParameter(new SqlReturnResultSet(OUT_NO_COUNT,
					new GenericRowMapper<NotificationCountByStatus>(
							NotificationCountByStatus.class)));
			declareParameter(new SqlOutParameter(OUT_TOTAL_NO_COUNT, Types.INTEGER));

		}
	}

	public NotificationProgressStatusCount getNotificationCountForLocationByStatus(
			String locationID) {

		NotificationCountForLocationByStatusProcedure proc =
				new NotificationCountForLocationByStatusProcedure();
		return proc.execute(locationID);

	}

	private class NotificationCountForLocationByStatusProcedure extends HanaStoredProcedure {

		private static final String IN_CLIENT = "iv_client";
		private static final String IN_LANG = "iv_lang";
		private static final String IN_LOCATIONID = "iv_locationid";

		private static final String OUT_NO_COUNT = "ot_notification_count";
		private static final String OUT_TOTAL_NO_COUNT = "ot_total_no_count";

		public NotificationCountForLocationByStatusProcedure() {
			super(jdbcTemplate,
					"\"sap.ain.proc.notification::NotificationCountForLocationByStatus\"");
			declareParameter();
			compile();
		}

		@SuppressWarnings("unchecked")
		public NotificationProgressStatusCount execute(String locationID) {

			Map<String, Object> inputs = new HashMap<String, Object>();

			inputs.put(IN_CLIENT,
					AuthenticatedUserDetails.getInstance().getUserDetails().getUserClientId());
			inputs.put(IN_LANG, AuthenticatedUserDetails.getInstance().getUserDetails().getLocale()
					.getLanguage());
			inputs.put(IN_LOCATIONID, locationID);

			NotificationProgressStatusCount count = new NotificationProgressStatusCount();

			List<NotificationCountByStatus> notification =
					new ArrayList<NotificationCountByStatus>();

			try {
				Map<String, Object> output = super.execute(inputs);
				notification = (List<NotificationCountByStatus>) (output.get(OUT_NO_COUNT));
				count.setTotalCount((int) output.get(OUT_TOTAL_NO_COUNT));

			} catch (Exception e) {
				logger.debug(
						"Exception occured while calling procedure to get notification status by count...");
			}

			count.setNotificationProgressStatus(notification);

			return count;
		}

		private void declareParameter() {

			declareParameter(new SqlParameter(IN_CLIENT, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_LANG, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_LOCATIONID, Types.NVARCHAR));

			declareParameter(new SqlReturnResultSet(OUT_NO_COUNT,
					new GenericRowMapper<NotificationCountByStatus>(
							NotificationCountByStatus.class)));
			declareParameter(new SqlOutParameter(OUT_TOTAL_NO_COUNT, Types.INTEGER));

		}
	}

	public boolean isSystemIdValid(String externalSystemId) {
		boolean isValid = false;
		String query =
				"SELECT count(*) FROM \"_SYS_BIC\".\"sap.ain.views/ExternalSystemsList\" (PLACEHOLDER.\"$$iv_client$$\" => ? ,PLACEHOLDER.\"$$iv_lang$$\"=>?)"
						+ " where \"SystemStatusDescription\" = 'Active' and \"ID\" = ?";
		try {
			int count = jdbcTemplate.queryForObject(query,
					new Object[] {aud.getUserDetails().getUserClientId(), "en", externalSystemId},
					Integer.class);
			if (count > 0) {
				return isValid = true;
			}
		} catch (DataAccessException exception) {
			logger.error(
					"Data access exception occurred while getting system id... in NotificationDao.getSystemId: {} "
							+ exception.getMessage());
		}
		return isValid;
	}

	public List<NotificationDescription> getNotificationDescription(String notificationId) {
		TypedQuery<NotificationDescriptionEntity> notificationDescriptionQuery = em.createQuery(
				"select descEntity from NotificationDescriptionEntity descEntity WHERE descEntity.client.client = :client and descEntity.notificationID=:notificationId",
				NotificationDescriptionEntity.class);
		notificationDescriptionQuery.setParameter("client", aud.getUserDetails().getUserClientId());
		notificationDescriptionQuery.setParameter("notificationId", notificationId);
		List<NotificationDescriptionEntity> notificationDescriptionEntity = notificationDescriptionQuery.getResultList();
		List<NotificationDescription> descriptionPayload = new ArrayList<>();
		if(!ObjectUtils.isListEmpty(notificationDescriptionEntity)) {
			notificationDescriptionEntity.forEach(notification -> {
				NotificationDescription description = new NotificationDescription();
				description.setLanguageISoCode(notification.getLanguageISOCode());
				description.setShortDescription(notification.getShortDescription());
				description.setLongDescription(notification.getLongDescription());
				descriptionPayload.add(description);
			});
		}
		return descriptionPayload;
	}
	
	public String getSystemId() {
		String query =
				"SELECT top 1 \"ID\" FROM \"_SYS_BIC\".\"sap.ain.views/ExternalSystemsList\" (PLACEHOLDER.\"$$iv_client$$\" => ? ,PLACEHOLDER.\"$$iv_lang$$\" => ?)"
						+ " where \"SystemStatusDescription\" = 'Active' and \"SystemType\" = 'SAP ERP'";

		String extSysId = "";
		try {
			extSysId = jdbcTemplate.queryForObject(query,
					new Object[] {aud.getUserDetails().getUserClientId(), "en"}, String.class);
		} catch (DataAccessException exception) {
			logger.debug("DataAccessException occured while getting external id for notification...");
		}
		return extSysId;
	}
	
	public String getToken(String authorization) {
		String token = null;
		if (EnvironmentUtils.isXSA() || EnvironmentUtils.isCF()) {
			token = authorization;
		}

		if (token == null)
			return authorization;
		return token;
	}
	
	public Long getNotificationCount(String filter) throws UnsupportedEncodingException {

		Class<?> entity = null;
		String entityName = "NotificationList";
		ODataSQLContextImpl oDataSQLContextImpl = new ODataSQLContextImpl(null);
		ODataSQLEdmProvider edmProvider = null;
		edmProvider = new ODataSQLEdmProvider(oDataSQLContextImpl, false);
		oDataSQLContextImpl.setEdmProvider(edmProvider);

		if (filter == null) {
			filter = "";
		}

		filter = URLDecoder.decode(filter, "UTF-8");

		String replacedFilter = getNotificationFilters(filter);

		FilterExpression oDataFilterExpression =
				getODataFilterExpressionForList(replacedFilter, entityName, edmProvider);


		entity = edmProvider.getEntity(entityName);

		ODataSQLFilterExpression filterExpression =
				new ODataSQLFilterExpression(oDataFilterExpression, oDataSQLContextImpl, entity);

		filter = filterExpression.getFilterQuery();

		String orderByColumn = "";
		String order = "asc";

		String query = "select count(*) from \"_SYS_BIC\".\"sap.ain.views/NotificationList\" "
				+ "( 'placeholder' = ( '$$iv_lang$$', '"
				+ aud.getUserDetails().getLocale().getLanguage() + "' ), "
				+ "'placeholder' = ( '$$iv_client$$', '" + aud.getUserDetails().getUserClientId()
				+ "' )," + " 'placeholder' = ( '$$iv_scope$$', '" + aud.getUserDetails().getScope()
				+ "'), 'placeholder' = ( '$$iv_user_bp_id$$', '"
				+ aud.getUserDetails().getUserBpId() + "'), "
			    + "'placeholder' = ('$$iv_equipmentid$$','*')) as "  + ODataConstants.ENTITY_ALIAS;

		// filter
		if (!Strings.isNullOrEmpty(filter)) {
			query = query + ODataConstants.SPACE + ODataConstants.WHERE + ODataConstants.OPEN_BRACE
					+ filter + ODataConstants.CLOSE_BRACE;
		}

		// queryparameters
		SQLEdmEntity sqlEdmEntity = edmProvider.getSQLEdmEntity(entity);
		SQLFilterExpression sqlFilterExpression =
				new SQLFilterExpression(oDataFilterExpression, sqlEdmEntity.getTable());

		List<QueryParameter> queryParameters = sqlFilterExpression.getQueryParameters();
		Object[] args = new Object[queryParameters.size()];
		int[] argsTypes = new int[queryParameters.size()];
		for (int i = 0; i < queryParameters.size(); i++) {
			args[i] = queryParameters.get(i).getParameter();
			argsTypes[i] = queryParameters.get(i).getType();
		}
		
		Long count = jdbcTemplate.queryForObject(query, args, argsTypes, Long.class);


		return count;
	}
	private FilterExpression getODataFilterExpressionForList(String filter, String entity,
			EdmProvider edmProvider) {


		Edm edm = RuntimeDelegate.createEdm(edmProvider);
		FilterExpression filterExpr = null;
		try {
			filterExpr =
					UriParser.parseFilter(edm, edm.getEntityType("ODataEntities", entity), filter);
		} catch (ODataMessageException e) {
			String errorMessage = e.getMessage().substring(0, e.getMessage().indexOf("in"));
			throw new CustomExceptionList(400,
					DaoHelper.getErrorMessages(Severity.ERROR, errorMessage));
		}

		return filterExpr;

	}

}
