package gscop.mfm_application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class choix_item extends Activity {

    TextView textNomPrenomPatient;
    Button buttonChgtPatient;
    Button buttonItem18;
    Button buttonItem22;
    String name = "";
    String surname = "";
    String birthdate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_exercise);

        textNomPrenomPatient = (TextView) findViewById(R.id.PatientName);

        buttonChgtPatient = (Button) findViewById(R.id.boutonchgmtpatient);
        buttonChgtPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on revient à l'écran d'accueil d'entrée des infos patient
                Intent myIntent = new Intent(choix_item.this, ouverture_appli.class);
                startActivity(myIntent);
            }
        });

        buttonItem18 = (Button) findViewById(R.id.buttonitem18);
        buttonItem18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on lance les consignes de l'item 18
                Intent myIntent = new Intent(choix_item.this, consignes_item18.class);
                startActivity(myIntent);
            }
        });

        buttonItem22 = (Button) findViewById(R.id.buttonitem22);
        buttonItem22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on lance les consignes de l'item 22
                Intent myIntent = new Intent(choix_item.this, consignes_item22.class);
                startActivity(myIntent);
            }
        });

        // ------------------ à débugger : fait planter l'application
        // on récupère les données de l'activité précédente
        Intent intent = getIntent();
        if(intent != null){
            name = intent.getStringExtra("name");
            surname = intent.getStringExtra("surname");
            birthdate = intent.getStringExtra("birthdate");
            textNomPrenomPatient.setText("Patient : "+name.toUpperCase()+" "+surname.toLowerCase()+" "+birthdate);
        }
    }
}
