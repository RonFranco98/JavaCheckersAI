package checkers;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * this class pravide a size-risponsive massage box to output a massage in a difreent window
 */

public class massage {
    public static void Massage(String mass) {
        Stage PS = new Stage();

        //compunents
        Text massage = new Text(mass);
        massage.setFont(Font.font("tahoma", 35));

        //layout
        StackPane SP = new StackPane();
        SP.getChildren().add(massage);

        //scene
        Scene scene = new Scene(SP, mass.length() * 17, 250);
        scene.getStylesheets().add("checkers/style.css");

        //window
        PS.setScene(scene);
        PS.setTitle("Checkers project");
        PS.show();
    }
}
