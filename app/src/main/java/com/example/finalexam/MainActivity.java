package com.example.finalexam;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public TextView resultDie1TV;
    public TextView resultDie2TV;
    public TextView myValsTV;
    public EditText customRollET;
    public ArrayList<String> numOfSides;
    public ArrayList<String> customDies;
    public ArrayAdapter<String> adapter;
    private final String[] sides = {"4","6","8","10","12","20", "True 10", "10s"};
    public Spinner spinner;
    public String customDieSides;
    public String myVals = "";
    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.prefrences,false);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        customRollET = findViewById(R.id.customRollET);

        resultDie1TV = findViewById(R.id.resultDie1TV);
        resultDie2TV = findViewById(R.id.resultDie2TV);
        myValsTV = findViewById(R.id.myValsTV);

        numOfSides = new ArrayList<>();
        Collections.addAll(numOfSides, sides);

        for (TextView textView : Arrays.asList(resultDie1TV, resultDie2TV)) {
            textView.setText("0");
        }

        spinner = findViewById(R.id.numOfSidesSpinner);
        spinner.setOnItemSelectedListener(this);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, numOfSides);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        Button Roll = findViewById(R.id.rollButton);
        Roll.setOnClickListener(this);

        Button Save = findViewById(R.id.saveButton);
        Save.setOnClickListener(this);



        SwitchCompat numberOfRollsSwitch = findViewById(R.id.numberOfRollsSwitch);
        numberOfRollsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                resultDie2TV.setVisibility(View.VISIBLE);
                for (TextView textView : Arrays.asList(resultDie1TV, resultDie2TV)) {
                    textView.setText("0");
                }
            }
            else {
                resultDie2TV.setVisibility(View.INVISIBLE);
                for (TextView textView : Arrays.asList(resultDie1TV, resultDie2TV)) {
                    textView.setText("0");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String savingPref = sharedPrefs.getString("saving", getString(R.string.no_saving));
        if (!savingPref.equals(getString(R.string.save_values_i))) {
            getString(R.string.no_saving_i);
        } else {
            String myVals = sharedPrefs.getString("myVals", "0");
            myValsTV.setText(myVals);
            //customDies.add(String.valueOf(sharedPrefs.getStringSet("customDies", null)));
            String [] customDies = myVals.split(", ");
            numOfSides.addAll(Arrays.asList(customDies));
        }


    }

    @Override
    protected void onPause() {
        saveData();
        super.onPause();
    }

    @Override
    protected void onStop() {
        saveData();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        saveData();
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        if (itemID == R.id.menu_settings) {
//            Toast.makeText(this, "Settings menu item", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    public int roll(int numSides){
        return (int)(Math.random() * numSides) + 1;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.rollButton){
            switch (spinner.getSelectedItem().toString()){
                case "4":
                    resultDie1TV.setText(Integer.toString(roll(4)));
                    resultDie2TV.setText(Integer.toString(roll(4)));
                    break;
                case "6":
                    resultDie1TV.setText(Integer.toString(roll(6)));
                    resultDie2TV.setText(Integer.toString(roll(6)));
                    break;
                case "8":
                    resultDie1TV.setText(Integer.toString(roll(8)));
                    resultDie2TV.setText(Integer.toString(roll(8)));
                    break;
                case "10":
                    resultDie1TV.setText(Integer.toString(roll(10)));
                    resultDie2TV.setText(Integer.toString(roll(10)));
                    break;
                case "12":
                    resultDie1TV.setText(Integer.toString(roll(12)));
                    resultDie2TV.setText(Integer.toString(roll(12)));
                    break;
                case "20":
                    resultDie1TV.setText(Integer.toString(roll(20)));
                    resultDie2TV.setText(Integer.toString(roll(20)));
                    break;
                case "10s":
                    resultDie1TV.setText(Integer.toString(roll(10)*10));
                    resultDie2TV.setText(Integer.toString(roll(10)*10));
                    break;
                case "True 10":
                    resultDie1TV.setText(Integer.toString(roll(10)-1));
                    resultDie2TV.setText(Integer.toString(roll(10)-1));
                    break;
                //case spinner.getSelectedItem().toString():
                default:
                    resultDie1TV.setText(Integer.toString(roll(Integer.parseInt(spinner.getSelectedItem().toString()))));
                    resultDie2TV.setText(Integer.toString(roll(Integer.parseInt(spinner.getSelectedItem().toString()))));

            }

        } else if(view.getId() == R.id.saveButton){
            customDieSides = customRollET.getText().toString();
            numOfSides.add(customDieSides);
            //customDies.add(customDieSides);
            Log.wtf("customDieSides", customDieSides);

            //if statement to make sure that the spinner doesn't ry to set anything
            //as well as only save values when something is actually entered
            if(!customDieSides.isEmpty()) {
                spinner.setSelection(numOfSides.size() - 1);
                //added a temp string to get rid of the annoying floating comma at the end of the string
                String temp = "";
                temp += customDieSides + ", ";
                myVals = temp.substring(0, temp.length()-2);
                myValsTV.setText(myVals);
            }


            customRollET.setText("");


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),"d"+spinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
        for (TextView textView : Arrays.asList(resultDie1TV, resultDie2TV)) {
            textView.setText("0");
        }



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void saveData(){
        SharedPreferences.Editor editor = sharedPrefs.edit();

        editor.putString("myVals", myVals);
        //editor.putStringSet("customDies", (Set<String>) customDies);

        editor.apply();
    }
}