package Tests;

import FileManager.SignUp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


class SignUpTest {

    SignUp su = new SignUp();
    JLabel Confirm = new JLabel();
    JTable table = new JTable();
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UserData?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
            "root", "048498");

    SignUpTest() throws SQLException {
    }

    @Test
    void signUpDB() throws NoSuchAlgorithmException, SQLException {
        String id = "asd";
        su.signUpDB("000", "000", Confirm);
        String selectSql =  "select Username from usersdata where Username = " + "'" + "000" + "'" + " and Password = " + "'" +su.md5.getHash("000") + "'";
        PreparedStatement preparedStmt = conn.prepareStatement(selectSql);
        ResultSet result = preparedStmt.executeQuery();
       while(result.next()){
           id = result.getString("Username");
       }
        Assertions.assertEquals(id, "000");
    }

    @Test
    void logInDB() {
        su.logInDB("222", "222", Confirm);
        Assertions.assertEquals(Confirm.getText(), "Authentification succesfull. ");
    }

    @Test
    void showUsersDB() {
        su.showUsersDB(table);
        int i = table.getRowCount();
        Assertions.assertEquals(4, i);
    }

    @Test
    void changePermissionsDB() throws SQLException, IOException, NoSuchAlgorithmException {
        String permissions = "asd";
        su.logInDB("admin","admin",Confirm);
        su.changePermissionsDB("222", "222", "delete", Confirm, true);
        String selectSql =  "select * from usersdata where Username = " + "'" + "222" + "'" + " and Password = " + "'" +su.md5.getHash("222") + "'";
        PreparedStatement preparedStmt = conn.prepareStatement(selectSql);
        ResultSet result = preparedStmt.executeQuery();
        if(result.next()){
            permissions = result.getString(3);
        }
        Assertions.assertEquals("delete,sortsize,sortname,sorttype,viewdoc,viewexe,viewimg,viewvid,viewoth,rename,copy", permissions);

    }

    @Test
    void removeUserDB() throws Exception {
        String id = "asd";
        su.removeUserDB("000", "000", Confirm);
        String selectSql2 =  "select * from usersdata where Username = " + "'" + "000" + "'" + " and Password = " + "'" + su.md5.getHash("000") + "'";
        PreparedStatement statement2 =  conn.prepareStatement(selectSql2);
        ResultSet rs2 = statement2.executeQuery();
        while(rs2.next()){
            id = rs2.getString("Username");
        }
        Assertions.assertNotEquals(id, "000");
    }

    @Test
    void verifyPermissions() {
        su.logInDB("admin", "admin", Confirm);
        Assertions.assertTrue(su.verifyPermissions("delete"));
    }
}