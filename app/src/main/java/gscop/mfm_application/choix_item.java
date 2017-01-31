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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_exercise);

        textNomPrenomPatient = (TextView) findViewById(R.id.PatientName);
        //textNomPrenomPatient.setText(nom.toUpperCase()+" "+prenom+" "+dateNaissance);

        buttonChgtPatient = (Button) findViewById(R.id.boutonchgmtpatient);
        buttonChgtPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(choix_item.this, ouverture_appli.class);
                startActivity(myIntent);
            }
        });

        buttonItem18 = (Button) findViewById(R.id.buttonitem18);
        buttonItem18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(choix_item.this, consignes_item18.class);
                startActivity(myIntent);
            }
        });

        buttonItem22 = (Button) findViewById(R.id.buttonitem22);
        buttonItem22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(choix_item.this, consignes_item22.class);
                startActivity(myIntent);
            }
        });


    }
}
