package halab2018.halab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Properties extends AppCompatActivity {
    ListView listView;
    Toolbar toolbar;
    int[] images={R.drawable.properties1,R.drawable.properties2};
    public static ArrayList<String> pro_country,pro_location,pro_bedrooms,pro_bathrooms,pro_area,pro_garages,pro_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.properties);

        toolbar=(Toolbar)findViewById(R.id.properties_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_on_back_press);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pro_country=new ArrayList<String>();
        pro_country.add(0,"Qatar");
        pro_country.add(1,"Qatar");
        pro_location=new ArrayList<String>();
        pro_location.add(0,"ALDOHA, 49St");
        pro_location.add(1,"ALDOHA, 49St");
        pro_bedrooms=new ArrayList<String>();
        pro_bedrooms.add(0,"6");
        pro_bedrooms.add(1,"6");
        pro_bathrooms=new ArrayList<String>();
        pro_bathrooms.add(0,"3");
        pro_bathrooms.add(1,"3");
        pro_area=new ArrayList<String>();
        pro_area.add(0,"750");
        pro_area.add(1,"750");
        pro_garages=new ArrayList<String>();
        pro_garages.add(0,"2");
        pro_garages.add(1,"2");
        pro_price=new ArrayList<String>();
        pro_price.add(0,"230,000");
        pro_price.add(1,"230,000");



        listView=(ListView)findViewById(R.id.propertiesAc_listView_id);
        CustomAdapter customAdapter=new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Intent i = new Intent(getApplicationContext(), Pro_details.class);
               // startActivity(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=getLayoutInflater().inflate(R.layout.custom_listview,null);
            ImageView imageView=(ImageView)convertView.findViewById(R.id.custom_list_img_id);
            TextView country=(TextView)convertView.findViewById(R.id.custom_list_country_id);
            TextView location=(TextView)convertView.findViewById(R.id.custom_list_location_id);
            TextView bedrooms=(TextView)convertView.findViewById(R.id.custom_list_bedroom_id);
            TextView area=(TextView)convertView.findViewById(R.id.custom_list_area_id);
            TextView garages=(TextView)convertView.findViewById(R.id.custom_list_garage_id);
            TextView price=(TextView)convertView.findViewById(R.id.custom_list_price_id);

            imageView.setImageResource(images[position]);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), Pro_details.class);
                    startActivity(i);
                }
            });
            country.setText(pro_country.get(position));
            location.setText(pro_location.get(position));
            bedrooms.setText(pro_bedrooms.get(position));
            area.setText(pro_area.get(position));
            garages.setText(pro_garages.get(position));
            price.setText(pro_price.get(position));


            return convertView;
        }
    }

}
