import mdp.*;

/**
 * Created by ldlopes on 12/19/17.
 */

public class Sarsa extends Learner {
    private QGreedyPolicy epsilonPolicy;
    private QGreedyPolicy goodPolicy;
    Env environ;
    double[][] Q;
    double alpha;
    double gamma;
    double epsilon;
    double lambda;
    int[] visited;

    public Sarsa(Env environ, double alpha, double gamma, double epsilon, double lambda ) {
        super(environ);
        Q = new double[environ.maxStates()][environ.availActions().length];
        epsilonPolicy = new QGreedyPolicy(Q, environ.availActions(),epsilon);
        goodPolicy = new QGreedyPolicy(Q, environ.availActions(),0);
        this.environ = environ;
        this.alpha = alpha;
        this.lambda = lambda;
        this.epsilon = epsilon;
        this.gamma = gamma;
        visited = new int[environ.maxStates()];
    }

    public Policy getPolicy() {
        return(goodPolicy);
    }

    public int learn(boolean showProgress) {
        double fortuna;
        double reward;
        State state;
        Action action;
        State newState;
        Action newAction;
        double oldQ;
        double nextQ;
        int steps= 0;
        double[][] e = new double[Q.length][Q[0].length];
        double delta;  // (error term)

        environ.restart();
        visited[environ.currentState().getIndex()]++;

        state = environ.currentState();
        action = epsilonPolicy.giveAction(state);

        while(!environ.atGoal() ) {
            steps++;
            reward = environ.transition(action);
            visited[environ.currentState().getIndex()]++;

            if (showProgress)
                environ.printWorld();

            newState = environ.currentState();
            newAction = epsilonPolicy.giveAction(newState);

            oldQ = Q[state.getIndex()][action.getIndex()];
            if (environ.atGoal())
                nextQ = 0;
            else
                nextQ = Q[newState.getIndex()][newAction.getIndex()];

            delta = reward+gamma*nextQ-oldQ;
            e[state.getIndex()][action.getIndex()]=1;

            for (int i=0; i<Q.length; i++)
                for (int j=0; j<Q[0].length; j++) {
                    Q[i][j] = Q[i][j] + alpha*delta*e[i][j];
                    e[i][j]*=gamma*lambda;
                }

            state = newState;
            action = newAction;
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

}