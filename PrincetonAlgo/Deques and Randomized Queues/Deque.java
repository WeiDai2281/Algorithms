import java.util.NoSuchElementException;
import java.util.Iterator;
public class Deque<Item> implements Iterable<Item> {
    private Node head;
    private Node tail;
    private int size;
    private class Node {
        Item val;
        Node next;
        Node prev;
        Node(Item val, Node prev, Node next) {
            this.val = val;
            this.prev = prev;
            this.next = next;
        }
    }
    public Deque() {                          // construct an empty deque
        head = null;
        tail = null;
        size = 0;
    }
    public boolean isEmpty() {                 // is the deque empty?
        return size == 0;
    }
    public int size() {                        // return the number of items on the deque
        return size;
    }

    public void addFirst(Item item) {          // add the item to the front
        if (item == null) throw new NullPointerException();
        Node n = new Node(item, null, head);
        if (head != null) {
            head.prev = n;
        }
        head = n;
        size++;
        if (size == 1) tail = head;

    }
    public void addLast(Item item) {           // add the item to the end
        if (item == null) throw new NullPointerException();
        Node n = new Node(item, tail, null);
        if (tail != null) {
            tail.next = n;
        }
        tail = n;
        size++;
        if (size == 1) head = tail;

    }
    public Item removeFirst() {                // remove and return the item from the front
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            Node second = head.next;
            Item n = head.val;
            head = null;
            head = second;
            if (head != null) {
                head.prev = null;
            }
            size--;
            return n;
        }
    }
    public Item removeLast() {                 // remove and return the item from the end
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else {
            Node second = tail.prev;
            Item n = tail.val;
            tail = null;
            tail = second;
            if (tail != null) {
                tail.next = null;
            }
            size--;
            return n;
        }
    }
    public Iterator<Item> iterator() {         // return an iterator over items in order from front to end
        return new Dequeiterator();
    }
    private class Dequeiterator implements Iterator<Item> {
        Node first = head;
        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        @Override
        public boolean hasNext() {
            return (first != null);
        }
        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            else {
                Item result = first.val;
                first = first.next;
                return result;
            }
        }
    }
    public static void main(String[] args) {   // unit testing (optional)

    }
}
