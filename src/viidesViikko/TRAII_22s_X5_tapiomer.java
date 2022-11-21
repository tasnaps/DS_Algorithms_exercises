package viidesViikko;

import fi.uef.cs.tra.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class TRAII_22s_X5_tapiomer implements TRAII_22s_X5 {
    /**
     *Ratkaisussa käytän HashSettiä tarkistamaan onko solmuissa käyty aiemmin.
     * Kun ollaan juuressa niin voidaan käyttää joko !Graph.GREY tai HashSet.contains puussa etenemiseen.
     * Contains on hyvä kun aikaisemmat kaaret ja juuret on jo värjätty harmaiksi.
     * aikavaativuus kai O(2n)
     */
    @Override
    public float painavimmanPuunPaino(DiGraph G) {
        laskepuut.summa = 0.0F;
        laskepuut.kaaria = false;
        laskepuut.iteroidutSolmut.clear();
        laskepuut.puunPainot.clear();

        if(G.size()==0){
            return Float.NaN;
        }
        for (Vertex v: G.vertices()) {// käydään kaikki graafin solmut kerran läpi
            if(!TRAII_22s_X5_tapiomer.laskepuut.iteroidutSolmut.contains(v)){
                    TRAII_22s_X5_tapiomer.laskepuut.bfs(v);
            }
        }
        if(!laskepuut.kaaria){
            return 0.0F;
        }

        float paino = 0;
        //otetaan suurin puu listasta
        Iterator<Float> i = laskepuut.puunPainot.iterator();
        while(i.hasNext()){
            float verrokki = i.next();
            if(paino < verrokki){
                paino = verrokki;
            }
        }
        return paino;
    }

    private static class laskepuut{
        public static float summa = 0;
        public static boolean kaaria = false;
        static HashSet<Vertex> iteroidutSolmut = new HashSet<Vertex>();

        static HashSet<Edge> iteroidutKaaret = new HashSet<Edge>();

        public static ArrayList<Float> puunPainot = new ArrayList<>();


        static void bfs(Vertex start){
            LinkedQueue<Vertex> vQ = new LinkedQueue<Vertex>();
            //Tarkista mikä on aloitussolmu eli juuri
            vQ.add(start);
            start.setColor(Graph.GRAY);


            while(!vQ.isEmpty()){
                Vertex v = vQ.remove();
                //Kaarten läpikäynti
                for(Edge e : v.edges()){
                    if(e.getColor() != Graph.GRAY || laskepuut.iteroidutKaaret.contains(e)){
                        summa += e.getWeight();
                        e.setColor(Graph.GRAY);
                        kaaria = true;
                        laskepuut.iteroidutKaaret.add(e);

                    }
                }
                /**
                 * Kun ollaan puun juuressa edetään vaan or ehtolauseilla
                 */
                for(Vertex w : v.neighbors()){
                    if(w.getColor() != Graph.GRAY || laskepuut.iteroidutSolmut.contains(w) || laskepuut.iteroidutSolmut.contains(v)){
                        w.setColor(Graph.GRAY);
                        vQ.add(w);

                    }
                }
                iteroidutSolmut.add(v);
            }
            puunPainot.add(summa);
            summa = 0;

        }
    }
}
