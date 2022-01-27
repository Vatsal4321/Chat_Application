package chat.app;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Server implements ActionListener {

    JPanel p1;
    JTextField t1;
    JButton b1;
    static JPanel a1;
    static JFrame f1 = new JFrame();

    static Box vertical = Box.createVerticalBox();

    static ServerSocket skt;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;

    Boolean typing;

    Server() {
        f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(17, 132, 120));
        p1.setBounds(0, 0, 450, 65);
        f1.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chat/app/Icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(4, 20, 25, 25);
        p1.add(l1);

        l1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chat/app/Icons/elon1.png"));
//       Image i5 = i4.getImage().getScaledInstance(55, 55, Image.SCALE_DEFAULT);
//       ImageIcon i6 = new ImageIcon(i5);
        JLabel l2 = new JLabel(i4);
        l2.setBounds(30, 5, 55, 55);
        p1.add(l2);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("chat/app/Icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel l5 = new JLabel(i9);
        l5.setBounds(260, 20, 30, 30);
        p1.add(l5);

        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("chat/app/Icons/phone.png"));
        Image i12 = i11.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i13 = new ImageIcon(i12);
        JLabel l6 = new JLabel(i13);
        l6.setBounds(315, 20, 35, 30);
        p1.add(l6);

        ImageIcon i14 = new ImageIcon(ClassLoader.getSystemResource("chat/app/Icons/3icon.png"));
        Image i15 = i14.getImage().getScaledInstance(13, 25, Image.SCALE_DEFAULT);
        ImageIcon i16 = new ImageIcon(i15);
        JLabel l7 = new JLabel(i16);
        l7.setBounds(375, 20, 13, 25);
        p1.add(l7);

        JLabel l3 = new JLabel("Elon Musk");
        l3.setFont(new Font("Times New Roman", Font.BOLD, 18));
        l3.setForeground(Color.WHITE);
        l3.setBounds(90, 15, 100, 22);
        p1.add(l3);

        JLabel l4 = new JLabel("Online");
        l4.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        l4.setForeground(Color.WHITE);
        l4.setBounds(92, 35, 100, 20);
        p1.add(l4);

        Timer t = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (!typing) {
                    l4.setText("Online");
                }
            }
        });

        t.setInitialDelay(2000);

        a1 = new JPanel();
//       a1.setBounds(5, 65, 390, 500);
        a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        a1.setForeground(Color.BLACK);
//       f1.add(a1);

        JScrollPane sp = new JScrollPane(a1);
        sp.setBounds(5, 70, 390, 505);
        sp.setBorder(BorderFactory.createEmptyBorder());

        ScrollBarUI ui = new BasicScrollBarUI() {
            protected JButton createDecreaseButton(int orientation) {
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(new Color(245, 245, 245));
                button.setForeground(Color.WHITE);
                this.thumbColor = new Color(245, 245, 245);
                this.trackColor = new Color(245, 245, 245);
                return button;
            }

            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(new Color(245, 245, 245));
                button.setForeground(Color.WHITE);
                this.thumbColor = new Color(245, 245, 245);
                this.trackColor = new Color(245, 245, 245);
                return button;
            }
        };

        sp.getVerticalScrollBar().setUI(ui);
        f1.add(sp);

        t1 = new JTextField();
        t1.setBounds(5, 580, 290, 30);
        t1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        f1.add(t1);

        t1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                l4.setText("typing...");

                t.stop();

                typing = true;
            }

            public void keyReleased(KeyEvent ke) {
                typing = false;

                if (!t.isRunning()) {
                    t.start();
                }
            }
        });

        b1 = new JButton("Send");
        b1.setBounds(295, 580, 100, 30);
        b1.setBackground(new Color(17, 132, 120));
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        b1.addActionListener(this);
        f1.add(b1);

        f1.getContentPane().setBackground(new Color(255, 255, 255));
        f1.setLayout(null);
        f1.setSize(400, 615);
        f1.setLocation(200, 30);
        f1.setUndecorated(true);
        f1.setVisible(true);

    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String out = t1.getText();
            sendTextToFile(out);
            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            //a1.add(p2);
            dout.writeUTF(out);
            t1.setText("");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendTextToFile(String message) throws FileNotFoundException {
        try (FileWriter f = new FileWriter("chats.txt");
                PrintWriter p = new PrintWriter(new BufferedWriter(f));) {
            p.println("Elon Musk: " + message);
        } catch (Exception e) {
        }
    }

    public static JPanel formatLabel(String out) {
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));

        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">" + out + "</p></html>");
        l1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        l1.setBackground(new Color(220, 248, 198));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15, 15, 15, 50));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));

        p3.add(l1);
        p3.add(l2);
        return p3;
    }

    public static void main(String[] args) {
        new Server().f1.setVisible(true);

        String msginput = "";
        try {
            skt = new ServerSocket(6001);
            while (true) {
                s = skt.accept();
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while (true) {
                    msginput = din.readUTF();
                    JPanel p2 = formatLabel(msginput);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(p2, BorderLayout.LINE_START);
                    vertical.add(left);
                    f1.validate();
                }

            }

        } catch (Exception e) {
        }
    }
}
