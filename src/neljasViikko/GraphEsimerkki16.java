package neljasViikko;

import fi.uef.cs.tra.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.HashMap;


public class GraphEsimerkki16 {

    public static void main(String[] args) {

        // defaults
        int vertices = 3;
        int edges = 6;

        if (args.length > 0)
            vertices = Integer.valueOf(args[0]);

        if (args.length > 1)
            edges = Integer.valueOf(args[1]);

        int seed = 0;

        if (args.length > 2)
            seed = Integer.valueOf(args[2]);

        Graph graph;
        GraphEsimerkki16 y = new GraphEsimerkki16();


        System.out.println("\nVerkko1: ");
        graph = GraphMaker.createGraph(vertices, edges, seed);
        System.out.println(graph.toString());
        System.out.println(GraphMaker.toString(graph, 0));

        System.out.print("\nYhtenÃ¤inen: ");
        System.out.println(y.yhtenainen(graph));

        if (y.yhtenainen(graph)) {
            System.out.print("Leikkaussolmut:   ");
            System.out.println(y.leikkausSolmut(graph));
        }

        // System.out.print("KehÃ¤inen:   ");
        // System.out.println(y.kehainen(graph));

        System.out.print("Komponentteja:");
        System.out.println(y.komponentteja(graph));
        // System.out.println(y.komponentit(graph));

        if (y.yhtenainen(graph)) {
            System.out.print("MST:   ");
            System.out.println(y.MSTPrim(graph));
        }


        System.out.println("\nKlikkiverkko: ");
        graph = GraphMaker.createGraph(vertices, 0);
        GraphMaker.addCliques(graph, false, new int[] {3, 4}, seed);
        System.out.println(graph.toString());
        System.out.println(GraphMaker.toMatrixString(graph));
        System.out.println("GreedyColor: " + y.greedyColor(graph));
        System.out.println(graph.toString());



        System.out.println("\n2-jakoinen: ");
        graph = GraphMaker.createBiPartie(vertices, vertices, edges, seed);
        // System.out.println(graph.toString());
        System.out.println("GreedyColor: " + y.greedyColor(graph));
        System.out.println(GraphMaker.toString(graph, 0));
        System.out.println("2-jakoinen: " + y.isBiPartie(graph));
        // System.out.println(graph.toString());

        LinkedList<Edge> sov = y.maksimaalinenSovitus(graph);
        System.out.println("Sovitus: " + sov);
        if (sov != null) System.out.println("Sovitus: " + sov.size());

    }

    boolean yhtenainen(Graph g) {
        // kaikki valkoisiksi
        varita(g, Graph.WHITE);

        // syvyyssyyntainen lpikynti jostain solmusta
        Vertex w = g.firstVertex(); // tai g.vertices().getFirst();
        dfsColor(w, Graph.BLACK);

        for (Vertex v : g.vertices())
            if (v.getColor() == Graph.WHITE)
                return false;
        return true;
    }

    int greedyColor(Graph G) {
        int n = 0;	// solmujen määrä

        // lasketaan & väritetään solmut värillä 0
        for (Vertex v : G.vertices()) {
            v.setColor(0);
            n++;
        }

        int i = 0; // jo väritetyt solmut
        int c = 0; // käytettävä väri

        while (i < n) {
            c++; // uusi väri

            for (Vertex v : G.vertices()) {
                if (v.getColor() == 0) {
                    boolean okColor = true;
                    for (Vertex w : v.neighbors()) {
                        if (w.getColor() == c) {
                            okColor = false;
                            break;
                        }
                    }

                    if (okColor) {
                        v.setColor(c);
                        i++;
                    }
                } // if (v.getColor)
            }	// for
        } // while

        return c;

    } // greedyColor()


    // yhtenaisten komponenttien määrä
    int komponentteja(Graph g) {
        varita(g, Graph.WHITE);
        int n = 0;
        for (Vertex v : g.vertices())
            if (v.getColor() == Graph.WHITE) {
                dfsColor(v, Graph.BLACK);
                n++;
            }
        return n;
    } // komponentit()

    // metsän puiden määrä, 0 jos ei metsä
    // HT: puukomponenttien määrä
    int puusto(Graph g) {
        return kehainen(g) ? 0 : komponentteja(g);
    }



    // syvyyssyynainen lpikynti vritten vrill c
    void dfsColor(Vertex v, int color) {
        v.setColor(color);

        for (Vertex w : v.neighbors())
            if (w.getColor() != color)
                dfsColor(w, color);
    }


    // verkko annetun vriseksi
    void varita(AbstractGraph g, int c) {
        for (Vertex v : g.vertices())
            v.setColor(c);
    }


    // kehttmyyden selvittminen
    // kutsuu kehakomponenttia kullekin komponentille
    boolean kehainen(Graph g) {
        // poistettu
        return false;
    }



    // Finds minimum spanning tree of g
    // returns list of tree edges
    // assumes connected graph
    LinkedList<Edge> MSTPrim(Graph g) {

        varita(g, Graph.WHITE);
        LinkedList<Edge> mst = new LinkedList<Edge>();

        AssignablePriorityQueue<Edge> Q =
                new AssignablePriorityQueue<Edge>();

        int n = g.size();
        if (n == 0)
            return mst;

        Vertex v = g.firstVertex();
        v.setColor(Graph.BLACK);
        // edges to priority queue
        for (Edge e : v.edges())
            Q.add(e, e.getWeight());

        int i = 1;

        while (i < n) {
            if (Q.size() == 0)
                throw new RuntimeException("MSTPrim: Not connected graph");
            Edge e = Q.poll();

            Vertex v1 = e.getStartPoint();
            Vertex v2 = e.getEndPoint();

            if (v1.getColor() != Graph.BLACK ||
                    v2.getColor() != Graph.BLACK) {
                if (v2.getColor() == Graph.BLACK)
                    v2 = v1; // v2 will be the white one

                mst.add(e);
                v2.setColor(Graph.BLACK);
                i++;
                for (Edge e2 : v2.edges())
                    if (e2.getEndPoint(v2).getColor() == Graph.WHITE)
                        Q.add(e2, e2.getWeight());
            } // if
        } // while

        return mst;
    } // MSTPrim()


    boolean isBiPartie(Graph g) {

        varita(g, 2);

        for (Vertex v : g.vertices())
            if (v.getColor() == 2)
                if (! dfs2varita(v, 0))
                    return false;

        return true;
    }

    boolean dfs2varita(Vertex v, int c) {
        v.setColor(c);

        for (Vertex w : v.neighbors()) {

            if (w.getColor() == c)
                return false;
            else if (w.getColor() == 2) {
                if (! dfs2varita(w, (c+1)%2))
                    return false;
            }
        }

        return true;
    }


    // leikkaussolmut suuntaamattomasta verkosta
    LinkedList<Vertex> leikkausSolmut(Graph g) {

        Vertex[] vA = GraphMaker.getVertexArrayIndex(g);
        int n = g.size();

        varita(g, Graph.WHITE);
        for (Edge e : g.edges())
            e.setColor(Graph.WHITE);

        int[] dfsnumber = new int[n];
        int[] low = new int[n];
        int i = 0;
        LinkedList<Vertex> L = new LinkedList<Vertex>();
        for (Vertex v : g.vertices())
            if (v.getColor() == Graph.WHITE)
                i = numberdfs(v, dfsnumber, low, i, L, null);

        return L;
    }

    // fdsnumerinti taulukkoon, samalla luokittelee kaaret
    // puukaaret mustiksi, paluukaaret harmaiksi
    int numberdfs(Vertex v, int[] dfsnumber, int[] low, int i,
                  LinkedList<Vertex> L, Vertex isa) {
        v.setColor(Graph.BLACK);
        dfsnumber[v.getIndex()] = i++;

        // dfs rekursio ja kaarien luokittelu
        for (Edge e : v.edges()) {

            // kaari on jo käsitelty toiseen suuntaa (isä-kaari,
            // tai jälkeläisen paluukaari
            if (e.getColor() != Graph.WHITE)
                continue;

            Vertex w = e.getEndPoint(v);

            if (w.getColor() == Graph.WHITE) {
                e.setColor(Graph.BLACK);    // puukaari
                i = numberdfs(w, dfsnumber, low, i, L, v);
            } else if (w.getColor() == Graph.BLACK)
                e.setColor(Graph.GRAY); // paluukaari
        }

        // low-arvon laskenta
        int min = dfsnumber[v.getIndex()];
        for (Edge e : v.edges()) {
            Vertex w = e.getEndPoint(v);
            if (w == isa)   // isää ei lasketa
                continue;

            // lasten low-luvut
            if (e.getColor() == Graph.BLACK) {
                int pojanlow = low[w.getIndex()];
                if (pojanlow < min)
                    min = pojanlow;

                // esi-isien (joihin paluukaari) dfsnumerot
            } else if (e.getColor() == Graph.GRAY) {
                int esiisandfsnumber = dfsnumber[w.getIndex()];
                if (esiisandfsnumber < min)
                    min = esiisandfsnumber;
            } // valkoisia ei pitäisi ollakaan

        }
        low[v.getIndex()] = min;

        // leikkaussolmujen tunnistus
        if (isa == null) { // juurisolmu
            int poikia = 0;
            for (Edge e : v.edges())
                if (e.getColor() == Graph.BLACK)
                    poikia++;
            if (poikia > 1)
                L.add(v);

            // muut solmut
        } else {
            for (Edge e : v.edges())
                if (e.getColor() == Graph.BLACK && e.getEndPoint(v) != isa) {
                    Vertex w = e.getEndPoint(v);
                    if (low[w.getIndex()] >= dfsnumber[v.getIndex()]) {
                        L.add(v);
                        break;
                    }
                } // if BLACK
        } // else
        return i;
    }

    // palauttaa taulukkona ne solmut jotka ovat kaksijakoisessa
    // verkossa ensimmäisessä joukossa
    Vertex[] biParty(Graph g) {
        boolean ok = isBiPartie(g);
        if (! ok)
            return null;
        int n1 = 0;
        for (Vertex v : g.vertices())
            if (v.getColor() == 0)
                n1++;
        Vertex[] V1 = new Vertex[n1];
        int i = 0;
        for (Vertex v : g.vertices())
            if (v.getColor() == 0)
                V1[i++] = v;

        return V1;
    }

    // Maksimaalinen sovitus
    LinkedList<Edge> maksimaalinenSovitus(Graph g) {
        Vertex[] V1 = biParty(g);
        if (V1 == null)
            return null;

        return maksimaalinenSovitus(g, V1);
    }






    // Maksimaalinen sovitus, edellyttää verkon v1 ensimmäistä solmua
    // olevan ensimmäinen osajoukko
    LinkedList<Edge> maksimaalinenSovitus(Graph g, int v1) {
        Vertex[] V1 = new Vertex[v1];
        int i = 0;
        for (Vertex v : g.vertices()) {
            V1[i++] = v;
            if (i == v1)
                break;
        }

        return maksimaalinenSovitus(g, V1);
    }



    // Vertex.color käytetään syvyyssuuntaisessa haussa
    // Vertex.index käytetään merkkaamaan jo valittuja solmuja
    LinkedList<Edge> maksimaalinenSovitus(Graph g, Vertex[] V1) {

        for (Vertex v : g.vertices())
            v.setIndex(0);

        for (Edge e : g.edges())
            e.setColor(0);

        LinkedList<Edge> p;

        while (true) {
            p = laajentavaPolku(g, V1);
            if (p == null)
                break;

            System.out.println("Laajentava: " + p);

            for (Edge e : p) {
                e.setColor(e.getColor() ^ 1);
                e.getEndPoint().setIndex(1);
                e.getStartPoint().setIndex(1);
            }
        }

        p = new LinkedList<Edge>();
        for (Edge e : g.edges())
            if (e.getColor() == 1)
                p.add(e);

        return p;
    }

    LinkedList<Edge> laajentavaPolku(Graph g, Vertex[] V1) {
        int n1 = V1.length;

        LinkedList<Edge> p = new LinkedList<Edge>();

        for (int i = 0; i < n1; i++) {
            if (V1[i].getIndex() == 0 && laajentavaPolku2(g, V1[i], p))
                return p;
        }

        return null;

    }

    // Syvyyssuuntaista hakua käyttävä versio
    boolean laajentavaPolku(Graph g, Vertex v, LinkedList<Edge> p) {

        varita(g, 0);
        p.clear();

        return laajentavaPolku_r(v, p, 0);
    }

    boolean laajentavaPolku_r(Vertex v, LinkedList<Edge> p, int level) {

        if (level%2 == 1 && v.getIndex() == 0)
            return true;

        v.setColor(1);
        for (Edge e : v.edges()) {

            // joka toinen kaari sovittamaton
            if (e.getColor() != level%2)
                continue;

            // ei samaan solmuun takaisin
            Vertex w = e.getEndPoint(v);
            if (w.getColor() != 0)
                continue;

            p.addLast(e);

            if (laajentavaPolku_r(w, p, level+1))
                return true;

            p.removeLast();     // ei löytynyt
        }

        return false;
    }

    // Leveyssuuntaista hakua käyttävä versio
    boolean laajentavaPolku2(Graph g, Vertex v, LinkedList<Edge> p) {

        p.clear();

        // kutakin avoinna olevaa hakua vastaa HashMap<Vertex, Integer>
        // Solmut ovat mukana olevat solmut, Integer etäisyys polun alusta (tarvitaan
        // polun rekontruoimiseen lopuksi)
        LinkedQueue<HashMap<Vertex, Integer>> PQ = new LinkedQueue<HashMap<Vertex, Integer>>();
        LinkedQueue<Vertex> Q = new LinkedQueue<Vertex>();

        HashMap<Vertex, Integer> h = new HashMap<Vertex, Integer>();
        h.put(v, 0);
        PQ.offer(h);
        h = null;
        Q.offer(v);
        Q.offer(null);

        int level = 0;

        while (!Q.isEmpty()) {

            v = Q.poll();

            if (v == null) {
                if (Q.isEmpty())
                    return false;
                level++;
                Q.offer(null);
                continue;
            }

            h = PQ.poll();

            if (level%2 == 1 && v.getIndex() == 0)
                break;

            for (Edge e : v.edges()) {

                // joka toinen kaari sovittamaton
                if (e.getColor() != level%2)
                    continue;

                // ei samaan solmuun takaisin
                Vertex w = e.getEndPoint(v);
                if (h.get(w) != null)
                    continue;

                // Uusi vaihtohto, laitetaan jonoon
                HashMap<Vertex, Integer> h2 = new HashMap<Vertex, Integer>(h);
                h2.put(w, level+1);
                Q.offer(w);
                PQ.offer(h2);

            }

            h = null;

        } // while !isEmpty

        if (h == null)
            return false;

        // rakennetaan kaaripolku solmu-sijainti -kuvauksesta
        int n = h.size();
        Vertex[] path = new Vertex[n];
        for (Vertex w : h.keySet())
            path[h.get(w)] = w;

        for (int i = 0; i < n-1; i++)
            p.add(path[i].getEdge(path[i+1]));

        return true;
    }







}

