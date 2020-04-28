package com.example.shinebookstore;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class BuyProduct extends AsyncTask<Void,Void,Boolean> {
    private static final String TAG="detectadd";
    private static final int CONNECTION_TIMEOUT=10000;
    private static final int READ_TIMEOUT=10000;
    private String book_name;

    public BuyProduct(String book_name) {
        this.book_name = book_name;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        boolean res=false;
        String result;
        try{
            URL url = new URL("http://192.168.1.2:8080/sunshinebookapi/index.php");
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            String data= URLEncoder.encode("bname","utf-8")+"="+URLEncoder.encode(book_name,"utf-8");
            Log.i(TAG, "data: "+data);

            OutputStream outputStream=(OutputStream) httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"utf-8");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.write(data);

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStreamWriter.close();
            outputStream.close();

            int response_code=httpURLConnection.getResponseCode();
            if(response_code==HttpURLConnection.HTTP_OK){
                InputStream inputStream=(InputStream) httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line="";
                String jsonline="";

                while((line=bufferedReader.readLine())!=null){
                    jsonline+=line;

                }

                JSONObject jsonObject = new JSONObject(jsonline.toString());
                if(jsonObject.getString("res").equals("data inserted")){
                    result=jsonObject.getString("res");
                    res=true;

                }else{
                    result=jsonObject.getString("res");
                    res=false;
                }

            }

        }catch (IOException iex){
            iex.printStackTrace();

        }catch (JSONException jex){
            jex.printStackTrace();
        }
        return res;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
