package halab2018.halab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Altayeb on 10/11/2018.
 */
public class All extends Fragment {
    CustomAdapter customAdapter=new CustomAdapter();


    int[] images={R.drawable.properties1,R.drawable.properties2};
    //public static ArrayList<String> pro_country,pro_location,pro_bedrooms,pro_bathrooms,pro_area,pro_garages,pro_price;



    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog progressDialog;

    View view;
    ListView listView;
    public static ArrayList<String> pro_id,pro_country,pro_area,pro_bedrooms,pro_bathrooms,pro_space,pro_garages,pro_price,pro_images;

    public All() {
    }
    //////////////////////////////////


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.all,container,false);

        //P_Dialog
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getResources().getString(R.string.progressDialog_txt));
        progressDialog.setCancelable(false);


        //////////
        pro_country=new ArrayList<String>();
        pro_country.add(0,"Qatar");
        pro_country.add(1,"Qatar");
        pro_area=new ArrayList<String>();
        pro_area.add(0,"ALDOHA, 49St");
        pro_area.add(1,"ALDOHA, 49St");
        pro_bedrooms=new ArrayList<String>();
        pro_bedrooms.add(0,"6");
        pro_bedrooms.add(1,"6");
        pro_bathrooms=new ArrayList<String>();
        pro_bathrooms.add(0,"3");
        pro_bathrooms.add(1,"3");
        pro_space=new ArrayList<String>();
        pro_space.add(0,"750");
        pro_space.add(1,"750");
        pro_garages=new ArrayList<String>();
        pro_garages.add(0,"2");
        pro_garages.add(1,"2");
        pro_price=new ArrayList<String>();
        pro_price.add(0,"230,000");
        pro_price.add(1,"230,000");




        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorAccent);



        listView=(ListView) view.findViewById(R.id.all_listView_id);
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





        //////////
        /*pro_id=new ArrayList<String>();
        pro_country=new ArrayList<String>();
        pro_area=new ArrayList<String>();
        pro_bedrooms=new ArrayList<String>();
        pro_bathrooms=new ArrayList<String>();
        pro_space=new ArrayList<String>();
        pro_garages=new ArrayList<String>();
        pro_price=new ArrayList<String>();
        pro_images=new ArrayList<String>();
*/
        //getPro();

/*        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pro_id.clear();
                pro_country.clear();
                pro_area.clear();
                pro_bedrooms.clear();
                pro_bathrooms.clear();
                pro_space.clear();
                pro_garages.clear();
                pro_price.clear();
                pro_images.clear();
                //getPro();
            }
        });*/
        swipeRefreshLayout.setRefreshing(true);
        return view;}


    /////////custom ListView adapter
    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return pro_bedrooms.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView=getLayoutInflater(null).inflate(R.layout.custom_listview,null);
            ImageView imageView=(ImageView)convertView.findViewById(R.id.custom_list_img_id);
            ImageView data=(ImageView)convertView.findViewById(R.id.custom_data);
            ImageView share=(ImageView)convertView.findViewById(R.id.custom_share);
            ImageView like=(ImageView)convertView.findViewById(R.id.custom_like);
            //TextView pro_type=(TextView)convertView.findViewById(R.id.custom_list_pro_type);
            TextView main_location=(TextView)convertView.findViewById(R.id.custom_list_country_id);
            TextView sub_location=(TextView)convertView.findViewById(R.id.custom_list_location_id);
            TextView area=(TextView)convertView.findViewById(R.id.custom_list_area_id);
            TextView bedrooms=(TextView)convertView.findViewById(R.id.custom_list_bedroom_id);
            TextView bathrooms=(TextView)convertView.findViewById(R.id.custom_list_bathroom_id);
            TextView garages=(TextView)convertView.findViewById(R.id.custom_list_garage_id);
            TextView price=(TextView)convertView.findViewById(R.id.custom_list_price_id);

            //pro_type.setVisibility(View.GONE);


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(), Pro_details.class);
                    /*Bundle bundle = new Bundle();
                    bundle.putString("pro_id",pro_id.get(position) );
                    i.putExtras(bundle);*/
                    startActivity(i);
                }
            });
            //////////
            imageView.setImageResource(images[position]);
            main_location.setText(pro_country.get(position));
            sub_location.setText(pro_area.get(position));
            bedrooms.setText(pro_bedrooms.get(position));
            bathrooms.setText(pro_bathrooms.get(position));
            area.setText(pro_space.get(position)+getResources().getString(R.string.sqft));
            garages.setText(pro_garages.get(position));
            price.setText(pro_price.get(position));



            /*main_location.setText(pro_country.get(position));
            sub_location.setText(pro_area.get(position));
            bedrooms.setText(pro_bedrooms.get(position));
            area.setText(pro_space.get(position)+ " " + getResources().getString(R.string.spaceSign));
            bathrooms.setText(pro_bathrooms.get(position));
            garages.setText(pro_garages.get(position));
            price.setText(pro_price.get(position));
            pro_type.setText(getResources().getString(R.string.sale));*/

           /* Glide.with(getContext())
                    .load(URL.IMAGE_PATH+pro_images.get(position)+".jpg") // image url
                    .centerCrop()
                    .into(imageView);*/



            swipeRefreshLayout.setRefreshing(false);

            return convertView;
        }
    }

   /* //Array Request
    private void getPro() {
        if (URL.isNetworkConnected(getContext())) {
            if (!URL.isInternetAvailable()) {
                swipeRefreshLayout.setRefreshing(true);
                showDialog();
                final HashMap<String, String> params = new HashMap<String, String>();
                params.put("type_en", "sale");
                final JSONObject jsonObject = new JSONObject(params);

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL.GET_PRO_URL,jsonObject, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = (JSONObject) response.get(i);
                                String message = object.getString("message");
                                String id = object.getString("id");
                                String country = object.getString("country_en");
                                String area = object.getString("area_en");
                                String price = object.getString("price");
                                String space = object.getString("space");
                                String bedrooms = object.getString("bedrooms");
                                String bathrooms = object.getString("bathrooms");
                                String garages = object.getString("garage");
                                String image = object.getString("image");

                                if (message.equals("yes")) {
                                    pro_id.add(i,id.toString().trim());
                                    pro_country.add(i,country.toString().trim());
                                    pro_area.add(i,area.toString().trim());
                                    pro_price.add(i,price.toString().trim());
                                    pro_space.add(i,space.toString().trim());
                                    pro_bedrooms.add(i,bedrooms.toString().trim());
                                    pro_bathrooms.add(i,bathrooms.toString().trim());
                                    pro_garages.add(i,garages.toString().trim());
                                    pro_images.add(i,image.toString().trim());

                                    hideDialog();


                                } else {
                                    hideDialog();
                                    Toast.makeText(getContext(),getResources().getString(R.string.sale_empty_dept), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                hideDialog();
                                Toast.makeText(getContext(), getResources().getString(R.string.sale_empty_dept), Toast.LENGTH_SHORT).show();
                                swipeRefreshLayout.setRefreshing(false);
                                e.printStackTrace();
                            }
                        }
                        if(pro_id.isEmpty()){
                            hideDialog();
                            Toast.makeText(getContext(), getResources().getString(R.string.sale_empty_dept), Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                        }else{
                            hideDialog();
                            listView.setAdapter(customAdapter);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideDialog();
                        Toast.makeText(getContext(),getString(R.string.login_error_server_not_found), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
                MySingleton.getmInstance(getContext()).addToRequestque(jsonArrayRequest);
            } else {
                hideDialog();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();

            }
        } else {
            hideDialog();
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();

        }
    }*/



    //Operate Dialog
    private void showDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void hideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
