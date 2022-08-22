package FileManager;

import java.io.File;


public class ShowFiles extends Extensions implements Comparable<ShowFiles>{
    public Long size;
    public String name;
    public String type;
    public String size1;
    File folder = new File("C:/Users/Bebe/Downloads");
    File[] listOfFiles = folder.listFiles();
    public ShowFiles() {
    }

    public ShowFiles(Long size, String name, String type) {
        this.size = size;
        this.name = name;
        this.type = type;
    }


    @Override
    public String toString() {
        if (size >= 1024) size1 = size/1024 + "KB";
        if (size < 1024) size1 = size + "B";
        return "Name: " + name + ", " +
                "Size: " + size1 + ", " +
                "Type: " + type;
    }

    @Override
    public int compareTo(ShowFiles o) {
        return Long.compare(o.size,this.size);
    }

    public String getSize() {
        if (size >= 1024) size1 = size/1024 + "KB";
        if (size < 1024) size1 = size + "B";
        return size1;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
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
