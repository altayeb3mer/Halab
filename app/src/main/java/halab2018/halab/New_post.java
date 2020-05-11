package halab2018.halab;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class New_post extends AppCompatActivity {
    ProgressDialog progressDialog;
    Toolbar toolbar;
    Spinner section;
    String[] section_array,section_array_db;

    ImageView new_post_image_view,new_post_image_view2,new_post_image_view3,
    new_post_image_view4,new_post_image_view5;
    AppCompatButton new_post_select_image_btn,new_post_select_image_btn2,new_post_select_image_btn3,new_post_select_image_btn4,
            new_post_select_image_btn5,
    add_new_post;
    Bitmap bitmap, resizedbitmap,bitmap1,bitmap2,bitmap3,bitmap4,bitmap5;
    final int IMG_REQUEST = 1;
    final int REQUEST_CALL = 1;

    int temp=0;
    boolean btn=false;
    int img_count;

    AppCompatEditText new_post_title,new_post_description,new_post_name,new_post_email,new_post_phone,new_post_price,
            new_post_location;
    String s_section,s_new_post_title, s_new_post_description,
            s_new_post_name,s_new_post_email,s_new_post_phone,s_new_post_price,
            bundle_activity,my_added_post_id,
            img1,img2,img3,img4,img5
            ,s_new_post_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_post);



        Bundle bundle = getIntent().getExtras();
        bundle_activity = bundle.getString("activity");


        //P_Dialog
        progressDialog = new ProgressDialog(New_post.this);
        progressDialog.setMessage(getResources().getString(R.string.progressDialog_txt));
        progressDialog.setCancelable(false);

        new_post_title=(AppCompatEditText)findViewById(R.id.new_post_title_edt);
        new_post_description=(AppCompatEditText)findViewById(R.id.new_post_desciption_edt);
        new_post_name=(AppCompatEditText)findViewById(R.id.new_post_name_edt);
        new_post_email=(AppCompatEditText)findViewById(R.id.new_post_email_edt);
        new_post_phone=(AppCompatEditText)findViewById(R.id.new_post_phone_edt);
        new_post_price=(AppCompatEditText)findViewById(R.id.new_post_price_edt);
        new_post_location=(AppCompatEditText)findViewById(R.id.new_post_location_edt);

        new_post_image_view=(ImageView)findViewById(R.id.new_post_img_view);
        new_post_image_view2=(ImageView)findViewById(R.id.new_post_img_view2);
        new_post_image_view3=(ImageView)findViewById(R.id.new_post_img_view3);
        new_post_image_view4=(ImageView)findViewById(R.id.new_post_img_view4);
        new_post_image_view5=(ImageView)findViewById(R.id.new_post_img_view5);
        new_post_select_image_btn=(AppCompatButton)findViewById(R.id.new_post_select_img_btn);
        new_post_select_image_btn2=(AppCompatButton)findViewById(R.id.new_post_select_img_btn2);
        new_post_select_image_btn3=(AppCompatButton)findViewById(R.id.new_post_select_img_btn3);
        new_post_select_image_btn4=(AppCompatButton)findViewById(R.id.new_post_select_img_btn4);
        new_post_select_image_btn5=(AppCompatButton)findViewById(R.id.new_post_select_img_btn5);

        add_new_post=(AppCompatButton)findViewById(R.id.new_post_btn);
        ////////////UPDATE CONDITION
        if (bundle_activity.equals("my_added")) {
            my_added_post_id = bundle.getString("my_post_id");
            add_new_post.setText(getResources().getString(R.string.Update_my_post));
            editMyPostState();
        }
        add_new_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bundle_activity.equals("my_added")){
                    UpdatePost();
                }else{
                    SubmitPost();
                }



            }
        });

        new_post_select_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_count=1;
                btn=true;
                temp=1;
                selectImg();
            }
        });
        new_post_select_image_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_count=2;
                btn=true;
                temp=2;
                selectImg();
            }
        });
        new_post_select_image_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_count=3;
                btn=true;
                temp=3;
                selectImg();
            }
        });
        new_post_select_image_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_count=4;
                btn=true;
                temp=4;
                selectImg();
            }
        });
        new_post_select_image_btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_count=5;
                btn=true;
                temp=5;
                selectImg();
            }
        });


        new_post_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp=1;
                ChangeImagebuildAlert();
            }
        });
        new_post_image_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp=2;
                ChangeImagebuildAlert();
            }
        });
        new_post_image_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp=3;
                ChangeImagebuildAlert();
            }
        });
        new_post_image_view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp=4;
                ChangeImagebuildAlert();
            }
        });
        new_post_image_view5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp=5;
                ChangeImagebuildAlert();
            }
        });

        section_array=getResources().getStringArray(R.array.section);
        section_array_db=getResources().getStringArray(R.array.section_db);
        section = (Spinner)findViewById(R.id.new_post_spinner);
        SetSpinnerProSection();


        toolbar=(Toolbar)findViewById(R.id.new_post_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_on_back_press);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });







    }


    //Spinner handler pro_status
    public void SetSpinnerProSection() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,section_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        section.setAdapter(adapter);
        section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // pro_status = status_array[position].toString().trim();
                s_section=String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                section.setSelection(0);
            }
        });
    }
    //Convert image to string
    private String imgTostring(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    //Select image from gellary
    private void selectImg() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (ContextCompat.checkSelfPermission(New_post.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(New_post.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CALL);
        }else {
            startActivityForResult(intent, IMG_REQUEST);
        }}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                resizedbitmap=Bitmap.createScaledBitmap(bitmap, 300,300, false);
                switch (temp){
                    case 1:{
                        bitmap1=resizedbitmap;
                        new_post_image_view.setImageBitmap(bitmap1);
                        new_post_image_view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        new_post_image_view.setVisibility(View.VISIBLE);
                        if(btn==true) {
                            btn=false;
                            new_post_select_image_btn.setVisibility(View.GONE);
                            new_post_select_image_btn2.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    case 2:{
                        bitmap2=resizedbitmap;
                        new_post_image_view2.setImageBitmap(bitmap2);
                        new_post_image_view2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        new_post_image_view2.setVisibility(View.VISIBLE);
                        if(btn==true) {
                            btn=false;
                            new_post_select_image_btn2.setVisibility(View.GONE);
                            new_post_select_image_btn3.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    case 3:{
                        bitmap3=resizedbitmap;
                        new_post_image_view3.setImageBitmap(bitmap3);
                        new_post_image_view3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        new_post_image_view3.setVisibility(View.VISIBLE);
                        if(btn==true) {
                            btn=false;
                            new_post_select_image_btn3.setVisibility(View.GONE);
                            new_post_select_image_btn4.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    case 4:{
                        bitmap4=resizedbitmap;
                        new_post_image_view4.setImageBitmap(bitmap4);
                        new_post_image_view4.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        new_post_image_view4.setVisibility(View.VISIBLE);
                        if(btn==true) {
                            btn=false;
                            new_post_select_image_btn4.setVisibility(View.GONE);
                            new_post_select_image_btn5.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    case 5:{
                        bitmap5=resizedbitmap;
                        new_post_image_view5.setImageBitmap(bitmap5);
                        new_post_image_view5.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        new_post_image_view5.setVisibility(View.VISIBLE);
                        if(btn==true) {
                            btn=false;
                            new_post_select_image_btn5.setVisibility(View.GONE);
                        }
                        break;
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //Exit builder dialog
    private void ChangeImagebuildAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.dialog_msg_chose_another_img))
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        selectImg();
                        // TODO: 9/14/2018 Call the selecting image function
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

    // builder dialog
    protected void buildAlertMessage(final String code) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (code.equals("input_error")) {
            builder.setTitle(getResources().getString(R.string.reg_error_builder_title));
            builder.setMessage(getResources().getString(R.string.some_field_is_empty));
        } else if (code.equals("add_failed")) {
            builder.setTitle(getResources().getString(R.string.reg_error_builder_title));
            builder.setMessage(getResources().getString(R.string.add_pro_builder_unknown_error));
        } else if (code.equals("add_success")) {
            builder.setMessage(getResources().getString(R.string.add_pro_builder_add_success));
        }
     else if (code.equals("update_success")) {
        builder.setMessage(getResources().getString(R.string.update_success));
    }

        //update_success

        builder.setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.login_error_builder_ok_btn), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        if (code.equals("input_error")) {

                        } else if (code.equals("add_failed")) {

                        }
                     else if (code.equals("update_success")) {
                            if(SharePref.Is_Logged_in(New_post.this)) {
                                Toast.makeText(New_post.this, getResources().getString(R.string.update_success), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                    }
                    else if (code.equals("add_success")) {
                            if(SharePref.Is_Logged_in(New_post.this)){
                                Toast.makeText(New_post.this, getResources().getString(R.string.add_pro_builder_add_success), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                    }
                });
        final AlertDialog alert = builder.create();
        hideDialog();
        alert.show();
    }

    ///////////
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

    public void SubmitPost() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(New_post.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(New_post.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CALL);
                } else {
                    s_new_post_title = new_post_title.getText().toString().trim();
                    s_new_post_description = new_post_description.getText().toString().trim();
                    s_new_post_name = new_post_name.getText().toString().trim();
                    s_new_post_email = new_post_email.getText().toString().trim();
                    s_new_post_phone = new_post_phone.getText().toString().trim();
                    s_new_post_price = new_post_price.getText().toString().trim();
                    s_new_post_location = new_post_location.getText().toString().trim();
                    if (s_new_post_title.equals("") || s_new_post_description.equals("") || s_new_post_name.equals("") || s_new_post_email.equals("") || s_new_post_phone.equals("")
                            || s_new_post_price.equals("")|| s_new_post_location.equals("")
                    ||s_section.equals("0")
                         ) {
                        buildAlertMessage("input_error");

                    } else {

                        showDialog();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.NEW_POST_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONArray jsonArray = new JSONArray(response);
                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            String code = jsonObject.getString("code");

                                            buildAlertMessage(code);

                                        } catch (JSONException e) {
                                            hideDialog();
                                            Toast.makeText(New_post.this, "Something went wrong", Toast.LENGTH_SHORT).show();
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
                        })

                        {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();

                                params.put("img_count", String.valueOf(temp));
                                params.put("section_id", s_section);


                                switch (temp){
                                    case 1:{
                                        params.put("image1",imgTostring(bitmap1));
                                        params.put("image2","null");
                                        params.put("image3","null");
                                        params.put("image4","null");
                                        params.put("image5","null");
                                        break;
                                    }
                                    case 2:{
                                        params.put("image1",imgTostring(bitmap1));
                                        params.put("image2",imgTostring(bitmap2));
                                        params.put("image3","null");
                                        params.put("image4","null");
                                        params.put("image5","null");
                                        break;
                                    }
                                    case 3:{
                                        params.put("image1",imgTostring(bitmap1));
                                        params.put("image2",imgTostring(bitmap2));
                                        params.put("image3",imgTostring(bitmap3));
                                        params.put("image4","null");
                                        params.put("image5","null");
                                        break;
                                    }
                                    case 4:{
                                        params.put("image1",imgTostring(bitmap1));
                                        params.put("image2",imgTostring(bitmap2));
                                        params.put("image3",imgTostring(bitmap3));
                                        params.put("image4",imgTostring(bitmap4));
                                        params.put("image5","null");
                                        break;
                                    }
                                    case 5:{
                                        params.put("image1",imgTostring(bitmap1));
                                        params.put("image2",imgTostring(bitmap2));
                                        params.put("image3",imgTostring(bitmap3));
                                        params.put("image4",imgTostring(bitmap4));
                                        params.put("image5",imgTostring(bitmap5));
                                        break;
                                    }
                                }


                                params.put("title", s_new_post_title);
                                params.put("description", s_new_post_description);
                                params.put("name", s_new_post_name);
                                params.put("email", s_new_post_email);
                                params.put("phone", s_new_post_phone);
                                params.put("price", s_new_post_price);
                                params.put("area", s_new_post_location);

                                if(SharePref.Is_Logged_in(New_post.this)) {
                                    params.put("user_id", SharePref.Get_userId(New_post.this));
                                }else{
                                    params.put("user_id", "0");
                                }



                                return params;
                            }
                        };
                       // stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        MySingleton.getmInstance(New_post.this).addToRequestque(stringRequest);


                    }
                }
            } else {
                hideDialog();
                Toast.makeText(New_post.this, getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            hideDialog();
            Toast.makeText(New_post.this, getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
        }

    }



    public void editMyPostState() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(New_post.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(New_post.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CALL);
                } else {
                    showDialog();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.GET_MY_ADDED_POST_DETAILS,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");

                                        if (code.equals("yes")) {
                                            s_section = jsonObject.getString("section_id");
                                            section.setSelection(Integer.parseInt(s_section));

                                            s_new_post_title = jsonObject.getString("title");
                                            new_post_title.setText(s_new_post_title);
                                            s_new_post_description = jsonObject.getString("description");
                                            new_post_description.setText(s_new_post_description);
                                            s_new_post_price = jsonObject.getString("price");
                                            new_post_price.setText(s_new_post_price);

                                            s_new_post_location = jsonObject.getString("area");
                                            new_post_location.setText(s_new_post_location);

                                            s_new_post_name = jsonObject.getString("name");
                                            new_post_name.setText(s_new_post_name);
                                            s_new_post_email = jsonObject.getString("email");
                                            new_post_email.setText(s_new_post_email);
                                            s_new_post_phone = jsonObject.getString("phone");
                                            new_post_phone.setText(s_new_post_phone);



                                            img1 = jsonObject.getString("img1");
                                            img2 = jsonObject.getString("img2");
                                            img3 = jsonObject.getString("img3");
                                            img4 = jsonObject.getString("img4");
                                            img5 = jsonObject.getString("img5");


                                            temp=1;
                                            Glide.with(getApplicationContext())
                                                    .load(URL.IMAGE_PATH + img1.trim())
                                                    .centerCrop()
                                                    .into(new_post_image_view);
                                            new_post_image_view.setVisibility(View.VISIBLE);
                                            new_post_select_image_btn.setVisibility(View.GONE);
                                            new_post_select_image_btn2.setVisibility(View.VISIBLE);

                                            if(!img2.equals("")){
                                                temp=2;
                                                Glide.with(getApplicationContext())
                                                        .load(URL.IMAGE_PATH + img2.trim())
                                                        .centerCrop()
                                                        .into(new_post_image_view2);
                                                new_post_image_view2.setVisibility(View.VISIBLE);
                                                new_post_select_image_btn2.setVisibility(View.GONE);
                                                new_post_select_image_btn3.setVisibility(View.VISIBLE);

                                            }
                                            if(!img3.equals("")){
                                                temp=3;
                                                Glide.with(getApplicationContext())
                                                        .load(URL.IMAGE_PATH + img3.trim())
                                                        .centerCrop()
                                                        .into(new_post_image_view3);
                                                new_post_image_view3.setVisibility(View.VISIBLE);
                                                new_post_select_image_btn3.setVisibility(View.GONE);
                                                new_post_select_image_btn4.setVisibility(View.VISIBLE);
                                            }
                                            if(!img4.equals("")){
                                                temp=4;
                                                Glide.with(getApplicationContext())
                                                        .load(URL.IMAGE_PATH + img4.trim())
                                                        .centerCrop()
                                                        .into(new_post_image_view4);
                                                new_post_image_view4.setVisibility(View.VISIBLE);
                                                new_post_select_image_btn4.setVisibility(View.GONE);
                                                new_post_select_image_btn5.setVisibility(View.VISIBLE);
                                            }
                                            if(!img5.equals("")){
                                                temp=5;
                                                Glide.with(getApplicationContext())
                                                        .load(URL.IMAGE_PATH + img5.trim())
                                                        .centerCrop()
                                                        .into(new_post_image_view5);
                                                new_post_image_view5.setVisibility(View.VISIBLE);
                                                new_post_select_image_btn5.setVisibility(View.GONE);
                                            }




                                            hideDialog();
                                        } else {
                                            hideDialog();
                                            Toast.makeText(New_post.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }


                                    } catch (JSONException e) {
                                        hideDialog();
                                        Toast.makeText(getApplicationContext(), getString(R.string.login_error_server_not_found), Toast.LENGTH_SHORT).show();
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
                            params.put("my_added_post_detail_id", my_added_post_id);
                            return params;
                        }
                    };
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000,5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    MySingleton.getmInstance(New_post.this).addToRequestque(stringRequest);

                }
            } else {
                hideDialog();
                Toast.makeText(New_post.this, getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            hideDialog();
            Toast.makeText(New_post.this, getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
        }
    }



    public void UpdatePost() {
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(New_post.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(New_post.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CALL);
                } else {
                    if(!SharePref.Is_Logged_in(New_post.this)){
                        Toast.makeText(New_post.this, getResources().getString(R.string.plz_login_first), Toast.LENGTH_SHORT).show();
                    }else{
                        new_post_image_view.buildDrawingCache();
                        bitmap1=new_post_image_view.getDrawingCache();
                        new_post_image_view2.buildDrawingCache();
                        bitmap2=new_post_image_view2.getDrawingCache();
                        new_post_image_view3.buildDrawingCache();
                        bitmap3=new_post_image_view3.getDrawingCache();
                        new_post_image_view4.buildDrawingCache();
                        bitmap4=new_post_image_view4.getDrawingCache();
                        new_post_image_view5.buildDrawingCache();
                        bitmap5=new_post_image_view5.getDrawingCache();

                        s_new_post_title = new_post_title.getText().toString().trim();
                        s_new_post_description = new_post_description.getText().toString().trim();
                        s_new_post_name = new_post_name.getText().toString().trim();
                        s_new_post_email = new_post_email.getText().toString().trim();
                        s_new_post_phone = new_post_phone.getText().toString().trim();
                        s_new_post_price = new_post_price.getText().toString().trim();
                        s_new_post_location = new_post_location.getText().toString().trim();
                        if (s_new_post_title.equals("") || s_new_post_description.equals("") || s_new_post_name.equals("") || s_new_post_email.equals("") || s_new_post_phone.equals("")
                                || s_new_post_price.equals("")|| s_new_post_location.equals("")
                                ||s_section.equals("0")
                                ) {
                            buildAlertMessage("input_error");

                        } else {

                            showDialog();
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.UPDATE_MY_POST,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONArray jsonArray = new JSONArray(response);
                                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                                String code = jsonObject.getString("code").trim();
                                                Toast.makeText(New_post.this,code, Toast.LENGTH_SHORT).show();

                                                buildAlertMessage(code);

                                            } catch (JSONException e) {
                                                hideDialog();
                                                Toast.makeText(New_post.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                                e.printStackTrace();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    hideDialog();
                                    Toast.makeText(getApplicationContext(), getString(R.string.login_error_server_not_found)+error.toString(), Toast.LENGTH_SHORT).show();
                                    error.printStackTrace();

                                }
                            })

                            {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();

                                    params.put("img_count", String.valueOf(temp));
                                    //params.put("img_count", temp);
                                    params.put("section_id", s_section);


                                    switch (temp){
                                        case 1:{
                                            params.put("image1",imgTostring(bitmap1));
                                            params.put("image2","null");
                                            params.put("image3","null");
                                            params.put("image4","null");
                                            params.put("image5","null");

                                            params.put("s_image1",img1);
                                            params.put("s_image2","null");
                                            params.put("s_image3","null");
                                            params.put("s_image4","null");
                                            params.put("s_image5","null");
                                            break;
                                        }
                                        case 2:{
                                            params.put("image1",imgTostring(bitmap1));
                                            params.put("image2",imgTostring(bitmap2));
                                            params.put("image3","null");
                                            params.put("image4","null");
                                            params.put("image5","null");

                                            params.put("s_image1",img1);
                                            params.put("s_image2",img2);
                                            params.put("s_image3","null");
                                            params.put("s_image4","null");
                                            params.put("s_image5","null");
                                            break;
                                        }
                                        case 3:{
                                            params.put("image1",imgTostring(bitmap1));
                                            params.put("image2",imgTostring(bitmap2));
                                            params.put("image3",imgTostring(bitmap3));
                                            params.put("image4","null");
                                            params.put("image5","null");

                                            params.put("s_image1",img1);
                                            params.put("s_image2",img2);
                                            params.put("s_image3",img3);
                                            params.put("s_image4","null");
                                            params.put("s_image5","null");
                                            break;
                                        }
                                        case 4:{
                                            params.put("image1",imgTostring(bitmap1));
                                            params.put("image2",imgTostring(bitmap2));
                                            params.put("image3",imgTostring(bitmap3));
                                            params.put("image4",imgTostring(bitmap4));
                                            params.put("image5","null");

                                            params.put("s_image1",img1);
                                            params.put("s_image2",img2);
                                            params.put("s_image3",img3);
                                            params.put("s_image4",img4);
                                            params.put("s_image5","null");
                                            break;
                                        }
                                        case 5:{
                                            params.put("image1",imgTostring(bitmap1));
                                            params.put("image2",imgTostring(bitmap2));
                                            params.put("image3",imgTostring(bitmap3));
                                            params.put("image4",imgTostring(bitmap4));
                                            params.put("image5",imgTostring(bitmap5));

                                            params.put("s_image1",img1);
                                            params.put("s_image2",img2);
                                            params.put("s_image3",img3);
                                            params.put("s_image4",img4);
                                            params.put("s_image5",img5);
                                            break;
                                        }
                                    }


                                    params.put("title", s_new_post_title);
                                    params.put("description", s_new_post_description);
                                    params.put("name", s_new_post_name);
                                    params.put("email", s_new_post_email);
                                    params.put("phone", s_new_post_phone);
                                    params.put("price", s_new_post_price);

                                    params.put("area", s_new_post_location);

                                    params.put("id", my_added_post_id);



                                    return params;
                                }
                            };
                            // stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                            stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000,5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                            MySingleton.getmInstance(New_post.this).addToRequestque(stringRequest);


                        }
                    }}
            } else {
                hideDialog();
                Toast.makeText(New_post.this, getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            hideDialog();
            Toast.makeText(New_post.this, getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (bundle_activity.equals("my_added")){
            getMenuInflater().inflate(R.menu.delete_post_menu,menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.delete_post_menu_id){
            //// TODO: 12/13/2018 call delete function
            buildAlertMessageDelete();
        }
        return super.onOptionsItemSelected(item);
    }


    protected void buildAlertMessageDelete() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.delete_msg))
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                      DeletePost();
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

    private void DeletePost(){

 /////////////
        if (URL.isNetworkConnected(getApplicationContext())) {
            if (!URL.isInternetAvailable()) {
                if (ContextCompat.checkSelfPermission(New_post.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(New_post.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CALL);
                } else {
                        showDialog();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.DELETE_POST_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONArray jsonArray = new JSONArray(response);
                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            String code = jsonObject.getString("code");
                                            hideDialog();
                                            if(code.equals("yes")){
                                                Toast.makeText(New_post.this,getResources().getString(R.string.delete_done), Toast.LENGTH_SHORT).show();
                                                hideDialog();
                                                finish();
                                            }else{
                                                Toast.makeText(New_post.this,getResources().getString(R.string.delete_faild), Toast.LENGTH_SHORT).show();
                                                hideDialog();
                                            }
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
                                params.put("id", my_added_post_id);
                                return params;
                            }
                        };
                        MySingleton.getmInstance(New_post.this).addToRequestque(stringRequest);


                }
            } else {
                Toast.makeText(New_post.this, getString(R.string.login_error_network_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(New_post.this, getString(R.string.login_error_no_internet_access), Toast.LENGTH_SHORT).show();
        }
        /////////////

    }





    }


