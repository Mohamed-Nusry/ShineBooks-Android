package com.example.shinebookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PurchaseActivity extends AppCompatActivity {
    private static final String TAG="detectpurchase";
    private TextView pbname;
    private Button bConfirm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        Intent intent=getIntent();
        final String pBookname= intent.getStringExtra("pBookname");

        bConfirm=(Button) findViewById(R.id.btn_confirm);
        pbname=(TextView) findViewById(R.id.p_bname);

        pbname.setText("Book name : "+pBookname);

        bConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BuyProduct buyProduct = new BuyProduct(pBookname);
                    boolean ok = buyProduct.execute().get();
                    if(ok){
                        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent1);
                        Log.i(TAG, "done");
                    }else {
                        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent1);
                        Log.i(TAG, "not done");
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }
}
