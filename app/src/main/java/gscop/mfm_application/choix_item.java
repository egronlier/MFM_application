package gscop.mfm_application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class choix_item extends Activity {

    TextView textNomPrenomPatient;
    Button buttonItem18;
    Button buttonItem22;
    String name = "";
    String surname = "";
    String birthdate = "";
    String main = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_exercise);
        textNomPrenomPatient = (TextView) findViewById(R.id.PatientName);

        // on récupère les données de l'activité précédente pour afficher les données du patient
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            surname = intent.getStringExtra("surname");
            birthdate = intent.getStringExtra("birthdate");
            main = intent.getStringExtra("main");
            textNomPrenomPatient.setText("Patient : " + name.toUpperCase() + " " + surname.toLowerCase() + " \n né(e) le : " + birthdate +"\n"+ main );
        }

        buttonItem18 = (Button) findViewById(R.id.buttonitem18);
        buttonItem18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on lance les consignes de l'item 18
                Intent myIntent = new Intent(choix_item.this, consignes_item18.class);
                myIntent.putExtra("name", name);
                myIntent.putExtra("surname", surname);
                myIntent.putExtra("birthdate", birthdate);
                myIntent.putExtra("main",main);
                startActivity(myIntent);
                // on ferme l'activité en cours
                finish();
            }
        });

        buttonItem22 = (Button) findViewById(R.id.buttonitem22);
        buttonItem22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on lance les consignes de l'item 22
                Intent myIntent = new Intent(choix_item.this, consignes_item22.class);
                myIntent.putExtra("name", name);
                myIntent.putExtra("surname", surname);
                myIntent.putExtra("birthdate", birthdate);
                myIntent.putExtra("main",main);
                startActivity(myIntent);
                // on ferme l'activité en cours
                finish();
            }
        });

    }

    private boolean back_answer = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.confirmBackAccueil)
                    .setTitle("Changement patient")
                    .setCancelable(false)
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            back_answer = true;
                            // on revient à l'écran d'accueil d'entrée des infos patient
                            Intent myIntent = new Intent(choix_item.this, ouverture_appli.class);
                            // pas besoin des extra car pas d'infos à faire transiter
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
