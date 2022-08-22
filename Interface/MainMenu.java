package Interface;

import FileManager.EventLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;


public class MainMenu extends ChangePasswordMenu {
    JFrame mainmenuframe = new JFrame("Main Menu");
    private JPanel MainMenuPanel;
    private JComboBox<String> ComboBox;
    JTable table1;
    JButton logOutButton;
    JButton HomeButton;
    private JMenu ShowMenu;
    private JMenuBar MenuBar;
    private JMenu SortMenu;
    private JMenu FileMenu;
    private JMenuItem ShowDocumentsMenu;
    private JMenuItem ShowImagesMenu;
    private JMenuItem ShowVideosMenu;
    private JMenuItem ShowExecutablesMenu;
    private JMenuItem ShowOthersMenu;
    private JMenuItem SortByNameAscendingMenu;
    private JMenuItem SortByNameDescendingMenu;
    private JMenuItem SortBySizeDescendingMenu;
    private JMenuItem SortBySizeAscendingMenu;
    private JMenuItem SortByTypeAscendingMenu;
    private JMenuItem SortByTypeDescendingMenu;
    private JMenuItem DeleteFileMenu;
    private JMenuItem CopyFileMenu;
    private JMenuItem RenameFileMenu;
    private JMenuItem OpenFile = new JMenuItem("Open File");
    private JMenu PermissionsUserMenu;
    private JMenuItem AddPermissionsMenu;
    private JMenuItem DeletePermissionsMenu;
    private JMenuItem RemoveUserMenu;
    private JMenuItem ShowUsersMenu;
    private JMenuItem ShowFilesMenu;
    private JButton backButton;
    private JButton changeDirectoryButton;
    private JTextField directoryPath;
    private JButton previousFolderButton;
    JPopupMenu jpm = new JPopupMenu();
    String previous;
    String previousFolder = "C:\\Users\\Bebe\\Downloads";
    String fileToOpen;
    String id1;
    Desktop desktop = Desktop.getDesktop();
    int clicked = 0;
    String filepath = "C:\\Users\\Bebe\\Downloads";
    EventLogger evlog = new EventLogger();
    public MainMenu() {
        directoryPath.setText(filepath);

        //Listener pentru return din interfata de removeuser
        returnButtonUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeuser.dispose();
                mainmenuframe.setVisible(true);
                clear();
            }
        });
        //Listener pentru return din interfata de addpermisison
        returnButtonAddPermissions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addpermissions.dispose();
                mainmenuframe.setVisible(true);
                permissionaddclear();
            }
        });
        //Listener pentru return din interfata de deletepermission
        returnButtonDeletePermissions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletepermissions.dispose();
                mainmenuframe.setVisible(true);
                permissiondeleteclear();
            }
        });
        //Listener pentru return din interfata de delete
        returnButtonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletefile.dispose();
                mainmenuframe.setVisible(true);
                FilePathField.setText("");
                DeleteConfirmLabel.setText("");
            }
        });
        //Listener pentru return din interfata de rename
        returnButtonRenameFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renamefile.dispose();
                mainmenuframe.setVisible(true);
                renameclear();
            }
        });
        //Listener pentru return din interfata de copy
        returnButtonCopyFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyfile.dispose();
                mainmenuframe.setVisible(true);
                copyclear();
            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!(e.getButton() == MouseEvent.BUTTON3)) {
                    // Daca interfetele sunt deschise, copiaza automat path-ul in casuta specifica.
                    if (renamefile.isShowing()) RenameFileOldPath.setText(ap.getPath(table1));
                    if (deletefile.isShowing()) FilePathField.setText(ap.getPath(table1));
                    if (copyfile.isShowing()) CopyFileOldPath.setText(ap.getPath(table1));
                    int j = table1.getSelectedRow();
                    String directory1 = table1.getValueAt(j, 3).toString();
                    // Pentru click-dreapta
                    FileMenu.add(DeleteFileMenu);
                    FileMenu.add(RenameFileMenu);
                    FileMenu.add(CopyFileMenu);
                    // Functia care deschide fisierele
                    if (directory1.equals("directory")) {
                        try {
                            filepath = ap.getPath(table1);
                            ap.showfiles.setFolder(new File(ap.getPath(table1)));
                            ap.scanFolder();
                            ap.show(table1);
                            previousFolder = directoryPath.getText();
                            directoryPath.setText(filepath);

                        } catch (Exception er) {
                            ErrorBox();
                            ap.showfiles.setFolder(new File("C:/Users/Bebe/Downloads"));
                            ap.getPath(table1);
                            ap.scanFolder();
                            ap.show(table1);
                            try {
                                ap.eventlog.eventLogger(id1, "has tried to acess " + ap.getPath(table1) + " which is private.");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
                // Functia pentru click-dreapta
                if (e.getButton() == MouseEvent.BUTTON3){
                    Point point = e.getPoint();
                    int index = table1.rowAtPoint(point);
                    fileToOpen = ap.getPathRightClick(table1, index);
                    jpm.add(DeleteFileMenu);jpm.add(RenameFileMenu);jpm.add(CopyFileMenu);jpm.add(OpenFile);
                    jpm.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        // Functia care permite sortarea apasand pe header-ul tabelului
        table1.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                int index = table1.columnAtPoint(point);
                clicked++;
                if (index == 1) {
                    if(clicked%2 == 1){
                        ap.listaSortataCrescatorByName(table1);
                        table1.getColumnModel().getColumn(1).setHeaderValue("Name ↑");
                        previous = "SortByNameAscending";
                        try {
                            evlog.eventLogger(id1, "sorted all files by name ascending. ");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if(clicked%2 == 0){
                        ap.listaSortataDescrescatorByName(table1);
                        table1.getColumnModel().getColumn(1).setHeaderValue("Name ↓");
                        previous = "SortByNameDescending";
                        try {
                            evlog.eventLogger(id1, "sorted all files by name descending. ");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                if (index == 2) {
                    if(clicked%2 == 1){
                        ap.listaSortataCrescatorBySize(table1);
                        table1.getColumnModel().getColumn(2).setHeaderValue("Size ↑");
                        previous = "SortBySizeAscending";
                        try {
                            evlog.eventLogger(id1, "sorted all files by size ascending. ");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if(clicked%2 == 0){
                        ap.listaSortataDescrescatorBySize(table1);
                        table1.getColumnModel().getColumn(2).setHeaderValue("Size ↓");
                        previous = "SortBySizeDescending";
                        try {
                            evlog.eventLogger(id1, "sorted all files by size descending. ");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                if (index == 3) {
                    if(clicked%2 == 1){
                        ap.listaSortataCrescatorByType(table1);
                        table1.getColumnModel().getColumn(3).setHeaderValue("Type ↑");
                        previous = "SortByTypeAscending";
                        try {
                            evlog.eventLogger(id1, "sorted all files by type ascending. ");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if(clicked%2 == 0){
                        ap.listaSortataDescrescatorByType(table1);
                        table1.getColumnModel().getColumn(3).setHeaderValue("Type ↓");
                        previous = "SortByTypeDescending";
                        try {
                            evlog.eventLogger(id1, "sorted all files by type descending. ");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            };
        });
        // Listener pentru butonul de rename
        renameFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ap.renameFile(RenameFileOldPath.getText(), RenameFileNewPath.getText(), RenameFileConfirm);
                if(previous != null) operation();
                try {
                    evlog.eventLogger(id1, "renamed " + RenameFileOldPath.getText() + " to " + RenameFileNewPath.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // Listener pentru butonul de deleteFile
        deleteFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ap.deleteFile(new File(FilePathField.getText()), DeleteConfirmLabel);
                if(previous != null) operation();
                try {
                    evlog.eventLogger(id1, "deleted " + FilePathField.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // Listener pentru butonul de copy
        copyFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ap.copyFile(new File(CopyFileOldPath.getText()), new File(CopyFileNewPath.getText()), CopyFileConfirm);
                    try {
                        evlog.eventLogger(id1, "copied " + CopyFileOldPath.toString() + " to " + CopyFileNewPath.toString());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    if(previous != null) operation();
                }
            }
        });
        // Listener pentru butonul de removeUser
        removeUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = UsersIDTextField.getText();
                String pw = UsersPWTextField.getText();
                try {
                    if(dataform) as.removeUserDB(id, pw, ConfirmUserDelete);
                    if(!dataform) as.removeUser(id, pw, ConfirmUserDelete);
                    if(previous != null) operation();
                    try {
                        evlog.eventLogger(id1, "removed user " + id);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        // Listener pentru butonul de home
        HomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filepath = "C:\\Users\\Bebe\\Downloads";
                ap.showfiles.setFolder(new File("C:/Users/Bebe/Downloads"));
                ap.scanFolder();
                ap.show(table1);
                previousFolder = directoryPath.getText();
                directoryPath.setText(filepath);
            }
        });
        // Listener pentru butonul de showfiles
        ShowFilesMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    previous = "ShowFiles";
                    ap.show(table1);
                    try {
                        evlog.eventLogger(id1, "showed all documents. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
            }
        });
        // Listener pentru butonul de showdocuments
        ShowDocumentsMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (as.verifyPermissions("viewdoc")) {
                    ap.showDocuments(table1);
                    previous = "ShowDocuments";
                    try {
                        evlog.eventLogger(id1, "showed all documents. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    ErrorBox();
                    try {
                        evlog.eventLogger(id1, "tried to show all documents without permission. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // Listener pentru butonul de showimages
        ShowImagesMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (as.verifyPermissions("viewimg")) {
                    previous = "ShowImages";
                    ap.showImages(table1);
                    try {
                        evlog.eventLogger(id1, "showed all images. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    ErrorBox();
                    try {
                        evlog.eventLogger(id1, "tried to show all images without permission. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // Listener pentru butonul de showvideos
        ShowVideosMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (as.verifyPermissions("viewvid")) {
                    previous = "ShowVideos";
                    ap.showVideos(table1);
                    try {
                        evlog.eventLogger(id1, "showed all videos. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    ErrorBox();
                    try {
                        evlog.eventLogger(id1, "tried to show all videos without permission. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // Listener pentru butonul de showexe
        ShowExecutablesMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (as.verifyPermissions("viewexe")) {
                    previous = "ShowExecutables";
                    ap.showExecutables(table1);
                    try {
                        evlog.eventLogger(id1, "showed all executables. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    ErrorBox();
                    try {
                        evlog.eventLogger(id1, "tried to show all executables without permission. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // Listener pentru butonul de showothers
        ShowOthersMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (as.verifyPermissions("viewoth")) {
                    previous = "ShowOthers";
                    ap.showOthers(table1);
                    try {
                        evlog.eventLogger(id1, "showed all others. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    ErrorBox();
                    try {
                        evlog.eventLogger(id1, "tried to show all others without permission. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // Listener pentru butonul de sortare crescatoare dupa nume
        SortByNameAscendingMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (as.verifyPermissions("sortname")) {
                    previous = "SortByNameAscending";
                    ap.listaSortataCrescatorByName(table1);
                    try {
                        evlog.eventLogger(id1, "sorted all files by name ascending. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    ErrorBox();
                    try {
                        evlog.eventLogger(id1, "tried to sort all files by name ascending without permission. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // Listener pentru butonul de sortare descrescatoare dupa nume
        SortByNameDescendingMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (as.verifyPermissions("sortname")) {
                    previous = "SortByNameDescending";
                    ap.listaSortataDescrescatorByName(table1);
                    try {
                        evlog.eventLogger(id1, "sorted all files by name descending. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    ErrorBox();
                    try {
                        evlog.eventLogger(id1, "tried to sort all files by name descending without permission. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // Listener pentru butonul de sortare crescatoare dupa size
        SortBySizeAscendingMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (as.verifyPermissions("sortsize")) {
                    previous = "SortBySizeAscending";
                    ap.listaSortataCrescatorBySize(table1);
                    try {
                        evlog.eventLogger(id1, "sorted all files by size ascending. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    ErrorBox();
                    try {
                        evlog.eventLogger(id1, "tried to sort all files by size ascending without permission. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // Listener pentru butonul de sortare descrescatoare dupa size
        SortBySizeDescendingMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (as.verifyPermissions("sortsize")) {
                    previous = "SortBySizeDescending";
                    ap.listaSortataDescrescatorBySize(table1);
                    try {
                        evlog.eventLogger(id1, "sorted all files by size descending. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    ErrorBox();
                    try {
                        evlog.eventLogger(id1, "tried to sort all files by size descending without permission. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // Listener pentru butonul de sortare crescatoare dupa type
        SortByTypeAscendingMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (as.verifyPermissions("sorttype")) {
                    previous = "SortByTypeAscending";
                    ap.listaSortataCrescatorByType(table1);
                    try {
                        evlog.eventLogger(id1, "sorted all files by type descending. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    ErrorBox();
                    try {
                        evlog.eventLogger(id1, "tried to sort all files by type ascending without permission. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // Listener pentru butonul de sortare descrescatoare dupa type
       SortByTypeDescendingMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (as.verifyPermissions("sorttype")) {
                    previous = "SortByTypeDescending";
                    ap.listaSortataDescrescatorByType(table1);
                    try {
                        evlog.eventLogger(id1, "sorted all files by type descending. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    ErrorBox();
                    try {
                        evlog.eventLogger(id1, "tried to sort all files by type descending without permission. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
       // Listener care deschide interfata de deletefile
        DeleteFileMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (as.verifyPermissions("delete")) {
                    DeleteFileMenu();
                } else {
                    ErrorBox();
                    try {
                        evlog.eventLogger(id1, "tried to delete a file without permission. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // Listener care deschide interfata de copy
        CopyFileMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (as.verifyPermissions("copy")) {
                    CopyFileMenu();
                } else {
                    ErrorBox();
                    try {
                        evlog.eventLogger(id1, "tried to copy a file without permission. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // Listener pentru OpenFile din click dreapta
        OpenFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    desktop.open(new File(fileToOpen));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        // Listener care deschide interfata de renamefile
        RenameFileMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (as.verifyPermissions("rename")) {
                    RenameFileMenu();
                } else {
                    ErrorBox();
                    try {
                        evlog.eventLogger(id1, "tried to rename a file without permission. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // Listener care deschide interfata de addpermission
        AddPermissionsMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (as.verifyPermissions("changepermission")) {
                    AddPermissionsMenu();
                } else {
                    ErrorBox();
                    try {
                        evlog.eventLogger(id1, "tried to change permissions without permission. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // Listener care deschide interfata de deletepermission
        DeletePermissionsMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (as.verifyPermissions("changepermission")) {
                    DeletePermissionsMenu();
                } else {
                    ErrorBox();
                    try {
                        evlog.eventLogger(id1, "tried to change permissions without permission. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // Listener care deschide arata userii in tabel
        ShowUsersMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (as.verifyPermissions("viewusers")) {
                    if(dataform) as.showUsersDB(table1);
                    if(!dataform) as.showUsers(table1);
                    try {
                        evlog.eventLogger(id1, "showed all users. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    ErrorBox();
                    try {
                        evlog.eventLogger(id1, "tried to show all users without permission. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // Listener care deschide interfata de removeuser
        RemoveUserMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (as.verifyPermissions("removeuser")) {
                    RemoveUserMenu();
                } else {
                    ErrorBox();
                    try {
                        evlog.eventLogger(id1, "tried to remove an user without permission. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        // Meniul pentru click dreapta
        FileMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FileMenu.add(DeleteFileMenu);FileMenu.add(RenameFileMenu);FileMenu.add(CopyFileMenu);
            }
        });
        // Listener pentru butonul de back
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filepath = filepath.substring(0, filepath.lastIndexOf("\\")) + "\\";
                ap.showfiles.setFolder(new File(filepath));
                ap.scanFolder();
                ap.show(table1);
                previousFolder = directoryPath.getText();
                directoryPath.setText(filepath);
                if(filepath.endsWith("\\")) filepath = filepath.substring(0, filepath.length()-1);
            }
        });
        // Listener pentru butonul de change directory
        changeDirectoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    ap.showfiles.setFolder(new File(directoryPath.getText()));
                    ap.scanFolder();
                    ap.show(table1);
                }
                catch (Exception exc){
                    directoryPath.setText("Illegal path. ");
                }
            }
        });
        // Listener pentru butonul de previous
        previousFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ap.showfiles.setFolder(new File(previousFolder));
                ap.scanFolder();
                ap.show(table1);
                directoryPath.setText(previousFolder);
            }
        });
        directoryPath.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                previousFolder = directoryPath.getText();
            }
        });
    }
    // Functia care actualizeaza lista dupa ce a fost efectuat un rename / copy / delete
    public void operation(){
        if (previous.equals("ShowFiles")) ap.show(table1);
        if (previous.equals("SortByNameAscending")) ap.listaSortataCrescatorByName(table1);
        if (previous.equals("SortByNameDescending")) ap.listaSortataDescrescatorByName(table1);
        if (previous.equals("SortByTypeAscending")) ap.listaSortataCrescatorByType(table1);
        if (previous.equals("SortByTypeDescending")) ap.listaSortataDescrescatorByType(table1);
        if (previous.equals("SortBySizeAscending")) ap.listaSortataCrescatorBySize(table1);
        if (previous.equals("SortBySizeDescending"))  ap.listaSortataDescrescatorBySize(table1);
        if (previous.equals("ShowDocuments")) ap.showDocuments(table1);
        if (previous.equals("ShowImages")) ap.showImages(table1);
        if (previous.equals("ShowExecutables")) ap.showExecutables(table1);
        if (previous.equals("ShowVideos")) ap.showVideos(table1);
        if (previous.equals("ShowOthers")) ap.showOthers(table1);
        if (previous.equals("ShowUsers") && !dataform) as.showUsers(table1);
        if (previous.equals("ShowUsers") && dataform) as.showUsersDB(table1);
    }
    //Functia pentru interfata
    public void Menu(){
        ap.show(table1);
        mainmenuframe.setContentPane(MainMenuPanel);
        mainmenuframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainmenuframe.setLocationRelativeTo(null);
        mainmenuframe.setResizable(true);
        mainmenuframe.setSize(1000, 500);
        mainmenuframe.setVisible(true);
    }

}
