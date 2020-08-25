package it.uninsubria.pdm.trovaprezzi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView tvrisultato;
    String readbarcode;
    String outputprod;
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
                    readbarcode=barcode.displayValue;
                    DownloadTask task= new DownloadTask ();
                    task.execute ( "https://api.barcodelookup.com/v2/products?barcode=" + readbarcode + "&formatted=y&key=uib5sgx0puls4twxs6b7e2eg8felpg");
                    tvrisultato.setText ( outputprod );
                } else {
                    super.onActivityResult ( RequestCode, ResultCode, data );
                }
            } else {
                tvrisultato.setText ( "no barcode found" );
            }
        }
    }
    public class DownloadTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            String result="Barcode non valido!";
            try {
                URL url=new URL (strings[0]);
                BufferedReader br = new BufferedReader (new InputStreamReader (url.openStream()));
                String str = "";
                String data = "";
                while (null != (str= br.readLine())) {
                    data+=str;
                }

                Gson g = new Gson ();

                RootObject value = g.fromJson(data, RootObject.class);
                result= "Barcode Number: " + value.products[0].barcode_number + "\n" +
                        "Nome: " + value.products[0].product_name + "\n";
                for(int i=0;i<value.products[0].stores.length;i++)
                    result+="Price: " + value.products[0].stores[i].store_price + " " + value.products[0].stores[i].currency_code + " (" + value.products[0].stores[i].store_name + ") " +"\n";


            } catch (Exception e) {
                e.printStackTrace ( );
            }
            outputprod=result;
            return result;
        }
    }

    public class Store
    {
        public String store_name;
        public String store_price;
        public String product_url;
        public String currency_code;
        public String currency_symbol;
    }


    public class Product
    {
        public String barcode_number;
        public String product_name;
        public Store[] stores;

    }

    public class RootObject
    {
        public Product[] products;
    }
}