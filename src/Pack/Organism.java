package Pack;

import javafx.scene.image.Image;

public abstract class Organism {

    protected String id;
    protected String speciesName;
    protected double age;
    protected int maxLifeSpan;
    protected int xPosition;
    protected int yPosition;
    protected double idealTemperature;
    protected double temperatureTolerance;
    protected double toxinResistance;
    protected double reproductionCapacity;
    protected double mutationProbability;
    protected double currentEnergy;
    protected double dailyEnergyConsumption;
    protected double nutritionalValue;
    protected boolean isInfected;
    protected boolean isAlive;

    public Organism() {

        this("Organism", 0, 0, 0, 0, 25, 10, 10, 10, 10, 75, 5, 50, false, true);

    }

    public Organism(String speciesName, double age, int xPosition, int yPosition) {

        this(speciesName, age, -1, xPosition, yPosition, 25, 10, 10, 10, 10, 75, 5, 50, false, true);

    }

    public Organism(String speciesName, double age, int maxLifeSpan, int xPosition, int yPosition, double idealTemperature, double temperatureTolerance, double toxinResistance,
                    double reproductionCapacity, double mutationProbability, double currentEnergy, double dailyEnergyConsumption, double nutritionalValue, boolean isInfected, boolean isAlive) {

        this.speciesName = speciesName;
        this.age = age;
        this.maxLifeSpan = maxLifeSpan;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.idealTemperature = idealTemperature;
        this.temperatureTolerance = temperatureTolerance;
        this.toxinResistance = toxinResistance;
        this.reproductionCapacity = reproductionCapacity;
        this.mutationProbability = mutationProbability;
        this.currentEnergy = currentEnergy;
        this.dailyEnergyConsumption = dailyEnergyConsumption;
        this.nutritionalValue = nutritionalValue;
        this.isInfected = isInfected;
        this.isAlive = isAlive;

    }

    public String getId() {
        return id;
    }

    public void setId(String newId) {
        this.id = newId;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String newSpeciesName) {
        this.speciesName = newSpeciesName;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double newAge) {
        this.age = newAge;
    }

    public int getMaxLifeSpan() {
        return maxLifeSpan;
    }

    public void setMaxLifeSpan(int newMaxLifeSpan) {
        this.maxLifeSpan = newMaxLifeSpan;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int newxPosition) {
        this.xPosition = newxPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int newyPosition) {
        this.yPosition = newyPosition;
    }

    public double getIdealTemperature() {
        return idealTemperature;
    }

    public void setIdealTemperature(double newIdealTemperature) {
        this.idealTemperature = newIdealTemperature;
    }

    public double getTemperatureTolerance() {
        return temperatureTolerance;
    }

    public void setTemperatureTolerance(double newTemperatureTolerance) {
        this.temperatureTolerance = newTemperatureTolerance;
    }

    public double getToxinResistance() {
        return toxinResistance;
    }

    public void setToxinResistance(double newToxinResistance) {
        this.toxinResistance = newToxinResistance;
    }

    public double getReproductionCapacity() {
        return reproductionCapacity;
    }

    public void setReproductionCapacity(double newReproductionCapacity) {
        this.reproductionCapacity = newReproductionCapacity;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public void setMutationProbability(double newMotationProbility) {
        this.mutationProbability = newMotationProbility;
    }

    public double getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(double newCurrentEnergy) {
        this.currentEnergy = newCurrentEnergy;
    }

    public double getDailyEnergyConsumption() {
        return dailyEnergyConsumption;
    }

    public void setDailyEnergyConsumption(double newDailyEnergyConsumption) {
        this.dailyEnergyConsumption = newDailyEnergyConsumption;
    }

    public double getNutritionalValue() {
        return nutritionalValue;
    }

    public void setNutritionalValue(double newNutritionalValue) {
        this.nutritionalValue = newNutritionalValue;
    }

    public boolean getIsInfected() {
        return isInfected;
    }

    public void setIsInfected(boolean newIsInfected) {
        this.isInfected = newIsInfected;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean newIsAlive) {
        this.isAlive = newIsAlive;
    }

    public void handleStarvation() {
        dailyEnergyConsumption -= 0.5;

        if (dailyEnergyConsumption < 0) {
            dailyEnergyConsumption = 0;
        }
    }


    public void processMetabolism() {

        double tempEnergy = currentEnergy;

        if (isInfected) {
            currentEnergy -= (1.2 * dailyEnergyConsumption);
        } else {
            currentEnergy -= dailyEnergyConsumption;
        }

        if (currentEnergy <= tempEnergy * 0.20) {
            handleStarvation();
        }

        die();

        age += 0.1;

    }

    public void generateMutations() {

        if ((int) (Math.random() * 100) <= 5) {

            toxinResistance *= 1.10;
            idealTemperature *= 0.90;

        }

    }

    public void die() {
        if (isAlive && (currentEnergy <= 0 || age >= maxLifeSpan)) {
            decompose();
        }
    }

    public void decompose() {

        if (isInfected) {
            nutritionalValue = nutritionalValue * 0.80;
        }
        if (age <= maxLifeSpan * 0.20) {
            nutritionalValue = nutritionalValue * 0.30;
        }

        isAlive = false;

    }

    public abstract Image getAvatar();

    public abstract void feed();

    public abstract void reactToEnvironment(double currentTemperature, double environmentalToxin);

    // Reproduction is intentionally disabled — population grows unbounded and breaks grid rendering at current tuning.
    // See README.md.

    public abstract Organism reproduce();

    public abstract void generateId();

}
