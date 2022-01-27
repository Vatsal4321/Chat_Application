package chat.application;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class UserOne extends JFrame implements ActionListener, Runnable {

    JTextField t1;
    JButton b1;
    JTextArea a1;
    JLabel l1, l2, l3, l4, l5, l6, l7, l8;
    Timer t;

    BufferedWriter writer;
    BufferedReader reader;

    Boolean typing;

    UserOne() {

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chat/application/Icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        l1 = new JLabel(i3);
        l1.setBounds(2, 15, 25, 25);
        add(l1);

        l1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        ImageIcon i8 = new ImageIcon(ClassLoader.getSystemResource("chat/application/Icons/video.png"));
        Image i12 = i8.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon i13 = new ImageIcon(i12);
        l6 = new JLabel(i13);
        l6.setBounds(200, 15, 20, 20);
        add(l6);

        ImageIcon i9 = new ImageIcon(ClassLoader.getSystemResource("chat/application/Icons/phone.png"));
        Image i14 = i9.getImage().getScaledInstance(18, 20, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        l7 = new JLabel(i15);
        l7.setBounds(240, 15, 18, 20);
        add(l7);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("chat/application/Icons/3icon.png"));
        Image i16 = i10.getImage().getScaledInstance(8, 20, Image.SCALE_DEFAULT);
        ImageIcon i17 = new ImageIcon(i16);
        l8 = new JLabel(i17);
        l8.setBounds(280, 15, 8, 20);
        add(l8);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("chat/application/Icons/elon.png"));
        l3 = new JLabel(i7);
        l3.setBounds(27, 5, 40, 40);
        add(l3);

        ImageIcon i = new ImageIcon(ClassLoader.getSystemResource("chat/application/Icons/grad3.png"));
        b1 = new JButton(i);
        b1.setBounds(207, 515, 87, 30);
        b1.addActionListener(this);
        add(b1);

        a1 = new JTextArea();
        a1.setBounds(5, 55, 290, 452);
        a1.setFont(new Font("SEN_SERIF", Font.PLAIN, 16));
        a1.setEditable(false);
        a1.setLineWrap(true);
        a1.setWrapStyleWord(true);
        a1.setBackground(new Color(200, 237, 255));
        add(a1);

        l4 = new JLabel("Billianiers");
        l4.setFont(new Font("SAN_SERIF", Font.BOLD, 17));
        l4.setForeground(Color.WHITE);
        l4.setBounds(72, 10, 120, 18);
        add(l4);

        l5 = new JLabel("Online");
        l5.setFont(new Font("SAN_SERIF", Font.PLAIN, 10));
        l5.setForeground(Color.WHITE);
        l5.setBounds(76, 32, 50, 10);
        add(l5);

        t = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (!typing) {
                    l5.setText("Elon Musk");
                }
            }
        });

        t.setInitialDelay(2000);

        t1 = new JTextField();
        t1.setBounds(5, 515, 200, 30);
        t1.setFont(new Font("SEN_SERIF", Font.PLAIN, 16));
        add(t1);

        t1.addKeyListener(new KeyAdapter() {
            public void KeyPressed(KeyEvent ke) {
                l5.setText("Typing...");

                t.stop();

                typing = true;

            }

            public void KeyReleased(KeyEvent ke) {
                typing = false;

                if (!t.isRunning()) {
                    t.start();
                }
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chat/application/Icons/grad.png"));
        Image i5 = i4.getImage().getScaledInstance(300, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        l2 = new JLabel(i6);
        l2.setBounds(0, 0, 300, 50);
        add(l2);

        getContentPane().setBackground(new Color(230, 242, 255));
        setLayout(null);
        setSize(300, 550);
        setLocation(0, 40);
        setUndecorated(true);
        setVisible(true);

        try {
            Socket socketClient = new Socket("localhost", 2005);
            writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        } catch (Exception e) {
        }

    }

    public void actionPerformed(ActionEvent ae) {
        String str = "Elon Musk :\n" + t1.getText();
        try {
            writer.write(str);
            writer.write("\r\n\n");
            writer.flush();
        } catch (Exception e) {
        }
        t1.setText("");
    }

    public void run() {
        try {
            String msg = "";
            while ((msg = reader.readLine()) != null) {
                a1.append(msg + "\n");
            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        UserOne one = new UserOne();
        Thread t1 = new Thread(one);
        t1.start();
    }
}
