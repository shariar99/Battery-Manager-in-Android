package com.shariar99.batteryms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.health.HealthStats;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView level,voltage,health,batteryType,chargingSource,temperature,chargingStatus;
    BroadcastReceiver batteryBroadcast;
    IntentFilter intentFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        level = findViewById(R.id.textLevel);
        voltage = findViewById(R.id.textVoltag);
        health = findViewById(R.id.textHealth);
        batteryType = findViewById(R.id.textType);
        chargingSource = findViewById(R.id.textChargingSource);
        temperature = findViewById(R.id.textTemperature);
        chargingStatus = findViewById(R.id.textChargingStatus);

        intentFilterAndBroadcast();

    }

    private void intentFilterAndBroadcast() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        batteryBroadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
                   //for Charging Level
                    level.setText(String.valueOf(intent.getIntExtra("level",0))+"%");

                    //for Battery voltage
                    float voltageTemp = (float)(intent.getIntExtra("voltage",0)*0.001);
                    voltage.setText(voltageTemp+"v");
                    //for Battery health
                    setHealth(intent);
                    //for battery Technology
                    batteryType.setText(intent.getStringExtra("technology"));
                    //for charging source
                    getChargingSource(intent);

                    //for batter Temprature
                    float tempTemp = (float) intent.getIntExtra("temperature",-1)/10;
                    temperature.setText(tempTemp+"°C");

                    // for charging status
                    setChaegingStatus(intent);




                }
            }
        };




    }

    private void setChaegingStatus(Intent intent) {
        int statusTemp = intent.getIntExtra("status",1);
        switch (statusTemp){
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                chargingStatus.setText("Unknown");
                break;
            case BatteryManager.BATTERY_STATUS_CHARGING:
                chargingStatus.setText("Charging");
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                chargingStatus.setText("Discharging");
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                chargingStatus.setText("Not Charging");
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                chargingStatus.setText("Full");
                break;
            default:
                chargingStatus.setText("Null");
                break;
        }

    }

    private void getChargingSource(Intent intent) {
        int sourceTemp = intent.getIntExtra("plugged",-1);

        switch (sourceTemp){

            case BatteryManager.BATTERY_PLUGGED_AC:
                chargingSource.setText("AC");
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                chargingSource.setText("USB");
                break;
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                chargingSource.setText("Wireless");
                break;
            default:
                chargingSource.setText("Null");
                break;
        }
    }

    private void setHealth(Intent intent) {

        int val = intent.getIntExtra("health",0);

        switch (val){
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                health.setText("Unknown");
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                health.setText("Good");
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                health.setText("OverHeat");
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                health.setText("Dead");
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                health.setText("Over Volatage");
                break;
             case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                health.setText("Unsprcified Failure");
                break;
            case BatteryManager.BATTERY_HEALTH_COLD:
                health.setText("Cold");
                break;
            default:
                health.setText("Null");

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(batteryBroadcast,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(batteryBroadcast);
    }
}