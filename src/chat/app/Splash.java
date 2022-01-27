
package chat.app;

import static chat.app.Server.f1;
import java.awt.*;
import javax.swing.*;

public class Splash{
    public static void main(String s[]){
        Frame f = new Frame("Sadhana University"); 
        f.setVisible(true); 
        int i;
        int x=1;
        for(i=3;i<=550;i+=4,x+=1){
            f.setLocation((500-((i+x)/2) ),300-(i/2));
            f.setSize(i+3*x,i+2*x);
            
            try{
                Thread.sleep(10);
            }catch(Exception e) { }
        }
    }
}
class Frame extends JFrame implements Runnable{
    Thread t1;
    Frame(String s){
        super(s);
        setLayout(new FlowLayout());
        ImageIcon c1 = new ImageIcon(ClassLoader.getSystemResource("chat/app/Icons/Chat.png"));
        Image i1 = c1.getImage().getScaledInstance(1000, 615,Image.SCALE_DEFAULT);
        ImageIcon i2 = new ImageIcon(i1);
        
        JLabel m1 = new JLabel(i2);
        add(m1);
        t1 = new Thread(this);
        t1.start();
        
        setUndecorated(true);
    }
    public void run(){
        try{
            Thread.sleep(7000);
            this.setVisible(false);
            
            
            
        }catch(Exception e){
            e.printStackTrace(); 
        }
    }
}
