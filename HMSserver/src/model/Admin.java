package model;

import java.io.Serializable;

public class Admin extends User implements Serializable {

    private int adminID;
    private String accessLevel;

    public Admin() {}

    public Admin(int userID, String name, String email, String password,
                 int adminID, String accessLevel) {
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
}
