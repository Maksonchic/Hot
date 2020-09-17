package ru.probe.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class MessageModel<T> {
	
	public boolean ok = true;
	public T data;
	public final Map<String, String> errMsgs;
	
	public MessageModel() {
		this.errMsgs = new HashMap<>();
	}
	
	public MessageModel<T> sendData(T t) {
		this.data = t;
		return this;
	}
	
	public MessageModel<T> sendErrors(Map<String, String> errors) {
		this.ok = false;
		this.errMsgs.putAll(errors);
		return this;
	}
	
	public MessageModel<T> sendError(String errMessage) {
		return this.sendError("error", errMessage);
	}
	
	public MessageModel<T> sendError(String errType, String errMessage) {
		this.ok = false;
		this.errMsgs.put(errType, errMessage);
		return this;
	}
}

