package halab2018.halab;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Car_tab_layout extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    String activityId;
    String sec_db[];
    public  static String section;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_tab_layout);

        sec_db=getResources().getStringArray(R.array.section_db);

        viewPager=(ViewPager)findViewById(R.id.main_viewpager);
        viewPager.setOffscreenPageLimit(1);
        adapter=new ViewPagerAdapter(getSupportFragmentManager());

        tabLayout=(TabLayout)findViewById(R.id.main_tablayout);

        //toolbar event
        toolbar = (Toolbar) findViewById(R.id.car_tab_toolbar);

        Bundle bundle = getIntent().getExtras();
        activityId = bundle.getString("activityId");
        setTab(activityId);

    }

    public void setTab(String activityId){

        switch (activityId){
            case "properties":{
                section=sec_db[1];
                toolbar.setTitle(getResources().getString(R.string.properties));
                setToolbar();
                adapter.addFragment(new Premium(),getResources().getString(R.string.premium_tab));
                adapter.addFragment(new Get_posts(),getResources().getString(R.string.tab_all));
                if(SharePref.Is_Logged_in(getApplicationContext())){
                    adapter.addFragment(new My_added(),getResources().getString(R.string.tab_my_added));
                }
                viewPager.setAdapter(adapter);
                tabLayout.setupWithViewPager(viewPager);
                break;
            }
            case "jobs":{
                section=sec_db[2];
                toolbar.setTitle(getResources().getString(R.string.jobs_toolbar_title));
                setToolbar();
                adapter.addFragment(new Premium(),getResources().getString(R.string.premium_tab));
                adapter.addFragment(new Get_posts(),getResources().getString(R.string.tab_all));
                if(SharePref.Is_Logged_in(getApplicationContext())){
                    adapter.addFragment(new My_added(),getResources().getString(R.string.tab_my_added));
                }
                viewPager.setAdapter(adapter);
                tabLayout.setupWithViewPager(viewPager);
                break;
            }
            case "cars":{
                section=sec_db[3];
                toolbar.setTitle(getResources().getString(R.string.cars_toolbar_title));
                setToolbar();
                adapter.addFragment(new Premium(),getResources().getString(R.string.premium_tab));
                adapter.addFragment(new Get_posts(),getResources().getString(R.string.tab_all));
                if(SharePref.Is_Logged_in(getApplicationContext())){
                    adapter.addFragment(new My_added(),getResources().getString(R.string.tab_my_added));
                }
                viewPager.setAdapter(adapter);
                tabLayout.setupWithViewPager(viewPager);
                break;
            }
            case "furniture":{
                section=sec_db[4];
                toolbar.setTitle(getResources().getString(R.string.furniture_toolbar_title));
                setToolbar();
                adapter.addFragment(new Premium(),getResources().getString(R.string.premium_tab));
                adapter.addFragment(new Get_posts(),getResources().getString(R.string.tab_all));
                if(SharePref.Is_Logged_in(getApplicationContext())){
                    adapter.addFragment(new My_added(),getResources().getString(R.string.tab_my_added));
                }
                viewPager.setAdapter(adapter);
                tabLayout.setupWithViewPager(viewPager);
                break;
            }
            case "conditioning":{
                section=sec_db[5];
                toolbar.setTitle(getResources().getString(R.string.conditioning_toolbar_title));
                setToolbar();
                adapter.addFragment(new Premium(),getResources().getString(R.string.premium_tab));
                adapter.addFragment(new Get_posts(),getResources().getString(R.string.tab_all));
                if(SharePref.Is_Logged_in(getApplicationContext())){
                    adapter.addFragment(new My_added(),getResources().getString(R.string.tab_my_added));
                }
                viewPager.setAdapter(adapter);
                tabLayout.setupWithViewPager(viewPager);
                break;
            }
            case "c_services":{
                section=sec_db[6];
                toolbar.setTitle(getResources().getString(R.string.c_services_toolbar_title));
                setToolbar();
                adapter.addFragment(new Premium(),getResources().getString(R.string.premium_tab));
                adapter.addFragment(new Get_posts(),getResources().getString(R.string.tab_all));
                if(SharePref.Is_Logged_in(getApplicationContext())){
                    adapter.addFragment(new My_added(),getResources().getString(R.string.tab_my_added));
                }
                viewPager.setAdapter(adapter);
                tabLayout.setupWithViewPager(viewPager);
                break;
            }
            case "Electronic":{
                section=sec_db[7];
                toolbar.setTitle(getResources().getString(R.string.electronic));
                setToolbar();
                adapter.addFragment(new Premium(),getResources().getString(R.string.premium_tab));
                adapter.addFragment(new Get_posts(),getResources().getString(R.string.tab_all));
                if(SharePref.Is_Logged_in(getApplicationContext())){
                    adapter.addFragment(new My_added(),getResources().getString(R.string.tab_my_added));
                }
                viewPager.setAdapter(adapter);
                tabLayout.setupWithViewPager(viewPager);
                break;
            }
            case "Cleaning":{
                section=sec_db[8];
                toolbar.setTitle(getResources().getString(R.string.cleaning));
                setToolbar();
                adapter.addFragment(new Premium(),getResources().getString(R.string.premium_tab));
                adapter.addFragment(new Get_posts(),getResources().getString(R.string.tab_all));
                if(SharePref.Is_Logged_in(getApplicationContext())){
                    adapter.addFragment(new My_added(),getResources().getString(R.string.tab_my_added));
                }
                viewPager.setAdapter(adapter);
                tabLayout.setupWithViewPager(viewPager);
                break;
            }



        }

    }


    public void setToolbar(){
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_on_back_press);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_post_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.add_post_menu_id){
            Intent i = new Intent(Car_tab_layout.this, New_post.class);
            Bundle bundle = new Bundle();
            bundle.putString("activity","Car_tab_layout");
            i.putExtras(bundle);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }


}
