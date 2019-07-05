package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText editTextWeight;
    EditText editTextHeight;
    Button buttonCalculate;
    Button buttonReset;
    TextView textViewDate;
    TextView textViewBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonReset = findViewById(R.id.buttonReset);
        textViewDate = findViewById(R.id.textViewDate);
        textViewBMI = findViewById(R.id.textViewBMI);


        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                textViewDate.setText("Last Calculated Date: " + datetime);
                float weight = Float.parseFloat(editTextWeight.getText().toString());
                float height = Float.parseFloat(editTextHeight.getText().toString());

                float BMI = weight / (height*height);
                String result = String.format("Last Calculated BMI: %.3f" , BMI);

                if (BMI < 18.5){
                    result += "\n You are underweight";
                }else if(BMI < 24.9){
                    result += "\n Your BMI is normal";
                }else if(BMI < 29.9){
                    result += "\n You are overweight";
                }else{
                    result += "\n You are obese";
                }
                textViewBMI.setText(result);



            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewDate.setText("Last Calculated Date: ");
                textViewBMI.setText("Last Calculated BMI: ");
                editTextHeight.setText("");
                editTextWeight.setText("");
            }
        });


    }


    @Override
    protected void onPause() {
        super.onPause();
        String date = textViewDate.getText().toString();
        String bmi = textViewBMI.getText().toString();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("date", date);
        prefEdit.putString("bmi", bmi);

        prefEdit.commit();


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String date = prefs.getString("date","");
        String bmi = prefs.getString("bmi", "");

        textViewDate.setText(date);
        textViewBMI.setText(bmi);

    }
}
