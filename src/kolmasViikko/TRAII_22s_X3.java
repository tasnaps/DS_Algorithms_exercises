package kolmasViikko;

import java.util.Collection;
import java.util.SortedMap;

public interface TRAII_22s_X3 {

    /**
     * Annetun kokoelman contains-operaation aikavaativuus erilaisilla kokoelman alkiomÃ¤Ã¤rillÃ¤.
     * Aika lasketaan kullekin alkiomÃ¤Ã¤rÃ¤lle jotka lÃ¶ytyvÃ¤t kuvauksen tulokset avaimista.
     * Kukin aika talletetaan kuvauksen tulokset ko. avaimen arvoksi.
     *
     * @param C         testattava kokoelma (aluksi tyhjÃ¤)
     * @param tulokset  kuvaus jonka avaimet kertovat testattavat alkiomÃ¤Ã¤rÃ¤t
     * @param loytyy    testataanko tilannetta jossa alkiot lÃ¶ytyvÃ¤t (true) kokoelmasta vai ei
     */
    public void containsNopeus(Collection<Double> C, SortedMap<Integer, Long> tulokset, boolean loytyy);


}

