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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import tutoandroid.libmultispinner.MultiSelectionSpinner;

public class comments_item18 extends Activity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {

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
    RadioGroup radioGroupCercle;
    RadioButton boutonCerclePetit;
    RadioButton boutonCercleGrand;
    MultiSelectionSpinner listeComment;
    EditText comments;
    String cotation = "cotation inconnue";
    String cercle = "cercle inconnu";
    String commentaire = "aucun commentaire";
    TextView infosPatient;
    String path = "";
    Bitmap cartoBitmap;
    File myFile;
    List<String> listeComm ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_item18);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // on récupère les infos de l'intent
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            surname = intent.getStringExtra("surname");
            birthdate = intent.getStringExtra("birthdate");
            main = intent.getStringExtra("main");
            path = intent.getStringExtra("path");
            try {
                File f = new File(path, "cartographie.png");
                cartoBitmap = BitmapFactory.decodeStream(new FileInputStream(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), R.string.errorCarto, Toast.LENGTH_LONG).show();
            }
            // on remplit la liste déroulante
            String[] array = {"-", "difficulté", "sans appui de la main", "avec appui de la main", "arrêt", "change de doigt", "avec compensation"};
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
        radioGroupCercle = (RadioGroup) findViewById(R.id.radioGroupCercle);
        boutonCerclePetit = (RadioButton) findViewById(R.id.radioButtonSmall);
        boutonCercleGrand = (RadioButton) findViewById(R.id.radioButtonBig);

        comments = (EditText) findViewById(R.id.editTextComments);
        comments.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        infosPatient = (TextView) findViewById(R.id.PatientName);
        infosPatient.setText("Patient : " + name + " " + surname + " \nné(e) le : " + birthdate + "\n" + main);

        boutonEnregistrer = (Button) findViewById(R.id.buttonSave);
        boutonEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on évite que la personne clique 2 fois sur le bouton en le rendant non cliquable
                boutonEnregistrer.setClickable(false);

                // on vérifie qu'au moins un radioButton a été sélectionné dans chaque radioGroup
                // radioGroup : cotation
                if (boutonCotation0.isChecked() || boutonCotation1.isChecked() || boutonCotation2.isChecked() || boutonCotation3.isChecked() || boutonCotationNSP.isChecked()) {
                    // radioGroup : cercle
                    if (boutonCercleGrand.isChecked() || boutonCerclePetit.isChecked()) {

                        // --------------------- on récupère les commentaires du kiné -------------------
                        // ------- COTATION
                        int radioButtonSelectedID = radioGroupCotation.getCheckedRadioButtonId();
                        View radioButtonSelected = radioGroupCotation.findViewById(radioButtonSelectedID);
                        int index = radioGroupCotation.indexOfChild(radioButtonSelected);
                        RadioButton r = (RadioButton) radioGroupCotation.getChildAt(index);
                        cotation = r.getText().toString();
                        // ------- CERCLE
                        radioButtonSelectedID = radioGroupCercle.getCheckedRadioButtonId();
                        radioButtonSelected = radioGroupCercle.findViewById(radioButtonSelectedID);
                        index = radioGroupCercle.indexOfChild(radioButtonSelected);
                        r = (RadioButton) radioGroupCercle.getChildAt(index);
                        cercle = r.getText().toString();
                        // ------- COMMENTAIRES
                        listeComm = listeComment.getSelectedStrings();
                        commentaire = comments.getText().toString();
                        // ------------------------------------------------------------------------------

                        // ouvrir une boite de dialogue permettant de valider la création du pdf
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder
                                .setTitle("Confirmation de validation")
                                .setMessage("Etes-vous certain de vouloir créer un fichier pour ce patient ?")
                                .setCancelable(false)
                                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, on fait l'enregistrement
                                        dialog.cancel();
                                        try {
                                            // ----------- CREATION DU PDF -------------
                                            createPdf();
                                            Toast.makeText(getApplicationContext(), R.string.savedOK, Toast.LENGTH_LONG).show();
                                            // on renvoie alors vers l'interface de choix d'item
                                            Intent myIntent = new Intent(comments_item18.this, choix_item.class);
                                            myIntent.putExtra("name", name);
                                            myIntent.putExtra("surname", surname);
                                            myIntent.putExtra("birthdate", birthdate);
                                            myIntent.putExtra("main", main);
                                            startActivity(myIntent);
                                            // on ferme l'activité en cours
                                            finish();
                                        } catch (FileNotFoundException | DocumentException e) {
                                            e.printStackTrace();
                                            Toast.makeText(getApplicationContext(), R.string.pbPDF, Toast.LENGTH_LONG).show();
                                        }
                                   }
                                })
                                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // if this button is clicked, close the dialog box
                                        dialog.cancel();
                                        boutonEnregistrer.setClickable(true);
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
            // on revient à l'écran d'affichage de cartographie de l'item 18
            Intent myIntent = new Intent(comments_item18.this, carto_item18.class);
            myIntent.putExtra("name", name);
            myIntent.putExtra("surname", surname);
            myIntent.putExtra("birthdate", birthdate);
            myIntent.putExtra("main", main);
            myIntent.putExtra("path",path);
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
//   Toast.makeText(this, strings.toString(), Toast.LENGTH_LONG).show();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void createPdf() throws FileNotFoundException, DocumentException {

        // on crée un dossier NOM_prenom du patient s'il n'existe pas déjà
        File pdfFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                , "patient_" + name + "_" + surname);
        if (!pdfFolder.exists()) {
            pdfFolder.mkdir();
            Log.i("TAG", "Pdf Directory created");
        }

        //Create time stamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.FRANCE).format(new Date());

        // on crée le nom du fichier pdf à enregistrer
        String filePath = pdfFolder.toString() + "/" + name + "_" + surname + "_" + timeStamp + "_" + "item18.pdf";
        myFile = new File(filePath);
        OutputStream output = new FileOutputStream(myFile);

        //Step 1 : on crée le document
        Document document = new Document(PageSize.LETTER);
        document.setMarginMirroring(true);
        document.setMarginMirroringTopBottom(true);

        //Step 2 : on instantie le PdfWriter
        PdfWriter.getInstance(document, output);

        //Step 3 : ouverture du document
        document.open();

        //Step 4 : Add content
        // choix des polices
        Font myFontTitre = new Font(Font.FontFamily.HELVETICA,24,Font.BOLD);
        // TITRE
        Paragraph paragraphTitre = new Paragraph();
        paragraphTitre.setAlignment(Element.ALIGN_CENTER);
        paragraphTitre.setFont(myFontTitre);
        paragraphTitre.add("Fiche récapitulative \n \n \n");
        document.add(paragraphTitre);

        // INFOS PATIENT
        Paragraph paragraphInfosTitre = new Paragraph();
        paragraphInfosTitre.setFont(myFontTitre);
        paragraphInfosTitre.add("\n\n INFORMATIONS PATIENT : \n");
        document.add(paragraphInfosTitre);

        String strText = " Patient : " + name + " " + surname +
                "\n Date de naissance : " + birthdate +
                "\n " + main + "\n \n";
        Paragraph paragraphInfos = new Paragraph();
        paragraphInfos.add(strText);
        document.add(paragraphInfos);

        // INFOS ITEM
        Paragraph paragraphInfosItemTitre = new Paragraph();
        paragraphInfosItemTitre.setFont(myFontTitre);
        paragraphInfosItemTitre.add("\n ITEM 18 :");
        document.add(paragraphInfosItemTitre);

        String timeStampSimple = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(new Date());
        strText = "réalisé le : " + timeStampSimple + "\n \n";
        Paragraph paragraphInfosItem = new Paragraph();
        paragraphInfosItem.add(strText);
        document.add(paragraphInfosItem);

        // INFOS COMPLEMENTAIRES
        Paragraph paragraphInfosCompTitre = new Paragraph();
        paragraphInfosCompTitre.setFont(myFontTitre);
        paragraphInfosCompTitre.add("\n INFORMATIONS COMPLEMENTAIRES : \n");
        document.add(paragraphInfosCompTitre);

        strText = "Cotation : " + cotation + "\nCercle : " + cercle + "\n \n";
        Paragraph paragraphInfosComp = new Paragraph();
        paragraphInfosComp.add(strText);
        document.add(paragraphInfosComp);

        // COMMENTAIRES KINE
        Paragraph paragraphCommKineTitre = new Paragraph();
        paragraphCommKineTitre.setFont(myFontTitre);
        paragraphCommKineTitre.add("\n COMMENTAIRES : \n");
        document.add(paragraphCommKineTitre);

        strText = listeComm + "\n" + commentaire + "\n \n";
        Paragraph paragraphCommKine = new Paragraph();
        paragraphCommKine.add(strText);
        document.add(paragraphCommKine);

        // CARTOGRAPHIE
        // on change de page
        document.newPage();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        cartoBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image trueImage = null;
        try {
            trueImage = Image.getInstance(stream.toByteArray());
            Log.i("TAG", "Try image succeeded");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Paragraph paragraphCarto = new Paragraph();
        paragraphCarto.setFont(myFontTitre);
        paragraphCarto.setAlignment(Element.ALIGN_CENTER);
        paragraphCarto.add("Cartographie : \n");
        document.add(paragraphCarto);
        document.add(trueImage);

        //Step 5: Close the document
        document.close();

//        promptForNextAction();
    }
}

