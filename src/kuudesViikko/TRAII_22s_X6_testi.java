package kuudesViikko;

import java.util.*;

public class TRAII_22s_X6_testi {

    static TRAII_22s_X6 testattava = new TRAII_22s_X6_pohja();

    static Random rnd = new Random();

    public static void main(String[] args) {

        // komentoriviparametrina saatu lukujen mÃ¤Ã¤rÃ¤ ja koko
        int N = 20;
        if (args.length > 0)
            N = Integer.parseInt(args[0]);

        int M = N;
        if (args.length > 1)
            M = Integer.parseInt(args[1]);

        // satunnaislukusiemen
        int siemen = (int) System.currentTimeMillis();
        if (args.length > 2)
            siemen = Integer.parseInt(args[2]);
        rnd.setSeed(siemen);

        // tulostusten mÃ¤Ã¤rÃ¤
        int print = 5;
        if (args.length > 3)
            print = Integer.parseInt(args[3]);


        // kutsutaan N kokoisilla syÃ¶tteillÃ¤, pienillÃ¤ luvuilla
        int nTest = 5;
        int eroYht = 0;
        int kaikkiErotYht = 0;
        int kaikkiTestit = 0;
        boolean ok = true;
        for (int i = 0; i < nTest; i++) {
            int ero = testaa(teeTasaTasaSyote(N, N * M), print, 3);
            if (ero < 0)
                ok = false;
            eroYht += ero;
        }
        kaikkiErotYht += eroYht;
        kaikkiTestit += nTest;
        if (ok)
            System.out.format("\nEro keskimÃ¤Ã¤rin %.2f\n",  1.0*eroYht/nTest);
        else
            System.out.println("\nJossain testissÃ¤ virheellinen tulos");

        // kutsutaan N kokoisilla syÃ¶tteillÃ¤, isommilla luvuilla
        eroYht = 0;
        ok = true;
        for (int i = 0; i < nTest; i++) {
            int ero = testaa(teeTasaTasaSyote(N, N * M * 20), print, 3);
            if (ero < 0)
                ok = false;
            eroYht += ero;
        }
        kaikkiErotYht += eroYht;
        kaikkiTestit += nTest;
        if (ok)
            System.out.format("\nEro keskimÃ¤Ã¤rin %.2f\n",  1.0*eroYht/nTest);
        else
            System.out.println("\nJossain testissÃ¤ virheellinen tulos");

        // kutsutaan 100 kertaa isommilla syÃ¶tteillÃ¤
        eroYht = 0;
        for (int i = 0; i < nTest; i++) {
            int ero = testaa(teeTasaTasaSyote(N*100, N * M * 4*10000), print, 3);
            if (ero < 0)
                ok = false;
            eroYht += ero;
        }
        kaikkiErotYht += eroYht;
        kaikkiTestit += nTest;
        if (ok)
            System.out.format("\nEro keskimÃ¤Ã¤rin %.2f\n" , 1.0*eroYht/nTest);
        else
            System.out.println("\nJossain testissÃ¤ virheellinen tulos");



        // kutsutaan 100000 kertaa isommilla syÃ¶tteillÃ¤
        eroYht = 0;
        for (int i = 0; i < nTest; i++) {
            int ero = testaa(teeTasaTasaSyote(N*10000, N * M * 4*1000000), print, 3);
            if (ero < 0)
                ok = false;
            eroYht += ero;
        }
        kaikkiErotYht += eroYht;
        kaikkiTestit += nTest;
        if (ok)
            System.out.format("\nEro keskimÃ¤Ã¤rin %.2f\n" , 1.0*eroYht/nTest);
        else
            System.out.println("\nJossain testissÃ¤ virheellinen tulos");

        if (ok)
            System.out.format("\nEro kaikissa %d testeissÃ¤ keskimÃ¤Ã¤rin %.2f\n" , kaikkiTestit, 1.0*kaikkiErotYht/kaikkiTestit);



    }


    static int testaa(ArrayList<Integer> syote, int print, int maxAika) {

        if (syote.size() % 2 != 0)
            System.out.println("Varoitus: parittoman mittainen syÃ¶te");

        int syoteSumEnnen = sum(syote);

        if (print > 0)
            System.out.println("\nTesti, n=" + syote.size() + " sum=" + syoteSumEnnen + " maxAika=" + maxAika);
        if (print > 3 && syote.size() <= 30)
            System.out.println("SyÃ¶te: " + syote);

        Iterator<Integer> li = syote.iterator();

        ArrayList<Integer> tulos1 = new ArrayList<>(syote.size() / 2 + 2);
        ArrayList<Integer> tulos2 = new ArrayList<>(syote.size() / 2 + 2);

        // mitataan myÃ¶s aika ja huomautetaan jos yliaikaa
        long alku = System.currentTimeMillis();

        testattava.jaaTasanKahteenTasakokoiseen(syote, tulos1, tulos2, maxAika);

        long aika = System.currentTimeMillis() - alku;
        if (aika > maxAika*1300L) // 30% pelivaraa
            System.out.println("Turhan pitkÃ¤ suoritus: " + (aika/1000.0) + "s (max=" + maxAika + "s)");

        int sum1 = sum(tulos1);
        int sum2 = sum(tulos2);
        int ero = Math.abs(sum1 - sum2);

        if (print > 0)
            System.out.println("summa1=" + sum1 + " summa2=" + sum2 + " ero=" + ero);

        if (print > 3 && tulos1.size() <= 30)
            System.out.println("tulos1: " + tulos1);
        if (print > 3 && tulos2.size() <= 30)
            System.out.println("tulos2: " + tulos2);


        int syoteSumJalkeen = sum(syote);

        if (syoteSumEnnen != syoteSumJalkeen) {
            System.out.println("  SyÃ¶tettÃ¤ on muutettu!");
            ero = -1;
        }

        try {
            boolean hN = li.hasNext();
        } catch (ConcurrentModificationException e) {
            System.out.println("  SyÃ¶tettÃ¤ on muutettu!");
            ero = -1;
        }


        if (syoteSumJalkeen != sum1 + sum2) {
            System.out.println("  tulosten yhteissumma ei tÃ¤smÃ¤Ã¤!");
            ero = -2;
        }

        if (! onkoSamat(syote, tulos1, tulos2, print)) {
            System.out.println("  tuloksissa ei ole samat alkiot kuin syÃ¶tteessÃ¤!");
            ero = -3;
        }

        if (tulos1.size() != tulos2.size()) {
            System.out.println("  tuloksissa eri mÃ¤Ã¤rÃ¤ alkioita: " + tulos1.size() + "+" + tulos2.size() +
                    " (piti olla: " + syote.size()/2);
        }


        return ero;

    }


    /**
     * Kokoelman alkioiden summa
     * @param L syÃ¶tekokoelma
     * @return alkioiden summa
     */
    static int sum(Collection<Integer> L) {
        int s = 0;
        for (int x : L)
            s += x;
        return s;
    }


    /**
     * Luo satunnaisen kokoelman
     * @param n alkiomÃ¤Ã¤rÃ¤
     * @param min pienin alkio
     * @param max suurin alkio
     * @return kokonaislukukokoelma
     */
    static Collection<Integer> teeSyote(int n, int min, int max) {
        List<Integer> L = new ArrayList<>(n);

        if (min < 1) min = 1;
        if (max < min) max = min;
        int k = max-min+1;

        for (int i = 0; i < n; i++)
            L.add(rnd.nextInt( k)+min);

        return L;
    }




    /**
     * Tekee kokoelman joka on jaoteltavissa kahteen summaltaan yhtÃ¤ suureen osaan.
     * @param n alkioiden mÃ¤Ã¤rÃ¤
     * @param sum likimÃ¤Ã¤rÃ¤inen kokonaissumma
     * @return kokonaislukukokoelma
     */
    static ArrayList<Integer> teeTasaSyote(int n, int sum) {
        ArrayList<Integer> L = new ArrayList<>(n);

        LinkedList<Integer> L1 = new LinkedList<>();
        LinkedList<Integer> L2 = new LinkedList<>();

        if (n < 2) n = 3;
        if (sum < 2*n) sum = 2*n;

        int avg = sum/n;
        int ala = 2*avg/4;

        int sum1 = 0;
        int sum2 = 0;

        int i = 0;
        while (i < n-2) {
            int x = rnd.nextInt(avg) + ala;
            if (rnd.nextBoolean()) {
                L1.add(x);
                sum1 += x;
            } else {
                L2.add(x);
                sum2 += x;
            }
            i++;
        }

        int ero = sum1 - sum2;
        int x = rnd.nextInt(avg) + 1;
        if (ero < 0) {
            L1.add(x-ero);
            sum1 += x-ero;
            L2.add(x);
            sum2 += x;
        } else {
            L1.add(x);
            sum1 += x;
            L2.add(x+ero);
            sum2 += x+ero;
        }

        while (!L1.isEmpty() || !L2.isEmpty()) {
            if (L1.isEmpty() || (rnd.nextBoolean() && !L2.isEmpty()))
                L.add(L2.removeLast());
            else
                L.add(L1.removeLast());
        }

        return L;
    }


    /**
     * Tekee kokoelman joka on jaoteltavissa kahteen summaltaan yhtÃ¤ suureen osaan.
     * @param n alkioiden mÃ¤Ã¤rÃ¤
     * @param sum likimÃ¤Ã¤rÃ¤inen kokonaissumma
     * @return kokonaislukukokoelma
     */
    static ArrayList<Integer> teeTasaTasaSyote(int n, int sum) {
        ArrayList<Integer> L = new ArrayList<>(n);

        LinkedList<Integer> L1 = new LinkedList<>();
        LinkedList<Integer> L2 = new LinkedList<>();

        if (n < 4) n = 4;
        if (n % 2 != 0)
            n+=1;

        if (sum < 2*n) sum = 2*n;

        int avg = sum/n;
        int ala = 2*avg/4;

        int sum1 = 0;
        int sum2 = 0;

        int i = 0;
        while (i < n-2) {
            int x = rnd.nextInt(avg) + ala;
            if (i % 2 == 0) {
                L1.add(x);
                sum1 += x;
            } else {
                L2.add(x);
                sum2 += x;
            }
            i++;
        }

        int ero = sum1 - sum2;
        int x = rnd.nextInt(avg) + 1;
        if (ero < 0) {
            L1.add(x-ero);
            sum1 += x-ero;
            L2.add(x);
            sum2 += x;
        } else {
            L1.add(x);
            sum1 += x;
            L2.add(x+ero);
            sum2 += x+ero;
        }

        // System.out.println("Gener: n=" + n + " avg=" + avg + " sum1=" + sum1 + " sum2=" + sum2 + " |L1|=" + L1.size() + " |L2|=" + L2.size());
        while (!L1.isEmpty() || !L2.isEmpty()) {
            if (L1.isEmpty() || (rnd.nextBoolean() && !L2.isEmpty()))
                L.add(rnd.nextBoolean() ? L2.removeLast() : L2.removeFirst());
            else
                L.add(rnd.nextBoolean() ? L1.removeLast() : L1.removeFirst());
        }

        return L;
    }



    /**
     * Onko kaikki = L1 + L2
     * @param kaikki kaikki alkiot
     * @param L1 osa alkioista
     * @param L2 loput alkiot
     * @param print tulostuksen mÃ¤Ã¤rÃ¤ (ei kÃ¤ytÃ¶ssÃ¤)
     * @param <E> alkiotyyppi
     * @return true jos samat alkiot, muuten false
     */
    static <E> boolean onkoSamat(Collection<E> kaikki, Collection<E> L1, Collection<E> L2, int print) {
        HashMap<E, Integer> MK = new HashMap<>(kaikki.size()*2);
        HashMap<E, Integer> M12 = new HashMap<>(L1.size()*2);
        for (E x : kaikki)
            MK.compute(x, (k, v) -> v == null ? 1 : v + 1);
        for (E x : L1)
            M12.compute(x, (k, v) -> v == null ? 1 : v + 1);
        for (E x : L2)
            M12.compute(x, (k, v) -> v == null ? 1 : v + 1);
        return MK.equals(M12);
    }

}

