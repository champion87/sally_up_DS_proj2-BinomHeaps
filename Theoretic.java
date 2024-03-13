import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Theoretic {
    public static void main(String[] args) {
        test2(10);

        /*
         * 
         * WHAT WE NEED TO ADD:
         * - LINK COUNTER FOR MELD - TO RETURN
         * - RANK COUNTER FOR DELETEMIN - TO RETURN
         * 
         */
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
            start = System.currentTimeMillis();
            test1(n);
            end = System.currentTimeMillis();
            
            System.out.println("time:");
            System.out.println(end - start);
        }
        System.out.println();
    }

    static void loop2()
    {
        long start;
        long end;
        int n;

        System.out.println("TEST 1");
        for (int i = 1; i <= 6; i++)
        {
            n = (int)Math.pow(3, i + 5) - 1;
            System.out.println(i);
            start = System.currentTimeMillis();
            test2(n);
            end = System.currentTimeMillis();
            
            System.out.println("time:");
            System.out.println(end - start);
        }
        System.out.println();
    }

    static void loop3()
    {
        long start;
        long end;
        int n;

        System.out.println("TEST 1");
        for (int i = 1; i <= 6; i++)
        {
            n = (int)Math.pow(3, i + 5) - 1;
            System.out.println(i);
            start = System.currentTimeMillis();
            test3(n);
            end = System.currentTimeMillis();
            
            System.out.println("time:");
            System.out.println(end - start);
        }
        System.out.println();
    }

    static void test1(int n)
    {
        BinomialHeap b = new BinomialHeap();
        for (int i = 1; i <= n; i++)
            b.insert(i, null);

        System.out.print("Num Trees: ");
        System.out.println(b.numTrees());
        // TODO: PRINT MELDS
    }

    static void test2(int n)
    {
        BinomialHeap b = new BinomialHeap();
        List<Integer> al = new ArrayList<>();
        for (int i = 1; i <= n; i++)
            al.add(i);
        Collections.shuffle(al);
        Integer[] ar = new Integer[n];
        al.toArray(ar);

        // insert elements in random order
        for (int i = 0; i < n; i++)
            b.insert(ar[i], null);
        
        // delete
        for (int i = 0; i < n / 2; i++)
            b.deleteMin();

        System.out.println(ar[0]);

        System.out.print("Num Trees: ");
        System.out.println(b.numTrees());
        // TODO: PRINT MELDS
    }

    static void test3(int n)
    {
        BinomialHeap b = new BinomialHeap();

        for (int i = n; i > 0; i++)
            b.insert(i, null);

        while (b.size() > 31)
            b.deleteMin();

        System.out.print("Num Trees: ");
        System.out.println(b.numTrees());
        // TODO: PRINT MELDS
        // TODO: PRINT SUM DELETED RANKS
    }
}
