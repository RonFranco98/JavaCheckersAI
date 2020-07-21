package checkers;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class TwoPlayersGame {
    //vars
    private static opening_window OP = new opening_window();
    private static String name1;
    private static String name2;
    private static boolean turn = true;//true = blue; false = red;
    private static Group layout = new Group();
    private static Scene scene = new Scene(layout, 800 - 12, 800 - 12);
    private static Rectangle[][] board_sqers = new Rectangle[8][8];
    private static Circle[] RedCircle = new Circle[12];
    private static Circle[] BlueCircle = new Circle[12];
    private static String[][] PawnLocations = {
            {"R", "N", "R", "N", "R", "N", "R", "N"},
            {"N", "R", "N", "R", "N", "R", "N", "R"},
            {"R", "N", "R", "N", "R", "N", "R", "N"},
            {"N", "N", "N", "N", "N", "N", "N", "N"},
            {"N", "N", "N", "N", "N", "N", "N", "N"},
            {"N", "B", "N", "B", "N", "B", "N", "B"},
            {"B", "N", "B", "N", "B", "N", "B", "N"},
            {"N", "B", "N", "B", "N", "B", "N", "B"}
    };

    public void start(String n1, String n2) {
        name1 = n1;
        name2 = n2;
        paint();
        PaintPawn();
        AddMotionDetectorToAll();
        OP.window.setScene(scene);
        OP.window.setResizable(false);
    }

    //graphics
    public static void paint() {
        boolean BlackWhite = true;//true = black , false = white
        for (int i = 0; i < board_sqers.length; i++) {
            for (int j = 0; j < board_sqers.length; j++) {
                if (BlackWhite == true) {
                    board_sqers[i][j] = new Rectangle(i * 100, j * 100, 100, 100);
                    board_sqers[i][j].setFill(Color.rgb(131, 89, 48));
                    BlackWhite = false;
                } else if (BlackWhite == false) {
                    board_sqers[i][j] = new Rectangle(i * 100, j * 100, 100, 100);
                    board_sqers[i][j].setFill(Color.rgb(163, 133, 102));
                    BlackWhite = true;
                }
                layout.getChildren().add(board_sqers[i][j]);
            }
            if (BlackWhite == true) {
                BlackWhite = false;
            } else if (BlackWhite == false) {
                BlackWhite = true;
            }
        }
    }

    public static void PaintPawn() {
        int X = 50;
        int Y = 50;
        int RedCounter = 0;
        int BlueCounter = 0;
        for (int i = 0; i < PawnLocations.length; i++) {
            for (int j = 0; j < PawnLocations.length; j++) {
                if (PawnLocations[i][j] == "R") {
                    RedCircle[RedCounter] = new Circle(X, Y, 40, Color.rgb(255, 71, 71));
                    layout.getChildren().add(RedCircle[RedCounter]);
                    RedCounter++;
                } else if (PawnLocations[i][j] == "B") {
                    BlueCircle[BlueCounter] = new Circle(X, Y, 40, Color.rgb(51, 102, 255));
                    layout.getChildren().add(BlueCircle[BlueCounter]);
                    BlueCounter++;
                } else if (PawnLocations[i][j] == "RK") {
                    RedCircle[RedCounter] = new Circle(X, Y, 40, Color.rgb(179, 0, 0));
                    RedCircle[RedCounter].setStroke(Color.GOLD);
                    RedCircle[RedCounter].setStrokeWidth(5);
                    layout.getChildren().add(RedCircle[RedCounter]);
                    RedCounter++;
                } else if (PawnLocations[i][j] == "BK") {
                    BlueCircle[BlueCounter] = new Circle(X, Y, 40, Color.rgb(0, 49, 204));
                    BlueCircle[BlueCounter].setStroke(Color.GOLD);
                    BlueCircle[BlueCounter].setStrokeWidth(5);
                    layout.getChildren().add(BlueCircle[BlueCounter]);
                    BlueCounter++;
                }
                X = X + 100;
            }
            X = 50;
            Y = Y + 100;
        }
        for (; RedCounter < RedCircle.length; RedCounter++) {
            RedCircle[RedCounter] = new Circle(900, 900, 40, Color.rgb(255, 71, 71));
            layout.getChildren().add(RedCircle[RedCounter]);
        }
        for (; BlueCounter < BlueCircle.length; BlueCounter++) {
            BlueCircle[BlueCounter] = new Circle(900, 900, 40, Color.rgb(51, 102, 255));
            layout.getChildren().add(BlueCircle[BlueCounter]);
        }
    }

    //user movement input function
    public static void AddMotionDetector(int i) {
        if (turn == true) {
            BlueCircle[i].setOnMouseClicked(e -> {
                cleanSquerOutLines();
                int colum = (int) (((BlueCircle[i].getCenterX()) - 50) / 100);
                int row = (int) (((BlueCircle[i].getCenterY()) - 50) / 100);
                if (colum > 0 && row > 0) {
                    if (can_move_left(colum, row) == true) {
                        makeSquerOutLines(colum - 1, row - 1);
                        board_sqers[colum - 1][row - 1].setOnMouseClicked(event -> {
                            move_left(colum, row);
                        });
                    }
                    if (colum > 1 && row > 1) {
                        if (can_eat_left(colum, row)) {
                            makeSquerOutLines(colum - 2, row - 2);
                            board_sqers[colum - 2][row - 2].setOnMouseClicked(event -> {
                                eat_left(colum, row);
                            });
                        }
                    }
                }
                if (colum < 7 && row > 0) {
                    if (can_move_right(colum, row)) {
                        makeSquerOutLines(colum + 1, row - 1);
                        board_sqers[colum + 1][row - 1].setOnMouseClicked(event -> {
                            move_right(colum, row);
                        });
                    }
                    if (colum < 6 && row > 1) {
                        if (can_eat_right(colum, row)) {
                            makeSquerOutLines(colum + 2, row - 2);
                            board_sqers[colum + 2][row - 2].setOnMouseClicked(event -> {
                                eat_right(colum, row);
                            });
                        }
                    }
                }
                if (colum > 0 && row < 7) {
                    if (can_move_left_back(colum, row) == true) {
                        makeSquerOutLines(colum - 1, row + 1);
                        board_sqers[colum - 1][row + 1].setOnMouseClicked(event -> {
                            move_left_back(colum, row);
                        });
                    }
                    if(colum > 1 && row <6){
                        if(can_eat_left_back(colum , row)){
                            makeSquerOutLines(colum - 2, row + 2);
                            board_sqers[colum - 2][row + 2].setOnMouseClicked(event -> {
                                eat_left_back(colum, row);
                            });
                        }
                    }
                }
                if (colum < 7 && row < 7) {
                    if (can_move_right_back(colum, row) == true) {
                        makeSquerOutLines(colum + 1, row + 1);
                        board_sqers[colum + 1][row + 1].setOnMouseClicked(event -> {
                            move_right_back(colum, row);
                        });
                    }
                    if(colum < 6 && row < 6){
                        if(can_eat_right_back(colum, row)){
                            makeSquerOutLines(colum + 2, row + 2);
                            board_sqers[colum + 2][row + 2].setOnMouseClicked(event -> {
                                eat_right_back(colum, row);
                            });
                        }
                    }
                }
            });
        } else if (turn == false) {
            RedCircle[i].setOnMouseClicked(e -> {
                cleanSquerOutLines();
                int colum = (int) (((RedCircle[i].getCenterX()) - 50) / 100);
                int row = (int) (((RedCircle[i].getCenterY()) - 50) / 100);
                if (colum > 0 && row > 0) {
                    if (can_move_left(colum, row)) {
                        makeSquerOutLines(colum - 1, row - 1);
                        board_sqers[colum - 1][row - 1].setOnMouseClicked(event -> {
                            move_left(colum, row);
                        });
                    }
                    if (colum > 1 && row > 1) {
                        if (can_eat_left(colum, row)) {
                            makeSquerOutLines(colum - 2, row - 2);
                            board_sqers[colum - 2][row - 2].setOnMouseClicked(event -> {
                                eat_left(colum, row);
                                repaint();
                            });
                        }
                    }
                }
                if (colum < 7 && row > 0) {
                    if (can_move_right(colum, row)) {
                        makeSquerOutLines(colum + 1, row - 1);
                        board_sqers[colum + 1][row - 1].setOnMouseClicked(event -> {
                            move_right(colum, row);
                        });
                    }
                    if (colum < 6 && row > 1) {
                        if (can_eat_right(colum, row)) {
                            makeSquerOutLines(colum + 2, row - 2);
                            board_sqers[colum + 2][row - 2].setOnMouseClicked(event -> {
                                eat_right(colum, row);
                            });
                        }
                    }
                }
                if (colum > 0 && row < 7) {
                    if (can_move_left_back(colum, row) == true) {
                        makeSquerOutLines(colum - 1, row + 1);
                        board_sqers[colum - 1][row + 1].setOnMouseClicked(event -> {
                            move_left_back(colum, row);
                        });
                    }
                    if(colum > 1 && row < 6){
                        if(can_eat_left_back(colum , row)){
                            makeSquerOutLines(colum - 2, row + 2);
                            board_sqers[colum - 2][row + 2].setOnMouseClicked(event -> {
                                eat_left_back(colum, row);
                            });
                        }
                    }
                }
                if (colum < 7 && row < 7) {
                    if (can_move_right_back(colum, row) == true) {
                        makeSquerOutLines(colum + 1, row + 1);
                        board_sqers[colum + 1][row + 1].setOnMouseClicked(event -> {
                            move_right_back(colum, row);
                        });
                    }
                    if(colum < 6 && row < 6){
                        if(can_eat_right_back(colum , row)){
                            makeSquerOutLines(colum + 2, row + 2);
                            board_sqers[colum + 2][row + 2].setOnMouseClicked(event -> {
                                eat_right_back(colum, row);
                            });
                        }
                    }
                }
            });
        }
    }

    public static void AddMotionDetectorToAll() {
        for (int i = 0; i < RedCircle.length; i++) {
            AddMotionDetector(i);
        }
    }

    //tools
    public static void makeSquerOutLines(int colum, int row) {
        board_sqers[colum][row].setStroke(Color.rgb(153, 230, 255));
        board_sqers[colum][row].setStrokeWidth(5);
    }

    public static void cleanSquerOutLines() {
        for (int i = 0; i < board_sqers.length; i++) {
            for (int j = 0; j < board_sqers.length; j++) {
                board_sqers[i][j].setStrokeWidth(0);
            }
        }
    }

    public static void LocationConverter() {
        String temp;
        for (int i = 0; i < PawnLocations.length / 2; i++) {
            for (int j = 0; j < PawnLocations.length; j++) {
                temp = PawnLocations[i][j];
                PawnLocations[i][j] = PawnLocations[(PawnLocations.length - 1) - i][(PawnLocations.length - 1) - j];
                PawnLocations[(PawnLocations.length - 1) - i][(PawnLocations.length - 1) - j] = temp;

            }
        }
    }

    public static void turnBoard() {
        LocationConverter();
        layout.getChildren().remove(0, 88);
        paint();
        PaintPawn();
        AddMotionDetectorToAll();
    }

    public static void repaint() {
        layout.getChildren().remove(0, 88);
        paint();
        PaintPawn();
        AddMotionDetectorToAll();
    }

    //wining functions
    public static boolean CheckRedWin() {
        boolean bool = true;
        for (int i = 0; i < PawnLocations.length; i++) {
            for (int j = 0; j < PawnLocations.length; j++) {
                if (PawnLocations[i][j] == "B" ||PawnLocations[i][j] == "BK") {
                    bool = false;
                }
            }
        }
        return bool;
    }

    public static boolean CheckBlueWin() {
        boolean bool = true;
        for (int i = 0; i < PawnLocations.length; i++) {
            for (int j = 0; j < PawnLocations.length; j++) {
                if (PawnLocations[i][j] == "R" || PawnLocations[i][j] == "RK") {
                    bool = false;
                }
            }
        }
        return bool;
    }

    public static void won(String Name, String color) {
        GridPane GP = new GridPane();
        Text text = new Text(Name + " won!!");
        Button rematch_button = new Button("Rematch ?");
        text.setFont(Font.font("Tahoma", 72));
        if (color == "red") {
            text.setFill(Color.RED);
        } else if (color == "blue") {
            text.setFill(Color.BLUE);
        }
        GP.add(text,0,0);
        GP.add(rematch_button,0,1);
        GP.setAlignment(Pos.CENTER);
        GP.setVgap(10);
        Scene sce = new Scene(GP, 750, 300);
        sce.getStylesheets().add("checkers/style.css");
        OP.setWindowScene(sce);
        rematch_button.setOnAction(e ->{
            PawnLocations = new String[][]{
                    {"R", "N", "R", "N", "R", "N", "R", "N"},
                    {"N", "R", "N", "R", "N", "R", "N", "R"},
                    {"R", "N", "R", "N", "R", "N", "R", "N"},
                    {"N", "N", "N", "N", "N", "N", "N", "N"},
                    {"N", "N", "N", "N", "N", "N", "N", "N"},
                    {"N", "B", "N", "B", "N", "B", "N", "B"},
                    {"B", "N", "B", "N", "B", "N", "B", "N"},
                    {"N", "B", "N", "B", "N", "B", "N", "B"}
            };
            OP.window.setScene(scene);
            turn = true;
            turnBoard();
            turnBoard();
        });
    }

    //moving
    public static boolean can_move_left(int colum, int row) {
        if (PawnLocations[row - 1][colum - 1] == "N") {
            return true;
        } else {
            return false;
        }
    }

    public static void move_left(int colum, int row) {
        if (turn == true) {
            if (PawnLocations[row][colum] == "B") {
                PawnLocations[row - 1][colum - 1] = "B";
            } else if (PawnLocations[row][colum] == "BK") {
                PawnLocations[row - 1][colum - 1] = "BK";
            }
            if (row - 1 == 0) {
                PawnLocations[row - 1][colum - 1] = "BK";
            }
            turn = false;
        } else if (turn == false) {
            if (PawnLocations[row][colum] == "R") {
                PawnLocations[row - 1][colum - 1] = "R";
            } else if (PawnLocations[row][colum] == "RK") {
                PawnLocations[row - 1][colum - 1] = "RK";
            }
            if (row - 1 == 0) {
                PawnLocations[row - 1][colum - 1] = "RK";
            }
            turn = true;
        }
        PawnLocations[row][colum] = "N";
        turnBoard();
    }

    public static boolean can_move_right(int colum, int row) {
        if (PawnLocations[row - 1][colum + 1] == "N") {
            return true;
        } else {
            return false;
        }
    }

    public static void move_right(int colum, int row) {
        if (turn == true) {
            if (PawnLocations[row][colum] == "B") {
                PawnLocations[row - 1][colum + 1] = "B";
            } else if (PawnLocations[row][colum] == "BK") {
                PawnLocations[row - 1][colum + 1] = "BK";
            }
            if (row - 1 == 0) {
                PawnLocations[row - 1][colum + 1] = "BK";
            }
            turn = false;
        } else if (turn == false) {
            if (PawnLocations[row][colum] == "R") {
                PawnLocations[row - 1][colum + 1] = "R";
            } else if (PawnLocations[row][colum] == "RK") {
                PawnLocations[row - 1][colum + 1] = "RK";
            }
            if (row - 1 == 0) {
                PawnLocations[row - 1][colum + 1] = "RK";
            }
            turn = true;
        }
        PawnLocations[row][colum] = "N";
        turnBoard();
    }

    //eating
    public static boolean can_eat_left(int colum, int row) {
        if (turn == true) {
            if ((PawnLocations[row - 1][colum - 1] == "R" || PawnLocations[row - 1][colum - 1] == "RK") && PawnLocations[row - 2][colum - 2] == "N") {
                return true;
            } else {
                return false;
            }
        } else {
            if ((PawnLocations[row - 1][colum - 1] == "B" || PawnLocations[row - 1][colum - 1] == "BK") && PawnLocations[row - 2][colum - 2] == "N") {
                return true;
            } else {
                return false;
            }
        }
    }

    public static void eat_left(int colum, int row) {
        if (turn == true) {
            if (PawnLocations[row][colum] == "B") {
                PawnLocations[row - 2][colum - 2] = "B";
            } else if (PawnLocations[row][colum] == "BK") {
                PawnLocations[row - 2][colum - 2] = "BK";
            }
            PawnLocations[row - 1][colum - 1] = "N";
            PawnLocations[row][colum] = "N";
            if (row - 2 == 0) {
                PawnLocations[row - 2][colum - 2] = "BK";
            }
            if (CheckBlueWin() == true) {
                won(name1, "blue");
            }
            if (colum - 2 > 1 && row - 2 > 1) {
                if (can_eat_right(colum - 2, row - 2) == false && can_eat_left(colum - 2, row - 2) == false) {
                    turn = false;
                    turnBoard();
                } else {
                    turn = true;
                    repaint();
                }
            } else {
                turn = false;
                turnBoard();
            }
        } else if (turn == false) {
            if (PawnLocations[row][colum] == "R") {
                PawnLocations[row - 2][colum - 2] = "R";
            } else if (PawnLocations[row][colum] == "RK") {
                PawnLocations[row - 2][colum - 2] = "RK";
            }
            PawnLocations[row - 1][colum - 1] = "N";
            PawnLocations[row][colum] = "N";
            if (row - 2 == 0) {
                PawnLocations[row - 2][colum - 2] = "RK";
            }
            if (CheckRedWin() == true) {
                won(name2, "red");
            }
            if (colum - 2 > 1 && row - 2 > 1) {
                if (can_eat_right(colum - 2, row - 2) == false && can_eat_left(colum - 2, row - 2) == false) {
                    turn = true;
                    turnBoard();
                } else {
                    turn = false;
                    repaint();
                }
            } else {
                turn = true;
                turnBoard();
            }
        }
    }

    public static boolean can_eat_right(int colum, int row) {
        if (turn == true) {
            if ((PawnLocations[row - 1][colum + 1] == "R" || PawnLocations[row - 1][colum + 1] == "RK") && PawnLocations[row - 2][colum + 2] == "N") {
                return true;
            } else {
                return false;
            }
        } else {
            if ((PawnLocations[row - 1][colum + 1] == "B" || PawnLocations[row - 1][colum + 1] == "BK") && PawnLocations[row - 2][colum + 2] == "N") {
                return true;
            } else {
                return false;
            }
        }
    }

    public static void eat_right(int colum, int row) {
        if (turn == true) {
            if (PawnLocations[row][colum] == "B") {
                PawnLocations[row - 2][colum + 2] = "B";
            } else if (PawnLocations[row][colum] == "BK") {
                PawnLocations[row - 2][colum + 2] = "BK";
            }
            PawnLocations[row - 1][colum + 1] = "N";
            PawnLocations[row][colum] = "N";
            if (row - 2 == 0) {
                PawnLocations[row - 2][colum + 2] = "BK";
            }
            if (CheckBlueWin() == true) {
                won(name1, "blue");
            }
            if (colum + 2 < 6 && row - 2 > 1) {
                if (can_eat_left(colum + 2, row - 2) == false && can_eat_right(colum + 2, row - 2) == false) {
                    turn = false;
                    turnBoard();
                } else {
                    turn = true;
                    repaint();
                }
            } else {
                turn = false;
                turnBoard();
            }
        } else if (turn == false) {
            if (PawnLocations[row][colum] == "R") {
                PawnLocations[row - 2][colum + 2] = "R";
            } else if (PawnLocations[row][colum] == "RK") {
                PawnLocations[row - 2][colum + 2] = "RK";
            }
            PawnLocations[row - 1][colum + 1] = "N";
            PawnLocations[row][colum] = "N";
            if (row - 2 == 0) {
                PawnLocations[row - 2][colum + 2] = "RK";
            }
            if (CheckRedWin() == true) {
                won(name2, "red");
            }
            if (colum + 2 < 6 && row - 2 > 1) {
                if (can_eat_left(colum + 2, row - 2) == false && can_eat_right(colum + 2, row - 2) == false) {
                    turn = true;
                    turnBoard();
                } else {
                    turn = false;
                    repaint();
                }
            } else {
                turn = true;
                turnBoard();
            }
        }
    }

    //moving back
    public static boolean can_move_left_back(int colum, int row) {
        if (PawnLocations[row + 1][colum - 1] == "N" && (PawnLocations[row][colum] == "BK" || PawnLocations[row][colum] == "RK")) {
            return true;
        } else {
            return false;
        }
    }

    public static void move_left_back(int colum, int row) {
        if (turn == true) {
            if (PawnLocations[row][colum] == "B") {
                PawnLocations[row + 1][colum - 1] = "B";
            } else if (PawnLocations[row][colum] == "BK") {
                PawnLocations[row + 1][colum - 1] = "BK";
            }
            if (row - 1 == 0) {
                PawnLocations[row + 1][colum - 1] = "BK";
            }
            turn = false;
        } else if (turn == false) {
            if (PawnLocations[row][colum] == "R") {
                PawnLocations[row + 1][colum - 1] = "R";
            } else if (PawnLocations[row][colum] == "RK") {
                PawnLocations[row + 1][colum - 1] = "RK";
            }
            if (row - 1 == 0) {
                PawnLocations[row + 1][colum - 1] = "RK";
            }
            turn = true;
        }
        PawnLocations[row][colum] = "N";
        turnBoard();
    }

    public static boolean can_move_right_back(int colum, int row) {
        if (PawnLocations[row + 1][colum + 1] == "N" && (PawnLocations[row][colum] == "BK" || PawnLocations[row][colum] == "RK")) {
            return true;
        } else {
            return false;
        }
    }

    public static void move_right_back(int colum, int row) {
        if (turn == true) {
            if (PawnLocations[row][colum] == "B") {
                PawnLocations[row + 1][colum + 1] = "B";
            } else if (PawnLocations[row][colum] == "BK") {
                PawnLocations[row + 1][colum + 1] = "BK";
            }
            if (row - 1 == 0) {
                PawnLocations[row + 1][colum + 1] = "BK";
            }
            turn = false;
        } else if (turn == false) {
            if (PawnLocations[row][colum] == "R") {
                PawnLocations[row + 1][colum + 1] = "R";
            } else if (PawnLocations[row][colum] == "RK") {
                PawnLocations[row + 1][colum + 1] = "RK";
            }
            if (row - 1 == 0) {
                PawnLocations[row + 1][colum + 1] = "RK";
            }
            turn = true;
        }
        PawnLocations[row][colum] = "N";
        turnBoard();
    }
    //eating back
    public static boolean can_eat_left_back(int colum, int row) {
        if (turn == true) {
            if (((PawnLocations[row + 1][colum - 1] == "R" || PawnLocations[row + 1][colum - 1] == "RK") && PawnLocations[row + 2][colum - 2] == "N") && PawnLocations[row][colum] == "BK") {
                return true;
            } else {
                return false;
            }
        } else {
            if (((PawnLocations[row + 1][colum - 1] == "B" || PawnLocations[row + 1][colum - 1] == "BK") && PawnLocations[row + 2][colum - 2] == "N") && (PawnLocations[row][colum] == "RK")) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static void eat_left_back(int colum, int row) {
        if (turn == true) {
            PawnLocations[row + 2][colum - 2] = "BK";
            PawnLocations[row + 1][colum - 1] = "N";
            PawnLocations[row][colum] = "N";
            if (CheckBlueWin() == true) {
                won(name1, "blue");
            }
            if (colum - 2 > 1 && row + 2 > 1) {
                if (can_eat_right_back(colum - 2, row + 2) == false && can_eat_left_back(colum - 2, row + 2) == false && can_eat_right(colum - 2, row + 2) == false && can_eat_left(colum - 2, row + 2) == false) {
                    turn = false;
                    turnBoard();
                } else {
                    turn = true;
                    repaint();
                }
            } else {
                turn = false;
                turnBoard();
            }
        } else if (turn == false) {
            PawnLocations[row + 2][colum - 2] = "RK";
            PawnLocations[row + 1][colum - 1] = "N";
            PawnLocations[row][colum] = "N";
            if (CheckRedWin() == true) {
                won(name2, "red");
            }
            if (colum - 2 > 1 && row + 2 > 1) {
                if (can_eat_right_back(colum - 2, row + 2) == false && can_eat_left_back(colum - 2, row + 2) == false && can_eat_right(colum - 2, row + 2) == false && can_eat_left(colum - 2, row + 2) == false) {
                    turn = true;
                    turnBoard();
                } else {
                    turn = false;
                    repaint();
                }
            } else {
                turn = true;
                turnBoard();
            }
        }
    }

    public static boolean can_eat_right_back(int colum, int row) {
        if (turn == true) {
            if (((PawnLocations[row + 1][colum + 1] == "R" || PawnLocations[row + 1][colum + 1] == "RK") && PawnLocations[row + 2][colum + 2] == "N") && PawnLocations[row][colum] == "BK") {
                return true;
            } else {
                return false;
            }
        } else {
            if (((PawnLocations[row + 1][colum + 1] == "B" || PawnLocations[row + 1][colum + 1] == "BK") && PawnLocations[row + 2][colum + 2] == "N") && PawnLocations[row][colum] == "RK") {
                return true;
            } else {
                return false;
            }
        }
    }

    public static void eat_right_back(int colum, int row) {
        if (turn == true) {
            PawnLocations[row + 2][colum + 2] = "BK";
            PawnLocations[row + 1][colum + 1] = "N";
            PawnLocations[row][colum] = "N";
            if (CheckBlueWin() == true) {
                won(name1, "blue");
            }
            if (colum + 2 < 6 && row + 2 < 6) {
                if (can_eat_left_back(colum + 2, row + 2) == false && can_eat_right_back(colum + 2, row + 2) == false && can_eat_left(colum + 2, row + 2) == false && can_eat_right(colum + 2, row + 2) == false) {
                    turn = false;
                    turnBoard();
                } else {
                    turn = true;
                    repaint();
                }
            } else {
                turn = false;
                turnBoard();
            }
        } else if (turn == false) {
            PawnLocations[row + 2][colum + 2] = "RK";
            PawnLocations[row + 1][colum + 1] = "N";
            PawnLocations[row][colum] = "N";
            if (CheckRedWin() == true) {
                won(name2, "red");
            }
            if (colum + 2 < 6 && row + 2 < 6) {
                if (can_eat_left(colum + 2, row + 2) == false && can_eat_right(colum + 2, row + 2) == false && can_eat_left_back(colum + 2, row + 2) == false && can_eat_right_back(colum + 2, row + 2) == false) {
                    turn = true;
                    turnBoard();
                } else {
                    turn = false;
                    repaint();
                }
            } else {
                turn = true;
                turnBoard();
            }
        }
    }
}