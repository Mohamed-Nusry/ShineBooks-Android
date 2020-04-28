package com.example.shinebookstore;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FetchBookData extends AsyncTask<Void,Void, List<Book>> {
    private static final String TAG="detect";
    private static final int CONNECTION_TIMEOUT=10000;
    private static final int READ_TIMEOUT=10000;
    //private String method;
    private String  url;

    public FetchBookData(String url) {
       // this.method = method;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Book> doInBackground(Void... voids) {
        List<Book> bookList=new ArrayList<>();
        String linkUrl=url;


        try {
            URL url = new URL(linkUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(false);

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = (InputStream) httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String readLine = "";
                StringBuilder jsonString = new StringBuilder();
                while ((readLine = bufferedReader.readLine()) != null) {
                    jsonString.append(readLine);
                    Log.i(TAG, "line" + readLine);

                }

                JSONObject jsonObject = new JSONObject(jsonString.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("book_details");

                for (int i = 0; i < jsonArray.length(); i++) {
                    Book book = new Book();
                    JSONObject jobj = jsonArray.getJSONObject(i);

                    book.setName(jobj.getString("bname"));
                    book.setISBN(jobj.getString("bisbn"));
                    book.setPrice(jobj.getString("bprice"));
                    book.setAuthor(jobj.getString("bauthor"));
                    book.setImageURL(jobj.getString("bimage"));
                    book.setBookPurchased(Integer.parseInt(jobj.getString("bpurchased")));

                    bookList.add(book);
                    Log.i(TAG, "array" + book);
                }


            }

        } catch (JSONException e) {
            Log.i(TAG, "JSON Error: " + e.getMessage());
        } catch (IOException ex) {
            Log.i(TAG, "ID Error: " + ex.getMessage());
        }

        return bookList;

    }


    @Override
    protected void onPostExecute(List<Book> books) {
        super.onPostExecute(books);
    }
}
