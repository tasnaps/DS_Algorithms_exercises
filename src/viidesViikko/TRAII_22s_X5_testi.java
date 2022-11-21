package viidesViikko;

// TRAII_22s_X5_testi.java SJ

import fi.uef.cs.tra.DiGraph;
import fi.uef.cs.tra.Vertex;

import java.util.LinkedList;
import java.util.Random;

public class TRAII_22s_X5_testi {

    static TRAII_22s_X5 testattava = new TRAII_22s_X5_tapiomer();
    //                                              ^^^^ oma tunnus tÃ¤hÃ¤n

    static Random rnd = new Random();

    public static void main(String[] args) {

        // komentoriviparametrina saatu puun koko
        int N = 10;
        if (args.length > 0)
            N = Integer.parseInt(args[0]);

        // satunnaislukusiemen
        int siemen = 42;
        if (args.length > 1)
            siemen = Integer.parseInt(args[1]);
        rnd.setSeed(siemen);

        // tulostusten mÃ¤Ã¤rÃ¤
        int print = 5;
        if (args.length > 2)
            print = Integer.parseInt(args[2]);


        int nTest = 200;
        int k;
        int virheet = 0;


        rnd.setSeed(siemen);
        boolean ok = true;

        ok &= testaaPainavinPuu(0, print);
        ok &= testaaPainavinPuu(1, 0, false, print);
        ok &= testaaPainavinPuu(1, 3, false, print);
        ok &= testaaPainavinPuu(2, 4, false, print);
        ok &= testaaPainavinPuu(3, 2, true, print);
        ok &= testaaPainavinPuu(3, 0, true, print);
        ok &= testaaPainavinPuu(3, 5, false, print);
        ok &= testaaPainavinPuu(N, N, true, print);



        rnd.setSeed(System.currentTimeMillis());
        k = 0;
        while (k < nTest) {
            k++;
            int np = rnd.nextInt(k+1);

            if (!testaaPainavinPuu(np, np/2, true, 0))
                virheet++;
            if (virheet > 30)
                break;
        }
        if (virheet > 0)
            ok = false;
        System.out.println("\n" + k + " satunnaisesta puumÃ¤Ã¤rÃ¤testistÃ¤ " + (k - virheet) + " oikein.");

        if (ok)
            System.out.println("\nKaikki tehdyt puumÃ¤Ã¤rÃ¤testit antoivat oikean tuloksen.");
        else
            System.out.println("\nJoissain puumÃ¤Ã¤rÃ¤testeissÃ¤ virheitÃ¤.");




    } // main()


    static DiGraph teeSyote(int puita, LinkedList<Float> maxPaino) {
        return teeSyote(puita, rnd.nextInt(puita+1), false, maxPaino);
    }

    static DiGraph teeSyote(int puita, int puuKoko, boolean sekoita, LinkedList<Float> maxPaino) {
        DiGraph G = new DiGraph();


        float maxP = Float.NaN;

        // jos sekoita = false, niin
        // tÃ¤ssÃ¤ solmujen nimet on annettu siten, ettÃ¤ puut ja muut on helppo erottaa
        // Ã„LÃ„ KUITENKAAN RYHDY TUNNISTAMAAN PUITA SOLMUN NIMEN TAI JÃ„RJESTYKSEN PERUSTEELLA
        for (int i = 0; i < puita; i++) {
            Vertex juuri = G.addVertex("P" + i + ".");
            // pÃ¤ivitÃ¤ uusin GraphMaker
            float we = GraphMaker.growWeightedTree(G, juuri, rnd, puuKoko, 10f);
            if (Float.isNaN(maxP) || maxP < we)
                maxP = we;
        }

        if (maxPaino != null) {
            maxPaino.clear();
            maxPaino.add(maxP);
        }


        if (sekoita)
            GraphMaker.randomVertexNames(G);
        return sekoita ? GraphMaker.shuffleCopy(G) : G;

    }


    static boolean testaaPainavinPuu(int puita, int print) {
        LinkedList<Float> maxPaino = new LinkedList<>();
        DiGraph G = teeSyote(puita, maxPaino);
        return testaaPainavinPuu(G, puita, maxPaino.getFirst(), print);
    }

    static boolean testaaPainavinPuu(int puita, int puuKoko, boolean sekoita, int print) {
        LinkedList<Float> maxPaino = new LinkedList<>();
        DiGraph G = teeSyote(puita, puuKoko, sekoita, maxPaino);
        return testaaPainavinPuu(G, puita, maxPaino.getFirst(), print);
    }


    static boolean testaaPainavinPuu(DiGraph G, int puita, float odotus, int print) {
        if (print > 0)
            System.out.format("\nPuiden mÃ¤Ã¤rÃ¤testi, puita=%d, painavinOdotus=%.1f\n", puita, odotus);
        if (print > 4 && G.size() < 25) {
            System.out.println("SyÃ¶te:");
            System.out.print(GraphMaker.toString(G, 1));
        }

        float tulos = testattava.painavimmanPuunPaino(G);

        if (print > 1) {
            System.out.format("Saatu tulos: %.1f, odotettu tulos: %.1f\n", tulos, odotus);
        }

        if (vertaa(tulos, odotus)) {
            if (print > 0)
                System.out.println("Painavin puu testi ok");
            return true;
        } else {
            System.out.format("Painavimman puun paino ei tÃ¤smÃ¤Ã¤ odotettuun : Saatu tulos: %.1f, odotettu tulos: %.1f\n", tulos, odotus);
            if (print > 2 && G.size() < 50) {
                System.out.println("SyÃ¶te oli:");
                System.out.print(GraphMaker.toString(G, 1));
            }

            return false;
        }
    }

    static boolean vertaa(float f1, float f2) {
        return ((Float.isNaN(f1) && Float.isNaN(f2)) || Math.abs(f1-f2) < 0.1);
    }


}

