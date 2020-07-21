package checkers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.io.*;

/**
 * this class pravid the statistic data of wining and losing agenst the computer and display it on a pie-chart
 */
public class statistic{
    public static int NumberOfGame, NumberOfWins, NumberOfLose;
    public static Scene StatisticScene;
    public static opening_window OP = new opening_window();
    public static void getData() {
        String str1, str2;
        File file = new File("test.txt");
        try {
            if (file.exists() == false) {
                file.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            str1 = reader.readLine();
            str2 = reader.readLine();
            reader.close();
            if(str1 == null || str2 == null){
                NumberOfGame = 0;
                NumberOfWins = 0;
            }
            else {
                NumberOfGame = Integer.parseInt(str1);
                NumberOfWins = Integer.parseInt(str2);
            }
            NumberOfLose = NumberOfGame - NumberOfWins;
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static int getNumberOfGame() {return NumberOfGame;}

    public static int getNumberOfWins() {
        return NumberOfWins;
    }

    public static int getNumberOfLose() {
        return NumberOfLose;
    }

    public static void incriseByOneWin(){
        getData();
        NumberOfGame = NumberOfGame + 1;
        NumberOfWins = NumberOfWins + 1;
        RewriteFile();
    }
    public static void incriseByOneLose(){
        getData();
        NumberOfGame = NumberOfGame + 1;
        NumberOfLose = NumberOfLose + 1;
        RewriteFile();
    }
    public static void RewriteFile(){
        try {
            File file = new File("test.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            writer.write(Integer.toString(NumberOfGame));
            writer.newLine();
            writer.write(Integer.toString(NumberOfWins));
            writer.close();
        }catch(Exception e){}
    }
    public void PieChart() {
        getData();
        Button back_button = new Button("", new ImageView("checkers/img/backward.png"));
        Group layout = new Group();

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("wins : " + NumberOfWins, NumberOfWins),
                        new PieChart.Data("lose : " + NumberOfLose, NumberOfLose));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("game statistic");

        layout.getChildren().add(chart);
        layout.getChildren().add(back_button);
        StatisticScene = new Scene(layout, 500, 500);
        StatisticScene.getStylesheets().add("checkers/style.css");
        back_button.setOnMouseClicked(e -> {
            OP.setWindowScene(OP.scene);
        });
        OP.setWindowScene(StatisticScene);
    }
}