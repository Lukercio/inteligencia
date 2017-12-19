package sgw;
import mdp.*;

/**
 * Created by ldlopes on 12/19/17.
 */

public class GridEnvironment extends Env {

    protected double successRate;
    protected int gridXSize;
    protected int gridYSize;
    protected int maxIndex;
    protected double rewardOnGoal;
    protected GridState startState;
    protected GridState goalState;
    protected GridState currentState;
    protected GridAction[] actions;
    protected MoveModifier mod;

    protected double grid[][]; // contains obstacles, walls etc... (basically a probability-modifier for enetering that field)
    protected double p[][][];  // contains transition probabilities [startIndex][action][targetIndex]
    protected double r[][];    // contains action-state rewards [startIndex][action]

    final protected int coord2Index(int x, int y) {
        return(x+gridXSize*y);
    }
    final protected int coord2Index(int[] coords) {
        return(coord2Index(coords[0], coords[1]));
    }

    final protected int[] index2Coord(int ind) {
        return( new int[] { ind % gridXSize, ind / gridXSize});
    }

    private boolean isValidState(GridState state) {
        int [] temp = state.getCoords();
        return ((temp[0]>=0) && (temp[0]<gridXSize) && (temp[1]>=0) && (temp[1]<gridYSize));
    }

    private GridState makeValidState(GridState state) {
        if (state.getX() < 0)
            state.setX(0);
        else if (state.getX()>=gridXSize)
            state.setX(gridXSize-1);

        if (state.getY() < 0)
            state.setY(0);
        else if (state.getY()>=gridYSize)
            state.setY(gridYSize-1);

        return (state);
    }

    private void genProbs() {
        GridState temp;
        int[] target;
        int[] finalSt;
        int[] altFinalSt;
        p = new double[maxIndex][actions.length][maxIndex];

        for (int start=0; start<maxIndex; start++) {
            // for each start state...
            for (int a=0; a<actions.length; a++) {
                // for each intended action

                for (int ap=0; ap<actions.length; ap++) {

                    temp = new GridState(index2Coord(start),this);
                    temp.applyAction(actions[ap]);

                    if (isValidState(temp))
                        target = temp.getCoords();
                    else
                        target = index2Coord(start);

                    temp = new GridState(target,this);
                    temp.applyAction(mod.getMod(new GridState(index2Coord(start),this)));
                    finalSt = makeValidState(temp).getCoords();

                    temp = new GridState(index2Coord(start),this);
                    temp.applyAction(mod.getMod(new GridState(index2Coord(start),this)));
                    altFinalSt = makeValidState(temp).getCoords();

                    if (a==ap) {
                        p[start][a][coord2Index(finalSt)]    += successRate*   grid[target[0]][target[1]] *   grid[finalSt[0]][finalSt[1]];
                        p[start][a][coord2Index(target)]     += successRate*   grid[target[0]][target[1]] *(1-grid[finalSt[0]][finalSt[1]]);
                        p[start][a][coord2Index(altFinalSt)] += successRate*(1-grid[target[0]][target[1]])*   grid[finalSt[0]][finalSt[1]];
                        p[start][a][start]                   += successRate*(1-grid[target[0]][target[1]])*(1-grid[finalSt[0]][finalSt[1]]);
                    }
                    else {
                        p[start][a][coord2Index(finalSt)]    += ((1-successRate)/(actions.length-1))*   grid[target[0]][target[1]] *   grid[finalSt[0]][finalSt[1]];
                        p[start][a][coord2Index(target)]     += ((1-successRate)/(actions.length-1))*   grid[target[0]][target[1]] *(1-grid[finalSt[0]][finalSt[1]]);
                        p[start][a][coord2Index(altFinalSt)] += ((1-successRate)/(actions.length-1))*(1-grid[target[0]][target[1]])*   grid[finalSt[0]][finalSt[1]];
                        p[start][a][start]                   += ((1-successRate)/(actions.length-1))*(1-grid[target[0]][target[1]])*(1-grid[finalSt[0]][finalSt[1]]);
                    }
                }
            }
        }
    }

    double[][] transcribeR(double[][][] r) {
        double[][] temp = new double[maxIndex][actions.length];

        for (int i=0; i<r.length; i++)
            for (int j=0; j<r[0].length; j++)
                for (int k=0; k<r[0][0].length; k++)
                    temp[coord2Index(i, j)][k] = r[i][j][k];

        return(temp);
    }

    public GridEnvironment(GridSettings set) {
        this((Settings)set);
    }

    public GridEnvironment(Settings rawSet) {
        super(rawSet);
        GridSettings set = (GridSettings) rawSet;
        this.gridXSize = set.gridXSize;
        this.gridYSize = set.gridYSize;

        this.maxIndex = gridXSize*gridYSize;

        this.successRate = set.successRate;
        this.rewardOnGoal = set.rewardOnGoal;
        this.startState = set.startState;
        this.startState.setParent(this);
        this.goalState  = set.goalState;
        this.goalState.setParent(this);
        this.mod = set.mod;
        this.actions = (GridAction[]) set.actions.clone();

        this.grid = (double[][])set.grid.clone();
        this.r = transcribeR(set.r);

        try {
            this.currentState = (GridState)startState.clone();
        } catch(CloneNotSupportedException e) {}

        genProbs();
    }

    public GridEnvironment(int gridXSize, int gridYSize, double successRate, double reward, int [] startStateCoord, int [] goalStateCoord, MoveModifier mod, GridAction[] actions) {
        this(new GridSettings(gridXSize, gridYSize, successRate, new double[][][] { { { reward } } }, GridSettings.defaultRewardOnGoal, new GridState(startStateCoord, null), new GridState(goalStateCoord, null), mod, actions, null));
    }
    public GridEnvironment(int gridXSize, int gridYSize, double successRate, double reward, int [] startStateCoord, int [] goalStateCoord, MoveModifier mod) {
        this(new GridSettings(gridXSize, gridYSize, successRate, new double[][][] { { {reward } } }, GridSettings.defaultRewardOnGoal, new GridState(startStateCoord, null), new GridState(goalStateCoord, null), mod, null, null));
    }
    public GridEnvironment(int gridXSize, int gridYSize) {
        this(new GridSettings(gridXSize, gridYSize));
    }
    public GridEnvironment() {
        this(new GridSettings());
    }

    public void restart() {
        try {
            currentState = (GridState)startState.clone();
        } catch (CloneNotSupportedException e) {}
    }

    public State currentState() {
        try {
            return ((State)currentState.clone());
        } catch (CloneNotSupportedException cnse) { return(null); }
    }

    public boolean atGoal() {
        return (goalState.same(currentState));
    }

    public double transition(Action rawAction) {
        GridAction action = (GridAction)rawAction;

        double fortuna = Math.random();
        double temp = 1;
        int tryState;
        int stateInd = currentState.getIndex();
        int actionInd = action.getIndex();

        for (tryState=0; temp>fortuna; tryState++) {
            temp-=p[stateInd][actionInd][tryState];
        }
        tryState--;

        currentState.setCoords(index2Coord(tryState));
        if (atGoal())
            return(rewardOnGoal);
        else
            return(r[stateInd][action.getIndex()]);
    }

    public Action[] availActions() {
        return ((GridAction[])actions.clone());
    }

    public int maxStates() {
        return(maxIndex);
    }

    public State genState(int index) {
        return (new GridState(index2Coord(index), this));
    }

    public boolean validState(State state) {
        int[] coords = ((GridState)state).getCoords();
        return(grid[coords[0]][coords[1]] != 0);
    }

    public MemberTester interpolate(State[] rawStates) {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int sumX = 0;
        int sumY = 0;
        double avgX;
        double avgY;
        double stddevX = 0;
        double stddevY = 0;

        GridState[] states = new GridState[rawStates.length];

        for (int i=0; i<rawStates.length; i++)
            states[i] = (GridState) rawStates[i];

        int[] curCoords;

        for (int curState = 0; curState < states.length; curState++) {
            curCoords = states[curState].getCoords();
            sumX+=curCoords[0];
            sumY+=curCoords[1];
        }

        avgX = sumX/(double)states.length;
        avgY = sumY/(double)states.length;

        for (int curState = 0; curState < states.length; curState++) {
            curCoords = states[curState].getCoords();

            stddevX+=(curCoords[0]-avgX)*(curCoords[0]-avgX);
            stddevY+=(curCoords[1]-avgY)*(curCoords[1]-avgY);
        }

        stddevX=Math.sqrt(stddevX/(states.length-1));
        stddevY=Math.sqrt(stddevY/(states.length-1));

        for (int curState = 0; curState < states.length; curState++) {
            curCoords = states[curState].getCoords();
            // get rid of outlyers (sp?) ...
            if ( (curCoords[0]<avgX+2*stddevX) && (curCoords[0]>avgX-2*stddevX) &&
                    (curCoords[1]<avgY+2*stddevY) && (curCoords[1]>avgY-2*stddevY)) {

                if (curCoords[0]<minX)
                    minX = curCoords[0];
                if (curCoords[0]>maxX)
                    maxX = curCoords[0];
                if (curCoords[1]<minY)
                    minY = curCoords[1];
                if (curCoords[1]>maxY)
                    maxY = curCoords[1];
            } else System.out.println("kicked: "+states[curState]);  // debug
        }

        return(new GridRectMemberTester(minX, maxX, minY, maxY));
    }

    public void printWorld() {
        String temp;
        for (int i=0; i<gridXSize+2; i++)
            System.out.print("#___ ");
        System.out.println();

        for (int i=0; i<gridYSize; i++) {
            System.out.print("#___ ");
            for (int j=0; j<gridXSize; j++) {
                if (grid[j][i] < 0.2)
                    temp = ("#_");
                else if (grid[j][i] < 0.5)
                    temp = (":_");
                else if (grid[j][i] < 1)
                    temp = ("._");
                else
                    temp = ("__");
                if ((startState.getX() == j) && (startState.getY() == i))
                    temp += ("S");
                else if ((goalState.getX() == j) && (goalState.getY() == i))
                    temp += ("G");
                else
                    temp += ("_");
                if ((currentState.getX() == j) && (currentState.getY() == i))
                    temp += ("C ");
                else
                    temp += ("_ ");
                System.out.print(temp);
            }


            System.out.println("#___ ");
        }
        for (int i=0; i<gridXSize+2; i++)
            System.out.print("#___ ");
        System.out.println();
    }

    protected void printProbs(GridState state, GridAction action) {
        String temp = "";
        double curProb = 0;

        for (int i=0; i<gridXSize+2; i++)
            System.out.print("#    ");
        System.out.println();

        for (int i=0; i<gridYSize; i++) {
            System.out.print("#    ");
            for (int j=0; j<gridXSize; j++) {
                curProb = p[coord2Index(state.getCoords())][action.getIndex()][coord2Index(j,i)];
                if (curProb>0)
                    temp = Double.toString(curProb);
                else {
                    if (grid[j][i] < 0.2)
                        temp = ("# ");
                    else if (grid[j][i] < 0.5)
                        temp = (": ");
                    else if (grid[j][i] < 1)
                        temp = (". ");
                    else
                        temp = ("  ");
                    if ((startState.getX() == j) && (startState.getY() == i))
                        temp += ("S");
                    else if ((goalState.getX() == j) && (goalState.getY() == i))
                        temp += ("G");
                    else
                        temp += (" ");
                    if ((currentState.getX() == j) && (currentState.getY() == i))
                        temp += ("C");
                    else
                        temp += (" ");
                }

                while (temp.length()<4)
                    temp = temp+" ";
                System.out.print(temp.substring(0,4)+" ");
            }
            System.out.println("#    ");
        }
        for (int i=0; i<gridXSize+2; i++)
            System.out.print("#    ");
        System.out.println();
    }

    public void printProbs(Action action) {
        printProbs(currentState, (GridAction)action);
    }

    public static void main(String[] args) {
        int testInt=0;
        GridEnvironment test = new GridEnvironment();
        GridAction[] actions = (GridAction[])test.availActions();
        GridState testState = new GridState(4,6,test);

        test.printWorld();
        test.printProbs(testState, actions[0]);
    }
}