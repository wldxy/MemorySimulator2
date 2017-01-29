import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Random;

public class MemSystem {
    Integer[] memory;
    int max_size;
    Integer type;
    HashMap<Integer, Node> map = new HashMap<>();
    private Node first, last;
    //type=0 LRU; type=1 FIFO; type=2 random

    MemSystem(int size, int type) {
        max_size = size;
        memory = new Integer[size];
        for (int i = 0;i < size;i++)
            memory[i] = -1;
        this.type = type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer[] getMemory() {
        return memory;
    }

    public boolean request(int id) {
        if (type < 2)
            return requestByCal(id);
        else
            return requestByRandom(id);
    }

    private boolean requestByRandom(int id) {
        if (map.get(id) == null) {
            if (map.size() < max_size) {
                addToLast(new Node(id, map.size()));
                memory[map.size()] = id;
            }
            else {
                Random r = new Random();
                int next = r.nextInt(map.size());
                Node temp = first;
                while (--next > 0) {
                    temp = temp.next;
                }
                temp.id = id;
            }
            if (map.size() > max_size)
                return true;
            else
                return false;
        }
        else {
            return false;
        }
    }

    private void addToLast(Node node) {
        node.pre = last;
        if (last != null) {
            last.next = node;
        }
        else {
           last = first = node;
        }
        map.put(node.id, node);
    }

    private void remove(Node node) {
        if (first == node)
            first = node.next;
        if (last == node)
            last = node.pre;
        if (node.pre != null)
            node.pre.next = node.next;
        if (node.next != null)
            node.next.pre = node.pre;
        map.remove(node.id);
    }

    private boolean requestByCal(int id) {
        if (map.get(id) == null) {
            int mem_id;
            if (map.size() >= max_size) {
                mem_id = first.mem_id;
                map.remove(first.id);
                removeFirst();
            }
            else {
                mem_id = map.size();
            }
            memory[mem_id] = id;
            Node node = new Node(id, mem_id);
            moveToLast(node);
            map.put(id, node);
            //System.out.println(map.size());
            if (map.size() == max_size)
                return true;
            else
                return false;
        }
        else if (type == 0) {
            moveToLast(map.get(id));
        }
        return false;
    }

    private void moveToLast(Node node) {
        if (last == node)
            return;
        if (last == null) {
            last = first = node;
            return;
        }
        if (node == first)
            first = first.next;
        if (node.pre != null)
            node.pre.next = node.next;
        if (node.next != null)
            node.next.pre = node.pre;
        node.pre = last;
        node.next = null;
        last.next = node;
        last = node;
    }

    private void removeFirst() {
        if (first != null) {
            map.remove(first.id);
            first = first.next;
            if (first == null)
                last = null;
        }
    }

    public void print() {
        for (int i = 0;i < max_size;i++) {
            System.out.printf("%d  ", memory[i]);
        }
        System.out.println("  *  ");
        Node temp = first;
        while (temp != null) {
            System.out.printf("%d  ", temp.id);
            temp = temp.next;
        }
        //System.out.println();
        //System.out.println("=============");
    }

    public static void main(String[] args) {
        MemSystem mem = new MemSystem(10, 0);
        for (int i = 0;i < 15;i++) {
            mem.request(i);
        }
        mem.print();
        mem.request(5);
        mem.print();
        mem.request(12);
        mem.print();
    }
}

class Node {
    Node pre, next;
    int id, mem_id;

    Node(int id, int mem_id) {
        this.id = id;
        this.mem_id = mem_id;
        this.next = null;
        this.pre = null;
    }
}