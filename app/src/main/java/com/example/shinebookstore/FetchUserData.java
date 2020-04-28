package com.example.shinebookstore;

import android.content.Context;
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
import java.net.URLConnection;
import java.net.URLEncoder;

public class FetchUserData extends AsyncTask<Void,Void,User> {
    private String email;
    private String password;
    private static final int CONNECTION_TIMEOUT=10000;
    private static final int READ_TIMEOUT=10000;
    private static final String TAG="logcheck";
    private Context context;


    public FetchUserData(String email, String password) {
        this.email = email;
        this.password = password;

        Log.i(TAG,"user_information "+email+" "+password);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected User doInBackground(Void... voids) {
        User user=null;

        try {
            URL url = new URL("http://192.168.1.6:8080/sunshinebookapi/apilogin.php");
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            String udata= URLEncoder.encode("email","utf-8")+"="+URLEncoder.encode(email,"utf-8")+"&"+
                    URLEncoder.encode("password","utf-8")+"="+URLEncoder.encode(password,"utf-8");

            Log.i(TAG, "user_data : "+udata);

            OutputStream outputStream =(OutputStream) httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream,"utf-8");
            BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);

            bufferedWriter.write(udata);

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStreamWriter.close();
            outputStream.close();

            int response=httpURLConnection.getResponseCode();
            if(response==HttpURLConnection.HTTP_OK){
                InputStream inputStream=(InputStream) httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"utf-8");
                BufferedReader bufferedReader= new BufferedReader(inputStreamReader);

                String read_line="";
                String json_data="";

                while ((read_line=bufferedReader.readLine())!=null){
                    json_data+=read_line;

                    Log.i(TAG,"read_data"+read_line);
                }
                Log.i(TAG,"json_data"+json_data);

                JSONObject jsonObject= new JSONObject(json_data.toString());
                Log.i(TAG,"result_type "+jsonObject.getString("login_result"));
                if(jsonObject.getString("login_result").equals("Success")){
                    JSONObject user_jsonObject = jsonObject.getJSONObject("user_data");

                    user=new User();
                    user.setUsername(user_jsonObject.getString("username"));
                    user.setPassword(user_jsonObject.getString("password"));
                    user.setEmail(user_jsonObject.getString("email"));
                    user.setAddress(user_jsonObject.getString("address"));
                    user.setMobile(user_jsonObject.getString("mobile"));
                    user.setLast_logged(user_jsonObject.getString("last_logged"));




                }else{
                    Log.i(TAG,"result_type "+jsonObject.getString("login_result"));
                }



            }



        }catch (IOException iex){
            Log.i(TAG,"IOException"+iex);

        }catch (JSONException jex){
            Log.i(TAG,"JSONException"+jex);
        }
        return user;
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);
    }
}
