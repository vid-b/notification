package com.sap.iot.ain.notification.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sap.iot.ain.reuse.annotation.LanguageValidations;
import com.sap.iot.ain.reuse.annotation.Size;
import com.sap.iot.ain.validation.utils.Order;

/**
 * 
 * @author I322597
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDescription {

	@Size(min = 2, max = 2, field = "languageISoCode", groups = Order.Level1.class)
	@LanguageValidations(groups = Order.Level2.class)
	private String languageISoCode;
	
	@Size(max = 40, field = "shortDescription", groups = Order.Level1.class)
	private String shortDescription;
	
	@Size(max = 5000, field = "Long Description", groups = Order.Level1.class)
	private String longDescription;

	public String getLanguageISoCode() {
		return languageISoCode;
	}

	public void setLanguageISoCode(String languageISoCode) {
		this.languageISoCode = languageISoCode;
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

}
