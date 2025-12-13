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
        
        // Admin can approve any urgency type (has final authority)
        request.approve();
        return "Admin";  // Return handler name that processed it
    }
}

