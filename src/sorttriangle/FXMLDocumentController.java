/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sorttriangle;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 *
 * @author blj0011
 */
public class FXMLDocumentController implements Initializable {
    @FXML AnchorPane apMain;
    @FXML Group gpMain;
    
    Circle circle1 = new Circle();
    Circle circle2 = new Circle();
    Circle circle3 = new Circle();
    Circle circle4 = new Circle();
        
    int[] circleValues = {4,3,1,2};
    final Circle[] circleContainer = {circle1, circle2, circle3, circle4};
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        circle1.setFill(Color.BLUE);
        circle1.setRadius(20);
        circle1.setCenterX(50);
        circle1.setCenterY(50);
        circle1.setId("4");
        
        circle2.setFill(Color.RED);
        circle2.setRadius(20);
        circle2.setCenterX(100);
        circle2.setCenterY(50);
        circle2.setId("3");
        
        circle3.setFill(Color.GREEN);
        circle3.setRadius(20);
        circle3.setCenterX(150);
        circle3.setCenterY(50);
        circle3.setId("1");
        
        circle4.setFill(Color.YELLOW);
        circle4.setRadius(20);
        circle4.setCenterX(200);
        circle4.setCenterY(50);
        circle4.setId("2");        
             
        //gpMain.getChildren().addAll(circleContainer);
        apMain.getChildren().addAll(circleContainer);
        SequentialTransition sequentialTransition = new SequentialTransition();
        for (int i = 0; i < circleValues.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < circleValues.length; j++)
            {
                if (circleValues[j] < circleValues[index])
                {
                    index = j;
                }
            }      
            int smallerNumber = circleValues[index]; 
            Circle smallerCircle = circleContainer[index];
            
            circleValues[index] = circleValues[i];
            circleContainer[index] = circleContainer[i];
            
            circleValues[i] = smallerNumber;  
            circleContainer[i] = smallerCircle;
            
            sequentialTransition.getChildren().addAll(swapBalls(circleContainer[i], circleContainer[index]));
        }
        
        sequentialTransition.setCycleCount(1);
        sequentialTransition.play();
    }    
    
    ParallelTransition swapBalls(Circle c1, Circle c2)
    {
        Bounds c1Bounds = c1.localToScene(c1.getBoundsInLocal());
        Bounds c2Bounds = c2.localToScene(c2.getBoundsInLocal());
        
        System.out.println(c1Bounds.toString());
        System.out.println(c2Bounds.toString());
        Path path = moveTo(c1Bounds, c2Bounds);//path used to move circle from initial postion to final position
        Path path2 = moveFrom(c2Bounds, c1Bounds);//path used to move cicle in final posion to inital position
        
        //Take circle1 and move it into circle2's position.
        final PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(2));
        pathTransition.setPath(path);
        pathTransition.setNode(c1);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        //pathTransition.play();
        
        //Take circle2 and move it into circle1's position.
        final PathTransition pathTransition2 = new PathTransition();
        pathTransition2.setDuration(Duration.seconds(2));
        pathTransition2.setPath(path2);
        pathTransition2.setNode(c2);
        pathTransition2.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition2.setCycleCount(1);
        pathTransition2.setAutoReverse(false);
        //pathTransition2.play();
        
        ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(pathTransition, pathTransition2);
       
        return pt;
    }
    
    
    //I personally would add inputs for initial position x, y, and ending position x, y.
    Path moveTo(Bounds c1Bounds, Bounds c2Bounds)
    {
        double c1CenterX = (c1Bounds.getMinX() + c1Bounds.getMaxX()) / 2;
        double c1CenterY = (c1Bounds.getMinY() + c1Bounds.getMaxY()) / 2;
        double c2CenterX = (c2Bounds.getMinX() + c2Bounds.getMaxX()) / 2;
        double c2CenterY = (c2Bounds.getMinY() + c2Bounds.getMaxY()) / 2;
        
        MoveTo moveTo = new MoveTo();
        moveTo.setX(c1CenterX);//initial x
        moveTo.setY(c1CenterY);//initial y

        CubicCurveTo cubicTo = new CubicCurveTo();
        cubicTo.setControlX1(c1CenterX);//initial x
        cubicTo.setControlY1(c1CenterY - 35);//height - set this. initial y - 35
        cubicTo.setControlX2(c2CenterX);//ending x
        cubicTo.setControlY2(c1CenterY - 35);//height - set this. initial y - 35
        cubicTo.setX(c2CenterX);//ending x
        cubicTo.setY(c2CenterY);//ending y
        
        Path path = new Path();
        path.getElements().add(moveTo);
        path.getElements().add(cubicTo);    
        
        return path;
    }
    
    Path moveFrom(Bounds c1Bounds, Bounds c2Bounds)
    {
        double c1CenterX = (c1Bounds.getMinX() + c1Bounds.getMaxX()) / 2;
        double c1CenterY = (c1Bounds.getMinY() + c1Bounds.getMaxY()) / 2;
        double c2CenterX = (c2Bounds.getMinX() + c2Bounds.getMaxX()) / 2;
        double c2CenterY = (c2Bounds.getMinY() + c2Bounds.getMaxY()) / 2;
        
        MoveTo moveTo = new MoveTo();
        moveTo.setX(c1CenterX);//initial x
        moveTo.setY(c1CenterY);//initial y

        CubicCurveTo cubicTo = new CubicCurveTo();
        cubicTo.setControlX1(c1CenterX);//initial x
        cubicTo.setControlY1(c1CenterY + 35);//height - set this. I did initial y + 35
        cubicTo.setControlX2(c2CenterX);//ending x
        cubicTo.setControlY2(c1CenterY + 35);//height - set this. I did initial y + 35
        cubicTo.setX(c2CenterX);//ending x
        cubicTo.setY(c2CenterY);//ending y
        
        Path path = new Path();
        path.getElements().add(moveTo);
        path.getElements().add(cubicTo);      
        
        return path;
    }    
}