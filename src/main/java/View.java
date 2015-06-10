import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;

public class View {

    private Model model;
    private Controller controller;
    private String version;
    private JDatePanel[][] datePanels;
    private JLabel[][] dateLabels;
    private JFrame subFrame1;

    public void init(Model model, Controller controller, String version) {
        this.model = model;
        this.controller = controller;
        this.version = version;
    }

    public void mainWindow(Calendar calendar) {
        JFrame mainFrame = new JFrame("Personal Calendar");
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JPanel leftPanel = new JPanel(new GridLayout(12, 1, 5, 5));
        JPanel rightPanel = new JPanel(new GridLayout(4, 7, 5, 5));
        datePanels = new JDatePanel[4][7];
        dateLabels = new JLabel[4][7];

        int todayMonth = calendar.get(Calendar.MONTH);
        int todayDate = calendar.get(Calendar.DAY_OF_MONTH);
        String todayDay = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                String output = "<html><b>" + (todayMonth+1) + "/" + todayDate;
                output += " " + todayDay + "</b><br><br>";
                ArrayList<String> results = model.fetchDatabase(calendar);
                for (String result : results)
                    output += result + "<br>";
                output += "</html>";

                dateLabels[i][j] = new JLabel(output);
                dateLabels[i][j].setFont(new Font(Font.SERIF, Font.PLAIN, 16));

                datePanels[i][j] = new JDatePanel(new FlowLayout(FlowLayout.LEFT), (Calendar) calendar.clone());
                datePanels[i][j].setPreferredSize(new Dimension(150, 150));
                datePanels[i][j].setBackground(new Color(255, 219, 184));
                datePanels[i][j].add(dateLabels[i][j]);
                datePanels[i][j].addMouseListener(controller);

                rightPanel.add(datePanels[i][j]);

                calendar.add(Calendar.DATE, 1);
                todayMonth = calendar.get(Calendar.MONTH);
                todayDate = calendar.get(Calendar.DAY_OF_MONTH);
                todayDay = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
            }
        }

        calendar.add(Calendar.DATE, -28);

        JButton button1 = new JButton("<html><b><i>&lt;</i></b></html>");
        button1.setBorder(new RoundedBorder());
        button1.setToolTipText("Move Backward");
        button1.setActionCommand("Move Backward");
        button1.addActionListener(controller);

        JButton button2 = new JButton("<html><b><i>&gt;</i></b></html>");
        button2.setBorder(new RoundedBorder());
        button2.setToolTipText("Move Forward");
        button2.setActionCommand("Move Forward");
        button2.addActionListener(controller);

        JButton button3 = new JButton("<html><b><i>+</i></b></html>");
        button3.setBorder(new RoundedBorder());
        button3.setToolTipText("Synchronize Google Calendar");
        button3.setActionCommand("Synchronize Google Calendar");
        button3.addActionListener(controller);

        JButton button4 = new JButton("<html><b><i>i</i></b></html>");
        button4.setBorder(new RoundedBorder());
        button4.setToolTipText("About");
        button4.setActionCommand("About");
        button4.addActionListener(controller);

        leftPanel.add(button1);
        leftPanel.add(button2);
        leftPanel.add(button3);
        leftPanel.add(button4);

        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        mainFrame.add(panel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    public void mainWindowRender(Calendar calendar) {
        int todayMonth = calendar.get(Calendar.MONTH);
        int todayDate = calendar.get(Calendar.DAY_OF_MONTH);
        String todayDay = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                String output = "<html><b>" + (todayMonth + 1) + "/" + todayDate;
                output += " " + todayDay + "</b><br><br>";
                ArrayList<String> results = model.fetchDatabase(calendar);
                for (String result : results)
                    output += result + "<br>";
                output += "</html>";

                dateLabels[i][j].setText(output);
                datePanels[i][j].setCalendar((Calendar) calendar.clone());

                calendar.add(Calendar.DATE, 1);
                todayMonth = calendar.get(Calendar.MONTH);
                todayDate = calendar.get(Calendar.DAY_OF_MONTH);
                todayDay = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
            }
        }

        calendar.add(Calendar.DATE, -28);
    }

    public void subWindow1(Calendar calendar) {
        if (subFrame1 != null) subFrame1.dispose();
        subFrame1 = new JFrame("View Daily Schedule");
        ArrayList<String> results = model.fetchDatabase(calendar);
        int resultCount = (results.size() > 2) ? results.size() : 2;
        String date = model.calendarToStr(calendar);

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JPanel leftPanel = new JPanel(new GridLayout(resultCount, 1, 5, 5));
        JPanel rightPanel = new JPanel(new GridLayout(resultCount, 1, 5, 5));
        JPanel[] eventPanels = new JPanel[resultCount];
        JLabel[] eventLabels = new JLabel[resultCount];
        JButton[] eventButtons1 = new JButton[resultCount];
        JButton[] eventButtons2 = new JButton[resultCount];

        int i = 0;
        for (String result : results) {
            eventButtons1[i] = new JButton("<html><b><i>&#x2713;</i></b></html>");
            eventButtons1[i].setToolTipText("Remove Event");
            eventButtons1[i].setActionCommand("Remove Event " + date + " " + i);
            eventButtons1[i].addActionListener(controller);

            eventButtons2[i] = new JButton("<html><b><i>=</i></b></html>");
            eventButtons2[i].setToolTipText("Modify Event");
            eventButtons2[i].setActionCommand("Modify Event " + date + " " + i + " %" + result);
            eventButtons2[i].addActionListener(controller);

            eventLabels[i] = new JLabel(result);
            eventLabels[i].setFont(new Font(Font.SERIF, Font.PLAIN, 16));

            eventPanels[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
            eventPanels[i].setBackground(new Color(255, 219, 184));
            eventPanels[i].setBorder(new EmptyBorder(5, 5, 5, 5));
            eventPanels[i].add(eventButtons1[i]);
            eventPanels[i].add(eventButtons2[i]);
            eventPanels[i].add(eventLabels[i]);

            rightPanel.add(eventPanels[i]);
            ++i;
        }

        int todayDate = calendar.get(Calendar.DAY_OF_MONTH);
        String output = "<html><b><i>" + todayDate + "</i></b></html>";
        JLabel label = new JLabel(output);
        label.setBorder(new RoundedBorder());

        JButton button = new JButton("<html><b><i>+</i></b></html>");
        button.setBorder(new RoundedBorder());
        button.setToolTipText("Add Event");
        button.setActionCommand("Add Event " + date);
        button.addActionListener(controller);

        leftPanel.add(label);
        leftPanel.add(button);

        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        subFrame1.add(panel);
        subFrame1.pack();
        subFrame1.setLocationRelativeTo(null);
        subFrame1.setResizable(false);
        subFrame1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        subFrame1.setVisible(true);
    }

    public void subWindow2() {
        JFrame subFrame2 = new JFrame("Synchronize Google Calendar");
        ArrayList<String> results = model.fetchGoogleCalendar();
        int resultCount = results.size();

        if (resultCount == 0) {
            String message = "No upcoming events found.";
            JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new GridLayout(resultCount, 1, 5, 5));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JPanel[] eventPanels = new JPanel[resultCount];
        JLabel[] eventLabels = new JLabel[resultCount];
        JButton[] eventButtons = new JButton[resultCount];

        int i = 0;
        for (String result : results) {
            eventButtons[i] = new JButton("<html><b><i>+</i></b></html>");
            eventButtons[i].setToolTipText("Add Google Calendar Event");
            eventButtons[i].setActionCommand("Add Google Calendar Event " + result);
            eventButtons[i].addActionListener(controller);

            String output =
                    result.replaceFirst("-", "/").replaceFirst("-", "/").replaceFirst("%", "");
            eventLabels[i] = new JLabel(output);
            eventLabels[i].setFont(new Font(Font.SERIF, Font.PLAIN, 16));

            eventPanels[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
            eventPanels[i].setBackground(new Color(255, 219, 184));
            eventPanels[i].setBorder(new EmptyBorder(5, 5, 5, 5));
            eventPanels[i].add(eventButtons[i]);
            eventPanels[i].add(eventLabels[i]);

            panel.add(eventPanels[i]);
            ++i;
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        subFrame2.add(scrollPane);
        subFrame2.pack();
        subFrame2.setSize(new Dimension(subFrame2.getWidth()+5, 600));
        subFrame2.setLocationRelativeTo(null);
        subFrame2.setResizable(false);
        subFrame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        subFrame2.setVisible(true);
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

class JDatePanel extends JPanel {

    private Calendar calendar;

    public JDatePanel(LayoutManager layoutManager, Calendar calendar) {
        super(layoutManager);
        this.calendar = calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Calendar getCalendar() {
        return calendar;
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
