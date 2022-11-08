package ekaViikko;// TRAII_22s_t3_4.java SJ

/**
 * 3. Kirjoita algoritmi, joka saa syÃ¶tteenÃ¤Ã¤n kokoelman (Collection<E> C ) ja kokonaisluvun k
 (k >0), ja joka palauttaa tuloksenaan uuden listan niistÃ¤ alkioista jotka esiintyivÃ¤t kokoelmassa
 C tasan k kertaa. Kukin k kertaa esiintynyt alkio tulee tuloslistaan yhden kerran. Ã„lÃ¤ muuta
 syÃ¶tettÃ¤. Alkiot ovat samat jos niiden .equals() -metodi palauttaa true. KÃ¤ytÃ¤ apuna kuvausta
 (Map<E, Integer>). MikÃ¤ on algoritmisi aikavaativuus? Ota pohjaa ja esimerkkiÃ¤ Moodlesta.

 4. Vertaa tehtÃ¤vÃ¤n 3 toimintaa kun apuvÃ¤linekuvauksena on (a) HashMap tai (b) TreeMap.
 Kirjoita ohjelma joka mittaa nÃ¤iden nopeutta kun syÃ¶te kasvaa. Miten selitÃ¤t tulokset? Ota
 pohjaa ja esimerkkiÃ¤ Moodlesta.

 */

import java.util.*;

public class TRAII_22s_t3_4_pohja {


    // PÃ¤Ã¤ohjelman kÃ¤yttÃ¶:
    // java TRAII_22s_t3_4 [N] [siemen]
    // missÃ¤ N on alkioiden mÃ¤Ã¤rÃ¤
    // ja siemen on satunnaislukusiemen

    public static void main(String[] args) {

        // á¸±okoelman koko
        int N = 1200000;
        if (args.length > 0)
            N = Integer.parseInt(args[0]);


        // satunnaislukusiemen
        int siemen = N;
        if (args.length > 1)
            siemen = Integer.parseInt(args[1]);


        // ensin pieni lista
        Random r = new Random(siemen);
        LinkedList<Integer> L = randomLinkedList(20, r);

        // tulostetaan lista jos alkioita ei ole paljoa
        System.out.println(L.size() < 30 ? L : (L.size() + " alkioinen syÃ¶telista"));
        Ajastin2 at = null;
        List<Integer> kTulos = null;

        at = new Ajastin2("" + L.size());
        kTulos = kEsiintyvat(L, 2);

        at.stop();
        System.out.println("aika: " + at + ", " +
                (at.time() * 1.0 / L.size()) + " ns/elem");
        System.out.println("2 kertaa esiintyneet " + kTulos);

        // sitten vÃ¤hÃ¤n isompi
        L = randomLinkedList(N, r);

        // tulostetaan lista jos alkioita ei ole paljoa
        System.out.println(L.size() < 30 ? L : (L.size() + " alkioinen syÃ¶telista"));

        at = new Ajastin2("" + L.size());
        kTulos = kEsiintyvat(L, 3);
        at.stop();
        System.out.println("aika: " + at + ", " +
                (at.time() * 1.0 / L.size()) + " ns/elem");
        if (kTulos.size() < 20)
            System.out.println("3 kertaa esiintyneet " + kTulos);
        else
            System.out.println("3 kertaa esiintyneitÃ¤ oli " + kTulos.size() + " kappaletta");

        // TODO T4

        System.out.println("Seuraavaksi TreeMap toteutuksen mittaus");
        ///////////////////////////////////////////////////////////////
        Ajastin2 at2 = null;
        List<Integer> puuTulos = null;
        at2 = new Ajastin2("" + L.size());
        puuTulos = kTreemap(L, 2);
        at2.stop();
        System.out.println("aika: " + at2 + ", " +
                (at.time() * 1.0 / L.size()) + " ns/elem");
        System.out.println("2 kertaa esiintyneet " + puuTulos);
        at2 = new Ajastin2("" + L.size());
        puuTulos = kTreemap(L, 3);
        at2.stop();

        System.out.println("aika: " + at2 + ", " +
                (at2.time() * 1.0 / L.size()) + " ns/elem");
        if (puuTulos.size() < 20)
            System.out.println("3 kertaa esiintyneet " + puuTulos);
        else
            System.out.println("3 kertaa esiintyneitÃ¤ oli " + puuTulos.size() + " kappaletta");




    } // main()


    /**
     * MitkÃ¤ alkio esiintyvÃ¤t tasan k kertaa C?
     * Palautetaan ko. alkiot listana siten, ettÃ¤ kukin alkio on tuloslistassa vain kerran.
     *
     * @param <E> alkiotyyppi
     * @param C   SyÃ¶tekokoelma
     * @param k   Haettavien esiintymien mÃ¤Ã¤rÃ¤
     * @return lista jossa on kaikki ne alkiot jotka esiintyvÃ¤t syÃ¶tteessÃ¤ k kertaa
     */
    public static <E> List<E> kEsiintyvat(Collection<E> C, int k) {
        //aikavaativuus O(n**2)
        List<E> tulos = new ArrayList<>();
        Map<E, Integer> kartta = new HashMap<>();
        int index = 0;

        for (E e:C) {
            kartta.put(e, index);
            index = index+1;
        }
        Iterator<E> mapIteratorr = kartta.keySet().iterator();
        while(mapIteratorr.hasNext()){
            int lkm = 0;
            E verrattava = mapIteratorr.next();
            for(E ver: C){
                if(ver.equals(verrattava)){
                    lkm = lkm+1;
                }
            }
            if(lkm ==k){
                tulos.add(verrattava);
            }
        }

        return tulos;
    }

    /**
     * HashSet toteutus
     *
     HashSet<E> tarkastusLista = new HashSet<>(C);
     Iterator<E> iterator = tarkastusLista.iterator();
     while(iterator.hasNext()){
     int lkm = 0;
     E verrattava = iterator.next();
     for (E num: C) {
     if(num.equals(verrattava)){
     lkm = lkm+1;
     }
     }
     if(lkm == k){
     tulos.add(verrattava);
     }
     }
     *
     *
     *
     * @param C
     * @param k
     * @return
     * @param <E>
     */


    //4 b, treemap
    public static <E> List<E> kTreemap(Collection<E> C, int k){
        List<E> tulos = new ArrayList<>();
        Map<E, Integer> puukartta = new TreeMap<>();
        int index = 0;

        for (E e : C){
            puukartta.put(e, index);
            index = index+1;
        }


        Iterator<E> puuIterator = puukartta.keySet().iterator();
        while(puuIterator.hasNext()){
            int lkm = 0;
            E verratava = puuIterator.next();
            for(E ver: C){
                if(ver.equals(verratava)){
                    lkm = lkm+1;
                }
            }
            if(lkm ==k){
                tulos.add(verratava);
                System.out.println("Puukartta toiminnassa");
            }
        }
        return tulos;
    }




    public static LinkedList<Integer> randomLinkedList(int n, int seed) {
        Random r = new Random(seed);
        LinkedList<Integer> V = new LinkedList<>();
        for (int i = 0; i < n; i++)
            V.add(r.nextInt(n));
        return V;
    }

    public static LinkedList<Integer> randomLinkedList(int n, Random r) {
        LinkedList<Integer> V = new LinkedList<>();
        for (int i = 0; i < n; i++)
            V.add(r.nextInt(n));
        return V;
    }


} // class

