package start.rknapp;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity
         implements NavigationView.OnNavigationItemSelectedListener {
    private View buttons;
    private int[] marks = new int[3];

    private final static String[] INTERNET_PERMISSIONS = {
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (SendMarksActivity.isUrlSet() == null)
        {
            SendMarksActivity.setUrl("http://192.168.1.29:8000/enter/");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                send();
             }
         });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
     }

    private void send(){
        Intent intent = new Intent();
        intent.setClass(this, SendMarksActivity.class);
        startActivity(intent);
    }

    protected void onStart()  {
        super.onStart();
        ActivityCompat.requestPermissions(this, INTERNET_PERMISSIONS, 1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_view);
        if (drawer == null) {
            super.onBackPressed();
        }
        else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClass(this, ShowOptionsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.op1:
                SetProjectView(0);
                break;
            case R.id.op2:
                SetProjectView(1);
                break;
            case R.id.op3:
                SetProjectView(2);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_view);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void SetProjectView(final int x){
        View windowForButtons = findViewById(R.id.frame);
        ViewGroup parent = (ViewGroup) windowForButtons.getParent();
        buttons = findViewById(R.id.vote_buttons);

        if(buttons != null) {
            parent.removeView(buttons);
        }
        int indexToAdd = parent.indexOfChild(windowForButtons);
        buttons = getLayoutInflater().inflate(R.layout.content_main, parent, false);

        ((RadioGroup)buttons).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedButtonId = ((RadioGroup) buttons).getCheckedRadioButtonId();
                marks[x] = convertIdToButtonNumber(checkedButtonId);
            }
        });

        if(marks[x] != 0) ((RadioButton)((RadioGroup) buttons).getChildAt(marks[x])).setChecked(true);
        parent.addView(buttons, indexToAdd);
    }

    private int convertIdToButtonNumber(int btn){ // buttons ids and numbers aren't equal
        switch (btn){
            case R.id.r1:
                return 1;
            case R.id.r2:
                return 2;
            case R.id.r3:
                return 3;
            case R.id.r4:
                return 4;
            case R.id.r5:
                return 5;
            case R.id.r6:
                return 6;
            case R.id.r7:
                return 7;
            case R.id.r8:
                return 8;
            case R.id.r9:
                return 9;
            case R.id.r10:
                return 10;
            default:
                return 0;
        }
    }
}
