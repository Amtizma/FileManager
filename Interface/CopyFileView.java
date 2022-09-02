package Interface;

import FileManager.FileManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class CopyFileView extends RenameFileView{
    JFrame copyfile = new JFrame("Copy File Menu");
    JPanel CopyFilePanel;
    JTextField CopyFileOldPath;
    JTextField CopyFileNewPath;
    JButton copyFileButton;
    JLabel CopyFileConfirm;
    JButton clearButtonCopyFile;
    JButton returnButtonCopyFile;
    public CopyFileView() {
        /************************************************
         * Listener ce apeleaza functia de clear
         ***********************************************/
        clearButtonCopyFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyclear();
            }
        });
    }

    /************************************************
     * Functia de clear
     ***********************************************/
    public void copyclear(){
        CopyFileOldPath.setText("");
        CopyFileNewPath.setText("");
        CopyFileConfirm.setText("");
    }
    /************************************************
     * Functia care deschide interfata de copyFile.
     ***********************************************/
    public void CopyFileMenu(){
        copyfile.setContentPane(CopyFilePanel);
        copyfile.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        copyfile.setLocationRelativeTo(null);
        copyfile.setResizable(true);
        copyfile.pack();
        copyfile.setVisible(true);
    }
}
