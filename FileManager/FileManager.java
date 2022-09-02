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

/************************************************
 * Clasa File Manager cuprinde toate functiile
 * care lucreaza cu fisierele si toate functiile
 * de sortare.
 ***********************************************/
public class FileManager{
    public ShowFiles showfiles = new ShowFiles();
    public SignUp signup = new SignUp();
    public EventLogger eventlog = new EventLogger();
    public ArrayList<ShowFiles> lista = new ArrayList<>();
    public ArrayList<ShowFiles> listacomparare = new ArrayList<>();
    public ArrayList<ShowFiles> sizes = new ArrayList<>();
    int s = 0;
    /************************************************
     * Functia de rename are nevoie de path-ul initial
     * si de numele nou. Se foloseste de metoda renameTo
     * din biblioteca File. Dupa rename, se reapeleaza metoda
     * de scan pentru ca listele sa fie updatate cu noul nume.
     ***********************************************/
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
    /************************************************
     * Functia de copyFile are nevoie de path-ul initial
     * si de noul path. Se foloseste de metoda copy
     * din biblioteca Files. Dupa copiere, se reapeleaza metoda
     * de scan pentru ca listele sa fie updatate cu noul nume.
     ***********************************************/
    public void copyFile(java.io.File oldpath, java.io.File newpath, JLabel Confirm) throws IOException {
        Files.copy(oldpath.toPath(), newpath.toPath());
        showfiles.listOfFiles = showfiles.folder.listFiles();
        lista.clear();
        listacomparare.clear();
        scanFolder();
    }
    /************************************************
     * Functia de delete are nevoie doar de path.
     * Sterge fisierele, iar daca aceasta da de un director,
     * sterge recursiv toate fisierele si din acel director.
     * La final se reapeleaza metoda de scan pentru actualizare.
     ***********************************************/
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

    /************************************************
     * Functia de showDocuments cauta in lista fiecare element
     * ce are type-ul corespunzator unui document si le afiseaza
     * in tabel.
     ***********************************************/
    public void showDocuments(JTable table) {
        int j = 1;
        String[] col = {"Index", "Name", "Owner", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(70);
        Object[] rowData = new Object[5];
        for (ShowFiles showFiles : lista) {
            for (Extensions.documentExtensions docext : Extensions.documentExtensions.values()) {
                if (showFiles.type.equals(docext.name())) {
                    rowData[0] = j;
                    rowData[1]= showFiles.getName();
                    rowData[2] = showFiles.getOwner();
                    rowData[3] = showFiles.getSize();
                    rowData[4] = showFiles.getType();
                    tableModel.addRow(rowData);
                    j++;
                }
            }
        }
    }
    /************************************************
     * Functia de showExecutables cauta in lista fiecare element
     * ce are type-ul de tip exe si le afiseaza
     * in tabel.
     ***********************************************/
    public void showExecutables(JTable table){
        int j = 1;
        String[] col = {"Index", "Name", "Owner", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
       
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(70);
        Object[] rowData = new Object[5];
        for (ShowFiles showFiles : lista) {
            if (showFiles.type.equals("exe")) {
                rowData[0] = j;
                rowData[1]= showFiles.getName();
                rowData[2] = showFiles.getOwner();
                rowData[3] = showFiles.getSize();
                rowData[4] = showFiles.getType();
                tableModel.addRow(rowData);
                j++;
            }
        }
    }
    /************************************************
     * Functia de showImages cauta in lista fiecare element
     * ce are type-ul corespunzator unei imagini si le afiseaza
     * in tabel.
     ***********************************************/
    public void showImages(JTable table) {
        String[] col = {"Index", "Name", "Owner", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
       
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(70);
        Object[] rowData = new Object[5];
        int j = 1;
        for (ShowFiles showFiles : lista) {
            for (Extensions.imageExtensions imgext : Extensions.imageExtensions.values()) {
                if (showFiles.type.equals(imgext.name())){
                    rowData[0] = j;
                    rowData[1]= showFiles.getName();
                    rowData[2] = showFiles.getOwner();
                    rowData[3] = showFiles.getSize();
                    rowData[4] = showFiles.getType();
                    tableModel.addRow(rowData);
                    j++;
                }
            }
        }
    }
    /************************************************
     * Liste necesare pentru functia de showOthers.
     * Sunt necesare deoarece functia de showOthers se face
     * prin eliminarea tuturor elementelor care au extensie
     * de document, imagine, etc.
     ***********************************************/
    public ArrayList<ShowFiles> templist = new ArrayList<>();
    public ArrayList<ShowFiles> clonelist = new ArrayList<>();
    boolean clone = false;

    /************************************************
     * O functie folosita pentru popularea listei cloneList
     * necesara la showOthers
     ***********************************************/
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
    /************************************************
     * Functia de showOthers care elimina toate elementele
     * care au type-ul de document, imagini, etc.
     * si le afiseaza pe restul in tabel.
     ***********************************************/
    public void showOthers(JTable table){
        if(!clone) clonelist.addAll(lista);
        clone = true;
        String[] col = {"Index", "Name", "Owner", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(70);
        Object[] rowData = new Object[5];
        int j = 1;
        getlist();
        clonelist.removeAll(templist);
        for (int i = 0; i < clonelist.size(); i++) {
            rowData[0] = j;
            rowData[1] = clonelist.get(i).getName();
            rowData[2] = clonelist.get(i).getOwner();
            rowData[3] = clonelist.get(i).getSize();
            rowData[4] = clonelist.get(i).getType();
            tableModel.addRow(rowData);
            j++;
        }
    }
    /************************************************
     * Functia de showVideos cauta in lista fiecare element
     * ce are type-ul corespunzator unui video si le afiseaza
     * in tabel.
     ***********************************************/
    public void showVideos(JTable table){
        String[] col = {"Index", "Name", "Owner", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
       
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(70);
        Object[] rowData = new Object[5];
        int j = 1;
        for (ShowFiles showFiles : lista) {
            for (Extensions.videoExtensions  vidext : Extensions.videoExtensions.values()) {
                if (showFiles.type.equals(vidext.name())){
                    rowData[0] = j;
                    rowData[1]= showFiles.getName();
                    rowData[2] = showFiles.getOwner();
                    rowData[3] = showFiles.getSize();
                    rowData[4] = showFiles.getType();
                    tableModel.addRow(rowData);
                    j++;
                }
            }
        }
    }
    /************************************************
     * Functia are implementat un comparator ce ordoneaza crescator dupa nume
     * in functie de care se sorteaza listacomparare si apoi afiseaza
     * lista sortata in tabel
     ***********************************************/
    public void listaSortataDescrescatorByName(JTable table){
        int j = 1;
        String[] col = {"Index", "Name", "Owner", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(70);
        Object[] rowData = new Object[5];
        Comparator<ShowFiles> compname = (ShowFiles a, ShowFiles b) -> {
            return b.getName().toLowerCase().compareTo(a.getName().toLowerCase());
        };
        Collections.sort(listacomparare, compname);
        for (int i = 0; i < listacomparare.size(); i++) {
            rowData[0] = j;
            rowData[1]= listacomparare.get(i).getName();
            rowData[2] = listacomparare.get(i).getOwner();
            rowData[3] = listacomparare.get(i).getSize();
            rowData[4] = listacomparare.get(i).getType();
            tableModel.addRow(rowData);
            j++;
        }
    }
    /************************************************
     * Functia are implementat un comparator ce ordoneaza crescator dupa nume
     * in functie de care se sorteaza listacomparare, o inverseaza pentru a deveni descrescatoare
     * si apoi afiseaza lista sortata in tabel
     ***********************************************/
    public void listaSortataCrescatorByName(JTable table){
        int j = 1;
        String[] col = {"Index", "Name", "Owner", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
       
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(70);
        Object[] rowData = new Object[5];
        Comparator<ShowFiles> compname = (ShowFiles a, ShowFiles b) -> {
            return b.getName().toLowerCase().compareTo(a.getName().toLowerCase());
        };
        Collections.sort(listacomparare, compname);
        Collections.reverse(listacomparare);
        for (int i = 0; i < listacomparare.size(); i++) {
            rowData[0] = j;
            rowData[1]= listacomparare.get(i).getName();
            rowData[2] = listacomparare.get(i).getOwner();
            rowData[3] = listacomparare.get(i).getSize();
            rowData[4] = listacomparare.get(i).getType();
            tableModel.addRow(rowData);
            j++;
        }
    }
    /************************************************
     * Functia apeleaza override-ul compareTo din showfiles pentru a sorta
     * direct in functie de size si apoi afiseaza lista sortata in tabel.
     ***********************************************/
    public void listaSortataDescrescatorBySize(JTable table){
        int j = 1;
        String[] col = {"Index", "Name", "Owner", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
       
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(70);
        Object[] rowData = new Object[5];
        Collections.sort(listacomparare);
        for (int i = 0; i < listacomparare.size(); i++) {
            rowData[0] = j;
            rowData[1]= listacomparare.get(i).getName();
            rowData[2] = listacomparare.get(i).getOwner();
            rowData[3] = listacomparare.get(i).getSize();
            rowData[4] = listacomparare.get(i).getType();
            tableModel.addRow(rowData);
            j++;
        }
    }
    /************************************************
     * Functia apeleaza override-ul compareTo din showfiles pentru a sorta
     * direct in functie de size, o inverseaza si apoi afiseaza lista sortata in tabel.
     ***********************************************/
    public void listaSortataCrescatorBySize(JTable table){
        int j = 1;
        String[] col = {"Index", "Name", "Owner", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
       
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(70);
        Object[] rowData = new Object[5];
        Collections.sort(listacomparare);
        Collections.reverse(listacomparare);
        for (int i = 0; i < listacomparare.size(); i++) {
            rowData[0] = j;
            rowData[1]= listacomparare.get(i).getName();
            rowData[2] = listacomparare.get(i).getOwner();
            rowData[3] = listacomparare.get(i).getSize();
            rowData[4] = listacomparare.get(i).getType();
            tableModel.addRow(rowData);
            j++;
        }
    }
    /************************************************
     * Functia are implementat un comparator ce ordoneaza crescator dupa type
     * in functie de care se sorteaza listacomparare
     * si apoi afiseaza lista sortata in tabel
     ***********************************************/
    public void listaSortataCrescatorByType(JTable table){
        int j = 1;
        String[] col = {"Index", "Name", "Owner", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
       
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(70);
        Object[] rowData = new Object[5];
        Comparator<ShowFiles> comptype = (ShowFiles a, ShowFiles b) -> {
            return b.getType().toLowerCase().compareTo(a.getType().toLowerCase());
        };
        Collections.sort(listacomparare, comptype);
        Collections.reverse(listacomparare);
        for (int i = 0; i < listacomparare.size(); i++) {
            rowData[0] = j;
            rowData[1]= listacomparare.get(i).getName();
            rowData[2] = listacomparare.get(i).getOwner();
            rowData[3] = listacomparare.get(i).getSize();
            rowData[4] = listacomparare.get(i).getType();
            tableModel.addRow(rowData);
            j++;
        }
    }
    /************************************************
     * Functia are implementat un comparator ce ordoneaza crescator dupa type
     * in functie de care se sorteaza listacomparare, o inverseaza pentru a deveni descrescatoare
     * si apoi afiseaza lista sortata in tabel
     ***********************************************/
    public void listaSortataDescrescatorByType(JTable table){
        int j = 1;
        String[] col = {"Index", "Name", "Owner", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);
       
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(70);
        Object[] rowData = new Object[5];
        Comparator<ShowFiles> comptype = (ShowFiles a, ShowFiles b) -> {
            return b.getType().toLowerCase().compareTo(a.getType().toLowerCase());
        };
        Collections.sort(listacomparare, comptype);
        for (int i = 0; i < listacomparare.size(); i++) {
            rowData[0] = j;
            rowData[1]= listacomparare.get(i).getName();
            rowData[2] = listacomparare.get(i).getOwner();
            rowData[3] = listacomparare.get(i).getSize();
            rowData[4] = listacomparare.get(i).getType();
            tableModel.addRow(rowData);
            j++;
        }
    }

    /************************************************
     * Functia scaneaza continutul directoarelor si populeaza listele.
     * Daca type-ul nu este definit se completeaza cu unknown.
     * Se populeaza si o lista separata care se executa pe thread-uri pentru a afisa size-ul directoarelor.
     ***********************************************/
    public void scanFolder(){
        lista.clear();
        listacomparare.clear();
        sizes.clear();
        for (File file : showfiles.listFiles()) {
            try {
                if (file.isFile()) {
                    int i = file.getName().lastIndexOf('.');
                    if (i > 0) {
                        showfiles.type = file.getName().substring(i + 1);
                    } else showfiles.type = "unknown";
                    showfiles.size = file.length();
                    if (showfiles.type.equals("unknown")) {
                        ShowFiles fisier = new ShowFiles(showfiles.size, file.getName(), showfiles.type, Files.getOwner(file.toPath()));
                        lista.add(fisier);
                        listacomparare.add(fisier);
                    } else {
                        ShowFiles fisier = new ShowFiles(showfiles.size, file.getName().substring(0, file.getName().lastIndexOf('.')), showfiles.type, Files.getOwner(file.toPath()));
                        lista.add(fisier);
                        listacomparare.add(fisier);
                    }
                }
                if (file.isDirectory()) {
                    ShowFiles fisier = new ShowFiles(0L, file.getName(), "directory", Files.getOwner(file.toPath()));
                    lista.add(fisier);
                    listacomparare.add(fisier);
                    // Thread-ul adauga directoarele intr-o alta lista
                    new Thread(() -> {
                        ShowFiles fisier1 = null;
                        try {
                            fisier1 = new ShowFiles(folderSize(file), file.getName(), "directory", Files.getOwner(file.toPath()));
                        } catch (IOException e) {
                        }
                        sizes.add(fisier1);
                    }).start();

                }
            }
            catch (Exception e){
            }
        }
    }
    /************************************************
     * Functia calculeaza size-ul directoarelor adunand
     * size-ul fiecarui fisier din interiorul directorului.
     ***********************************************/
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
    /************************************************
     * Functia afiseaza in tabel continutul listelor.
     * Pe thread-uri se inlocuieste in tabel un director dupa ce size-ul lui a fost calculat.
     ***********************************************/
    public void show(JTable table){
        calculateSize();
            int j = 1;
        String[] col = {"Index", "Name", "Owner", "Size", "Type"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        table.setModel(tableModel);
        table.getColumnModel().getColumn(0).setMaxWidth(40);

        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(70);
        Object[] rowData = new Object[5];
            for (int i = 0; i < lista.size(); i++) {
                    rowData[0] = j;
                    rowData[1] = lista.get(i).getName();
                    rowData[2] = lista.get(i).getOwner();
                    rowData[3] = lista.get(i).getSize();
                    rowData[4] = lista.get(i).getType();
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
                                    tableModel.setValueAt(sizes.get(k).getSize(), i, 3);
                                }
                            }
                        }
                    } while (sizes.size() != s);
                }
                catch (IndexOutOfBoundsException e){

                }
            }).start();
    }
    /************************************************
     * Functia care numara directoarele pentru a avea o conditie
     * de do-while.
     ***********************************************/
    public void calculateSize() {
        s = 0;
        for (File file : showfiles.listFiles()) {
            if (file.isDirectory()) {
                s++;
            }
        }
    }
    /************************************************
     * Functia care obtine path-ul unui element din tabel
     * atunci cand se click pe el.
     ***********************************************/
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
    /************************************************
     * Functie care obtine path-ul unui element din tabel atunci cand se da
     * click dreapta pe acesta.
     ***********************************************/
    public String getPathRightClick(JTable table, int j){
        String filename = table.getValueAt(j, 1).toString() + "." + table.getValueAt(j, 4);
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
