package gscop.mfm_application;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class choix_item extends Activity {

    TextView textNomPrenomPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_exercise);

        textNomPrenomPatient = (TextView) findViewById(R.id.PatientName);
        //textNomPrenomPatient.setText(nom.toUpperCase()+" "+prenom+" "+dateNaissance);


    }
}
