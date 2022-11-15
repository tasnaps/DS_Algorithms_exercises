package neljasViikko;

import fi.uef.cs.tra.AbstractGraph;
import fi.uef.cs.tra.DiGraph;
import fi.uef.cs.tra.Vertex;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TRAII_22s_t12_esim {

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

        jokuPolku_r(alku, loppu, tulos);
        return tulos;
    }

    /**
     * Rekursiivinen osa polun hakua, rakentaa polun solmusta v solmuun loppu.
     * @param v solmu jossa ollaan menossa
     * @param loppu tavoiteltava maalisolmu
     * @param tulos lista johon polku kerÃ¤tÃ¤Ã¤n
     * @return true jos maali lÃ¶ytyi tÃ¤stÃ¤ tai syvemmÃ¤ltÃ¤, muuten false
     */
    static boolean jokuPolku_r(Vertex v, Vertex loppu, List<Vertex> tulos) {

        v.setColor(DiGraph.BLACK);
        tulos.add(v);       // solmu spekulatiivisesti listaan
        if (v == loppu) // maali lÃ¶ytyi
            return true;
        for (Vertex w : v.neighbors()) {
            if (w.getColor() == DiGraph.WHITE)
                if (jokuPolku_r(w, loppu, tulos))   // jos maali lÃ¶ytyi
                    return true;                // poistutaan muutamatta listaa

        }

        tulos.remove(tulos.size()-1);   // jollei polkua lÃ¶ytynyt, niin poistetaan
        return  false;
    }


    /**
     * Joku polku solmusta alku solmuun loppu.
     * Versio joka rakentaa polkua vasta palatessa kun maali lÃ¶ytyi
     * @param G tarkasteltava verkko (tarvitaan pohjavÃ¤ritykseen)
     * @param alku polun alkusolmu
     * @param loppu polun loppusolmu
     * @return lista polun solmuista, tai tyhjÃ¤ lista jollei polkua ole olemassa
     */
    static List<Vertex> jokuPolku2(DiGraph G, Vertex alku, Vertex loppu) {

        GraphMaker.varita(G, DiGraph.WHITE);
        List<Vertex> tulos = new LinkedList<>();

        jokuPolku_r(alku, loppu, tulos);
        return tulos;

    }

    /**
     * Rekursiivinen osa polun hakua, rakentaa polun solmusta v solmuun loppu.
     * @param v solmu jossa ollaan menossa
     * @param loppu tavoiteltava maalisolmu
     * @param tulos lista johon polku kerÃ¤tÃ¤Ã¤n
     * @return true jos maali lÃ¶ytyi tÃ¤stÃ¤ tai syvemmÃ¤ltÃ¤, muuten false
     */
    static boolean jokuPolku2_r(Vertex v, Vertex loppu, List<Vertex> tulos) {

        v.setColor(DiGraph.BLACK);
        if (v == loppu) {   // maali lÃ¶ydetty
            tulos.add(0, v); // kasataan listaa
            return true;
        }
        for (Vertex w : v.neighbors()) {
            if (w.getColor() == DiGraph.WHITE)
                if (jokuPolku_r(w, loppu, tulos)) { // rekursio lÃ¶ysi maalisolmun
                    tulos.add(0, v);    // solmu v lisÃ¤Ã¤ listan alkuun
                    return true;
                }

        }
        return  false;
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
