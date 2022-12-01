package kuudesViikko;

import java.util.ArrayList;

public interface TRAII_22s_X6 {

    /**
     * Jaottelee kokonaislukulista alkiot kahteen tasapituiseen listaan osaan siten, ettÃ¤ kukin syÃ¶telistan alkio kopioidaan
     * jompaankumpaan tuloslistaan (mutta ei molempiin). Kumpaankin tuloslistaan on tultava yhtÃ¤ monta alkiota
     * (syÃ¶telistassa on siis parillinen mÃ¤Ã¤rÃ¤ alkioita). Tavoitteena on, ettÃ¤ tuloslistojen alkioiden summat
     * olisivat mahdollisimman lÃ¤hellÃ¤ toisiaan. Koska tehtÃ¤vÃ¤ on NP vaikea, optimaalista tulosta ei yleensÃ¤
     * saavuteta, mutta pyritÃ¤Ã¤n kohtuullisessa ajassa jotenkin kohtuulliseen tulokseen.
     * Algoritmin on suoriuduttava tehtÃ¤vÃ¤stÃ¤ maxAika sekunnissa.
     * @param syote syÃ¶telista, tÃ¤tÃ¤ ei saa muuttaa mitenkÃ¤Ã¤n, ei edes jÃ¤rjestÃ¤Ã¤
     * @param tulos1 toinen tulos (kutsuttaessa tyhjÃ¤, palautettaessa sisÃ¤ltÃ¤Ã¤ tasan puolet alkioista)
     * @param tulos2 toinen tulos (kutsuttaessa tyhjÃ¤, palautettaessa sisÃ¤ltÃ¤Ã¤ tasan puolet alkioista)
     * @param maxAika suurin kÃ¤ytettÃ¤vissÃ¤ oleva aika (sekunteja)
     */
    void jaaTasanKahteenTasakokoiseen(ArrayList<Integer> syote, ArrayList<Integer> tulos1, ArrayList<Integer> tulos2, int maxAika);
}

