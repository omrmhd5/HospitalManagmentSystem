package DesignPattern.ChainOfResponsibility;

import DesignPattern.Strategy.ICURequest;


public class AdminHandler implements ICURequestHandler {
    
    private ICURequestHandler nextHandler;
    
    @Override
    public void setNext(ICURequestHandler handler) {
        this.nextHandler = handler;
    }

    @Override
    public String handleRequest(ICURequest request) {
        System.out.println("AdminHandler: Processing ICU request ID " + request.getRequestID());
        
        // Admin can approve high and emergency urgent requests
        String urgency = request.getUrgency();
        
        if (urgency != null && (urgency.equalsIgnoreCase("high") || 
                                urgency.equalsIgnoreCase("emergency")
                                )) {
            request.approve();
            return "Admin";  // Return handler name that processed it
        } else {
            // If there's a next handler, pass it along
            if (nextHandler != null) {
                return nextHandler.handleRequest(request);
            } else {
                // no next handler, admin has final authority
                request.approve();
                return "Admin";  // Admin processed it as final authority
            }
        }
    }
}

