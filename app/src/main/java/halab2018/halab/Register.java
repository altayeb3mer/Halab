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

public class Register extends AppCompatActivity {
    TextView sign_in_txt;
    Toolbar toolbar;

    AppCompatEditText first_name,last_name,email,password,confirm_password;
    AppCompatButton register;
    String s_username_name,s_last_name,s_email,s_password,s_confirm_password;

    ProgressDialog progressDialog;

    public static final int REQUEST_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);



        //P_Dialog
        progressDialog = new ProgressDialog(Register.this);
        progressDialog.setMessage(getResources().getString(R.string.progressDialog_txt));
        progressDialog.setCancelable(false);


        first_name=(AppCompatEditText)findViewById(R.id.register_username_edt);
        last_name=(AppCompatEditText)findViewById(R.id.reg_lastname_edt);
        email=(AppCompatEditText)findViewById(R.id.reg_email_edt);
        password=(AppCompatEditText)findViewById(R.id.reg_password_edt);
        confirm_password=(AppCompatEditText)findViewById(R.id.reg_confirm_password_edt);

        register=(AppCompatButton)findViewById(R.id.reg_btn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Registration();
                if (URL.isNetworkConnected(getApplicationContext())) {
                    if (!URL.isInternetAvailable()) {
                        s_username_name = first_name.getText().toString().trim();
                        s_email = email.getText().toString().trim();
                        s_password = password.getText().toString().trim();
                        s_confirm_password = confirm_password.getText().toString().trim();

                        if (s_username_name.equals("") || s_password.equals("")||s_email.equals("")||s_confirm_password.equals("")) {
                            buildAlertMessage("input_error");
                        }else {
                            registrationRetro();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
                }
                //// TODO: 11/11/2018 Function
            }
        });

        toolbar=(Toolbar)findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_on_back_press);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        sign_in_txt=(TextView)findViewById(R.id.reg_sign_in_txt);
        sign_in_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Login.class);
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


    // builder dialog
    protected void buildAlertMessage(final String code) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (code.equals("input_error")) {
            builder.setTitle(getResources().getString(R.string.reg_error_builder_title));
            builder.setMessage(getResources().getString(R.string.some_field_is_empty));
        } else if (code.equals("reg_failed")) {
            builder.setTitle(getResources().getString(R.string.reg_error_builder_title));
            builder.setMessage(getResources().getString(R.string.exist_user));
        } else if (code.equals("reg_success")) {
            builder.setMessage(getResources().getString(R.string.registeration_done));
        }

        builder.setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.login_error_builder_ok_btn), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        if (code.equals("input_error")) {

                        } else if (code.equals("reg_failed")) {
                            password.setText("");
                            confirm_password.setText("");

                        } else if (code.equals("reg_success")) {
                            startActivity(new Intent(getApplicationContext(),Login.class));
                            finish();
                        }

                    }
                });
        final AlertDialog alert = builder.create();
        hideDialog();
        alert.show();
    }

    private void registrationRetro() {
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

        RetroApi.registration service = retrofit.create(RetroApi.registration.class);

        HashMap hashMap = new HashMap();
        hashMap.put("Content-Type", "application/json;charset=UTF-8");
//                Call<TransResult> call = service.getStringScalar(new SendToCard(cardModel.getCard_no(), s_card_no, "000", cardModel.getMonth() + cardModel.getYear(), s_money, uuid, "moneyTransfer"));
//        Call<String> call = service.getStringScalar(new SendToPhoneModel(c), hashMap);
//        Call<String> call = service.getStringScalar(new SendToPhoneModel(""),hashMap);

//        Call<String> call = service.getStringScalar(new SendToPhoneModel(my_card_no, s_phone,s_sim_type, s_encryptedvalu, my_card_exp_date, s_amount, uuid, "topUp"), hashMap);
        Call<String> call = service.getStringScalar(s_username_name,s_email,s_password);
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

//Registration Function
    private void Registration() {

        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(Register.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Register.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CALL);
                } else {
                    s_username_name = first_name.getText().toString().trim();
                    s_email = email.getText().toString().trim();
                    s_password = password.getText().toString().trim();
                    s_confirm_password = confirm_password.getText().toString().trim();

                    if (s_username_name.equals("") || s_password.equals("")||s_email.equals("")||s_confirm_password.equals("")) {
                        buildAlertMessage("input_error");
                    } else {
                        if(s_password.equals(s_confirm_password)){
                            showDialog();
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.REG_URL,
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
                                    params.put("username", s_username_name);
                                    //params.put("last_name", s_last_name);
                                    params.put("email", s_email);
                                    params.put("password", s_password);
                                    return params;
                                }
                            };
                            MySingleton.getmInstance(Register.this).addToRequestque(stringRequest);
                        }else{
                            Toast.makeText(Register.this, getResources().getString(R.string.reg_password_doesnt_match), Toast.LENGTH_SHORT).show();
                        }}

                }
            } else {
                Toast.makeText(Register.this, getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Register.this, getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
        }

    }
    /////////


}
