package com.sap.iot.ain.notification.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sap.iot.ain.reuse.annotation.NotBlank;
import com.sap.iot.ain.reuse.annotation.NotNull;
import com.sap.iot.ain.reuse.annotation.ListNotNull;
import com.sap.iot.ain.validation.utils.Order;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationPOSTForAlert {

	@NotBlank(field = "Mode", groups = Order.Level1.class)
	@NotNull(objectName = "Mode", groups = Order.Level1.class)
	private String mode;

	@ListNotNull(field = "Alerts", groups = Order.Level1.class)
	private List<String> alerts;

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<String> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<String> alerts) {
		this.alerts = alerts;
	}



}
