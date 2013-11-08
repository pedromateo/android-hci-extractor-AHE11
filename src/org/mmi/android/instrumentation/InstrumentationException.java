package org.mmi.android.instrumentation;

/**
 * Exception class to notify instrumentation critical errors
 * @author mateo-navarro.pedro
 *
 */
public class InstrumentationException extends Exception {

	
	public InstrumentationException() {
		// TODO Auto-generated constructor stub
	}
	
	public InstrumentationException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}

	public InstrumentationException(Throwable throwable) {
		super(throwable);
		// TODO Auto-generated constructor stub
	}

	public InstrumentationException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}

}
