package client;

import controllers.loginRegister.RoleSelectController;
import gui.loginRegister.RoleSelect;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        
       
        Registry registry = LocateRegistry.getRegistry(1099);
        
     
        RoleSelect roleSelect = new RoleSelect();
        RoleSelectController roleSelectController = new RoleSelectController(roleSelect, registry);
    }
}

    