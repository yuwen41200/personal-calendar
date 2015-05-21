import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;

public class View {

    private Model model;
    private Controller controller;
    private String version;
    private JFrame frame;

    public void init(Model model, Controller controller, String version) {
        this.model = model;
        this.controller = controller;
        this.version = version;
    }

    public void mainWindow(Calendar calendar) {
        if (frame != null) frame.dispose();
        frame = new JFrame("Personal Calendar");
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JPanel leftPanel = new JPanel(new GridLayout(12, 1, 5, 5));
        JPanel rightPanel = new JPanel(new GridLayout(4, 7, 5, 5));
        JPanel[][] datePanels = new JPanel[4][7];
        JLabel[][] dateLabels = new JLabel[4][7];

        int todayMonth = calendar.get(Calendar.MONTH);
        int todayDate = calendar.get(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                String output = "<html>" + (todayMonth+1) + "/" + todayDate + "<br><br>";
                output += model.fetchDatabase(calendar) + "</html>";

                dateLabels[i][j] = new JLabel(output);
                dateLabels[i][j].setFont(new Font(Font.SERIF, Font.BOLD, 16));

                datePanels[i][j] = new JPanel(new FlowLayout(FlowLayout.LEFT));
                datePanels[i][j].setPreferredSize(new Dimension(150, 150));
                datePanels[i][j].setBackground(new Color(255, 219, 184));
                datePanels[i][j].add(dateLabels[i][j]);
                datePanels[i][j].addMouseListener(controller);

                rightPanel.add(datePanels[i][j]);

                calendar.add(Calendar.DATE, 1);
                todayMonth = calendar.get(Calendar.MONTH);
                todayDate = calendar.get(Calendar.DAY_OF_MONTH);
            }
        }

        calendar.add(Calendar.DATE, -28);

        JButton button1 = new JButton("<html><b><i>&lt;</i></b></html>");
        button1.setBorder(new RoundedBorder());
        button1.setActionCommand("moveBackward");
        button1.addActionListener(controller);

        JButton button2 = new JButton("<html><b><i>&gt;</i></b></html>");
        button2.setBorder(new RoundedBorder());
        button2.setActionCommand("moveForward");
        button2.addActionListener(controller);

        JButton button3 = new JButton("<html><b><i>+</i></b></html>");
        button3.setBorder(new RoundedBorder());
        button3.setActionCommand("showSubWindow2");
        button3.addActionListener(controller);

        JButton button4 = new JButton("<html><b><i>i</i></b></html>");
        button4.setBorder(new RoundedBorder());
        button4.setActionCommand("showSubWindow3");
        button4.addActionListener(controller);

        leftPanel.add(button1);
        leftPanel.add(button2);
        leftPanel.add(button3);
        leftPanel.add(button4);

        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        frame.add(panel);
        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void subWindow1() {

    }

    public void subWindow2() {

    }

    public void subWindow3() {
        String message = "<html><font size=+2><center>Personal Calendar</center></font>";
        message += "<center>By Yu-wen Pwu, NCTU CS</center>";
        message += "<center>" + version + "</center><br>";
        message += "This is an open source software, released under the MIT License.<br>";
        message += "More information: https://github.com/yuwen41200/personal-calendar</html>";
        JOptionPane.showMessageDialog(null, message, "About", JOptionPane.PLAIN_MESSAGE);
    }

}

class RoundedBorder implements Border {

    @Override
    public void paintBorder(Component component, Graphics graphics, int x, int y, int w, int h) {
        graphics.drawRoundRect(x, y, w-1, h-1, 16, 16);
    }

    @Override
    public Insets getBorderInsets(Component component) {
        return new Insets(17, 17, 18, 16);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }

}
