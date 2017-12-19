package options;
import mdp.*;

/**
 * Created by ldlopes on 12/19/17.
 */
public class SimpleOption extends Option {
    Policy myPolicy;
    int index;
    State[] starters;
    State[] quitters;
    State goal;

    protected boolean isInArray(State[] array, State state) {
        for (int i=0; i<array.length; i++)
            if (array[i].same(state))
                return(true);

        return (false);
    }

    public SimpleOption(Policy myPolicy, int index, State[] starters, State[] quitters) {
        this.myPolicy = myPolicy;
        this.index = index;
        this.starters = starters;
        this.quitters = quitters;
    }

    public int getIndex() {
        return (index);
    }

    public boolean isEligible(State state) {
        return(isInArray(starters, state));
    }

    public Action giveAction(State state) {
        if (isInArray(quitters, state))
            return(null);
        else
            return (myPolicy.giveAction(state));
    }
}