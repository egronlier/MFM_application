package gscop.mfm_application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class do_item22 extends Activity {

    Button boutonTerminer;
    String name = "";
    String surname = "";
    String birthdate = "";
    String main = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.do_item22);

        boutonTerminer = (Button) findViewById(R.id.boutonTerminer);
        boutonTerminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // action quand on appuie sur terminer -> affiche la cartographie ou la fenêtre de commentaires du kiné ?
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            surname = intent.getStringExtra("surname");
            birthdate = intent.getStringExtra("birthdate");
            main = intent.getStringExtra("main");
        }
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
                            // on revient à l'écran des consignes de l'item 22
                            Intent myIntent = new Intent(do_item22.this, consignes_item22.class);
                            myIntent.putExtra("name", name);
                            myIntent.putExtra("surname", surname);
                            myIntent.putExtra("birthdate", birthdate);
                            myIntent.putExtra("main",main);
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
