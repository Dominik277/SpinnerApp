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
                    //u njenoj deklaraciji smo naveli kao parametar da prima String label
                    //u label varijablu je pohranjeno ono sto je korisnik unio u EditText
                    //(navesti sto insertLabel() metoda radi)??????????
                    db.insertLabel(label);


                    //ovom naredbom smo definirali da priliko otvaranja aplikacije pocetni tekst koji ce biti
                    //prikazan u EditText-u ce biti prazan string
                    inputLabel.setText("");



                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(inputLabel.getWindowToken(),0);

                    //pozvali smo metodu koju smo deklarirali u DatabaseHelper klasi, mozemo ju pozvati iz druge
                    //klase jer je ona tipa public
                    //(objasniti sta ta metoda radi)?????????
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

        //kao i u prethodnoj metodi u if dijelu stvaramo objekt u memoriji tipa DatabaseHelper
        //kojeg cemo referencirati preko imena db.Pri kreiranju ovog objekta kao konstruktor
        //saljemo metodu getApplicationContext jer smo to naveli prilikom deklaracije custom konstruktora
        //u njegovoj klasi
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        //
        List<String> labels = db.getAllLabels();


        //ArrayAdapter sam naucio da je klasa ciji je objekt zasluzen za opskrbljivanje podacima onag
        //view-a koji koji se koristi,bio to ListView,RecyclerView ili neki drugi, on također sluzi da
        //odredi koji je nacin prikazivanja podataka i na kraju moramo mu priložiti objekt koji sadrzi
        //sve podatke koje adapter treba prikazati
        //u ovom slucaju smo napravili objekt u memoriji sa desne strane te smo u konstruktoru pohranili sve
        //podatke koji su mu potrebni za njegov rad, a s lijeve strane smo napisali ime preko kojega cemo ga referencirati
        //android.R.layout.simple_list_item_1 -->ovaj argument nam samo govori na koji nacin cemo posloziti podatke
        //                                       u npr. ListView-u
        //labels -->to je objekt u kojem su sadrzani svi podaci koji trebaju biti prikazani npr u ListView-u
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,labels);

        //setDropDownResource() -->ova metoda postavlja podatke koji su unutar dataAdapter-a u padajuci menu
        //parametar -->ovaj parametar nam prikazuje podatke na tocno određeni nacin koji je specifican za tu naredbu
        //             kao sto simple_list_item_1 prikazuje podatke na sebi specifican nacin tako i
        //             simple_spinner_dropdown_item prikazuje podatke na sebi svojstven nacin
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //na vrhu ove klase smo definirali objekt spinner iz XML-a, i pomocu metode setAdapter() postavljamo
        //objekt adapter na taj spinner, a kao sto sam rekao vec, dataAdapter je objekt koji u sebi sadrzi podatke
        //koje treba prikazati i nacin na koji treba prikazati te podatke, tako da pomocu ove metode mi sve te podatke
        //prosljeđujemo spinner objektu kojemu je zadatak samo prikazati podatke jer sve drugo mu je omogucio
        //dataAdapter objekt
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