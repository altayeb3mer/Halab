package halab2018.halab;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

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

import java.util.HashMap;
import java.util.Map;

import halab2018.halab.Utils.RetroApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Login extends AppCompatActivity {
    TextView sign_up_txt;
    Toolbar toolbar;

    AppCompatButton login;
    AppCompatEditText username, password;
    String s_username, s_password;
    public static final int REQUEST_CALL = 1;
    ProgressDialog progressDialog;

    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (AppCompatEditText) findViewById(R.id.login_username_edt);
        password = (AppCompatEditText) findViewById(R.id.login_password_edt);

        login = (AppCompatButton) findViewById(R.id.login_login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                login();
                if (URL.isNetworkConnected(getApplicationContext())) {
                    if (!URL.isInternetAvailable()) {
                        s_username = username.getText().toString().trim();
                        s_password = password.getText().toString().trim();

                        if (s_username.equals("") || s_password.equals("")) {
                            Toast.makeText(Login.this, getResources().getString(R.string.some_field_is_empty), Toast.LENGTH_SHORT).show();
                        }else {
                            loginRetro();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
                }
            }
        });


        //P_Dialog
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage(getResources().getString(R.string.progressDialog_txt));
        progressDialog.setCancelable(false);

        builder = new AlertDialog.Builder(Login.this);

        toolbar=(Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_on_back_press);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        sign_up_txt=(TextView)findViewById(R.id.login_sign_up_txt);
        sign_up_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
                finish();
            }
        });
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

    public void displayAlert(String message) {
        builder.setMessage(message);
        builder.setPositiveButton(getResources().getString(R.string.login_error_builder_ok_btn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                password.setText("");

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void loginRetro() {
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

        RetroApi.login service = retrofit.create(RetroApi.login.class);

        HashMap hashMap = new HashMap();
        hashMap.put("Content-Type", "application/json;charset=UTF-8");
//                Call<TransResult> call = service.getStringScalar(new SendToCard(cardModel.getCard_no(), s_card_no, "000", cardModel.getMonth() + cardModel.getYear(), s_money, uuid, "moneyTransfer"));
//        Call<String> call = service.getStringScalar(new SendToPhoneModel(c), hashMap);
//        Call<String> call = service.getStringScalar(new SendToPhoneModel(""),hashMap);

//        Call<String> call = service.getStringScalar(new SendToPhoneModel(my_card_no, s_phone,s_sim_type, s_encryptedvalu, my_card_exp_date, s_amount, uuid, "topUp"), hashMap);
        Call<String> call = service.getStringScalar(s_username,s_password,hashMap);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.body());
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String code = jsonObject.getString("code");

                    if (code.equals("1")) {
                        Toast.makeText(Login.this, getResources().getString(R.string.login_seccess), Toast.LENGTH_SHORT).show();
                        String server_username = jsonObject.getString("username");
                        String server_user_id = jsonObject.getString("user_id");
                        SharePref.SaveUsername(server_username,server_user_id,Login.this);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        hideDialog();
                        finish();
                    } else if(code.equals("0")){
                        hideDialog();
                        builder.setTitle(getResources().getString(R.string.login_error_builder_title));
                        displayAlert(getResources().getString(R.string.login_error_builder_message));
                    }


                } catch (JSONException e) {
                    hideDialog();
                    Toast.makeText(getApplicationContext(), getString(R.string.login_error_server_not_found)+e.toString(), Toast.LENGTH_SHORT).show();
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

    private void login() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(Login.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CALL);
                } else {
                    s_username = username.getText().toString().trim();
                    s_password = password.getText().toString().trim();

                    if (s_username.equals("") || s_password.equals("")) {
                        Toast.makeText(Login.this, getResources().getString(R.string.some_field_is_empty), Toast.LENGTH_SHORT).show();

                    } else {
                        showDialog();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.LOGIN_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONArray jsonArray = new JSONArray(response);
                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            String code = jsonObject.getString("code");

                                            if (code.equals("1")) {
                                                Toast.makeText(Login.this, getResources().getString(R.string.login_seccess), Toast.LENGTH_SHORT).show();
                                                String server_username = jsonObject.getString("username");
                                                String server_user_id = jsonObject.getString("user_id");
                                                SharePref.SaveUsername(server_username,server_user_id,Login.this);
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                hideDialog();
                                                finish();
                                            } else if(code.equals("0")){
                                                hideDialog();
                                                builder.setTitle(getResources().getString(R.string.login_error_builder_title));
                                                displayAlert(getResources().getString(R.string.login_error_builder_message));
                                            }


                                        } catch (JSONException e) {
                                            hideDialog();
                                            Toast.makeText(getApplicationContext(), getString(R.string.login_error_server_not_found)+e.toString(), Toast.LENGTH_SHORT).show();
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
                                params.put("username", s_username);
                                params.put("password", s_password);
                                return params;
                            }
                        };
                        MySingleton.getmInstance(Login.this).addToRequestque(stringRequest);
                    }
                }
            } else {
                Toast.makeText(Login.this, getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Login.this, getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
        }

    }

}
