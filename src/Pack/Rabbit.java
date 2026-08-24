package Pack;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Rabbit extends Animal implements Prey {

    private int lastTargetLocationX = Integer.MIN_VALUE;
    private int lastTargetLocationY = Integer.MIN_VALUE;

    private boolean hasReached;
    private boolean hasTarget;

    private double Distance;

    private static final Image avatar = new Image("Pack/photos/rabbit.png");

    private static final int MAP_SIZE = 15;

    public Rabbit() {
        super("Rabbit", 0, 20, -1, -1, 25, 10, 10, 10, 10, 30, 5, 40, false, true, 3, 15, false);
    }

    public Rabbit(double age, int xPosition, int yPosition) {
        super("Rabbit", age, 20, xPosition, yPosition, 25, 10, 10, 10, 10, 30, 5, 40, false, true, 3, 15, false);

    }

    public Rabbit(double age, int maxLifeSpan, int xPosition, int yPosition, double idealTemperature, double temperatureTolerance, double toxinResistance,
                  double reproductionCapacity, double mutationProbability, double currentEnergy, double dailyEnergyConsumption, double nutritionalValue, boolean isInfected, boolean isAlive,
                  int speed, int visionRange, boolean isPredator) {

        super("Rabbit", age, maxLifeSpan, xPosition, yPosition, idealTemperature, temperatureTolerance, toxinResistance, reproductionCapacity, mutationProbability, currentEnergy, dailyEnergyConsumption, nutritionalValue, false, true, speed, visionRange, isPredator);

    }

    public Image getAvatar() {
        return avatar;
    }


    public void scanSurroundings(ArrayList<Plant> plantArray, ArrayList<Fox> animal) {

        int closestAnimalIndex = Integer.MIN_VALUE;
        double minDistanceAnimal = Double.MAX_VALUE;

        boolean safe = true;

        for (int i = 0; i < animal.size(); i++) {

            if (animal.get(i).getIsPredator() != true) {
                continue;
            }

            double xGap = animal.get(i).getxPosition() - getxPosition();
            double yGap = animal.get(i).getyPosition() - getyPosition();

            double distanceToTarget = (Math.sqrt(Math.pow(xGap, 2) + Math.pow(yGap, 2)));

            if (distanceToTarget <= getVisionRange() && minDistanceAnimal > distanceToTarget) {

                closestAnimalIndex = i;
                minDistanceAnimal = distanceToTarget;

                super.predatorX = animal.get(closestAnimalIndex).getxPosition();
                super.predatorY = animal.get(closestAnimalIndex).getyPosition();

            }


        }

        if (getIsTheTarget()) {
            run();
            safe = false;

        } else if (!getIsTheTarget()) {
            safe = true;

        }

        setIsTheTarget(false);

        if (safe) {
            int tempLocationX = getxPosition();
            int tempLocationY = getyPosition();

            double minDistance = Double.MAX_VALUE;
            int targetLocationX = Integer.MIN_VALUE;
            int targetLocationY = Integer.MIN_VALUE;
            boolean hasFindATarget = false;
            int closestPlantIndex = Integer.MIN_VALUE;


            for (int i = 0; i < plantArray.size(); i++) {

                if (plantArray.get(i).getIsAlive() == false) {
                    continue;
                }

                double xGap = plantArray.get(i).getxPosition() - getxPosition();

                double yGap = plantArray.get(i).getyPosition() - getyPosition();

                this.Distance = Math.sqrt(Math.pow(xGap, 2) + Math.pow(yGap, 2));

                if (Distance <= getVisionRange() && Distance < minDistance) {

                    closestPlantIndex = i;

                    minDistance = Distance;

                    targetLocationX = plantArray.get(closestPlantIndex).getxPosition();
                    targetLocationY = plantArray.get(closestPlantIndex).getyPosition();

                    hasFindATarget = true;

                }

            }

            if (hasFindATarget) {

                if (targetLocationX != lastTargetLocationX || targetLocationY != lastTargetLocationY) {
                    lastTargetLocationX = targetLocationX;
                    lastTargetLocationY = targetLocationY;
                }


                if (targetLocationX > getxPosition()) {
                    super.xPosition = getxPosition() + 1;
                } else if (targetLocationX < getxPosition()) {
                    super.xPosition = getxPosition() - 1;
                }

                if (targetLocationY > getyPosition()) {
                    super.yPosition = getyPosition() + 1;
                } else if (targetLocationY < getyPosition()) {
                    super.yPosition = getyPosition() - 1;
                }

            }

            if (hasFindATarget && plantArray.get(closestPlantIndex).getxPosition() == this.getxPosition() && plantArray.get(closestPlantIndex).getyPosition() == this.getyPosition()) {

                hasReached = true;

                this.feed(plantArray.get(closestPlantIndex));


            }

        }

    }

    public void run() {

        boolean wasStuck = false;

        if (getIsTheTarget()) {

            int count0 = 0;
            int stuckCount = 0;

            while (count0 < getSpeed()) {

                if (count0 < getSpeed()) {

                    if (super.predatorX - getxPosition() <= 0 && (getxPosition() + 1) < MAP_SIZE) {
                        setxPosition(getxPosition() + 1);
                        count0++;
                    } else if (super.predatorX - getxPosition() > 0 && (getxPosition() - 1) < 0) {
                        setxPosition(getxPosition() - 1);
                        count0++;
                    } else {
                        stuckCount++;
                    }
                }

                if (count0 < getSpeed()) {
                    if (super.predatorY - getyPosition() < 0 && (getyPosition() + 1) < MAP_SIZE) {
                        setyPosition(getyPosition() + 1);
                        count0++;
                    } else if (super.predatorY - getyPosition() >= 0 && (getyPosition() - 1) < 0) {
                        setyPosition(getyPosition() - 1);
                        count0++;
                    } else {
                        stuckCount++;
                    }
                }

                if (stuckCount >= 9) {
                    wasStuck = true;
                    break;
                }

            }

            int count = 0;

            while (count <= 3 && wasStuck) {

                ArrayList<Integer> validMoves = new ArrayList<Integer>();

                if (getxPosition() + 1 < MAP_SIZE) validMoves.add(1);
                if (getxPosition() - 1 > 0) validMoves.add(2);
                if (getyPosition() + 1 < MAP_SIZE) validMoves.add(3);
                if (getyPosition() - 1 > 0) validMoves.add(4);

                if (!validMoves.isEmpty()) {

                    int ran = (int) (Math.random() * validMoves.size());

                    if (validMoves.get(ran) == 1) {
                        setxPosition(getxPosition() + 1);
                        count++;
                    } else if (validMoves.get(ran) == 2) {
                        setxPosition(getxPosition() - 1);
                        count++;
                    } else if (validMoves.get(ran) == 3) {
                        setyPosition(getyPosition() + 1);
                        count++;
                    } else if (validMoves.get(ran) == 4) {
                        setyPosition(getyPosition() - 1);
                        count++;
                    }

                } else {
                    break;
                }

            }

        }

    }

    public void feed() {

        super.processMetabolism();

    }

    public void feed(Plant plant) {

        if (plant.getIsAlive()) {

            if (plant.nutritionalValue == 0) {

                plant.decompose();

            } else if (plant.getNutritionalValue() <= 20) {

                setCurrentEnergy(plant.getNutritionalValue() + getCurrentEnergy());
                plant.decompose();

            } else {

                setCurrentEnergy(getCurrentEnergy() + 20);
                plant.setNutritionalValue(plant.getNutritionalValue() - 20);

            }

        }

    }

    private static int IDCounter = 100;

    public void generateId() {

        setId("Rabbit" + IDCounter);
        IDCounter++;

    }

}
