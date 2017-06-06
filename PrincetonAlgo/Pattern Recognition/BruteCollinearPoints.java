
/**
 * Created by David on 04/06/2017.
 */

import javax.sound.sampled.Line;
import java.util.*;

public class BruteCollinearPoints {
    private Point[] points;
    private List<LineSegment> seglist = new ArrayList<>();
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        Point[] line = new Point[4];
        int len = points.length;
        Point p1, p2, p3, p4;
        double slope12, slope13, slope14;
        for (int i = 0; i < len; i++) {
            p1 = points[i];
            for (int j = i + 1; j < len; j++) {
                p2 = points[j];
                slope12 = p1.slopeTo(p2);
                if (slope12 == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }
                for (int k = j + 1; k < len; k++) {
                    p3 = points[k];
                    slope13 = p1.slopeTo(p3);
                    if (slope13 == Double.NEGATIVE_INFINITY) {
                        throw new IllegalArgumentException();
                    }
                    if (slope12 != slope13) continue;
                    for (int l = k + 1; l < len; l++) {
                        p4 = points[l];
                        slope14 = p1.slopeTo(p4);
                        if (slope14 == Double.NEGATIVE_INFINITY) {
                            throw new IllegalArgumentException();
                        }
                        if (slope14 != slope13) continue;
                        else {
                            line[0] = p1;
                            line[1] = p2;
                            line[2] = p3;
                            line[3] = p4;
                            Arrays.sort(line);
                            seglist.add(new LineSegment(line[0], line[3]));
                         }
                    }
                }

            }
        }

    }
    public int numberOfSegments() {
        return seglist.size();

    }

    public LineSegment[] segments() {
        return seglist.toArray(new LineSegment[numberOfSegments()]);

    }
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }

}
