import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by ocean on 16-5-27.
 */
public class AppSystem extends JPanel {
     MemSystem memSystem;
    PageInfo[] pageInfos;
    int codeLen, blockLen, numBlock, runLen;
    boolean[] code;
    int[] codeList;
    JTextArea jta1, jta2;
    int curCode, change;
    boolean isRun;
    int index, coindex;

    AppSystem(JTextArea jta1, JTextArea jta2) {
        this.jta1 = jta1;
        this.jta2 = jta2;
        isRun = false;
    }

    public static int[] randomArray(int min, int max, int n){
        int len = max-min+1;

        if(max < min || n > len){
            return null;
        }

        int[] source = new int[len];
        for (int i = min; i < min+len; i++){
            source[i-min] = i;
        }

        int[] result = new int[n];
        Random rd = new Random();
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            index = Math.abs(rd.nextInt() % len--);
            result[i] = source[index];
            source[index] = source[len];
        }
        return result;
    }

    public void init(int codeLen, int blockLen, int runLen, int type) {
        this.codeLen = codeLen;
        this.blockLen = blockLen;
        this.runLen = runLen;
        numBlock = codeLen / blockLen;
        isRun = true;
        if (codeLen % blockLen != 0)
            numBlock++;
        memSystem = new MemSystem(runLen, type);
        curCode = 0;
        change = 0;
        pageInfos = new PageInfo[numBlock];
        code = new boolean[codeLen];
        for (int i = 0;i < numBlock-1;i++) {
            pageInfos[i] = new PageInfo(blockLen);
        }
        if (codeLen % blockLen != 0) {
            pageInfos[numBlock-1] = new PageInfo(codeLen % blockLen);
        }
        else {
            pageInfos[numBlock-1] = new PageInfo(blockLen);
        }
        codeList = randomArray(0, codeLen-1, codeLen);
//        for (int i: codeList) {
//            System.out.printf("%d  ", codeList[i]);
//        }
//        System.out.println();
    }

    public void next() {
        index = codeList[curCode] / blockLen;
        coindex = codeList[curCode] % blockLen;
        jta1.append("指令"+codeList[curCode]+",内存块"+index+"\n");
        pageInfos[index].code[coindex] = true;
        if (memSystem.request(index)) {
            change++;
            int rate = (int) (((double)change / curCode) * 100);
            jta2.append("出现调页  共"+change+"次  缺页率"+rate+"%\n");
        }
        else {
            jta2.append("不出现调页\n");
        }
        curCode++;
        this.repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (isRun) {
            int dLen = 600, x = 20, y = 50, h = 100;
            int single = (dLen - (runLen - 1) * 10) / runLen;
            //System.out.printf("%d === ", single);
            int delta = 10 + single;
            for (int i = 0; i < runLen; i++) {
                if (memSystem.memory[i] != -1) {
                    PageInfo t = pageInfos[memSystem.memory[i]];
                    int tx = x, len = single / blockLen;
                    for (int j = 0; j < t.code.length; j++) {
                        if (t.code[j])
                            g.setColor(Color.orange);
                        else
                            g.setColor(Color.cyan);
                        if (j == coindex && memSystem.memory[i] == index) {
                            g.setColor(Color.red);
                        }
                        g.fillRect(tx, y, len, h);
                        tx += len;
                    }
                    x += delta;
                }
            }
        }
    }
}
