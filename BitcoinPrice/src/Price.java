import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Price {

    private static HttpURLConnection connection;

    public void getCurrentPrice() {
        BufferedReader reader;
        String line;
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL("https://api.coindesk.com/v1/bpi/currentprice.json");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            int connectionStatus = connection.getResponseCode();
//            System.out.println(status);
            if (connectionStatus > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            }
            System.out.println(response.toString());
            ObjectMapper o = new ObjectMapper();
            String json = response.toString();
            Data data = o.readValue(json, Data.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

// date must be in format yyyy-mm-dd
    public void getPriceAtDate(String startDate, String endDate) {

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        try {
            URL url = new URL("https://api.coindesk.com/v1/bpi/historical/close.json?start="+startDate+"&end="+endDate);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            int status = connection.getResponseCode();
//            System.out.println(status);
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            System.out.println(responseContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    class JsonString{
        Data data;
    }

    class Data {
        String base;
        String currency;
        Double amount;
    }


    public double bitcoinAtTimeOfInvestment(double priceOfBitcoin, double investment){
        priceOfBitcoin=0;
        investment=0;
        return priceOfBitcoin/investment;

    }

    public double currentPriceOfInvestment(double bitcoinOwned, double currentPrice){
        return bitcoinOwned * currentPrice;
    }




}