// 300分 95%
/*

*/

// We have imported the necessary tool classes.
// If you need to import additional packages or classes, please import here.

public class Main {
    public static void main(String[] args) {
    // please define the JAVA input here. For example: Scanner s = new Scanner(System.in);
    // please finish the function body here.
    // please define the JAVA output here. For example: System.out.println(s.nextInt());
        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt();
        int[][] grid = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }
        
        int[][] a = new int[n][n];
        a[0][0] = grid[0][0];
        for (int i = 1; i < n; i++) {
            if (a[i-1][0]!=Integer.MAX_VALUE && grid[i][0] != 0) 
                a[i][0] = a[i-1][0] + grid[i][0];
            else 
                a[i][0] = Integer.MAX_VALUE;
        }
        for (int j = 1; j < n; j++) {
            if (a[0][j-1]!=Integer.MAX_VALUE && grid[0][j] != 0) 
                a[0][j] = a[0][j-1] + grid[0][j];
            else 
                a[0][j] = Integer.MAX_VALUE;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                if (Math.min(a[i-1][j], a[i][j-1])!=Integer.MAX_VALUE && grid[i][j] != 0) 
                    a[i][j] = Math.min(a[i-1][j], a[i][j-1]) + grid[i][j];
                else
                    a[i][j] = Integer.MAX_VALUE;
            }
        }
        
        int[][] b = new int[n][n];
        b[n-1][n-1] = grid[n-1][n-1];
        for (int i = n-2; i >= 0; i--) {
            if (b[i+1][n-1]!=Integer.MAX_VALUE && grid[i][n-1] != 0) 
                b[i][n-1] = b[i+1][n-1] + grid[i][n-1];
            else
                b[i][n-1] = Integer.MAX_VALUE;
        }
        for (int j = n-2; j >= 0; j--) {
            if (b[n-1][j+1]!=Integer.MAX_VALUE && grid[n-1][j] != 0) 
                b[n-1][j] = b[n-1][j+1] + grid[n-1][j];
            else
                b[n-1][j] = Integer.MAX_VALUE;
        }
        for (int i = n-2; i >= 0; i--) {
            for (int j = n-2; j >= 0; j--) {
                if (Math.min(b[i+1][j], b[i][j+1])!=Integer.MAX_VALUE && grid[i][j] != 0) 
                    b[i][j] = Math.min(b[i+1][j], b[i][j+1]) + grid[i][j];
                else
                    b[i][j] = Integer.MAX_VALUE;
            }
        }
        
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j+1 < n && grid[i][j]!=0 && grid[i][j+1]!=0) {
                    res = Math.min(res, Math.max(a[i][j], b[i][j+1]));
                }
                if (i+1 < n && grid[i][j]!=0 && grid[i+1][j]!=0) {
                    res = Math.min(res, Math.max(a[i][j], b[i+1][j]));
                }
            }
        }
        if (res != Integer.MAX_VALUE)
            System.out.println(res); 
        else 
            System.out.println(-1); 
    }
}