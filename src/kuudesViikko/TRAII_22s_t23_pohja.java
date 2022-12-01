package kuudesViikko;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class TRAII_22s_t23_pohja {
    static LinkedList<Integer> rahat = new LinkedList<Integer>();

    public static void main(String[] args) {

        // defaults
        int rahaSumma = 80;

        // rahasumma komentoriviltÃ¤
        if (args.length > 0)
            rahaSumma = Integer.parseInt(args[0]);

        ArrayList<Integer> Kolikot = new ArrayList<>();

        // kolikot komentoriviltÃ¤
        for (int i = 1; i < args.length; i++)
            Kolikot.add(Integer.valueOf(args[i]));

        if (Kolikot.size() == 0) {
            Kolikot.add(1);
            Kolikot.add(2);
            Kolikot.add(5);
            Kolikot.add(10);
            Kolikot.add(40);
            Kolikot.add(50);
        }

        System.out.println("Dynaaminen");
        LinkedList<Integer> kolikot = dynjako23(rahaSumma, Kolikot);

        System.out.println("Kolikoita tarvitaan " + kolikot.size() + " kpl");
        System.out.println("Kolikot: " + kolikot);

    }


    /**
     * Rahajako dynaamisen ohjelmoinnin keinoin
     *
     * @param rahaSumma kasattava rahamÃ¤Ã¤rÃ¤
     * @param Kolikot   kÃ¤ytÃ¶ssÃ¤ olevien kolikkojen arvot
     * @return tarvittavien kolikkojen lukumÃ¤Ã¤rÃ¤
     */
    static int dynjako(int rahaSumma, Collection<Integer> Kolikot) {
        int[] kolikkoMaara = new int[rahaSumma + 1];
        kolikkoMaara[0] = 0;
        // haetaan ja talletetaan kaikki osaratkaisut
        for (int rahaSummaIter = 1; rahaSummaIter <= rahaSumma; rahaSummaIter++) {
            int min = rahaSummaIter;
            // kullakin kolikolla

            //mita tapahtuu kun iter = 1?
            for (Integer kolikko : Kolikot) {
                //if haaraan kaikilla kolikoilla
                if (kolikko <= rahaSummaIter) {
                    //kolikot 1, 2, 5, 10, 20, 40, 50
                    //1 == 1, kolikoita = kolikkomaara[1-1] + 1, kolikoita = 1
                    //2 == 2 kolikoita = kolikkomaara[2-2] +2, kolikoita = 2
                    //if iter = 40, 40==40, kolikoita = kolikkomaara[40-40]+1 = 1
                    //if iter = 80, 80==80, kolikoita = kolikkomaara[80-40]+1 = 2
                    int kolikoita = kolikkoMaara[rahaSummaIter - kolikko] + 1;

                    if(kolikko==rahaSumma-rahaSummaIter){
                        rahat.add(kolikko);
                    }
                    if (kolikoita < min)
                        min = kolikoita;
                }
            }
            kolikkoMaara[rahaSummaIter] = min;
        }
        // vastaus alkuperÃ¤iseen tehtÃ¤vÃ¤Ã¤n
        return kolikkoMaara[rahaSumma];
    }

    /**
     * Rahajako dynaamisen ohjelmoinnin keinoin
     *
     * @param rahaSumma kasattava rahamÃ¤Ã¤rÃ¤
     * @param Kolikot   kÃ¤ytÃ¶ssÃ¤ olevien kolikkojen arvot
     * @return lista palautettavista kolikoista
     */
    static LinkedList<Integer> dynjako23(int rahaSumma, Collection<Integer> Kolikot) {
        if(rahat != null){
            rahat.clear();
        }
        dynjako(rahaSumma, Kolikot);
        LinkedList<Integer> tulos = new LinkedList<>(rahat);
        return tulos;
    }
}

