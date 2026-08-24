package Pack;

import java.util.ArrayList;

public interface Predator {

    public void hunt(Animal animal);

    public void scanSurroundings(ArrayList<Animal> preyList);

}
