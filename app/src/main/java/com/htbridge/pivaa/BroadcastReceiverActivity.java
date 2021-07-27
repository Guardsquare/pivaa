package com.htbridge.pivaa;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.htbridge.pivaa.handlers.MenuHandler;

import java.io.FileOutputStream;
import java.io.PrintWriter;

public class BroadcastReceiverActivity extends AppCompatActivity {
    private String location;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MenuHandler menuHandler = new MenuHandler();
        return menuHandler.route(BroadcastReceiverActivity.this, item.getItemId());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        location = this.getExternalFilesDir(null) + "/broadcast.html";

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(location, false);
            PrintWriter pw = new PrintWriter(fos);
            pw.println("Your new file");
            pw.flush();
            pw.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



        // send broadcast
        Button mWebviewButtonLink1 = (Button) findViewById(R.id.button_broadcast_receiver);
        mWebviewButtonLink1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText mBroadcastInputView = (EditText) findViewById(R.id.input_broadcast_receiver);
                String input_broadcast = mBroadcastInputView.getText().toString();


                Intent intent = new Intent();
                intent.setAction("service.vulnerable.vulnerableservice.LOG");
                intent.putExtra("data", input_broadcast);
                intent.putExtra("location", location);

                sendBroadcast(intent);
            }

        });



    }


}
