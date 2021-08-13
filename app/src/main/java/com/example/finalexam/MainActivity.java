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
import android.widget.ImageButton;
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
    public TextView resultDie1TV, resultDie2TV, DieRollsTV, DieRollsLabel;
    public TextView myValsTV;
    public EditText customRollET;
    public ArrayList<String> numOfSides;
    public ArrayAdapter<String> adapter;
    private final String[] sides = {"4","6","8","10","12","20"};
    public Spinner spinner;
    public String customDieSides;
    public String myVals = "";
    private SharedPreferences sharedPrefs;
    public SwitchCompat numberOfRollsSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.prefrences,true);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        customRollET = findViewById(R.id.customRollET);

        resultDie1TV = findViewById(R.id.resultDie1TV);
        resultDie2TV = findViewById(R.id.resultDie2TV);
        DieRollsLabel = findViewById(R.id.DieRollsLabel);

        DieRollsTV = findViewById(R.id.DieRollsTV);
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

        ImageButton Roll = findViewById(R.id.rollButton);
        Roll.setOnClickListener(this);

        Button Save = findViewById(R.id.saveButton);
        Save.setOnClickListener(this);



        numberOfRollsSwitch = findViewById(R.id.numberOfRollsSwitch);
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
        boolean savingPref = sharedPrefs.getBoolean("savingVals", true);
        //String savingPref = sharedPrefs.getString("saving", getString(R.string.no_saving));
        if (savingPref) {
            String myVals = sharedPrefs.getString("myVals", "");
            myValsTV.setText(myVals);
            String DieRolls = sharedPrefs.getString("DieRolls", "");
            DieRollsTV.setText(DieRolls);

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
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.rollButton) {
            Die sides;

            switch (spinner.getSelectedItem().toString()) {

                case "4":
                    sides = new Die(4);
                    break;
                case "6":
                    sides = new Die(6);
                    break;
                case "8":
                    sides = new Die(8);
                    break;
                case "10":
                    sides = new Die(10);
                    break;
                case "12":
                    sides = new Die(12);
                    break;
                case "20":
                    sides = new Die(20);
                    break;
                default:
                    sides = new Die(Integer.parseInt(spinner.getSelectedItem().toString()));

            }
            resultDie1TV.setText(Integer.toString(sides.getSideUp()));
            if(DieRollsTV.getText().toString().isEmpty()) {
                DieRollsTV.append(Integer.toString(sides.getSideUp()));
            } else {
                DieRollsTV.append(", " + Integer.toString(sides.getSideUp()));
            }

            sides.roll();
            resultDie2TV.setText(Integer.toString(sides.getSideUp()));

            if (numberOfRollsSwitch.isChecked()) {
                DieRollsTV.append(", " + Integer.toString(sides.getSideUp()));

            }



        } else if(view.getId() == R.id.saveButton){
            customDieSides = customRollET.getText().toString();

            //customDies.add(customDieSides);
            //Log.wtf("customDieSides", customDieSides);

            //if statement to make sure that the spinner doesn't ry to set anything
            //as well as only save values when something is actually entered
            if(!customDieSides.isEmpty()) {
                numOfSides.add(customDieSides);
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
        editor.putString("DieRolls",DieRollsTV.getText().toString());
        //editor.putStringSet("customDies", (Set<String>) customDies);

        editor.apply();
    }
}