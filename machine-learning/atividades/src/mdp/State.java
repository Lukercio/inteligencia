package mdp;

/**
 * Created by ldlopes on 12/19/17.
 */
public abstract class State {
    public abstract boolean same(State other);
    public abstract int getIndex();
    protected abstract void applyAction(Action action);
}