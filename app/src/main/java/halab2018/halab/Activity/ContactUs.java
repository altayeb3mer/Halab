package halab2018.halab.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import halab2018.halab.ContactDetails;
import halab2018.halab.Login;
import halab2018.halab.MainActivity;
import halab2018.halab.MySingleton;
import halab2018.halab.R;
import halab2018.halab.SharePref;
import halab2018.halab.URL;
import halab2018.halab.Utils.RetroApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ContactUs extends AppCompatActivity {
    AppCompatButton more_contact_details_btn,send_msg;
    Toolbar toolbar;

    ProgressDialog progressDialog;
    AppCompatEditText name,phone,email,message;
    String s_name,s_phone,s_email,s_message;

    public static final int REQUEST_CALL = 1;

    CardView map_cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);

        map_cardView=(CardView)findViewById(R.id.map_cardview);
        map_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:25.288851,51.536203"));
                i.setClassName("com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity");
                startActivity(i);
            }
        });

        name=(AppCompatEditText)findViewById(R.id.register_name_edt);
        phone=(AppCompatEditText)findViewById(R.id.reg_phone_edt);
        email=(AppCompatEditText)findViewById(R.id.reg_email_edt);
        message=(AppCompatEditText)findViewById(R.id.reg_message_edt);

        //P_Dialog
        progressDialog = new ProgressDialog(ContactUs.this);
        progressDialog.setMessage(getResources().getString(R.string.progressDialog_txt));
        progressDialog.setCancelable(false);

        toolbar=(Toolbar)findViewById(R.id.contact_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_on_back_press);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        more_contact_details_btn=(AppCompatButton)findViewById(R.id.contact_more_contact_details_btn);
        more_contact_details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ContactDetails.class);
                startActivity(i);
            }
        });

        send_msg=(AppCompatButton)findViewById(R.id.reg_send_msg_btn);
        send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              SendMsg();
                if (URL.isNetworkConnected(getApplicationContext())) {
                    if (!URL.isInternetAvailable()) {
                        s_name =name.getText().toString().trim();
                        s_phone = phone.getText().toString().trim();
                        s_email = email.getText().toString().trim();

                        s_message = message.getText().toString().trim();

                        if (s_name.equals("") || s_phone.equals("")||s_email.equals("")||s_message.equals("")) {
                            buildAlertMessage("input_error");
                        }else {
                            sendMsgRetro();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
                }
            }


        });
    }

    private void sendMsgRetro() {
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
        showDialog();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.ROOT_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetroApi.contactUs service = retrofit.create(RetroApi.contactUs.class);

        HashMap hashMap = new HashMap();
        hashMap.put("Content-Type", "application/json;charset=UTF-8");
//                Call<TransResult> call = service.getStringScalar(new SendToCard(cardModel.getCard_no(), s_card_no, "000", cardModel.getMonth() + cardModel.getYear(), s_money, uuid, "moneyTransfer"));
//        Call<String> call = service.getStringScalar(new SendToPhoneModel(c), hashMap);
//        Call<String> call = service.getStringScalar(new SendToPhoneModel(""),hashMap);

//        Call<String> call = service.getStringScalar(new SendToPhoneModel(my_card_no, s_phone,s_sim_type, s_encryptedvalu, my_card_exp_date, s_amount, uuid, "topUp"), hashMap);
        Call<String> call = service.getStringScalar(s_name,s_phone,s_email,s_message);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.body());
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String code = jsonObject.getString("code");
                    hideDialog();
                    buildAlertMessage(code);
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

    private void SendMsg(){
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(ContactUs.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ContactUs.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CALL);
                } else {
                    s_name =name.getText().toString().trim();
                    s_phone = phone.getText().toString().trim();
                    s_email = email.getText().toString().trim();

                    s_message = message.getText().toString().trim();

                    if (s_name.equals("") || s_phone.equals("")||s_email.equals("")||s_message.equals("")) {
                        buildAlertMessage("input_error");
                    } else {
                            showDialog();
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.SEND_MESSAGE_URL,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONArray jsonArray = new JSONArray(response);
                                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                                String code = jsonObject.getString("code");
                                                hideDialog();
                                                buildAlertMessage(code);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    hideDialog();
                                    Toast.makeText(getApplicationContext(), getString(R.string.login_error_server_not_found), Toast.LENGTH_SHORT).show();
                                    error.printStackTrace();

                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("name", s_name);
                                    params.put("phone", s_phone);
                                    params.put("email", s_email);
                                    params.put("message", s_message);
                                    return params;
                                }
                            };
                            MySingleton.getmInstance(ContactUs.this).addToRequestque(stringRequest);
                       }

                }
            } else {
                Toast.makeText(ContactUs.this, getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ContactUs.this, getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
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
    // builder dialog
    protected void buildAlertMessage(final String code) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (code.equals("input_error")) {
            builder.setTitle(getResources().getString(R.string.reg_error_builder_title));
            builder.setMessage(getResources().getString(R.string.some_field_is_empty));
        } else if (code.equals("no")) {
            builder.setTitle(getResources().getString(R.string.reg_error_builder_title));
            builder.setMessage(getResources().getString(R.string.send_failed));
        } else if (code.equals("yes")) {
            builder.setMessage(getResources().getString(R.string.send_seccess));
        }

        builder.setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.login_error_builder_ok_btn), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        if (code.equals("input_error")) {

                        } else if (code.equals("no")) {


                        } else if (code.equals("yes")) {
                            name.setText("");
                            phone.setText("");
                            email.setText("");
                            message.setText("");
                        }

                    }
                });
        final AlertDialog alert = builder.create();
        hideDialog();
        alert.show();
    }

}
