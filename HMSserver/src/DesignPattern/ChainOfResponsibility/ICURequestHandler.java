package DesignPattern.ChainOfResponsibility;

import DesignPattern.Strategy.ICURequest;

// Interface for handling ICU requests
public interface ICURequestHandler {
    
    void setNext(ICURequestHandler handler);

    String handleRequest(ICURequest request);
}

