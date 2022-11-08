package kolmasViikko;

import fi.uef.cs.tra.AbstractGraph;
import fi.uef.cs.tra.DiGraph;
import fi.uef.cs.tra.Vertex;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TRAII_22s_t11_12_pohja {

    public static void main(String[] args) {

        // defaults
        int vertices = 5;
        int edges = 7;

        if (args.length > 0)
            vertices = Integer.parseInt(args[0]);

        if (args.length > 1)
            edges = Integer.parseInt(args[1]);

        int seed = vertices+edges+5;    // tÃ¤stÃ¤kin voi vaihdella minkÃ¤laisia verkkoja syntyy

        if (args.length > 2)
            seed = Integer.parseInt(args[2]);


        // Luodaan satunnainen verkko
        DiGraph graph = GraphMaker.createDiGraph(vertices, edges, seed);
        System.out.println("\nVerkko (numerot ovat solmujen nimiÃ¤, kirjaimet kaarten nimiÃ¤):");
        System.out.println(graph);

        System.out.println("\nSeuraajat kullekin solmulle:");
        for (Vertex v : graph.vertices()) {
            System.out.println(v + " : " + seuraajienJoukko(graph, v));
        }


        int polkuja = 15; // testaa max 15 polkua
        System.out.println("\nPolkuja:");
        for (Vertex v1 : graph.vertices()) {
            for (Vertex v2 : graph.vertices()) {
                if (v1 == v2)
                    continue;
                System.out.println("" + v1 + "->" + v2 + " : " + jokuPolku(graph, v1, v2));
                if (polkuja-- <= 0)
                    break;
            }
        }





    } // main()


    /**
     * Solmun seuraajien joukko.
     * Solmun seuraajien joukko ovat ne solmut joihin on polku annetusta solmusta.
     * @param G tarkasteltava verkko (ei vÃ¤lttÃ¤mÃ¤ttÃ¤ tarvita)
     * @param solmu aloitussolmu
     * @return kaikki solmut joihin on polku solmusta solmu
     */
    static Set<Vertex> seuraajienJoukko(DiGraph G, Vertex solmu) {
        varita(G, DiGraph.WHITE);
        Set<Vertex> s = new HashSet<>();

        // TODO

        return s;
    }


    /**
     * Joku polku solmusta alku solmuun loppu.
     * Versio joka rakentaa polkua rekursiossa edetessÃ¤ (ja purkaa jollei maalia lÃ¶ydy)
     * @param G tarkasteltava verkko (tarvitaan pohjavÃ¤ritykseen)
     * @param alku polun alkusolmu
     * @param loppu polun loppusolmu
     * @return lista polun solmuista, tai tyhjÃ¤ lista jollei polkua ole olemassa
     */
    static List<Vertex> jokuPolku(DiGraph G, Vertex alku, Vertex loppu) {

        GraphMaker.varita(G, DiGraph.WHITE);
        List<Vertex> tulos = new LinkedList<>();

        // TODO

        return tulos;
    }



    /**
     * Syvyyssuuntainen lÃ¤pikÃ¤ynti (tekemÃ¤ttÃ¤ verkolle mitÃ¤Ã¤n)
     * Siis runko.
     *
     * @param G lÃ¤pikÃ¤ytÃ¤vÃ¤ verkko
     */
    static void dfsStart(DiGraph G) {
        for (Vertex v : G.vertices())                // kaikki solmut valkoisiksi
            v.setColor(DiGraph.WHITE);
        for (Vertex v : G.vertices())                // aloitus vielÃ¤ kÃ¤ymÃ¤ttÃ¶mistÃ¤ solmuista
            if (v.getColor() == DiGraph.WHITE)
                dfsRekursio(v);
    }



    // esimerkkejÃ¤


    /**
     * Syvyyssuuntainen lÃ¤pikÃ¤ynti solmusta node alkaen
     *
     * @param node aloitussolmu
     */
    static void dfsRekursio(Vertex node) {
        // tÃ¤hÃ¤n toimenpide solmulle node (jos esijÃ¤rjestys)
        node.setColor(DiGraph.GRAY);
        for (Vertex v : node.neighbors())                // vielÃ¤ kÃ¤ymÃ¤ttÃ¶mÃ¤t
            if (v.getColor() == DiGraph.WHITE)            // naapurit
                dfsRekursio(v);
        // tÃ¤hÃ¤n toimenpide solmulle node (jos jÃ¤lkijÃ¤rjestys)
    }


    /**
     * VÃ¤ritÃ¤ verkon kaikki solmut.
     * @param G vÃ¤ritettÃ¤vÃ¤ verkko
     * @param c vÃ¤ri jota kÃ¤ytetÃ¤Ã¤n
     */
    static void varita(AbstractGraph G, int c) {
        for (Vertex v : G.vertices())
            v.setColor(c);
    }



}

