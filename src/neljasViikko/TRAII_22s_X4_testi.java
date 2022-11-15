package neljasViikko;

// TRAII_22s_X4_testi.java SJ

import fi.uef.cs.tra.Graph;
import fi.uef.cs.tra.Vertex;

import java.util.Random;

public class TRAII_22s_X4_testi {

    static TRAII_22s_X4 testattava = new TRAII_22s_X4_tapiomer();
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

        ok &= testaaPuidenMaara(0, 0, print);
        ok &= testaaPuidenMaara(1, 0, 0, 0, false, print);
        ok &= testaaPuidenMaara(1, 0, 3, 0, false, print);
        ok &= testaaPuidenMaara(2, 0, 4, 0, false, print);
        ok &= testaaPuidenMaara(3, 0, 2, 0, true, print);
        ok &= testaaPuidenMaara(0, 1, print);
        ok &= testaaPuidenMaara(0, 2, print);
        ok &= testaaPuidenMaara(1, 2, print);
        ok &= testaaPuidenMaara(2, 3, 2, 3, true, print);
        ok &= testaaPuidenMaara(0, 2, print);
        ok &= testaaPuidenMaara(3, 3, print);
        ok &= testaaPuidenMaara(5, 5, print);
        ok &= testaaPuidenMaara(N, N, N, N, true, print);



        rnd.setSeed(System.currentTimeMillis());
        k = 0;
        while (k < nTest) {
            k++;
            int np = rnd.nextInt(k+1);
            int nm = rnd.nextInt(k+1);

            if (!testaaPuidenMaara(np, nm, np, nm, false, 0))
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


    static Graph teeSyote(int puita, int muita) {
        return teeSyote(puita, muita, rnd.nextInt(puita+1), rnd.nextInt(muita+1)+5, false);
    }

    static Graph teeSyote(int puita, int muita, int puuKoko, int muuKoko, boolean sekoita) {
        Graph G = new Graph();

        // jos sekoita = false, niin
        // tÃ¤ssÃ¤ solmujen nimet on annettu siten, ettÃ¤ puut ja muut on helppo erottaa
        // Ã„LÃ„ KUITENKAAN RYHDY TUNNISTAMAAN PUITA SOLMUN NIMEN TAI JÃ„RJESTYKSEN PERUSTEELLA
        for (int i = 0; i < puita; i++) {
            Vertex juuri = G.addVertex("P" + i + ".");
            GraphMaker.growTree(G, juuri, rnd, puuKoko);
        }

        for (int i = 0; i < muita; i++) {
            Vertex solmu = G.addVertex("M" + i + ".");
            int e = muuKoko+rnd.nextInt(muuKoko);
            GraphMaker.makeNonTree(G, solmu, rnd, muuKoko, e);
        }

        if (sekoita)
            GraphMaker.randomVertexNames(G);
        return sekoita ? GraphMaker.shuffleCopy(G) : G;
    }


    static boolean testaaPuidenMaara(int puita, int muita, int print) {
        return testaaPuidenMaara(teeSyote(puita, muita), puita, muita, print);
    }

    static boolean testaaPuidenMaara(int puita, int muita, int puuKoko, int muuKoko, boolean sekoita, int print) {
        return testaaPuidenMaara(teeSyote(puita, muita, puuKoko, muuKoko, sekoita), puita, muita, print);
    }


    static boolean testaaPuidenMaara(Graph G, int puita, int muita, int print) {
        if (print > 0)
            System.out.println("\nPuiden mÃ¤Ã¤rÃ¤testi, puita=" + puita + " muita="+muita);
        if (print > 4 && G.size() < 25) {
            System.out.println("SyÃ¶te:");
            System.out.print(GraphMaker.toString(G, 1));
        }

        int tulos = testattava.puidenMaara(G);

        if (print > 1) {
            System.out.println("Saatu tulos: " + tulos + ", odotettu tulos: " + puita);
        }

        if (tulos == puita) {
            if (print > 0)
                System.out.println("PuumÃ¤Ã¤rÃ¤testi ok");
            return true;
        } else {
            System.out.println("Puiden mÃ¤Ã¤rÃ¤ ei tÃ¤smÃ¤Ã¤ odotettuun : Saatu tulos: " + tulos + ", odotettu tulos: " + puita);
            if (print > 2 && G.size() < 50) {
                System.out.println("SyÃ¶te oli:");
                System.out.print(GraphMaker.toString(G, 1));
            }

            return false;
        }
    }






}

