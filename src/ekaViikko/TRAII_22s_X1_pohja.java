package ekaViikko;

import fi.uef.cs.tra.BTree;
import fi.uef.cs.tra.BTreeNode;
/**
 * ITSEARVIOINTI TÃ„HÃ„N:
 *
 *
 *
 */


/**
 Kirjoita algoritmi joka tarkistaa onko annettu binääripuu keko (eli kasa) vai ei.

 Keko on binääripuu jossa jokaisen solmun sisältämä alkio edeltää (on ”pienempi”) tai on yhtäsuuri
 kuin minkään sen jälkeläisen sisältämä alkio.

 Jos binääripuussa on alkioita jotka eivät ole
 keskenään oikeassa järjestyksessä, algoritmi palauttaa epätoden, muuten toden. Puun ta-
 sapainoa ei tarkastella.

 Parametrina binääripuu, palautusarvona totuusarvo.

 Vihje: rekursio.
 Mikä on algoritmisi aikavaativuus?
 **/
public class TRAII_22s_X1_pohja implements TRAII_22s_X1 {

    /**
     * Palauttaa tiedon onko annettu binÃ¤Ã¤ripuu keko (eli kasa) vai ei.
     * Keossa jokainen solmu edeltÃ¤Ã¤ (tai on samanarvoinen) kuin kaikki jÃ¤lkelÃ¤isensÃ¤.
     *
     * @param T SyÃ¶tepuu.
     * @return true jos kysessÃ¤ on keko, muuten false
     */
    @Override
    public <E extends Comparable<? super E>> boolean onkoKeko(BTree<E> T) {
        BTreeNode vertailuun = T.getRoot();
        return checkNodes(vertailuun);
    }


    private <E extends Comparable<? super E>>  boolean checkNodes (BTreeNode<E> e) {
        boolean a = true;
        boolean b = true;
        if (e != null) {//tarkistetaan että on tavaraa
            if (e.getParent() != null) {//löytyy vanhempi node mihin verrata
                int vertaus = e.getElement().compareTo(e.getParent().getElement());
                if (vertaus < 0) {
                    return false;
                }
            }
            a =checkNodes(e.getLeftChild());
            b =checkNodes(e.getRightChild());

        }
        return a && b;
    }
}

