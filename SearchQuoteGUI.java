
package investmentinterfacetrial;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;


/*
PROBLEMS:
1. Fix size of searchField TextField
2. Fix spacing of percent changes and "day" and "ytd"
*/

public class SearchQuoteGUI extends Application {
    
    String quote;
    
    public BorderPane startSQ(Stage stage) throws IOException {
       
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #000000");
        pane.setPadding(new Insets (5, 5, 5, 5));
        
        Text title = new Text("Quote Search");
        title.setStyle("-fx-font: normal 24px Helvetica, Helvetica, sans-serif");
        title.setFill(Color.WHITE);
        title.setUnderline(true);
        pane.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        
        //Outer grid houses all nodes
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 0, 0, 0));
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        
        //Inner grid houses day% and ytd%
        GridPane innerGrid = new GridPane();
        innerGrid.setHgap(50);
        
        //main grid goes in the center of BP
        pane.setCenter(grid);
        
        
        //HBOX TO HOLD SEARCH FIELD AND PIN BUTTON
        HBox h = new HBox();
        h.setPadding(new Insets(2, 10, 2, 10));
        h.setSpacing(80);
        grid.add(h, 0, 4);
        
        //Create TF to search for stock and put it at bottom of grid pane
        TextField searchField = new TextField();
        searchField.setMaxWidth(100);
        searchField.setMinHeight(30);
        searchField.setPadding(new Insets (5, 5, 5, 5));
        searchField.setStyle("-fx-background-color: #000000;"
                + "-fx-text-fill: white;"
                + "-fx-font-weight: bold;");
        searchField.setBorder(new Border(new BorderStroke(Color.GREY, 
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        
        
        //NEED TO FIX THE WIDTH
        h.getChildren().add(searchField);
        
        //Labels for each grid space
        final Text tickerText =  new Text();
        tickerText.setStyle("-fx-font: normal 28px Helvetica, Helvetica, sans-serif");
        tickerText.setFill(Color.WHITE);
        grid.add(tickerText, 0, 0);
        GridPane.setHalignment(tickerText, HPos.CENTER);
        
        final Text companyText = new Text();
        companyText.setStyle("-fx-font: normal 19px Helvetica, Helvetica, sans-serif");
        companyText.setFill(Color.WHITE);
        companyText.setWrappingWidth(250);
        companyText.setTextAlignment(TextAlignment.CENTER);
        grid.add(companyText, 0, 1);
        
        final Text priceText = new Text();
        priceText.setStyle("-fx-font: normal 38px Helvetica, Helvetica, sans-serif");
        priceText.setFill(Color.WHITE);
        StackPane priceStack = new StackPane();
        priceStack.getChildren().add(priceText);
        GridPane.setHalignment(priceText, HPos.CENTER);
        grid.add(priceStack, 0, 2);
        
        
        //Add innerGrid to main grid
        grid.add(innerGrid, 0, 3);
        GridPane.setHalignment(innerGrid, HPos.CENTER);
        
        //CHANGE % day and ytd
        Text dayChangeText = new Text();
        StackPane dayChange = new StackPane();
        dayChange.getChildren().add(dayChangeText);
        innerGrid.add(dayChange, 0, 0);
        dayChangeText.setTextAlignment(TextAlignment.CENTER);
        GridPane.setHalignment(dayChangeText, HPos.RIGHT);
       
        final Text dayPercent = new Text();
        dayPercent.setFill(Color.WHITE);
        innerGrid.add(dayPercent, 0, 1);
        dayPercent.setTextAlignment(TextAlignment.CENTER);
        //GridPane.setHalignment(dayPercent, HPos.RIGHT);
        
        Text ytdChangeText = new Text();
        StackPane ytdChange = new StackPane();
        ytdChange.getChildren().add(ytdChangeText);
        innerGrid.add(ytdChange, 1, 0);
        ytdChangeText.setTextAlignment(TextAlignment.CENTER);
        GridPane.setHalignment(ytdChangeText, HPos.RIGHT);
        
        final Text YTDPercent = new Text();
        YTDPercent.setFill(Color.WHITE);
        innerGrid.add(YTDPercent, 1, 1);
        YTDPercent.setTextAlignment(TextAlignment.CENTER);
        //GridPane.setHalignment(YTDPercent, HPos.RIGHT);

        
        //Add button and lambda functionality
        Button pin = new Button("Pin");
        pin.setMinSize(50, 30);
        h.getChildren().add(pin);
        
       
        
        //LAMBDA FOR SEARCH FIELD 
        //When ticker is entered into searchField
        searchField.setOnAction((ActionEvent) ->
        {
            tickerText.setText(searchField.getText().toUpperCase());
            
            try 
            {
                this.quote = searchField.getText();
                /*Scrape company name & price passing the searched ticker symbol
                and put in respective text objects*/
                companyText.setText(getCompanyName(searchField.getText()));
                priceText.setText(getPrice(searchField.getText()));
                
                /*Scrape day and ytd change and call setColor() to make green or
                red*/
                dayChangeText.setText(getDayChange(searchField.getText()));
                setColor(dayChangeText);
                ytdChangeText.setText(getYTDchange(searchField.getText()));
                setColor(ytdChangeText);
                
                //Method to continuously update price
                updatePrice(searchField.getText(), priceStack, dayChange, ytdChange);
                
                
                //Add strings to text to differentiate between day and ytd
                dayPercent.setText("Day");
                YTDPercent.setText("YTD");
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(SearchQuoteGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            //Clear searchField and focus curser in
            searchField.setText("");
            searchField.requestFocus(); //force curser to click into tf
        }); //eo lambda for text field
        
        
        //PIN BUTTON LAMBDA
        pin.setOnMousePressed((ActionEvent) -> 
        {
           try 
            {
                Interface.pinned.pin(quote);
                
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(SearchQuoteGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Make object of PinStock class for first element in Vbox here
        }); //eo pin button lambda
        
        
        Scene scene = new Scene(pane);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        
        return pane;
    }

    
    
    public String getCompanyName(String quote) throws IOException
    {
        QuoteReader q = new QuoteReader(quote);

        return q.companyName();
    }


    public String getPrice(String quote) throws IOException
    {
        QuoteReader q = new QuoteReader(quote);
        
        return "$" + q.currentQuote();
    }
    
    public void updatePrice (String quote, StackPane priceStack, 
            StackPane dayChange, StackPane ytdChange) throws IOException
    {
        
        Thread t;
        t = new Thread(() -> {
            
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
                        priceStack.getChildren().clear();
                        dayChange.getChildren().clear();
                        ytdChange.getChildren().clear();
                        
                        Text quoteText = new Text(getPrice(quote));
                        quoteText.setFill(Color.WHITE);
                        quoteText.setStyle("-fx-font: normal 38px Helvetica, Helvetica, sans-serif");
                        
                        Text dayCh = new Text();
                        dayCh.setText(getDayChange(quote));
                        setColor(dayCh);
                        
                        Text ytd = new Text();
                        ytd.setText(getYTDchange(quote));
                        setColor(ytd);
                        
                        priceStack.getChildren().add(quoteText);
                        dayChange.getChildren().add(dayCh);
                        ytdChange.getChildren().add(ytd);
                    } 
                    catch (IOException e) 
                    {
                        System.err.println("Thread interrupted OR couldn't refresh");
                    }
                       
                }); //eo Platform.runLater() lambda
            } //eo while loop    
        }); //eo thread lambda
        
        t.start();
        
    }
    
    public String getDayChange(String quote) throws IOException
    {
        QuoteReader q = new QuoteReader(quote);
        
        return q.currentDayChange();
    }

    public String getYTDchange(String quote) throws IOException
    {
        QuoteReader q = new QuoteReader(quote);
        
        return q.currentYTD() + "%";
    }

    public void setColor(Text t)
    {
        if(t.getText().startsWith("-"))
        {
            t.setFill(Color.RED);
        }
        else
        {
            t.setFill(Color.LIMEGREEN);
        }
        
        t.setStyle("-fx-font: bold 21px Helvetica, Helvetica, sans-serif");
    }
    
    public String getQuote()
    {
        return this.quote;
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("You're running the wrong file!");
    }

    
}