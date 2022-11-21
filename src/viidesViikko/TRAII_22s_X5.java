package viidesViikko;

import fi.uef.cs.tra.DiGraph;

public interface TRAII_22s_X5 {

    /**
     * MetsÃ¤n painavimman puun paino.
     * Etsii verkon G komponenteista sen jonka kaarten yhteispaino on suurin.
     * Verkko G koostuu vain puista, eli kussakin komponentissa on yksi solmu johon ei johda kaarta
     * ja kussakin komponentissa on vain puukaaria (ei palukaaria, ristikkÃ¤iskaaria, eikÃ¤ etenemiskaaria).
     * Painavimmasta puusta palautetaan komponentin paino.
     * Jos verkko on tyhjÃ¤, palautetaan Float.NaN. Jos verkossa on ainoastaan kaarettomia komponentteja,
     * palautetaan 0.0.
     * @param G syÃ¶teverkko (koostuu vain puista)
     * @return painavimman komponentin kaarten painojen yhteissumma tai Float.NaN jos verkko on tyhjÃ¤
     */
    public float painavimmanPuunPaino(DiGraph G);

}
