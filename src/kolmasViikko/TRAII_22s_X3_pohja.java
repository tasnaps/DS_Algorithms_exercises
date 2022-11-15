package kolmasViikko;

import java.util.*;

public class TRAII_22s_X3_pohja implements TRAII_22s_X3 {
    //                    ^^^^^ oma tunnus tÃ¤hÃ¤n


    /**
     * ITSEARVIOINTI TÃ„HÃ„N:
     * Mielestäni testit mittautuvat oikein kun käytössä on Arraylist, TreeSet contains pitäis olla logaritminen, mutta tulokset pysyvät  samalla alueella alkiomäärää kasvatettaessa
     * HashSet contains toiminto on vakioaikainen ja se näkyy myös mittauksissa.
     * Yritin parantaa edellisten viikkojen mittaustekniikkaa. Otan aina alkion listan keskeltä testiin, ja pyöräytän mittauksen useamman for loopin läpi, jotta tulisi luotettavia tuloksia...
     * Toivottavasti ratkaisu näyttää loogiselta.
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
        double searchable = 0;
        long startTime;
        long taskTime = 0;
        long parasTulos = 150000;
        Long[] testit = new Long[20];
        long palautus = 0;

        for (int alkioMaara : tulokset.keySet()) {
            C.clear();//tyhjennetään aikaisemmat tulokset
            ArrayList<Double>lista =new ArrayList<>(satunnaisia(alkioMaara));
            Object haettava = lista.get((lista.size()/2)+1); //valitaan keskimmäinen alkio
            C.addAll(lista);
            //ajetaan parit lämmittelyt
            for(int l = 0; l<5; l++){
                double joku = 1.4;
                C.contains(joku);
            }


            if(!loytyy){
                searchable = alkioMaara +1; //satunnaisia() metodissa otetaan lukuja joissa max on alkiomäärä. yksi isompi siis ei löydy listasta
                for(int i = 0; i<20; i++){
                    parasTulos = Integer.MAX_VALUE; //Tätä arvoa aina pienennetään sisemmässä loopissa
                    for(int j = 0; j<20; j++){
                        startTime = System.nanoTime(); //Mittauksen aloitus
                        for(int x = 0; x<5; x++){ //Mitataan contains 5 kertaa
                            C.contains(searchable);
                        }
                        taskTime = (System.nanoTime() - startTime)/5; //lasketaan keskiarvo sisemmälle loopille
                        if((taskTime<parasTulos)&&taskTime!=0){ //Mikäli keskiarvo pienempi kuin aikaisemmat arvot, päivitetään tulosta.
                            parasTulos = taskTime;
                        }
                    }
                    testit[i] = parasTulos; //lisätään kunkin kierroksen pienimmät tulokset taulukkoon
                }
                long summa = 0;
                for(long testi : testit){ //taas keskiarvo
                    summa += testi;
                }
                palautus = summa/testit.length;
            }// loytyy haara on peilikuva yllaolevasta koodista, ainoastaan haettava on olemassa
            if(loytyy){
                for(int i=0; i<20; i++){
                    parasTulos = Integer.MAX_VALUE;
                    for(int j=0; j<20; j++){
                        startTime = System.nanoTime();
                        for(int x=0; x<5; x++){
                            C.contains(haettava);
                        }
                        taskTime = (System.nanoTime() - startTime)/5;
                        if((taskTime<parasTulos)&&taskTime!=0){
                            parasTulos = taskTime;
                        }
                    }
                    testit[i] = parasTulos;
                }
                long summa = 0;
                for(long testi : testit){
                    summa += testi;
                }

                palautus = summa/testit.length;

            }

            tulokset.put(alkioMaara, palautus);

        }
    }
    private ArrayList<Double> satunnaisia(int n) {
        ArrayList<Double> A = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            A.add(rnd.nextDouble()*n);
        return A;
    }


}
