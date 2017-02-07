package gscop.mfm_application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

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

                                            // ------------------------- A FAIRE -----------------------------

//                                            // --------------- VERSION TEST SANS PDF -----------------
//                                            // on crée un nouveau fichier. S'il existe déjà, il ne sera pas créé
//                                            try {
//                                                //fileInput = new FileInputStream("entree.txt"); // on ouvre le fichier à lire
//                                                String test = "fichier test lecture";
//                                                //fileOutput = new FileOutputStream("sortie.txt");
//                                                fileOutput = openFileOutput(PRENOM, MODE_PRIVATE);
//                                                // MODE_PRIVATE : crée un fichier utilisé que par cette appli
//                                                // MODE_WORLD_READABLE : fichier lu par d'autres appli
//                                                // MODE_WORLD_WRITABLE : fichier modifiable par d'autres appli
//                                                // MODE_APPEND : on peut écrire à la fin du fichier préexistant
////                                               int octet;
////                                                // on utilise un StringBuffer pour construire la chaine au fur et à mesure
////                                            StringBuffer lu = new StringBuffer();
////                                            try{
////                                                // on lit les caractères un par un
////                                               // la méthode renvoie -1 dès qu'il n'y a plus rien à lire
////                                                while ((octet = fileInput.read()) != -1) {
////                                                    fileOutput.write(octet);
////                                                }
//                                                fileOutput.write(test.getBytes());
//                                                System.out.println("test affichage output : \n \n" + fileOutput.toString());
//                                                Toast.makeText(getApplicationContext(), R.string.savedOK, Toast.LENGTH_LONG).show();
////                                                if (fileInput != null)
////                                                    fileInput.close();
//                                                if (fileOutput != null)
//                                                    fileOutput.close();
//                                            } catch (FileNotFoundException e) {
//                                                e.printStackTrace();
//                                            } catch (IOException e) {
//                                                e.printStackTrace();
//                                            }
//                                            // -------------------------------------------------------

//                                            // -------------------- VERSION TEST AVEC PDF ------------
//                                                // on crée un nouveau doc
//                                                PdfDocument document = new PdfDocument();
//                                                // on crée une description de page
//                                                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(new Rect(0,0,100,100),1).create();
//                                                // on commence la page
//                                                PdfDocument.Page page = document.startPage(pageInfo);
//                                                // on met quelquechose sur la page
//                                                View content = getContentView();
//                                                content.draw(page.getCanvas());
//                                                // on finit la page
//                                                document.finishPage(page);
//                                                // on ajoute des pages
//                                                // ...
//                                                // on écrit le contenu du document
//                                                document.writeTo(getOutputStream());
//                                                // on ferme le document
//                                                document.close();
//
//                                            // -------------------------------------------------------A CONTINUER :)

                                            // ---------------------------------------------------------------
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
