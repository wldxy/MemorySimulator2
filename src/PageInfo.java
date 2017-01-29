
public class PageInfo {
    int id, size;
    boolean[] code;
    PageInfo(int size) {
        this.size = size;
        code = new boolean[size];
        for (int i = 0;i < size;i++)
            code[i] = false;
    }

}
