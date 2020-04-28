package com.example.shinebookstore;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class LoginActivity extends AppCompatActivity {

    private EditText etemail;
    private EditText etpassword;
    private Button btnlogin;
    private Button btnregister;
    private Button btnlogout;
    private User user = null;
    private static final String TAG="glogin";
    private GoogleSignInClient mGoogleSignInClient;
    public GoogleSignInAccount lastAccount;
    //private Context context;
    private File file;
    CallbackManager callbackManager;
    ProgressDialog mdialog;

    String data[] = {"john@gmail.com", "john111"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        //Important

       // getPublicAlbumStorageDir("newproal2"); //excecute make directory

       // File file=getTempFile(this,"cachenew"); //create cache file

        //  boolean ok =isExternalStorageWritable(); //excecute check external memory








//Basic login

        etemail=(EditText) findViewById(R.id.uemail);
        etpassword=(EditText) findViewById(R.id.upass);
        btnlogin=(Button) findViewById(R.id.btnlogin);
        btnregister=(Button) findViewById(R.id.btn_reg);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String uname = etemail.getText().toString();
                    String upass = etpassword.getText().toString();
                    boolean log=logtest(uname,upass);
                    if (log){
                        Toast.makeText(getApplicationContext(), "Successfully Logged in", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Authentication failed!", Toast.LENGTH_SHORT).show();
                    }

                    Log.i(TAG,"uname "+uname);
                    Log.i(TAG,"upass "+upass);
                    FetchUserData fetchUserData = new FetchUserData(uname, upass);
                    user = fetchUserData.execute().get();
                    if(user!=null){
                        Toast.makeText(getApplicationContext(),"Success "+user.getLast_logged(),Toast.LENGTH_SHORT).show();
                        writedata(user.getEmail(),user.getPassword());
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.putExtra("username",user.getUsername());
                        intent.putExtra("email",user.getEmail());
                        intent.putExtra("image",user.getAddress());
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"NOT Logged ",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(regIntent);
            }
        });




        //Google login

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        lastAccount = GoogleSignIn.getLastSignedInAccount(this);


        ArrayList<String> basiclogin = new ArrayList<>(); //Check last sign in of basic login

        basiclogin = read_data();

        if(basiclogin!=null && !basiclogin.isEmpty() && !basiclogin.equals("") && !basiclogin.get(0).equals("null")) {
            checkuser(); //run the checkuser method
        }else {
            revokeAccess();
        }


        findViewById(R.id.sign_in_button_impl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastAccount == null) {
                    writedata("google","login");
                    signIn();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("username",lastAccount.getDisplayName());
                    intent.putExtra("email",lastAccount.getEmail());
                    intent.putExtra("image",lastAccount.getPhotoUrl());
                    writedata("google","login");
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Already signedin "+lastAccount.getDisplayName(),Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "Url: "+lastAccount.getPhotoUrl());
                }
            }
        });



        //Facebook login

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton=(LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile","email","user_birthday","user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mdialog =new ProgressDialog(LoginActivity.this);
                mdialog.setMessage("Reading Data");
                mdialog.show();

                String accesstoken=loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        mdialog.dismiss();

                        Log.i(TAG, "facebook: "+response.toString());

                        getData(object);
                    }
                });

                //request graph api

                Bundle parameters= new Bundle();
                parameters.putString("fields","id,email,birthday,friends");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "facebookb: "+"y");
            }

            @Override
            public void onError(FacebookException error) {
                Log.i(TAG, "facebooka: "+error.getMessage());
            }
        });

        //check already login

        if(AccessToken.getCurrentAccessToken() != null){
            Toast.makeText(this,AccessToken.getCurrentAccessToken().getUserId(),Toast.LENGTH_SHORT).show();

        }

    }

    private void getData(JSONObject object) {
        try{
            URL profile_picture=new URL("https://graph.facebook.com/"+object.getString("id")+"/picture?width=250&height=250");
            Toast.makeText(this,object.getString("email"),Toast.LENGTH_SHORT).show();
            etemail.setText(object.getString("email"));
            Log.i(TAG, "facebook: "+object.getString("email"));

            String friends = object.getJSONObject("friends").getJSONObject("summary").getString("total_count");


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean logtest(String uname,String upass){
        try{
            if (uname.equals(data[0]) && upass.equals(data[1])){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void checkuser(){
        ArrayList<String> basiclog = new ArrayList<>(); //Check last sign in of basic login
        if (lastAccount == null) {
            basiclog = read_data();

            try {
                FetchUserData fetchUserData = new FetchUserData(basiclog.get(0), basiclog.get(1));
                user = fetchUserData.execute().get();
                if (user != null) {
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("username",user.getUsername());
                    intent.putExtra("email",user.getEmail());
                    intent.putExtra("image",user.getAddress());
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Already logged in basic log ", Toast.LENGTH_SHORT).show();
                    //writedata(user.getEmail(),user.getPassword());
                } else {
                    Toast.makeText(getApplicationContext(), "Please sign in ", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else{ //Check last sign in of basic Google login
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("username",lastAccount.getDisplayName());
            intent.putExtra("email",lastAccount.getEmail());
            intent.putExtra("image",lastAccount.getPhotoUrl().toString());
            startActivity(intent);
            Toast.makeText(getApplicationContext(),"Already signedin "+lastAccount.getDisplayName(),Toast.LENGTH_SHORT).show();
            Log.i(TAG, "checkimg: "+lastAccount.getPhotoUrl());
        }
    }

    public void checklogout(){
        if(lastAccount == null) {
            //signOut();
            Toast.makeText(getApplicationContext(),"Not signed out ",Toast.LENGTH_SHORT).show();

        }else{
            revokeAccess();

        }

    }


    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 9001);
    }

    public void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), " Deleted success ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(getApplicationContext(), "Sign out failed ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 9001) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{
            if(completedTask.isComplete()){
                GoogleSignInAccount account =completedTask.getResult(ApiException.class);
                Toast.makeText(this,"Login Successfull "+account.getDisplayName(),Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Url: "+account.getPhotoUrl());
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("username",account.getDisplayName());
                intent.putExtra("email",account.getEmail());
                intent.putExtra("image",account.getPhotoUrl());
                startActivity(intent);
            }else{

                Toast.makeText(this,"Try again ",Toast.LENGTH_SHORT).show();


            }
            // Signed in successfully, show authenticated UI.
            //  updateUI(account);
        } catch (ApiException e) {
            Toast.makeText(this,"Exception "+e.getMessage()+" "+e.getStackTrace()+" "
                    +"Or update Google Play Services ",Toast.LENGTH_SHORT).show();


            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            // Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            // updateUI(null);
        }
    }


    /*private File getTempFile(Context context, String url) { //create cache file
        File file = new File(context.getFilesDir(), "newfileabc");
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e) {
            // Error while creating file
        }
        return file;
    }

    public boolean isExternalStorageWritable() { //check external storage
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    public File getPublicAlbumStorageDir(String albumName) { //Create directory
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.i(TAG,"try again");
        }
        return file;
    }*/


    public void writedata(String email, String pass){
        String[][] yourArray = new String[5][4];

        try {
            File myFile = new File("/sdcard/mysdfile.txt");
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(email+'\n'+pass);
            myOutWriter.close();
            fOut.close();

        } catch (Exception e) {
            Log.e("ERRR", "Could not create file",e);
        }
    }

    public ArrayList<String> read_data() {
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, "mysdfile.txt");
        ArrayList<String> listOfLines = new ArrayList<>();

        try {
            BufferedReader bufReader = new BufferedReader(new FileReader(file));

            String line = bufReader.readLine();
            while (line != null) {
                listOfLines.add(line);
                line = bufReader.readLine();
            }

            bufReader.close();

        }catch (IOException e){
            e.printStackTrace();
        }
        return listOfLines;
    }

    public void delete_data(){
        writedata(null,null);

    }


}
