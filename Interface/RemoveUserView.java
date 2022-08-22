package Interface;

import FileManager.MD5;
import FileManager.SignUp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RemoveUserView extends SignUpView{
    JFrame removeuser = new JFrame("Remove User Menu");
    JTextField UsersIDTextField;
    JTextField UsersPWTextField;
    JButton removeUserButton;
    JButton clearButtonUser;
    JButton returnButtonUser;
    JLabel ConfirmUserDelete;
    JPanel RemoveUserPanel;
    // Listener pentru clear
    public RemoveUserView() {
        clearButtonUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               clear();
            }
        });
    }
    //Functia de clear
    public void clear(){
        UsersIDTextField.setText("");
        UsersPWTextField.setText("");
        ConfirmUserDelete.setText("");
    }
    // Functia de interfata
    public void RemoveUserMenu(){
        removeuser.setContentPane(RemoveUserPanel);
        removeuser.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        removeuser.setLocationRelativeTo(null);
        removeuser.setResizable(true);
        removeuser.pack();
        removeuser.setVisible(true);
    }
}
