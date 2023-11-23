import Toaster.Toaster;
import Utils.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;

public class CollectorUI extends JFrame {

    private final Toaster toaster;

    private Collector commentCollector;
    private ArrayList<JTextField> list = new ArrayList<JTextField>();
    private ArrayList<TextFieldOutput> outputLabel = new ArrayList<TextFieldOutput>();
    private String URL;

    public static void main(String[] args) {
        new CollectorUI();
    }

    private CollectorUI() {
        JPanel mainJPanel = getMainJPanel();

        addLogo(mainJPanel);

        addSeparator(mainJPanel);

        addURLTextField(mainJPanel);

        addOutputTextField(mainJPanel);

        addCollectButton(mainJPanel);

        addGitHubButton(mainJPanel);

        addWebsiteButton(mainJPanel);

        this.add(mainJPanel);
        this.pack();
        this.setVisible(true);
        this.toFront();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);

        toaster = new Toaster(mainJPanel);
    }

    private JPanel getMainJPanel() {
        this.setUndecorated(true);

        Dimension size = new Dimension(800, 400);

        JPanel panel1 = new JPanel();
        panel1.setSize(size);
        panel1.setPreferredSize(size);
        panel1.setBackground(UIUtils.COLOR_BACKGROUND);
        panel1.setLayout(null);

        MouseAdapter ma = new MouseAdapter() {
            int lastX, lastY;

            @Override
            public void mousePressed(MouseEvent e) {
                lastX = e.getXOnScreen();
                lastY = e.getYOnScreen();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(getLocationOnScreen().x + x - lastX, getLocationOnScreen().y + y - lastY);
                lastX = x;
                lastY = y;
            }
        };

        panel1.addMouseListener(ma);
        panel1.addMouseMotionListener(ma);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        return panel1;
    }

    private void addSeparator(JPanel panel1) {
        JSeparator separator1 = new JSeparator();
        separator1.setOrientation(SwingConstants.VERTICAL);
        separator1.setForeground(UIUtils.COLOR_OUTLINE);
        panel1.add(separator1);
        separator1.setBounds(310, 80, 1, 240);
    }

    private void addLogo(JPanel panel1) {
        JLabel label1 = new JLabel();
        label1.setFocusable(false);
        label1.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("github-logo.PNG")).getFile()));
        panel1.add(label1);
        label1.setBounds(65, 106, 180, 180);
    }

    private void addURLTextField(JPanel panel1) {
        TextFieldURL urlField = new TextFieldURL();
        list.add(urlField);

        urlField.setBounds(423, 109, 250, 44);
        urlField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (urlField.getText().equals(UIUtils.PLACEHOLDER_TEXT_URL)) {
                    urlField.setText("");
                }
                urlField.setForeground(Color.white);
                urlField.setBorderColor(UIUtils.COLOR_INTERACTIVE);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (urlField.getText().isEmpty()) {
                    urlField.setText(UIUtils.PLACEHOLDER_TEXT_URL);
                }
                urlField.setForeground(UIUtils.COLOR_OUTLINE);
                urlField.setBorderColor(UIUtils.COLOR_OUTLINE);
            }
        });

        panel1.add(urlField);
    }

    private void addOutputTextField(JPanel panel1) {
        TextFieldOutput textField = new TextFieldOutput();
        textField.setText("Paste Workshop URL");
        textField.setHorizontalAlignment(JLabel.CENTER);
        outputLabel.add(textField);

        textField.setBounds(423, 168, 250, 44);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setForeground(Color.white);
                textField.setBorderColor(UIUtils.COLOR_INTERACTIVE);
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.setForeground(UIUtils.COLOR_OUTLINE);
                textField.setBorderColor(UIUtils.COLOR_OUTLINE);
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    try {
                        URL = list.get(0).getText();
                        System.out.println("This is the URL: " + URL);
                        collectEventHandler();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        panel1.add(textField);
    }

    private void addCollectButton(JPanel panel1) {
        final Color[] collectButtonColors = {UIUtils.COLOR_INTERACTIVE, Color.white};

        JLabel collectButton = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = UIUtils.get2dGraphics(g);
                super.paintComponent(g2);

                Insets insets = getInsets();
                int w = getWidth() - insets.left - insets.right;
                int h = getHeight() - insets.top - insets.bottom;
                g2.setColor(collectButtonColors[0]);
                g2.fillRoundRect(insets.left, insets.top, w, h, UIUtils.ROUNDNESS, UIUtils.ROUNDNESS);

                FontMetrics metrics = g2.getFontMetrics(UIUtils.FONT_GENERAL_UI);
                int x2 = (getWidth() - metrics.stringWidth(UIUtils.BUTTON_TEXT_COLLECT)) / 2;
                int y2 = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                g2.setFont(UIUtils.FONT_GENERAL_UI);
                g2.setColor(collectButtonColors[1]);
                g2.drawString(UIUtils.BUTTON_TEXT_COLLECT, x2, y2);
            }
        };

        collectButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    URL = list.get(0).getText();
                    System.out.println("This is the URL: " + URL);
                    collectEventHandler();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                collectButtonColors[0] = UIUtils.COLOR_INTERACTIVE_DARKER;
                collectButtonColors[1] = UIUtils.OFFWHITE;
                collectButton.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                collectButtonColors[0] = UIUtils.COLOR_INTERACTIVE;
                collectButtonColors[1] = Color.white;
                collectButton.repaint();
            }
        });

        collectButton.setBackground(UIUtils.COLOR_BACKGROUND);
        collectButton.setBounds(423, 247, 250, 44);
        collectButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel1.add(collectButton);
    }

    private void addGitHubButton(JPanel panel1) {
        panel1.add(new HyperlinkText(UIUtils.BUTTON_TEXT_GITHUB, 423, 300, () -> {
            try {

                Desktop.getDesktop().browse(new URI("https://github.com/hxc34"));

            } catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
            }
        }));
    }

    private void addWebsiteButton(JPanel panel1) {
        panel1.add(new HyperlinkText(UIUtils.BUTTON_TEXT_WEBSITE, 578, 300, () -> {

        }));
    }

    private void collectEventHandler() throws IOException, InterruptedException {


        commentCollector = new Collector();
        Collector.setupWebdriverChromeDriver();
        commentCollector.setup(URL);
        commentCollector.startSteamCollection();
        commentCollector.teardown();
        outputLabel.get(0).setText("Collection Complete");

    }
}