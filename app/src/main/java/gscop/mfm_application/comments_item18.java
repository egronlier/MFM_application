package gscop.mfm_application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cete.dynamicpdf.Align;
import com.cete.dynamicpdf.Document;
import com.cete.dynamicpdf.Font;
import com.cete.dynamicpdf.Page;
import com.cete.dynamicpdf.PageOrientation;
import com.cete.dynamicpdf.PageSize;
import com.cete.dynamicpdf.TextAlign;
import com.cete.dynamicpdf.pageelements.Image;
import com.cete.dynamicpdf.pageelements.Label;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
//    FileInputStream fileInput = null;
//    FileOutputStream fileOutput = null;
//    private String PRENOM = "prenom.txt";

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

                                            // -----------CREATION et ENREGISTREMENT du PDF---------
                                            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.FRANCE).format(new Date());
                                            String FILE = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + name.toLowerCase() + "_" + surname.toLowerCase() + "_" + timeStamp + ".pdf";
                                            // Create a document and set it's properties
                                            Document objDocument = new Document();
                                            objDocument.setCreator("MFM_application");
                                            objDocument.setAuthor("MFM_application");
                                            objDocument.setTitle(name.toLowerCase() + "_" + surname.toLowerCase() + "_" + timeStamp + ".pdf");

                                            // Create a page to add to the document
                                            Page page1 = new Page(PageSize.LETTER, PageOrientation.PORTRAIT, 54.0f);
                                            Page page2 = new Page(PageSize.LETTER, PageOrientation.PORTRAIT, 54.0f);

                                            // Create a Label to add to the page
                                            String strText = "Patient : " + name + " " + surname +
                                                    "\nDate de naissance : " + birthdate +
                                                    "\n" + main +
                                                    "\n Item réalisé le : " + timeStamp;
                                            Label objLabel = new Label(strText, 0, 0, 504, 100, Font.getHelvetica(), 18, TextAlign.CENTER);

                                            // on ajoute l'image au pdf
                                            Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.cd_test_tour);
                                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                            byte[] bitMapData = stream.toByteArray();
                                            Image monImage = new Image(bitMapData, 0, 0);
                                            monImage.setAlign(Align.CENTER);

                                            // Add label to page
                                            page1.getElements().add(objLabel);
                                            page2.getElements().add(monImage);

                                            // Add page to document
                                            objDocument.getPages().add(page1);
                                            objDocument.getPages().add(page2);

                                            try {
                                                // Outputs the document to file
                                                objDocument.draw(FILE);
                                                String message = R.string.savedOK + FILE;
                                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                            } catch (Exception e) {
                                                String message = R.string.savedPB + e.getMessage();
                                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                                            }
                                            // -----------------------------------------------------

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

    // quand on appuie sur la touche retour de la tablette
    private boolean back_answer = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("Êtes-vous certain de vouloir quitter l'exercice ? (le tracé sera perdu)")
//                    .setCancelable(true)
//                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
            back_answer = true;
            // on revient à l'écran de réalisation de l'item 18
            Intent myIntent = new Intent(comments_item18.this, carto_item18.class);
            myIntent.putExtra("name", name);
            myIntent.putExtra("surname", surname);
            myIntent.putExtra("birthdate", birthdate);
            myIntent.putExtra("main", main);
            startActivity(myIntent);
            // on ferme l'activité en cours
            finish();
        }
//                    })
//                    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            back_answer = false;
//                        }
//                    });
//            AlertDialog alert = builder.create();
//            alert.show();
//        }
        return back_answer;
    }
}
