package com.soch.sochhomeautomation;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Controller extends AppCompatActivity {

    int count1=0,count2=0,count3=0;
    String deviceName;
    public final String TAG = "Main";
    private Bluetooth bt;
    public String text;
    CardView device1,device2,device3,device4;
    TextView device1Status,device2Status,device3Status,device4Status;
    ImageView image1,image2,image3,image4,image5,image6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        Intent intent = getIntent();
        deviceName = intent.getStringExtra("deviceName");
        device1Status = (TextView) findViewById(R.id.status_light1);
        device2Status = (TextView) findViewById(R.id.status_light2);
        device3Status = (TextView) findViewById(R.id.status_light3);
        device4Status = (TextView) findViewById(R.id.status_light4);
        device1 = (CardView) findViewById(R.id.card_light1);
        device2 = (CardView) findViewById(R.id.card_light2);
        device3 = (CardView) findViewById(R.id.card_light3);
        device4 = (CardView) findViewById(R.id.card_light4);
        image1 = (ImageView) findViewById(R.id.image_light1);
        image2 = (ImageView) findViewById(R.id.image_light2);
        image3 = (ImageView) findViewById(R.id.image_light3);
        image4 = (ImageView) findViewById(R.id.image_up);
        image5  = (ImageView) findViewById(R.id.image_down);
        image6 = (ImageView) findViewById(R.id.image_stop);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count1 % 2 == 0) {
                    bt.sendMessage("1");
                    image1.setImageResource(R.mipmap.lighton);
                    device1Status.setText("Status : On");
                } else {
                    bt.sendMessage("2");
                    image1.setImageResource(R.mipmap.lightoff);
                    device1Status.setText("Status : Off");
                }
                count1++;
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count2%2==0){
                    bt.sendMessage("3");
                    image2.setImageResource(R.mipmap.lighton);
                    device2Status.setText("Status : On");
                }
                else{
                    bt.sendMessage("4");
                    image2.setImageResource(R.mipmap.lightoff);
                    device2Status.setText("Status : Off");
                }
                count2++;
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count3 % 2 == 0) {
                    bt.sendMessage("5");
                    image3.setImageResource(R.mipmap.lighton);
                    device3Status.setText("Status : On");
                } else {
                    bt.sendMessage("6");
                    image3.setImageResource(R.mipmap.lightoff);
                    device3Status.setText("Status : Off");
                }
                count3++;
            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    bt.sendMessage("7");
                    image4.setImageResource(R.mipmap.upclicked);
                    image5.setImageResource(R.mipmap.down);
                    image6.setImageResource(R.mipmap.stop);
                    device4Status.setText("Status : Forward");
            }
        });
        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt.sendMessage("8");
                image4.setImageResource(R.mipmap.up);
                image5.setImageResource(R.mipmap.downclicked);
                image6.setImageResource(R.mipmap.stop);
                device4Status.setText("Status : Backward");
            }
        });
        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt.sendMessage("9");
                image4.setImageResource(R.mipmap.up);
                image5.setImageResource(R.mipmap.down);
                image6.setImageResource(R.mipmap.stopclicked);
                device4Status.setText("Status : Stop");
            }
        });
        /*
        device4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count4%2==0){
                    bt.sendMessage("7");
                    image4.setImageResource(R.mipmap.motorON);
                }
                else{
                    bt.sendMessage("8");
                    image4.setImageResource(R.mipmap.motorOFF);
                }
                count4++;
            }
        });
        */
        bt = new Bluetooth(this, mHandler);
        connectService();
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 40 && resultCode==RESULT_OK){
            deviceName = data.getStringExtra("deviceName");
            Toast.makeText(getApplicationContext(), "Device : " + deviceName + " Connected", Toast.LENGTH_LONG).show();
            connectService();
        }
    }
*/
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Bluetooth.MESSAGE_STATE_CHANGE:
                    Log.d(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    break;
                case Bluetooth.MESSAGE_WRITE:
                    Log.d(TAG, "MESSAGE_WRITE ");
                    break;
                case Bluetooth.MESSAGE_READ:
                    Log.d(TAG, "MESSAGE_READ ");
                    break;
                case Bluetooth.MESSAGE_DEVICE_NAME:
                    Log.d(TAG, "MESSAGE_DEVICE_NAME "+msg);
                    break;
                case Bluetooth.MESSAGE_TOAST:
                    Log.d(TAG, "MESSAGE_TOAST "+msg);
                    break;
            }
        }
    };
    public void connectService(){
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Toast.makeText(getApplicationContext(),"Connecting...",Toast.LENGTH_SHORT).show();
            if (bluetoothAdapter.isEnabled()) {
                bt.start();
                bt.connectDevice(deviceName);
                Log.d(TAG, "Btservice started - listening");
                Toast.makeText(getApplicationContext(),"Connected to "+deviceName,Toast.LENGTH_SHORT).show();
            } else {
                Log.w(TAG, "Btservice started - bluetooth is not enabled");
                Toast.makeText(getApplicationContext(),"Bluetooth not Enabled",Toast.LENGTH_SHORT).show();
            }
        } catch(Exception e){
            Log.e(TAG, "Unable to start bt ", e);
            Toast.makeText(getApplicationContext(),"Unable to Start Bluetooth",Toast.LENGTH_SHORT).show();
        }
    }
}
