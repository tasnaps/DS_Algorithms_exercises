package kuudesViikko;

// DoubleSetFile.java SJ


public interface DoubleSetFile
{

    /**
     * LisÃ¤Ã¤ joukkoon liukuluvun x jollei sitÃ¤ ole siellÃ¤ ennestÃ¤Ã¤n.
     * @param x lisÃ¤ttÃ¤vÃ¤ piste.
     * @return true jos lisÃ¤ys tehtiin, false jos x oli jo joukossa.
     * @throws java.nio.BufferOverflowException jos hajautustaulu on liian tÃ¤ynnÃ¤.
     */
    public boolean add(Double x);


    /**
     * Palauttaa tiedon onko luku x joukossa vai ei.
     * @param x tarkasteltava liukuluku
     * @return true jos x on joukossa, muuten false
     */
    public boolean contains(Double x);


    /**
     * Sulkee tiedoston kun joukon kÃ¤yttÃ¶ lopetetaan.
     */
    public void close();

}

