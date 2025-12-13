package DesignPattern.State;

import model.ICURoom;

public interface ICUState {
    void handleRequest(ICURoom icu);
    String getStateName();
}
