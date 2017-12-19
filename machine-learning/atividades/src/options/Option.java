package options;
import mdp.*;

/**
 * Created by ldlopes on 12/19/17.
 */
public abstract class Option implements Policy, Action {

    public abstract boolean isEligible(State state);
    public abstract Action giveAction(State state);
    // can return null for termination
    public abstract int getIndex();
}