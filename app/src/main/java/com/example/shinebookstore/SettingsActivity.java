package com.example.shinebookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView show_nme;
    private String show_img;
    private TextView show_email;
    private Button show_logout;
    private Button show_home;
    String email;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();
        show_img= intent.getStringExtra("image");
        username=intent.getStringExtra("username");
        email=intent.getStringExtra("email");

        show_nme=(TextView) findViewById(R.id.sh_name);
        show_email=(TextView) findViewById(R.id.sh_email);

        show_logout=(Button) findViewById(R.id.sh_lgout);
        show_logout.setOnClickListener(this);

        show_home=(Button) findViewById(R.id.sh_home);
        show_home.setOnClickListener(this);



        ImageView sh_image = (ImageView) findViewById(R.id.sh_img);

        show_nme.setText(username);
        show_email.setText(email);

        if(show_img!=null && !show_img.isEmpty() && !show_img.equals("null") ){
            Glide.with(getApplicationContext())
                    .load(show_img)
                    .placeholder(R.drawable.ic_account_circle_black_24dp)
                    .circleCrop()
                    .override(100,100)
                    .into(sh_image);
        }else {
            sh_image.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_account_circle_black_24dp));
        }




    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case R.id.sh_lgout:
                LoginActivity loginActivity = new LoginActivity();
                loginActivity.delete_data();
                Intent intent1 = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.sh_home:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);

        }

    }
}
