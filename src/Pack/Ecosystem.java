package Pack;

import java.util.ArrayList;

public class Ecosystem {

    protected ArrayList<Plant> plantArrayList = new ArrayList<>();
    protected ArrayList<Bees> beesArrayList = new ArrayList<>();
    protected ArrayList<Fox> foxArrayList = new ArrayList<>();
    protected ArrayList<Rabbit> rabbitArrayList = new ArrayList<>();

    protected int day = 1;

    public Ecosystem() {

        // ---- PLANTS ----

        for (int i = 0; i < 20; i++) {

            plantArrayList.add(new Plant("Plant", 0, (int) (Math.random() * 15), (int) (Math.random() * 15)));

        }

        // ---- BEES ----

        for (int i = 0; i < 10; i++) {

            beesArrayList.add(new Bees(0, (int) (Math.random() * 15), (int) (Math.random() * 15)));

        }

        // ---- RABBITS ----

        for (int i = 0; i < 15; i++) {

            rabbitArrayList.add(new Rabbit(0, 60, (int) (Math.random() * 15), (int) (Math.random() * 15), 25, 60, 30, 10, 8, 150, 3, 45, false, true, 3, 22, false));

        }

        // ---- FOXES ----

        for (int i = 0; i < 8; i++) {

            foxArrayList.add(new Fox(0, 70, (int) (Math.random() * 15), (int) (Math.random() * 15), 25, 60, 30, 8, 8, 180, 4, 80, false, true, 4, 28, true));

        }

    }

    public void playOneDay() {

        int temperature = (int) (Math.random() * 16) + 25;
        int environmentalToxin = (int) (Math.random() * 8) + 3;

        for (int i = 0; i < plantArrayList.size(); i++) {

            if (plantArrayList.get(i).getIsAlive()) {

                plantArrayList.get(i).feed();

                plantArrayList.get(i).reactToEnvironment(temperature, environmentalToxin);

            }

        }

        for (int i = 0; i < beesArrayList.size(); i++) {

            if (beesArrayList.get(i).isAlive) {

                beesArrayList.get(i).feed();
                beesArrayList.get(i).scanSurroundings(15, plantArrayList);
                beesArrayList.get(i).reactToEnvironment(temperature, environmentalToxin);

            }

        }

        for (int i = 0; i < foxArrayList.size(); i++) {

            if (foxArrayList.get(i).isAlive) {

                ArrayList<Animal> preyList = new ArrayList<>(rabbitArrayList);

                foxArrayList.get(i).scanSurroundings(preyList);
                foxArrayList.get(i).reactToEnvironment(temperature, environmentalToxin);
                foxArrayList.get(i).processMetabolism();

            }

        }

        for (int i = 0; i < rabbitArrayList.size(); i++) {

            if (rabbitArrayList.get(i).isAlive) {

                rabbitArrayList.get(i).feed();
                rabbitArrayList.get(i).scanSurroundings(plantArrayList, foxArrayList);
                rabbitArrayList.get(i).reactToEnvironment(temperature, environmentalToxin);

            }

        }

        plantArrayList.removeIf(p -> !p.getIsAlive());
        beesArrayList.removeIf(b -> !b.getIsAlive());
        foxArrayList.removeIf(f -> !f.getIsAlive());
        rabbitArrayList.removeIf(r -> !r.getIsAlive());


        day++;

    }

    public Organism[] getAllOrganisms() {
        ArrayList<Organism> all = new ArrayList<>();

        all.addAll(plantArrayList);
        all.addAll(beesArrayList);
        all.addAll(foxArrayList);
        all.addAll(rabbitArrayList);


        return all.toArray(new Organism[0]);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int newDay) {
        this.day = newDay;
    }

    public ArrayList<Plant> getPlantArrayList() {
        return plantArrayList;
    }

    public ArrayList<Bees> getBeesArrayList() {
        return beesArrayList;
    }

    public ArrayList<Rabbit> getRabbitArrayList() {
        return rabbitArrayList;
    }

    public ArrayList<Fox> getFoxArrayList() {
        return foxArrayList;
    }
}
