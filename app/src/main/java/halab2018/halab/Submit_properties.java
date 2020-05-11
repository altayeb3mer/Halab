package halab2018.halab;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Submit_properties extends AppCompatActivity {
    Toolbar toolbar;
    //spinner declaration
    Spinner status,type,rooms,building_age,bedrooms,bathrooms;
    String[] status_array,type_array,rooms_array,building_age_array,bedrooms_array,bathrooms_array;
    String pro_status,pro_type,pro_rooms,pro_building_age,pro_bedrooms,pro_bathrooms;
    //image and btn
    ImageView sub_pro_image_view,sub_pro_image_view2,sub_pro_image_view3;
    AppCompatButton sub_pro_select_image_btn,sub_pro_select_image_btn2,sub_pro_select_image_btn3;
    Bitmap bitmap, resizedbitmap;
    final int IMG_REQUEST = 1;
    final int REQUEST_CALL = 1;

    int temp=0;
    boolean btn=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_properties);
        //
        sub_pro_image_view=(ImageView)findViewById(R.id.submit_properties_img_view);
        sub_pro_image_view2=(ImageView)findViewById(R.id.new_post_img_view2);
        sub_pro_image_view3=(ImageView)findViewById(R.id.new_post_img_view3);
        sub_pro_select_image_btn=(AppCompatButton)findViewById(R.id.submit_properties_select_img_btn);
        sub_pro_select_image_btn2=(AppCompatButton)findViewById(R.id.submit_properties_select_img_btn2);
        sub_pro_select_image_btn3=(AppCompatButton)findViewById(R.id.submit_properties_select_img_btn3);
        sub_pro_select_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn=true;
                temp=1;
                selectImg();
            }
        });
        sub_pro_select_image_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn=true;
                temp=2;
                selectImg();
            }
        });
        sub_pro_select_image_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn=true;
                temp=3;
                selectImg();
            }
        });


        sub_pro_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp=1;
                ChangeImagebuildAlert();
            }
        });
        sub_pro_image_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp=2;
                ChangeImagebuildAlert();
            }
        });
        sub_pro_image_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp=3;
                ChangeImagebuildAlert();
            }
        });


        //spinner declaration
        status = (Spinner) findViewById(R.id.sub_pro_status_spinner);
        status_array = getResources().getStringArray(R.array.pro_status);
        type = (Spinner) findViewById(R.id.sub_pro_type_spinner);
        type_array = getResources().getStringArray(R.array.pro_type);
        rooms = (Spinner) findViewById(R.id.sub_pro_rooms_spinner);
        rooms_array = getResources().getStringArray(R.array.pro_rooms);
        //
        building_age = (Spinner) findViewById(R.id.sub_pro_B_ageO_spinner);
        building_age_array = getResources().getStringArray(R.array.sub_pro_building_age);
        bedrooms = (Spinner) findViewById(R.id.sub_pro_bedroomsO_spinner);
        bedrooms_array = getResources().getStringArray(R.array.sub_pro_bedrooms_age);
        bathrooms = (Spinner) findViewById(R.id.sub_pro_bathroomsO_spinner);
        bathrooms_array = getResources().getStringArray(R.array.sub_pro_bathrooms_age);
        SetSpinnerProStatus();
        SetSpinnerProType();
        SetSpinnerProRooms();
        SetSpinnerProBuildingAge();
        SetSpinnerProBedrooms();
        SetSpinnerProBathrooms();


        //toolbar event
        toolbar=(Toolbar)findViewById(R.id.submit_properties_toolbar);
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
    public void SetSpinnerProStatus() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,status_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(adapter);
        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pro_status = status_array[position].toString().trim();
                Toast.makeText(getApplicationContext(),pro_status , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                status.setSelection(0);
            }
        });
    }
    //Spinner handler pro_type
    public void SetSpinnerProType() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,type_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pro_type = type_array[position].toString().trim();
                Toast.makeText(getApplicationContext(),pro_type , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                type.setSelection(0);
            }
        });
    }
    //Spinner handler pro_rooms
    public void SetSpinnerProRooms() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,rooms_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rooms.setAdapter(adapter);
        rooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pro_rooms = rooms_array[position].toString().trim();
                Toast.makeText(getApplicationContext(),pro_rooms , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                rooms.setSelection(0);
            }
        });
    }
    //Spinner handler pro_Building_Age
    public void SetSpinnerProBuildingAge() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,building_age_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        building_age.setAdapter(adapter);
        building_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pro_building_age = building_age_array[position].toString().trim();
                Toast.makeText(getApplicationContext(),pro_building_age , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                building_age.setSelection(0);
            }
        });
    }
    //Spinner handler pro_Bedrooms
    public void SetSpinnerProBedrooms() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,bedrooms_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bedrooms.setAdapter(adapter);
        bedrooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pro_bedrooms = bedrooms_array[position].toString().trim();
                Toast.makeText(getApplicationContext(),pro_bedrooms , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bedrooms.setSelection(0);
            }
        });
    }
    //Spinner handler pro_Bathrooms
    public void SetSpinnerProBathrooms() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,bathrooms_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bathrooms.setAdapter(adapter);
        bathrooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pro_bathrooms = bathrooms_array[position].toString().trim();
                Toast.makeText(getApplicationContext(),pro_bathrooms , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                bathrooms.setSelection(0);
            }
        });
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
        if (ContextCompat.checkSelfPermission(Submit_properties.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Submit_properties.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CALL);
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
                resizedbitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                switch (temp){
                    case 1:{
                        sub_pro_image_view.setImageBitmap(resizedbitmap);
                        sub_pro_image_view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        sub_pro_image_view.setVisibility(View.VISIBLE);
                        if(btn==true) {
                            btn=false;
                            sub_pro_select_image_btn.setVisibility(View.GONE);
                            sub_pro_select_image_btn2.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    case 2:{
                        sub_pro_image_view2.setImageBitmap(resizedbitmap);
                        sub_pro_image_view2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        sub_pro_image_view2.setVisibility(View.VISIBLE);
                        if(btn==true) {
                            btn=false;
                            sub_pro_select_image_btn2.setVisibility(View.GONE);
                            sub_pro_select_image_btn3.setVisibility(View.VISIBLE);
                        }
                        break;
                    }
                    case 3:{
                        sub_pro_image_view3.setImageBitmap(resizedbitmap);
                        sub_pro_image_view3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        sub_pro_image_view3.setVisibility(View.VISIBLE);
                        if(btn==true) {
                            btn=false;
                            sub_pro_select_image_btn3.setVisibility(View.GONE);
                        }
                        break;
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
