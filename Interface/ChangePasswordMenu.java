package Interface;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class ChangePasswordMenu extends ErrorBox {
    JFrame changepassword = new JFrame("Change Password Menu");
    private JPanel ChangePasswordPanel;
    private JLabel ChangePasswordUserIDLabel;
    private JTextField ChangePasswordUserIDText;
    private JLabel ChangePasswordUserPWLabel;
    private JPasswordField ChangePasswordUserPWText;
    private JLabel ChangePasswordLabel;
    private JPasswordField ChangePasswordText;
    private JLabel ChangePasswordConfirmLabel;
    JButton returnButtonChangePassword;
    private JButton clearButtonChangePassword;
    private JButton ChangePasswordButton;
    private JCheckBox ShowPasswordChangePW;

    public ChangePasswordMenu() {
        /************************************************
         * Listener care apeleaza functiile de schimbare a parolei, in functie de
         * ce metoda de conectare a ales user-ul.
         ***********************************************/
        ChangePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(dataform) as.changePasswordDB(ChangePasswordUserIDText.getText(), ChangePasswordUserPWText.getText(), ChangePasswordText.getText(), ChangePasswordConfirmLabel);
                    if(!dataform) as.changePassword(ChangePasswordUserIDText.getText(), ChangePasswordUserPWText.getText(), ChangePasswordText.getText(), ChangePasswordConfirmLabel);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        /************************************************
         * Listener ce apeleaza functia de clear.
         ***********************************************/
        clearButtonChangePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changepasswordclear();
            }
        });
        /************************************************
         * Functie care face parola vizibila sau nu.
         ***********************************************/
        ShowPasswordChangePW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ShowPasswordChangePW.isSelected()) {
                    ChangePasswordUserPWText.setEchoChar((char)0);
                    ChangePasswordText.setEchoChar((char)0);
                }
                else {
                    ChangePasswordUserPWText.setEchoChar('*');
                    ChangePasswordText.setEchoChar('*');
                }
            }
        });
    }

    /************************************************
     * Functia care deschide interfata de schimbare a parolei.
     ***********************************************/
    public void changePasswordMenu(){
        changepassword.setContentPane(ChangePasswordPanel);
        changepassword.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        changepassword.setLocationRelativeTo(null);
        changepassword.setResizable(true);
        changepassword.pack();
        changepassword.setVisible(true);
    }
    /************************************************
     * Functie de clear
     ***********************************************/
    public void changepasswordclear(){
        ChangePasswordUserIDText.setText("");
        ChangePasswordUserPWText.setText("");
        ChangePasswordText.setText("");
        ChangePasswordConfirmLabel.setText("");
    }
}
