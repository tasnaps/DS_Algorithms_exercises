package kuudesViikko;


import java.util.HashSet;
import java.util.Random;

public class TRAII_22s_t21_22_testi
{

    // TestauspÃ¤Ã¤ohjelma tehtÃ¤ville 21-22
    // -----------------------------------------------------------------

    public static void main(String[] args) {

        TRAII_22s_t21_22_pohja    tiedostoJoukko;
        int ntst = 10; // montako lukua laitetaan
        int maxKoko = 100; // miten iso hajautustaulu
        boolean vanha = false;

        if (args.length < 2) {
            // ei komentoriviparametreja, sÃ¤Ã¤dÃ¤ tÃ¤stÃ¤ lÃ¤hdekoodissa tiedot:

            // uuden tiedoston luonti:
            tiedostoJoukko = new TRAII_22s_t21_22_pohja("data2122.data", maxKoko);

            // vanhan tiedoston kÃ¤yttÃ¶:
            // tiedostoJoukko = new TRAII_22st21_22("data26270.data");
            // vanha = true;

        } else {
            // komentoriviparametrit
            // KÃ¤yttÃ¶: java TRAII_22s_t21_22_testi tiedostonimi testienmaara [uudentiedostonkoko]

            ntst = Integer.parseInt(args[1]);

            if (args.length > 2) {
                int maxs = Integer.parseInt(args[2]);

                // uusi tiedosto
                tiedostoJoukko = new TRAII_22s_t21_22_pohja(args[0], maxs);

            } else {

                // vanha tiedosto
                tiedostoJoukko = new TRAII_22s_t21_22_pohja(args[0]);
                vanha = true;

            }
        }

        Random rnd = new Random(System.currentTimeMillis());

        // Tulosten tarkastamiseksi tallennetaan lisÃ¤ttÃ¤vÃ¤t arvot myÃ¶s
        // muistinvaraiseen joukkoon
        HashSet<Double> verrokki = new HashSet<>();

        // jos on avattu vanha tiedosto, niin tehdÃ¤Ã¤n siitÃ¤ lÃ¤pikÃ¤ynti
        // ja lisÃ¤tÃ¤Ã¤n vanhat alkiot verrokkiin
        if (vanha) {
            for (double x : tiedostoJoukko)
                verrokki.add(x);
        }

        // lisÃ¤tÃ¤Ã¤n satunnaisia liukulukuja
        for (int i = 0; i < ntst; i++) {

            Double uusi = rnd.nextDouble();

            // lisÃ¤tÃ¤Ã¤n tiedostoon ja verrokkiin
            boolean lisatty = tiedostoJoukko.add(uusi);
            System.out.println("Add(" + uusi + ") : " + lisatty);

            boolean lisattyVrt = verrokki.add(uusi);    // lisÃ¤tÃ¤Ã¤n myÃ¶s verrokkiin
            if (lisatty != lisattyVrt)
                System.out.println("Eri tulos lisÃ¤yksessa");
            // tÃ¤mÃ¤ on mahdollista jos tiedostossa on aiemmilta suorituskerroilta
            // ollut sama alkio, sitÃ¤ ei silloin ole verrokissa jossa on vain tÃ¤mÃ¤n
            // kerran alkiot. Kuitenkin hyvin epÃ¤todennÃ¤kÃ¶istÃ¤ nÃ¤illÃ¤ syÃ¶tteillÃ¤!
        }

        // lisÃ¤tÃ¤Ã¤n muutama duplikaatti, nÃ¤iden ei pitÃ¤isi mennÃ¤ joukkoon
        System.out.println("\nLisÃ¤tÃ¤Ã¤n duplikaatteja");
        int dup = 0;
        for (Double x : verrokki) {
            boolean lisatty = tiedostoJoukko.add(x);
            System.out.println("Add(" + x + ") : " + lisatty);
            if (lisatty)
                System.out.println("VÃ„Ã„RIN: add() palautti true vaikka alkion piti jo olla joukossa");
            if (++dup >= 5)
                break;
        }
        System.out.println();


        // testataan hakemista

        // haetaan satunnaisia:
        System.out.println("\nHaetaan satunnaisia, tuskin lÃ¶ytyy yhtÃ¤Ã¤n");
        for (int i = 0; i < ntst; i++) {

            Double p = rnd.nextDouble();

            boolean is = tiedostoJoukko.contains(p);
            System.out.println("Contains(" + p + ") = " + is);
            if (is != verrokki.contains(p))
                System.out.println("VÃ„Ã„RIN: contains palautti eri kuin verrokki" );
        }

        // haetaan verrokin alkioita
        System.out.println("\nVerrokki, kaikki pitÃ¤isi lÃ¶ytyÃ¤");
        for (Double p : verrokki) {
            boolean is = tiedostoJoukko.contains(p);
            System.out.println("Contains(" + p + ") = " + is);
            if (! is)
                System.out.println("VÃ„Ã„RIN: verrokissa alkio jota ei ole tiedostossa");
        }
        System.out.println();


        // testataan lÃ¤pikÃ¤yntiÃ¤:
        System.out.println("\nLÃ¤pikÃ¤ynnin testaus:");
        for (double x : tiedostoJoukko) {
            boolean is = verrokki.contains(x);
            System.out.println(x + " verr: " + is);
            if (!is)
                System.out.println("VÃ„Ã„RIN: lÃ¤pikÃ¤ynti lÃ¶ysi alkion jota ei ole verrokissa");
        }

        tiedostoJoukko.close();

    } /// main()

}   // class

