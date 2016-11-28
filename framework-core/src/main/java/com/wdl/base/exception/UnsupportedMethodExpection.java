package com.wdl.base.exception;

public class UnsupportedMethodExpection extends RuntimeException {
	private static final long serialVersionUID = -3443916787179710638L;
	public UnsupportedMethodExpection() {
        super();
    }
    public UnsupportedMethodExpection(String message) {
        super(message);
    }
    public UnsupportedMethodExpection(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedMethodExpection(Throwable cause) {
        super(cause);
    }
    protected UnsupportedMethodExpection(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
