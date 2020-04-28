package com.example.shinebookstore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private EditText etemail;
    private EditText etname;
    private EditText etpass;
    private Button btadd;
    private boolean register;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etemail = (EditText) findViewById(R.id.reg_email);
        etname = (EditText) findViewById(R.id.reg_name);
        etpass = (EditText) findViewById(R.id.reg_pass);
        btadd = (Button) findViewById(R.id.btn_add);


        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    String regEmail = etemail.getText().toString();
                    String regName = etemail.getText().toString();
                    String regPass = etemail.getText().toString();

                    RegisterUserAsync registerUserAsync = new RegisterUserAsync(regEmail, regName, regPass);
                    register = registerUserAsync.execute().get();
                    if(register){
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Try again",Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception ex){
                    ex.printStackTrace();
                }



            }
        });





    }
}
