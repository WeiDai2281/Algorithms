import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int capacity;
    private int size;
    private Item[] items;

    public RandomizedQueue() {                 // construct an empty randomized queue
        capacity = 2;
        size = 0;
        this.items = (Item[]) new Object[2];
    }

    public boolean isEmpty() {                 // is the queue empty?
        return size == 0;
    }
    public int size() {                        // return the number of items on the queue
        return size;
    }

    public void enqueue(Item item) {           // add the item
        if (item == null) throw new NullPointerException();
        if (size == capacity) {
            capacity *= 2;
            resize(capacity);
        }
        items[size++] = item;
    }
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    public Item dequeue() {                    // remove and return a random item
        if (isEmpty()) throw new java.util.NoSuchElementException();
        else {
            int cur = StdRandom.uniform(size);
            Item n = items[cur];
            items[cur] = items[--size];
            items[size] = null;
            if (size > 0 && size == capacity / 4) {
                capacity = capacity / 2;
                resize(capacity);
            }
            return n;
        }
    }
    public Item sample() {                     // return (but do not remove) a random item
        if (isEmpty()) throw new java.util.NoSuchElementException();
        else {
            int cur = StdRandom.uniform(size);
            return items[cur];
        }
    }
    public Iterator<Item> iterator() {         // return an independent iterator over items in random order
        return new RandomIterator();

    }
    private class RandomIterator implements Iterator<Item> {
        int N;
        Item[] newitems;
        RandomIterator() {
            N = size;
            newitems = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
                newitems[i] = items[i];
            }
        }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        public boolean hasNext() {
            return N > 0;

        }
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            else {
                int cur = StdRandom.uniform(N);
                Item n = newitems[cur];
                newitems[cur] = newitems[N - 1];
                newitems[--N] = null;
                return n;
            }
        }
    }
    public static void main(String[] args) {   // unit testing (optional)
        RandomizedQueue<Integer> rd = new RandomizedQueue<>();
        Iterator<Integer> test = rd.iterator();
        rd.enqueue(10);
        rd.enqueue(20);
        System.out.println(test.hasNext());


    }
}