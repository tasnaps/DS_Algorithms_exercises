package toinenViikko;

// TRAII_22s_X2_testi.java SJ
// Testiluokka TRAII 2020 tehtÃ¤viin X2 ja 5-7


import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class TRAII_22s_X2_testi {

    static TRAII_22s_X2 testattava = new TRAII_22s_X2_pohja();
    //                                              ^^^^ oma tunnus tÃ¤hÃ¤n


    public static void main(String[] args) {

        // montako alkiota pakkaan korkeintaan laitetaan pohjalle
        int testiMax = 100000;
        if (args.length > 0)
            testiMax = Integer.parseInt(args[0]);

        // satunnaislukusiemen
        int siemen = 42;
        if (args.length > 1)
            siemen = Integer.parseInt(args[1]);
        Random rnd = new Random(siemen);

        // tulostusten mÃ¤Ã¤rÃ¤
        int print = 3;
        if (args.length > 2)
            print = Integer.parseInt(args[2]);


        // kutsutaan ensin ilman tulostuksia kutakin 2 sekunnin ajan
        lammita(new ArrayDeque<>(), testiMax, rnd, 1);
        lammita(new LinkedList<>(), testiMax, rnd, 1);
        lammita(new ConcurrentLinkedDeque<>(), testiMax, rnd, 1);

        // sitten varsinainen testiajo muutamalla eri kokoelmalla
        System.out.println("\nTestataan ArrayDeque:llÃ¤, pitÃ¤isi olla vakioaikainen (pl. vÃ¤limuistit)");
        testaaX2(new ArrayDeque<>(), testiMax, 10, 0, rnd, print);


        System.out.println("\nTestataan LinkedList:llÃ¤, pitÃ¤isi olla vakioaikainen (pl. vÃ¤limuistit)");
        testaaX2(new LinkedList<>(), testiMax, 10, 0, rnd, print);

        System.out.println("\nTestataan ConcurrentLinkedDeque:llÃ¤, pitÃ¤isi olla vakioaikainen (pl. vÃ¤limuistit)");
        testaaX2(new ConcurrentLinkedDeque<>(), testiMax/10, 10, 0, rnd, print);

    } // main()



    /**
     * Testaa tehtÃ¤vÃ¤Ã¤ syÃ¶tekoolla 1..n
     * @param n maksimi syÃ¶tekoko
     * @param kerroin alkiomÃ¤Ã¤rÃ¤n kasvatuskerroin
     * @param loppu ajanhetki (System.nanoTime) jonka jÃ¤lkeen keskeytetÃ¤Ã¤n
     * @param rnd satunnaislukugeneraattori
     * @param print tulostusten mÃ¤Ã¤rÃ¤
     */
    static void testaaX2(Deque<Integer> D, int n, int kerroin, long loppu, Random rnd, int print) {
        int k = 1;
        while (D.size() < n) {
            while (D.size() < k)
                D.add(rnd.nextInt());
            testaaX2(D, print);
            if (loppu > 0 && System.nanoTime() > loppu)
                break;
            k *= kerroin;
        }
    }



    /**
     * Kutsuu testausta ilman tulostuksia kunnes annettu aika on kuulunut.
     * @param n maksimi syÃ¶tekoko
     * @param rnd satunnaislukugeneraattori
     * @param sek suorituksen kesto
     */
    static void lammita(Deque<Integer> C, int n, Random rnd, int sek) {

        System.out.println("LÃ¤mmitys alkaa " + sek + "s");
        long loppu = System.nanoTime() + sek*1000L*1000*1000;
        while (System.nanoTime() < loppu)
            testaaX2(C, n, 2, loppu, rnd, 0);
        System.out.println("LÃ¤mmitys loppuu");
    }


    /**
     * Testaa tehtÃ¤vÃ¤Ã¤ X2 kokoelmalla D
     * @param D valmis kokoelma jota testataan
     * @param print tulostusten mÃ¤Ã¤rÃ¤
     */
    static void testaaX2(Deque<Integer> D, int print) {

        if (print > 1)
            System.out.println("Testi, Deque = " + D.getClass().toString() + " n = " + D.size());

        long alku = System.nanoTime(); // mitataan testin aika

        // kutsutaan varsinaista mittausta
        long tulos = testattava.offerPollAika(D);

        long loppu = System.nanoTime();

        if (print > 0) System.out.println("  offerPollAika (tulos) = " + tulos + " ns");

        if (print > 0 && loppu-alku > 100L*1000*1000)
            System.out.println("Varoitus: testi oli liian hidas (yli 0,1s)");

        if (print > 3)  // sÃ¤Ã¤dÃ¤ tÃ¤stÃ¤ jos haluat nÃ¤hdÃ¤ paljonko testisi kesti
            System.out.println("Testi kesti " + ((loppu-alku)/1000000.0) + " ms");


    }


}

