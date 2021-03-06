package halab2018.halab;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import halab2018.halab.Utils.RetroApi;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Post_details extends AppCompatActivity {
    Toolbar toolbar;


    ViewPager viewPager;
    SlideShow_adapter adapter;
    CircleIndicator indicator;

    android.os.Handler handler;
    Runnable runnable;
    Timer timer;

    ArrayList<String> list_images;


    TextView post_price,post_g_info,post_name,post_email,post_phone,post_location;
    String post_id,
            s_post_price,s_post_g_info,s_post_name,s_post_email,s_post_phone,s_post_location,
            img1,img2,img3,img4,img5;
    ProgressDialog progressDialog;
    public static final int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_details);

        list_images = new ArrayList<String>();


        indicator = (CircleIndicator)findViewById(R.id.indicator);


        //P_Dialog
        progressDialog = new ProgressDialog(Post_details.this);
        progressDialog.setMessage(getResources().getString(R.string.progressDialog_txt));
        progressDialog.setCancelable(false);

        toolbar=(Toolbar)findViewById(R.id.post_details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_on_back_press);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        post_price=(TextView)findViewById(R.id.post_details_price);
        post_g_info=(TextView)findViewById(R.id.post_details_g_info);
        post_name=(TextView)findViewById(R.id.post_details_name);
        post_email=(TextView)findViewById(R.id.post_details_email);
        post_phone=(TextView)findViewById(R.id.post_details_phone);
        post_location=(TextView)findViewById(R.id.post_details_location);

        Bundle bundle = getIntent().getExtras();
        post_id = bundle.getString("post_id");

//        getPostDetails();
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                //getPremium
                getPostDetRetro();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
            finish();
        }

        post_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(Post_details.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Post_details.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CALL);
                } else {

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse("mailto:"+post_email.getText().toString().trim()));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.send_mail_extras));

                    try {
                        startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.send_intent_title)));
                    }catch (Exception e){
                        Toast.makeText(Post_details.this,getResources().getString(R.string.send_mail_error), Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });
        post_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildAlertMessageDail();
            }
        });



        /////////
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

    private void slideImgs(){
        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                int i=viewPager.getCurrentItem();

                if(i==adapter.urls.size()-1){
                    i=0;
                    viewPager.setCurrentItem(i,true);
                }else{
                    i++;
                    viewPager.setCurrentItem(i,true);
                }

            }
        };
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        },4000,4000);
    }

    private void getPostDetRetro() {
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
        viewPager=(ViewPager)findViewById(R.id.postdetails_viewpager);
        showDialog();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.ROOT_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetroApi.getPostDetails service = retrofit.create(RetroApi.getPostDetails.class);

        HashMap hashMap = new HashMap();
        hashMap.put("Content-Type", "application/json;charset=UTF-8");
//                Call<TransResult> call = service.getStringScalar(new SendToCard(cardModel.getCard_no(), s_card_no, "000", cardModel.getMonth() + cardModel.getYear(), s_money, uuid, "moneyTransfer"));
//        Call<String> call = service.getStringScalar(new SendToPhoneModel(c), hashMap);
//        Call<String> call = service.getStringScalar(new SendToPhoneModel(""),hashMap);

//        Call<String> call = service.getStringScalar(new SendToPhoneModel(my_card_no, s_phone,s_sim_type, s_encryptedvalu, my_card_exp_date, s_amount, uuid, "topUp"), hashMap);
        Call<String> call = service.getStringScalar(post_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.body());
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String code = jsonObject.getString("code");
                    if (code.equals("yes")) {
                        s_post_price = jsonObject.getString("price");
                        post_price.setText(s_post_price);
                        s_post_g_info = jsonObject.getString("description");
                        post_g_info.setText(s_post_g_info);

                        s_post_location = jsonObject.getString("area");
                        post_location.setText(s_post_location);

                        s_post_name = jsonObject.getString("name");
                        post_name.setText(s_post_name);
                        s_post_email = jsonObject.getString("email");
                        post_email.setText(s_post_email);
                        s_post_phone = jsonObject.getString("phone");
                        post_phone.setText(s_post_phone);
                        img1 = jsonObject.getString("img1");
                        img2 = jsonObject.getString("img2");
                        img3 = jsonObject.getString("img3");
                        img4 = jsonObject.getString("img4");
                        img5 = jsonObject.getString("img5");




                        if(!img1.equals("")){
                            list_images.add(img1);
                        }
                        if(!img2.equals("")){
                            list_images.add(img2);
                        }
                        if(!img3.equals("")){
                            list_images.add(img3);
                        }
                        if(!img4.equals("")){
                            list_images.add(img4);
                        }
                        if(!img5.equals("")){
                            list_images.add(img5);
                        }
                        adapter=new SlideShow_adapter(getApplicationContext(),list_images);
                        viewPager.setAdapter(adapter);
                        indicator.setViewPager(viewPager);
                        if(list_images.isEmpty()){
                            TextView no_img=(TextView) findViewById(R.id.post_has_no_img);
                            no_img.setVisibility(View.VISIBLE);
                        }else{
                            slideImgs();
                        }
                        hideDialog();
                    } else {
                        Toast.makeText(Post_details.this, "Unknown error", Toast.LENGTH_SHORT).show();
                        hideDialog();
                        finish();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                hideDialog();
                finish();
                Toast.makeText(getApplicationContext(), getString(R.string.login_error_server_not_found), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getPostDetails() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(Post_details.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Post_details.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CALL);
                } else {
                    viewPager=(ViewPager)findViewById(R.id.postdetails_viewpager);
                    showDialog();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.GET_POST_DETAILS_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        if (code.equals("yes")) {
                                            s_post_price = jsonObject.getString("price");
                                            post_price.setText(s_post_price);
                                            s_post_g_info = jsonObject.getString("description");
                                            post_g_info.setText(s_post_g_info);

                                            s_post_location = jsonObject.getString("area");
                                            post_location.setText(s_post_location);

                                            s_post_name = jsonObject.getString("name");
                                            post_name.setText(s_post_name);
                                            s_post_email = jsonObject.getString("email");
                                            post_email.setText(s_post_email);
                                            s_post_phone = jsonObject.getString("phone");
                                            post_phone.setText(s_post_phone);
                                            img1 = jsonObject.getString("img1");
                                            img2 = jsonObject.getString("img2");
                                            img3 = jsonObject.getString("img3");
                                            img4 = jsonObject.getString("img4");
                                            img5 = jsonObject.getString("img5");


                                            if(!img1.equals("")){
                                               list_images.add(img1);
                                            }
                                            if(!img2.equals("")){
                                               list_images.add(img2);
                                            }
                                            if(!img3.equals("")){
                                               list_images.add(img3);
                                            }
                                            if(!img4.equals("")){
                                                list_images.add(img4);
                                            }
                                            if(!img5.equals("")){
                                                list_images.add(img5);
                                            }
                                            adapter=new SlideShow_adapter(getApplicationContext(),list_images);
                                            viewPager.setAdapter(adapter);
                                            indicator.setViewPager(viewPager);
                                            if(list_images.isEmpty()){
                                                TextView no_img=(TextView) findViewById(R.id.post_has_no_img);
                                                no_img.setVisibility(View.VISIBLE);
                                            }


                                            hideDialog();

                                        } else {
                                            Toast.makeText(Post_details.this, "Unknown error", Toast.LENGTH_SHORT).show();
                                            hideDialog();
                                            finish();

                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideDialog();
                            finish();
                            Toast.makeText(getApplicationContext(), getString(R.string.login_error_server_not_found), Toast.LENGTH_SHORT).show();
                            error.printStackTrace();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("id", post_id);
                            return params;
                        }
                    };
                    MySingleton.getmInstance(Post_details.this).addToRequestque(stringRequest);

                }
            } else {
                Toast.makeText(Post_details.this, getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Post_details.this, getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
        }

    }


    protected void buildAlertMessageDail() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.dail_no)+" "+post_phone.getText().toString().trim()+"?")
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        if (ContextCompat.checkSelfPermission(Post_details.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(Post_details.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                        } else {
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + post_phone.getText().toString().trim())));
                        }
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

}
