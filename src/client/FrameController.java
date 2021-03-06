package client;

import ui.ErrorDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class FrameController {
    protected void setFrameProperties(JFrame frame) {
        // size the window to obtain a best fit for the components
        frame.pack();

        // center the frame
        Dimension d = frame.getToolkit().getScreenSize();
        Rectangle r = frame.getBounds();
        frame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
        frame.setVisible(true);
    }

    public void createErrorDialog(String message) {
        JFrame errorFrame = new JFrame("Error");
        ErrorDialog errorDialog = new ErrorDialog();
        errorFrame.setContentPane(errorDialog.getRootPanel());
        errorDialog.getErrorLabel().setText(message);
        setFrameProperties(errorFrame);
        errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
