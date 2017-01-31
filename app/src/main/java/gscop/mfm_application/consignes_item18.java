package gscop.mfm_application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class consignes_item18 extends Activity {

    Button boutonRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.consignes_item18);

        boutonRetour = (Button) findViewById(R.id.boutonRetour);
        boutonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(consignes_item18.this, choix_item.class);
                startActivity(myIntent);
            }
        });
    }
}
