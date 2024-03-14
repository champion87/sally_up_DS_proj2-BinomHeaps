import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Theoretic {
    public static void main(String[] args) {
        test1(8);

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
        {
            b.insert(i, null);
        }

        b.print();
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

        System.out.println(al.size());
        
        Collections.shuffle(al);
        Integer[] ar = new Integer[n];
        al.toArray(ar);
        System.out.println(ar.length);

        // insert elements in random order
        for (int i = 0; i < n; i++)
        {
            b.insert(ar[i], null);
            System.out.print(ar[i] + " ");
        }    
        System.out.println("\nsize: " + b.size());
        
        b.print();
        System.out.println("sedrftjgred");
        // delete
        for (int i = 0; i < n / 2; i++)
        {
            b.deleteMin();
            System.out.print(b.size() + " ");
        }

        b.print();
        System.out.print("size: " + b.size() + " Num Trees: ");
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
