package checkers;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


/**
 * if the user choose to play with the computer and choose the difficulty level he have to choose a name for has player
 */
public class ChooseOneName{
    public static Scene CONscene;
    public static String player_one_name;
    public static Difficulty diff_object = new Difficulty();
    public static opening_window OP = new opening_window();
    public static int Difficulty;
    public static void ChooseOneNameScene(int difficulty){
        //seting Difficulty level
        Difficulty = difficulty;
        //compunents
        TextField name = new TextField("player one");
        Button back_button = new Button("" ,new ImageView("checkers/img/backward.png"));
        Button OK = new Button("OK");
        Text title = new Text("choose your name");
        title.setFont(Font.font("tahoma", 35));
        Circle circle = new Circle(25,25,25);
        circle.setFill(Color.BLUE);
        //layout
        GridPane GP = new GridPane();
        GP.setAlignment(Pos.CENTER);
        GP.add(title, 0, 0);
        GP.add(name, 0, 1);
        GP.add(OK ,0,2);
        GP.add(circle , 1 ,1);
        GP.add(back_button ,0,3);
        GP.setVgap(20);

        //scene
        CONscene = new Scene(GP , 500,500);
        CONscene.getStylesheets().add("checkers/style.css");

        //events
        OK.setOnAction(e ->{
            if(name.getText().length() > 2 && name.getText().length() < 12 ) {
                player_one_name = name.getText();
                OnePlayerGame.start(player_one_name , Difficulty);
            }
            else{
                massage.Massage("the name should have at least 3 letters and lest than 12");
            }
        });
        back_button.setOnMouseClicked(e ->{
            OP.setWindowScene(diff_object.getScene());
        });
        OP.setWindowScene(CONscene);
    }
}
