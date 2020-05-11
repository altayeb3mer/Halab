package halab2018.halab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class About extends AppCompatActivity {
    TextView isn_website;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        //toolbar event
        toolbar=(Toolbar)findViewById(R.id.about_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_on_back_press);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        isn_website=(TextView)findViewById(R.id.isn_website);
        isn_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://" +isn_website.getText().toString().trim();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });


    }
}