import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Theoretic {
    public static void main(String[] args) {
        System.nanoTime();
        loop2();
    }

    static void loop1()
    {
        long start;
        long end;
        int n;

        System.out.println("TEST 1");
        for (int i = 1; i <= 6; i++)
        {
            n = (int)Math.pow(3, i + 5) - 1;
            System.out.println(i);
            start = System.nanoTime();
            test1(n);
            end = System.nanoTime();
            
            System.out.println("time: " + ((end - start) / 1000));
            System.out.println();
        }
        System.out.println();
    }

    static void loop2()
    {
        long start;
        long end;
        int n;

        System.out.println("TEST 2");
        for (int i = 1; i <= 6; i++)
        {
            //n = (int)Math.pow(3, i + 5) - 1;
            n = (int)Math.pow(3, i) - 1;
            start = System.nanoTime();
            if (i == 4) test2(n);
            end = System.nanoTime();
            
            System.out.println("time: " + ((end - start) / 1000));
        }
        System.out.println();
    }

    static void loop3()
    {
        long start;
        long end;
        int n;

        System.out.println("TEST 3");
        for (int i = 1; i <= 6; i++)
        {
            n = (int)Math.pow(3, i + 5) - 1;
            System.out.println(i);
            start = System.nanoTime();
            test3(n);
            end = System.nanoTime();
            
            System.out.println("time: " + ((end - start) / 1000));
            System.out.println();
        }
        System.out.println();
    }

    static void test1(int n)
    {
        BinomialHeap b = new BinomialHeap();
        int melds = 0;

        for (int i = 1; i <= n; i++)
        {
            melds += b.insert(i, null);
        }

        System.out.print("Num Trees: ");
        System.out.println(b.numTrees());
        System.out.println("Melds: " + melds);
    }

    static void test2(int n)
    {
        BinomialHeap b = new BinomialHeap();
        List<Integer> al = new ArrayList<>();
        int[] res = {0, 0};
        int melds = 0;
        int ranks = 0;

        for (int i = 1; i <= n; i++)
            al.add(i);
        
        Collections.shuffle(al);
        Integer[] ar = new Integer[n];
        al.toArray(ar);
        ar = new Integer[] {61, 32, 4, 62, 18, 75, 72, 14, 36, 48, 26, 15, 
            37, 31, 3, 7, 41, 45, 80, 69, 47, 17, 50, 12, 77, 38, 44, 52, 66, 56, 25, 68, 23, 49, 11, 60, 6, 59, 34, 2, 64, 28, 71, 21, 51, 46, 67, 79, 29, 
            8, 10, 22, 9, 78, 53, 74, 16, 27, 58, 19, 13, 73, 55, 54, 40, 30, 33, 39, 42, 24, 63, 1, 5, 35, 
            43, 57, 76, 70, 20, 65};

        // insert elements in random order
        for (int i = 0; i < n; i++)
            melds += b.insert(ar[i], null);
        
        b.print();
        // delete
        for (int i = 0; i < n / 2; i++)
        {
            if (i == 20)
                break;
            if (i == 17 || i == 16 || i == 3)
            {
                System.out.println("sdjyfhg");
                b.print();
                System.out.println("min: " + b.min.item.key + "\n\n");
            }
            System.out.println(i);
            res = b.deleteMin();
            //System.out.println();
            //b.print();
            melds += res[1];
            ranks += res[0];
        }
        //b.print();
        //b.deleteMin();

        System.out.println("Num Trees: " + b.numTrees());
        System.out.println("Melds: " + melds);
        System.out.println("Ranks: " + ranks);
    }

    static void test3(int n)
    {
        BinomialHeap b = new BinomialHeap();
        int[] res = {0, 0};
        int melds = 0;
        int ranks = 0;

        for (int i = n; i > 0; i--)
            melds += b.insert(i, null);

        while (b.size() > 31)
        {
            res = b.deleteMin();
            melds += res[1];
            ranks += res[0];
        }

        System.out.print("Num Trees: ");
        System.out.println(b.numTrees());
        System.out.println("Melds: " + melds);
        System.out.println("Ranks: " + ranks);
    }
}
