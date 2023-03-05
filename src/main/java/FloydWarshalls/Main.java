package FloydWarshalls;

import java.util.ArrayList;
import java.util.Collections;

import Dijkstra.DiEdge;
import Dijkstra.DijkstraSP;
import Dijkstra.EWDiGraph;

public class Main {
    public static void main(String[] args) {
        var MAX = 1000;
        EWDiGraph ewd = new EWDiGraph(MAX);

        var v = new int[MAX];
        var numbers1 = new ArrayList<Integer>();
        var numbers2 = new ArrayList<Integer>();
        for (var i = 0; i < MAX; i++) {
            v[i] = i;
            numbers1.add(i);
            numbers2.add(i);
        }
        Collections.shuffle(numbers1);
        Collections.shuffle(numbers2);

        for (var i = 0; i < MAX; i++) {
            if (numbers1.get(i).equals(numbers2.get(i)))
                return; // dont connect to itself

            ewd.addEdge(new DiEdge(numbers1.get(i), numbers2.get(i), 1));
        }

        var floydw = new FloydWConcurrent();

        var start = System.nanoTime();

        var dm = floydw.start(8, ewd, v);

        var end = System.nanoTime();
        var time = (end - start);

        System.out.println("Time for FloWar: " + time);

        start = System.nanoTime();

        int s = 0;
        DijkstraSP sp = new DijkstraSP(ewd, s);

        for(var p=1; p< ewd.V(); p++){
            if(sp.distTo(p)< Double.POSITIVE_INFINITY){
                System.out.printf("%d to %d (%f): ",s,p,sp.distTo(p));


            } else {
                System.out.printf("%d to %d: no path\n ", s, p);
            }
        }

        end = System.nanoTime();
        time = (end - start);

        System.out.println("Time for Dijkstra: " + time);
    }
}
