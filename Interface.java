
package investmentinterfacetrial;

import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Interface extends Application {
    
    static PinStock pinned; 
    
    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        
        //Objects for different sections of the interface
        SearchQuoteGUI searchQuote = new SearchQuoteGUI();
        IndexTicker indexTicker = new IndexTicker();
        InvCalcFX invCalc = new InvCalcFX();
        pinned = new PinStock();
        
        //LARGE BORDER PANE CONTAINS THAT CONTAINS INNER CONTAINERS
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #000000");
        
        //VBOX TO CONTAIN TOP AND BOTTOM
        VBox v = new VBox();
        
        
        //IndexTicker section
        BorderPane indexPane = new BorderPane();
        indexPane.setCenter(indexTicker.startIndexTicker(primaryStage));
        indexPane.setPadding(new Insets(10, 10, 10, 10));
          
        //SearchQuote Section
        BorderPane SQpane = new BorderPane();
        SQpane.setTop(searchQuote.startSQ(primaryStage));
        SQpane.setPadding(new Insets(10, 10, 10, 10));

        //Investment Calc section
        BorderPane investmentCalcPane = new BorderPane();
        investmentCalcPane.setTop(invCalc.startInvCalc(primaryStage));

        //HBOX to hold 3 small apps in a row at TOP CENTER of OUTER BP
        HBox h = new HBox();
        h.setPadding(new Insets(10, 10, 10, 10));
        h.getChildren().add(indexPane);
        h.getChildren().add(SQpane);
        h.getChildren().add(investmentCalcPane);
        //Add hbox to vbox
        v.getChildren().add(h);
       
        
        /*
                END OF TOP HBOX SECTION
        */
        
        /*
                START OF BOTTOM PINNED STOCK SECTION
        */
        BorderPane pinnedPane = new BorderPane();
        pinnedPane.setCenter(pinned.startPin(primaryStage));
        v.getChildren().add(pinnedPane);

        pane.setCenter(v);
        BorderPane.setAlignment(v, Pos.CENTER);
        //pane.getChildren().add(pinnedPane);
        //BorderPane.setAlignment(pinnedPane, Pos.BOTTOM_CENTER);
        //pane.setBottom(pinnedPane);
        
        //SCENE STUFF
        Scene scene = new Scene(pane, 900, 700);
        primaryStage.setTitle("Inv Interface");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
