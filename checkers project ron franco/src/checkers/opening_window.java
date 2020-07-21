package checkers;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This window is the first thing the user will see when he is opening the Jar File
 */
public class opening_window extends Application {
    // objects

    public static Difficulty DifficultyObject = new Difficulty();
    public static statistic Statistic = new statistic();
    public static Scene scene;
    public static Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage PS) throws Exception {
        window = PS;
        //compunents
        Button computer_button = new Button("computer");
        Button another_user_button = new Button("another user");
        Button quit_button = new Button("quit");
        Button statistic_button = new Button("statistic");
        Text title = new Text("Let's Play Checkers");
        title.setFont(Font.font("tahoma", 35));
        //layout
        GridPane GP = new GridPane();
        GP.setAlignment(Pos.CENTER);
        GP.add(title, 0, 0);
        GP.add(another_user_button, 0, 1);
        GP.add(computer_button, 0, 2);
        GP.add(statistic_button, 0, 3);
        GP.add(quit_button, 0, 4);
        GP.setVgap(20);

        //scene
        scene = new Scene(GP, 500, 500);
        scene.getStylesheets().add("checkers/style.css");

        //window
        window.setScene(scene);
        window.setTitle("Checkers project");
        window.show();

        //evnt handeler
        another_user_button.setOnAction(e -> {
            setWindowScene(ChooseTwoNames.getScene());
        });
        computer_button.setOnAction(e -> {
            DifficultyObject.DifficultyScene();
        });
        statistic_button.setOnAction(e -> {
            Statistic.PieChart();
        });
        quit_button.setOnMouseClicked(e -> {
            window.close();
        });
    }

    public static void setWindowScene(Scene ParamaterScene) {
        window.setScene(ParamaterScene);
    }
}