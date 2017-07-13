import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
public class PointSET {
    private TreeSet<Point2D> set;
    public PointSET() {                               // construct an empty set of points
        set = new TreeSet<>();
    }

    public boolean isEmpty() {                      // is the set empty?
        return set.isEmpty();
    }

    public int size() {                         // number of points in the set
        return set.size();
    }
    public void insert(Point2D p) {              // add the point to the set (if it is not already in the set)
        if (p == null) throw new NullPointerException();
        set.add(p);
    }

    public boolean contains(Point2D p) {            // does the set contain point p?
        if (p == null) throw new NullPointerException();
        return set.contains(p);

    }
    public void draw() {                         // draw all points to standard draw
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);

        for (Point2D p : set)
            StdDraw.point(p.x(), p.y());
    }

    public Iterable<Point2D> range(RectHV rect) {             // all points that are inside the rectangle
        if (rect == null) throw new NullPointerException();
        List<Point2D> rangeList = new LinkedList<>();
        for (Point2D p: set) {
            if (rect.contains(p)) rangeList.add(p);
        }
        return rangeList;


    }
    public Point2D nearest(Point2D p) {             // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new NullPointerException();
        Point2D closest = null;
        for (Point2D n: set) {
            if (closest == null) closest = n;
            if (closest.distanceSquaredTo(p) > n.distanceSquaredTo(p)) closest = n;
        }
        return closest;
    }

    public static void main(String[] args) {                  // unit testing of the methods (optional)

    }
}