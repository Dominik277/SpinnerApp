package spinner.app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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

        //ovdje smo na spinner objekt pozvali metodu setOnItemSelectedListener
        //koja je jedna od dvije metode unutar AdapterView interfejsa te smo ju
        //zbog toga morali implementirati,spinner objekt ce nakon ovoga imati sve
        //mogucnosti koje smo napisali u tijelu te metode
        spinner.setOnItemSelectedListener(this);

        //ovdje samo dozivamo loadSpinnerData() metodu koju smo deklarirali i
        //odredili koji je njen zadatak unutar onCreate metode koja se izvrsava
        //pri ulasku u aplikaciju
        loadSpinnerData();


        //ova metoda koja se odvija nad btnAdd objektom nam govori da ce gumb imati
        //neku funkciju,nismo jos definirali tocno koju, ali smo definirali da ce imati
        btnAdd.setOnClickListener(new View.OnClickListener() {

            //onClick metoda je metoda koja u svom tijelu ima sve naredbe koje ce se izvrsiti
            //prilikom klika na gumb
            //View v -->
            @Override
            public void onClick(View v) {

                //label je varijabla tipa String u koju cemo spremiti sve sto se nalazi s desne strane
                //inputLabel je view u XML datoteci koji predstavlja mjesto gdje upisujemo text, ali mi
                //smo na pocetku ove klase deklarirali da ce to biti referenca na objekt i preko njega
                //sada mozemo zvati metode koje vracaju string
                //getText()-->ovu metodu mozemo zamisliti da uzima tekst koji je upisan u view EditText
                //            u XML datoteci i sprema ga u sebe te u sebi sadrzava taj tekst koji je unesen
                //toString()-->zbog toga sto svi podaci koji trebaju biti u inputLabel objektu moraju biti
                //             tipa String, onda pomocu ove metode sve pretvaramo u string, npr u ovom
                //             slucaju ako korisnik unese neki broj u EditText ova metoda ga pretvara u
                //             string tako da ga omotava navodnicima(tako ja zamisljam)
                String label = inputLabel.getText().toString();


                //u zagradi unutar if naredbe se provjerava uneseni tekst koji je korisnik unio u EditText
                //tocnije, provjerava se broj znamenki koje je korisnik unio gdje se razmaci ne broje
                //u slucaju da je broj znamenki veci od 0, onda se izvrsava dio koda koji je unutar
                //viticastih zagrada kod if,a u slucaju da je korisnik kliknuo gumb, a nije unio nikakve
                //podatke onda se izvrsava dio koda koji je unutar viticastih zagrada kod else
                if (label.trim().length() > 0){

                    //ovdje smo stvorili objekt tipa DatabaseHelper u memoriji racunala na desnoj strani
                    //na lijevoj strani smo samo stvorili referencu preko koje cemo pozivati taj objekt
                    //getApplicationContext -->u konstruktoru smo morali navesti ovu metodu jer smo u
                    //                         DatabaseHelper klasi napravili custom konstruktor koji kao
                    //                         parametar prima Context
                    //getApplicationContext() -->
                    DatabaseHelper db = new DatabaseHelper(getApplicationContext());

                    //u naredbi iznad smo stvorili referencu db na objekt u memoriji, posto smo ju stvorili
                    //unutar if naredbe ona je vidljiva samo unutar nje, izvan if naredbe ona ne postoji
                    //preko nje smo dozvali insertLabel() metodu koja sam deklarirao u DatabaseHelper, ali je
                    //ona tipa public pa joj se moze pristupiti i unutar druge klase
                    //
                    db.insertLabel(label);

                    inputLabel.setText("");

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(inputLabel.getWindowToken(),0);
                    loadSpinnerData();

                //else dio if naredbe nam govori ako kojim slucajem je korisnik stisnuo gumb dodaj
                //a prostor u EditText-u je bio prazan, onda se obavlja dio koda koji se nalazi unutar
                //viticastih zagrada, a to je da se na dnu ekrana ispisuje Toast poruka "Molimo unesite element"
                //getApplicationContext() -->
                }else {
                    Toast.makeText(getApplicationContext(),"Molimo unesite element",
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String label = parent.getItemAtPosition(position).toString();

        Toast.makeText(parent.getContext(),"You selected: "
        + label,Toast.LENGTH_LONG).show();
    }

    //metoda koja je implementirana jer se nalazi u AdapterView interfejsu ali nam nije potrebna
    //pa u njenom tijelu nismo nista deklarirali
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}