package com.example.scdapp.ui;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.example.scdapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class UserHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    HomeFragment homeFragment;
    NoticeBoard noticeBoard;
    ContactUs contactUs;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    FirebaseAuth auth;
    WebView webView;
    ProgressDialog progressDialog;
    void initViews(){
        homeFragment=new HomeFragment();
        noticeBoard=new NoticeBoard();
        contactUs=new ContactUs();
        auth=FirebaseAuth.getInstance();
        webView=findViewById(R.id.webView);
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://goprokart.com/index.php/home/");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait... ");
        progressDialog.setCancelable(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initViews();
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
    void Logout(){
        auth.signOut();
        Intent intent=new Intent(UserHomeActivity.this,UserLoginActivity.class);
        startActivity(intent);
        progressDialog.dismiss();
        finish();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            progressDialog.show();
            Logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.user_home) {
            setFragment(homeFragment);
            getSupportActionBar().setTitle("Home");

        }  else if (id == R.id.user_notice) {
            setFragment(noticeBoard);
            getSupportActionBar().setTitle("Notice Board");

        } else if (id == R.id.contact_us) {
            setFragment(contactUs);
            getSupportActionBar().setTitle("Contact Us");

        } else if (id == R.id.logg) {
            auth.signOut();
            progressDialog.show();
            Intent intent=new Intent(UserHomeActivity.this,UserLoginActivity.class);
            startActivity(intent);
            progressDialog.dismiss();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(Fragment fragment){
        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

}
