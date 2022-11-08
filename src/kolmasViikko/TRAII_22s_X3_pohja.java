package kolmasViikko;

import java.util.*;

public class TRAII_22s_X3_pohja implements TRAII_22s_X3 {
    //                    ^^^^^ oma tunnus tÃ¤hÃ¤n


    /**
     * ITSEARVIOINTI TÃ„HÃ„N:
     *
     *
     *
     */

    Random rnd = new Random(System.currentTimeMillis());


    /**
     * Annetun kokoelman contains-operaation aikavaativuus erilaisilla kokoelman alkiomÃ¤Ã¤rillÃ¤.
     * Aika lasketaan kullekin alkiomÃ¤Ã¤rÃ¤lle jotka lÃ¶ytyvÃ¤t kuvauksen tulokset avaimista.
     * Kukin aika talletetaan kuvauksen tulokset ko. avaimen arvoksi.
     * @param C        testattava kokoelma (aluksi tyhjÃ¤)
     * @param tulokset kuvaus jonka avaimet kertovat testattavat alkiomÃ¤Ã¤rÃ¤t
     * @param loytyy   testataanko tilannetta jossa alkiot lÃ¶ytyvÃ¤t (true) kokoelmasta vai ei
     */
    @Override
    public void containsNopeus(Collection<Double> C, SortedMap<Integer, Long> tulokset, boolean loytyy) {
        //Etsittävä alkio pitää valita joko C löytyväksi jos loytyy, muuten ei loydy
        double searchable = 0;
        long startTime;
        long taskTime = 0;
        long parasTulos = 150000;
        // TODO ehkÃ¤ tÃ¤hÃ¤n

        // eri alkiomÃ¤Ã¤rÃ¤t joilla mittaus pitÃ¤Ã¤ tehdÃ¤:
        for (int alkioMaara : tulokset.keySet()) {
            C.clear();//tyhjennetään aikaisemmat tulokset
            C.addAll(satunnaisia(alkioMaara));//lisätään satunnaisia alkioita kokoelmaan


            if(!loytyy){
                searchable = alkioMaara +1;
                for(int i = 0; i<10; i++){
                    startTime = System.nanoTime();
                    C.contains(searchable);
                    taskTime = System.nanoTime() - startTime;
                    if((taskTime<parasTulos)&&taskTime!=0){
                        parasTulos = taskTime;
                    }
                }

            }

            if(loytyy){
                for(int i=0; i<10; i++){//kymmenen testiä
                    //valitse alkio joka löytyy kokoelmasta.
                    Iterator iterator = C.iterator();
                    searchable = (double)iterator.next();
                    startTime = System.nanoTime();
                    C.contains(searchable);
                    taskTime = System.nanoTime() - startTime;
                    if((taskTime<parasTulos)&&taskTime!=0){
                        parasTulos = taskTime;
                    }
                }

            }
            tulokset.put(alkioMaara, parasTulos);

        }

        // TODO ehkÃ¤ tÃ¤nnekin

    }
    private ArrayList<Double> satunnaisia(int n) {
        ArrayList<Double> A = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            A.add(rnd.nextDouble()*n);
        return A;
    }


}
