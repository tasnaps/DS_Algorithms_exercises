package toinenViikko;

import java.util.Deque;

public interface TRAII_22s_X2 {

    /**
     * Mittaa annetun pakan offerFirst-pollLast -operaatioparin aikavaativuuden nanosekunteina.
     * Mittaa ns. normaalin onnistuneen suorituksen tilanteessa jossa lisÃ¤ys ja poisto tehtiin
     * kokoelmasta.
     * Pakkaan jÃ¤Ã¤ lopuksi yhtÃ¤ monta alkiota kuin siellÃ¤ oli entuudestaan.
     *
     * @param D testattava pakka
     * @return offerFirst-pollLast -operaatioparin operaation normikesto nanosekunteina
     */
    public long offerPollAika(Deque<Integer> D);

}

