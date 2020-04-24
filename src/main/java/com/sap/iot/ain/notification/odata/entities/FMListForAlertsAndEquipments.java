package com.sap.iot.ain.notification.odata.entities;

import javax.persistence.Column;
import javax.persistence.Id;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;

import com.sap.iot.ain.odata.core.ODataEntity;
import com.sap.iot.ain.odata.core.annotations.CustomParameters;
import com.sap.iot.ain.odata.core.annotations.Entity;
import com.sap.iot.ain.odata.core.annotations.FacetsProperty;
import com.sap.iot.ain.odata.core.annotations.SapAnnotation;
import com.sap.iot.ain.odata.core.annotations.SapPropertyAnnotationList;
import com.sap.iot.ain.odata.core.annotations.SystemParameters;
import com.sap.iot.ain.odata.core.annotations.Table;
import com.sap.iot.ain.security.Secure;

/**
 * @author i311136
 *
 */
@Table(name = FMListForAlertsAndEquipments.DB_TABLE)
@Entity(name = "FMListForAlertsAndEquipments")
@SystemParameters(client = true, language = true, scope = true, user_bp_id = true)
@CustomParameters(parameters = {"iv_equipment_id", "iv_alerttype_ids"})
@Secure(read = {"EQUIPMENT_READ"}, write = {"EQUIPMENT_EDIT"}, delete = {"EQUIPMENT_DELETE"})
public class FMListForAlertsAndEquipments extends ODataEntity {
	
	public static final String DB_TABLE = "\"_SYS_BIC\".\"sap.ain.views/FMListForAlertsAndEquipments\"";
	
	@Id
	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "false")})
	@Column(name = "\"ID\"")
	@FacetsProperty(maxLength = 32, nullable = false)
	private String id;
	
	@SapPropertyAnnotationList(annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"Client\"")
	@FacetsProperty(maxLength = 32)
	private String client;
	
	@SapPropertyAnnotationList(annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"SubClass\"")
	@FacetsProperty(maxLength = 256)
	private String subClass;
	
	@SapPropertyAnnotationList(annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"SubClassDescription\"")
	@FacetsProperty(maxLength = 255)
	private String subClassDescription;
	
	@SapPropertyAnnotationList(annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"Type\"")
	@FacetsProperty(maxLength = 3000)
	private String type;
	
	@SapPropertyAnnotationList(annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"Source\"")
	@FacetsProperty(maxLength = 256)
	private String source;
	
	@SapPropertyAnnotationList(annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"SourceID\"")
	@FacetsProperty(maxLength = 255)
	private String sourceID;
	
	@Column(name = "\"Version\"")
	@FacetsProperty(edmSimpleTypeKind = EdmSimpleTypeKind.Double)
	private Double version;
	
	@SapPropertyAnnotationList(annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"ShortDescription\"")
	@FacetsProperty(maxLength = 256)
	private String shortDescription;
	
	@SapPropertyAnnotationList(annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"LongDescription\"")
	@FacetsProperty(maxLength = 5000)
	private String longDescription;
	
	@SapPropertyAnnotationList(annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"Owner\"")
	@FacetsProperty(maxLength = 255)
	private String owner;
	
	@SapPropertyAnnotationList(annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"ImageID\"")
	@FacetsProperty(maxLength = 255)
	private String imageID;
	
	@SapPropertyAnnotationList(annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"TypeCode\"")
	@FacetsProperty(maxLength = 10)
	private String typeCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getSubClass() {
		return subClass;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	public String getSubClassDescription() {
		return subClassDescription;
	}

	public void setSubClassDescription(String subClassDescription) {
		this.subClassDescription = subClassDescription;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceID() {
		return sourceID;
	}

	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}

	public Double getVersion() {
		return version;
	}

	public void setVersion(Double version) {
		this.version = version;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getImageID() {
		return imageID;
	}

	public void setImageID(String imageID) {
		this.imageID = imageID;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	
}
