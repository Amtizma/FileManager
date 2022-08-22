package Interface;

import FileManager.EventLogger;
import FileManager.SignUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

public class Authentification extends MainMenu{
    JFrame authframe = new JFrame("Authentification");
    private JPanel AuthPanel;
    private JLabel WelcomeMessage;
    private JPanel WelcomePanel;
    private JPanel UserDataPanel;
    private JPanel AuthButtonsPanel;
    JFormattedTextField UserIDField;
    JPasswordField passwordField;
    private JLabel ID;
    private JLabel Password;
    JButton LogInButton;
    private JButton ClearButton;
    private JButton SignUpButton;
    private JLabel AuthConfirmation;
    private JButton changePasswordButton;
    private JCheckBox dataBaseCheckBox;
    private JCheckBox dataBaseOrTXTFileCheckBox;
    private JCheckBox showPasswordLogInCheckBox;

    public Authentification() {
        // Listener care deschide meniul de SignUp
        SignUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SignUpMenu();
                authframe.setVisible(false);
            }
        });
        // Listener pentru butonul de LogIn
        LogInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = UserIDField.getText();
                String password = passwordField.getText();
                if(dataform) as.logInDB(id, password, AuthConfirmation);
                if(!dataform) as.logIn(id, password, AuthConfirmation);
                if(AuthConfirmation.getText().equals("Authentification succesfull. ")) {
                    authframe.setVisible(false);
                    Menu();
                    id1 = id;
                    try {
                        evlog.eventLogger(id1, "logged in. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // Listener pentru butonul de return
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signupframe.dispose();
                authframe.setVisible(true);
                IDField.setText("");
                PasswordField.setText("");
                Confirmation.setText("");
            }
        });
        // listener pentru butonul de return din interfata de schimbare a parolei
        returnButtonChangePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changepassword.dispose();
                authframe.setVisible(true);
                changepasswordclear();
            }
        });
        // functie de clear
        ClearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserIDField.setText("");
                passwordField.setText("");
            }
        });
        // listener pentru butonul de logout
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainmenuframe.dispose();
                Auth();
                UserIDField.setText("");
                passwordField.setText("");
                AuthConfirmation.setText("");
                try {
                    evlog.eventLogger(id1, "logged out. ");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // listener care deschide interfata de schimbare a parolei
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePasswordMenu();
                authframe.setVisible(false);
            }
        });

        // listener care retine in dataform tipul de metoda selectat (fisier sau baza de date)
        dataBaseOrTXTFileCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataform = dataBaseOrTXTFileCheckBox.isSelected();
            }
        });
        // listener pentru butonul care face parola vizibila sau nu)
        showPasswordLogInCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showPasswordLogInCheckBox.isSelected()) passwordField.setEchoChar((char)0);
                else passwordField.setEchoChar('*');
            }
        });
    }
    // Functie pentru interfata
    public void Auth(){
        authframe.setContentPane(AuthPanel);
        authframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        authframe.setLocationRelativeTo(null);
        authframe.setResizable(true);
        authframe.pack();
        authframe.setVisible(true);
    }
}
