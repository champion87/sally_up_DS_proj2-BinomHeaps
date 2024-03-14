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
            System.out.println(i);
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

        // insert elements in random order
        for (int i = 0; i < n; i++)
            melds += b.insert(ar[i], null);
        
        b.print();
        // delete
        for (int i = 0; i < n / 2; i++)
        {
            //System.out.println(i);
            res = b.deleteMin();
            //System.out.println();
            //b.print();
            melds += res[1];
            ranks += res[0];
        }

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
