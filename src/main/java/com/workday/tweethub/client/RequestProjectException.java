package com.workday.tweethub.client;

public class RequestProjectException extends Exception{
	
	private static final long serialVersionUID = -5128698032477986583L;

	public RequestProjectException(String message, Throwable e){
		super(message, e);
	}
	
}
