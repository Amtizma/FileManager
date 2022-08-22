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
        // Functie care modifica parola in functie de modalitatea de conectare aleasa (DB sau fisier)
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

        // Functie de clear
        clearButtonChangePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changepasswordclear();
            }
        });
        // Functie care face parola vizibila sau nu
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

    // Functia pentru interfata
    public void changePasswordMenu(){
        changepassword.setContentPane(ChangePasswordPanel);
        changepassword.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        changepassword.setLocationRelativeTo(null);
        changepassword.setResizable(true);
        changepassword.pack();
        changepassword.setVisible(true);
    }
    // Clear
    public void changepasswordclear(){
        ChangePasswordUserIDText.setText("");
        ChangePasswordUserPWText.setText("");
        ChangePasswordText.setText("");
        ChangePasswordConfirmLabel.setText("");
    }
}
