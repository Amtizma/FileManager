package FileManager;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Arrays;

public class SignUp extends LogIn{
    final String connection = "jdbc:mysql://localhost:3306/UserData?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";

    // Functie de signUp cu fisier
    public void signUp(String id, String password, JLabel Confirmation) {
        try {
            if(!id.isEmpty() && !password.isEmpty()) {
                boolean taken = false;
                FileWriter myWriter = new FileWriter(txtname, true);
                BufferedReader b = new BufferedReader(new FileReader(txtname));
                String readLine = "";
                while ((readLine = b.readLine()) != null) {
                    String[] sir = readLine.split(",");
                    if (sir[0].equals(id)) {
                        Confirmation.setText("Username is already taken. ");
                        taken = true;
                        break;
                    }
                }
                if (!taken) {
                    myWriter.write(id + "," + md5.getHash(password) + ",sortsize" + ",sortname" + ",sorttype" + ",viewdoc" + ",viewexe" + ",viewimg" + ",viewvid" + ",viewoth" + ",rename" + ",copy" + "\n");
                    Confirmation.setText("You have successfully signed up. ");
                    myWriter.close();
                }
            }
            else Confirmation.setText("Username or/and password can't be null. ");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Functia de signUp pentru baza de date
    public void signUpDB(String id, String password, JLabel Confirmation){
        double value=0.0;
        if(!id.isEmpty() && !password.isEmpty()) {
            try {

                Connection conn = DriverManager.getConnection(connection,
                        "root", "048498");
                Statement stmt = conn.createStatement();
                String selectSql = "insert into usersdata(Username, Password, Permissions)" + "values(" + "'" + id + "'" + "," + "'" + md5.getHash(password) + "'" + "," + "'sortsize,sortname,sorttype,viewdoc,viewexe,viewimg,viewvid,viewoth,rename,copy'" + ")" + ";";
                PreparedStatement preparedStmt = conn.prepareStatement(selectSql);
                PreparedStatement statement =  conn.prepareStatement("SELECT Username,SUM(Username =  " + "'" + id + "'" + ")"  + "AS cnt\n" +
                        "FROM usersdata\n" +
                        "GROUP BY Username\n" +
                        "HAVING (cnt >= 1)");
                ResultSet result = statement.executeQuery();
                result.next();
                String sum = "as";
                try{
                    sum = result.getString(2);
                }
                catch (Exception e){
                    sum = "0";
                }
                value = Double.parseDouble(sum);
                if(value>=1) {
                    Confirmation.setText("Username is already taken. ");
                }
                else{
                    preparedStmt.execute();
                    Confirmation.setText("You have successfully signed up. ");
                }
            } catch (SQLException | NoSuchAlgorithmException throwables) {
                throwables.printStackTrace();
            }
        }
        else Confirmation.setText("Username or/and password can't be null. ");
    }
    // Functie de log in pentru baza de date
    public void logInDB(String id, String password, JLabel Confirmation) {
        try {
            Connection conn = DriverManager.getConnection(connection,
                    "root", "048498");
            String selectSql =  "select * from usersdata where Username = " + "'" + id + "'" + " and Password = " + "'" + md5.getHash(password) + "'";
            PreparedStatement statement =  conn.prepareStatement(selectSql);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
               String permission = rs.getString(3);
                String[] sir = permission.split(",");
                permissions.addAll(Arrays.asList(sir));
                Confirmation.setText("Authentification succesfull. ");
            }
            else Confirmation.setText("Incorrect ID or Password. ");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Functie pentru afisarea user-ilor din baza de date
    public void showUsersDB(JTable table){
        try {
            Connection conn = DriverManager.getConnection(connection,
                    "root", "048498");
            String selectSql =  "select * from usersdata";
            PreparedStatement statement =  conn.prepareStatement(selectSql);
            ResultSet rs = statement.executeQuery();
            String[] col = {"Index", "ID", "Password"};
            DefaultTableModel tableModel = new DefaultTableModel(col, 0);
            table.setModel(tableModel);
            table.getColumnModel().getColumn(0).setMaxWidth(40);
            table.getColumnModel().getColumn(1).setMaxWidth(50);
            Object[] rowData = new Object[3];
            int j = 1;;
            while (rs.next()) {
                rowData[0] = j;
                rowData[1]= rs.getString(1);
                rowData[2] = rs.getString(2);
                tableModel.addRow(rowData);
                j++;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    // Functie pentru schimbare permisiunilor unui utilizator (permissionvalue = true - adauga permisiuni, false - stergere permisiuni)
    public void changePermissionsDB(String id, String password, String permissions1, JLabel Confirm, boolean permissionvalue) throws IOException, NoSuchAlgorithmException, SQLException {
        boolean permissionexist1 = false;
        boolean permissionexists1 = false;
        Connection conn = DriverManager.getConnection(connection,
                "root", "048498");
        if(id.equals("admin")) Confirm.setText("You can't change admin's permissions.");
        else {
            boolean perm = false;
            for (String permission : permissions) {
                if (permission.equals("changepermission")) {
                    perm = true;
                    break;
                }
            }
            for (Extensions.permissionsList permlist : Extensions.permissionsList.values()) {
                if (permissions1.equals(permlist.name())) {
                    permissionexist1 = true;
                    break;
                }
            }
            String selectSql2 =  "select * from usersdata where Username = " + "'" + id + "'" + " and Password = " + "'" + md5.getHash(password) + "'";
            PreparedStatement statement2 =  conn.prepareStatement(selectSql2);
            ResultSet rs2 = statement2.executeQuery();
            if(rs2.next()) {
                if (perm && permissionvalue && permissionexist1) {
                    String selectSql1 = "SELECT * from usersdata where Username = " + "'" + id + "'" + "and permissions LIKE '%" + permissions1 + "%';";
                    PreparedStatement statement1 = conn.prepareStatement(selectSql1);
                    ResultSet rs1 = statement1.executeQuery();
                    if (rs1.next()) {
                        permissionexists1 = true;
                        Confirm.setText("Permission already exists. ");
                    }
                }
                if (perm && permissionvalue && permissionexist1 && !permissionexists1) {
                    String selectSql = "UPDATE usersdata SET permissions = CONCAT(" + "'" + permissions1 + "," + "'" + "," + "permissions) WHERE Username = " + "'" + id + "'";
                    PreparedStatement statement = conn.prepareStatement(selectSql);
                    int rs = statement.executeUpdate();
                    Confirm.setText("Permission added. ");
                }
                if (perm && !permissionvalue && permissionexist1) {
                    String selectSql1 = "UPDATE usersdata SET permissions = REPLACE(permissions," + "'" + permissions1 +"," +"'" + ",'') WHERE Username = " + "'" + id + "'";
                    PreparedStatement statement1 = conn.prepareStatement(selectSql1);
                    int rs1 = statement1.executeUpdate();
                    Confirm.setText("Permission deleted. ");
                }
                if (!permissionexist1) Confirm.setText("Permission doesn't exist. ");
            }
            else Confirm.setText("User doesn't exist. ");
            permissions.clear();
            String selectSql4 =  "select * from usersdata where Username = " + "'" + id + "'" + " and Password = " + "'" + md5.getHash(password) + "'";
            PreparedStatement statement4 =  conn.prepareStatement(selectSql4);
            ResultSet rs4 = statement4.executeQuery();
            if(rs4.next()) {
                String permission = rs4.getString(3);
                String[] sir = permission.split(",");
                permissions.addAll(Arrays.asList(sir));
            }
        }
        conn.close();
    }
    // Functie pentru stergerea unui user din baza de date
    public void removeUserDB(String id, String password, JLabel Confirmation) throws Exception {
        Connection conn = DriverManager.getConnection(connection,
                "root", "048498");
        if(id.equals("admin")) Confirmation.setText("You can't remove admin");
        else {
            String selectSql2 =  "select * from usersdata where Username = " + "'" + id + "'" + " and Password = " + "'" + md5.getHash(password) + "'";
            PreparedStatement statement2 =  conn.prepareStatement(selectSql2);
            ResultSet rs2 = statement2.executeQuery();
            if(rs2.next()) {
               String selectSql = "DELETE FROM usersdata where Username = " + "'" + id + "'" + "and Password = " + "'" + md5.getHash(password) + "'" + ";";
                PreparedStatement statement =  conn.prepareStatement(selectSql);
                int rs = statement.executeUpdate();
               Confirmation.setText("User deleted");
            } else Confirmation.setText("User doesn't exist. ");
        }
        conn.close();
    }
    // Functie pentru schimbarea parolei unui user din baza de date
    public void changePasswordDB(String id, String password, String newpassword, JLabel Confirmation) throws Exception {
        Connection conn = DriverManager.getConnection(connection,
                "root", "048498");
        if(id.equals("admin")) Confirmation.setText("You can't change admin's password");
        else {
            String selectSql2 =  "select * from usersdata where Username = " + "'" + id + "'" + " and Password = " + "'" + md5.getHash(password) + "'";
            PreparedStatement statement2 =  conn.prepareStatement(selectSql2);
            ResultSet rs2 = statement2.executeQuery();
            if(rs2.next()) {
                String selectSql = "UPDATE usersdata SET Password = REPLACE(Password," + "'" + md5.getHash(password) +"'"  + "," + "'" + md5.getHash(newpassword) + "'" + ") WHERE Username = " + "'" + id + "';";
                PreparedStatement statement =  conn.prepareStatement(selectSql);
                int rs = statement.executeUpdate();
                Confirmation.setText("Password changed");
            } else Confirmation.setText("User doesn't exist. ");
        }
        conn.close();
    }

    public SignUp() {

    }
    // Functia de log in pentru fisier
    public void logIn(String id, String password, JLabel Confirmation) {
        boolean verifydata = false;
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

    // Functia pentru afisarea user-ilor din fisier
    public void showUsers(JTable table){
        try {
            String[] col = {"Index", "ID", "Password"};
            DefaultTableModel tableModel = new DefaultTableModel(col, 0);
            table.setModel(tableModel);
            table.getColumnModel().getColumn(0).setMaxWidth(40);
            table.getColumnModel().getColumn(1).setMaxWidth(50);
            Object[] rowData = new Object[3];
            int j = 1;
            BufferedReader b = new BufferedReader(new FileReader(txtname));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                String sir[] = readLine.split(",");
                rowData[0] = j;
                rowData[1]= sir[0];
                rowData[2] = sir[1];
                tableModel.addRow(rowData);
                j++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } }
    // Boolean-uri folosite pentru schimbarea permisiunilor
    boolean verifydata2 = false;
    String userinfo = "asd";
    boolean permissionexist = false;
    boolean duplicate = false;
    boolean permissionexists = false;

    // Functie care gaseste linia pentru un user precizat (folosita pentru changePermissions)
    public void findLine(String id, String password) {
        try {
            BufferedReader b = new BufferedReader(new FileReader(txtname));
            String readLine;
            while ((readLine = b.readLine()) != null) {
                String[] sir = readLine.split(",");
                if (sir[0].equals(id) && sir[1].equals(md5.getHash(password))) {
                    userinfo = readLine.trim();
                    verifydata2 = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Functie intermediara care creeaza un fisier temporar
    public void changePermissionsPartially(String perm, boolean permissionvalue) throws IOException {
        File inputFile = new File(txtname);
        File tempFile = new File("temp.txt");
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String currentLine1;
        while ((currentLine1 = reader.readLine()) != null) {
            String trimmedLine = currentLine1.trim();
            if (trimmedLine.equals(userinfo) && permissionvalue) {
                String[] sir1 = userinfo.split(",");
                for (String s : sir1) {
                    if (s.equals(perm)) {
                        duplicate = true;
                        break;
                    } else duplicate = false;
                }
                for (Extensions.permissionsList permlist : Extensions.permissionsList.values()) {
                    if (perm.equals(permlist.name()) && !duplicate) {
                        currentLine1 = trimmedLine + "," + perm;
                        permissionexist = true;
                        break;
                    } else permissionexist = false;

                }
            }
            if (trimmedLine.equals(userinfo) && !permissionvalue) {
                String[] sir1 = userinfo.split(",");
                for (String s : sir1) {
                    if (s.equals(perm)) {
                        permissionexists = true;
                        break;
                    } else permissionexists = false;
                }
                for (Extensions.permissionsList permlist : Extensions.permissionsList.values()) {
                    if (perm.equals(permlist.name())) {
                        currentLine1 = trimmedLine.replace("," + perm, "");
                        permissionexist = true;
                        break;
                    } else permissionexist = false;

                }
            }
            writer.write(currentLine1 + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        tempFile.deleteOnExit();
    }

    // Functie finala de schimbare de permisiuni pentru fisier
    public void changePermissions(String id, String password, String permissions1, JLabel Confirm, boolean permissionvalue) throws IOException, NoSuchAlgorithmException {
        if(id.equals("admin")) Confirm.setText("You can't change admin's permissions.");
        else {
            boolean perm = false;
            for (String permission : permissions) {
                if (permission.equals("changepermission")) {
                    perm = true;
                    findLine(id, password);
                    changePermissionsPartially(permissions1, permissionvalue);
                    if (verifydata2) {
                        File tempFile = new File(txtname);
                        File inputFile = new File("temp.txt");
                        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                        String currentLine;
                        while ((currentLine = reader.readLine()) != null) {
                            writer.write(currentLine + System.getProperty("line.separator"));
                        }
                        writer.close();
                        reader.close();
                        inputFile.deleteOnExit();
                        if (permissionvalue && permissionexist && !duplicate) Confirm.setText("Permission added. ");
                        if (!permissionvalue && permissionexist && permissionexists)
                            Confirm.setText("Permission removed. ");
                        if (!permissionexist && !duplicate) Confirm.setText("Permission doesn't exist. ");
                        if (!permissionvalue && !permissionexists) Confirm.setText("Permission doesn't exist. ");
                        if (duplicate) Confirm.setText("Permission already exists. ");
                    } else Confirm.setText("User doesn't exist. ");

                }
            }
            permissions.clear();
            File inputFile = new File(txtname);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] sir = currentLine.split(",");
                if (sir[0].equals(id) && sir[1].equals(md5.getHash(password))) {
                    for (int i = 2; i < sir.length; i++) {
                        permissions.add(sir[i]);
                    }
                }
            }
            if (!perm) System.out.println("You don't have the permission to do that. ");
        }
    }
    // Functie intermediara pentru eliminarea unui user care creeaza un fisier temporar
    public void removeUserPartially() throws IOException {
        File inputFile = new File(txtname);
        File tempFile = new File("myTempFile.txt");
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            if (trimmedLine.equals(userinfo)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
    }
    //Functie pentru eliminarea unui user
    public void removeUser(String id, String password, JLabel Confirmation) throws Exception {
        if(id.equals("admin")) Confirmation.setText("You can't remove admin");
        else {
            findLine(id, password);
            removeUserPartially();
            if (verifydata2) {
                File tempFile = new File(txtname);
                File inputFile = new File("myTempFile.txt");
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
                writer.close();
                reader.close();
                inputFile.deleteOnExit();
                Confirmation.setText("User deleted. ");
            } else Confirmation.setText("User doesn't exist. ");
        }
    }
    // Functie care verifica permisiunile
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
    // Functie intermediara pentru schimbarea parolei
    public void changePasswordPartially(String newPassword) throws IOException, NoSuchAlgorithmException {
        File inputFile = new File(txtname);
        File tempFile = new File("myTempFile1.txt");
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            if (trimmedLine.equals(userinfo)){
                String[] sir1 = userinfo.split(",");
                currentLine = trimmedLine.replace(sir1[1], md5.getHash(newPassword));
            }
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
    }
    // Functie pentru schimbarea parolei
    public void changePassword(String id, String password, String newpassword, JLabel Confirmation) throws Exception {
        if(id.equals("admin")) Confirmation.setText("You can't change admin's password");
        else {
            findLine(id, password);
            changePasswordPartially(newpassword);
            if (verifydata2) {
                File tempFile = new File(txtname);
                File inputFile = new File("myTempFile1.txt");
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
                writer.close();
                reader.close();
                inputFile.deleteOnExit();
                Confirmation.setText("Password changed. ");
            } else Confirmation.setText("User doesn't exist. ");
        }
    }

}
