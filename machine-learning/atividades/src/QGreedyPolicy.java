import mdp.*;

/**
 * Created by ldlopes on 12/19/17.
 */

public class QGreedyPolicy implements Policy {
    protected Action[] actions;
    protected double[][] Q;
    protected double epsilon;

    public QGreedyPolicy(double[][] Q, Action[] actions, double epsilon) {
        this.Q = Q;
        this.actions = actions;
        this.epsilon = epsilon;
    }

    public Action giveAction(State state) {
        if (Math.random()<epsilon) {
            return (actions[(int)(actions.length*Math.random())]);
        } else {
            return (actions[Util.maxIndex( Q[state.getIndex()] )]);
        }
    }

    public void printQ(int x) {
        String temp;

        for (int j=0; j<actions.length; j++) {
            System.out.println(actions[j]);
            for(int i=0; i<Q.length; i++) {
                temp = Double.toString(Q[i][j]);
                while (temp.length()<6)
                    temp+=" ";
                System.out.print(temp.substring(0,6)+" ");
                if (( (i+1) %x )== 0)
                    System.out.println();
            }
            System.out.println();
        }
    }

    public double[][] getQ() {
        return((double[][])Q.clone());
    }
}