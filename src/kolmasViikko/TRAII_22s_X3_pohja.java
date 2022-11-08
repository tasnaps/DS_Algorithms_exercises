package kolmasViikko;

import java.util.*;

public class TRAII_22s_X3_pohja implements TRAII_22s_X3 {
    //                    ^^^^^ oma tunnus tÃ¤hÃ¤n


    /**
     * ITSEARVIOINTI TÃ„HÃ„N:
     *
     *
     *
     */

    Random rnd = new Random(System.currentTimeMillis());


    /**
     * Annetun kokoelman contains-operaation aikavaativuus erilaisilla kokoelman alkiomÃ¤Ã¤rillÃ¤.
     * Aika lasketaan kullekin alkiomÃ¤Ã¤rÃ¤lle jotka lÃ¶ytyvÃ¤t kuvauksen tulokset avaimista.
     * Kukin aika talletetaan kuvauksen tulokset ko. avaimen arvoksi.
     * @param C        testattava kokoelma (aluksi tyhjÃ¤)
     * @param tulokset kuvaus jonka avaimet kertovat testattavat alkiomÃ¤Ã¤rÃ¤t
     * @param loytyy   testataanko tilannetta jossa alkiot lÃ¶ytyvÃ¤t (true) kokoelmasta vai ei
     */
    @Override
    public void containsNopeus(Collection<Double> C, SortedMap<Integer, Long> tulokset, boolean loytyy) {

        // TODO ehkÃ¤ tÃ¤hÃ¤n

        // eri alkiomÃ¤Ã¤rÃ¤t joilla mittaus pitÃ¤Ã¤ tehdÃ¤:
        for (int alkioMaara : tulokset.keySet()) {

            // TODO tÃ¤hÃ¤n ainakin pitÃ¤Ã¤ tehdÃ¤ jotain

            // tulosten tallettaminen
            tulokset.put(alkioMaara, 1L /* TODO tÃ¤hÃ¤n se tulos nanosekunteina */ );

        }

        // TODO ehkÃ¤ tÃ¤nnekin

    }


    /**
     * Luo ArrayList:n jossa jossa on n satunnaista liukulukua.
     * Saa kÃ¤yttÃ¤Ã¤ jos haluaa.
     * @param n liukulukujen mÃ¤Ã¤rÃ¤
     * @return liukulukutaulukko
     */
    private ArrayList<Double> satunnaisia(int n) {
        ArrayList<Double> A = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            A.add(rnd.nextDouble()*n);
        return A;
    }


}
