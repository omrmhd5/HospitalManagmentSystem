package StateDesignPattern;

import model.ICURoom;

public class PendingState implements ICUState {

    public PendingState() {
    }

    @Override
    public void handleRequest(ICURoom icu) {
        icu.setState(new ApprovedState());
    }

    @Override
    public String getStateName() {
        return "Pending";
    }
}

