package FileManager;

import java.io.File;
import java.nio.file.attribute.UserPrincipal;

/************************************************
 * Clasa ShowFiles contine modelul in functie de care
 * obiectele se adauga in lista.
 ***********************************************/
public class ShowFiles extends Extensions implements Comparable<ShowFiles>{
    public Long size;
    public String name;
    public String type;
    public String size1;
    public UserPrincipal owner;
    File folder = new File("C:/Users/Bebe/Downloads");
    File[] listOfFiles = folder.listFiles();
    public ShowFiles() {
    }
    /************************************************
     * Constuctorul clasei.
     ***********************************************/
    public ShowFiles(Long size, String name, String type, UserPrincipal owner) {
        this.size = size;
        this.name = name;
        this.type = type;
        this.owner = owner;
    }


    @Override
    public String toString() {
        if (size >= 1024) size1 = size/1024 + "KB";
        if (size < 1024) size1 = size + "B";
        return "Name: " + name + ", " +
                "Size: " + size1 + ", " +
                "Type: " + type;
    }

    /************************************************
     * Override la metoda de compareTo pentru a sorta
     * in functie de size.
     ***********************************************/
    @Override
    public int compareTo(ShowFiles o) {
        return Long.compare(o.size,this.size);
    }

    /************************************************
     * Functia getSize are rolul de a afisa mai elegant size-ul
     * unui fisier.
     ***********************************************/
    public String getSize() {
        if (size >= 1024) size1 = size/1024 + "KB";
        if (size < 1024) size1 = size + "B";
        return size1;
    }

    /************************************************
     * Getteri si setteri.
     ***********************************************/
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public UserPrincipal getOwner() {
        return owner;
    }
    public File getFolder(){
        return folder;
    }
    public void setFolder(File folder1){
        folder = folder1;
    }
   public File[] listFiles(){
       return folder.listFiles();
   }
}
