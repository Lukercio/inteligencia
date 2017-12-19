package mdp;

/**
 * Created by ldlopes on 12/19/17.
 */
public abstract class Settings implements Cloneable {
    public abstract void setStartState(State startState);
    public abstract void setGoalState(State goalState);
    public Object clone() throws CloneNotSupportedException {
        return(super.clone());
    }
}