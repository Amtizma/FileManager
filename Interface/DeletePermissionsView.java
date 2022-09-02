package Interface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class DeletePermissionsView extends AddPermissionsView{
    JFrame deletepermissions = new JFrame("Delete Permissions Menu");
    private JPanel DeletePermissionsPanel;
    private JLabel PermissionsUserIDLabel;
    private JTextField PermissionsUserIDText;
    private JLabel PermissionsUserPWLabel;
    private JTextField PermissionsUserPWText;
    private JLabel PermissionsLabel;
    private JTextField PermissionsText;
    private JLabel PermissionsConfirmLabel;
    private JButton clearButtonPermissions;
    JButton returnButtonDeletePermissions;
    private JButton deletePermissionsButton;

    public DeletePermissionsView() {
        /************************************************
         * Listener pentru butonul de delete permissions. Acesta preia ID-ul, parola si permisinuile care doresc sa fie
         * sterse din interfata. Permissionvalue are valoarea false pentru a sterge.
         * Dataform este preferinta user-ului intre DB sau fisier.
         ***********************************************/
        deletePermissionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(dataform) as.changePermissionsDB(PermissionsUserIDText.getText(), PermissionsUserPWText.getText(), PermissionsText.getText(), PermissionsConfirmLabel, false);
                    if(!dataform) as.changePermissions(PermissionsUserIDText.getText(), PermissionsUserPWText.getText(), PermissionsText.getText(), PermissionsConfirmLabel, false);
                } catch (IOException | NoSuchAlgorithmException | SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        /************************************************
         * Listener pentru functia de clear.
         ***********************************************/
        clearButtonPermissions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                permissiondeleteclear();
            }
        });
    }
    /************************************************
     * Functia care deschide interfata.
     ***********************************************/
    public void DeletePermissionsMenu(){
        deletepermissions.setContentPane(DeletePermissionsPanel);
        deletepermissions.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deletepermissions.setLocationRelativeTo(null);
        deletepermissions.setResizable(true);
        deletepermissions.pack();
        deletepermissions.setVisible(true);
    }
    /************************************************
     * Functia de clear.
     ***********************************************/
    public void permissiondeleteclear(){
        PermissionsUserIDText.setText("");
        PermissionsUserPWText.setText("");
        PermissionsText.setText("");
        PermissionsConfirmLabel.setText("");
    }
}
