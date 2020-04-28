package com.example.shinebookstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG="mainDetect";
    private String uname;
    private String email;
    private String image;
    private TextView navText;
    private GoogleSignInClient mGoogleSignInClient;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        MyFragmentAdapter myFragmentAdapter=new MyFragmentAdapter(getSupportFragmentManager());

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(myFragmentAdapter);

        Intent intent =getIntent();
         uname= intent.getStringExtra("username");
         email= intent.getStringExtra("email");
         image= intent.getStringExtra("image");
        Log.i(TAG, "onImage: "+image);
        Log.i(TAG, "onUsername: "+uname);
        Log.i(TAG, "onEmail: "+email);
        

        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.nav_nme);

        nav_user.setText(uname);

        ImageView nav_img = (ImageView) hView.findViewById(R.id.imageView);

       if(image!=null && !image.isEmpty() && !image.equals("null") ){
            Glide.with(getApplicationContext())
                    .load(image)
                    .placeholder(R.drawable.ic_account_circle_black_24dp)
                    .circleCrop()
                    .override(100,100)
                    .into(nav_img);
        }else {
            nav_img.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_account_circle_black_24dp));
        }

       TextView nav_email =(TextView) hView.findViewById(R.id.text_nav_Email);

       nav_email.setText(email);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, MapsActivity.class );
            startActivity(intent);
            return true;
        }
        if(id==R.id.action_logout) {


        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


         if (id == R.id.nav_store) {

        } else if (id == R.id.nav_purchase) {


        } else if (id == R.id.nav_profile) {
             Intent intent1 = new Intent(this,SettingsActivity.class);
             intent1.putExtra("username",uname);
             intent1.putExtra("email",email);
             intent1.putExtra("image",image);
             startActivity(intent1);

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this,AboutUsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_log) {
             LoginActivity loginActivity = new LoginActivity();
             loginActivity.delete_data();
             Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
             startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }



}
