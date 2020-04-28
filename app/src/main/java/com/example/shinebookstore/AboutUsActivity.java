package com.example.shinebookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        btnMap = (Button) findViewById(R.id.shw_map);
        btnMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.shw_map:
                Intent intent = new Intent(this,MapsActivity.class);
                startActivity(intent);
        }

    }
}
