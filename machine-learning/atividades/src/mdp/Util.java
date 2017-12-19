package mdp;

/**
 * Created by ldlopes on 12/19/17.
 */
public class Util {
    public static  int maxIndex(double[] array, boolean[] except) {
        int maxInd = 0;
        double maxVal = Double.NEGATIVE_INFINITY;
        int maxIndex = array.length;
        int numMax = 0;

        for (int i=0; i<maxIndex; i++) {
            if (i>=except.length  || (!except[i]))
                if (array[i]==maxVal) {
                    numMax++;
                } else if (array[i]>maxVal) {
                    maxInd = i;
                    maxVal = array[i];
                    numMax = 1;
                }
        }

        if (numMax>=1) {
            numMax = (int)(Math.random()*(double)numMax);
            for (int i = 0; numMax>=0; i++) {
                if (array[i] == maxVal) {
                    numMax--;
                    maxInd=i;
                }
            }
        }
        return(maxInd);

    }
    public static int maxIndex(double[] array) {
        return(maxIndex(array, new boolean[0]));
    }

    public static  int maxIndex(int[] array, boolean[] except) {
        int maxInd = 0;
        int maxVal = Integer.MIN_VALUE;
        int maxIndex = array.length;
        int numMax = 0;

        for (int i=0; i<maxIndex; i++) {
            if (i>=except.length  || (!except[i]))
                if (array[i]==maxVal) {
                    numMax++;
                } else if (array[i]>maxVal) {
                    maxInd = i;
                    maxVal = array[i];
                    numMax = 1;
                }
        }

        if (numMax>=1) {
            numMax = (int)(Math.random()*(double)numMax);
            for (int i = 0; numMax>=0; i++) {
                if (array[i] == maxVal) {
                    numMax--;
                    maxInd=i;
                }
            }
        }
        return(maxInd);

    }  // end   maxIndex(double[], boolean[])
    public static int maxIndex(int[] array) {
        return(maxIndex(array, new boolean[0]));
    }

    public static int sum(int[] array) {
        int sum = 0;
        for (int i = 0; i<array.length; i++)
            sum+=array[i];

        return(sum);
    }

    public static int tryPolicy(Env environ, Policy pol, boolean show) {
        // note: does not restart environment!
        int nSteps = 0;
        Action nextAction;

        if (show)
            environ.printWorld();

        while (!environ.atGoal()) {
            nSteps++;
            nextAction = pol.giveAction(environ.currentState());
            environ.transition(nextAction);
            if (show) {
                System.out.println(nextAction);
                environ.printWorld();
            }
            if ((nSteps%100) == 0)
                System.out.println(nSteps);
        }
        return (nSteps);
    }
}