package lab1;

import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.*;

public class JavaFXClass extends Application {
	private Group root;
    private int x = 150;
    private int y = 150;
    private int r1 = 70;
    private int r2 = 62;
    private int r3 = 7;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        root = new Group();
        
        Scene scene = new Scene(root, 300, 250);
        scene.setFill(Color.DARKOLIVEGREEN);
        primaryStage.setScene(scene);

        Circle mainCircle1 = new Circle(x,y,r1);
        mainCircle1.setFill(Color.GOLD);
        root.getChildren().add(mainCircle1);
        
        Circle mainCircle2 = new Circle(x,y,r2);
        mainCircle2.setFill(Color.WHITESMOKE);
        root.getChildren().add(mainCircle2);
        
        Circle subCircle1 = new Circle(x + 50,y,r3);
        subCircle1.setFill(Color.GOLD);
        root.getChildren().add(subCircle1);
        
        Circle subCircle2 = new Circle(x - 50,y,r3);
        subCircle2.setFill(Color.GOLD);
        root.getChildren().add(subCircle2);
        
        Circle subCircle3 = new Circle(x,y + 50,r3);
        subCircle3.setFill(Color.GOLD);
        root.getChildren().add(subCircle3);
        
        Circle subCircle4 = new Circle(x,y - 50,r3);
        subCircle4.setFill(Color.GOLD);
        root.getChildren().add(subCircle4);
        
        Polygon outerHourHandPolygon = new Polygon(
                x-7, y,
                x, y - 8,
                x+38, y,
                x-7, y,
                x, y + 8,
                x+38, y);
        
        Polygon innerHourHandPolygon = new Polygon(
                x-4, y,
                x, y - 5,
                x+35, y,
                x-4, y,
                x, y + 5,
                x+35, y);
        
        innerHourHandPolygon.setFill(Color.WHITESMOKE);
        root.getChildren().addAll(outerHourHandPolygon, innerHourHandPolygon);
        
        Polygon minuteHandPolygon = new Polygon(
                x, y+8,
                x-3.5, y+4,
                x, y-58,
                x, y+8,
                x+3.5, y+4,
                x, y-58);

        root.getChildren().add(minuteHandPolygon);

        primaryStage.show();
    }
}
