package gscop.mfm_application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class comments_item22 extends Activity {

    Spinner mySpinner;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_item22);

        // on récupère le spinner déclaré dans le fichier xml
        mySpinner = (Spinner) findViewById(R.id.spinner);
        // on créé une liste d'éléments à afficher dans le spinner
        List myList = new ArrayList();
        myList.add("rien à ajouter");
        myList.add("a touché le quadrillage");
        myList.add("ne lève pas son doigt");
        // adapter pour la représentation par défaut du spinner
        ArrayAdapter myAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                myList
        );
        // représentation du spinner quand il est déroulé
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // on passe l'adapter au spinner
        mySpinner.setAdapter(myAdapter);

        boutonCotation0 = (RadioButton) findViewById(R.id.radioButton0);
        boutonCotation1 = (RadioButton) findViewById(R.id.radioButton1);
        boutonCotation2 = (RadioButton) findViewById(R.id.radioButton2);
        boutonCotation3 = (RadioButton) findViewById(R.id.radioButton3);
        boutonCotationNSP = (RadioButton) findViewById(R.id.radioButtonNSP);
        boutonCompensOui = (RadioButton) findViewById(R.id.radioButtonYes);
        boutonCompensNon = (RadioButton) findViewById(R.id.radioButtonNo);
        boutonCompensNSP = (RadioButton) findViewById(R.id.radioButtonNSP2);

        boutonEnregistrer = (Button) findViewById(R.id.buttonSave);
        boutonEnregistrer.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 // on vérifie qu'au moins un radioButton a été sélectionné dans chaque radioGroup
                 // radioGroup : cotation
                 if (boutonCotation0.isChecked() || boutonCotation1.isChecked() || boutonCotation2.isChecked() || boutonCotation3.isChecked() || boutonCotationNSP.isChecked()) {
                     // radioGroup : compensation
                     if (boutonCompensOui.isChecked() || boutonCompensNon.isChecked() || boutonCompensNSP.isChecked()) {
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
                         Toast.makeText(getApplicationContext(), R.string.errorCompensation, Toast.LENGTH_LONG).show();
                     }
                 } else {
                     Toast.makeText(getApplicationContext(), R.string.errorCotation, Toast.LENGTH_LONG).show();
                 }
             }
         }
        );
    }
}
