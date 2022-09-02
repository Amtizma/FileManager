package Interface;

import FileManager.FileManager;
import FileManager.SignUp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpView{
    SignUp as = new SignUp();
    public FileManager ap = new FileManager();
    JFrame signupframe = new JFrame("Sign Up");
    private JPanel SignUpPanel;
    JFormattedTextField IDField;
    JPasswordField PasswordField;
    private JLabel ID;
    private JLabel Password;
    private JButton SignUpButton;
    JButton ClearButton;
    JButton returnButton;
    JLabel Confirmation;
    private JCheckBox ShowPasswordSignUp;
    boolean dataform = false;
    public SignUpView() {
        /************************************************
         * Listener pentru butonul de signUp. Preia ID-ul si parola din interfata
         * si apeleaza functiile de signUp din clasa SignUp.
         * Dataforma reprezinta alegerea user-ului intre DB si fisier.
         ***********************************************/
        SignUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = IDField.getText();
                String password = PasswordField.getText();
                if(dataform) as.signUpDB(id, password, Confirmation);
                if(!dataform) as.signUp(id, password, Confirmation);
            }
        });
        /************************************************
         * Listener pentru functia de clear.
         ***********************************************/
        ClearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IDField.setText("");
                PasswordField.setText("");
                Confirmation.setText("");
            }
        });
        /************************************************
         * Listener pentru butonul care face parola vizibila sau nu.
         ***********************************************/
        ShowPasswordSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ShowPasswordSignUp.isSelected()) PasswordField.setEchoChar((char)0);
                else PasswordField.setEchoChar('*');
            }
        });
    }
    /************************************************
     * Functia care deschide interfata de signUp.
     ***********************************************/
    public void SignUpMenu(){
        signupframe.setContentPane(SignUpPanel);
        signupframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signupframe.setLocationRelativeTo(null);
        signupframe.setResizable(true);
        signupframe.pack();
        signupframe.setVisible(true);
    }
}
