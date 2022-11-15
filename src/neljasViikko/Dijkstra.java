package neljasViikko;

import fi.uef.cs.tra.*;
import java.util.Random;
import java.util.Vector;

public class Dijkstra {

    public static void main(String[] args) {

        // defaults
        int vertices = 3;
        int edges = 6;

        if (args.length > 0)
            vertices = Integer.valueOf(args[0]);

        if (args.length > 1)
            edges = Integer.valueOf(args[1]);

        int seed = vertices+edges;

        if (args.length > 2)
            seed = Integer.valueOf(args[2]);

        Random r = new Random(seed);

        Dijkstra y = new Dijkstra();

        // Luodaan satunnainen verkko
        DiGraph graph = GraphMaker.createDiGraph(vertices, edges, seed);
        // annetaan painot kaarille
        for (Edge e : graph.edges())
            e.setWeight((float)(r.nextInt(edges)+1));

        System.out.println("\nVerkko:");
        System.out.println(graph);

        float[] et = y.Dijkstra(graph);
        System.out.print("\nEtäisyydet");
        for (int i = 1; i < graph.size(); i++)
            System.out.print(" " + i + ":" + et[i]);
        System.out.println();
        System.out.println(graph);
    }


    float inf = (float)999999;

    float[] Dijkstra(DiGraph G) {
        Vertex[] va = GraphMaker.getVertexArrayIndex(G);
        int n = G.size();
        va[0].setWeight((float)0.0);
        va[0].setColor(DiGraph.BLACK);

        // taulukkona D käytetään solmujen painoja
        // solmujen painoiksi etäisyys aloitussolmuista
        for (int i = 1; i < n; i++) {
            va[i].setWeight(inf);
            va[i].setColor(DiGraph.WHITE);
        }

        for (Edge e : va[0].edges()) {
            Vertex w = e.getEndPoint();
            w.setWeight(e.getWeight());
        }

        // prioriteettijono, taulukko prioriteettijonon solmuista
        AdjustablePriorityQueue<Vertex> Q = new AdjustablePriorityQueue<Vertex>();
        Vector<BTreeNode<Vertex>> pqn = new Vector<BTreeNode<Vertex>>(n);
        pqn.setSize(n);
        for (int i = 1; i < n; i++)
            pqn.set(i, Q.offer(va[i]));

        while (! Q.isEmpty()) {
            Vertex v = Q.poll();       // lähin jäljelläoleva solmu
            v.setColor(DiGraph.BLACK);
            for (Edge e : v.edges()) {  // kaarien&naapurien läpikäynti
                Vertex w = e.getEndPoint();
                if (w.getColor() != DiGraph.WHITE)  // jo valmis
                    continue;
                if (w.getWeight() > v.getWeight() + e.getWeight()) {
                    // löytyi lyhyempi reitti
                    w.setWeight(v.getWeight() + e.getWeight());
                    Q.improvePriority(pqn.get(w.getIndex()));
                }
            }
        }

        // tehdään vielä taulukkokin
        float[] D = new float[n];
        for (int i = 0; i < n; i++)
            D[i] = va[i].getWeight();

        return D;
    }



}
