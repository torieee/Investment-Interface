
package investmentinterfacetrial;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class PinStock extends Application {
    
    String quote;
    GridPane grid = new GridPane();
    
    //These facilitate moving between grid spaces
    int Qcount = 0;
    int newCount = 0;
    

    //StackPanes
    StackPane tickerStack;
    StackPane priceStack;
    StackPane changeStack;
    
    public BorderPane startPin(Stage primaryStage) throws IOException, InterruptedException {
        
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #000000");
        pane.setPadding(new Insets(5, 10, 5, 10));
        
        Text title = new Text("Pinned Quotes");
        title.setStyle("-fx-font: normal 24px Helvetica, Helvetica, sans-serif");
        title.setFill(Color.WHITE);
        title.setUnderline(true);
        pane.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        
        pane.setCenter(this.grid);
        BorderPane.setAlignment(this.grid, Pos.TOP_CENTER);
        
        //grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.setVgap(5);
        grid.setHgap(20);
        pane.setCenter(grid);
        
        //Program won't run with this
        //updatePrices(quote);
      
        Scene scene = new Scene(pane, 850, 350);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        return pane;
    }


    //FUNCTIONALITY METHODS
    public void pin(String quote) throws IOException
    {
        QuoteReader reader = new QuoteReader(quote);
        Text ticker = indexFont(quote);
        Text change = dailyChangeFont(reader.currentDayChange());
        Text price = priceFont(reader.currentQuote());
        
        tickerStack = new StackPane();
        tickerStack.getChildren().add(ticker);
        changeStack = new StackPane();
        changeStack.getChildren().add(change);
        priceStack = new StackPane();
        priceStack.getChildren().add(price);
        
        if(Qcount == 7)
        {
            Qcount = 0; //resets row positions once Qcount gets to column max of 7
        }
        
        if(newCount <= 6)
        {
            grid.add(tickerStack, 0, (2 * Qcount)); //Start (Q=0): C0, R0 ... When Q=1: C0, R2
            grid.add(changeStack, 1, (2 * Qcount)); //Start (Q=0): C1, R0 ... When Q=1: C1, R2
            grid.add(priceStack, 1, (1 + (2 * Qcount))); //Start (Q=0): C1, R1 ... When Q=1: C1, R3
        }
        else if(newCount > 6 && newCount <= 13)
        {
            grid.add(tickerStack, 2, (2 * Qcount));
            grid.add(changeStack, 3, (2 * Qcount));
            grid.add(priceStack, 3, (1 + (2 * Qcount)));
        }
        else if(newCount > 13 && newCount <= 20)
        {
            grid.add(tickerStack, 4, (2 * Qcount));
            grid.add(changeStack, 5, ((2 * Qcount)));
            grid.add(priceStack, 5, (1 + (2 * Qcount)));
        }
        else if(newCount > 20 && newCount <= 27)
        {
            grid.add(tickerStack, 6, (2 * Qcount));
            grid.add(changeStack, 7, ((2 * Qcount)));
            grid.add(priceStack, 7, (1 + (2 * Qcount)));
        }
        else
        {
            System.out.println("*********CANNOT PIN ANOTHER STOCK***********");
        }
        
        Qcount++;
        newCount++;
    }
    
    
    //THREAD TO UPDATE - but not using this rn 
    public void updatePrices(String quote) throws IOException, InterruptedException
    {

            while(true)
            {
                try 
                {
                    Thread.sleep(10000);
                } 
                catch (InterruptedException ex) 
                {
                    System.err.println("Thread interrupted!!!!");
                }
                
                Platform.runLater(() -> 
                {
                    try 
                    {
                        System.out.println("starting thread");
                        tickerStack.getChildren().clear();
                        priceStack.getChildren().clear();
                        changeStack.getChildren().clear();
                        
                        QuoteReader reader = new QuoteReader(quote);
                        Text ticker = indexFont(quote);
                        Text change = dailyChangeFont(reader.currentDayChange());
                        Text price = priceFont(reader.currentQuote());
                        tickerStack.getChildren().add(ticker);
                        changeStack.getChildren().add(change);
                        priceStack.getChildren().add(price);
                    } 
                    catch (IOException e) 
                    {
                        System.err.println("Thread interrupted OR couldn't refresh");
                    }
                       
                }); //eo Platform.runLater() lambda
            } //eo while loop    
        
    }
    
    
    //FORMATTING METHODS
    public Text indexFont(String str)
    {
        String s = str.toUpperCase();
        Text t = new Text(s);
        t.setFill(Color.WHITE);
        t.setStyle("-fx-font: normal 20px Helvetica, Helvetica, sans-serif");
        return t;
    }
    
    public Text priceFont(String s)
    {
        Text t = new Text(s);
        t.setFill(Color.WHITE);
        t.setStyle("-fx-font: normal 16px Helvetica, Helvetica, sans-serif");
        
        return t;
    }
    
    public Text dailyChangeFont(String t)
    {
        Text changeText = new Text(t);
        
        if(t.startsWith("-"))
        {
            changeText.setFill(Color.RED);
        }
        else
        {
            changeText.setFill(Color.LIMEGREEN);
        }
        
        changeText.setStyle("-fx-font: bold 21px Helvetica, Helvetica, sans-serif");
        
        return changeText;
    }
    
    
    
    

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        launch(args);
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("YOU RAN THE WRONG FILE!!!");
    }
    
}
