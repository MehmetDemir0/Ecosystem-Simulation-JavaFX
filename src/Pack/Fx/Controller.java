package Pack.Fx;

import Pack.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.Map;

public class Controller {

    @FXML
    private GridPane ecosystemGrid;

    private static final int SIZE = 15;

    private Ecosystem ecosystem;

    @FXML
    private Label dayCounter;
    public Button startButton;

    @FXML
    Label allOrganisms = new Label();

    private static final Map<Class<? extends Organism>, SpeciesStyle> STYLES = new HashMap<>();

    static {
        STYLES.put(Rabbit.class, new SpeciesStyle(HPos.LEFT, VPos.BOTTOM));
        STYLES.put(Bees.class, new SpeciesStyle(HPos.RIGHT, VPos.BOTTOM));
        STYLES.put(Plant.class, new SpeciesStyle(HPos.RIGHT, VPos.TOP));
        STYLES.put(Fox.class, new SpeciesStyle(HPos.LEFT, VPos.TOP));
    }

    private record SpeciesStyle(HPos hPos, VPos vPos) {
    }

    @FXML
    public void nextDay(ActionEvent e) {
        if (ecosystem.getDay() == 1) {
            startButton.setText("NEXT DAY");
            startButton.setAlignment(Pos.BOTTOM_RIGHT);
        }
        dayCounter.setText("Day: " + ecosystem.getDay());
        ecosystem.playOneDay();
        createGrid(ecosystem.getAllOrganisms());
    }

    public void setEcosystem(Ecosystem ecosystem) {
        this.ecosystem = ecosystem;
        ecosystemGrid.setGridLinesVisible(true);
        createGrid(this.ecosystem.getAllOrganisms());
    }

    @FXML
    private void createGrid(Organism[] organisms) {

        ecosystemGrid.getChildren().clear();
        ecosystemGrid.setGridLinesVisible(false);
        ecosystemGrid.setGridLinesVisible(true);

        if (ecosystem.getPlantArrayList().isEmpty()
                && ecosystem.getBeesArrayList().isEmpty()
                && ecosystem.getRabbitArrayList().isEmpty()
                && ecosystem.getFoxArrayList().isEmpty()) {
            allOrganisms.setVisible(true);
        }

        Map<Class<? extends Organism>, int[][]> countsBySpecies = new HashMap<>();
        Map<Class<? extends Organism>, boolean[][]> drawnBySpecies = new HashMap<>();
        for (Class<? extends Organism> species : STYLES.keySet()) {
            countsBySpecies.put(species, new int[SIZE][SIZE]);
            drawnBySpecies.put(species, new boolean[SIZE][SIZE]);
        }

        int[][] speciesCountPerCell = new int[SIZE][SIZE];

        for (Organism o : organisms) {
            int[][] counts = countsBySpecies.get(o.getClass());
            if (counts != null) {
                counts[o.getxPosition()][o.getyPosition()]++;
            }
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                for (int[][] counts : countsBySpecies.values()) {
                    if (counts[i][j] > 0) {
                        speciesCountPerCell[i][j]++;
                    }
                }
            }
        }

        for (Organism organism : organisms) {
            Class<? extends Organism> species = organism.getClass();
            SpeciesStyle style = STYLES.get(species);
            if (style == null) {
                continue;
            }

            int x = organism.getxPosition();
            int y = organism.getyPosition();

            boolean[][] drawn = drawnBySpecies.get(species);
            if (drawn[x][y]) {
                continue;
            }

            int countInCell = countsBySpecies.get(species)[x][y];
            boolean cellHasSingleSpecies = speciesCountPerCell[x][y] == 1;

            StackPane stackPane = buildOrganismView(organism, countInCell, cellHasSingleSpecies);

            GridPane.setFillWidth(stackPane, false);
            GridPane.setFillHeight(stackPane, false);
            ecosystemGrid.add(stackPane, x, y);

            if (cellHasSingleSpecies) {
                GridPane.setHalignment(stackPane, HPos.CENTER);
                GridPane.setValignment(stackPane, VPos.CENTER);
            } else {
                GridPane.setHalignment(stackPane, style.hPos());
                GridPane.setValignment(stackPane, style.vPos());
            }

            drawn[x][y] = true;
        }
    }

    private StackPane buildOrganismView(Organism organism, int countInCell, boolean cellHasSingleSpecies) {
        ImageView imageView = new ImageView(organism.getAvatar());

        double size = cellHasSingleSpecies ? 48 : 20;
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(imageView);

        if (countInCell > 1) {
            Label label = new Label("x" + countInCell);
            String fontSize = cellHasSingleSpecies ? "15px" : "10px";
            label.setStyle("-fx-font-size: " + fontSize + "; -fx-font-weight: bold;");
            stackPane.getChildren().add(label);
            stackPane.setAlignment(label, Pos.BOTTOM_RIGHT);
        }

        return stackPane;
    }

    public void exit(ActionEvent actionEvent) {
        this.generalExit();
    }

    public void generalExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("EXIT");
        alert.setHeaderText("Youre about to close the program!");
        alert.setContentText("Are you sure about exiting?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}