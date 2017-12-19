import mdp.*;

/**
 * Created by ldlopes on 12/19/17.
 */

public class QLearning extends Learner {

    protected QGreedyPolicy learnPolicy;
    protected QGreedyPolicy goodPolicy;
    protected Env environ;
    protected double[][] Q;
    protected double alpha;
    protected double gamma;
    double epsilon;
    protected int[] visited;

    public QLearning(Env environ, double[][] Q, double alpha, double gamma, double epsilon) {
        super(environ);

        if ((Q.length != environ.maxStates()) || (Q[0].length!=environ.availActions().length))
            throw( new IllegalArgumentException("predefined Q has the wrong dimensions"));

        this.Q = (double[][])Q.clone();
        this.learnPolicy = new QGreedyPolicy(Q, environ.availActions(),epsilon);
        this.goodPolicy = new QGreedyPolicy(Q, environ.availActions(),0);
        this.environ = environ;
        this.alpha = alpha;
        this.epsilon = epsilon;
        this.gamma = gamma;
        visited = new int[environ.maxStates()];
    }
    public QLearning(Env environ, double alpha, double gamma, double epsilon) {
        this(environ, new double[environ.maxStates()][environ.availActions().length], alpha, gamma, epsilon);
    }

    public Policy getPolicy() {
        return(goodPolicy);
    }

    protected int myMaxIndex(double[] array, State state) {
        return(Util.maxIndex(array));
    }

    protected double learnStep() {
        double reward;
        State state;
        Action action;
        State newState;
        double oldQ;
        double nextQ;

        state = environ.currentState();
        action = learnPolicy.giveAction(state);
        reward = environ.transition(action);
        newState = environ.currentState();

        oldQ = Q[state.getIndex()][action.getIndex()];
        nextQ = Q[newState.getIndex()][myMaxIndex(Q[newState.getIndex()], newState)];
        Q[state.getIndex()][action.getIndex()] = (1.0-alpha)*oldQ + alpha*(reward+gamma*nextQ);

        return(reward);
    }

    public int learn(boolean showProgress) {
        int steps= 0;

        environ.restart();
        visited[environ.currentState().getIndex()]++;

        while(!environ.atGoal() ) {
            learnStep();

            steps++;
            visited[environ.currentState().getIndex()]++;

            if (showProgress)
                environ.printWorld();
        }
        return(steps);
    }
    public int learn() {
        return(learn(false));
    }

    public void printVisits(int x) {
        String temp;
        for(int i=0; i<Q.length; i++) {
            temp = Integer.toString(visited[i]);
            while (temp.length()<4)
                temp+=" ";
            System.out.print(temp.substring(0,4)+" ");
            if (( (i+1) %x )== 0)
                System.out.println();
        }
    }

    public int[] getVisits() {
        return(visited);
    }

    public void resetVisits() {
        visited = new int[environ.maxStates()];
    }
}