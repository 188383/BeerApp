package project.pwr.database;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import android.content.Context;
import android.net.*;

/**
 * Created by pawel on 24.05.15.
 */
public class DataProc {

    public String readValues(InputStream is)throws Exception{
        BufferedReader scan = new BufferedReader(new InputStreamReader(is));
        String input;
        String buffer = "";
        if(scan.ready()){
            while((input=scan.readLine())!=null){
                buffer = buffer.concat(input);
            }
        }
        return buffer;
    }

    public String buildPost(String... vals) throws Exception{
        JSONObject object = new JSONObject();
        int action = Integer.parseInt(vals[0]);
        object.put("action",action);
        object.put("username",vals[1]);
        object.put("email",vals[2]);
        return object.toString();
    }

    public String postData(String post) throws Exception{
        InputStream is = null;
        OutputStream os =null;
        String answer = null;



        URL newURL = new URL("http://192.168.1.11:8080/BeerServer/list");
        HttpURLConnection connection = (HttpURLConnection)newURL.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.connect();

        String query = "data="+post;
        os = connection.getOutputStream();
        os.write(query.getBytes());

        is = connection.getInputStream();
       // byte[]size = new byte[10];
     //   is.read(size);
     //   String t = new String(size);
     //   int l = Integer.parseInt(t);
        byte[]values = new byte[2096];
        is.read(values);
        answer = (new String(values)).trim();//readValues(is);

        connection.disconnect();

        return answer;
    }

    public void getData(){

    }
}
