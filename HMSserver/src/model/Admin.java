package model;

import java.rmi.RemoteException;
import rmi.AdminInterface;

public class Admin extends User implements AdminInterface {

    private int adminID;
    private String accessLevel;

    public Admin() throws RemoteException {}

    public Admin(int userID, String name, String email, String password,
                 int adminID, String accessLevel) throws RemoteException {
        super(userID, name, email, password, "Admin");
        this.adminID = adminID;
        this.accessLevel = accessLevel;
    }

    // ---------- UML METHODS ----------
    public void manageUser(User u) {
        System.out.println("Managing user: " + u.getName());
    }

    public Report generateReport(String type) {
        Report r = new Report(type);
        r.generateReport();
        return r;
    }

    public int getAdminID() { return adminID; }

    @Override
    public void print() throws RemoteException {
        System.out.println("Hello World");   
    }

}
