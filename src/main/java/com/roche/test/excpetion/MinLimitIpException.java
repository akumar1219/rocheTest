package com.roche.test.excpetion;

public class MinLimitIpException extends RuntimeException {

	private static final long serialVersionUID = 1058424956029675542L;

	public MinLimitIpException(String message) {
		super(message);
	}
}
