package spinner.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //ovdje smo samo deklarirali varijable i njihove tipove podataka
    //nismo jos nista u te varijable spremili,prazne su
    Spinner spinner;
    Button btnAdd;
    EditText inputLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //nakon sto smo gore odredili imena varijabli sada u njih spremamo
        //ono sto je s desne strane.S desne strane se nalazi metoda findViewById()
        //koja odlazi u XML datoteku i tamo nalazi atribute koji se nazivaju
        //u nasem slucaju spinner,btnAdd i inputLabel,te te atribute sprema u
        //odgovarajuce varijable onako kako smo naveli u kodu
        spinner = findViewById(R.id.spinner);
        btnAdd = findViewById(R.id.btnAdd);
        inputLabel = findViewById(R.id.inputLabel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String label = inputLabel.getText().toString();

                if (label.trim().length() > 0){
                    DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                    db.insertLabel(label);

                    inputLabel.setText("");

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(inputLabel.getWindowToken(),0);
                    loadSpinnerData();
                }else {
                    Toast.makeText(getApplicationContext(),"Please enter label name",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void loadSpinnerData(){
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List<String> labels = db.getAllLabels();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
    }

}