package com.example.ominext.exampleasynctask;

import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    Handler handler;
    AtomicBoolean isRunning=new AtomicBoolean(false);
    Button buttonStart;
    TextView textViewNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=(ProgressBar)findViewById(R.id.progress_timerun);
        textViewNumber=(TextView)findViewById(R.id.text_number);
        buttonStart=(Button)findViewById(R.id.button_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doStart();
            }


        });
        handler=new Handler(){
            public void handleMessage(Message message){
                super.handleMessage(message);
                progressBar.setProgress(message.arg2);
                textViewNumber.setText(message.arg2+"%");

            }
        };

    }

    private void doStart() {
        progressBar.setProgress(0);
        isRunning.set(false);
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=1;i<=100&&isRunning.get();i++){
                    SystemClock.sleep(50);
                    Message message=handler.obtainMessage();
                    message.arg2=i;
                    handler.sendMessage(message);
                }
            }
        });
        isRunning.set(true);
        thread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
}
