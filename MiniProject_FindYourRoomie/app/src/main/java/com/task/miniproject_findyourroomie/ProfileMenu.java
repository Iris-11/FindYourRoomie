package com.task.miniproject_findyourroomie;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class ProfileMenu extends AppCompatActivity {
    ImageButton profile_btn,nav_btn;
    private DrawerLayout drawerLayout;
    boolean isMenuOpen=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_menu);
        profile_btn=findViewById(R.id.imageButton);
        nav_btn=findViewById(R.id.imageButton1);

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
                            Toast.makeText(ProfileMenu.this, "You clicked on profile", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        else if(id == R.id.settings_menu){
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
                            //Creating dialog box
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
                int id = menuItem.getItemId();
                if(id == R.id.home){
                    Toast.makeText(ProfileMenu.this, "You clicked on home", Toast.LENGTH_SHORT).show();
                    Intent homePage = new Intent(ProfileMenu.this,HomePage.class);
                    startActivity(homePage);
                    return true;
                }
                else if(id == R.id.reviews){
                    Toast.makeText(ProfileMenu.this, "You clicked on reviews", Toast.LENGTH_SHORT).show();
                    return true;
                }
                else if(id == R.id.community){
                    Toast.makeText(ProfileMenu.this, "You clicked on community", Toast.LENGTH_SHORT).show();
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