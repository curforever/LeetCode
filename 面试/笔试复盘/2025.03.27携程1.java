// 100%

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String timeString = in.nextLine();
        int hours = Integer.parseInt(timeString.substring(0, 2));
        int minutes = Integer.parseInt(timeString.substring(3, 5));

        int res = 0;
        while (true) {
            String curTime = String.format("%02d:%02d", hours, minutes);
            if (isParlindrome(curTime)) {
                System.out.println(res);
                break;
            }
            minutes++;
            res++;

            if (minutes == 60) {
                minutes = 0;
                hours++;
                if (hours == 24) {
                    hours = 0;
                }
            }
        }
        return;

    }
    public static boolean isParlindrome(String time) {
        return time.charAt(0) == time.charAt(4) && time.charAt(1) == time.charAt(3);
    }
}
