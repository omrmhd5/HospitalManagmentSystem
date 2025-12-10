/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import gui.Login;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.AdminInterface;

/**
 *
 * @author omrmh
 */
public class LoginController {
    
    // We have reference to both the GUI and the rmi registry
    Login gui;
    Registry r;
    
    // The constructor takes the gui and the rmi registry as paramaters
    public LoginController(Login gui, Registry r)
    {
        this.gui = gui;
        this.r = r;
        // This registers the button with our action listener below (the inner class)
        gui.getContinueButton().addActionListener(new printBtnAction());
    }
    
    // This class is responsible for handling the button click
    class printBtnAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {

                // We try to obtain a remote reference to the grade remote object
                // that lives on the client. (using the registry object obtained from
                // the constructor above)
                AdminInterface admin = (AdminInterface) r.lookup("admin");
                admin.print();
               
            } catch (RemoteException | NotBoundException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
