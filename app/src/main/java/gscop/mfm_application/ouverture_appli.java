package gscop.mfm_application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class ouverture_appli extends Activity {

    Button boutonValider;
    Button boutonQuitter;
    Button boutonEffacer;
    TextView myTextViewErreur;
    EditText nomEntre;
    EditText prenomEntre;
    EditText dateNaissanceEntre;
    RadioButton boutonDroitier;
    RadioButton boutonGaucher;
   // TextView textNomPrenomPatient;

    @Override
    /*
     méthode appelée à l'initialisation
    */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ouverture_appli);

        // on utilise la méthode findViewById pour récupérer le bouton quand on clique dessus
        // R est la classe qui contient les ressources
        boutonValider = (Button) findViewById(R.id.boutonvalider);
        boutonQuitter = (Button) findViewById(R.id.boutonquitter);
        boutonEffacer = (Button) findViewById(R.id.buttonerase);
        nomEntre = (EditText) findViewById(R.id.nom);
        prenomEntre = (EditText) findViewById(R.id.prenom);
        dateNaissanceEntre = (EditText) findViewById(R.id.birthdate);
        myTextViewErreur = (TextView) findViewById(R.id.infoErreur);
        boutonDroitier = (RadioButton) findViewById(R.id.boutonDroitier);
        boutonGaucher = (RadioButton) findViewById(R.id.boutonGaucher);
        //textNomPrenomPatient = (TextView) findViewById(R.id.PatientName);

        // on implémente l'évènement, on met un listener qui regarde quand on clique sur le bouton
        boutonValider.setOnClickListener(validerListener);
        boutonQuitter.setOnClickListener(quitterListener);
        boutonEffacer.setOnClickListener(effacerListener);
        boutonDroitier.setOnClickListener(droitierListener);
        boutonGaucher.setOnClickListener(gaucherListener);
    }

    // Pour le bouton valider
    private View.OnClickListener validerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                // ----------- On lance une nouvelle activité : l'interface du choix d'exercice
                // On récupère le nom, le prénom et la date de naissance
                String nom = nomEntre.getText().toString();
                String prenom = prenomEntre.getText().toString();
                String dateNaissance = dateNaissanceEntre.getText().toString();
                // ----------- rajouter une étape qui vérifie le bon format de la date !
//                myTextViewErreur.setText("Vous voulez créer un fichier pour le patient : \n" +
//                nom.toUpperCase() + " " + prenom.toLowerCase() + "\n né le : " + dateNaissance);

           // textNomPrenomPatient.setText(nom.toUpperCase()+" "+prenom+" "+dateNaissance);

            Intent myIntent = new Intent(ouverture_appli.this, choix_item.class);
            startActivity(myIntent);
        }
    };

    // Pour le bouton quitter
    private View.OnClickListener quitterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // On quitte l'application
            finish();
            System.exit(0);
        }
    };

    // Listener du bouton effacer
    private View.OnClickListener effacerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            nomEntre.getText().clear();
            prenomEntre.getText().clear();
            dateNaissanceEntre.getText().clear();
            myTextViewErreur.setText("");
            boutonDroitier.setChecked(false);
            boutonGaucher.setChecked(false);
        }
    };

    // Pour le bouton droitier
    private View.OnClickListener droitierListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("OC_RSS", "Ca marche !!!");
        }
    };

    // Pour le bouton gaucher
    private View.OnClickListener gaucherListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("OC_RSS", "Ca marche !!!");
        }
    };

}