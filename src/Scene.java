import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

/**
 * Created by ocean on 16-5-26.
 */
public class Scene implements ActionListener {
    JFrame frame;
    JPanel control;
    JTextField codeLen, singleBlockCode, blockSize;
    JComboBox algo;
    AppSystem system;
    JRadioButton jrb1, jrb2;

    Scene() {
        frame = new JFrame("请求调页存储管理方式模拟");

        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container cont = frame.getContentPane();

        JButton initButton = new JButton("初始化");
        JButton nextButton = new JButton("下一条指令");
        initButton.setActionCommand("init");
        initButton.addActionListener(this);
        nextButton.setActionCommand("next");
        nextButton.addActionListener(this);
        frame.add(initButton);
        frame.add(nextButton);

        JTextArea jta1 = new JTextArea();
        JScrollPane scr1 = new JScrollPane(jta1);
        JTextArea jta2 = new JTextArea();
        JScrollPane scr2 = new JScrollPane(jta2);
        JTextArea jta3 = new JTextArea();
        JScrollPane scr3 = new JScrollPane(jta3);
        jta1.setLineWrap(true);
        jta2.setLineWrap(true);
        jta3.setLineWrap(true);
        scr1.setBounds(350, 150, 200, 300);
        scr2.setBounds(580, 150, 200, 300);

        JLabel jl1 = new JLabel("指令");
        JLabel jl2 = new JLabel("是否调页");
        frame.add(jl1);
        frame.add(jl2);
        jl1.setBounds(350, 130, 100, 20);
        jl2.setBounds(580, 130, 100, 20);

        frame.add(scr1);
        frame.add(scr2);

        system = new AppSystem(jta1, jta2);
        system.setBorder(BorderFactory.createTitledBorder("内存空间（未运行的指令为蓝色，已执行完毕的指令为黄色，正在执行的指令为红色）"));
        frame.add(system);
        system.init(320, 10, 4, 0);
        system.repaint();

        jrb1 = new JRadioButton("随机指令");
        jrb2 = new JRadioButton("输入指令");
        jrb1.setActionCommand("random");
        jrb2.setActionCommand("input");
        jrb1.addActionListener(this);
        jrb2.addActionListener(this);
        JPanel pan = new JPanel();
        pan.setBorder(BorderFactory.createTitledBorder("指令")); // 设置一个边框的显示条
        pan.setLayout(new GridLayout(3, 1));
        jrb1.setSelected(true);
        pan.add(jrb1);
        pan.add(jrb2);
        //pan.add(jrb3);
        cont.add(pan);

        initControl();
        cont.add(control);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent obj) {
                System.exit(1);
            }
        });

        frame.setLayout(null);
        initButton.setBounds(350, 50, 100, 40);
        nextButton.setBounds(500, 50, 100, 40);
        //jta1.setBounds(15, 80, 150, 105);
        //jta2.setBounds(200,80,170,87) ;

        control.setBounds(50, 50, 250, 200);
        pan.setBounds(50, 300, 250, 150);

        system.setBounds(50, 500, 700, 200);

        frame.setVisible(true);
    }

    private void initControl() {
        control = new JPanel();

        JLabel label1 = new JLabel("指令总条数");
        codeLen = new JTextField("320", 10);
        codeLen.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int key = e.getKeyChar();
                if (key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) {

                }
                else {
                    e.consume();
                }
            }
        });

        JLabel label2 = new JLabel("单内存块内指令数");
        singleBlockCode = new JTextField("10", 10);
        singleBlockCode.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int key = e.getKeyChar();
                if (key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) {

                }
                else {
                    e.consume();
                }
            }
        });

        JLabel label3 = new JLabel("内存块总数");
        blockSize = new JTextField("4", 10);
        blockSize.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int key = e.getKeyChar();
                if (key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) {

                }
                else {
                    e.consume();
                }
            }
        });

        JLabel label4 = new JLabel("算法选择");
        algo = new JComboBox();
        algo.addItem("LRU");
        algo.addItem("FIFO");
        algo.addItem("Random");
        System.out.println(algo.getSelectedIndex());

        control.setBorder(BorderFactory.createTitledBorder("参数设置"));
        control.setLayout(new GridLayout(4, 2, 20, 20));
        control.add(label1);
        control.add(codeLen);
        control.add(label2);
        control.add(singleBlockCode);
        control.add(label3);
        control.add(blockSize);
        control.add(label4);
        control.add(algo);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("init")) {
            int codelen = Integer.parseInt(codeLen.getText());
            int blocklen = Integer.parseInt(singleBlockCode.getText());
            int runlen = Integer.parseInt(blockSize.getText());
            int use = algo.getSelectedIndex();
            system.init(codelen, blocklen, runlen, use);
        }
        if (e.getActionCommand().equals("next")) {
            system.next();
        }
        if (e.getActionCommand().equals("random")) {
            jrb2.setSelected(false);
        }
        if (e.getActionCommand().equals("input")) {
            jrb1.setSelected(false);
        }
    }

    public static void main(String[] args) {
        new Scene();
    }
}
