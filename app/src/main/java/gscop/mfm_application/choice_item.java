package gscop.mfm_application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class choice_item extends Activity {

    TextView textNomPrenomPatient;
    Button buttonItem18;
    Button buttonItem22;
    Button buttonExit;
    String name = "";
    String surname = "";
    String birthdate = "";
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_item);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        textNomPrenomPatient = (TextView) findViewById(R.id.PatientName);

        // on récupère les données de l'activité précédente pour afficher les données du patient
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            surname = intent.getStringExtra("surname");
            birthdate = intent.getStringExtra("birthdate");
            textNomPrenomPatient.setText("Patient : " + name + " " + surname + " \nNé(e) le : " + birthdate);
        }

        buttonExit = (Button) findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Êtes-vous certain de vouloir quitter l'application ?")
                        .setCancelable(true)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // on quitte l'application courante
                                choice_item.this.finish();
                                System.exit(0);
                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        buttonItem18 = (Button) findViewById(R.id.buttonitem18);
        buttonItem18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // aléatoirement lance la version papier ou la version tablette
                int mini = 0;
                int max = 1;
                // génère aléatoirement 0 ou 1
                int varRandom = (int)( Math.random()*( max - mini + 1 ) ) + mini;
                if(varRandom == 0){
                    // on demande de réaliser l'item 18 version papier en premier
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(R.string.paper18)
                            .setTitle("MFM Papier")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // on lance les consignes de l'item 18
                                    Intent myIntent = new Intent(choice_item.this, consignes_item18.class);
                                    myIntent.putExtra("name", name);
                                    myIntent.putExtra("surname", surname);
                                    myIntent.putExtra("birthdate", birthdate);
                                    myIntent.putExtra("varRandom",0);
                                    startActivity(myIntent);
                                    // on ferme l'activité en cours
                                    finish();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    // on demande la version tablette en premier
                    Intent myIntent = new Intent(choice_item.this, consignes_item18.class);
                    myIntent.putExtra("name", name);
                    myIntent.putExtra("surname", surname);
                    myIntent.putExtra("birthdate", birthdate);
                    myIntent.putExtra("varRandom",1);
                    startActivity(myIntent);
                    // on ferme l'activité en cours
                    finish();
                }
            }
        });

        buttonItem22 = (Button) findViewById(R.id.buttonitem22);
        buttonItem22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // aléatoirement lance la version papier ou la version tablette
                int mini = 0;
                int max = 1;
                // génère aléatoirement 0 ou 1
                int varRandom = (int)( Math.random()*( max - mini + 1 ) ) + mini;
                if(varRandom == 0){
                    // on demande de réaliser l'item 18 version papier en premier
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(R.string.paper22)
                            .setTitle("MFM Papier")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // on lance les consignes de l'item 22
                                    Intent myIntent = new Intent(choice_item.this, consignes_item22.class);
                                    myIntent.putExtra("name", name);
                                    myIntent.putExtra("surname", surname);
                                    myIntent.putExtra("birthdate", birthdate);
                                    myIntent.putExtra("varRandom",0);
                                    startActivity(myIntent);
                                    // on ferme l'activité en cours
                                    finish();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    // on demande la version tablette en premier
                    Intent myIntent = new Intent(choice_item.this, consignes_item22.class);
                    myIntent.putExtra("name", name);
                    myIntent.putExtra("surname", surname);
                    myIntent.putExtra("birthdate", birthdate);
                    myIntent.putExtra("varRandom",1);
                    startActivity(myIntent);
                    // on ferme l'activité en cours
                    finish();
                }
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
                            Intent myIntent = new Intent(choice_item.this, ouverture_appli.class);
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
