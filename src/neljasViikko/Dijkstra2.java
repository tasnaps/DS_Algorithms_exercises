package neljasViikko;

// Dijkstra2.java SJ
// Tämä versio palauttaa myös edeltäjätaulukon jotta itse polut
// saadaan esiin

import fi.uef.cs.tra.*;
import java.util.Random;
import java.util.Vector;
import java.util.LinkedList;

public class Dijkstra2 {

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

        Dijkstra2 y = new Dijkstra2();

        // Luodaan satunnainen verkko
        DiGraph graph = GraphMaker.createDiGraph(vertices, edges, seed, (float)edges);

        System.out.println("\nVerkko:");
        System.out.println(graph);

        Vertex[] ed = y.Dijkstra(graph);
        System.out.print("\nEtäisyydet ja polut\n");
        for (Vertex v : graph.vertices()) {
            if (v.getIndex() == 0)
                System.out.println("Lähtösolmu " + v);
            else
                System.out.println(" -> " + v + ": " + v.getWeight() + " " +
                        y.haePolku(v, ed));
        }

    }   // main

    float inf = Float.POSITIVE_INFINITY;

    // hakee polun lähtösolmusta solmuun v edeltäjätaulukon avulla
    LinkedList<Vertex> haePolku(Vertex v, Vertex[] E) {
        LinkedList<Vertex> polku = new LinkedList<Vertex>();
        if (E[v.getIndex()] == null)
            return polku;    // ei polkua
        Vertex n = v;
        while (n != null) {
            polku.addFirst(n);
            n = E[n.getIndex()];
        }
        return polku;
    }



    // tämä versio palauttaa edeltäjätaulukon
    // solmujen etäisyys talletetaan kunkin solmun painokenttään
    // TODO: voisi ottaa lähtösolmun parametrina
    Vertex[] Dijkstra(DiGraph G) {
        Vertex[] va = GraphMaker.getVertexArrayIndex(G);
        int n = G.size();
        va[0].setWeight((float)0.0);
        va[0].setColor(DiGraph.BLACK);

        // edeltäjien taulukko
        Vertex[] E = new Vertex[n];
        E[0] = null;

        // etäisyydet talletetaan solmujen painoiksi
        for (int i = 1; i < n; i++) {
            va[i].setWeight(inf);
            va[i].setColor(DiGraph.WHITE);
            E[i] = null;
        }

        // lähtösolmusta lähtevät kaaret
        for (Edge e : va[0].edges()) {
            Vertex w = e.getEndPoint();
            w.setWeight(e.getWeight());
            E[w.getIndex()] = va[0];    // lähtösolmu on alustava edeltäjä
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
                    E[w.getIndex()] = v; // edeltäjä
                }
            }
        }


        return E;
    }   // Dijkstra()



}   // class
