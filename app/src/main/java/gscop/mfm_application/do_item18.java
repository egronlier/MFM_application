package gscop.mfm_application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class do_item18 extends Activity {

    Button boutonTerminer;
    String name = "";
    String surname = "";
    String birthdate = "";
    String main = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.do_item18);

        // on récupère les infos de l'intent
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            surname = intent.getStringExtra("surname");
            birthdate = intent.getStringExtra("birthdate");
            main = intent.getStringExtra("main");
        }

        boutonTerminer = (Button) findViewById(R.id.boutonTerminer);
        boutonTerminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // action quand on appuie sur terminer -> affiche la cartographie ou la fenêtre de commentaires du kiné ?
            }
        });

        // -----------on affiche le bouton à gauche pour les droitiers et à droite pour les gauchers
        // on vérifie si la tablette est en paysage (ORIENTATION_LANDSCAPE = 2),
        // sinon (en portrait) on ne change rien, le bouton terminer reste centré
        if (getResources().getConfiguration().orientation == 2) {
            // on vérifie ensuite si la personne est droitière ou gauchère
            // si elle est droitière, on met le bouton serré à gauche
            if (main.equals("Droitier")) {
                RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                rel_btn.width = 120;
                rel_btn.height = 60;
                rel_btn.setMarginStart(50);
                rel_btn.setMarginEnd(50);
                rel_btn.topMargin = 60;
                boutonTerminer.setLayoutParams(rel_btn);
                Toast.makeText(getApplicationContext(), "Position droitier activée", Toast.LENGTH_LONG).show();
            }
            // si elle est gauchère, on met le bouton serré à droite
            else {
                if (main.equals("Gaucher")) {
                    RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    rel_btn.width = 120;
                    rel_btn.height = 60;
                    rel_btn.setMarginStart(50);
                    rel_btn.topMargin = 60;
                    boutonTerminer.setLayoutParams(rel_btn);
                    Toast.makeText(getApplicationContext(), "Position gaucher activée", Toast.LENGTH_LONG).show();
                }
                // sinon on affiche un message d'erreur
                else {
                    Toast.makeText(getApplicationContext(), R.string.errorDG, Toast.LENGTH_LONG).show();
                }
            }
        }
        // --------------------------

    }

    private boolean back_answer = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Êtes-vous certain de vouloir quitter l'exercice ? (le tracé sera perdu)")
                    .setCancelable(false)
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            back_answer = true;
                            // on revient à l'écran des consignes de l'item 18
                            Intent myIntent = new Intent(do_item18.this, consignes_item18.class);
                            myIntent.putExtra("name", name);
                            myIntent.putExtra("surname", surname);
                            myIntent.putExtra("birthdate", birthdate);
                            myIntent.putExtra("main", main);
                            startActivity(myIntent);
                            // on ferme l'activité en cours
                            finish();
                        }
                    })
                    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            back_answer = false;
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        return back_answer;
    }
}
