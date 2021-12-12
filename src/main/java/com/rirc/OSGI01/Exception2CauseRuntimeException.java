package com.rirc.OSGI01;

public class Exception2CauseRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public Exception2CauseRuntimeException(Throwable ex) {
		initCause(ex);
	}
}
