package StateDesignPattern;

import model.ICURoom;

public interface ICUState {
    void handleRequest(ICURoom icu);
    String getStateName();
}
