/**
 * Created by ldlopes on 12/19/17.
 */
import java.util.Vector;

public class PolicyIteration {

    MarkovDecisionProcess mdp;
    PolicyEvaluator pe;

    public PolicyIteration(MarkovDecisionProcess mdp) {
        this.mdp = mdp;
        pe = new PolicyEvaluator(mdp);
    }

    public void generateRandomPolicy() {
        State s=mdp.getStartState();
        while(s != null) {
            mdp.setAction(s, mdp.getRandomAction());
            s = mdp.getNextState();
        }
		/*
		mdp.setAction(1,1, 3);
		mdp.setAction(2,1, 1);
		mdp.setAction(3,1, 3);
		mdp.setAction(1,2, 3);
		mdp.setAction(3,2, 0);
		mdp.setAction(1,3, 2);
		mdp.setAction(2,3, 2);
		mdp.setAction(3,3, 1);
		mdp.setAction(1,4, 1);
		*/
    }

    public int solve() {

        int numIterations = 0;

        mdp.generateProperPolicy();

        boolean changed = false;

        do {

            changed = false;

            pe.solve();

            State state = mdp.getStartState();
            while(state != null) {

                if(state.terminate){
                    state = mdp.getNextState();
                    continue;
                }

                Action action=mdp.getStartAction();
                Action policyAction = mdp.getAction(state);

                double maxCurrentUtil = -1e30;
                double policyUtility = 0;
                Action maxAction = null;

                while(action != null){

                    Vector T = mdp.getTransition(state, action);
                    int s = T.size();
                    double nextUtil = 0;
                    for(int i=0; i<s; ++i) {
                        Transition t=(Transition)T.get(i);
                        double prob=t.probability;
                        State sPrime=t.nextState;
                        nextUtil += (prob * mdp.getUtility(sPrime));
                    }

                    if(action == policyAction) {
                        policyUtility = nextUtil;
                    }

                    if(nextUtil > maxCurrentUtil){
                        maxCurrentUtil = nextUtil;
                        maxAction = action;
                    }

                    action = mdp.getNextAction();
                }

                if(maxCurrentUtil > policyUtility){
                    mdp.setAction(state, maxAction);
                    changed = true;
                }

                state = mdp.getNextState();

            }

            numIterations ++;

        } while(changed);

        return numIterations;
    }

}
