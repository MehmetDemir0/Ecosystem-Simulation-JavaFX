package Pack;

import Pack.Fx.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {

        launch(args);

    }

    public void start(Stage primaryStage) throws Exception {

        Ecosystem ecosystem = new Ecosystem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Fx/Scene1.fxml"));

        Parent root = loader.load();

        Controller controller = loader.getController();

        controller.setEcosystem(ecosystem);

        primaryStage.setTitle("Ecosystem Simulation by MEHMET DEMIR");

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        Image icon = new Image("Pack/photos/planet-earth.png");
        primaryStage.getIcons().add(icon);

        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            controller.generalExit();
        });


    }

}
