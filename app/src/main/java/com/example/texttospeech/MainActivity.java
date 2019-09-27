package com.example.texttospeech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.Activity;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, OnItemSelectedListener {
private TextToSpeech tts;
private EditText editText;
private Spinner speedSpinner,pitchSpinner;
private Button buttonSpeak;

private static String speed="normal";
    private SpinnerAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tts=new TextToSpeech(this,this);
buttonSpeak=(Button)findViewById(R.id.button);
editText=(EditText)findViewById(R.id.editText);
speedSpinner=(Spinner)findViewById(R.id.spinner2);
//load data into the spinner
        loadSpinnerData();
        speedSpinner.setOnItemSelectedListener(this);
        //button click event
        buttonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setSpeed();
                speakOut();
            }
        });
    }

    @Override
    public void onInit(int status) {
if (status==TextToSpeech.SUCCESS){
    int result=tts.setLanguage(Locale.US);
}
if (status==TextToSpeech.LANG_MISSING_DATA || status == TextToSpeech.LANG_NOT_SUPPORTED){
    Log.e("TTS","this language is not supported");
}
else{
    buttonSpeak.setEnabled(true);
    speakOut();

        Log.e("TTS","Initialization Failed");

}

        }
        public void onDestroy(){
        if (tts !=null){
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
        }
        private void setSpeed(){
        if (speed.equals("Very Slow")){
            tts.setSpeechRate(0.1f);
        }
        if (speed.equals("Slow")){
            tts.setSpeechRate(0.5f);
        }
        if (speed.equals("Normal")){
            tts.setSpeechRate(1.0f);
        }
        if (speed.equals("Fast")){
            tts.setSpeechRate(1.5f);
        }
        if (speed.equals("Very Fast")){
            tts.setSpeechRate(2.0f);
        }
        }
        private void speakOut(){
        String text=editText.getText().toString();
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
        }
        private void loadSpinnerData(){
        List<String>labels=new ArrayList<String>();
        labels.add("Very Slow");
        labels.add("Slow");
        labels.add("Normal");
        labels.add("Fast");
        labels.add("Very Fast");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            speedSpinner.setAdapter(dataAdapter);

        }

public void onItemSelected(AdapterView<?> parent, View view, int position,
                           long id){
    // On selecting a spinner item
    speed = parent.getItemAtPosition(position).toString();

    Toast.makeText(parent.getContext(), "You selected: " + speed,
            Toast.LENGTH_LONG).show();
}
    public void onNothingSelected(AdapterView<?> arg0) {

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
