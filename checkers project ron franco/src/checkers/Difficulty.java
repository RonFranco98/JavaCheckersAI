package checkers;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


/**
 * this is the window were the user choose the Difficulty level of the game against the computer
 */
public class Difficulty {
    public static Scene Dscene;
    private static int Difficulty; // 0 = easy , 1 = Difficult;
    public static ChooseOneName CON = new ChooseOneName();
    public static opening_window OP = new opening_window();
    public static void DifficultyScene() {
        //compunents
        Button easy = new Button("easy");
        Button Difficult = new Button("Difficult");
        Button back_button = new Button("" ,new ImageView("checkers/img/backward.png"));
        Text title = new Text("choose the Difficulty level");
        title.setFont(Font.font("tahoma", 35));
        //layout
        GridPane GP = new GridPane();
        GP.setAlignment(Pos.CENTER);
        GP.add(title, 0, 0);
        GP.add(easy, 0, 1);
        GP.add(Difficult ,0,2);
        GP.add(back_button , 0,3);
        GP.setVgap(20);

        //scene
        Dscene = new Scene(GP , 500,500);
        Dscene.getStylesheets().add("checkers/style.css");

        //event
        easy.setOnMouseClicked(e ->{
            Difficulty = 0;
            CON.ChooseOneNameScene(Difficulty);
        });
        Difficult.setOnMouseClicked(e ->{
            Difficulty = 1;
            CON.ChooseOneNameScene(Difficulty);
        });
        back_button.setOnMouseClicked(e ->{
            OP.window.setScene(OP.scene);
        });
        OP.setWindowScene(Dscene);
    }
    public static int getDifficulty(){
        return Difficulty;
    }
    public static void setScene(){OP.window.setScene(Dscene);}
    public static Scene getScene(){
        return Dscene;
    }
}
