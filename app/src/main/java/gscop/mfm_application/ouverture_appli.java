package gscop.mfm_application;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ouverture_appli extends Activity implements View.OnClickListener {

    Button boutonValider;
    Button boutonQuitter;
    TextView myTextViewErreur;
    EditText nomEntre;
    EditText prenomEntre;
    EditText dateNaissanceEntre;

    @Override
    /*
     méthode appelée à l'initialisation
    */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ouverture_appli);

        // on utilise la méthode findViewById pour récupérer le bouton quand on clique dessus
        // R est la classe qui contient les ressources
        boutonValider = (Button) findViewById(R.id.boutonvalider);
        boutonQuitter = (Button) findViewById(R.id.boutonquitter);
        myTextViewErreur = (TextView) findViewById(R.id.infoErreur);
        nomEntre = (EditText) findViewById(R.id.nom);
        prenomEntre = (EditText) findViewById(R.id.prenom);
        dateNaissanceEntre = (EditText) findViewById(R.id.birthdate);

        // on implémente l'évènement, on met un listener qui regarde quand on clique sur le bouton
        boutonValider.setOnClickListener(this);
        boutonQuitter.setOnClickListener(this);
    }

//    // Pour le bouton valider
//    private View.OnClickListener validerListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//                // On récupère le nom, le prénom et la date de naissance
//                String nom = nomEntre.getText().toString();
//                String prenom = prenomEntre.getText().toString();
//                String dateNaissance = dateNaissanceEntre.getText().toString();
//            // ----------- rajouter une étape qui vérifie le bon format de la date !
//                myTextViewErreur.setText("Vous voulez créer un fichier pour le patient : \n" +
//                 nom.toUpperCase() + " " + prenom.toLowerCase() +
//                "\n né le : " + dateNaissance);
//                }
//    };

//    // Listener du bouton de remise à zéro
//    private View.OnClickListener razListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            nomEntre.getText().clear();
//            prenomEntre.getText().clear();
//            dateNaissanceEntre.setText(defaut);
//        }
//    };

    @Override
    public void onClick(View v) {
        // On récupère le nom, le prénom et la date de naissance
        String nom = nomEntre.getText().toString();
        String prenom = prenomEntre.getText().toString();
        String dateNaissance = dateNaissanceEntre.getText().toString();
        // ----------- rajouter une étape qui vérifie le bon format de la date !
        myTextViewErreur.setText("Vous voulez créer un fichier pour le patient : \n" +
                nom.toUpperCase() + " " + prenom.toLowerCase() +
                "\n né le : " + dateNaissance);
    }
}