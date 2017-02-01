package gscop.mfm_application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

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
    final Context context = this;

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

        // on met un listener qui regarde quand on clique sur le bouton
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
            // On récupère le nom, le prénom et la date de naissance
            final String name = nomEntre.getText().toString();
            int length_name = name.length();
            final String surname = prenomEntre.getText().toString();
            int length_surname = surname.length();
            final String birthdate = dateNaissanceEntre.getText().toString();
            int length_birthdate = birthdate.length();
            // On vérifie que tous les champs ont été remplis
            if (boutonDroitier.isChecked() || boutonGaucher.isChecked()) {
                // on vérifie qu'au moins un radioButton a été sélectionné
                if (length_name > 0 && length_surname > 0 && length_birthdate > 0) {
                    // On vérifie que le nom et le prénom entrés contiennent bien que des lettres
                    if (Pattern.matches("[a-zA-Z -]*", name) && Pattern.matches("[a-zA-Z -]*", surname)) {
                        // on vérifie que la date entrée contient que des chiffres et des /
                        if (Pattern.matches("[0-9 /]*", birthdate)) {
                            // étape qui vérifie le bon format de la date
                            SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
                            try {
                                Date d = myFormat.parse(birthdate);
                                String t = myFormat.format(d);
                                if (t.compareTo(birthdate) != 0) {
                                   // System.out.println("NON VALIDE");
                                    myTextViewErreur.setText(R.string.errorDateFormat);
                                } else {
                                   // System.out.println("VALIDE");
                                    // ouvrir une boite de dialogue permettant de valider les infos entrées
                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                    // set titre
                                    alertDialogBuilder.setTitle("Confirmation des données");
                                    // set dialog message
                                    alertDialogBuilder
                                            .setMessage("Etes-vous certain de vouloir créer un fichier pour le patient suivant : \n" + name.toUpperCase() + " " + surname.toLowerCase() + "\n né le : " + birthdate)
                                            .setCancelable(false)
                                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // if this button is clicked, go to next activity
                                                    dialog.cancel();
                                                    // On lance une nouvelle activité : l'interface du choix d'item
                                                    Intent myIntent = new Intent(ouverture_appli.this, choix_item.class);
                                                    myIntent.putExtra(name, name);
                                                    myIntent.putExtra(surname, name);
                                                    myIntent.putExtra(birthdate, birthdate);
                                                    startActivity(myIntent);
                                                }
                                            })
                                            .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // if this button is clicked, close the dialog box
                                                    dialog.cancel();
                                                }
                                            });
                                    // create alert dialog
                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                    // show it
                                    alertDialog.show();
                                }
                            } catch (Exception e) {
                               // System.out.println("EXCEPTION");
                                myTextViewErreur.setText(R.string.internalError);
                            }
                        } else {
                            myTextViewErreur.setText(R.string.errorDate);
                        }
                    } else {
                        myTextViewErreur.setText(R.string.errorNames);
                    }
                } else {
                    myTextViewErreur.setText(R.string.errorVoid);
                }
            }
            else{
                myTextViewErreur.setText(R.string.errorRadioButton);
            }
        }
    };

    // Pour le bouton quitter
    private View.OnClickListener quitterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // On quitte l'application
            ouverture_appli.this.finish();
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
          //  Log.i("OC_RSS", "Ca marche !!!");
        }
    };

    // Pour le bouton gaucher
    private View.OnClickListener gaucherListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
         //   Log.i("OC_RSS", "Ca marche !!!");
        }
    };
}