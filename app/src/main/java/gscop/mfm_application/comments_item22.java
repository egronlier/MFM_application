package gscop.mfm_application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import java.util.List;
import java.util.Locale;

import tutoandroid.libmultispinner.MultiSelectionSpinner;

public class comments_item22 extends Activity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {

    String name = "";
    String surname = "";
    String birthdate = "";
    String main = "";
    Button boutonEnregistrer;
    final Context context = this;
    RadioGroup radioGroupCotation;
    RadioButton boutonCotation0;
    RadioButton boutonCotation1;
    RadioButton boutonCotation2;
    RadioButton boutonCotation3;
    RadioButton boutonCotationNSP;
    MultiSelectionSpinner listeComment;
    String cotation = "cotation inconnue";
    String commentaire = "aucun commentaire";
    EditText comments;
    TextView infosPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_item22);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // on récupère les infos de l'intent
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            surname = intent.getStringExtra("surname");
            birthdate = intent.getStringExtra("birthdate");
            main = intent.getStringExtra("main");

            // on remplit la liste déroulante
            String[] array = {"rien", "difficulté", "arrêt", "change de doigt",
                    "soulève le doigt", "ne soulève pas le doigt", "glisse le doigt", "ne glisse pas le doigt",
                    "touche le quadrillage", "ne touche pas le quadrillage", "pose le doigt sans précision",
                    "avec compensation", "sans appui de la main", "avec appui de la main"};
            listeComment = (MultiSelectionSpinner) findViewById(R.id.mySpinner);
            listeComment.setItems(array);
            listeComment.setListener(this);
        }

        radioGroupCotation = (RadioGroup) findViewById(R.id.radioGroupCotation);
        boutonCotation0 = (RadioButton) findViewById(R.id.radioButton0);
        boutonCotation1 = (RadioButton) findViewById(R.id.radioButton1);
        boutonCotation2 = (RadioButton) findViewById(R.id.radioButton2);
        boutonCotation3 = (RadioButton) findViewById(R.id.radioButton3);
        boutonCotationNSP = (RadioButton) findViewById(R.id.radioButtonNSP);
        comments = (EditText) findViewById(R.id.editTextComments);

        infosPatient = (TextView) findViewById(R.id.PatientName);
        infosPatient.setText("Patient : " + name.toUpperCase() + " " + surname.toLowerCase() + " \nné(e) le : " + birthdate + "\n" + main);

        boutonEnregistrer = (Button) findViewById(R.id.buttonSave);
        boutonEnregistrer.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 // on vérifie qu'au moins un radioButton a été sélectionné dans le radioGroup de cotation
                 if (boutonCotation0.isChecked() || boutonCotation1.isChecked() || boutonCotation2.isChecked() || boutonCotation3.isChecked() || boutonCotationNSP.isChecked()) {
                     // --------------------- on récupère les commentaires du kiné -------------------
                     // ------- COTATION
                     int radioButtonSelectedID = radioGroupCotation.getCheckedRadioButtonId();
                     View radioButtonSelected = radioGroupCotation.findViewById(radioButtonSelectedID);
                     int index = radioGroupCotation.indexOfChild(radioButtonSelected);
                     RadioButton r = (RadioButton) radioGroupCotation.getChildAt(index);
                     cotation = r.getText().toString();
                     // ------- COMMENTAIRES
                     final List<String> listeComm = listeComment.getSelectedStrings();
                     commentaire = comments.getText().toString();
                     // ------------------------------------------------------------------------------

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
                                     // ------------------ CREATION et ENREGISTREMENT du PDF ------------------
                                     final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.FRANCE).format(new Date());
                                     String FILE = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + name.toLowerCase() + "_" + surname.toLowerCase() + "_" + timeStamp + ".pdf";
                                     // Create a document and set it's properties
                                     Document objDocument = new Document();
                                     objDocument.setCreator("MFM_application");
                                     objDocument.setAuthor("MFM_application");
                                     objDocument.setTitle(name.toLowerCase() + "_" + surname.toLowerCase() + "_" + timeStamp + ".pdf");

                                     // Create a page to add to the document
                                     Page page1 = new Page(PageSize.LETTER, PageOrientation.PORTRAIT, 54.0f);
                                     Page page2 = new Page(PageSize.LETTER, PageOrientation.PORTRAIT, 54.0f);

                                     String timeStampSimple = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(new Date());

                                     // Create a Label to add to the page
                                     String strText = " Patient : " + name + " " + surname +
                                             "\n Date de naissance : " + birthdate +
                                             "\n " + main +
                                             "\n\n Item 22" +
                                             "\n réalisé le : " + timeStampSimple +
                                             "\n\n INFORMATIONS COMPLEMENTAIRES : " +
                                             "\n Cotation : " + cotation +
                                             "\n Commentaires : " + listeComm +
                                             "\n " + commentaire;
                                     Font font = Font.getHelvetica();
                                     int fontSize = 18;
                                     float textWidth = font.getTextWidth(strText, fontSize);
                                     Label objLabel = new Label(strText, 0, 0, 504, textWidth, font, fontSize, TextAlign.LEFT);

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
                                         Toast.makeText(getApplicationContext(), R.string.savedOK, Toast.LENGTH_LONG).show();
                                         // on renvoie alors vers l'interface de choix d'item
                                         Intent myIntent = new Intent(comments_item22.this, choix_item.class);
                                         myIntent.putExtra("name", name);
                                         myIntent.putExtra("surname", surname);
                                         myIntent.putExtra("birthdate", birthdate);
                                         myIntent.putExtra("main", main);
                                         startActivity(myIntent);
                                         // on ferme l'activité en cours
                                         finish();
                                     } catch (Exception e) {
                                         e.printStackTrace();
                                         Toast.makeText(getApplicationContext(), R.string.savedPB, Toast.LENGTH_LONG).show();
                                     }
                                     // --------------------------------------------------------------------
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
                     Toast.makeText(getApplicationContext(), R.string.errorCotation, Toast.LENGTH_LONG).show();
                 }
             }
         }
        );
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
            Intent myIntent = new Intent(comments_item22.this, carto_item22.class);
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

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {

    }
}
