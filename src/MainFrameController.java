import javax.swing.*;
import GUI.Login;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrameController {
    JFrame loginFrame;
    public MainFrameController() {


    }

    public void initLoginWindow() {
        loginFrame = new JFrame("User Login");
        loginFrame.setContentPane(new Login().rootPanel);
        // size the window to obtain a best fit for the components
        loginFrame.pack();

        // center the frame
        Dimension d = loginFrame.getToolkit().getScreenSize();
        Rectangle r = loginFrame.getBounds();
        loginFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
        loginFrame.setVisible(true);

        // anonymous inner class for closing the window
        loginFrame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
    }
}
