package com.task.miniproject;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class ProfileMenu extends AppCompatActivity {
    ImageButton profile_btn,nav_btn;
    //Button roomie,hostel;
    private DrawerLayout drawerLayout;
    boolean isMenuOpen=false;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_menu);
        profile_btn=findViewById(R.id.imageButton);
        nav_btn=findViewById(R.id.imageButton1);
        //roomie=findViewById(R.id.roomie);
        //hostel=findViewById(R.id.hostel);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);




        // Card 1 OnClickListener
        findViewById(R.id.roomie_card).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DisplayMatches.class);
            startActivity(intent);
            finish();
        });

        // Card 2 OnClickListener
        findViewById(R.id.room_card).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Maps.class);
            startActivity(intent);
            finish();
        });
        nav_btn.setImageResource(R.drawable.menu);

        nav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isMenuOpen){
                    showMenu();
                }
                else{
                    closeMenu();
                }
            }
        });

        /*roomie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(ProfileMenu.this, DisplayMatches.class);
                i.putExtra("username",prev.getStringExtra("username"));
                startActivity(i);

            }
        });*/

        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ProfileMenu.this, profile_btn);
                popupMenu.getMenuInflater().inflate(R.menu.profile_menu, popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        if(id == R.id.profile_menu){
                            Intent profile=new Intent(ProfileMenu.this,ProfileDisplay.class);

                            startActivity(profile);

                            Toast.makeText(ProfileMenu.this, "You clicked on profile", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        else if(id == R.id.settings_menu){
                            Intent settings=new Intent(ProfileMenu.this, Settings.class);
                            startActivity(settings);

                            Toast.makeText(ProfileMenu.this, "You clicked on settings", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        else if(id == R.id.logout_menu){
                            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileMenu.this);
                            builder.setMessage("Do you want to logout?")
                                    .setCancelable(false)


                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            finish();
                                            Intent logout = new Intent(getApplicationContext(), Welcome.class);
                                            startActivity(logout);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //  Action for 'NO' Button
                                            dialog.cancel();
                                        }
                                    })
                                    .setTitle("Logout")
                                    .setIcon(R.drawable.logout);

                            AlertDialog alert = builder.create();
                            alert.show();
                            return true;
                        }
                        else{
                            return false;
                        }
                    }
                });

            }
        });
        /*hostel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getMap = new Intent(getApplicationContext(), Maps.class);
                startActivity(getMap);
            }
        });*/

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void showMenu(){
        isMenuOpen=true;
        PopupMenu popupMenu = new PopupMenu(ProfileMenu.this, nav_btn);
        popupMenu.getMenuInflater().inflate(R.menu.navigation_menu, popupMenu.getMenu());
        nav_btn.setImageResource(R.drawable.backarrow);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent prev=getIntent();
                int id = menuItem.getItemId();
                if(id == R.id.home){
                    Toast.makeText(ProfileMenu.this, "You clicked on home", Toast.LENGTH_SHORT).show();
                    Intent homePage = new Intent(ProfileMenu.this,HomePage.class);
                    startActivity(homePage);
                    return true;
                }
                else if(id == R.id.reviews){
                    Toast.makeText(ProfileMenu.this, "You clicked on reviews", Toast.LENGTH_SHORT).show();
                    Intent reviews = new Intent(ProfileMenu.this, ReviewsChoice.class);
                    startActivity(reviews);
                    return true;
                }
                else if(id == R.id.community){
                    Toast.makeText(ProfileMenu.this, "You clicked on community", Toast.LENGTH_SHORT).show();
                    Intent community = new Intent(getApplicationContext(),Forum.class);
                    startActivity(community);
                    return true;
                }
                else{
                    return false;
                }
            }
        });
        popupMenu.show();

        popupMenu.setOnDismissListener(menu -> closeMenu());

    }
    public void closeMenu(){
        isMenuOpen=false;
        nav_btn.setImageResource(R.drawable.menu);

    }
}