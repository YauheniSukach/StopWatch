import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.TimerTask;

public class StopWatch extends JFrame{
    private JPanel panel = new JPanel(new GridLayout(1, 1, 1, 1));
    public TimerLabel tl = new TimerLabel();
    public StopWatch(){
        final JFrame f = new JFrame();
        final JPanel contentPane = new JPanel();
        contentPane.add(new JLabel("Запустить секундомер?", JLabel.CENTER));
        contentPane.add(new JButton(new AbstractAction("Запустить"){
            public void actionPerformed(ActionEvent e){
                setContentPane(new JPanel(){
                    public void paintComponent(Graphics g){
                        BufferedImage backGround = null;
                        try {
                            backGround = ImageIO.read(new File("src/clock.jpg"));
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        g.drawImage(backGround, 0, 0, this);
                    }
                });
                setVisible(true);
                setSize(500, 340);
                Container container = getContentPane();
                JButton button1 = new JButton("Stop");
                panel.add(button1);
                ActionListener actionListener = new TestActionListener();
                button1.addActionListener(actionListener);
                JButton button = new JButton("Запустить с начала");
                panel.add(button);
                ActionListener actionListene = new Test();
                button.addActionListener(actionListene);
                setResizable(false);// окно не меняет размер
                panel.setBackground(Color.WHITE);
                container.add(panel);
                panel.add(tl);
                panel.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
                Font font = new Font("Verdana", Font.PLAIN, 32);
                tl.setFont(font);
                tl.setForeground(Color.BLACK);
                f.setVisible(false);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            }
        }));
        contentPane.add(new JButton(new AbstractAction("Выход"){
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }));

        f.setContentPane(contentPane);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                f.pack();
                f.show();
            }
        });
    }
    class TimerLabel extends JLabel{
        public java.util.Timer timer = new java.util.Timer();
        public TimerLabel() {
            timer.scheduleAtFixedRate(timerTask, 0, 1000);
        }
        public TimerTask timerTask = new TimerTask(){
            private volatile int time = -1;
            private Runnable refresher = new Runnable(){
                public void run(){
                    int t = time;
                    DecimalFormat decimalFormat = new DecimalFormat("00"); // задаем формат вывода секундомера
                    TimerLabel.this.setText(decimalFormat.format(t / 60) + ":" + decimalFormat.format(t % 60));
                }
            };
            public void run(){
                time++;
                SwingUtilities.invokeLater(refresher);
            }

        };
        public void StopTimer() {
            timer.cancel();
        }
    }
    public static void main(String[] args) throws InterruptedException{
        JFrame app = new StopWatch();
        app.setVisible(false);
    }
    public class TestActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            tl.StopTimer();
        }
    }
    public class Test implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JFrame app = new StopWatch();
            app.setVisible(false);
            dispose();
        }
    }
}

class BPanel extends JPanel{
    public void paintComponent(Graphics g){
        Image im = null;
        try {
            im = ImageIO.read(new File("clock.jpg"));
        }
        catch (IOException e) {
            g.drawImage(im, 0, 0, null);
        }
    }
}
