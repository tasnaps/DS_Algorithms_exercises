package neljasViikko;

import fi.uef.cs.tra.Edge;
import fi.uef.cs.tra.Graph;
import fi.uef.cs.tra.LinkedQueue;
import fi.uef.cs.tra.Vertex;
import java.util.HashSet;

public class TRAII_22s_X4_tapiomer implements TRAII_22s_X4 {
    /**
     * ITSEARVIOINTI TÃ„HÃ„N:
     *aikavaativuus: O(n log n)
     * Käytän leveyssuuntaista algoritmia solmujen värittämiseen ja lukumäärän ottamiseen
     * Ratkaisu perustuu kaarien ja solmujen vaadittuun määrään (ei kehää kun kaaria vähemmän kuin solmuja)
     *
     *
     */

    @Override

    public int puidenMaara(Graph G) {
        laskepuut.summa = 0; //kertoo puiden lkm, nollataan nyt jotta päästään eroon vanhoista arvoista
        laskepuut.iteroidutSolmut.clear();
        if(G.size()==0){
            return 0;
        }

        for (Vertex v: G.vertices()) {// käydään kaikki graafin solmut kerran läpi
            if(!laskepuut.iteroidutSolmut.contains(v)){
                laskepuut.bfs(v);
            }
        }

        return laskepuut.puidenlkm;


        }

        private static class laskepuut{
           public static int summa = 0;
        static HashSet<Vertex> iteroidutSolmut = new HashSet<Vertex>();

        static int puidenlkm = 0;

        static void bfs(Vertex start){
            LinkedQueue<Vertex> vQ = new LinkedQueue<Vertex>();
            int vertexCount = 1;
            int edgeCount = 0;
            vQ.add(start);
            start.setColor(Graph.GRAY);

            while(!vQ.isEmpty()){
                Vertex v = vQ.remove();
                iteroidutSolmut.add(v);

                //Kaarten läpikäynti
                for(Edge e : v.edges()){
                    if(e.getColor() != Graph.GRAY){
                        edgeCount++;
                        e.setColor(Graph.GRAY);
                    }
                }
                //solmujen läpikäynti
                for(Vertex w : v.neighbors()){
                    if(w.getColor() != Graph.GRAY){
                        vertexCount++;
                        w.setColor(Graph.GRAY);
                        vQ.add(w);

                    }
                }
            }
            if(edgeCount<vertexCount){
                summa++;
            }
            puidenlkm = summa;
        }
        }
}

