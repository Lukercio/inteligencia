package sgw;

/**
 * Created by ldlopes on 12/19/17.
 */

public class MoveModifier {
    protected int[] stateCoord2ActionCoord(int[] stateCoord) {

        return(new int[] {0, 0});
    }

    GridAction getMod(GridState state) {
        return (new GridAction(stateCoord2ActionCoord(state.getCoords())));
    }
}