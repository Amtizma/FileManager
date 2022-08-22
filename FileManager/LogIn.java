package FileManager;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class LogIn {
    boolean verifydata = false;
    ArrayList<String> permissions = new ArrayList<>();
    public MD5 md5 = new MD5();
    final String txtname = "userdata.txt";
    // Functia de logIn
    public void logIn(String id, String password, JLabel Confirmation) {
        try {
            BufferedReader b = new BufferedReader(new FileReader(txtname));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                String sir[] = readLine.split(",");
                if (sir[0].equals(id) && sir[1].equals(md5.getHash(password))) {
                    for (int i = 2; i < sir.length; i++) {
                        permissions.add(sir[i]);
                    }
                    Confirmation.setText("Authentification succesfull. ");
                    verifydata = true;
                    break;
                } else {
                    verifydata = false;
                }
            }
            if (!verifydata) Confirmation.setText("Incorrect ID or Password. ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verifyPermissions(String permis) {
        boolean perm = false;
        for (String permission : permissions) {
            if (permission.equals(permis)) {
                perm = true;
                break;
            }
        }
        return perm;
    }
}
