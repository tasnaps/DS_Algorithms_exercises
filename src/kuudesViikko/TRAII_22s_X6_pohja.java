package kuudesViikko;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class TRAII_22s_X6_pohja implements TRAII_22s_X6 {

    /**
     * ITSEARVIOINTI TÃ„HÃ„N:
     *
     *
     *
     *
     */

    /**
     * Jaottelee kokonaislukulista alkiot kahteen tasapituiseen listaan osaan siten, ettÃ¤ kukin syÃ¶telistan alkio kopioidaan
     * jompaankumpaan tuloslistaan (mutta ei molempiin). Kumpaankin tuloslistaan on tultava yhtÃ¤ monta alkiota
     * (syÃ¶telistassa on siis parillinen mÃ¤Ã¤rÃ¤ alkioita). Tavoitteena on, ettÃ¤ tuloslistojen alkioiden summat
     * olisivat mahdollisimman lÃ¤hellÃ¤ toisiaan. Koska tehtÃ¤vÃ¤ on NP vaikea, optimaalista tulosta ei yleensÃ¤
     * saavuteta, mutta pyritÃ¤Ã¤n kohtuullisessa ajassa jotenkin kohtuulliseen tulokseen.
     * Algoritmin on suoriuduttava tehtÃ¤vÃ¤stÃ¤ maxAika sekunnissa.
     * @param syote syÃ¶telista, tÃ¤tÃ¤ ei saa muuttaa mitenkÃ¤Ã¤n
     * @param tulos1 toinen tulos (kutsuttaessa tyhjÃ¤, palautettaessa sisÃ¤ltÃ¤Ã¤ tasan puolet alkioista)
     * @param tulos2 toinen tulos (kutsuttaessa tyhjÃ¤, palautettaessa sisÃ¤ltÃ¤Ã¤ tasan puolet alkioista)
     * @param maxAika suurin kÃ¤ytettÃ¤vissÃ¤ oleva aika (sekunteja)
     */
    @Override
    public void jaaTasanKahteenTasakokoiseen(ArrayList<Integer> syote, ArrayList<Integer> tulos1, ArrayList<Integer> tulos2, int maxAika) {
        // varmuuden vuoksi tarkastus viallisen syÃ¶tteen varalle
        if (syote.size() % 2 != 0)
            throw new RuntimeException("SyÃ¶telistan alkiomÃ¤Ã¤rÃ¤ oli pariton, ei voi tasajakaa!");

        // TÃ¤ssÃ¤ on esimerkkinÃ¤ jaotellaan joka toinen alkio yhteen listaan
        // ja joka toinen alkio toiseen listaan
        // tuloksena kelvollinen jaottelu, mutta summien tasapaino on (yleensÃ¤) huono

        Iterator<Integer> it = syote.iterator();
        while (it.hasNext()) {
            tulos1.add(it.next());  // alkio tulos1:een
            tulos2.add(it.next());  // seuraava alkio tulos2:een
        }


        // TODO: tee siis tilalle oma versio joka tekee jaottelun tasapainoisemmin

        // vihjeitÃ¤:
        // (a) mieti missÃ¤ jÃ¤rjestyksessÃ¤ alkiot kannattaa lisÃ¤tÃ¤ ja kumpaan listaan
        //     alkio kannattaa milloinkin lisÃ¤tÃ¤
        // (b) kÃ¤ytÃ¤ (sopivasti) satunnaisuutta ja kokeile useita erilaisia jaotteluita
        //     ja lopuksi (ajan loputtua) lisÃ¤Ã¤ paras niistÃ¤ tuloslistoihin
        //     tai tee jokin jaottelu ja korjaa sitÃ¤ paremmaksi (satunnaisesti)
    }
}

