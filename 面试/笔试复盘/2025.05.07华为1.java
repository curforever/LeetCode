// 100分 95%
/*

*/

// We have imported the necessary tool classes.
// If you need to import additional packages or classes, please import here.

public class Main {
    static class Charger {
        int id;
        int x, y;
        int distance;
        public Charger(int id, int x, int y, int carX, int carY) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.distance = Math.abs(x-carX) + Math.abs(y-carY);
        }
    }
    
    public static void main(String[] args) {
    // please define the JAVA input here. For example: Scanner s = new Scanner(System.in);
    // please finish the function body here.
    // please define the JAVA output here. For example: System.out.println(s.nextInt());
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        int n = sc.nextInt();
        if (k == 0 || k > n) {
            System.out.println("null");
            return;
        }
        int carX = sc.nextInt();
        int carY = sc.nextInt();
        List<Charger> chargers = new ArrayList<>();
        
        for (int i = 1; i <= n; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            chargers.add(new Charger(i, x, y, carX, carY));
        }
        
        chargers.sort((a,b) -> {
           if (a.distance != b.distance)
               return Integer.compare(a.distance, b.distance);
           return Integer.compare(a.id, b.id);
        });
        
        for (int i = 0; i < k; i++) {
            Charger c = chargers.get(i);
            System.out.print(c.id +" "+ c.x +" "+ c.y +" "+ c.distance + "\n");
            // System.out.println();
        }
        
        sc.close();
    }
}