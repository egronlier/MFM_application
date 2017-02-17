package gscop.mfm_application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class carto_item22 extends Activity {

    String name = "";
    String surname = "";
    String birthdate = "";
    String main = "";
    Button boutonRecommencer;
    Button boutonValider;
    TextView infosPatient;
    String path = "";
    Bitmap cartoBitmap;
    ImageView carto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.carto_item22);

        carto = (ImageView) findViewById(R.id.cartographieItem22);

        // on récupère les infos de l'intent
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            surname = intent.getStringExtra("surname");
            birthdate = intent.getStringExtra("birthdate");
            main = intent.getStringExtra("main");
            path = intent.getStringExtra("path");
            try {
                File f=new File(path, "cartographie.png");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                carto.setImageBitmap(b);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        infosPatient = (TextView) findViewById(R.id.infosPatient);
        infosPatient.setText(" Patient : " + name + " " + surname + " \n Né(e) le : " + birthdate +"\n"+ " " + main );

        // pour le bouton Recommencer
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        boutonRecommencer = (Button) findViewById(R.id.boutonRecommencer);
        boutonRecommencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // quand on clique sur le bouton recommencer, ça retourne sur l'interface do_item18
                builder.setMessage("Êtes-vous certain de vouloir recommencer l'exercice ? (le tracé sera perdu)")
                        .setCancelable(true)
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close the dialog box
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // on revient à l'écran de réalisation de l'item 18
                                Intent myIntent = new Intent(carto_item22.this, do_item22.class);
                                myIntent.putExtra("name", name);
                                myIntent.putExtra("surname", surname);
                                myIntent.putExtra("birthdate", birthdate);
                                myIntent.putExtra("main", main);
                                startActivity(myIntent);
                                // on ferme l'activité en cours
                                finish();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        // pour le bouton Valider
        boutonValider = (Button) findViewById(R.id.boutonValider);
        boutonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // quand on clique sur le bouton valider, ça ouvre l'interface des commentaires du kiné
                builder.setMessage("Êtes-vous certain de vouloir valider cette cartographie ?")
                        .setCancelable(true)
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close the dialog box
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // on revient à l'écran de réalisation de l'item 18
                                Intent myIntent = new Intent(carto_item22.this, comments_item22.class);
                                myIntent.putExtra("name", name);
                                myIntent.putExtra("surname", surname);
                                myIntent.putExtra("birthdate", birthdate);
                                myIntent.putExtra("main", main);
                                myIntent.putExtra("path", path);
                                startActivity(myIntent);
                                // on ferme l'activité en cours
                                finish();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }

    // quand on appuie sur la touche retour de la tablette -> comme pour le bouton recommencer
    private boolean back_answer = false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Êtes-vous certain de vouloir quitter l'exercice ? (le tracé sera perdu)")
                    .setCancelable(true)
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            back_answer = true;
                            // on revient à l'écran de réalisation de l'item 22
                            Intent myIntent = new Intent(carto_item22.this, do_item22.class);
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
