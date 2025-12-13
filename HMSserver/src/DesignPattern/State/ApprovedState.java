package DesignPattern.State;

import model.ICURoom;

public class ApprovedState implements ICUState {

    
    public ApprovedState() {
    }

    @Override
    public void handleRequest(ICURoom icu) {
        icu.setState(new OccupiedState());
    }

    @Override
    public String getStateName() {
        return "Approved";
    }
}
