package gscop.mfm_application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Éloïse on 01/02/2017.
 */

public class do_item18 extends Activity {

    Button boutonRetour;
    Button boutonTerminer;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.do_item18);

        boutonRetour = (Button) findViewById(R.id.boutonRetour);
        boutonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ouvrir une boite de dialogue permettant de valider les infos entrées
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                // set titre
                alertDialogBuilder.setTitle("Confirmation");
                // set dialog message
                alertDialogBuilder
                        .setMessage("Êtes-vous certain de vouloir quitter l'exercice ? (le tracé sera perdu)")
                        .setCancelable(false)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, go to next activity
                                dialog.cancel();
                                // on lance l'activité de choix d'item
                                Intent myIntent = new Intent(do_item18.this, consignes_item18.class);
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
        });
    }


}
