package Pack;

import javafx.scene.image.Image;

public class Plant extends Organism {

    private double photosynthesisEfficiency;
    private double waterRequirement;
    private double mineralConsumption;
    private int seedDispersionRange;
    private boolean isPollinated;

    private int currentNectarPlant;
    private int dailyNectarProduction;
    private int maxNectar;

    int seedX = Integer.MIN_VALUE;
    int seedY = Integer.MIN_VALUE;

    private int reproductionCooldown = 0;

    private static final Image avatar = new Image("Pack/photos/plant.png");

    public Plant() {

        super("Plant", 0, 80, 0, 0, 25, 60, 30, 10, 10, 200, 2, 50, false, true);

        generateId();

        this.photosynthesisEfficiency = 35;
        this.waterRequirement = 4;
        this.mineralConsumption = 10;
        this.seedDispersionRange = 5;
        this.isPollinated = false;

        this.currentNectarPlant = 100;
        this.dailyNectarProduction = 5;
        this.maxNectar = 150;

    }

    public Plant(String speciesName, double age, int xPosition, int yPosition) {

        super(speciesName, age, 80, xPosition, yPosition, 25, 60, 30, 10, 10, 200, 2, 50, false, true);

        generateId();

        this.photosynthesisEfficiency = 35;
        this.waterRequirement = 4;
        this.mineralConsumption = 10;
        this.seedDispersionRange = 5;
        this.isPollinated = false;

        this.currentNectarPlant = 100;
        this.dailyNectarProduction = 5;
        this.maxNectar = 150;


    }

    public Plant(String speciesName, double age, int maxLifeSpan, int xPosition, int yPosition, double idealTemperature, double temperatureTolerance, double toxinResistance,
                 double reproductionCapacity, double mutationProbability, double currentEnergy, double dailyEnergyConsumption, double nutritionalValue, boolean isInfected, boolean isAlive,
                 double photosynthesisEfficiency, double waterRequirement, double mineralConsumption, int seedDispersionRange, boolean isPollinated,
                 int currentNectarPlant, int dailyNectarProduction, int maxNectar) {

        super(speciesName, age, maxLifeSpan, xPosition, yPosition, idealTemperature, temperatureTolerance, toxinResistance, reproductionCapacity, mutationProbability, currentEnergy, dailyEnergyConsumption, nutritionalValue, isInfected, isAlive);

        generateId();

        this.photosynthesisEfficiency = photosynthesisEfficiency;
        this.waterRequirement = waterRequirement;
        this.mineralConsumption = mineralConsumption;
        this.seedDispersionRange = seedDispersionRange;
        this.isPollinated = isPollinated;

        this.currentNectarPlant = currentNectarPlant;
        this.dailyNectarProduction = dailyNectarProduction;
        this.maxNectar = maxNectar;


    }

    public Image getAvatar() {
        return avatar;
    }


    public double getPhotosynthesisEfficiency() {
        return photosynthesisEfficiency;
    }

    public void setPhotosynthesisEfficiency(double newPhotosynthesisEfficiency) {
        this.photosynthesisEfficiency = newPhotosynthesisEfficiency;
    }

    public double getWaterRequirement() {
        return waterRequirement;
    }

    public void setWaterRequirement(double newWaterRequirement) {
        this.waterRequirement = newWaterRequirement;
    }

    public double getMineralConsumption() {
        return mineralConsumption;
    }

    public void setMineralConsumption(double newMineralConsumption) {
        this.mineralConsumption = newMineralConsumption;
    }

    public int getSeedDispersionRange() {
        return seedDispersionRange;
    }

    public void setSeedDispersionRange(int newSeedDispersionRange) {
        this.seedDispersionRange = newSeedDispersionRange;
    }

    public boolean getIsPollinated() {
        return isPollinated;
    }

    public void setIsPollinated(boolean newIsPollinated) {
        this.isPollinated = newIsPollinated;
    }

    public int getCurrentNectarPlant() {
        return currentNectarPlant;
    }

    public void setCurrentNectarPlant(int newCurrentNectarPlant) {
        this.currentNectarPlant = newCurrentNectarPlant;
    }

    public int getDailyNectarProduction() {
        return dailyNectarProduction;
    }

    public void setDailyNectarProduction(int newDailyNectarProduction) {
        this.dailyNectarProduction = newDailyNectarProduction;
    }

    public int getMaxNectar() {
        return maxNectar;
    }

    public void setMaxNectar(int newMaxNectar) {
        this.maxNectar = newMaxNectar;
    }

    public void feed() {

        super.currentEnergy += (100 + photosynthesisEfficiency) / 100;
        super.currentEnergy -= waterRequirement;

        if (reproductionCooldown > 0) {
            reproductionCooldown--;
        }

        if (currentNectarPlant < maxNectar) {
            currentNectarPlant += dailyNectarProduction;

            if (currentNectarPlant > maxNectar) {
                currentNectarPlant = maxNectar;
            }

        }


        if (super.isInfected) {
            super.dailyEnergyConsumption *= 1.5;
            this.photosynthesisEfficiency /= 2;
            this.waterRequirement *= 1.5;
            this.mineralConsumption *= 1.5;
        }

        super.processMetabolism();

    }

    public void reactToEnvironment(double currentTemperature, double environmentalToxin) {

        if (currentTemperature > (super.idealTemperature * ((100 + temperatureTolerance) / 100))) {

            super.currentEnergy *= 0.5;
            this.waterRequirement *= 2;
            super.dailyEnergyConsumption *= 1.5;

        }

        if (environmentalToxin > super.toxinResistance) {

            super.isInfected = true;
            this.mineralConsumption *= 2;
        }

    }

    public Organism reproduce() {

        if (isPollinated && currentEnergy >= 150 && reproductionCooldown == 0) {

            this.scatterSeeds();
            super.currentEnergy -= 50;
            this.isPollinated = false;
            this.reproductionCooldown = 12;

            Plant newPlant = new Plant(super.speciesName, 0, seedX, seedY);


            return newPlant;

        }

        return null;

    }

    private void scatterSeeds() {

        seedX = getxPosition() + (int) (Math.random() * 3);
        seedY = getyPosition() + (int) (Math.random() * 3);
    }

    private static int IDCounter = 100;

    public void generateId() {

        setId("Plant" + IDCounter);
        IDCounter++;

    }


}
