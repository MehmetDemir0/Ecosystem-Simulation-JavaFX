package Pack;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Fox extends Animal implements Predator {

    private int lastTargetLocationX = Integer.MIN_VALUE;
    private int lastTargetLocationY = Integer.MIN_VALUE;

    private boolean hasReached;
    private boolean hasTarget;

    private static final Image avatar = new Image("Pack/photos/fox.png");

    public Fox() {
        super("Fox", 0, 20, -1, -1, 25, 10, 10, 10, 10, 30, 5, 60, false, true, 4, 25, true);
    }

    public Fox(double age, int xPosition, int yPosition) {
        super("Fox", age, 20, xPosition, yPosition, 25, 10, 10, 10, 10, 30, 5, 60, false, true, 4, 25, true);

    }

    public Fox(double age, int maxLifeSpan, int xPosition, int yPosition, double idealTemperature, double temperatureTolerance, double toxinResistance,
               double reproductionCapacity, double mutationProbability, double currentEnergy, double dailyEnergyConsumption, double nutritionalValue, boolean isInfected, boolean isAlive,
               int speed, int visionRange, boolean isPredator) {
        super("Fox", age, maxLifeSpan, xPosition, yPosition, idealTemperature, temperatureTolerance, toxinResistance, reproductionCapacity, mutationProbability, currentEnergy, dailyEnergyConsumption, nutritionalValue, false, true, speed, visionRange, true);

    }

    public Image getAvatar() {
        return avatar;
    }

    public void scanSurroundings(ArrayList<Animal> preyArrayList) {

        for (Animal a : preyArrayList) {
            a.setIsTheTarget(false);
        }

        int tempLocationX = getxPosition();
        int tempLocaitonY = getyPosition();

        int targetLocationX = Integer.MIN_VALUE;
        int targetLocationY = Integer.MIN_VALUE;

        double minDistance = Double.MAX_VALUE;
        int closestIndex = Integer.MIN_VALUE;

        hasTarget = false;

        for (int i = 0; i < preyArrayList.size(); i++) {

            if (!preyArrayList.get(i).getIsAlive() || preyArrayList.get(i).isPredator) {
                continue;
            }

            if (hasTarget && getxPosition() == preyArrayList.get(closestIndex).getxPosition() && getyPosition() == preyArrayList.get(closestIndex).getyPosition()) {

                closestIndex = i;

                hasReached = true;

                hunt(preyArrayList.get(closestIndex));

                lastTargetLocationX = targetLocationX;
                lastTargetLocationY = targetLocationY;

                break;
            }

            double xGap = preyArrayList.get(i).getxPosition() - getxPosition();
            double yGap = preyArrayList.get(i).getyPosition() - getyPosition();

            double distanceToTarget = (Math.sqrt(Math.pow(xGap, 2) + Math.pow(yGap, 2)));

            if (distanceToTarget <= getVisionRange() && distanceToTarget < minDistance) {

                closestIndex = i;

                minDistance = distanceToTarget;

                targetLocationX = preyArrayList.get(i).getxPosition();
                targetLocationY = preyArrayList.get(i).getyPosition();

                super.predatorX = getxPosition();
                super.predatorY = getyPosition();

                hasTarget = true;

            }

        }

        if (hasTarget) {
            preyArrayList.get(closestIndex).setIsTheTarget(true);
        }

        int count0 = 0;

        if (hasTarget && !hasReached) {

            while (count0 < getSpeed()) {

                if (targetLocationX > getxPosition()) {
                    super.xPosition = getxPosition() + 1;
                    count0++;
                } else if (targetLocationX < getxPosition()) {
                    super.xPosition = getxPosition() - 1;
                    count0++;
                } else {
                    break;
                }

            }

            while (count0 < getSpeed()) {

                if (targetLocationY > getyPosition()) {
                    super.yPosition = getyPosition() + 1;
                    count0++;
                } else if (targetLocationY < getyPosition()) {
                    super.yPosition = getyPosition() - 1;
                    count0++;
                } else {
                    break;
                }

            }


        }

        if (hasTarget && getxPosition() == preyArrayList.get(closestIndex).getxPosition() && getyPosition() == preyArrayList.get(closestIndex).getyPosition()) {

            if (tempLocationX != getxPosition() || tempLocaitonY != getyPosition()) {

                preyArrayList.get(closestIndex).setIsAlive(false);
                hunt(preyArrayList.get(closestIndex));

            }

            hasTarget = false;
            hasReached = false;

        }

    }

    public Organism reproduce() {

        if (currentEnergy >= 80) {

            currentEnergy -= 50;

            Fox newFox = new Fox(0, getxPosition(), getyPosition());

            return newFox;

        } else {
            return null;
        }

    }

    public void feed(double nutrition) {

        this.setCurrentEnergy(getCurrentEnergy() + nutrition);

    }

    public void hunt(Animal animal) {

        animal.decompose();
        this.feed(animal.getNutritionalValue());

    }

    private static int IDCounter = 100;

    public void generateId() {

        setId("Fox" + IDCounter);
        IDCounter++;

    }


}
