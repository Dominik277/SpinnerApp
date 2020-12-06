package spinner.app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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

    }
}