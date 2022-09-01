package com.dinsaren.bbuappserver.payload.response;

public class MessageRes {
	private String message;
	private Object data;

	public MessageRes() {
	}

	public MessageRes(String message, Object data) {
	    this.message = message;
	    this.data = data;
	  }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
