package sgw;

/**
 * Created by ldlopes on 12/19/17.
 */


public class GridOptionTrainer {
    public static void train(GridSettings origSettings, double alpha, double gamma, double epsilon, int numEpisodes, GridOption option, int[] goalCoords) {
        GridSettings tempSettings;
        QLearning trainer = null;

        if (!option.isQuitter(new GridState(goalCoords, null)))
            throw (new IllegalArgumentException("goal state is not a terminal state"));

        System.out.println("Now training: "+option);
        try {
            for (int startX=0; startX<origSettings.getGridXSize(); startX++)
                for (int startY=0; startY<origSettings.getGridYSize(); startY++)
                    if ( option.isEligible(new GridState(startX, startY, null))) {

                        tempSettings = (GridSettings)origSettings.clone();
                        tempSettings.setStartState(new GridState(startX, startY, null));
                        tempSettings.setGoalState (new GridState(goalCoords, null));

                        if (null == trainer)
                            trainer = new QLearning(new GridEnvironment(tempSettings), alpha, gamma, epsilon);
                        else
                            trainer = new QLearning(new GridEnvironment(tempSettings), ((QGreedyPolicy)trainer.getPolicy()).getQ(), alpha, gamma, epsilon);

                        System.out.print("Learning "+startX+" "+startY+": ");
                        for (int curEpisode = 0; curEpisode<numEpisodes; curEpisode++) {
                            if ((curEpisode%5) == 4)
                                System.out.print(".");
                            trainer.learn(false);
                        }
                        option.replacePolicy(trainer.getPolicy());
                        System.out.println(" done");
                    }
        } catch (CloneNotSupportedException cnse) { System.err.println("Big booboo... settings aren't cloneable???"); }
    }
}