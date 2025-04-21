// 300分 50%
/*
判断一些直线的交点数目，输入几个两点组，输出交点数目
输入{(0,5),(5,0)}|{(0,0),(5,5)}
输出1
*/

import java.util.*;

public class Main {

    static class Point {
        double x, y;
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Line {
        double x1, y1, x2, y2;
        Line(double x1, double y1, double x2, double y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    public static List<Line> help(String s) {
        List<Line> lines = new ArrayList<>();
        String[] parts = s.split("\\|");
        for (String part : parts) {
            part = part.replaceAll("[\\{\\(\\)}]", "");
            String[]  nums = part.split(",");
            double x1 = Double.parseDouble(nums[0]);
            double y1 = Double.parseDouble(nums[1]);
            double x2 = Double.parseDouble(nums[2]);
            double y2 = Double.parseDouble(nums[3]);
            lines.add(new Line(x1, y1, x2, y2));
        }
        return lines;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        List<Line> lines = help(input);
        // for (Line line : lines) {
        //     System.out.println(line.x1 + "," +line.y1 + "|" +line.x1 + "," +line.y2);
        // }
        Set<String> mySet = new HashSet<>();
        for (int i = 0; i < lines.size(); i++) {
            for (int j = i+1; j < lines.size(); j++) {
                Point p = getIntersection(lines.get(i), lines.get(j));
                if (p != null) {
                    String key = Double.toString(p.x)+Double.toString(p.y);
                    mySet.add(key);
                }
            }
        }
        System.out.println(mySet.size());

    }

    public static Point getIntersection(Line l1, Line l2) {
        double x1 = l1.x1, y1 = l1.y1;
        double x2 = l1.x2, y2 = l1.y2;
        double x3 = l2.x1, y3 = l2.y1;
        double x4 = l2.x2, y4 = l2.y2;

        double A1 = y1-y2, B1 = x2-x1, C1 = x1*y2-x2*y1;
        double A2 = y3-y4, B2 = x4-x3, C2 = x3*y4-x4*y3;
        double D = B2*A1 - B1*A2;

        if (Math.abs(D) < 1e-8) {
            return null;
        }

        double px = (B1*C2-B2*C1) / D;
        double py = (C1*A2-C2*A1) / D;

        return new Point(px, py);
    }
}