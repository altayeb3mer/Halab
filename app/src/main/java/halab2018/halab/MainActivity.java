package halab2018.halab;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import halab2018.halab.Activity.ContactUs;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Toolbar
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    CardView properties,jobs,cars,furniture,c_services,conditioning,electronic,cleaning,card_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer);




        card_other = findViewById(R.id.card_other);
        card_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Car_tab_layout.class);
                Bundle bundle = new Bundle();
                bundle.putString("activityId", "Other");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        properties=(CardView)findViewById(R.id.activity_main_properties_cardview);
        properties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Car_tab_layout.class);
                Bundle bundle = new Bundle();
                bundle.putString("activityId", "properties");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        jobs=(CardView)findViewById(R.id.activity_main_jobs_cardview);
        jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Car_tab_layout.class);
                Bundle bundle = new Bundle();
                bundle.putString("activityId", "jobs");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        cars=(CardView)findViewById(R.id.activity_main_cars_cardview);
        cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Car_tab_layout.class);
                Bundle bundle = new Bundle();
                bundle.putString("activityId", "cars");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        furniture=(CardView)findViewById(R.id.activity_main_furniture_cardView);
        furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Car_tab_layout.class);
                Bundle bundle = new Bundle();
                bundle.putString("activityId", "furniture");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        conditioning=(CardView)findViewById(R.id.activity_main_conditioning_cardview);
        conditioning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Car_tab_layout.class);
                Bundle bundle = new Bundle();
                bundle.putString("activityId", "conditioning");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        c_services=(CardView)findViewById(R.id.activity_main_commercial_services_cardView);
        c_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Car_tab_layout.class);
                Bundle bundle = new Bundle();
                bundle.putString("activityId", "c_services");
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        electronic=(CardView)findViewById(R.id.activity_main_electronic_cardview);
        electronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Car_tab_layout.class);
                Bundle bundle = new Bundle();
                bundle.putString("activityId", "Electronic");
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        cleaning=(CardView)findViewById(R.id.activity_main_cleaning_cardView);
        cleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Car_tab_layout.class);
                Bundle bundle = new Bundle();
                bundle.putString("activityId", "Cleaning");
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        //N drawer
        toolbar=(Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=(DrawerLayout)findViewById(R.id.n_drawer);
        navigationView=(NavigationView)findViewById(R.id.n_view);


        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();



        hideItem();
    }
    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.menu_search_properties_id: {
                Toast.makeText(getApplicationContext(), "SEARCH PROPERTIES", Toast.LENGTH_SHORT).show();
                break;
            }


            case R.id.menu_login_id: {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                break;
            }

            case R.id.menu_register_id: {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
                break;
            }

/*            case R.id.menu_setting_id: {
                Toast.makeText(getApplicationContext(), "SETTING", Toast.LENGTH_SHORT).show();
                break;
            }*/

            case R.id.menu_contact_id: {
                Intent i = new Intent(getApplicationContext(), ContactUs.class);
                startActivity(i);
                break;
            }

            case R.id.menu_about_id: {
                Intent i = new Intent(getApplicationContext(), About.class);
                startActivity(i);
                break;
            }
            case R.id.menu_logout_id: {
                buildAlertMessageLogout();
                break;
            }

            case R.id.menu_exit_id: {
                buildAlertMessageNoGps();
                break;
            }
        }


        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    //Exit builder dialog
    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.dialog_msg_exit))
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    protected void buildAlertMessageLogout() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.logout_msg_builder))
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        SharePref.Logout(MainActivity.this);
                        Menu nav_Menu = navigationView.getMenu();
                        nav_Menu.findItem(R.id.menu_login_id).setVisible(true);
                        nav_Menu.findItem(R.id.menu_register_id).setVisible(true);
                        nav_Menu.findItem(R.id.menu_logout_id).setVisible(false);
                        Toast.makeText(MainActivity.this, getResources().getString(R.string.logout_msg), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    private void hideItem()
    {

        if(SharePref.Is_Logged_in(MainActivity.this)){
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.menu_login_id).setVisible(false);
            nav_Menu.findItem(R.id.menu_register_id).setVisible(false);
        }else{
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.menu_logout_id).setVisible(false);
        }


    }


}
