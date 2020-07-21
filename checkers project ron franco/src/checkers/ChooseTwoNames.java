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
 * Created by ron on 16/08/2015.
 */
public class ChooseTwoNames {
    public static TwoPlayersGame TPG = new TwoPlayersGame();
    public static Scene CTNscene;
    public static String player_one_name;//blue
    public static String player_two_name;//red
    public static opening_window OP = new opening_window();

    public static void ChooseTwoNameScene() {
        //compunents
        TextField name1 = new TextField("player one");
        TextField name2 = new TextField("player two");
        Button back_button = new Button("", new ImageView("checkers/img/backward.png"));
        Button OK = new Button("OK");
        Text title = new Text("choose your names");
        title.setFont(Font.font("tahoma", 35));
        Circle circle1 = new Circle(25, 25, 25);
        circle1.setFill(Color.BLUE);
        Circle circle2 = new Circle(25, 25, 25);
        circle2.setFill(Color.RED);
        //layout
        GridPane GP = new GridPane();
        GP.setAlignment(Pos.CENTER);
        GP.add(title, 0, 0);
        GP.add(name1, 0, 1);
        GP.add(circle1, 1, 1);
        GP.add(name2, 0, 2);
        GP.add(circle2, 1, 2);
        GP.add(OK, 0, 3);
        GP.add(back_button, 0, 4);
        GP.setVgap(20);

        //scene
        CTNscene = new Scene(GP, 500, 500);
        CTNscene.getStylesheets().add("checkers/style.css");

        //events
        OK.setOnAction(e -> {
            if ((name1.getText().length() > 2 && name1.getText().length() < 12) && (name2.getText().length() > 2 && name2.getText().length() < 12)) {
                player_one_name = name1.getText();
                player_two_name = name2.getText();
                TPG.start(player_one_name, player_two_name);
            } else {
                massage.Massage("the name should have at least 3 letters and lest than 12");
            }
        });
        back_button.setOnMouseClicked(e -> {
            OP.setWindowScene(OP.scene);
        });
        OP.setWindowScene(CTNscene);
    }
    public static Scene getScene(){
        ChooseTwoNameScene();
        return CTNscene;
    }
}