package StateDesignPattern;

import model.ICURoom;

public class ReleasedState implements ICUState {

    public ReleasedState() {
    }

    @Override
    public void handleRequest(ICURoom icu) {
        icu.setState(new PendingState());
    }

    @Override
    public String getStateName() {
        return "Released";
    }
}

