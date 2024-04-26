package source.userInterface;

import source.model.ShapeModel;

import javax.swing.*;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * UI to show Shape. Depends on ShapeModel interface.
 */

public class GUI {
    private static final int SIZE_X = 79;
    private static final int SIZE_Y = 24;
    private final ShapeModel model;
    private final PicturePanel picturePanel;
    private JFrame frame;
    private File lastJsonFile;
    private boolean isRedacted = false;

    public GUI(ShapeModel model) {
        this.model = model;
        picturePanel = new PicturePanel(SIZE_X, SIZE_Y);
    }

    public void start() {
        frame = new JFrame();
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.add(makeButtonPanel());

        frame.add(picturePanel);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private JPanel makeButtonPanel() {
        JButton open = new JButton("Load From JSON...");
        open.addActionListener((e) -> {
            openJsonFile();
        });

        JButton redact = new JButton("Redact");
        redact.addActionListener((e) -> {
            try {
                model.redact();
            } catch (Exception ex) {
                errorBox("ERROR: Uncaught exception redacting file.", ex);
            }
            picturePanel.redraw(model.iterator());
            isRedacted = true;
        });

        JButton save = new JButton("Export to file");
        save.addActionListener((e) -> {
            if (lastJsonFile == null) {
                errorBox("Please load a picture from JSON file before exporting.");
            }
            String redactString = isRedacted ? " (redacted)" : "";
            String name = "Picture -- " + lastJsonFile.getName() + redactString + ".txt";
            picturePanel.writePictureToFile(name);
        });

        JPanel panel = new JPanel();
        panel.add(open);
        panel.add(redact);
        panel.add(save);

        return panel;
    }

    private void openJsonFile() {
        // Source: https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
        // Handle open button action.
        final JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("."));
        int returnVal = fc.showOpenDialog(frame);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            lastJsonFile = fc.getSelectedFile();
            isRedacted = false;

            try {
                model.populateFromJSON(lastJsonFile);
            } catch (Exception e) {
                errorBox("ERROR: Uncaught exception loading from file.", e);
            }
            picturePanel.redraw(model.iterator());
        } else {
            // Cancelled.
        }
    }

    private void errorBox(String s) {
        JOptionPane.showMessageDialog(frame, s);
    }

    private void errorBox(String s, Exception e) {
        // SOURCE: https://www.baeldung.com/java-stacktrace-to-string
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);

        e.printStackTrace();
        JOptionPane.showMessageDialog(frame, s + "\n" + sw);
    }
}

