package sample;

import javafx.animation.RotateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * Created by thisum_kankanamge on 15/9/18.
 */
public class DrawShape extends Pane implements EventHandler
{
    private Button upButton;
    private Button downButton;
    private Button leftButton;
    private Button rightButton;
    private RotateTransition rotateTransition;
    private Box box;
    private Label lbl1;
    private Label lbl2;
    private Label lbl3;
    private Label lbl4;
    private Label lbl5;
    private Label lbl6;
    private Label lbl7;

    public DrawShape()
    {
        lbl1 = new Label("A");
        lbl2 = new Label("B");
        lbl3 = new Label("C");
        lbl4 = new Label("D");
        lbl5 = new Label("E");
        lbl6 = new Label("F");
        lbl7 = new Label("G");

        lbl1.setPrefSize(40, 40);
        lbl2.setPrefSize(40, 40);
        lbl3.setPrefSize(40, 40);
        lbl4.setPrefSize(40, 40);
        lbl5.setPrefSize(40, 40);
        lbl6.setPrefSize(40, 40);
        lbl7.setPrefSize(40, 40);
    }

    public GridPane getScreen()
    {
        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-image: url('file:/Users/thisum/Documents/AppDevelopment/Java/Yilei/ObjectRotation/img/Stick-man-blue.png'); -fx-background-repeat: no-repeat; -fx-border-color: black ");
        pane.setPadding(new Insets(20));

        Label empty1 = new Label();
        empty1.setPrefWidth(120);

        Label empty2 = new Label();
        empty2.setPrefWidth(125);

        Label empty3 = new Label();
        empty3.setPrefHeight(110);

        Label empty4 = new Label();
        empty4.setPrefHeight(40);

        Label empty5 = new Label();
        empty5.setPrefHeight(40);

        Label empty6 = new Label();
        empty6.setPrefHeight(190);

        pane.add(empty1, 0, 0);
        pane.add(lbl1, 1, 0);
        pane.add(empty3, 2, 1);
        pane.add(lbl2, 1, 2);
        pane.add(empty4, 1, 3);
        pane.add(lbl3, 0, 4);
        pane.add(empty2, 1, 4);
        pane.add(lbl4, 2, 4);
        pane.add(empty5, 1, 5);
        pane.add(lbl5, 1, 6);
        pane.add(empty6, 1, 7);
        pane.add(lbl6, 0, 8);
        pane.add(lbl7, 2, 8);

        return pane;
    }

    @Override
    public void handle(Event event)
    {

    }
}
