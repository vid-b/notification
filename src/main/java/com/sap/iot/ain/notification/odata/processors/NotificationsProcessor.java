package com.sap.iot.ain.notification.odata.processors;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.ep.callback.WriteEntryCallbackContext;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.info.DeleteUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetCountUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntitySetUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetEntityUriInfo;
import org.apache.olingo.odata2.api.uri.info.GetMediaResourceUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;
import org.springframework.jdbc.core.JdbcTemplate;

import com.sap.iot.ain.notification.odata.entities.Notifications;
import com.sap.iot.ain.odata.core.ODataEntity;
import com.sap.iot.ain.odata.core.processor.EntityProcessor;
import com.sap.iot.ain.odata.core.processor.ODataSQLContext;
import com.sap.iot.ain.reuse.GenericRowMapper;
import com.sap.iot.ain.validation.utils.AINSingletonSpringBeans;


public class NotificationsProcessor implements EntityProcessor<Notifications> {

	ODataSQLContext oDataSQLContext;

	public NotificationsProcessor(ODataSQLContext oDataSQLContext) {
		this.oDataSQLContext = oDataSQLContext;
	}

	public List<Notifications> readEntitySet(String query, Object[] args, int[] argTypes) {
		JdbcTemplate jdbcTemplate = AINSingletonSpringBeans.getInstance().getJdbcTemplate();
		List<Notifications> data = jdbcTemplate.query(query, args,
				new GenericRowMapper<Notifications>(Notifications.class));
		return data;
	}

	@Override
	public Notifications createEntity(PostUriInfo uriInfo, InputStream content,
			String requestContentType, String contentType) throws ODataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Notifications updateEntity(PutMergePatchUriInfo uriInfo, InputStream content,
			String requestContentType, boolean merge, String contentType) throws ODataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ODataResponse deleteEntity(DeleteUriInfo uriInfo, String contentType)
			throws ODataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] readEntityMedia(GetMediaResourceUriInfo uriInfo, String contentType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readMimeType(GetMediaResourceUriInfo uriInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Notifications> readEntitySetWithDelta(GetEntitySetUriInfo uriInfo,
			String contentType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Notifications> readDeletedData(GetEntitySetUriInfo uriInfo, String contentType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> readExpandedData(WriteEntryCallbackContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Notifications> readEntitySet(GetEntitySetUriInfo requestView) throws EdmException {
		List<?> data = oDataSQLContext.getDefaultODataProcessor().readEntitySet(requestView);
		return (List<Notifications>) data;
	}

	@Override
	public Notifications readEntity(GetEntityUriInfo requestView) throws EdmException {
		ODataEntity data = oDataSQLContext.getDefaultODataProcessor().readEntity(requestView);
		return (Notifications) data;
	}

	@Override
	public long countEntitySet(GetEntitySetCountUriInfo requestView) throws EdmException {
		long count = oDataSQLContext.getDefaultODataProcessor().countEntitySet(requestView);
		return count;
	}

	@Override
	public long countEntitySet(GetEntitySetUriInfo requestView) throws EdmException {
		long count = oDataSQLContext.getDefaultODataProcessor().countEntitySet(requestView);
		return count;
	}

	@Override
	public long existsEntity(GetEntityCountUriInfo uriInfo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, Object> createEntityLink(PostUriInfo uriInfo, InputStream content,
			String requestContentType, String contentType) {

		return null;
	}

	@Override
	public ODataResponse deleteEntityLink(DeleteUriInfo uriInfo, String contentType)
			throws ODataException {

		return null;
	}

	@Override
	public Map<String, Object> updateEntityMedia(PutMergePatchUriInfo uriInfo, InputStream content,
			String requestContentType, String contentType) {
		// TODO Auto-generated method stub
		return null;
	}


}
