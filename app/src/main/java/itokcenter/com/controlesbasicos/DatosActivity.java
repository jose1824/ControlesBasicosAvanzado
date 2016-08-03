package itokcenter.com.controlesbasicos;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DatosActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    EditText etNombre, etEdad, etCorreo, etTwitter;
    TextView txtNombre, txtEdad, txtCorreo, txtTwitter;
    Button btnGuardad, btnMostrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        etNombre = (EditText) findViewById(R.id.datos_et_nombre);
        etEdad = (EditText) findViewById(R.id.datos_et_edad);
        etCorreo = (EditText) findViewById(R.id.datos_et_mail);
        etTwitter = (EditText) findViewById(R.id.datos_et_twitter);

        txtNombre = (TextView) findViewById(R.id.datos_txt_nombre);
        txtEdad = (TextView) findViewById(R.id.datos_txt_edad);
        txtCorreo = (TextView) findViewById(R.id.datos_txt_mail);
        txtTwitter = (TextView) findViewById(R.id.datos_txt_twitter);
    }

    public void guardarDatos(View v) {
        editor = sharedPreferences.edit();
        editor.putString(getString(R.string.nombre), etNombre.getText().toString());
        editor.putInt(getString(R.string.edad), Integer.parseInt(etEdad.getText().toString()));
        editor.putString(getString(R.string.mail), etCorreo.getText().toString());
        editor.putString(getString(R.string.twitter), etTwitter.getText().toString());
        editor.apply(); //Escribir de golpe el shared preferences el Commit es màs lento

    }
}
