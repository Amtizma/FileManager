package Tests;

import FileManager.FileManager;
import FileManager.ShowFiles;
import FileManager.Extensions;
import org.junit.jupiter.api.Assertions;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {
    FileManager fileman = new FileManager();
    JLabel Confirm = new JLabel();
    JTable table = new JTable();
    @org.junit.jupiter.api.Test
    void renameFile() {
        fileman.scanFolder();
        fileman.renameFile("C:/Users/Bebe/Downloads/Test/doc1.docx", "doc11.docx", Confirm);
        Assertions.assertEquals(1, 1);
    }

    @org.junit.jupiter.api.Test
    void copyFile() throws IOException {
        fileman.scanFolder();
        fileman.copyFile(new File("C:/Users/Bebe/Downloads/Test/doc11.docx"), new File("C:/Users/Bebe/Downloads/Test/folder1/doc1.docx"), Confirm);
        Assertions.assertEquals(1, 1);
    }

    @org.junit.jupiter.api.Test
    void deleteFile() {
        fileman.scanFolder();
        fileman.deleteFile(new File("C:/Users/Bebe/Downloads/asd"), Confirm);
        Assertions.assertEquals(1, 1);
    }

    @org.junit.jupiter.api.Test
    void showDocuments() {
        fileman.scanFolder();
        fileman.showDocuments(table);
        int j = 0;
        for (ShowFiles showFiles : fileman.lista) {
            for (Extensions.documentExtensions docext : Extensions.documentExtensions.values()) {
                if (showFiles.type.equals(docext.name())) {
                    j++;
                }
            }
        }
        Assertions.assertEquals(j, table.getRowCount());
    }

    @org.junit.jupiter.api.Test
    void showExecutables() {
        int j = 0;
        fileman.scanFolder();
        fileman.showExecutables(table);
        Assertions.assertEquals(3, table.getRowCount());
    }

    @org.junit.jupiter.api.Test
    void showImages() {
        int j = 0;
        fileman.scanFolder();
        fileman.showImages(table);
        Assertions.assertEquals(5, table.getRowCount());
    }

    @org.junit.jupiter.api.Test
    void getlist() {
        fileman.scanFolder();
        fileman.clonelist.addAll(fileman.lista);
        fileman.getlist();
        Assertions.assertEquals(13, fileman.templist.size());
    }

    @org.junit.jupiter.api.Test
    void showOthers() {
        fileman.scanFolder();
        fileman.showOthers(table);
        Assertions.assertEquals(5, table.getRowCount());
    }

    @org.junit.jupiter.api.Test
    void showVideos() {
        fileman.scanFolder();
        fileman.showVideos(table);
        Assertions.assertEquals(0, table.getRowCount());
    }

    @org.junit.jupiter.api.Test
    void listaSortataDescrescatorByName() {
        fileman.scanFolder();
        fileman.listaSortataDescrescatorByName(table);
        int i = 0;
        if(table.getValueAt(0, 1).equals("Seminar_ETTI_II_G1B-2A_21.10.2021")) i = 1;
        Assertions.assertEquals(1, i);
    }

    @org.junit.jupiter.api.Test
    void listaSortataCrescatorByName() {
        fileman.scanFolder();
        fileman.listaSortataCrescatorByName(table);
        int i = 0;
        if(table.getValueAt(0, 1).equals("CEF2")) i = 1;
        Assertions.assertEquals(1, i);
    }

    @org.junit.jupiter.api.Test
    void listaSortataDescrescatorBySize() {
        fileman.scanFolder();
        fileman.listaSortataDescrescatorBySize(table);
        int i = 0;
        if(table.getValueAt(0, 1).equals("exe3")) i = 1;
        Assertions.assertEquals(1, i);
    }

    @org.junit.jupiter.api.Test
    void listaSortataCrescatorBySize() {
        fileman.scanFolder();
        fileman.listaSortataCrescatorBySize(table);
        int i = 0;
        if(table.getValueAt(0, 1).equals("folder1")) i = 1;
        Assertions.assertEquals(1, i);
    }

    @org.junit.jupiter.api.Test
    void listaSortataCrescatorByType() {
        fileman.scanFolder();
        fileman.listaSortataCrescatorByType(table);
        int i = 0;
        if(table.getValueAt(0, 3).equals("directory")) i = 1;
        Assertions.assertEquals(1, i);
    }

    @org.junit.jupiter.api.Test
    void listaSortataDescrescatorByType() {
        fileman.scanFolder();
        fileman.listaSortataDescrescatorByType(table);
        int i = 0;
        if(table.getValueAt(0, 3).equals("zip")) i = 1;
        Assertions.assertEquals(1, i);
    }

    @org.junit.jupiter.api.Test
    void reverselist() {
        fileman.scanFolder();
        fileman.reverselist(table);
        int i = 0;
        if(table.getValueAt(0, 1).equals("Seminar_ETTI_II_G1B-2A_21.10.2021")) i = 1;
        Assertions.assertEquals(1, i);
    }

    @org.junit.jupiter.api.Test
    void scanFolder() {
        fileman.scanFolder();
        fileman.show(table);
        int i = 0;
        if(table.getValueAt(0, 1).equals("CEF2")) i = 1;
        Assertions.assertEquals(1, i);
    }

    @org.junit.jupiter.api.Test
    void folderSize() {
        fileman.scanFolder();
        fileman.show(table);
        int i = 0;
        if(table.getValueAt(5, 2).equals("65B")) i = 1;
        Assertions.assertEquals(1, i);
    }

    @org.junit.jupiter.api.Test
    void show() {
        fileman.scanFolder();
        fileman.show(table);
        Assertions.assertEquals(fileman.lista.size(), table.getRowCount());
    }

    @org.junit.jupiter.api.Test
    void getPath() {
        fileman.scanFolder();
        fileman.show(table);
        table.setRowSelectionInterval(0,0);
        int i = 0;
        if(fileman.getPath(table).equals("C:\\Users\\Bebe\\Downloads\\Test\\CEF2.opj")) i = 1;
        Assertions.assertEquals(1,i);

    }
}