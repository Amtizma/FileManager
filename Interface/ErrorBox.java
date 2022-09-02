package Interface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorBox extends CopyFileView {
    JFrame errorbox = new JFrame("Error");
    private JButton erroreturn;
    private JLabel errortext;
    private JPanel errorpanel;
    /************************************************
     * Listener pentru butonul de return. Dispare fereastra de eroare.
     ***********************************************/
    public ErrorBox() {
        erroreturn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorbox.dispose();
            }
        });
    }
    /************************************************
     * Functia care deschide interfata.
     ***********************************************/
    public void ErrorBox(){
        errorbox.setContentPane(errorpanel);
        errorbox.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        errorbox.setLocationRelativeTo(null);
        errorbox.setResizable(true);
        errorbox.pack();
        errorbox.setVisible(true);
    }
}
