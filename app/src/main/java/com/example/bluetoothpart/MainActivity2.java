package com.example.bluetoothpart;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;

    TextView mStatusBlueTv, mPairedTv;
    ImageView mBlueIv;
    Button mOnBtn, mOffBtn;
    Button next;

    //adapter
    BluetoothAdapter mBlueAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mStatusBlueTv = findViewById(R.id.statusBluetoothTv);
        mBlueIv       = findViewById(R.id.bluetoothIv);
        mOnBtn        = findViewById(R.id.onBtn);
        mOffBtn       = findViewById(R.id.offBtn);
        next = findViewById(R.id.next);

//        mBlueAdapter.enable();

        //checking if bluetooth is available or not
        if (mBlueAdapter == null){
            mStatusBlueTv.setText("Bluetooth is not available");
            //System.exit(0);
        }
        else {
            mStatusBlueTv.setText("Bluetooth is available");
        }

//        setting image according to bluetooth status(on/off)
        if (mBlueAdapter.isEnabled()){
            mBlueIv.setImageResource(R.drawable.ic_action_on);
        }
        else {
            mBlueIv.setImageResource(R.drawable.ic_action_off);
        }

        //on btn click
        mOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBlueAdapter.isEnabled()){
                    //showToast("Turning On Bluetooth...");
                    showToast("Turning on Bluetooth");
//                    Toast.makeText(getApplication(),"TURNING ON BLUETOOTH",Toast.LENGTH_LONG).show();
                    //intent to on bluetooth
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                }
                else {
                    //showToast("Bluetooth is already on");
                    showToast("Bluetooth is already on");
//                    Toast.makeText(getApplication(),"Bluetooth is ALREADY ON",Toast.LENGTH_LONG).show();
                }
            }
        });
        //off btn click
        mOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueAdapter.isEnabled()){
                    mBlueAdapter.disable();
                    showToast("Turning Bluetooth Off");
                    mBlueIv.setImageResource(R.drawable.ic_action_off);
                }
                else {
                    showToast("Bluetooth is already off");
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK){
                    //bluetooth is on
                    mBlueIv.setImageResource(R.drawable.ic_action_on);
                    showToast("Bluetooth is on");
                    next.setClickable(true);
                    next.setBackgroundColor(0xFFFF0000);
                    next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                            startActivity(intent);
                        }
                    });
                }
                else {
                    //user denied to turn bluetooth on
                    showToast("could't on bluetooth");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void nextPage() {
        Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
        startActivity(intent);
    }

    //toast message function
    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}