package no.arktekk.training.spring.controller;

@SuppressWarnings("serial")
public class RestException extends RuntimeException {

    private int responseStatus;

    public RestException(int responseStatus, String message) {
        super(message);
        this.responseStatus = responseStatus;
    }

    public int getStatus() {
        return responseStatus;
    }
    
}