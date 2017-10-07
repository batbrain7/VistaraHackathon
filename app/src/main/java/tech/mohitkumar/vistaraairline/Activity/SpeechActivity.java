package tech.mohitkumar.vistaraairline.Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dnkilic.waveform.WaveView;

import java.util.ArrayList;
import java.util.Locale;

import tech.mohitkumar.vistaraairline.Adapters.RecyclerAdapter;
import tech.mohitkumar.vistaraairline.R;

public class SpeechActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> list = new ArrayList<String>();

   // WaveView waveView;
    ImageView imageView;
    TextToSpeech tts;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        imageView = (ImageView) findViewById(R.id.image_mic);
        layoutManager = new LinearLayoutManager(SpeechActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        tts = new TextToSpeech(this,this);
       // waveView = (WaveView) findViewById(R.id.waveview);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
       // waveView.initialize(dm);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    imageView.setVisibility(View.INVISIBLE);
           //     waveView.speechStarted();
                promptSpeechInput();

            }
        });


    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                // btnSpeak.setEnabled(true);
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    public void speakOut() {

        String text = txtText.getText().toString();

       // tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    // txtSpeechInput.setText(result.get(0));
                            Log.d("TAG",result.get(0));
                            list.add(result.get(0));
                    recyclerAdapter = new RecyclerAdapter(list,SpeechActivity.this);
                    recyclerView.setAdapter(recyclerAdapter);
                    Log.d("TAG",list.size() + "");
                }
                break;
            }

        }
    }
}
