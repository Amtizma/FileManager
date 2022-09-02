package Interface;

import FileManager.EventLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class MainMenu extends CreateDirectory {
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
    private JMenuItem CreateFolder = new JMenuItem("Create New Folder");
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

        /************************************************
         * Listener pentru return din interfata de removeUser.
         * A fost pus in mainmenu deoarece are nevoie de mainmenuframe, neaccesibil din clasa removeuser.
         ***********************************************/
        returnButtonUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeuser.dispose();
                mainmenuframe.setVisible(true);
                clear();
            }
        });
        /************************************************
         * Listener pentru return din interfata de createDirectory. Inchide fereastra.
         ***********************************************/
        returnButtonCreateDirectory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createdirectory.dispose();
                clear();
            }
        });
        /************************************************
         * Listener pentru return din interfata de addPermission.
         * A fost pus in mainmenu deoarece are nevoie de mainmenuframe, neaccesibil din clasa addpermission.
         ***********************************************/
        returnButtonAddPermissions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addpermissions.dispose();
                mainmenuframe.setVisible(true);
                permissionaddclear();
            }
        });
        /************************************************
         * Listener pentru return din interfata de deletePermission.
         * A fost pus in mainmenu deoarece are nevoie de mainmenuframe, neaccesibil din clasa deletepermission.
         ***********************************************/
        returnButtonDeletePermissions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletepermissions.dispose();
                mainmenuframe.setVisible(true);
                permissiondeleteclear();
            }
        });
        /************************************************
         * Listener pentru return din interfata de delete.
         * A fost pus in mainmenu deoarece are nevoie de mainmenuframe, neaccesibil din clasa delete.
         ***********************************************/
        returnButtonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletefile.dispose();
                mainmenuframe.setVisible(true);
                FilePathField.setText("");
                DeleteConfirmLabel.setText("");
            }
        });
        /************************************************
         * Listener pentru return din interfata de renamefile.
         * A fost pus in mainmenu deoarece are nevoie de mainmenuframe, neaccesibil din clasa renamefile.
         ***********************************************/
        returnButtonRenameFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renamefile.dispose();
                mainmenuframe.setVisible(true);
                renameclear();
            }
        });
        /************************************************
         * Listener pentru return din interfata de copyFile.
         * A fost pus in mainmenu deoarece are nevoie de mainmenuframe, neaccesibil din clasa copyFile.
         ***********************************************/
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
                    /************************************************
                     * if-urile copiaza automat path-ul fisierului pe care este apasat click daca interfetele
                     * de rename/delete/copy sunt vizibile.
                     ***********************************************/
                    if (renamefile.isShowing()) RenameFileOldPath.setText(ap.getPath(table1));
                    if (deletefile.isShowing()) FilePathField.setText(ap.getPath(table1));
                    if (copyfile.isShowing()) CopyFileOldPath.setText(ap.getPath(table1));
                    /************************************************
                     * Urmatoarele 2 linii verifica daca obiectul apasat este director sau nu.
                     ***********************************************/
                    int j = table1.getSelectedRow();
                    String directory1 = table1.getValueAt(j, 4).toString();
                    /************************************************
                     * In cazul in care apasam pe un director, se apeleaza setterul care schimba path-ul, se repeleaza functiile de scan,
                     * de afisare, se memoreaza ultimul folder (pentru return) si se afiseazan noul folder in textbox-ul de jos.
                     * In cazul in care esueaza, in catch se revine la folderul default (Downloads).
                     ***********************************************/
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
                /************************************************
                 * Daca se apasa click dreapta, se retine linia din tabel, se obtine path-ul si apare meniul jpm
                 * la pozitia mouse-ului cu cele 5 metode disponibile.
                 ***********************************************/
                if (e.getButton() == MouseEvent.BUTTON3){
                    Point point = e.getPoint();
                    int index = table1.rowAtPoint(point);
                    fileToOpen = ap.getPathRightClick(table1, index);
                    jpm.add(DeleteFileMenu);jpm.add(RenameFileMenu);jpm.add(CopyFileMenu);jpm.add(OpenFile);jpm.add(CreateFolder);
                    jpm.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        /************************************************
         * Listener-ul permite sortarea apasand pe header-ul tabelului. La un click impar se va sorta crescator, la unul par se va sorta descrescator.
         * Index-ul se folosete pentru a vedea care coloana este apasata.
         ***********************************************/
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
                if (index == 3) {
                    if(clicked%2 == 1){
                        ap.listaSortataCrescatorBySize(table1);
                        table1.getColumnModel().getColumn(3).setHeaderValue("Size ↑");
                        previous = "SortBySizeAscending";
                        try {
                            evlog.eventLogger(id1, "sorted all files by size ascending. ");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if(clicked%2 == 0){
                        ap.listaSortataDescrescatorBySize(table1);
                        table1.getColumnModel().getColumn(3).setHeaderValue("Size ↓");
                        previous = "SortBySizeDescending";
                        try {
                            evlog.eventLogger(id1, "sorted all files by size descending. ");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                if (index == 4) {
                    if(clicked%2 == 1){
                        ap.listaSortataCrescatorByType(table1);
                        table1.getColumnModel().getColumn(4).setHeaderValue("Type ↑");
                        previous = "SortByTypeAscending";
                        try {
                            evlog.eventLogger(id1, "sorted all files by type ascending. ");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if(clicked%2 == 0){
                        ap.listaSortataDescrescatorByType(table1);
                        table1.getColumnModel().getColumn(4).setHeaderValue("Type ↓");
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
        /************************************************
         * Listener pentru butonul de rename. Preia vechiul nume si noul nume din interfata si apelaza functia din FileManager.
         ***********************************************/
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
        /************************************************
         * Listener pentru butonul de delete. Preia path-ul din interfata si apeleaza functie de delete din FIleManager.
         ***********************************************/
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
        /************************************************
         * Listener pentru butonul de copy. Preia vechiul path si noul path din interfata si apelaza functia din FileManager.
         ***********************************************/
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
        /************************************************
         * Listener pentru butonul de removeUser. Preia ID-ul si parola din interfata si apeleaza functiile corespunzatoare.
         * Dataform reprezinta modalitatea user-ului de conectare (DB sau fisier).
         ***********************************************/
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
        /************************************************
         * Listener pentru butonul de home. Home-ul este setat ca si folder-ul cu care se initializeaza programul
         * Se reapeleaza functiile de scanare si afisare.
         ***********************************************/
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
        /************************************************
         * Listener pentru showFiles.
         ***********************************************/
        ShowFilesMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    previous = "ShowFiles";
                    ap.show(table1);
                    try {
                        evlog.eventLogger(id1, "showed all files. ");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
            }
        });
        /************************************************
         * Listener pentru butonul de showDocuments si se verifica daca user-ul are aceasta permisiune.
         ***********************************************/
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
        /************************************************
         * Listener pentru butonul de showImages si se verifica daca user-ul are aceasta permisiune.
         ***********************************************/
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
        /************************************************
         * Listener pentru butonul de showVideos si se verifica daca user-ul are aceasta permisiune.
         ***********************************************/
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
        /************************************************
         * Listener pentru butonul de showExecutables si se verifica daca user-ul are aceasta permisiune.
         ***********************************************/
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
        /************************************************
         * Listener pentru butonul de showOthers si se verifica daca user-ul are aceasta permisiune.
         ***********************************************/
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
        /************************************************
         * Listener pentru butonul de sortare crescatore dupa nume si se verifica daca user-ul are aceasta permisiune.
         ***********************************************/
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
        /************************************************
         * Listener pentru butonul de sortare descrescatoare dupa nume si se verifica daca user-ul are aceasta permisiune.
         ***********************************************/
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
        /************************************************
         * Listener pentru butonul de sortare crescatore dupa size si se verifica daca user-ul are aceasta permisiune.
         ***********************************************/
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
        /************************************************
         * Listener pentru butonul de sortare descrescatore dupa size si se verifica daca user-ul are aceasta permisiune.
         ***********************************************/
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
        /************************************************
         * Listener pentru butonul de sortare crescatore dupa type si se verifica daca user-ul are aceasta permisiune.
         ***********************************************/
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
        /************************************************
         * Listener pentru butonul de sortare descrescatore dupa type si se verifica daca user-ul are aceasta permisiune.
         ***********************************************/
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
        /************************************************
         * Listener care deschide interfata de deletefile si se verifica daca user-ul are permisiunea.
         ***********************************************/
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
        /************************************************
         * Listener care deschide interfata de copyflle si se verifica daca user-ul are permisiunea.
         ***********************************************/
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
        /************************************************
         * Listener pentru butonul de createdirectory. Creeaza un director nou cu numele din interfata in folderul curent.
         ***********************************************/
        createDirectroyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                  if(directoryPath.getText().endsWith("\\")) new File(directoryPath.getText() + directorynamelabel.getText()).mkdir();
                  else new File(directoryPath.getText() + "\\" + directorynamelabel.getText()).mkdir();
                  CreateDirConfirm.setText("Directory created. ");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        /************************************************
         * Listener care deschide interfata de createfolder.
         ***********************************************/
        CreateFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    createdirectorymenu();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        /************************************************
         * Listener pentru optiunea openFile de pe click drepata si deschide obiectul selectat.
         ***********************************************/
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
        /************************************************
         * Listener care deschide interfata de renamefile si se verifica daca user-ul are permisiunea.
         ***********************************************/
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
        /************************************************
         * Listener care deschide interfata de addpermissions si se verifica daca user-ul are permisiunea.
         ***********************************************/
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
        /************************************************
         * Listener care deschide interfata de deletepermissions si se verifica daca user-ul are permisiunea.
         ***********************************************/
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
        /************************************************
         * Listener care afiseaza userii in tabel daca user-ul curent are aceasta permisiune.
         * Apeleaza functia corespunzatoare in functie de dataform.
         ***********************************************/
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
        /************************************************
         * Listener care deschide interfata de removeuser si se verifica daca user-ul are permisiunea.
         ***********************************************/
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

        /************************************************
         * Listener pentru butonul de back. Functia taie tot ce urmeaza dupa ultima "\" ca sa se intoarca cu un folder inapoi,
         * dupa care apeleaza functiile de scanare si afisare.
         ***********************************************/
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
        /************************************************
         * Listener pentru butonul de schimbare de fisier. Seteaza folderul curent la cel din textbox, si apeleaza functiile
         * de scanare si afisare.
         ***********************************************/
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
        /************************************************
         * Listener care seteaza folderul curent la previous si repeleaza functiile de scanare si afisare.
         ***********************************************/
        previousFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ap.showfiles.setFolder(new File(previousFolder));
                ap.scanFolder();
                ap.show(table1);
                directoryPath.setText(previousFolder);
            }
        });
        /************************************************
         * Listener mereu activ care retine precedentul folder deschis.
         ***********************************************/
        directoryPath.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                previousFolder = directoryPath.getText();
            }
        });
    }
    /************************************************
     * Functie care reactualizeaza interfata dupa ce a fost efectuata o actiune care a modificat lista (rename, delete, copy, etc.)
     ***********************************************/
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
    /************************************************
     * Functia care deschide interfata princpiala.
     ***********************************************/
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
