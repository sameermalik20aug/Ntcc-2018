package com.example.hp.babysteps_1;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity  {

    FileAdapter fileAdapter;
    ArrayList <FilesV> filesV = new ArrayList<>();
    ArrayList<String> names;
    LinearLayoutManager llm;
    RecyclerView recyclerView;
    Intent navIntent,videoIntent;
    AdView myAdView;

    private static final int RequestCode= 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.rView);
        llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        navIntent = new Intent(this,NavItems.class);
        videoIntent = new Intent(this,Main2Activity.class);
        setSupportActionBar(toolbar);

        myAdView = findViewById(R.id.adView);
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("A9B2947E175A69BB0E2F5E1D37AC883A").build();
        myAdView.loadAd(adRequest);



        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RequestCode);
        }else
            {
                getVideos();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nStream) {
            final View alertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_layout, null, true);
            final AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Network Stream")
                    .setCancelable(false)
                    .setMessage("Please enter a network URL")
                    .setView(alertView)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EditText editText = alertView.findViewById(R.id.eText);
                            String edit = editText.getText().toString();
                            editText.setText("");
                             {
                                Toast.makeText(MainActivity.this, "Here You Go!", Toast.LENGTH_SHORT).show();
                                videoIntent.putExtra("Link", edit);
                                startActivity(videoIntent);
                            }
                        }
                    })

                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Toast.makeText(MainActivity.this, "As You Wish", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .create();
            alertDialog.show();

        }if(item.getItemId() == R.id.help){
          navIntent.putExtra("HELP",1);
          startActivity(navIntent);

        }if(item.getItemId() == R.id.about){
          navIntent.putExtra("HELP",2);
          startActivity(navIntent);
        }

        return  super.onOptionsItemSelected(item);
    }
    public ArrayList<String> GetFiles() {

        HashSet<String> videoName = new HashSet<>();

        Cursor cursor = getBaseContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null,null );

        try {
            if (cursor != null) {
                cursor.moveToFirst();

                do {
                    videoName.add((cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))));

                } while (cursor.moveToNext());

                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> result = new ArrayList<>(videoName);
        return result;
    }

    public void getVideos() {
        names = GetFiles();
        Collections.sort(names,String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < names.size(); i++)
        {
            filesV.add(new FilesV(names.get(i)));
        }
        fileAdapter = new FileAdapter(this,filesV);
        recyclerView.setAdapter(fileAdapter);
        fileAdapter.notifyDataSetChanged();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == RequestCode) {

            if (grantResults.length>0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Thank you for granting the permissions!", Toast.LENGTH_SHORT).show();
                 getVideos();
            } else {
                Toast.makeText(this,
                        "Sorry, but I need the permissions for the app to work!",Toast.LENGTH_SHORT).show();
                         finish();
            }

        }
    }
}
