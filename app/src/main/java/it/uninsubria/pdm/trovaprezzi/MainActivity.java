package it.uninsubria.pdm.trovaprezzi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView risultato;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        risultato= (TextView)findViewById ( R.id.tvrisultato );
    }

    public void scatta(View view) {
        Log.d ( "scattafoto", "Trova Prezzo");
    }
}