package Pack;

import javafx.scene.image.Image;

public abstract class Animal extends Organism {

    private double speed;
    private int visionRange;
    protected boolean isPredator;

    protected boolean isTheTarget;

    protected int predatorX;
    protected int predatorY;

    public Animal() {

        super("Animal", 0, 20, 0, 0, 25, 10, 10, 10, 10, 30, 5, 5, false, true);

        this.speed = 3;
        this.visionRange = 15;
        this.isPredator = false;

    }

    public Animal(String speciesName, double age, int xPosition, int yPosition) {

        super(speciesName, age, 20, xPosition, yPosition, 25, 10, 10, 10, 10, 30, 5, 5, false, true);


        generateId();

        this.speed = 3;
        this.visionRange = 15;
        this.isPredator = false;

    }

    public Animal(String speciesName, double age, int maxLifeSpan, int xPosition, int yPosition, double idealTemperature, double temperatureTolerance, double toxinResistance,
                  double reproductionCapacity, double mutationProbability, double currentEnergy, double dailyEnergyConsumption, double nutritionalValue, boolean isInfected, boolean isAlive,
                  int speed, int visionRange, boolean isPredator) {

        super(speciesName, age, maxLifeSpan, xPosition, yPosition, idealTemperature, temperatureTolerance, toxinResistance, reproductionCapacity, mutationProbability, currentEnergy, dailyEnergyConsumption, nutritionalValue, isInfected, isAlive);

        generateId();

        this.speed = speed;
        this.visionRange = visionRange;
        this.isPredator = isPredator;

    }

    public abstract Image getAvatar();

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getVisionRange() {
        return visionRange;
    }

    public void setVisionRange(int visionRange) {
        this.visionRange = visionRange;
    }

    public boolean getIsPredator() {
        return isPredator;
    }

    public void setIsPredator(boolean isPredator) {
        this.isPredator = isPredator;
    }

    public boolean getIsTheTarget() {
        return isTheTarget;
    }

    public void setIsTheTarget(boolean isTheTarget) {
        this.isTheTarget = isTheTarget;
    }

    public void feed() {

        super.processMetabolism();

    }

    public void reactToEnvironment(double currentTemperature, double environmentalToxin) {

        if (currentTemperature > (super.idealTemperature * ((100 + temperatureTolerance) / 100))) {

            super.dailyEnergyConsumption *= 1.2;
            this.speed *= 0.80;

        }

        if (environmentalToxin > super.toxinResistance) {

            super.dailyEnergyConsumption *= 1.2;
            super.isInfected = true;

        }

    }

    public Organism reproduce() {
        return null;
    }

    private static int IDCounter = 100;

    public void generateId() {

        setId("Animal" + IDCounter);
        IDCounter++;

    }


}
