package toinenViikko;

import java.util.List;
import java.util.PriorityQueue;

public class t8 {
    public static long mittaa(int k){
        if(k == 0){
            return 0;
        }
        //Random sattuma = new Random();
        Long[] ajat = new Long[k];
        long aloitus = 0;
        long estimatedTime;
        Long[] lisattavaLista = new Long[k];
        PriorityQueue testattava = new PriorityQueue();


        for(int i = 0; i < k; i++){
            lisattavaLista[i] = (long) i;
        }

        for(int i=0; i<k; i++){
            aloitus = System.nanoTime();
            testattava.addAll(List.of(lisattavaLista));
            estimatedTime = System.nanoTime() - aloitus;
            ajat[i] = estimatedTime;
        }
        int alkiot = ajat.length;
        long summa = 0;
        for (Long i: ajat){
            summa = summa+i;
        }
        return summa/alkiot;
    }


    public static void main(String[] args) {
        long viisSataa = mittaa(500);
        long tuhannella = mittaa(1000);
        long kaksiTuhatta = mittaa(2000);
        long neljaTuhatta = mittaa(4000);
        long kahdeksanTuhatta = mittaa(8000);


        System.out.println("500 alkiolla tulos: " + viisSataa + " ns");
        System.out.println("1k alkiolla tulos: " + tuhannella + " " + "ns");
        System.out.println("2k tuhannella alkiolla tulos: " + kaksiTuhatta + " " + "ns");
        System.out.println("4k alkiolla tulos: " + neljaTuhatta + " " + "ns");
        System.out.println("8k alkiolla tulos: " + kahdeksanTuhatta + " ns" );


        // 500 -> 1k -> 75% lisää kasvua ajassa
        // 1k siirros 2k -> 50% lisää kasvua ajassa
        // 2k siirros 4k -> 29 % kasvua
        // 4k siirros 8k -> 55% kasvua
        // kyseessä varmaan neliöllinen aikavaativuus
    }
}
