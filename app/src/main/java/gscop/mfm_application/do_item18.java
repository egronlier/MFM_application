package gscop.mfm_application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class do_item18 extends Activity {

    Button boutonTerminer;
    String name = "";
    String surname = "";
    String birthdate = "";
    String main = "";
    Dessin_item18 dessin;
    Bitmap cartoBitmap;
    TextView state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.do_item18);

        // on récupère les infos de l'intent
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            surname = intent.getStringExtra("surname");
            birthdate = intent.getStringExtra("birthdate");
            main = intent.getStringExtra("main");
        }

        dessin = (Dessin_item18) findViewById(R.id.drawingItem18);
        state = (TextView) findViewById(R.id.enCours);

        boutonTerminer = (Button) findViewById(R.id.buttonStop);
        boutonTerminer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutonTerminer.setClickable(false);
                state.setText(R.string.saving);
                Intent myIntent = new Intent(do_item18.this, carto_item18.class);
                myIntent.putExtra("name", name);
                myIntent.putExtra("surname", surname);
                myIntent.putExtra("birthdate", birthdate);
                myIntent.putExtra("main", main);
                cartoBitmap = dessin.getCartographie();
                myIntent.putExtra("path", saveToInternalStorage(cartoBitmap));
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

    // Cette méthode enregistre un bitmap dans la mémoire interne de l'appareil
    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "cartographie.png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

}
