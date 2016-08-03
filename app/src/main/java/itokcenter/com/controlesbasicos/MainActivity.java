package itokcenter.com.controlesbasicos;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    AppCompatSeekBar seekBar;
    TextView txtDataUno, txtDataDos;
    String TAG = "MainActivity";
    RadioGroup radioGroup;

    private int brillo;
    private ContentResolver cResolver;
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        seekBar = (AppCompatSeekBar) findViewById(R.id.main_seekBar);
        txtDataUno = (TextView) findViewById(R.id.txtData);
        txtDataDos = (TextView) findViewById(R.id.txtSecondData);

        radioGroup = (RadioGroup) findViewById(R.id.main_radioGroup);

        toolbar.setTitle("Titulo de mi toolbar");
        toolbar.setSubtitle("Subtitulo de mi toolbar");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //------------------------------------------------------------------------------------------
        cResolver = getContentResolver();
        window = getWindow();
        seekBar.setKeyProgressIncrement(1);

        try {
            brillo = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        seekBar.setProgress(brillo);
        //------------------------------------------------------------------------------------------


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int position, boolean b) {
                //txtDataUno.setText(position + "");

                //----------------------------------------------------------------------------------
                if (position <= 20) {
                    brillo = 20;
                } else {
                    brillo = position;
                }
                float calculo = (brillo / (float)255) * 100;
                txtDataUno.setText((int)calculo + "%");
                txtDataDos.setTranslationY((int)calculo);
                //----------------------------------------------------------------------------------
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //---------------------------------------------------------------------------------
                Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, brillo);
                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.screenBrightness = brillo / (float)255;
                window.setAttributes(layoutParams);
                //----------------------------------------------------------------------------------
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i) {
                    case R.id.main_radioButton1:
                        Log.e("rdgroup", getString(R.string.opcion_1));
                        break;
                    case R.id.main_radioButton2:
                        Log.e("rdgroup", getString(R.string.opcion_2));
                        break;
                    case R.id.main_radioButton3:
                        Log.e("rdgroup", getString(R.string.opcion_3));
                        break;
                    default:
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opc1:
                Toast.makeText(getBaseContext(), "Opción 1 presionada", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.opc2:
                Log.e(TAG, "onOptionsItemSelected: " + "opcion2");
                return true;
            case R.id.opc3:
                Log.i(TAG, "onOptionsItemSelected: " + "opcion3");
                return true;
            case R.id.opc4:
                Log.d(TAG, "onOptionsItemSelected: " + "Opcion 4");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getOpcion(View v) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selectedId);
        Toast.makeText(MainActivity.this, radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    public void vibrar(View v) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
    }

}
