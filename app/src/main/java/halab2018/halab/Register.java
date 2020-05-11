package halab2018.halab;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
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
                Registration();
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
                            finish();
                        }

                    }
                });
        final AlertDialog alert = builder.create();
        hideDialog();
        alert.show();
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