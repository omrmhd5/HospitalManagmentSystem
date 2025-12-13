package model;

import java.rmi.RemoteException;
import server.DB;

/**
 * Concrete implementation of User for RMI service
 * This is needed because User is abstract
 */
public class UserImpl extends User {
    
    // Mahmoud
    public UserImpl(DB db) throws RemoteException {
        super(db);
    }
}


