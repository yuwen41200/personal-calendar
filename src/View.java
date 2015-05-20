import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;

public class View {

    private Controller controller;
    private String version;

    public void mainWindow() {
        JFrame frame = new JFrame("Personal Calendar");
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JPanel leftPanel = new JPanel(new GridLayout(12, 1, 5, 5));
        JPanel rightPanel = new JPanel(new GridLayout(4, 7, 5, 5));
        JPanel[][] datePanels = new JPanel[4][7];
        JLabel[][] dateLabels = new JLabel[4][7];
        Calendar calendar = Calendar.getInstance();
        int todayMonth = calendar.get(Calendar.MONTH);
        int todayDate = calendar.get(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                datePanels[i][j] = new JPanel(new FlowLayout(FlowLayout.LEFT));
                dateLabels[i][j] = new JLabel((todayMonth+1) + "/" + todayDate);
                dateLabels[i][j].setFont(new Font(Font.SERIF, Font.BOLD, 16));
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
        JButton button1 = new JButton("<html><b><i>+</i></b></html>");
        button1.setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.drawRoundRect(x, y, width - 1, height - 1, 16, 16);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(17, 17, 18, 16);
            }

            @Override
            public boolean isBorderOpaque() {
                return true;
            }
        });
        button1.setActionCommand("showSubWindow2");
        button1.addActionListener(controller);
        JButton button2 = new JButton("<html><b><i>i</i></b></html>");
        button2.setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                g.drawRoundRect(x, y, width-1, height-1, 16, 16);
            }
            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(17, 17, 18, 16);
            }
            @Override
            public boolean isBorderOpaque() {
                return true;
            }
        });
        button2.setActionCommand("showSubWindow3");
        button2.addActionListener(controller);
        leftPanel.add(button1);
        leftPanel.add(button2);
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);
        frame.add(panel);
        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void subWindow3() {
        String message = "<html><font size=+2><center>Personal Calendar</center></font><br>";
        message += "<center>" + version + "</center><br>";
        message += "This is an open source software, released under the MIT License.<br>";
        message += "More information: https://github.com/yuwen41200/personal-calendar</html>";
        JOptionPane.showMessageDialog(null, message, "About", JOptionPane.PLAIN_MESSAGE);
    }

    public void init(Controller controller, String version) {
        this.controller = controller;
        this.version = version;
    }

}
