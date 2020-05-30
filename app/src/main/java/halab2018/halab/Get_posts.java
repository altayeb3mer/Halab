package halab2018.halab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import halab2018.halab.Utils.RetroApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Altayeb on 10/27/2018.
 */
public class Get_posts extends Fragment{
    CustomAdapter customAdapter=new CustomAdapter();

    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog progressDialog;

    View view;
    ListView listView;
    public static ArrayList<String> post_id,post_title,post_price,post_img1;

    public Get_posts() {
    }


    ////////////////
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.get_posts,container,false);


        //P_Dialog
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getResources().getString(R.string.progressDialog_txt));
        progressDialog.setCancelable(false);



        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));



        listView=(ListView) view.findViewById(R.id.get_posts_listView_id);
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
        post_id=new ArrayList<String>();
        post_title=new ArrayList<String>();
        post_price=new ArrayList<String>();
        post_img1=new ArrayList<String>();

//        getPosts();
        if (URL.isNetworkConnected(getContext())) {
            if (!URL.isInternetAvailable()) {
                //getPremium
                getPostsRetro();
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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                post_id.clear();
                post_title.clear();
                post_price.clear();
                post_img1.clear();

//                getPosts();
                if (URL.isNetworkConnected(getContext())) {
                    if (!URL.isInternetAvailable()) {
                        //getPremium
                        getPostsRetro();
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
            }
        });
        swipeRefreshLayout.setRefreshing(true);
        return view;}





    /////////custom ListView adapter
    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return post_id.size();
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
            convertView=getLayoutInflater(null).inflate(R.layout.custom_listview_post,null);
            ImageView imageView=(ImageView)convertView.findViewById(R.id.custom_list_post_img_id);
            TextView txt_post_title=(TextView)convertView.findViewById(R.id.custom_list_post_title_id);
            TextView txt_post_price=(TextView)convertView.findViewById(R.id.custom_list_post_price_id);



            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(), Post_details.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("post_id",post_id.get(position) );
                    i.putExtras(bundle);
                    startActivity(i);
                }
            });
            txt_post_title.setText(post_title.get(position));
            txt_post_price.setText(post_price.get(position));


            Glide.with(getContext())
                    .load(URL.IMAGE_PATH+post_img1.get(position)) // image url
                    .centerCrop()
                    .into(imageView);



            swipeRefreshLayout.setRefreshing(false);

            return convertView;
        }
    }

    private void getPostsRetro() {
//        OkHttpClient httpClient = new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public okhttp3.Response intercept(Chain chain) throws IOException {
//                        okhttp3.Request.Builder ongoing = chain.request().newBuilder();
//                        ongoing.addHeader("Accept", "application/json;");
//                        ongoing.addHeader("Content-Type", "application/x-www-form-urlencoded");
//
//                        ongoing.addHeader("Authorization", SharedPrefManager.getInstance(getApplicationContext()).GetToken());
//
//                        return chain.proceed(ongoing.build());
//                    }
//                })
//                .readTimeout(60, TimeUnit.SECONDS)
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .build();
        swipeRefreshLayout.setRefreshing(true);
        showDialog();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.ROOT_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetroApi.getPosts service = retrofit.create(RetroApi.getPosts.class);

        HashMap hashMap = new HashMap();
        hashMap.put("Content-Type", "application/json;charset=UTF-8");
//                Call<TransResult> call = service.getStringScalar(new SendToCard(cardModel.getCard_no(), s_card_no, "000", cardModel.getMonth() + cardModel.getYear(), s_money, uuid, "moneyTransfer"));
//        Call<String> call = service.getStringScalar(new SendToPhoneModel(c), hashMap);
//        Call<String> call = service.getStringScalar(new SendToPhoneModel(""),hashMap);

//        Call<String> call = service.getStringScalar(new SendToPhoneModel(my_card_no, s_phone,s_sim_type, s_encryptedvalu, my_card_exp_date, s_amount, uuid, "topUp"), hashMap);
        Call<String> call = service.getStringScalar(Car_tab_layout.section, hashMap);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                try {
//                    JSONObject OBject = new JSONObject(response.body());
                    JSONArray jsonArray = new JSONArray(response.body());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject object = (JSONObject) jsonArray.get(i);
                            String message = object.getString("message");
                            String id = object.getString("id");
                            String title = object.getString("title");
                            String price = object.getString("price");
                            String image = object.getString("img1");


                            if (message.equals("yes")) {
                                post_id.add(i, id.toString().trim());
                                post_title.add(i, title.toString().trim());
                                post_price.add(i, price.toString().trim());
                                post_img1.add(i, image.toString().trim());

                                hideDialog();


                            } else {
                                hideDialog();
                                Toast.makeText(getContext(), getResources().getString(R.string.no_prmium), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            hideDialog();
                            Toast.makeText(getContext(), getResources().getString(R.string.no_prmium), Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);
                            e.printStackTrace();
                        }
                    }
                    if (post_id.isEmpty()) {
                        hideDialog();
                        Toast.makeText(getContext(), getResources().getString(R.string.no_prmium), Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    } else {
                        hideDialog();
                        listView.setAdapter(customAdapter);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    hideDialog();
                    Toast.makeText(getContext(), getString(R.string.login_error_server_not_found), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                hideDialog();
                Toast.makeText(getContext(), getString(R.string.login_error_server_not_found), Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    //Array Request
    private void getPosts() {
        if (URL.isNetworkConnected(getContext())) {
            if (!URL.isInternetAvailable()) {
                swipeRefreshLayout.setRefreshing(true);
                showDialog();
                final HashMap<String, String> params = new HashMap<String, String>();
                params.put("section", Car_tab_layout.section);
                final JSONObject jsonObject = new JSONObject(params);

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL.GET_POSTS_URL,jsonObject, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = (JSONObject) response.get(i);
                                String message = object.getString("message");
                                String id = object.getString("id");
                                String title = object.getString("title");
                                String price = object.getString("price");
                                String image = object.getString("img1");




                                if (message.equals("yes")) {
                                    post_id.add(i,id.toString().trim());
                                    post_title.add(i,title.toString().trim());
                                    post_price.add(i,price.toString().trim());
                                    post_img1.add(i,image.toString().trim());

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
                        if(post_id.isEmpty()){
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
    }

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

    ////////////////

}
