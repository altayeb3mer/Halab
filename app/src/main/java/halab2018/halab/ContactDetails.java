package halab2018.halab;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ContactDetails extends AppCompatActivity {
    Toolbar toolbar;
    TextView dial_number,send_email,map_location;
    public static final int REQUEST_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details);

        toolbar=(Toolbar)findViewById(R.id.contact_details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_on_back_press);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dial_number=(TextView)findViewById(R.id.dial_number);
        map_location=(TextView)findViewById(R.id.map_location);
        dial_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           buildAlertMessageDail();
                }
        });
        map_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("geo:25.288851,51.536203"));
                i.setClassName("com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity");
                startActivity(i);
            }
        });
        send_email=(TextView)findViewById(R.id.email_intent);
        send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ContactDetails.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ContactDetails.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CALL);
                } else {

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse("mailto:"+send_email.getText().toString().trim()));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.send_mail_extras));

                    try {
                        startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.send_intent_title)));
                    }catch (Exception e){
                        Toast.makeText(ContactDetails.this,getResources().getString(R.string.send_mail_error), Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });


    }

    protected void buildAlertMessageDail() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.dail_no)+" "+dial_number.getText().toString().trim()+"?")
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        if (ContextCompat.checkSelfPermission(ContactDetails.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ContactDetails.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                        } else {
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + dial_number.getText().toString().trim())));
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
