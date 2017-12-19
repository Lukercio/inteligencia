package mdp;

/**
 * Created by ldlopes on 12/19/17.
 */
public abstract interface Policy {
    public abstract Action giveAction(State state);
}