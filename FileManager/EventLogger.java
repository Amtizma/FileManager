package FileManager;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class EventLogger {
    int numberoflines = 500;
    // Functie care face scrierea in fisier a evenimentului
    public void eventLogger(String id, String event) throws IOException {
        String timeStamp = new SimpleDateFormat("dd.MM.yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
        try {
            countLines();
            FileWriter myWriter = new FileWriter("eventlogger.txt", true);
            myWriter.write("["+timeStamp + "] " + "User" + " " + id + " " + event + "\n");
            myWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Functie care numara liniile si sterge ce este peste 500)
    public void countLines() throws IOException {
        int lines = 1;
        try {
            BufferedReader b = new BufferedReader(new FileReader("eventlogger.txt"));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                lines++;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(lines>numberoflines) {
            File path = new File("eventlogger.txt");
            Scanner scanner = new Scanner(path);
            ArrayList<String> data = new ArrayList<String>();
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                data.add(line);
            }

            scanner.close();

            FileWriter writer = new FileWriter(path);
            for (String line : data) {
                writer.write(line + "\n");
            }

            writer.close();
        }
    }
}
