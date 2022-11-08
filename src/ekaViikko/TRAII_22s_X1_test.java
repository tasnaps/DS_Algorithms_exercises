package ekaViikko;

import fi.uef.cs.tra.BTree;
import fi.uef.cs.tra.BTreeNode;

import java.util.Random;



public class TRAII_22s_X1_test {

    private static Random rnd = new Random();

    static TRAII_22s_X1 testattava = new TRAII_22s_X1_pohja(); /* <-- Oma tunnus tÃ¤hÃ¤n */

    public static void main(String[] args) {

        // alkioiden mÃ¤Ã¤rÃ¤
        int N = 10;
        if (args.length > 0)
            N = Integer.parseInt(args[0]);

        // satunnaislukusiemen
        int siemen = 42;
        if (args.length > 1)
            siemen = Integer.parseInt(args[1]);

        // satunnaislukusiemen
        int testeja = 10;
        if (args.length > 1)
            testeja = Integer.parseInt(args[1]);

        rnd.setSeed(siemen);

        boolean ok = true;

        // testataan ensin ehjillÃ¤ keoilla
        for (int i = 0; i < testeja; i++)
            ok &= testaa(N+i, false);

        // sitten rikkinÃ¤isillÃ¤
        for (int i = 0; i < testeja; i++)
            ok &= testaa(N+i, true);

        // sitten pienillÃ¤ keoilla
        for (int i = 0; i < testeja; i++)
            ok &= testaa(i, false);
        for (int i = 0; i < testeja; i++)
            ok &= testaa(i, true);

        // ja lopuksi vielÃ¤ vaihtuvilla keoilla
        rnd.setSeed(System.currentTimeMillis());

        for (int i = 0; i < testeja; i++)
            ok &= testaa(N+i, false);
        for (int i = 0; i < testeja; i++)
            ok &= testaa(N+i, true);

        // ja vielÃ¤ vÃ¤hÃ¤n isommilla keoilla

        for (int i = 0; i < testeja; i++)
            ok &= testaa(N*1000, rnd.nextBoolean());


        // ja vielÃ¤ liukuluvuilla
        for (int i = 0; i < testeja; i++)
            ok &= testaaD(N*10, rnd.nextBoolean());


        if (ok)
            System.out.println("\nKaikki testit ok");
        else
            System.out.println("\nJoissain testeissÃ¤ virheitÃ¤");

    }


    /**
     * Testaa n-kokoisella keolla.
     * @param n keon alkioiden mÃ¤Ã¤rÃ¤
     * @param riko yritetÃ¤Ã¤nkÃ¶ keko rikkoa vai ei
     */
    private static boolean testaa(int n, boolean riko) {
        System.out.print("Testi n=" + n + " : ");
        BTree<Integer> T = teeKeko(n);
        if (riko) {
            if (rikoKeko(T))
                System.out.print("Rikottu: ");
            else {
                System.out.print("Ei rikottu: ");
                riko = false;
            }
        }
        return testaa(T, !riko);
    }

    private static boolean testaaD(int n, boolean riko) {
        System.out.print("TestiD n=" + n + " : ");
        BTree<Double> T = teeDKeko(n);
        if (riko) {
            if (rikoKeko(T))
                System.out.print("Rikottu: ");
            else {
                System.out.print("Ei rikottu: ");
                riko = false;
            }
        }
        return testaa(T, !riko);
    }



    /** kutsuu onkoKeko -metodia
     * @param T keko
     * @param <E> alkiotyyppi
     */
    private static <E extends Comparable<? super E>> boolean testaa(BTree<E> T, boolean odotus) {
        boolean tulos = testattava.onkoKeko(T);
        System.out.println("Onko keko: " + tulos);
        if (tulos != odotus) {
            System.out.println("VÃ¤Ã¤rin: piti olla " + odotus);
            return false;
        } else
            return true;

    }


    /** Rikkoo keon jos pystyy.
     *
     * @param T  keko
     * @param <E> alkiotyyppi
     * @return true jos onnistui, muuten false
     */
    private static <E extends Comparable<? super E>> boolean rikoKeko(BTree<E> T) {

        BTreeNode<E> solmu = T.getRoot();
        if (solmu == null ||
                (solmu.getRightChild() == null && solmu.getLeftChild() == null))
            return false; // 0-1 solmun puusta ei voi kekoa rikkoa

        // yritetÃ¤Ã¤n max 10 kertaa
        for (int yritys = 0; yritys < 10; yritys++) {

            // tehdÃ¤Ã¤n virhe satunnaiselle tasolle
            int taso = rnd.nextInt(4) + 1;
            BTreeNode<E> vanhempi = null;
            while (taso > 0) {

                // jos tullaan lehtisolmuun, ei voida mennÃ¤ syvemmÃ¤lle
                if (solmu.getLeftChild() == null && solmu.getRightChild() == null)
                    break;

                vanhempi = solmu;

                // jos vain toinen, mennÃ¤Ã¤n sinne, muuten satunnaiseen lapseen
                if (solmu.getLeftChild() == null)
                    solmu = solmu.getRightChild();
                else if (solmu.getRightChild() == null || rnd.nextBoolean())
                    solmu = solmu.getLeftChild();
                else
                    solmu = solmu.getRightChild();
                taso--;
            }

            if (vanhempi == null || vanhempi.getElement().equals(solmu.getElement()))
                continue;   // vaihdettavat ovat tasa-arvoiset, ei onnistunut nyt

            // vaihdetaan lapsen ja vanhemman alkiot keskenÃ¤Ã¤n
            E tmp = vanhempi.getElement();
            vanhempi.setElement(solmu.getElement());
            solmu.setElement(tmp);

            return true;    // rikkominen onnistui

        }
        return false;   // ei onnistunut

    }   // rikoKeko()


    /**
     * Tekee keon
     * @param n alkioiden mÃ¤Ã¤rÃ¤
     * @return keko
     */
    private static BTree<Integer> teeKeko(int n) {
        BTree<Integer> T = new BTree<>();

        int i = 0;
        int x = 0;
        while (i < n) {
            x = x + rnd.nextInt(3) ;
            if (lisaaKekoon(T, x))
                i++;
        }
        return T;
    }

    private static BTree<Double> teeDKeko(int n) {
        BTree<Double> T = new BTree<>();

        int i = 0;
        double x = 0;
        while (i < n) {
            x = x + rnd.nextDouble()*3.0;
            if (lisaaKekoon(T, x))
                i++;
        }
        return T;
    }



    /**
     * LisÃ¤Ã¤ alkion keon lehtisolmuksi jos se on mahdollista.
     *
     * @param T syÃ¶tekeko
     * @param x lisÃ¤ttÃ¤vÃ¤ alkio
     * @param <E> alkiotyyppi
     * @return true jos lisÃ¤ys onnistui, muuten false
     */
    private static <E extends Comparable<? super E>> boolean lisaaKekoon(BTree<E> T, E x) {

        if (T.getRoot() == null) {
            T.setRoot(new BTreeNode<E>(x));
            return true;
        }

        BTreeNode<E> n = T.getRoot();
        while (true) {
            // jos n.elem suurempi kuin x, ei onnistu ilman siirtelyjÃ¤
            if (n.getElement().compareTo(x) > 0)
                return false; // eikÃ¤ jakseta siirrellÃ¤...

            // laitetaan vasemmaksi tai oikeaksi lapseksi jos ovat tyhjiÃ¤
            if (n.getLeftChild() == null) {
                n.setLeftChild(new BTreeNode<>(x));
                return true;
            } else if (n.getRightChild() == null) {
                n.setRightChild(new BTreeNode<>(x));
                return true;

                // muuten jatketaan tarkastelua suuremman lapsen kohdalla
            } else if (n.getRightChild().getElement().compareTo(n.getLeftChild().getElement()) > 0)
                n = n.getRightChild();
            else
                n = n.getLeftChild();

        }

    }   // lisaaKekoon()

} // class()
