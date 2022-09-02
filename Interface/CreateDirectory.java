package Interface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateDirectory extends ChangePasswordMenu {
    JFrame createdirectory = new JFrame("Create Directory Menu");
    private JPanel panel1;
    JButton createDirectroyButton;
    JButton returnButtonCreateDirectory;
    private JButton clearButton;
    JTextField directorynamelabel;
    JLabel CreateDirConfirm;

    /************************************************
     * Listener ce apeleaza functia de clear.
     ***********************************************/
    public CreateDirectory() {
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createdirclear();
            }
        });
    }
    /************************************************
     * Functia de clear
     ***********************************************/
    public void createdirclear(){
       directorynamelabel.setText("");
       CreateDirConfirm.setText("");
    }
    /************************************************
     * Functia care deschide interfata de createdirectory.
     ***********************************************/
    public void createdirectorymenu(){
        createdirectory.setContentPane(panel1);
        createdirectory.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createdirectory.setLocationRelativeTo(null);
        createdirectory.setResizable(true);
        createdirectory.pack();
        createdirectory.setVisible(true);
    }
}
