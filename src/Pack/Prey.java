package Pack;

import java.util.ArrayList;

public interface Prey {

    public void run();

    public void scanSurroundings(ArrayList<Plant> plantArray, ArrayList<Fox> animal);

}
