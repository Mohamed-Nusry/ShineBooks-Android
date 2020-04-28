package com.example.shinebookstore;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URL;

public class ViewBook extends AppCompatActivity {

    private TextView bname;
    private TextView bauthor;
    private TextView bprice;
    private ImageView bimg;
    private Button bbuy;
    private static final String TAG="viewdet";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);

        final Intent intent=getIntent();

        final String BookName=intent.getStringExtra("bookname");
        String Bookisbn=intent.getStringExtra("bookisbn");
        String BookPrice=intent.getStringExtra("bookprice");
        String Bookurl=intent.getStringExtra("bookurl");
       // String Bookurl="http://192.168.1.6:8080/sunshinebookapi/img/hp.jpg";

        //fix here. if the image url is null then the application will crash

//        Log.i(TAG, "url: "+Bookurl+ BookAuthor);

        Book book=new Book();
        bname=(TextView) findViewById(R.id.bkname);

        bname.setText(BookName);

        bauthor=(TextView) findViewById(R.id.bkauth);

        bauthor.setText("Book Author: - "+Bookisbn);

        bprice=(TextView) findViewById(R.id.bkprice);

        bprice.setText("Book Price: - LKR "+BookPrice);

        bimg=(ImageView) findViewById(R.id.bkimg);

        if(!Bookurl.isEmpty()&& !Bookurl.equals("null")){
            Glide.with(getApplicationContext())
                    .load(Bookurl)
                    .placeholder(R.drawable.ic_book_black_24dp)
                    .into(bimg);
        }
       else{
        bimg.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_book_black_24dp));
    }

       bbuy=(Button) findViewById(R.id.btnbuy);
       bbuy.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent1 = new Intent(getApplicationContext(),PurchaseActivity.class);
               intent1.putExtra("pBookname",BookName);
               startActivity(intent1);
           }
       });



}
}
