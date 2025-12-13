package DesignPattern.State;

import model.ICURoom;

public class OccupiedState implements ICUState {

    public OccupiedState() {
    }

    @Override
    public void handleRequest(ICURoom icu) {
        icu.setState(new ReleasedState());
    }

    @Override
    public String getStateName() {
        return "Occupied";
    }
}

