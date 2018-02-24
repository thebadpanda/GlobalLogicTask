package com.example.arsenko.globallogictask;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public String mCurrentState = "AlarmDisarmed_AllUnlocked";

    private Map<String, List<AbstractMap.SimpleEntry<String, String>>> mResults = new HashMap<>();

    private TextView alert_view;

    private Button lock_button;

    private Button lock_x2_button;

    private Button unlock_button;

    private Button unlock_x2_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alert_view = findViewById(R.id.state_view);
        lock_button = findViewById(R.id.lock_button);
        unlock_button = findViewById(R.id.unlock_button);
        lock_x2_button = findViewById(R.id.lock_x2_button);
        unlock_x2_button = findViewById(R.id.unlock_x2_button);
        lock_button.setOnClickListener(this);
        unlock_button.setOnClickListener(this);
        lock_x2_button.setOnClickListener(this);
        unlock_x2_button.setOnClickListener(this);


        // JSON String
        String jsonString = "{\"mResults\":[{\"mAction\":\"LOCK\", \"mStartState\":\"AlarmDisarmed_AllUnlocked\", \"mEndState\":\"AlarmDisarmed_AllLocked\"} ,\n" +
                "            {\"mAction\":\"LOCK\", \"mStartState\":\"AlarmDisarmed_DriverUnlocked\", \"mEndState\":\"AlarmDisarmed_AllLocked\"} ,\n" +
                "            {\"mAction\":\"LOCK\", \"mStartState\":\"AlarmDisarmed_AllLocked\", \"mEndState\":\"AlarmDisarmed_AllLocked\"} ,\n" +
                "            {\"mAction\":\"LOCK\", \"mStartState\":\"AlarmArmed_AllLocked\", \"mEndState\":\"AlarmArmed_AllLocked\"} ,\n" +
                "            {\"mAction\":\"LOCK_X2\", \"mStartState\":\"AlarmDisarmed_AllUnlocked\", \"mEndState\":\"AlarmArmed_AllLocked\"} ,\n" +
                "            {\"mAction\":\"LOCK_X2\", \"mStartState\":\"AlarmDisarmed_DriverUnlocked\", \"mEndState\":\"AlarmArmed_AllLocked\"} ,\n" +
                "            {\"mAction\":\"LOCK_X2\", \"mStartState\":\"AlarmDisarmed_AllLocked\", \"mEndState\":\"AlarmArmed_AllLocked\"} ,\n" +
                "            {\"mAction\":\"LOCK_X2\", \"mStartState\":\"AlarmArmed_AllLocked\", \"mEndState\":\"AlarmArmed_AllLocked\"} ,\n" +
                "            {\"mAction\":\"UNLOCK\", \"mStartState\":\"AlarmDisarmed_AllUnlocked\", \"mEndState\":\"AlarmDisarmed_AllUnlocked\"} ,\n" +
                "            {\"mAction\":\"UNLOCK\", \"mStartState\":\"AlarmDisarmed_DriverUnlocked\", \"mEndState\":\"AlarmDisarmed_DriverUnlocked\"} ,\n" +
                "            {\"mAction\":\"UNLOCK\", \"mStartState\":\"AlarmDisarmed_AllLocked\", \"mEndState\":\"AlarmDisarmed_DriverUnlocked\"} ,\n" +
                "            {\"mAction\":\"UNLOCK\", \"mStartState\":\"AlarmArmed_AllLocked\", \"mEndState\":\"AlarmDisarmed_DriverUnlocked\"} ,\n" +
                "            {\"mAction\":\"UNLOCK_X2\", \"mStartState\":\"AlarmDisarmed_AllUnlocked\", \"mEndState\":\"AlarmDisarmed_AllUnlocked\"} ,\n" +
                "            {\"mAction\":\"UNLOCK_X2\", \"mStartState\":\"AlarmDisarmed_DriverUnlocked\", \"mEndState\":\"AlarmDisarmed_DriverUnlocked\"} ,\n" +
                "            {\"mAction\":\"UNLOCK_X2\", \"mStartState\":\"AlarmDisarmed_AllLocked\", \"mEndState\":\"AlarmDisarmed_AllUnlocked\"} ,\n" +
                "            {\"mAction\":\"UNLOCK_X2\", \"mStartState\":\"AlarmArmed_AllLocked\", \"mEndState\":\"AlarmDisarmed_AllUnlocked\"}]}";
        try {
            JSONObject obj = new JSONObject(jsonString);
            JSONArray jsonArray = obj.getJSONArray("mResults");
            Log.i("TAG",  jsonArray.toString());

            for(int i=0; i < jsonArray.length(); i++){
                String action = jsonArray.getJSONObject(i).getString("mAction");
                String startState = jsonArray.getJSONObject(i).getString("mStartState");
                String endState = jsonArray.getJSONObject(i).getString("mEndState");

                parseFLM(action, startState, endState);
            }

        } catch (Throwable t) {
            Log.e("TAG", "Could not parse malformed JSON: \"" + jsonString + "\"");
        }

    }

    public void parseFLM(String act, String start, String end) {
        if (mResults.containsKey(act)) {
            List<AbstractMap.SimpleEntry<String, String>> list = mResults.get(act);
            list.add(new AbstractMap.SimpleEntry<String, String>( start, end));

        } else {
            List <AbstractMap.SimpleEntry<String, String>>list = new ArrayList<>();
            list.add(new AbstractMap.SimpleEntry<String, String>( start, end));
            mResults.put(act, list);
        }
    }

    public String getState(String action, String currentState) {

        if (mResults.containsKey(action)) {
            List<AbstractMap.SimpleEntry<String, String>> list = mResults.get(action);
            for (AbstractMap.SimpleEntry<String, String> entry : list) {
                if (entry.getKey().equals(currentState)) {
//                    Toast.makeText(MainActivity.this, currentState, Toast.LENGTH_SHORT).show();

                    return entry.getValue();

                }
            }
            return "Error!";
        } else {
            Log.e("TAG", "Exeption: return missing !");
        }
        return "Error!";
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.lock_button: {
                mCurrentState  = getState("LOCK", mCurrentState);
                Toast.makeText(MainActivity.this, "LOCK", Toast.LENGTH_SHORT).show();

                break;
            }

            case R.id.lock_x2_button: {
                mCurrentState  = getState("LOCK_X2", mCurrentState);
                Toast.makeText(MainActivity.this, "LOCK_X2", Toast.LENGTH_SHORT).show();

                break;
            }

            case R.id.unlock_button: {
                mCurrentState  = getState("UNLOCK", mCurrentState);
                Toast.makeText(MainActivity.this, "UNLOCK", Toast.LENGTH_SHORT).show();

                break;
            }

            case R.id.unlock_x2_button: {
                mCurrentState  = getState("UNLOCK_X2", mCurrentState);
                Toast.makeText(MainActivity.this, "UNLOCK_X2", Toast.LENGTH_SHORT).show();

                break;
            }
        }

        alert_view.setText(mCurrentState);
        alert_view.setBackgroundColor(Color.GREEN);

        if(mCurrentState.equals("AlarmArmed_AllLocked")){
            alert_view.setText(mCurrentState);
            alert_view.setBackgroundColor(Color.RED);
        }
    }
}
