package Interface;

import FileManager.FileManager;
import FileManager.SignUp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RenameFileView extends DeleteFileView{
    JFrame renamefile = new JFrame("Rename File Menu");
    JTextField RenameFileOldPath;
    JTextField RenameFileNewPath;
    JButton renameFileButton;
    JButton clearButtonRenameFile;
    JButton returnButtonRenameFile;
    JPanel RenameFilePanel;
    JLabel RenameFileConfirm;

    /************************************************
     * Listener pentru clear
     ***********************************************/
    public RenameFileView() {
        clearButtonRenameFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renameclear();
            }
        });
    }
    /************************************************
     * Functia de clear
     ***********************************************/
    public void renameclear(){
        RenameFileOldPath.setText("");
        RenameFileNewPath.setText("");
        RenameFileConfirm.setText("");
    }
    /************************************************
     * Functia care deschide interfata de renameFile.
     ***********************************************/
    public void RenameFileMenu(){
        renamefile.setContentPane(RenameFilePanel);
        renamefile.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        renamefile.setLocationRelativeTo(null);
        renamefile.setResizable(true);
        renamefile.pack();
        renamefile.setVisible(true);
    }
}
