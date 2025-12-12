package DesignPattern.ChainOfResponsibility;

import DesignPattern.Strategy.ICURequest;

public class ReceptionistHandler implements ICURequestHandler {
    
    private ICURequestHandler nextHandler;
    
    @Override
    public void setNext(ICURequestHandler handler) {
        this.nextHandler = handler;
    }
    
    @Override
    public String handleRequest(ICURequest request) {
        System.out.println("ReceptionistHandler: Processing ICU request ID " + request.getRequestID());
        
        // Receptionist can handle low and normal urgency requests
        String urgency = request.getUrgency();
        
        if (urgency != null && (urgency.equalsIgnoreCase("low") || 
                                urgency.equalsIgnoreCase("normal") )) {
            request.approve();
            return "Receptionist";  // Return handler name that processed it
        } else {
            // Pass to next handler (Admin) for high-priority or emergency requests
            if (nextHandler != null) {
                return nextHandler.handleRequest(request);
            } else {
                request.reject();
                return null;
            }
        }
    }
}

