package toinenViikko;

import java.util.PriorityQueue;
import java.util.Random;

public class t7 {

    public static long mittaa(int k){
        if(k == 0){
            return 0;
        }
        //Random sattuma = new Random();
        Long[] ajat = new Long[k];
        long aloitus = 0;
        long estimatedTime;
        PriorityQueue testattava = new PriorityQueue();
        for(int i = 0; i < k; i++){
            testattava.add(i);
        }

        for(int i=0; i<k; i++){
            aloitus = System.nanoTime();
            testattava.contains(i);
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
        long tuhannella = mittaa(1000);
        long kaksiTuhatta = mittaa(2000);
        long neljaTuhatta = mittaa(4000);
        long kahdeksanTuhatta = mittaa(8000);
        long kuustoistaTuhatta = mittaa(16000);
        long viiskytTonnia = mittaa(50000);
        long sataTuhatta = mittaa(100000);
        long sataviiskyt = mittaa(150000);


        System.out.println("1k alkiolla tulos: " + tuhannella + " " + "ns");
        System.out.println("2k tuhannella alkiolla tulos: " + kaksiTuhatta + " " + "ns");
        System.out.println("4k alkiolla tulos: " + neljaTuhatta + " " + "ns");
        System.out.println("8k alkiolla tulos: " + kahdeksanTuhatta + " ns" );
        System.out.println("16k alkiolla tulos: " + kuustoistaTuhatta + " " + "ns");
        System.out.println("50k alkiolla tulos: " + viiskytTonnia + " ns");
        System.out.println("100k tulos: " + sataTuhatta + " ns");
        System.out.println("150k tulos: " + sataviiskyt + " ns");

        // Tuloksista voi päätellä että kyseessä on O(n) kompleksisuus eli lineaari aikavaativuus. tulos tuplaantuu tuloksissa siirryttäessä 4k alkiosta eteenpäin.
    }

}
