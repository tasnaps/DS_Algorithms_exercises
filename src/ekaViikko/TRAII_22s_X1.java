package ekaViikko;

import fi.uef.cs.tra.BTree;
import fi.uef.cs.tra.BTreeNode;

public interface TRAII_22s_X1 {

    /**
     * Palauttaa tiedon onko annettu binÃ¤Ã¤ripuu keko (eli kasa) vai ei.
     * Keossa jokainen solmu edeltÃ¤Ã¤ (tai on samanarvoinen) kuin kaikki jÃ¤lkelÃ¤isensÃ¤.
     * @param T SyÃ¶tepuu.
     * @param <E> alkiotyyppi
     * @return true jos kysessÃ¤ on keko, muuten false
     */
    public <E extends Comparable<? super E>> boolean onkoKeko(BTree<E> T);
}