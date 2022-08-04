
package investmentinterfacetrial;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class InvCalcFX extends Application {
    

    public BorderPane startInvCalc(Stage stage) {
        //Pane and boxes
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets (10, 10, 10, 10));
        pane.setStyle("-fx-background-color: #000000");
        
        VBox v = new VBox();
        GridPane gpane = new GridPane();
        gpane.setPadding(new Insets (10, 10, 10, 10));
        gpane.setHgap(10);
        gpane.setVgap(10);
        HBox h = new HBox();
        
        //Title in VBox
        Text title = new Text("Investment Calculator");
        title.setStyle("-fx-font: normal 24px Helvetica, Helvetica, sans-serif");
        title.setFill(Color.WHITE);
        title.setUnderline(true);
        
        //VBox Parameters
        v.setAlignment(Pos.CENTER);
        v.setPadding(new Insets(5, 10, 0, 10));
        v.setSpacing(15);
        v.getChildren().add(title);
        
        //BEGINNING AMT SETUP AND LAMBDA
        Text startamt = new Text("Beginning Amount: $");
        startamt.setStyle("-fx-font: normal 16px Helvetica, Helvetica, sans-serif");
        startamt.setFill(Color.WHITE);
        gpane.add(startamt, 0, 0);
        
        TextField start = new TextField();
        tfProperties(start);
        
        gpane.add(start, 1, 0); 
        
        
        //MONTHLY CONTRIB SETUP AND LAMBDA
        Text monthlycont = new Text("Monthly Contribution: $");
        monthlycont.setStyle("-fx-font: normal 16px Helvetica, Helvetica, sans-serif");
        monthlycont.setFill(Color.WHITE);
        gpane.add(monthlycont, 0, 1);
        
        TextField monthly = new TextField();
        tfProperties(monthly);
        gpane.add(monthly, 1, 1);
        
        //YEARS SETUP AND LAMBDA
        Text yearsInv = new Text("Length of Investment: ");
        yearsInv.setStyle("-fx-font: normal 16px Helvetica, Helvetica, sans-serif");
        yearsInv.setFill(Color.WHITE);
        gpane.add(yearsInv, 0, 2);
        
        TextField years = new TextField();
        tfProperties(years);
        gpane.add(years, 1, 2);
        
        //ROR SETUP AND LAMBDA
        Text ror = new Text("Rate of Return: ");
        ror.setStyle("-fx-font: normal 16px Helvetica, Helvetica, sans-serif");
        ror.setFill(Color.WHITE);
        gpane.add(ror, 0, 3);
        
        TextField rate = new TextField();
        tfProperties(rate);
        gpane.add(rate, 1, 3);
        
        //HBox for calculate button, tf, and reset buttons
        Button calc = new Button();
        calc.setText("Calculate");
        Button reset = new Button();
        reset.setText("Reset");
        
        //Button properties
        calc.setMaxHeight(20);
        calc.setMaxWidth(100);
        calc.setMinWidth(75);
        
        reset.setMinHeight(20);
        reset.setMinWidth(75);
        
        TextField finalAmt = new TextField();
        tfProperties(finalAmt);
        finalAmt.setEditable(false);
        //finalAmt.setMinSize(100, 30);
        
        //Calculate button action (lambda)
        calc.setOnMouseClicked(e -> {
            double b = parseAmt(start);
            double m = parseAmt(monthly);
            double y = parseAmt(years);
            double r = parseAmt(rate) / 100;
            finalAmt.setText(String.format("$%.2f", b * Math.pow(1 + r, y)));
        });
        
        //Reset button action (lambda)
        reset.setOnMouseClicked(e -> {
             start.setText("");
             monthly.setText("");
             years.setText("");
             rate.setText("");
        });
        
        
        //VBox Parameters
        h.setAlignment(Pos.CENTER);
        h.setSpacing(10);
        h.setPadding(new Insets(10, 10, 10, 10));
        h.getChildren().addAll(calc, finalAmt, reset);
        
        
        //Adding box elements to pane
        pane.setTop(v);
        pane.setCenter(gpane);
        pane.setBottom(h);
        
        //Scene and contents of scene
        Scene scene = new Scene(pane);
        stage.setTitle("Investment Calculator");
        stage.setScene(scene);
        stage.show();
        
        return pane;
    }
    
    private static double parseAmt (TextField t) 
    {
      return Double.parseDouble(t.getText());
    }
    
    public void tfProperties (TextField t)
    {
        t.setMinWidth(100);
        t.setMaxHeight(30);
        t.setPadding(new Insets (10, 10, 10, 10));
        t.setAlignment(Pos.CENTER);
        t.setStyle("-fx-background-color: #000000;"
                + "-fx-text-fill: white;"
                + "-fx-font-weight: bold;");
        t.setBorder(new Border(new BorderStroke(Color.GREY, 
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }
    
    public void buttonColor (Button b)
    {
        b.setStyle("-fx-background-color : #e2d9ce");
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("You're running the wrong file!");
    }
    
}
