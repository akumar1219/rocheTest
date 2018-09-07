package com.roche.test.excpetion;

public class MaxLimitIpException extends RuntimeException {

	private static final long serialVersionUID = 2015321231813376184L;

	public MaxLimitIpException(String message) {
		super(message);
	}
}
