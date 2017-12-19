package sgw;
import options.*;
import mdp.*;

/**
 * Created by ldlopes on 12/19/17.
 */


public abstract class GridOption extends Option {
    protected Policy policy;
    private int index;

    protected int[] getCoords(GridState state) {

        return(state.getCoords());
    }

    public GridOption(Policy policy, int index) {
        this.policy = policy;
        this.index = index;
    }

    public int getIndex() {
        return (index);
    }

    public abstract boolean isEligible(State state);

    public abstract boolean isQuitter(State state);

    protected void replacePolicy(Policy newPolicy) {
        this.policy = newPolicy;
    }

    public Action giveAction(State state) {
        if (isQuitter(state))
            return(null);
        else
            return(policy.giveAction(state));
    }
}