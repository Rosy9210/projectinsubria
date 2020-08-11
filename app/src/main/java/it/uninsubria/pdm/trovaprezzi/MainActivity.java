package it.uninsubria.pdm.trovaprezzi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends AppCompatActivity {
    TextView tvrisultato;
    private Intent data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        tvrisultato= (TextView)findViewById ( R.id.tvrisultato );
    }


    public void scanBarcode(View view){
        Intent intent=new Intent(this,ScanBarcodeActivity.class);
        startActivityForResult ( intent, 0 );
    }
    @Override
    protected void onActivityResult(int RequestCode,int ResultCode,Intent data) {
        if (RequestCode == 0) {
            if (ResultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra ( "barcode" );
                    tvrisultato.setText ( "Barcode value :" + barcode.displayValue );
                } else {
                    super.onActivityResult ( RequestCode, ResultCode, data );
                }
            } else {
                tvrisultato.setText ( "no barcode found" );
            }
        }
    }

}