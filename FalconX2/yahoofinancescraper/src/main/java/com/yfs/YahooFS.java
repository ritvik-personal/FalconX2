package com.yfs;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class YahooFS {
    public static ArrayList<String> stocks = new ArrayList<String>(Arrays.asList("MSFT"));
    //"ADBE","SWI","GOOG","NTNX","MCHP","ZS","PANW","FDX","SBUX","ANET","IBM","ENPH","FLEX","CDNS","CTSH"));
    public static ArrayList<String> values = new ArrayList<String>();
    
    
    public static BigDecimal convertToBigDecimal(String valueStr) {
        BigDecimal multiplier = new BigDecimal(1.0);
        BigDecimal output;
        if (valueStr.endsWith("B")) {
            multiplier = new BigDecimal(1000000000.0); // 1 billion
        } else if (valueStr.endsWith("M")) {
            multiplier = new BigDecimal(1000000.0); // 1 million
        } else if (valueStr.endsWith("T")) {
            multiplier = new BigDecimal(1000000000000.0);
        }
        
        
        if (!multiplier.equals(new BigDecimal(1.0))) {
            String numericPart = valueStr.substring(0, valueStr.length() - 1);
            BigDecimal input = new BigDecimal(numericPart);
        output = input.multiply(multiplier);
        }
        else {
            output = new BigDecimal(valueStr);
        }
        return output;
    }

    public static String getAllInfo() {
        ArrayList<String> stock = new ArrayList<String>(stocks);
        String output = "";
        
        for (int i = 0; i < stock.size(); i++) {
            values = statisticsPage(stock.get(i));
            double stockPrice = getStockPrice(stock.get(i));
            double rsi = getRSI(stock.get(i));
            String rsiCat = rsiLogic(stock.get(i));
            BigDecimal pFCF = getPriceToFreeCashFlow(stock.get(i));
            String pFCFCat = priceToFCFLogic(stock.get(i));
            BigDecimal pTS = getPriceToSalesRatio(stock.get(i));

            String helper = "Stock: " + stock.get(i) + "\n" +
                            "Stock Price: " + stockPrice + "\n" +
                            "RSI: " + rsi + " " + rsiCat + "\n" +
                            "Price to FCF: " + pFCF + " " + pFCFCat + "\n" +
                            "Price to Sales: " + pTS + "\n\n";
    
            
            output = output.concat(helper);
        }
        
        return output;
    }
    public static double getStockPrice(String tick) {
        double finalOutput = 0;
       try {

        String url = ("https://finance.yahoo.com/quote/" + tick + "/history?p=" + tick);
        Document document = Jsoup.connect(url).get();
        Element stockPriceElement = document.selectFirst("td:nth-of-type(5)");
        if (stockPriceElement != null) {
            // Get the stock price value
            String stockPrice = stockPriceElement.text();

            // Output the stock price
            finalOutput = Double.parseDouble(stockPrice);
        } 
        else {
            System.out.println("Unable to fetch the stock price.");
        }
        return finalOutput;    
       }
       
       catch (IOException e) {
        e.printStackTrace();
        return 0;
        }
    }
    public static int getVolume(String tick) {
        int output = 0;
        try {
        String url = ("https://finance.yahoo.com/quote/" + tick + "/history?p=" + tick);
        Document document = Jsoup.connect(url).get();
        Element volumeTraded = document.selectFirst("td:nth-of-type(7)");
        if (volumeTraded != null) {
            // Get the stock price value
            String v = volumeTraded.text();
            String volumeT = v.replace(",","");
            output = Integer.parseInt(volumeT);
        } 
        else {
            System.out.println("Unable to fetch the volume traded.");
        }
        return output;
    } catch (IOException e) {
        e.printStackTrace();
        return 0;
        }
    }
    public static double getDailyOpen(String tick) {
        double finalOutput = 0;
        try {
 
         String url = ("https://finance.yahoo.com/quote/" + tick + "/history?p=" + tick);
         Document document = Jsoup.connect(url).get();
         Element stockPriceElement = document.selectFirst("td:nth-of-type(2)");
         if (stockPriceElement != null) {
             // Get the stock price value
             String stockPrice = stockPriceElement.text();
 
             // Output the stock price
             finalOutput = Double.parseDouble(stockPrice);
         } 
         else {
             System.out.println("Unable to fetch the stock price.");
         }
         return finalOutput;    
        }
        
        catch (IOException e) {
         e.printStackTrace();
         return 0;
         }
     }
    public static double getDailyHigh(String tick) {
        double finalOutput = 0;
        try {
 
         String url = ("https://finance.yahoo.com/quote/" + tick + "/history?p=" + tick);
         Document document = Jsoup.connect(url).get();
         Element stockPriceElement = document.selectFirst("td:nth-of-type(3)");
         if (stockPriceElement != null) {
             // Get the stock price value
             String stockPrice = stockPriceElement.text();
 
             // Output the stock price
             finalOutput = Double.parseDouble(stockPrice);
         } 
         else {
             System.out.println("Unable to fetch the stock price.");
         }
         return finalOutput;    
        }
        
        catch (IOException e) {
         e.printStackTrace();
         return 0;
         }
     }
    public static double getDailyLow(String tick) {
        double finalOutput = 0;
        try {
 
         String url = ("https://finance.yahoo.com/quote/" + tick + "/history?p=" + tick);
         Document document = Jsoup.connect(url).get();
         Element stockPriceElement = document.selectFirst("td:nth-of-type(4)");
         if (stockPriceElement != null) {
             // Get the stock price value
             String stockPrice = stockPriceElement.text();
 
             // Output the stock price
             finalOutput = Double.parseDouble(stockPrice);
         } 
         else {
             System.out.println("Unable to fetch the stock price.");
         }
         return finalOutput;    
        }
        
        catch (IOException e) {
         e.printStackTrace();
         return 0;
         }
     }
    public static double getDailyClose(String tick) {
        double finalOutput = 0;
        try {
 
         String url = ("https://finance.yahoo.com/quote/" + tick + "/history?p=" + tick);
         Document document = Jsoup.connect(url).get();
         Element stockPriceElement = document.selectFirst("td:nth-of-type(5)");
         if (stockPriceElement != null) {
             // Get the stock price value
             String stockPrice = stockPriceElement.text();
 
             // Output the stock price
             finalOutput = Double.parseDouble(stockPrice);
         } 
         else {
             System.out.println("Unable to fetch the stock price.");
         }
         return finalOutput;    
        }
        
        catch (IOException e) {
         e.printStackTrace();
         return 0;
         }
     }
    public static void printDailies(String tick) {
        System.out.println("Today's open value is " + getDailyOpen(tick));
        System.out.println("Today's high value is " + getDailyHigh(tick));
        System.out.println("Today's low value is " + getDailyLow(tick));
    }

    public static double fiftyTwoWeekRange(String tick, boolean x) {
        double finalOutput = 0;
        String helper = "";
        try{
            String url = "https://finance.yahoo.com/quote/" + tick;
            Document document = Jsoup.connect(url).get();
            Elements fiftyTwoWeekHE = document.getElementsByAttributeValue("data-test","FIFTY_TWO_WK_RANGE-value");
            for (Element i: fiftyTwoWeekHE) {
                helper = i.text();
            }
            String[] values = helper.split(" ");
            

            if (x==true) {
                finalOutput = Double.parseDouble(values[0]);
            }
            else {
                finalOutput = Double.parseDouble(values[2]);
                
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return finalOutput;
    }
    public static double getFiftyTwoWkLow(String tick) {
        double output = fiftyTwoWeekRange(tick, true);
        return output;
    } 
    public static double getFiftyTwoWkHigh(String tick) {
        double output = fiftyTwoWeekRange(tick, false);
        return output;
    }
    public static double getBeta(String tick) {
        double finalOutput = 0;
        String helper = "";
        try{
            String url = "https://finance.yahoo.com/quote/" + tick;
            Document document = Jsoup.connect(url).get();
            Elements beta = document.getElementsByAttributeValue("data-test","BETA_5Y-value");
            for (Element i : beta) {
                helper = i.text();
            }
            finalOutput = Double.parseDouble(helper);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return finalOutput;
    }
    public static double getPERatio(String tick) {
        double finalOutput = 0;
        String helper = "";
        try{
            String url = "https://finance.yahoo.com/quote/" + tick;
            Document document = Jsoup.connect(url).get();
            Elements beta = document.getElementsByAttributeValue("data-test","PE_RATIO-value");
            for (Element i : beta) {
                helper = i.text();
            }
            finalOutput = Double.parseDouble(helper);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return finalOutput;
    }
    public static double getEPS(String tick) {
        double finalOutput = 0;
        String helper = "";
        try{
            String url = "https://finance.yahoo.com/quote/" + tick;
            Document document = Jsoup.connect(url).get();
            Elements beta = document.getElementsByAttributeValue("data-test","EPS_RATIO-value");
            for (Element i : beta) {
                helper = i.text();
            }
            finalOutput = Double.parseDouble(helper);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return finalOutput;
    }
    public static BigDecimal getMarketCap(String tick) {
        BigDecimal a = BigDecimal.valueOf(0.0);
        
        String helper = "";
        try{
            String url = "https://finance.yahoo.com/quote/" + tick;
            Document document = Jsoup.connect(url).get();
            Elements beta = document.getElementsByAttributeValue("data-test","MARKET_CAP-value");
            for (Element i : beta) {
                helper = i.text();
            }

            a = convertToBigDecimal(helper);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return a;
    }
   
    public static BigDecimal getLeveredFCF(String tick) {
        BigDecimal finalOutput = new BigDecimal(0.0);
        int i = 50;
        //ArrayList<String> array = statisticsPage(tick);
        String helper = values.get(i);
        
        finalOutput = convertToBigDecimal(helper);
        

        return finalOutput;
    }
    public static BigDecimal getOperatingCashFlow(String tick) {
        BigDecimal finalOutput = new BigDecimal(0.0);
        int i = 49;
        //ArrayList<String> array = statisticsPage(tick);
        String helper = values.get(i);
        finalOutput = convertToBigDecimal(helper);
        

        return finalOutput;
    }
    public static BigDecimal getCashPerShare(String tick) {
        BigDecimal finalOutput = new BigDecimal(0.0);
        int i = 44;
        //ArrayList<String> array = statisticsPage(tick);
        String helper = values.get(i);
        
        finalOutput = convertToBigDecimal(helper);
        

        return finalOutput;
    }
    public static BigDecimal getGrossProfit(String tick) {
        BigDecimal finalOutput = new BigDecimal(0.0);
        int i = 38;
        //ArrayList<String> array = statisticsPage(tick);
        String helper = values.get(i);
        
        finalOutput = convertToBigDecimal(helper);
        

        return finalOutput;
    }
    public static BigDecimal getRevenuePerShare(String tick) {
        BigDecimal finalOutput = new BigDecimal(0.0);
        int i = 36;
        //(tick);
        String helper = values.get(i);
        
        finalOutput = convertToBigDecimal(helper);
        

        return finalOutput;
    }
    public static BigDecimal getRevenue(String tick) {
        BigDecimal finalOutput = new BigDecimal(0.0);
        int i = 35;
        //ArrayList<String> array = statisticsPage(tick);
        String helper = values.get(i);
        
        finalOutput = convertToBigDecimal(helper);
        

        return finalOutput;
    }
    public static BigDecimal getSharesOutstanding(String tick) {
        BigDecimal finalOutput = new BigDecimal(0.0);
        int i = 9;
        //ArrayList<String> array = statisticsPage(tick);
        if (values.size() > 0 ) {
            String helper = values.get(i);
            finalOutput = convertToBigDecimal(helper);
        }

        return finalOutput;
    }
    
    public static String getReturnOnAssets(String tick) {
        int i = 33;
        //ArrayList<String> array = statisticsPage(tick);
        String helper = values.get(i);
            return helper;
        }
    public static String getReturnOnEquity(String tick) {
        int i = 34;
        ArrayList<String> array = statisticsPage(tick);
        String helper = values.get(i);
            return helper;
        }
    
    public static ArrayList<String> statisticsPage(String tick) {
        ArrayList<String> array = new ArrayList<String>();
        String url = "https://finance.yahoo.com/quote/"+tick+"/key-statistics?p=" +tick;
        try { Document document = Jsoup.connect(url).get();
            Elements beta = document.getElementsByClass("Fw(500) Ta(end) Pstart(10px) Miw(60px)");
            for (Element i : beta) {
                array.add(i.text());
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return array;
    }


    //SIGNALS

    public static BigDecimal getPriceToSalesRatio(String tick) {
        BigDecimal marketCap = getMarketCap(tick);
        BigDecimal revenue = getRevenue(tick);
        MathContext m = new MathContext(3);
        BigDecimal output;

        // Handle division by zero or near-zero revenue
        if (revenue.compareTo(BigDecimal.ZERO) <= 0) {
            output = BigDecimal.ZERO; // Avoid division issues
        } else {
            output = marketCap.divide(revenue, m);
        }

        return output;
    }
   
    public static BigDecimal getPriceToFreeCashFlow(String tick) {
        double stockPrice = getStockPrice(tick);
        BigDecimal sP = BigDecimal.valueOf(stockPrice);
        BigDecimal cashFlow = getLeveredFCF(tick);
        BigDecimal sharesOutstanding = getSharesOutstanding(tick);
        MathContext m = new MathContext(3);
        
        if (sharesOutstanding.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO; // Avoid division issues
        }

        BigDecimal helper = cashFlow.divide(sharesOutstanding, m);
        
        if (helper.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO; // Avoid division issues
        }
        
        BigDecimal output = sP.divide(helper, m);
        return output;
    }
    
    public static double calculateRSI(ArrayList<Double> closingPrices, int period) {
        double avgGain = 0;
        double avgLoss = 0;

        for (int i = 1; i < period; i++) {
            double diff = closingPrices.get(i) - closingPrices.get(i - 1);
            if (diff > 0) {
                avgGain += diff;
            } else {
                avgLoss -= diff;
            }
        }

        avgGain /= period;
        avgLoss /= period;

        // Calculate RSI
        double rs = Math.abs(avgGain / avgLoss);
        return 100 - (100 / (1 + rs));
    }
    public static double getRSI(String tick) {
        double finalOutput = 0.0;
        double helper = 0.0;
        ArrayList<Double> cP = new ArrayList<Double>();
        int whileLoop = 0;
        try {
 
            String url = ("https://finance.yahoo.com/quote/" + tick + "/history?p=" + tick);
            Document document = Jsoup.connect(url).get();
            Elements stockPriceElements = document.select("td:nth-of-type(5)");
                for(Element i: stockPriceElements) {
                    if (whileLoop >19) {
                        break;
                    } else {
                    // Get the stock price value
                        String stockPrice = i.text();
                        helper = Double.parseDouble(stockPrice);
                        cP.add(0,helper);
                        whileLoop++;
                    }
                }
            
            finalOutput = calculateRSI(cP, 20);
            return finalOutput;    
           }
           
           catch (IOException e) {
            e.printStackTrace();
            return 0;
            }
    }
    
    public static double[] calculateBollingerBands(ArrayList<Double> closingPrices, int period, int multiplier) {
        double[] bands = new double[3];
        double[] deviations = new double[closingPrices.size() - period + 1];

        // Calculate simple moving average (SMA) over the given period
        double sma = calculateSMA(closingPrices, period);

        // Calculate squared deviations
        for (int i = period - 1; i < closingPrices.size(); i++) {
            double sum = 0;
            for (int j = i - period + 1; j <= i; j++) {
                sum += Math.pow(closingPrices.get(j) - sma, 2);
            }
            deviations[i - period + 1] = Math.sqrt(sum / period);
        }

        // Calculate Bollinger Bands
        bands[1] = sma;
        bands[0] = sma - (multiplier * deviations[deviations.length - 1]);
        bands[2] = sma + (multiplier * deviations[deviations.length - 1]);

        return bands;
    }
    public static double calculateSMA(ArrayList<Double> closingPrices, int period) {
        double sum = 0;
        for (int i = closingPrices.size() - period; i < closingPrices.size(); i++) {
            sum += closingPrices.get(i);
        }
        return sum / period;
    }
    public static void getBollingerBands(String tick) {
        double helper = 0.0;
        ArrayList<Double> cP = new ArrayList<Double>();
        int whileLoop = 0;
        try {
 
            String url = ("https://finance.yahoo.com/quote/" + tick + "/history?p=" + tick);
            Document document = Jsoup.connect(url).get();
            Elements stockPriceElements = document.select("td:nth-of-type(5)");
                for(Element i: stockPriceElements) {
                    if (whileLoop >19) {
                        break;
                    } else {
                    // Get the stock price value
                        String stockPrice = i.text();
                        helper = Double.parseDouble(stockPrice);
                        cP.add(0,helper);
                        whileLoop++;
                    }
                }
            double[] bollingerBands = calculateBollingerBands(cP, 20,2);
            for (double i: bollingerBands) {
                System.out.println(i + " ");
            } 
           }
           
           catch (IOException e) {
            e.printStackTrace();

            }
    }

    public static String rsiLogic(String tick) {
        double value = getRSI(tick);
        String output = "";
        if (value <=40) {
            output = "green";
        }
        if (value > 40 & value <= 50) {
            output = "yellow";
        }
        else {
            output = "red";
        }
        return output;

    }
    public static String reportRSILogic(String tick) {
        String output = rsiLogic(tick);
        return output;
    }

    public static String priceToFCFLogic(String tick) {
        String output = "";
        BigDecimal value = getPriceToFreeCashFlow(tick);
        BigDecimal b1 = new BigDecimal(10.0);
        BigDecimal b3 = new BigDecimal(20.0);
        if (value.compareTo(b1) != 1) {
            output = "green";
        }
        if (value.compareTo(b1) == 1 & (value.compareTo(b3)!= 1)) {
            output = "yellow";
        }
        else {
            output = "red";
        }
        return output;
        
    }
    public static void main(String[] args) {
        System.out.println(rsiLogic("MSFT"));
        
        
       
    }
}