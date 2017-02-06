package gscop.mfm_application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class comments_item18 extends Activity {

    String name = "";
    String surname = "";
    String birthdate = "";
    String main = "";
    Button boutonEnregistrer;
    final Context context = this;
    RadioButton boutonCotation0;
    RadioButton boutonCotation1;
    RadioButton boutonCotation2;
    RadioButton boutonCotation3;
    RadioButton boutonCotationNSP;
    RadioButton boutonCompensOui;
    RadioButton boutonCompensNon;
    RadioButton boutonCompensNSP;
    RadioButton boutonCerclePetit;
    RadioButton boutonCercleGrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_item18);

        // on récupère les infos de l'intent
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            surname = intent.getStringExtra("surname");
            birthdate = intent.getStringExtra("birthdate");
            main = intent.getStringExtra("main");
        }

        boutonCotation0 = (RadioButton) findViewById(R.id.radioButton0);
        boutonCotation1 = (RadioButton) findViewById(R.id.radioButton1);
        boutonCotation2 = (RadioButton) findViewById(R.id.radioButton2);
        boutonCotation3 = (RadioButton) findViewById(R.id.radioButton3);
        boutonCotationNSP = (RadioButton) findViewById(R.id.radioButtonNSP);
        boutonCompensOui = (RadioButton) findViewById(R.id.radioButtonYes);
        boutonCompensNon = (RadioButton) findViewById(R.id.radioButtonNo);
        boutonCompensNSP = (RadioButton) findViewById(R.id.radioButtonNSP2);
        boutonCerclePetit = (RadioButton) findViewById(R.id.radioButtonSmall);
        boutonCercleGrand = (RadioButton) findViewById(R.id.radioButtonBig);

        boutonEnregistrer = (Button) findViewById(R.id.buttonSave);
        boutonEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on vérifie qu'au moins un radioButton a été sélectionné dans chaque radioGroup
                // radioGroup : cotation
                if (boutonCotation0.isChecked() || boutonCotation1.isChecked() || boutonCotation2.isChecked() || boutonCotation3.isChecked() || boutonCotationNSP.isChecked()) {
                    // radioGroup : compensation
                    if (boutonCompensOui.isChecked() || boutonCompensNon.isChecked() || boutonCompensNSP.isChecked()) {
                        // radioGroup : cercle
                        if (boutonCercleGrand.isChecked() || boutonCerclePetit.isChecked()) {
                            // ouvrir une boite de dialogue permettant de valider
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                            // set titre
                            alertDialogBuilder
                                    .setTitle("Confirmation de validation")
                                    .setMessage("Etes-vous certain de vouloir créer un fichier pour ce patient ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // if this button is clicked, on fait l'enregistrement et on affiche le résultat
                                            dialog.cancel();
                                            // ------------------------- A FAIRE -----------------------------
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
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.errorCircle, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.errorCompensation, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.errorCotation, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
