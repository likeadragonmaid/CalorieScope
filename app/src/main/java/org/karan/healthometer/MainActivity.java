package org.karan.healthometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener {

    private TextView textView;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Steps: ";
    public long numSteps;
    private Button BtnStart, BtnStop, BtnReset;
    private TextView TvSteps,CalorieView, currentWaterValue, currentCaffeineValue, waterQuantity, caffeineQuantity;
    public int waterGlasses,caffeineCups, currentWaterQuantity, currentCaffeineQuantity;
    public double Calories;
    public ImageButton addWater, addCaffeine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TvSteps = (TextView) findViewById(R.id.tv_steps);
        CalorieView = (TextView) findViewById(R.id.CalorieView);
        BtnStart = (Button) findViewById(R.id.btn_start);
        BtnStop = (Button) findViewById(R.id.btn_stop);
        BtnReset = (Button) findViewById(R.id.btn_reset);
        addWater = (ImageButton) findViewById(R.id.addWater);
        addCaffeine = (ImageButton) findViewById(R.id.addCaffeine);
        currentWaterValue = (TextView) findViewById(R.id.currentWaterValue);
        currentCaffeineValue = (TextView) findViewById(R.id.currentCaffeineValue);
        waterQuantity = (TextView) findViewById(R.id.waterQuantity);
        caffeineQuantity = (TextView) findViewById(R.id.caffeineQuantity);

        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        BtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                BtnStart.setVisibility(View.INVISIBLE);
                BtnReset.setVisibility(View.INVISIBLE);
                BtnStop.setVisibility(View.VISIBLE);
            }
        });


        BtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                TvSteps.setText("Paused");
                sensorManager.unregisterListener(MainActivity.this);
                BtnStop.setVisibility(View.INVISIBLE);
                BtnStart.setVisibility(View.VISIBLE);
                BtnReset.setVisibility(View.VISIBLE);
            }
        });

        BtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                TvSteps.setText("Let's Start!");
                CalorieView.setText("");
                sensorManager.unregisterListener(MainActivity.this);
                numSteps=0;
                Calories=0;
                BtnReset.setVisibility(View.INVISIBLE);
                BtnStop.setVisibility(View.INVISIBLE);
                BtnStart.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Records cleared", Toast.LENGTH_SHORT).show();
            }
        });

        addWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                waterGlasses++;
                currentWaterQuantity=currentWaterQuantity+250;
                currentWaterValue.setText(""+waterGlasses);
                waterQuantity.setText(currentWaterQuantity+" ml");
            }
        });

        addCaffeine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                caffeineCups++;
                currentCaffeineQuantity=currentCaffeineQuantity+80;
                currentCaffeineValue.setText(""+caffeineCups);
                caffeineQuantity.setText(currentCaffeineQuantity +" mg");
            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++; //Stores value of steps
        Calories = numSteps/(double)20; //Stores calories, algorithm by "Shape Up America!"
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
        CalorieView.setText("Calories Burnt: "+ Double.toString(Calories)+" cal");
    }
}