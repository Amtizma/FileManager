package FileManager;

import Interface.Authentification;

import java.io.File;

public class Test extends Authentification {
    public static void main(String[] args) throws Exception {
        Authentification d = new Authentification();
        d.ap.scanFolder();
        d.Auth();
                }
            }

