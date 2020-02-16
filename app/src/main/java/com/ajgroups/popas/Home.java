package com.ajgroups.popas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import io.kommunicate.KmConversationBuilder;
import io.kommunicate.Kommunicate;
import io.kommunicate.callbacks.KmCallback;

import static android.provider.UserDictionary.Words.APP_ID;

public class Home extends AppCompatActivity {
    private FloatingActionButton chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please wait connecting");
        pd.setTitle("Connecting......");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        Kommunicate.init(this,"10d41c16826bf7e3f821f15f63aa68cca");
        chat = findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                new KmConversationBuilder(Home.this).launchConversation(new KmCallback() {
                    @Override
                    public void onSuccess(Object message) {
                       Log.d("Conversation","Success:"+message);
                       pd.dismiss();

                    }

                    @Override
                    public void onFailure(Object error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                        Log.d("Conversation","Failure:"+error);
                    }
                });
            }
        });

    }
}
