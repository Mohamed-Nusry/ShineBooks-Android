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
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ConcurrentModificationException;

public class RegisterUserAsync extends AsyncTask<Void,Void,Boolean> {

    private static final int CONNECTION_TIMEOUT=10000;
    private static final int READ_TIMEOUT=10000;
    private String email;
    private String name;
    private String password;
    private static final String TAG="regcheck";

    public RegisterUserAsync(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Boolean reg=false;
        String result="";

        try {
            URL url = new URL("http://192.168.1.2:8080/sunshinebookapi/apireg.php");
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            String data= URLEncoder.encode("email","utf-8")+"="+URLEncoder.encode(email,"utf-8")+"&"+
                    URLEncoder.encode("username","utf-8")+"="+URLEncoder.encode(name,"utf-8")+"&"+
                    URLEncoder.encode("password","utf-8")+"="+URLEncoder.encode(password,"utf-8");

            OutputStream outputStream= (OutputStream) httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter= new OutputStreamWriter(outputStream,"utf-8");
            BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);

            bufferedWriter.write(data);

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStreamWriter.close();
            outputStream.close();

            int response_code= httpURLConnection.getResponseCode();
            if(response_code==HttpURLConnection.HTTP_OK){
                InputStream inputStream = (InputStream) httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader  = new InputStreamReader(inputStream,"utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String readline="";
                String jsoninfo="";

                while ((readline=bufferedReader.readLine())!=null){
                    jsoninfo+=readline;
                }

                JSONObject jsonObject = new JSONObject(jsoninfo.toString());
                if(jsonObject.getString("result").equals("Register success")){
                    result=jsonObject.getString("result");
                    reg=true;
                }
                else{
                    Log.i(TAG,"res "+jsonObject.getString("result"));
                    result=jsonObject.getString("result");
                    reg=false;
                }

            }



        }catch (IOException iex){
            Log.i(TAG,"IOException "+iex);
            iex.printStackTrace();

        }

        catch (JSONException jex){
            Log.i(TAG,"JSONException "+jex);
            jex.printStackTrace();

        }




        Log.i(TAG,"user result ="+result+" "+reg);
        return reg;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
