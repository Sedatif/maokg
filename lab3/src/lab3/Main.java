package lab3;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Lab 3");

        Group root = new Group();

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        HeaderBitmapImage image = ReadingImageFromFile.loadBitmapImage("trajectory.bmp");
        int width = (int)image.getWidth();
        int height = (int)image.getHeight();
        int half = (int)image.getHalfOfWidth();

        int let, let1, let2;
        char[][] map = new char[width][height];
        int numberOfPixels = 0;

        BufferedInputStream reader = new BufferedInputStream (new FileInputStream("pixels.txt"));

        for(int i=0;i<height;i++)
        {
            for(int j=0;j<half;j++)
            {
                Circle cir;
                let = reader.read();
                let1=let;
                let2=let;
                let1=let1&(0xf0);
                let1=let1>>4;
                let2=let2&(0x0f);
                if(j*2<width)
                {
                    cir = new Circle ((j)*2,(height-1-i),1, Color.valueOf((returnPixelColor(let1))));
                    root.getChildren().add(cir);
                    if (returnPixelColor(let1) == "BLACK")
                    {
                        map[j*2][height-1-i] = '1';
                        numberOfPixels++;
                    }
                    else
                    {
                        map[j*2][height-1-i] = '0';
                    }
                }
                if(j*2+1<width)
                {
                    cir = new Circle ((j)*2+1,(height-1-i),1,Color.valueOf((returnPixelColor(let2))));
                    root.getChildren().add(cir);
                    if (returnPixelColor(let2) == "BLACK")
                    {
                        map[j*2+1][height-1-i] = '1';
                        numberOfPixels++;
                    }
                    else
                    {
                        map[j*2+1][height-1-i] = '0';
                    }
                }
            }
        }

        reader.close();

        int[][] black;
        black = new int[numberOfPixels][2];
        int lich = 0;

        BufferedOutputStream writer = new BufferedOutputStream (new FileOutputStream("map.txt"));
        for(int i=0;i<height;i++)
        {
            for(int j=0;j<width;j++)
            {
                if (map[j][i] == '1')
                {
                    black[lich][0] = j;
                    black[lich][1] = i;
                    lich++;
                }
                writer.write(map[j][i]);
            }
            writer.write(10);
        }
        writer.close();

        System.out.println("number of black color pixels = " + numberOfPixels);

        Group train = new Group();

        Group body = new Group();

        Rectangle bodyBackground = new Rectangle(0, 0, 180, 100);
        bodyBackground.setFill(Color.rgb(255, 255, 255, 0));
        bodyBackground.setStroke(Color.BLACK);

        Rectangle firstBodyPart = new Rectangle(0, 0, 180, 51);
        firstBodyPart.setFill(Color.rgb(15, 84, 189));

        Rectangle secondBodyPart = new Rectangle(0, 50, 180, 34);
        secondBodyPart.setFill(Color.rgb(22, 54, 140));

        Rectangle thirdBodyPart = new Rectangle(0, 83, 180, 8);
        thirdBodyPart.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(22, 54, 140)), new Stop(1, Color.rgb(13, 36, 31))));

        Rectangle foughtBodyPart = new Rectangle(0, 90, 180, 10);
        foughtBodyPart.setFill(Color.rgb(13, 36, 31));

        Path cockpit = new Path(
                new MoveTo(110, 0),
                new LineTo(110, -45),
                new LineTo(90, -45),
                new LineTo(90, -55),
                new LineTo(185, -55),
                new LineTo(185, -45),
                new LineTo(180, -45),
                new LineTo(180, 0),
                new ClosePath()
        );
        cockpit.setFill(Color.rgb(15, 84, 189));

        Rectangle window = new Rectangle(120, -45, 50, 45);
        window.setFill(Color.rgb(43, 114, 193));
        window.setStroke(Color.BLACK);

        Path back = new Path(
                new MoveTo(180, 0),
                new QuadCurveTo(180, 15, 205, 20),
                new LineTo(200, 95),
                new LineTo(180, 95),
                new ClosePath()
        );
        back.setFill(Color.rgb(15, 84, 189));

        CubicCurve hump = new CubicCurve(58, 0, 58, -30, 98, -30, 98, 0);
        hump.setFill(Color.rgb(22, 54, 140));
        hump.setStroke(Color.BLACK);

        Group vent = new Group();

        CubicCurve ventUp = new CubicCurve(-30, -70, -35, -95, 75, -95, 70, -70);
        ventUp.setFill(Color.rgb(22, 54, 140));
        ventUp.setStroke(Color.BLACK);

        Path ventMain = new Path(
                new MoveTo(5, 0),
                new QuadCurveTo(0, -50,-30, -70),
                new QuadCurveTo(20, -80, 70, -70),
                new QuadCurveTo(40, -50, 35, 0),
                new ClosePath()
        );
        ventMain.setFill(Color.rgb(13, 36, 31));

        Shape ventRedSection = Shape.intersect(
                ventMain,
                Shape.union(
                        new QuadCurve(-35, -40, 20, -80, 70, -40),
                        new QuadCurve(-35, -40, 20, -35, 70, -40)
                )
        );
        ventRedSection.setFill(Color.RED);
        ventRedSection.setStroke(Color.BLACK);

        Path ventFooter = new Path(
                new MoveTo(0, 0),
                new QuadCurveTo(0, -7, 4, -7),
                new LineTo(36, -7),
                new QuadCurveTo(40, -7, 40, 0),
                new ClosePath()
        );
        ventFooter.setFill(Color.rgb(13, 36, 31));

        vent.getChildren().addAll(ventUp, ventMain, ventRedSection, ventFooter);
        vent.setLayoutX(7);

        Circle backWheel = new Circle(145, 50, 60);
        backWheel.setFill(Color.rgb(255, 0, 0));
        backWheel.setStroke(Color.BLACK);

        Circle backWheelRivet = new Circle(145, 50, 10);
        backWheelRivet.setFill(Color.rgb(13, 36, 31));
        backWheelRivet.setStroke(Color.BLACK);

        Path neck = new Path(
                new MoveTo(0, 0),
                new LineTo(15, 0),
                new QuadCurveTo(45, 50, 15, 100),
                new LineTo(0, 100),
                new ClosePath()
        );
        neck.setFill(Color.rgb(69, 60, 43));

        Group head = new Group();

        Shape face = Shape.union(
                new QuadCurve(0, 0, 30, 50, 0, 100),
                new Arc(0, 50, 45, 50, 90, 181)
        );
        face.setFill(Color.rgb(15, 84, 189));
        face.setStroke(Color.BLACK);

        Path mouth = new Path(
                new MoveTo(-30, 77),
                new QuadCurveTo(-13, 98, 0, 72),
                new QuadCurveTo(-13, 80, -30, 77)
        );
        mouth.setFill(Color.rgb(22, 54, 140));

        QuadCurve leftMouth = new QuadCurve(-1, 69, -1, 72, 3, 73);
        leftMouth.setFill(Color.TRANSPARENT);
        leftMouth.setStroke(Color.BLACK);

        QuadCurve rightMouth = new QuadCurve(-30, 75, -30, 77, -32, 79);
        rightMouth.setFill(Color.TRANSPARENT);
        rightMouth.setStroke(Color.BLACK);

        CubicCurve nose = new CubicCurve(-16, 71, -27, 75, -30, 61, -18, 61);
        nose.setFill(Color.rgb(22, 54, 140));
        nose.setStroke(Color.BLACK);

        Shape leftEye = Shape.union(
                new CubicCurve(-1, 60, -18, 70, -20, 45, -10, 30),
                new CubicCurve(-10, 30, 0, 25, 3, 48, -1, 60)
        );
        leftEye.setFill(Color.WHITE);
        leftEye.setStroke(Color.BLACK);

        Path leftEyeBrow1 = new Path(
                new MoveTo(1, 42),
                new QuadCurveTo(0, 32, -4, 28),
                new LineTo(-15, 23),
                new QuadCurveTo(-9, 30, 1, 42)
        );
        leftEyeBrow1.setFill(Color.rgb(22, 54, 140));

        Path leftEyeBrow2 = new Path(
                new MoveTo(-15, 23),
                new QuadCurveTo(-16, 27, -14, 36),
                new QuadCurveTo(-12, 34,-10, 30)
        );
        leftEyeBrow2.setFill(Color.rgb(22, 54, 140));

        Shape leftEyePupil = Shape.intersect(
                leftEye,
                Shape.union(
                        new CubicCurve(0, 58, -13, 63, -10, 45, -8, 40),
                        new CubicCurve(-8, 40, -3, 30, 5, 40, 0, 58)
                )
        );
        leftEyePupil.setFill(Color.BLACK);
        Polygon leftEyeLight = new Polygon(
                leftEyePupil.getLayoutBounds().getCenterX() - 10, leftEyePupil.getLayoutBounds().getCenterY() - 1,
                leftEyePupil.getLayoutBounds().getCenterX() - 10, leftEyePupil.getLayoutBounds().getCenterY() + 3,
                leftEyePupil.getLayoutBounds().getCenterX(), leftEyePupil.getLayoutBounds().getCenterY()
        );
        leftEyeLight.setFill(Color.WHITE);

        Group leftEyeGroup = new Group();
        leftEyeGroup.getChildren().addAll(leftEye, leftEyePupil, leftEyeBrow2, leftEyeBrow1, leftEyeLight);

        Shape rightEye = Shape.union(leftEye, leftEye);
        rightEye.setFill(Color.WHITE);
        rightEye.setStroke(Color.BLACK);

        Shape rightEyeBrow1 = Shape.union(leftEyeBrow1, leftEyeBrow1);
        rightEyeBrow1.setFill(Color.rgb(22, 54, 140));
        rightEyeBrow1.setStroke(Color.BLACK);

        Shape rightEyeBrow2 = Shape.union(leftEyeBrow2, leftEyeBrow2);
        rightEyeBrow2.setFill(Color.rgb(22, 54, 140));
        rightEyeBrow2.setStroke(Color.BLACK);

        Shape rightEyeLight = Shape.union(leftEyeLight, leftEyeLight);
        rightEyeLight.setFill(Color.WHITE);

        Shape rightEyePupil = Shape.union(leftEyePupil, leftEyePupil);
        rightEyePupil.setFill(Color.BLACK);

        Group rightEyeGroup = new Group();
        rightEyeGroup.getChildren().addAll(
                rightEye, rightEyeBrow1, rightEyeBrow2, rightEyePupil, rightEyeLight
        );
        rightEyeGroup.setLayoutX(-27);
        rightEyeGroup.setLayoutY(3);

        rightEyeGroup.setScaleX(0.85);
        rightEyeGroup.setScaleY(0.95);

        head.getChildren().addAll(
                face, mouth, rightMouth, leftMouth, nose,
                leftEyeGroup, rightEyeGroup
        );

        body.getChildren().addAll(
                hump,
                vent,
                firstBodyPart,
                secondBodyPart,
                thirdBodyPart,
                foughtBodyPart,
                bodyBackground,
                cockpit,
                window,
                back,
                backWheel,
                backWheelRivet,
                neck,
                head
        );

        body.setRotate(10);

        Circle frontLeftWheel = new Circle(15, 92, 28);
        frontLeftWheel.setFill(Color.rgb(255, 0, 0));
        frontLeftWheel.setStroke(Color.BLACK);

        Circle frontLeftWheelRivet = new Circle(15, 92, 4);
        frontLeftWheelRivet.setFill(Color.rgb(13, 36, 31));
        frontLeftWheelRivet.setStroke(Color.BLACK);

        Circle frontRightWheel = new Circle(0, 92, 28);
        frontRightWheel.setFill(Color.rgb(255, 0, 0));
        frontRightWheel.setStroke(Color.BLACK);

        train.getChildren().addAll(frontRightWheel, body, frontLeftWheel, frontLeftWheelRivet);
        train.setScaleX(0.6);
        train.setScaleY(0.6);

        root.getChildren().add(train);

        Path path2 = new Path();
        for (int l=0; l<numberOfPixels-1; l++)
        {
            path2.getElements().addAll(
                    new MoveTo(black[l][0],black[l][1]),
                    new LineTo (black[l+1][0],black[l+1][1])
            );
        }

        PathTransition pathTransition = new PathTransition(Duration.millis(8000), path2, train);
        pathTransition.setCycleCount(Transition.INDEFINITE);
        pathTransition.setAutoReverse(true);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(4000), train);
        scaleTransition.setByX(0.3);
        scaleTransition.setByY(0.3);
        scaleTransition.setCycleCount(Transition.INDEFINITE);
        scaleTransition.setAutoReverse(true);

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(8000), train);
        rotateTransition.setByAngle(360f);
        rotateTransition.setCycleCount(Transition.INDEFINITE);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(pathTransition, scaleTransition, rotateTransition);

        parallelTransition.play();

        primaryStage.show();
    }

    private String returnPixelColor (int color)
    {
        String col = "BLACK";
        switch(color)
        {
            case 0: return "BLACK";     //BLACK;
            case 1: return "LIGHTCORAL";  //LIGHTCORAL;
            case 2: return "GREEN";     //GREEN
            case 3: return "BROWN";     //BROWN
            case 4: return "BLUE";      //BLUE;
            case 5: return "MAGENTA";   //MAGENTA;
            case 6: return "CYAN";      //CYAN;
            case 7: return "LIGHTGRAY"; //LIGHTGRAY;
            case 8: return "DARKGRAY";  //DARKGRAY;
            case 9: return "RED";       //RED;
            case 10:return "LIGHTGREEN";//LIGHTGREEN
            case 11:return "YELLOW";    //YELLOW;
            case 12:return "LIGHTBLUE"; //LIGHTBLUE;
            case 13:return "LIGHTPINK";    //LIGHTMAGENTA
            case 14:return "LIGHTCYAN";    //LIGHTCYAN;
            case 15:return "WHITE";    //WHITE;
        }
        return col;
    }
}