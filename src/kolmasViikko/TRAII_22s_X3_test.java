package kolmasViikko;

import java.util.*;

/**
 * Testiluokka tehtÃ¤vÃ¤Ã¤n X3
 */
public class TRAII_22s_X3_test {

    public static void main(String[] args) {

        // nÃ¤itÃ¤ voi toki vaihdella
        // minimi testattava alkiomÃ¤Ã¤rÃ¤
        int N1 = 10;
        if (args.length > 0)
            N1 = Integer.parseInt(args[0]);

        // maksimi testattava alkiomÃ¤Ã¤rÃ¤
        int N2 = 2000000;
        if (args.length > 1)
            N2 = Integer.parseInt(args[1]);

        // lasketaanko kakkosen potenssi, vai esim 10
        int kerroin = 2;
        if (args.length > 2)
            kerroin = Integer.parseInt(args[2]);

        TRAII_22s_X3 testattava = new TRAII_22s_X3_pohja(); // oma tunnus tÃ¤hÃ¤n

        // testataan parilla eri kokoelmalla

        testaa(testattava, new ArrayList<>(), N1, N2, kerroin, false);
        testaa(testattava, new ArrayList<>(), N1, N2, kerroin, true);

        testaa(testattava, new TreeSet<>(), N1, N2, kerroin, false);
        testaa(testattava, new TreeSet<>(), N1, N2, kerroin, true);

        testaa(testattava, new HashSet<>(), N1, N2, kerroin, false);
        testaa(testattava, new HashSet<>(), N1, N2, kerroin, true);

    }


    /**
     * Testaa tehtÃ¤vÃ¤ X3 yhdellÃ¤ listalla.
     *
     * @param testattava testattava mittausluokka
     * @param C          testattava lista
     * @param min        alkiomÃ¤Ã¤rÃ¤n alaraja
     * @param max        alkiomÃ¤Ã¤rÃ¤n ylÃ¤raja
     * @param kerroin
     * @param loytyy
     */
    static void testaa(TRAII_22s_X3 testattava, Collection<Double> C, int min, int max, int kerroin, boolean loytyy) {
        System.out.println("\nTestataan " + C.getClass().getName() + " " + min + ".." + max +
                " lÃ¶ytyy="+loytyy);

        SortedMap<Integer, Long> tulos = new TreeMap<>();
        for (int i = min; i <= max; i *= kerroin)
            tulos.put(i, 0L);


        // kutsutaan mittausta
        long alku = System.nanoTime();
        testattava.containsNopeus(C, tulos, loytyy);
        long aika = System.nanoTime() - alku;


        // tulostetaan mittaukseen mennyt aika, max 1s/alkiomÃ¤Ã¤rÃ¤ oli tarkoitus kÃ¤yttÃ¤Ã¤
        System.out.format("Mittaus kesti %.2f s/alkiomÃ¤Ã¤rÃ¤%n", (1.0*aika / (1000.0*1000*1000*tulos.keySet().size())));

        // tulostetaan tuloskuvauksen sisÃ¤ltÃ¶
        System.out.println("        n       ns  ns/n");
        for (Map.Entry<Integer, Long> e : tulos.entrySet()) {
            System.out.format("%9d  %7d  %.2f%n",  e.getKey(), e.getValue(),
                    1.0*e.getValue()/e.getKey());
        }
    }


}
