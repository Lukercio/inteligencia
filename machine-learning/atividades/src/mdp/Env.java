package mdp;

/**
 * Created by ldlopes on 12/19/17.
 */
public abstract class Env {
    public Env(Settings sets) {
    }
    public abstract boolean validState(State state);
    public abstract State genState(int index);
    public abstract boolean atGoal();
    public abstract void printWorld();
    public abstract void printProbs(Action action);
    public abstract MemberTester interpolate(State[] states);
    public abstract void restart();
    public abstract State currentState();
    public abstract double transition(Action action);
    public abstract Action[] availActions();
    public abstract int maxStates();


}