package com.mh.ui.validation.listener;

import com.mh.ui.validation.event.ValidationEvent;

public interface ValidationListener {
	public void onFailure(ValidationEvent vEvent);
	public void onSuccess(ValidationEvent vEvent);
}
