package Interface;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class AddPermissionsView extends RemoveUserView {
    JFrame addpermissions = new JFrame("Add Permissions Menu");
    boolean permissions = false;
    private JTextField PermissionsUserIDText;
    private JTextField PermissionsUserPWText;
    private JTextField PermissionsText;
    private JButton clearButtonPermissions;
    JButton returnButtonAddPermissions;
    private JLabel PermissionsUserIDLabel;
    private JLabel PermissionsUserPWLabel;
    private JLabel PermissionsLabel;
    private JLabel PermissionsConfirmLabel;
    private JPanel AddPermissionsPanel;
    private JButton addPermissionsButton;
    public AddPermissionsView() {

        // Listener pentru butonul de addPermission. dataform retine daca user-ul a selectat fisiser sau baza de date.
        addPermissionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(dataform) as.changePermissionsDB(PermissionsUserIDText.getText(), PermissionsUserPWText.getText(), PermissionsText.getText(), PermissionsConfirmLabel, true);
                    if(!dataform) as.changePermissions(PermissionsUserIDText.getText(), PermissionsUserPWText.getText(), PermissionsText.getText(), PermissionsConfirmLabel, true);
                } catch (IOException | NoSuchAlgorithmException | SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Functie de clear
        clearButtonPermissions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                permissionaddclear();
            }
        });
    }
    // Meniul pentru addPermissions
    public void AddPermissionsMenu(){
        addpermissions.setContentPane(AddPermissionsPanel);
        addpermissions.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addpermissions.setLocationRelativeTo(null);
        addpermissions.setResizable(true);
        addpermissions.pack();
        addpermissions.setVisible(true);
    }
    // Functie de clear
    public void permissionaddclear(){
        PermissionsUserIDText.setText("");
        PermissionsUserPWText.setText("");
        PermissionsText.setText("");
        PermissionsConfirmLabel.setText("");
    }
}

