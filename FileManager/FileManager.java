package FileManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;


public class FileManager{
    public ShowFiles showfiles = new ShowFiles();
    public SignUp signup = new SignUp();
    public EventLogger eventlog = new EventLogger();
    public ArrayList<ShowFiles> lista = new ArrayList<>();
    public ArrayList<ShowFiles> listacomparare = new ArrayList<>();
    public ArrayList<ShowFiles> sizes = new ArrayList<>();
    int s = 0;
    // Functia de renameFile
    public void renameFile(String originalname, String newname, JLabel Confirm){
        java.io.File file = new java.io.File(originalname);
        java.io.File rename = new java.io.File(file.getParentFile(), newname);
        boolean flag = file.renameTo(rename);
        if (flag) {
            Confirm.setText("File Successfully Renamed");
        } else {
            Confirm.setText("Operation Failed");
        }
        showfiles.listOfFiles = showfiles.folder.listFiles();
        lista.clear();
        listacomparare.clear();
        scanFolder();
    }
    // Functia de copyFile
    public void copyFile(java.io.File oldpath, java.io.File newpath, JLabel Confirm) throws IOException {
        Files.copy(oldpath.toPath(), newpath.toPath());
        showfiles.listOfFiles = showfiles.folder.listFiles();
        lista.clear();
        listacomparare.clear();
        scanFolder();
    }
    // Functia de deleteFile
    public void deleteFile(java.io.File path, JLabel Confirm) {
        if (path.isDirectory()) {
            java.io.File[] c = path.listFiles();
            for (java.io.File file : c) {
                if (file.isDirectory()) {
                    deleteFile(file, Confirm);
                }
                file.delete();
            }
            Confirm.setText("File deleted successfully.");
            path.delete();
        } else {
            path.delete();
            Confirm.setText("File deleted successfully.");
        }
        showfiles.listOfFiles = showfiles.folder.listFiles();
        lista.clear();
        listacomparare.clear();
        scanFolder();
    }

    // Functia de showDocuments
    public void showDocuments(JTable table) {
        int j = 1;
        String[] col = {"Index", "Name", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(2).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(70);
        Object[] rowData = new Object[4];
        for (ShowFiles showFiles : lista) {
            for (Extensions.documentExtensions docext : Extensions.documentExtensions.values()) {
                if (showFiles.type.equals(docext.name())) {
                    rowData[0] = j;
                    rowData[1]= showFiles.getName();
                    rowData[2] = showFiles.getSize();
                    rowData[3] = showFiles.getType();
                    tableModel.addRow(rowData);
                    j++;
                }
            }
        }
    }
    // Functia de showExecutables
    public void showExecutables(JTable table){
        int j = 1;
        String[] col = {"Index", "Name", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(2).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(70);
        Object[] rowData = new Object[4];
        for (ShowFiles showFiles : lista) {
            if (showFiles.type.equals("exe")) {
                rowData[0] = j;
                rowData[1]= showFiles.getName();
                rowData[2] = showFiles.getSize();
                rowData[3] = showFiles.getType();
                tableModel.addRow(rowData);
                j++;
            }
        }
    }
    // Functia de showImages
    public void showImages(JTable table) {
        String[] col = {"Index", "Name", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(2).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(70);
        Object[] rowData = new Object[4];
        int j = 1;
        for (ShowFiles showFiles : lista) {
            for (Extensions.imageExtensions imgext : Extensions.imageExtensions.values()) {
                if (showFiles.type.equals(imgext.name())){
                    rowData[0] = j;
                    rowData[1]= showFiles.getName();
                    rowData[2] = showFiles.getSize();
                    rowData[3] = showFiles.getType();
                    tableModel.addRow(rowData);
                    j++;
                }
            }
        }
    }
    // Niste liste create pentru functia de showOthers
    public ArrayList<ShowFiles> templist = new ArrayList<>();
    public ArrayList<ShowFiles> clonelist = new ArrayList<>();
    boolean clone = false;

    // Functie creata pentru showOthers
    public void getlist() {
        for (int i = 0; i < clonelist.size(); i++) {
            for (Extensions.extensions ext : Extensions.extensions.values()) {
                if (clonelist.get(i).type.equals(ext.name())){
                    templist.add(clonelist.get(i));
                    break;
                }

            }
        }

    }
    // Functia de showOthers (elimina tot ce e document, imagine, etc. din lista cu toate valorile)
    public void showOthers(JTable table){
        if(!clone) clonelist.addAll(lista);
        clone = true;
        String[] col = {"Index", "Name", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(2).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(70);
        Object[] rowData = new Object[4];
        int j = 1;
        getlist();
        clonelist.removeAll(templist);
        for (int i = 0; i < clonelist.size(); i++) {
            rowData[0] = j;
            rowData[1] = clonelist.get(i).getName();
            rowData[2] = clonelist.get(i).getSize();
            rowData[3] = clonelist.get(i).getType();
            tableModel.addRow(rowData);
            j++;
        }
    }
    // Functia de showVideos
    public void showVideos(JTable table){
        String[] col = {"Index", "Name", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(2).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(70);
        Object[] rowData = new Object[4];
        int j = 1;
        for (ShowFiles showFiles : lista) {
            for (Extensions.videoExtensions  vidext : Extensions.videoExtensions.values()) {
                if (showFiles.type.equals(vidext.name())){
                    rowData[0] = j;
                    rowData[1]= showFiles.getName();
                    rowData[2] = showFiles.getSize();
                    rowData[3] = showFiles.getType();
                    tableModel.addRow(rowData);
                    j++;
                }
            }
        }
    }
    public void listaSortataDescrescatorByName(JTable table){
        int j = 1;
        String[] col = {"Index", "Name", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(2).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(70);
        Object[] rowData = new Object[4];
        Comparator<ShowFiles> compname = (ShowFiles a, ShowFiles b) -> {
            return b.getName().toLowerCase().compareTo(a.getName().toLowerCase());
        };
        Collections.sort(listacomparare, compname);
        for (int i = 0; i < listacomparare.size(); i++) {
            rowData[0] = j;
            rowData[1]= listacomparare.get(i).getName();
            rowData[2] = listacomparare.get(i).getSize();
            rowData[3] = listacomparare.get(i).getType();
            tableModel.addRow(rowData);
            j++;
        }
    }
    public void listaSortataCrescatorByName(JTable table){
        int j = 1;
        String[] col = {"Index", "Name", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(2).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(70);
        Object[] rowData = new Object[4];
        Comparator<ShowFiles> compname = (ShowFiles a, ShowFiles b) -> {
            return b.getName().toLowerCase().compareTo(a.getName().toLowerCase());
        };
        Collections.sort(listacomparare, compname);
        Collections.reverse(listacomparare);
        for (int i = 0; i < listacomparare.size(); i++) {
            rowData[0] = j;
            rowData[1]= listacomparare.get(i).getName();
            rowData[2] = listacomparare.get(i).getSize();
            rowData[3] = listacomparare.get(i).getType();
            tableModel.addRow(rowData);
            j++;
        }
    }
    public void listaSortataDescrescatorBySize(JTable table){
        int j = 1;
        String[] col = {"Index", "Name", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(2).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(70);
        Object[] rowData = new Object[4];
        Collections.sort(listacomparare);
        for (int i = 0; i < listacomparare.size(); i++) {
            rowData[0] = j;
            rowData[1]= listacomparare.get(i).getName();
            rowData[2] = listacomparare.get(i).getSize();
            rowData[3] = listacomparare.get(i).getType();
            tableModel.addRow(rowData);
            j++;
        }
    }
    public void listaSortataCrescatorBySize(JTable table){
        int j = 1;
        String[] col = {"Index", "Name", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(2).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(70);
        Object[] rowData = new Object[4];
        Collections.sort(listacomparare);
        Collections.reverse(listacomparare);
        for (int i = 0; i < listacomparare.size(); i++) {
            rowData[0] = j;
            rowData[1]= listacomparare.get(i).getName();
            rowData[2] = listacomparare.get(i).getSize();
            rowData[3] = listacomparare.get(i).getType();
            tableModel.addRow(rowData);
            j++;
        }
    }
    public void listaSortataCrescatorByType(JTable table){
        int j = 1;
        String[] col = {"Index", "Name", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(2).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(70);
        Object[] rowData = new Object[4];
        Comparator<ShowFiles> comptype = (ShowFiles a, ShowFiles b) -> {
            return b.getType().toLowerCase().compareTo(a.getType().toLowerCase());
        };
        Collections.sort(listacomparare, comptype);
        Collections.reverse(listacomparare);
        for (int i = 0; i < listacomparare.size(); i++) {
            rowData[0] = j;
            rowData[1]= listacomparare.get(i).getName();
            rowData[2] = listacomparare.get(i).getSize();
            rowData[3] = listacomparare.get(i).getType();
            tableModel.addRow(rowData);
            j++;
        }
    }
    public void listaSortataDescrescatorByType(JTable table){
        int j = 1;
        String[] col = {"Index", "Name", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(2).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(70);
        Object[] rowData = new Object[4];
        Comparator<ShowFiles> comptype = (ShowFiles a, ShowFiles b) -> {
            return b.getType().toLowerCase().compareTo(a.getType().toLowerCase());
        };
        Collections.sort(listacomparare, comptype);
        for (int i = 0; i < listacomparare.size(); i++) {
            rowData[0] = j;
            rowData[1]= listacomparare.get(i).getName();
            rowData[2] = listacomparare.get(i).getSize();
            rowData[3] = listacomparare.get(i).getType();
            tableModel.addRow(rowData);
            j++;
        }
    }
    // Functie pentru sortarile descrescatoare (le sortez crescator si apoi invers intreaga lista)
    public void reverselist(JTable table)
    {
        Collections.reverse(lista);
        int j = lista.size();
        String[] col = {"Index","Name", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(2).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(70);
        Object[] rowData = new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            rowData[0] = j;
            rowData[1]= lista.get(i).getName();
            rowData[2] = lista.get(i).getSize();
            rowData[3] = lista.get(i).getType();
            tableModel.addRow(rowData);
            j--;
        }
        Collections.reverse(lista);
    }
    // Functia care scaneaza continutul folderelor si le salveaza intr-o lista
    public void scanFolder(){
        lista.clear();
        listacomparare.clear();
        sizes.clear();
        for (File file : showfiles.listFiles()) {
            if (file.isFile()) {
                int i = file.getName().lastIndexOf('.');
                if (i > 0) {
                    showfiles.type = file.getName().substring(i+1);
                }
                else showfiles.type = "unknown";
                showfiles.size = file.length();
                if(showfiles.type.equals("unknown")) {
                    ShowFiles fisier = new ShowFiles(showfiles.size, file.getName(), showfiles.type);
                    lista.add(fisier);
                    listacomparare.add(fisier);
                }
                    else {
                    ShowFiles fisier = new ShowFiles(showfiles.size, file.getName().substring(0, file.getName().lastIndexOf('.')), showfiles.type);
                    lista.add(fisier);
                    listacomparare.add(fisier);
                }
            }
            if(file.isDirectory()) {
                    ShowFiles fisier = new ShowFiles(0L, file.getName(), "directory");
                    lista.add(fisier);
                    listacomparare.add(fisier);
                    // Thread-ul adauga directoarele intr-o alta lista
                    new Thread(()-> {
                        ShowFiles fisier1 = new ShowFiles(folderSize(file), file.getName(), "directory");
                        sizes.add(fisier1);
                    }).start();

            }
        }
    }
    // Functie care calculeaza size-ul directoarelor
    public long folderSize(File directory){
            long length = 0;
            try {
                for (File file : directory.listFiles()) {
                    if (file.isFile())
                        length += file.length();
                    else
                        length += folderSize(file);
                }
            } catch (Exception e) {
            }

        return length;
    }
     // Functie de afisare
    public void show(JTable table){
        calculateSize();
            int j = 1;
            String[] col = {"Index", "Name", "Size", "Type"};
            DefaultTableModel tableModel = new DefaultTableModel(col, 0);
            table.setModel(tableModel);
            table.getColumnModel().getColumn(0).setMaxWidth(40);
            table.getColumnModel().getColumn(2).setMaxWidth(100);
            table.getColumnModel().getColumn(3).setMaxWidth(70);
            Object[] rowData = new Object[4];
            for (int i = 0; i < lista.size(); i++) {
                    rowData[0] = j;
                    rowData[1] = lista.get(i).getName();
                    rowData[2] = lista.get(i).getSize();
                    rowData[3] = lista.get(i).getType();
                    tableModel.addRow(rowData);
                    j++;
                }
            // Thread care afiseaza size-ul directoarelor dupa ce a fost calculat
            new Thread(()-> {
                try {
                    do {
                        for (int i = 0; i < lista.size(); i++) {
                            for (int k = 0; k < sizes.size(); k++) {
                                if (lista.get(i).name.equals(sizes.get(k).name)) {
                                    tableModel.setValueAt(sizes.get(k).getSize(), i, 2);
                                }
                            }
                        }
                    } while (sizes.size() != s);
                }
                catch (IndexOutOfBoundsException e){

                }
            }).start();
    }
    // Functie care calculeaza numarul de directoare
    public void calculateSize() {
        s = 0;
        for (File file : showfiles.listFiles()) {
            if (file.isDirectory()) {
                s++;
            }
        }
    }
    // Functie care obtine path-ul unui obiect din tabel
    public String getPath(JTable table){
        int j = table.getSelectedRow();
        String filename = table.getValueAt(j, 1).toString() + "." + table.getValueAt(j, 3);
        String directoryname = table.getValueAt(j, 1).toString();
        String path = "asd";
        for (File file : showfiles.listFiles()) {
                if (file.getName().equals(filename)) {
                    path = file.getPath();
                    break;
                }
                if (file.getName().equals(directoryname)) {
                    path = file.getPath();
                    break;
                }

        }
        return path;
    }
    //Functie care obtine path-ul unui obiect din tabel cu click dreapta
    public String getPathRightClick(JTable table, int j){
        String filename = table.getValueAt(j, 1).toString() + "." + table.getValueAt(j, 3);
        String directoryname = table.getValueAt(j, 1).toString();
        String path = "asd";
        for (File file : showfiles.listFiles()) {
            if (file.getName().equals(filename)) {
                path = file.getPath();
                break;
            }
            if (file.getName().equals(directoryname)) {
                path = file.getPath();
                break;
            }

        }
        return path;
    }
}
