package com.example.finalexam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public TextView resultDie1TV;
    public TextView resultDie2TV;
    public EditText customRollET;
    public ArrayList<String> numOfSides;
    public ArrayAdapter<String> adapter;
    private String[] sides = {"4","6","8","10","12","20"};
    public Spinner spinner;
    public String customDieSides;
    public String myVal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customRollET = findViewById(R.id.customRollET);

        resultDie1TV = findViewById(R.id.resultDie1TV);
        resultDie2TV = findViewById(R.id.resultDie2TV);

        numOfSides = new ArrayList<>();
        for(int i = 0; i < sides.length; i++ ){
            numOfSides.add(sides[i]);
        }

        resultDie1TV.setText("0");
        resultDie2TV.setText("0");

        spinner = (Spinner)findViewById(R.id.numOfSidesSpinner);
        spinner.setOnItemSelectedListener(this);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, numOfSides);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        Button Roll = findViewById(R.id.rollButton);
        Roll.setOnClickListener(this);

        Button Save = findViewById(R.id.saveButton);
        Save.setOnClickListener(this);

        Switch numberOfRollsSwitch = findViewById(R.id.numberOfRollsSwitch);
        numberOfRollsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    resultDie2TV.setVisibility(View.VISIBLE);
                    resultDie2TV.setText("0");
                    resultDie1TV.setText("0");
                }
                else {
                    resultDie2TV.setVisibility(View.INVISIBLE);
                    resultDie1TV.setText("0");
                    resultDie2TV.setText("0");
                }
            }
        });
    }

    public int roll(int numSides){
        int randomVal = (int)(Math.random() * numSides) + 1;
        return randomVal;
    }

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
                //case spinner.getSelectedItem().toString():
                default:
                    resultDie1TV.setText(Integer.toString(roll(Integer.parseInt(spinner.getSelectedItem().toString()))));
                    resultDie2TV.setText(Integer.toString(roll(Integer.parseInt(spinner.getSelectedItem().toString()))));

            }

        } else if(view.getId() == R.id.saveButton){
            customDieSides = customRollET.getText().toString();
            numOfSides.add(customDieSides);
            Log.wtf("customDieSides", customDieSides.toString());

            myVal += customDieSides + ", ";
            resultDie2TV.setText(myVal);
            customRollET.setText("");

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),"d"+spinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
        resultDie1TV.setText("0");
        resultDie2TV.setText("0");


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}