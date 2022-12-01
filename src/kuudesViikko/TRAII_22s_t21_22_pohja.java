package kuudesViikko;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Iterator;


/**
 * Pohja liukulukujoukon toteuttamiseen suljetulla hajautuksella tiedostoon.
 */

// nimeÃ¤ _pohja -luokka uudelleen TRAII_22s_t21_22
// tai vaihda testiohjelmaa kÃ¤yttÃ¤mÃ¤Ã¤n tÃ¤tÃ¤ _pohja -luokkaa
public class TRAII_22s_t21_22_pohja implements  DoubleSetFile, Iterable<Double> {

    private RandomAccessFile tiedosto;        // talletustiedosto
    private long hajautusKoko = 1000;          // varsinainen hajatustaulun koko tietueina
    private long ylivuotoTila = 100;          // ylivuotoalueen koko tietueina
    private final long otsikkoPituus = 2 * Long.BYTES;  // alkuosan koko (kaksi long:ia)
    private final int tietueKoko = Double.BYTES;      // tietueen koko (yksi double)


    /**
     * Konstruktori joka luo uuden ja tyhjÃ¤n DoubleSetFile tiedoston.
     * Ã„lÃ¤ muuta tai poista tÃ¤tÃ¤ konstruktoria!
     *
     * @param tiedostonNimi uuden tiedoston nimi.
     * @param koko          hajautustaulun slottien mÃ¤Ã¤rÃ¤.
     */
    public TRAII_22s_t21_22_pohja(String tiedostonNimi, int koko) {
        hajautusKoko = koko;
        try {
            synchronized (this) {
                tiedosto = new RandomAccessFile(tiedostonNimi, "rw");

                // kirjoitetaan otsikkotiedot
                tiedosto.writeLong(hajautusKoko);
                tiedosto.writeLong(ylivuotoTila);

                // lasketaan ja asetetaan koko tiedoston koko
                tiedosto.setLength(otsikkoPituus + tietueKoko * (hajautusKoko + ylivuotoTila));

                // tehdaan nollia sisaltava tavutaulukko
                int puskuriKoko = 512;
                byte[] kirjoitusPuskuri = new byte[tietueKoko * puskuriKoko];
                for (int i = 0; i < puskuriKoko * tietueKoko; i++)
                    kirjoitusPuskuri[i] = 0;

                // taytetaan tiedosto tyhjilla tavuilla
                for (long i = 0; i < hajautusKoko + ylivuotoTila; i += puskuriKoko) {
                    // viimeinen lohko voi olla vajaa
                    if (i + puskuriKoko > hajautusKoko + ylivuotoTila)
                        puskuriKoko = (int) (hajautusKoko + ylivuotoTila - i);
                    tiedosto.write(kirjoitusPuskuri, 0, puskuriKoko * tietueKoko);
                }
            }

        } catch (IOException e) {
            System.err.println("Tiedoston luonnissa virhe: " + e);
        }
    }

    /**
     * Avaa olemassaolevan tiedoston kÃ¤sittelyÃ¤ varten.
     * Ã„lÃ¤ muuta tai poista tÃ¤tÃ¤ konstruktoria!
     *
     * @param tiedostonNimi Tiedoston nimi.
     */
    public TRAII_22s_t21_22_pohja(String tiedostonNimi) {
        try {
            synchronized (this) {
                tiedosto = new RandomAccessFile(tiedostonNimi, "rw");
                // luetaan otsikkotiedot
                hajautusKoko = tiedosto.readLong();
                ylivuotoTila = tiedosto.readLong();
            }

        } catch (IOException e) {
            // System.err.println(e);
            tiedosto = null;
            throw new RuntimeException("Tiedoston avaus ei onnistunut: " + tiedostonNimi +
                    " : " + e);
        }
    }


    /**
     * LisÃ¤Ã¤ joukkoon doublen x jollei sitÃ¤ ole siellÃ¤ ennestÃ¤Ã¤n.
     *
     * @param x lisÃ¤ttÃ¤vÃ¤ double.
     * @return true jos lisÃ¤ys tehtiin, false jos double oli jo joukossa.
     * @throws RuntimeException jos hajautustaulu on liian tÃ¤ynnÃ¤.
     */
    // @Override
    public boolean add(Double x) {
        // kotiosoite
        // siis hajautustaulun slotin kotiosoite
        // tÃ¤stÃ¤ vielÃ¤ pitÃ¤Ã¤ muokata tavuosoite!
        long kotiOsoite = Math.floorMod(x.hashCode(), hajautusKoko);

        // varataan tavutaulukko johon luetaan oikea kohta tiedostosta
        // luetaan kerralla ylivuotoTila tietuetta
        byte[] vanhaData = new byte[(int) (ylivuotoTila * tietueKoko)];

        try {
            tiedosto.seek(0L /* TODO oikea tavuosoite */);
            tiedosto.read(vanhaData);

            for (int i = 0; i < ylivuotoTila - 1; i++) {

                // TODO
                // etsitÃ¤Ã¤n onko x luetussa tavutaulukossa (jolloin ei lisÃ¤tÃ¤)
                // tai lÃ¶ytyykÃ¶ sieltÃ¤ tyhjÃ¤ kohta (jolloin kirjoitetaan x siihen

                return true; // TODO true jos lisÃ¤ys tehtiin, muuten false

            }

            // jos ei lÃ¶ydetty tyhjÃ¤Ã¤ slottia, heitetÃ¤Ã¤n poikkeus
            // oikeasti tehtÃ¤isiin uudelleenhajautus ja lisÃ¤ys
            throw new java.nio.BufferOverflowException();

        } catch (IOException e) {
            System.err.println("Poikkeus: " + e);
            return false;
        }

    }

    /**
     * Palauttaa tiedon onko double p joukossa vai ei.
     *
     * @param x tarkasteltava double
     * @return true jos p on joukossa, muuten false
     */
    // @Override
    public boolean contains(Double x) {

        // kotiosoite
        // siis hajautustaulun slotin kotiOsoite
        // tÃ¤stÃ¤ vielÃ¤ pitÃ¤Ã¤ muokata tavuosoite!
        long kotiOsoite = Math.floorMod(x.hashCode(), hajautusKoko);

        // varataan tavutaulukko johon luetaan oikea kohta tiedostosta
        // luetaan kerralla ylivuotoTila tietuetta
        byte[] data = new byte[(int) (ylivuotoTila * tietueKoko)];

        try {
            tiedosto.seek(0 /* TODO oikea tavuosoite */);

            // TODO


            return false; // jollei lÃ¶ydy

        } catch (IOException e) {
            System.err.println(e);
            return false;
        }

    }

    /**
     * Sulkee tiedoston kun joukon kaytto lopetetaan.
     */
    // @Override
    public void close() {
        try {
            tiedosto.close();
            tiedosto = null;
        } catch (IOException e) {
            System.err.println(e);
        }
    }


    // apumetodeja:


    /**
     * Kirjoittaa yhden doublen tavutaulukkoon pyydettyyn kohtaan.
     *
     * @param A      tavutaulukko
     * @param offset kirjoituskohta tavuina
     * @param x      kirjoitettava liukuluku
     */
    public void doubleToByteArray(byte[] A, int offset, double x) {
        ByteBuffer.wrap(A, offset, Double.BYTES).putDouble(x);
    }


    /**
     * Lukee tavutaulukosta A kohdasta offset yhden liukuluvun.
     *
     * @param A      luettava tavutaulukko.
     * @param offset lukukohta
     * @return luettu liukuluku
     */
    public double doubleFromByteArray(byte[] A, int offset) {
        return ByteBuffer.wrap(A, offset, Double.BYTES).getDouble();
    }


    /**
     * Palauttaa tiedon onko tavutaulukon slotti tyhjÃ¤, eli ovatko kaikki tavutaulukon A
     * alkiot A[alku]..A[alku+n-1] nollia.
     *
     * @param A    tavutaulukko
     * @param alku tarkastelun alkukohta
     * @param n    tarkasteltavien tavujen mÃ¤Ã¤rÃ¤
     * @return true jos kaikki tarkastetut tavut olivat nollia, muuten false
     */
    private boolean onkoKaikkiNollia(byte[] A, int alku, int n) {
        int loppu = alku + n;
        for (int i = alku; i < loppu; i++) {
            if (A[i] != 0)
                return false;
        }
        return true;
    }

    /**
     * Hyvin yksinkertainen iteroinnin toteutus testausta varten.
     * TÃ¤tÃ¤ ei pidÃ¤ kÃ¤yttÃ¤Ã¤ add():n tai contains():n toteutuksessa.
     * @return
     */
    @Override
    public Iterator<Double> iterator() {
        return new fileIter();
    }

    private class fileIter implements Iterator<Double> {

        byte[] puskuri = new byte[(int) (ylivuotoTila * tietueKoko)]; // puskuri
        long tiedInd = otsikkoPituus;   // tiedostonkohta josta luetaan
        int puskuriInd = puskuri.length;    // kohta jossa menossa puskurissa

        @Override
        public boolean hasNext() {
            // etsitÃ¤Ã¤n indeksit siihen paikkaan josta lÃ¶ytyy muuta kuin nollaa
            while (true) {
                if (puskuriInd >= puskuri.length) {
                    // puskuri kÃ¤ytetty loppuun, luetaan uusi puskurillinen
                    try {
                        tiedosto.seek(tiedInd);
                        int luettu = tiedosto.read(puskuri);
                        if (luettu < tietueKoko)
                            return false;   // ei saatu riittÃ¤vÃ¤sti tietoa puskuriin
                        tiedInd += luettu;
                        puskuriInd = 0;
                    } catch (IOException e) {
                        return false; // lukuvirhe
                    }

                }
                // etsitÃ¤Ã¤n puskurista muuta kuin nollia
                while (puskuriInd < puskuri.length) {
                    if (! onkoKaikkiNollia(puskuri, puskuriInd, tietueKoko))
                        return true;
                    puskuriInd += tietueKoko;
                }
            }
        }

        @Override
        public Double next() {
            if (! hasNext()) // varmistetaan, ettÃ¤ indeksissÃ¤ on muuta kuin nollia
                throw new IllegalStateException("Alkioita ei enÃ¤Ã¤ ole");
            double val = doubleFromByteArray(puskuri, puskuriInd);  // luetaan luku
            puskuriInd += tietueKoko;   // siirrytÃ¤Ã¤n seuraavaan slottiin
            return val;
        }
    }


}
