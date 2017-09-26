import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;

public class ConnectWithServer {

    private String query = null;
    private HttpURLConnection httpURLConnection = null;
    private Double rateToFrame;
    private String line;

    public void connect() throws Exception{
        try {
            URL url = new URL(query);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(250);
            httpURLConnection.setReadTimeout(250);
            httpURLConnection.connect();

            if(HttpURLConnection.HTTP_OK == httpURLConnection.getResponseCode()){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                line = null;
                StringBuilder stringBuilder = new StringBuilder();
                while((line = bufferedReader.readLine()) !=null){
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }

                Gson gson = new GsonBuilder().
                        registerTypeAdapter(RateObject.class, new RatesDeserializer()).
                        create();
                DateFromServer dateFromServer = gson.fromJson(stringBuilder.toString(), DateFromServer.class);
                rateToFrame =  dateFromServer.getRates().getRate();

            }else{
                System.out.println("сервер не отвечает");
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            if(httpURLConnection != null){
                httpURLConnection.disconnect();
            }
        }
}
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    public Double getRateToFrame() {
        return rateToFrame;
    }
}
