
package src;

import java.util.InputMismatchException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Ceagan
 */
public class DrawOnCanvas extends Application{

    private Canvas canvas = new Canvas(610, 590);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private int counter = 0; 
    private double radius = 0, x = 0, y = 0;
    private int red = 0, green = 0, blue = 0;

    private Color c = Color.BLACK;    
    
    private TextField[] RGB = new TextField[3], drawProperties = new TextField[3];
    private Label output = new Label();
    
    private ColorValidate cv = new ColorValidate(0, 0, 0);
    
    @Override
    public void start(Stage stage) throws Exception{
        //primary initializations and declarations for primary attributes and objects
        Pane root = new Pane();
        Scene scene = new Scene(root, 610, 750);

        stage.setScene(scene);
        
        stage.centerOnScreen();
        
        stage.setTitle("Circle Art");
        stage.show();
        //end of primary declarations, secondary coding can start below here
        
        
        RGB[0] = new TextField("0");
        RGB[1] = new TextField("0");
        RGB[2] = new TextField("0");
        for (TextField f: RGB){
            f.setLayoutY(630);
            f.setPrefColumnCount(2);
            f.setAlignment(Pos.CENTER); 
            f.setOnMouseClicked(this::clickRGB);
        }    
        //initilize then add to root afterwards
        RGB[0].setLayoutX(310);
        Label redText = new Label("Red");
        redText.setLayoutX(RGB[0].getLayoutX() - 30);
        redText.setLayoutY(RGB[0].getLayoutY() + 5);
        
        RGB[1].setLayoutX(RGB[0].getLayoutX() + 95);
        Label greenText = new Label("Green");
        greenText.setLayoutX(RGB[1].getLayoutX() - 45);
        greenText.setLayoutY(RGB[1].getLayoutY() + 5);
        
        RGB[2].setLayoutX(RGB[1].getLayoutX() + 85);
        Label blueText = new Label("Blue");
        blueText.setLayoutX(RGB[2].getLayoutX() - 35);
        blueText.setLayoutY(RGB[2].getLayoutY() + 5);
         
        
        drawProperties[0] = new TextField("0"); //size
        drawProperties[1] = new TextField("0"); //x location
        drawProperties[2] = new TextField("0"); //y location
        for (TextField p: drawProperties){
            p.setLayoutY(630);
            p.setPrefColumnCount(2);
            p.setAlignment(Pos.CENTER);
        }
        drawProperties[0].setLayoutX(60);
        drawProperties[1].setLayoutX(drawProperties[0].getLayoutX() + 70);
        drawProperties[2].setLayoutX(drawProperties[1].getLayoutX() + 70);
        
        
        Label[] drawText = new Label[3];
        drawText[0] = new Label("Radius");
        drawText[1] = new Label("X");
        drawText[2] = new Label("Y");
        for (int i = 0; i < drawProperties.length; i++){
            drawText[i].setLayoutY(635);
        }
        drawText[0].setLayoutX(drawProperties[0].getLayoutX() - 50);
        drawText[1].setLayoutX(drawProperties[1].getLayoutX() - 15);
        drawText[2].setLayoutX(drawProperties[2].getLayoutX() - 15);
        
        
        Label notice = new Label("After typing in an option press enter.");
        notice.setLayoutX(120);
        notice.setLayoutY(605);
        
        //error output form
        output.setLayoutX(0);
        output.setMinWidth(stage.getWidth());
        output.setMinHeight(50);
        output.setAlignment(Pos.TOP_CENTER);
        output.setLayoutY(668);
        output.setStyle("-fx-background-color: GREEN;");
        output.setFont(new Font("Gregoria", 15));
        output.setText("No Error Found");
        
        //button declarations
        Button drawIt = new Button("Draw");
        drawIt.setLayoutX(550);
        drawIt.setLayoutY(630);
        
        Button clear = new Button("Clear");
        clear.setLayoutX(550);
        clear.setLayoutY(595);
        
        Button reset = new Button("Reset");
        reset.setLayoutX(490);
        reset.setLayoutY(595);
        
        //add objects to the root
        root.getChildren().add(output);
        root.getChildren().add(notice);
        root.getChildren().add(redText);
        root.getChildren().add(greenText);
        root.getChildren().add(blueText);
        root.getChildren().add(drawIt);
        root.getChildren().add(clear);
        root.getChildren().add(reset);
        root.getChildren().add(canvas);
        
        for (TextField f: RGB){
            root.getChildren().add(f);
            f.setOnKeyReleased(this::keyCheck);
        }     
        for (TextField p: drawProperties){
            root.getChildren().add(p);
            p.setOnKeyReleased(this::keyCheck);
        }
        
        for (Label t: drawText){
            root.getChildren().add(t);
        }
        
        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(0, 0, 610, 590);
        
        //event handlers are implemented
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, this::click);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, this::release);
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::dragged);
        drawIt.setOnAction(this::draw);
        clear.setOnAction(this::clear);
        reset.setOnAction(this::reset);
        
        
        
        
    }
    
    public void keyCheck(KeyEvent ke){        
        for (TextField p: RGB){
            if (p.isFocused() && (p.getText().equals(""))) {
                p.setText("0");
                output.setText("You can't have empty values.");
            } else if (p.isFocused()){
                try {
                    Integer.valueOf(p.getText());
                } catch (NumberFormatException e){
                    p.setText("0");
                    output.setText("A letter or character has been found in the RGB values.");
                }
                red = Integer.valueOf(RGB[0].getText());
                green = Integer.valueOf(RGB[1].getText());
                blue = Integer.valueOf(RGB[2].getText());
                }
        }
        
        cv = new ColorValidate(red, green, blue); 
        c = cv.validateColor();   
        
        for (TextField d: drawProperties){
            if (d.isFocused() && d.getText().equals("")){
                d.setText("0");
                output.setText("You can't have empty values for radius or location.");
            } else if (d.isFocused()){
                radius = Integer.valueOf(drawProperties[0].getText());
                if (radius == 0){
                    radius = 15;
                }
                if (x > canvas.getWidth()){
                    x = canvas.getWidth();
                    x = Integer.valueOf(drawProperties[1].getText());
                    y = Integer.valueOf(drawProperties[2].getText());
                }
            }    
        }
        if (cv.getError().length() > 0){
            output.setText(cv.getError());
        }
    }
    
    public void clickRGB(MouseEvent me){   
        RGB[0].setText(String.valueOf(cv.getRed()));
        RGB[1].setText(String.valueOf(cv.getGreen()));
        RGB[2].setText(String.valueOf(cv.getBlue()));
        
        for (TextField t: RGB){
            if (t.isFocused()){
                t.selectAll();
            }
            try {
                Integer.valueOf(t.getText());
            } catch (NumberFormatException e){
                output.setText("Text was found not a number. We Fixed It :)");
                t.setText("0");
            }
        }
    }
    
    public void draw(ActionEvent ae){
        gc.fillOval(x, y, radius, radius);
    }
    
    public void clear(ActionEvent e){
        for(TextField f: RGB){
            f.setText("0");
        }
 
        for(TextField p: drawProperties){
            p.setText("0");
        }
    }
    
    public void reset(ActionEvent e){
        gc.setFill(Color.LIGHTGREY);
        gc.fillRect(0, 0, 620, 590);
    }
    
    public void click(MouseEvent me){
        RGB[0].setText(String.valueOf(cv.getRed()));
        RGB[1].setText(String.valueOf(cv.getGreen()));
        RGB[2].setText(String.valueOf(cv.getBlue()));
                
        gc.setStroke(c);
        gc.setFill(c);
                
        if (radius == 0){
            radius = 15;
        }
        
        if (me.getY() < 590){            
            gc.fillOval(me.getX() - (radius / 2), me.getY() - (radius / 2), radius, radius);
        }        
    }
    
    public void release(MouseEvent me){
        if (me.getY() < 590){            
            gc.fillOval(me.getX() - (radius / 2), me.getY() - (radius / 2), radius, radius);        }
    }
    
    
    public void dragged(MouseEvent me){
        if (me.getY() < 590){
            gc.fillOval(me.getX() - (radius / 2), me.getY() - (radius / 2), radius, radius);
        }
    }
    
    /**
     * Used instead of thread.sleep() handles possible errors.
     *
     * @param duration time in milliseconds
     */
    public static void pause(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException ex) {
        }
    }

    //ends program run duration. Kills all tasks
    @Override
    public void stop() {
        System.exit(0);
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
