package DesignPattern.ChainOfResponsibility;

import DesignPattern.Strategy.ICURequest;

public class ICURequestChain {
    
    private ICURequestHandler firstHandler;
    
    /**
     * Constructs ReceptionistHandler as the first handler
     * and AdminHandler as the second handler.
     */
    public ICURequestChain() {
        // Create handlers
        ReceptionistHandler receptionistHandler = new ReceptionistHandler();
        AdminHandler adminHandler = new AdminHandler();
        
        receptionistHandler.setNext(adminHandler);
        
        this.firstHandler = receptionistHandler;
    }
    

    public String processRequest(ICURequest request) {
        return firstHandler.handleRequest(request);
    }
}

