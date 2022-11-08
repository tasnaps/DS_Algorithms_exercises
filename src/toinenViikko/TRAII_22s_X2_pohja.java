package toinenViikko;

import java.util.*;

public class TRAII_22s_X2_pohja implements TRAII_22s_X2 {
    /**
     * ITSEARVIOINTI TÃ„HÃ„N:
     * Ohjelmani mittaa offerfirst ja pollLast metodien aikavaativuuden keskimäärän mukaisesti.
     * Yritin poistaa häiriöitä tekemällä paljon (600,000) toistoja.
     * Sain tulokset tarkkuuteen +-11ns Linkedlistillä jossa Min: 32 ns, Max: 43ns
     * ArrayDeque kaikilla testeillä 29ns
     * Concurrent linkedDeque +-10ns, kun Min: 55ns, Max 65ns
     *
     * En ole varma olisiko kaikilla tuloksilla pitänyt päästä yhteen vakioon arvoon. Mutta omalla tietokoneella voi olla useita tekijöitä jotka vaikuttavat tulokseen (Win 10, ylikellotettu, taustaohjelmat)
     *metodin aikavaativuus O(1)
     *
     **/

    /**
     * Mittaa annetun pakan offerFirst-pollLast -operaatioparin aikavaativuuden nanosekunteina.
     * Mittaa ns. normaalin onnistuneen suorituksen tilanteessa jossa lisÃ¤ys ja poisto tehtiin
     * kokoelmasta.
     * Pakkaan jÃ¤Ã¤ lopuksi yhtÃ¤ monta alkiota kuin siellÃ¤ oli entuudestaan.
     *
     * @param D testattava pakka
     * @return offerFirst-pollLast -operaatioparin operaation normikesto nanosekunteina
     */
    @Override
    public long offerPollAika(Deque<Integer> D) {
        Long[] ajat = new Long[600000];
        //lammitys
        int index = 0;
        long startTime;
        long estimatedTime;
        for(int i = 0; i<600000; i++){
            startTime = System.nanoTime();
            D.offerFirst(index);
            D.pollLast();
            estimatedTime = System.nanoTime() - startTime;
            ajat[index] = estimatedTime;
            index = index+1;
        }

        long summa = 0;
        for (Long i: ajat){
            summa = summa+i;
        }
        return summa/600000;

    }
}

