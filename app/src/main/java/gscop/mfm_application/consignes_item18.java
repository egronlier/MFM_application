package gscop.mfm_application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class consignes_item18 extends Activity {

    Button boutonDemarrer;
    String name = "";
    String surname = "";
    String birthdate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consignes_item18);

        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("name");
            surname = intent.getStringExtra("surname");
            birthdate = intent.getStringExtra("birthdate");
        }

        boutonDemarrer = (Button) findViewById(R.id.boutonDemarrer);
        boutonDemarrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent2 = new Intent(consignes_item18.this, do_item18.class);
                myIntent2.putExtra("name", name);
                myIntent2.putExtra("surname", surname);
                myIntent2.putExtra("birthdate", birthdate);
                startActivity(myIntent2);
                // on ferme l'activité en cours
                finish();
            }
        });
    }

    private boolean back_answer = false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage(R.string.confirmBack)
//                    .setCancelable(false)
//                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
                            back_answer = true;
                            // on revient à l'écran de choix d'item
                            Intent myIntent = new Intent(consignes_item18.this, choix_item.class);
                            myIntent.putExtra("name", name);
                            myIntent.putExtra("surname", surname);
                            myIntent.putExtra("birthdate", birthdate);
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
