package client;

import controllers.LoginController;
import controllers.ViewProfileController;
import controllers.EditProfileController;
import controllers.RequestLabTestController;
import controllers.ManageUsersController;
import gui.Login;
import gui.ViewProfile;
import gui.EditProfile;
import gui.RequestLabTest;
import gui.ManageUsers;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        
        // We connect to the RMI Registry
        Registry registry = LocateRegistry.getRegistry(1099);
        
        // Create Login GUI and its controller
        Login l = new Login();
        LoginController lc = new LoginController(l, registry);
        
        // Create ViewProfile GUI and its controller
//        ViewProfile vp = new ViewProfile();
//        ViewProfileController vpc = new ViewProfileController(vp, registry);
        
        // Create EditProfile GUI and its controller
//        EditProfile ep = new EditProfile();
//        EditProfileController epc = new EditProfileController(ep, registry);
        
        // Create RequestLabTest GUI and its controller
//        RequestLabTest rlt = new RequestLabTest();
//        RequestLabTestController rltc = new RequestLabTestController(rlt, registry);
        
        // Create ManageUsers GUI and its controller
//        ManageUsers mu = new ManageUsers();
//        ManageUsersController muc = new ManageUsersController(mu, registry);
        
        // Our remote object g is binded to the name "grade"
//        AdminInterface admin = (AdminInterface) registry.lookup("admin");
//        admin.print();
    }
}
