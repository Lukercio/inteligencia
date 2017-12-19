package mdp;

/**
 * Created by ldlopes on 12/19/17.
 */
public abstract class Learner {
    public Learner(Env environ) {
    }
    public abstract int learn();
    public abstract Policy getPolicy();
}