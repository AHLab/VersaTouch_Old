package sample;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by thisum_kankanamge on 15/9/18.
 */
public class SimpleRotations extends Application implements EventHandler
{
    private Button upButton;
    private Button downButton;
    private Button leftButton;
    private Button rightButton;
    private RotateTransition rotateTransition;
    private Box box;
    private Pane basePane;

    public Pane getScreen()
    {
        basePane = new Pane();

        GridPane gridPane = new GridPane();

        GridPane controldGrid = new GridPane();
        upButton = new Button("up");
        downButton = new Button("down");
        leftButton = new Button("left");
        rightButton = new Button("right");
        upButton.setPrefWidth(70);
        downButton.setPrefWidth(70);
        leftButton.setPrefWidth(70);
        rightButton.setPrefWidth(70);

        upButton.setOnAction(this);
        downButton.setOnAction(this);
        leftButton.setOnAction(this);
        rightButton.setOnAction(this);

        Slider zoomSlider = new Slider(0.2, 1.2, 0.7);
        Slider dragSlider = new Slider(1, 500, 350);

        Pane spring = new Pane();
        spring.minHeightProperty().bind(upButton.heightProperty());

        controldGrid.add(upButton, 1, 0 );
        controldGrid.add(leftButton, 0, 1 );
        controldGrid.add(rightButton, 2, 1 );
        controldGrid.add(downButton, 1, 2 );
        controldGrid.add(spring, 0, 3 );
        controldGrid.add(new Label("zoom"), 0, 4);
        controldGrid.add(zoomSlider, 0, 5, 3, 1 );
        controldGrid.add(new Label("dragSlider"), 0, 6);
        controldGrid.add(dragSlider, 0, 7, 3, 1 );

        //Drawing a Box
        box = new Box();

        //Setting the properties of the Box
        box.setWidth(250.0);
        box.setHeight(400.0);
        box.setDepth(100.0);

        //Setting the position of the box
        box.setTranslateX(100);
        box.setTranslateY(100);
        box.setTranslateZ(50);

        //Setting the material of the box
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.DARKSLATEBLUE);
        material.setSpecularColor(Color.DARKSLATEBLUE);

        //Setting the diffuse color material to box
        box.setMaterial(material);

        //Setting the rotation animation to the box
        rotateTransition = new RotateTransition();

        rotateTransition.setDuration(Duration.millis(500));
        rotateTransition.setNode(box);
        rotateTransition.setAxis(Rotate.Y_AXIS);
        rotateTransition.setByAngle(360);

        //Setting auto reverse value to false
        rotateTransition.setAutoReverse(false);


        //Handling the mouse clicked event(on box)
        EventHandler<MouseEvent> eventHandlerBox =
                new EventHandler<javafx.scene.input.MouseEvent>() {

                    @Override
                    public void handle(javafx.scene.input.MouseEvent e) {
                        rotateTransition.stop();
                    }
                };
        //Adding the event handler to the box
        box.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, eventHandlerBox);

        ZoomingPane zoomingPane = new ZoomingPane(box);
        zoomingPane.zoomFactorProperty().bind(zoomSlider.valueProperty());
        gridPane.add(controldGrid, 0, 1, 1, 1);
        gridPane.add(zoomingPane, 1, 1, 1, 1);

        dragSlider.valueProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                box.setTranslateX(newValue.doubleValue());
            }
        });

        gridPane.setPadding(new Insets(20));
        basePane.getChildren().add(gridPane);

        return basePane;
    }

    @Override
    public void handle(Event event)
    {
        if(event.getSource() == upButton)
        {
            changeRotation(Rotate.X_AXIS, -360);
        }
        else if(event.getSource() == downButton)
        {
            changeRotation(Rotate.X_AXIS, 360);
        }
        else if(event.getSource() == leftButton)
        {
            changeRotation(Rotate.Y_AXIS, 360);
        }
        else if( event.getSource() == rightButton )
        {
            changeRotation(Rotate.Y_AXIS, -360);
        }
    }

    private void changeRotation(final Point3D axis, final double angle)
    {
        Platform.runLater(() ->
                          {
                              rotateTransition.stop();
                              rotateTransition.setCycleCount(1);
                              rotateTransition.setAxis(axis);
                              rotateTransition.setByAngle(angle);
                              rotateTransition.play();
                          });
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Pane pane = getScreen();

        //Creating a scene object
        Scene scene = new Scene(pane, 1200, 900);

        //Setting camera
        PerspectiveCamera camera = new PerspectiveCamera(false);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(0);
        scene.setCamera(camera);

        //Setting title to the Stage
        primaryStage.setTitle("Object modeling");

        //Adding scene to the stage
        primaryStage.setScene(scene);

        //Displaying the contents of the stage
        primaryStage.show();
    }

    private class ZoomingPane extends Pane
    {
        Node content;
        private DoubleProperty zoomFactor = new SimpleDoubleProperty(1);

        private ZoomingPane(Node content) {
            this.content = content;
            getChildren().add(content);
            Scale scale = new Scale(1, 1);
            content.getTransforms().add(scale);

            zoomFactor.addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    scale.setX(newValue.doubleValue());
                    scale.setY(newValue.doubleValue());
                    requestLayout();
                }
            });
        }

        protected void layoutChildren() {
            Pos pos = Pos.TOP_LEFT;
            double width = getWidth();
            double height = getHeight();
            double top = getInsets().getTop();
            double right = getInsets().getRight();
            double left = getInsets().getLeft();
            double bottom = getInsets().getBottom();
            double contentWidth = (width - left - right)/zoomFactor.get();
            double contentHeight = (height - top - bottom)/zoomFactor.get();
            layoutInArea(content, left, top,
                         contentWidth, contentHeight,
                         0, null,
                         pos.getHpos(),
                         pos.getVpos());
        }

        public final Double getZoomFactor() {
            return zoomFactor.get();
        }
        public final void setZoomFactor(Double zoomFactor) {
            this.zoomFactor.set(zoomFactor);
        }
        public final DoubleProperty zoomFactorProperty() {
            return zoomFactor;
        }
    }
}
