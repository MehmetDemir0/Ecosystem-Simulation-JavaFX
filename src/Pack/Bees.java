package Pack;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Bees extends Organism {

    private String hiveId;
    private double movementRange;
    private double nectarCapacity;
    private double currentNectar;
    private boolean isFlying;

    private int lastTargetLocationX = Integer.MIN_VALUE;
    private int lastTargetLocationY = Integer.MIN_VALUE;
    boolean hasReachedTheTarget = false;

    private int reproductionCooldown = 0;

    private static final Image avatar = new Image("Pack/photos/bee.png");

    public Bees() {

        super("Bee", 0, 60, 0, 0, 25, 40, 10, 10, 10, 100, 3, 5, false, true);

        generateId();

        this.hiveId = "Hive";
        this.movementRange = 10;
        this.nectarCapacity = 50;
        this.currentNectar = 0;
        this.isFlying = true;


    }

    public Bees(double age, int xPosition, int yPosition) {

        super("Bee", age, 60, xPosition, yPosition, 25, 40, 10, 10, 10, 100, 3, 5, false, true);

        generateId();

        this.hiveId = "Hive";
        this.movementRange = 10;
        this.nectarCapacity = 50;
        this.currentNectar = 5;
        this.isFlying = true;

    }

    public Bees(String speciesName, double age, int maxLifeSpan, int xPosition, int yPosition, double idealTemperature, double temperatureTolerance, double toxinResistance,
                double reproductionCapacity, double mutationProbability, double currentEnergy, double dailyEnergyConsumption, double nutritionalValue, boolean isInfected, boolean isAlive,
                String hiveId, double movementRange, double nectarCapacity, int currentNectar, boolean isFlying) {

        super(speciesName, age, maxLifeSpan, xPosition, yPosition, idealTemperature, temperatureTolerance, toxinResistance, reproductionCapacity, mutationProbability, currentEnergy, dailyEnergyConsumption, nutritionalValue, isInfected, isAlive);

        generateId();

        this.hiveId = hiveId;
        this.movementRange = movementRange;
        this.nectarCapacity = nectarCapacity;
        this.currentNectar = currentNectar;
        this.isFlying = isFlying;

    }

    public Image getAvatar() {
        return avatar;
    }

    public String getHiveId() {
        return hiveId;
    }

    public void setHiveId(String newHiveId) {
        this.hiveId = newHiveId;
    }

    public double getMovementRange() {
        return movementRange;
    }

    public void setMovementRange(double newMovementRange) {
        this.movementRange = newMovementRange;
    }

    public double getNectarCapacity() {
        return nectarCapacity;
    }

    public void setNectarCapacity(double newNectarCapacity) {
        this.nectarCapacity = newNectarCapacity;
    }

    public double getCurrentNectar() {
        return currentNectar;
    }

    public void setCurrentNectar(double newCurrentNectar) {
        this.currentNectar = newCurrentNectar;
    }

    public boolean getIsFlying() {
        return isFlying;
    }

    public void setIsFlying(boolean newIsFlying) {
        this.isFlying = newIsFlying;
    }

    public void scanSurroundings(int radius, ArrayList<Plant> plantArray) {

        int tempLocationX = getxPosition();
        int tempLocationY = getyPosition();

        double minDistance = Double.MAX_VALUE;
        int targetLocationX = Integer.MIN_VALUE;
        int targetLocationY = Integer.MIN_VALUE;
        boolean hasFindToTarget = false;
        int closestPlantIndex = Integer.MIN_VALUE;


        for (int i = 0; i < plantArray.size(); i++) {

            if (plantArray.get(i).getIsAlive() == false) {
                continue;
            }

            double xGap = plantArray.get(i).getxPosition() - getxPosition();

            double yGap = plantArray.get(i).getyPosition() - getyPosition();

            double Distance = Math.sqrt(Math.pow(xGap, 2) + Math.pow(yGap, 2));

            if (Distance <= radius && Distance < minDistance) {

                closestPlantIndex = i;

                minDistance = Distance;

                targetLocationX = plantArray.get(closestPlantIndex).getxPosition();
                targetLocationY = plantArray.get(closestPlantIndex).getyPosition();

                hasFindToTarget = true;

            }

        }

        if (hasFindToTarget) {

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

        if (hasFindToTarget && plantArray.get(closestPlantIndex).getxPosition() == this.getxPosition() && plantArray.get(closestPlantIndex).getyPosition() == this.getyPosition()) {

            hasReachedTheTarget = true;
            plantArray.get(closestPlantIndex).setIsPollinated(true);

            if (plantArray.get(closestPlantIndex).getCurrentNectarPlant() < 15) {

                this.currentNectar += plantArray.get(closestPlantIndex).getCurrentNectarPlant();
                plantArray.get(closestPlantIndex).setCurrentNectarPlant(0);

            } else {
                plantArray.get(closestPlantIndex).setCurrentNectarPlant(plantArray.get(closestPlantIndex).getCurrentNectarPlant() - 15);
                this.currentNectar += 15;
            }

            if (plantArray.get(closestPlantIndex).getCurrentNectarPlant() < 15) {
                plantArray.get(closestPlantIndex).setIsAlive(false);
            }

            if (currentNectar > nectarCapacity) {
                this.currentNectar = this.nectarCapacity;
            }

        } else {
            hasReachedTheTarget = false;
        }


    }

    public void feed() {

        if (reproductionCooldown > 0) {
            reproductionCooldown--;
        }

        if ((this.currentNectar) < 8) {

            this.currentEnergy += currentNectar;
            this.currentNectar = 0;

        } else {

            this.currentNectar -= 8;
            this.currentEnergy += 8;

        }

        super.processMetabolism();

    }

    public void reactToEnvironment(double currentTemperature, double environmentalToxin) {

        if (currentTemperature > (super.idealTemperature * ((100 + temperatureTolerance) / 100))) {

            this.isFlying = false;
            super.dailyEnergyConsumption *= 1.2;

        }

        if (environmentalToxin > super.toxinResistance) {

            super.isInfected = true;
            super.dailyEnergyConsumption *= 1.2;

        }

    }

    public Organism reproduce() {

        if (currentEnergy >= 80 && reproductionCooldown == 0) {

            currentEnergy -= 50;
            reproductionCooldown = 12;

            int positionX = getxPosition() + (int) (Math.random() * 3) - 1;
            int positionY = getyPosition() + (int) (Math.random() * 3) - 1;

            Bees newBee = new Bees(0, positionX, positionY);
            newBee.setHiveId(this.hiveId);
            newBee.setCurrentNectar(0);
            newBee.setNectarCapacity(this.nectarCapacity);
            newBee.setMovementRange(10);

            return newBee;

        }

        return null;

    }

    private static int IDCounter = 100;

    public void generateId() {

        setId("Bee" + IDCounter);
        IDCounter++;

    }


}
