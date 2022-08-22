package Interface;

import FileManager.FileManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DeleteFileView extends DeletePermissionsView{
    JFrame deletefile = new JFrame("Delete File Menu");
    JTextField FilePathField;
    JButton deleteFileButton;
    JButton clearButtonDelete;
    JButton returnButtonDelete;
    JLabel FilePathLabel;
    JLabel DeleteConfirmLabel;
    JPanel DeleteFile;

    //Listener pentru clear
    public DeleteFileView() {
        clearButtonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilePathField.setText("");
                DeleteConfirmLabel.setText("");
            }
        });
    }

    // Functia pentru interfata
    public void DeleteFileMenu(){
        deletefile.setContentPane(DeleteFile);
        deletefile.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deletefile.setLocationRelativeTo(null);
        deletefile.setResizable(true);
        deletefile.pack();
        deletefile.setVisible(true);
    }
}
