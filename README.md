# Investment-Interface

# About Investment Interface
GUI investing tool that allows the user to view updating US market index prices, calculate future returns, search specific stock prices, and pin stocks to a favorites board. 

# Functionality
Interface.java is the main class that contains the start() method. QuoteReader.java uses the Jsoup API to scrape stock data from the CNBC website, and the remaining classes create the Index Ticker, Quote Search tool, Investment Calculator tool, and the Pinned Stock functionality. 

The market indices and the user's chosen quote inputted into the Quote Search tool update prices continuously to reflect current market data. The user may pin up to 28 quotes.
