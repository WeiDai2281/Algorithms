import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
public class KdTree {
    private int size;
    private Node root;
    public KdTree() {                               // construct an empty set of points
        size = 0;
        root = null;
    }
    private class Node {
        private Point2D p;
        private Node left;
        private Node right;
        public Node(Point2D p) {
            this.p = p;
            this.left = null;
            this.right = null;
        }
    }

    public boolean isEmpty() {                      // is the set empty?
        return size == 0;
    }

    public int size() {                         // number of points in the set
        return size;
    }
    public void insert(Point2D p) {              // add the point to the set (if it is not already in the set)
        if (p == null) throw new NullPointerException();
        if (isEmpty()) {
            root = new Node(p);
            size++;
            return;
        } else {
            insert(root, p, 0);
        }
    }
    private void insert(Node current, Point2D p, int depth) {
        if (current.p.equals(p)) return;
        if (goLeft(current, p, depth)) {
            if (current.left == null) {
                current.left = new Node(p);
                size++;
                return;
            } else {
                insert(current.left, p, depth + 1);
            }
        } else {
            if (current.right == null) {
                current.right = new Node(p);
                size++;
                return;
            } else {
                insert(current.right, p, depth + 1);
            }
        }
    }
    private boolean goLeft(Node A, Point2D a, int dep) {
        if (dep % 2 == 0) return a.x() < A.p.x();
        else return a.y() < A.p.y();

    }

    public boolean contains(Point2D p) {            // does the set contain point p?
        if (root == null) return false;
        return contains(root, p, 0);
    }

    private boolean contains(Node current, Point2D p, int dep) {
        if (p == null) throw new NullPointerException();
        if (current.p.equals(p)) return true;
        if (goLeft(current, p, dep)) {
            if (current.left == null) return false;
            else {
                return contains(current.left, p, dep + 1);
            }
        } else {
            if (root.right == null) return false;
            else {
                return contains(current.right, p, dep + 1);
            }
        }
    }
    public void draw() {                         // draw all points to standard draw
        draw(root);
    }
    private void draw(Node current) {
        if (current == null) return;
        draw(current.left);
        current.p.draw();
        draw(current.right);
    }

    public Iterable<Point2D> range(RectHV rect) {             // all points that are inside the rectangle
        if (rect == null) throw new NullPointerException();
        SET<Point2D> rangeSet = new SET<>();
        add(root, rect, rangeSet, 0);
        return rangeSet;
    }

    private void add(Node current, RectHV rect, SET<Point2D> result, int dep) {
        if (current == null) return;
        if (rect.contains(current.p)) result.add(current.p);
        if (dep % 2 == 0) {
            if (current.p.x() > rect.xmax()) {
                add(current.left, rect, result, dep + 1);
            } else if (current.p.x() < rect.xmin()) {
                add(current.right, rect, result, dep + 1);
            } else {
                add(current.left, rect, result, dep + 1);
                add(current.right, rect, result, dep + 1);
            }
        } else {
            if (current.p.y() > rect.ymax()) {
                add(current.left, rect, result, dep + 1);
            } else if (current.p.y() < rect.ymin()) {
                add(current.right, rect, result, dep + 1);
            } else {
                add(current.left, rect, result, dep + 1);
                add(current.right, rect, result, dep + 1);
            }
        }
    }

    public Point2D nearest(Point2D p) {             // a nearest neighbor in the set to point p; null if the set is empty
        if (root == null) return null;
        Nearest result = new Nearest(new Point2D(0, 0), Double.POSITIVE_INFINITY);
        RectHV rect = new RectHV(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        result = nearest(root, p, 0, result, rect);
        return result.point;
    }

    private class Nearest {
        private Point2D point;
        private double distSq;
        public Nearest(Point2D p, double distSq) {
            this.point = p;
            this.distSq = distSq;
        }
    }

    private Nearest nearest(Node current, Point2D p, int depth, Nearest previous, RectHV rect) {
        Nearest result = previous;
        double distSq = current.p.distanceSquaredTo(p);
        if (distSq < previous.distSq) {
            result = new Nearest(current.p, distSq);
        }
        double xmin = rect.xmin();
        double xmax = rect.xmax();
        double ymin = rect.ymin();
        double ymax = rect.ymax();
        RectHV left, right;
        if (depth % 2 == 0) {
            left = new RectHV(xmin, ymin, current.p.x(), ymax);
            right = new RectHV(current.p.x(), ymin, xmax, ymax);
        } else {
            left = new RectHV(xmin, ymin, xmax, current.p.y());
            right = new RectHV(xmin, current.p.y(), xmax, ymax);
        }
        if (goLeft(current, p, depth)) {
            if (current.left != null) {
                result = nearest(current.left, p, depth + 1, result, left);
            }
            double distanceSquaredToRect = right.distanceSquaredTo(p);
            if ((current.right != null) && (distanceSquaredToRect < result.distSq)) {
                result = nearest(current.right, p, depth + 1, result, right);
            }
        } else {
            if (current.right != null) {
                result = nearest(current.right, p, depth + 1, result, right);
            }
            double distanceSquaredToRect = left.distanceSquaredTo(p);
            if ((current.left != null) && (distanceSquaredToRect < result.distSq)) {
                result = nearest(current.left, p, depth + 1, result, left);
            }
        }
        return result;
    }



    public static void main(String[] args) {                  // unit testing of the methods (optional)

    }
}