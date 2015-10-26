package com.ideas.webportal.exception;

import java.io.PrintWriter;

import java.io.StringWriter;

import com.ideas.webportal.model.domain.FlightBookingProperties;


public class ApplicationException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	

    private String code=null;
    private Object[] messageParams;
    private String stackTrace=null;
    private String message=null;

    public ApplicationException(){
    	super();
    }
    
    public ApplicationException(String code){
        super(FlightBookingProperties.getProperty(code));
        this.message=FlightBookingProperties.getProperty(code);
        this.code = code;
    }
    
    public ApplicationException(Throwable cause) {
        super(cause);
    }
    
    /**
     * @param code -
     *            The error code which represents the business failure.
     * @param params .
     * @param messages -
     *            container for a collection of business failure messages.
     */
    public ApplicationException(String code, Exception e) {
        super(FlightBookingProperties.getProperty(code));
        message=FlightBookingProperties.getProperty(code);
        this.code = code;
        this.stackTrace=getStackTrace(e);
    }
    
    /**
     * 
     * @param ex
     * @return
     */
    private String getStackTrace(Throwable ex) {
        if (ex == null) {
            return null;
        }
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        String stacktrace = sw.toString();
        return stacktrace;

    }


    /**
     * @param errorCode .
     * @param errorMessage .
     * @param hint .
     * @param shortText .
     * @param msgParams .
     */
    public ApplicationException(String errorCode, String errorMessage, String hint, String shortText, Object[] msgParams) {
        super(errorMessage);
        this.code = errorCode;
    }

    /**
     * @return - code .
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the messageParams
     */
    public Object[] getMessageParams() {
        return messageParams;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return message;
	}

	@Override
	public String getMessage(){
		return message;
	}

}
