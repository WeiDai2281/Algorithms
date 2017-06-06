import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> result;
    private int pointNum = 0;
    public FastCollinearPoints(Point[] inpoints) {
        if (inpoints == null) {
            throw new NullPointerException();
        }

        ArrayList<Point> temp;
        result = new ArrayList<>();

        Point[] points = inpoints.clone();
        for (int i = 0; i < points.length; i++) {
            Point startpoint = points[i];
            if (startpoint == null) {
                throw new IllegalArgumentException();
            }
            int begin = i + 1;
            if (begin > points.length - 1) {
                continue;
            }
            Arrays.sort(points, begin, points.length, startpoint.slopeOrder());
            double currentSlope = startpoint.slopeTo(points[begin]);
            if (currentSlope == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException();
            }

            temp = new ArrayList<>();
            temp.add(startpoint);
            temp.add(points[begin]);

            for (int j = begin + 1; j < points.length; j++) {
                Point p = points[j];
                double slope = startpoint.slopeTo(p);
                if (currentSlope == slope) {
                    temp.add(p);
                    if (temp.size() >= 4 && j == points.length - 1) {
                        registertemp(temp);
                    }
                } else {
                    if (temp.size() >= 4) {
                        registertemp(temp);
                    }
                    temp = new ArrayList<>();
                    temp.add(startpoint);
                    temp.add(p);
                    currentSlope = slope;
                }
            }
        }
    }

    private void registertemp(ArrayList<Point> temp) {
        if (pointNum == temp.size()) {
            pointNum = temp.size() - 1;
            return;
        }
        pointNum = temp.size() - 1;

        temp.sort(null);
        result.add(new LineSegment(temp.get(0), temp.get(temp.size() - 1)));
    }

    public int numberOfSegments() {
        return result.size();
    }

    public LineSegment[] segments() {
        return result.toArray(new LineSegment[result.size()]);
    }
}