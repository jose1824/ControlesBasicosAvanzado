package itokcenter.com.controlesbasicos;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;
import android.view.Window;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.security.Policy;
import java.util.logging.Logger;

public class NewAppWidget extends AppWidgetProvider {

    private int brillo;
    private ContentResolver cResolver;
    private Window window;

    private static final String myOnClick = "myOnClickTag";
    private static final String myOnClick2 = "myOnClickTag2";

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
            views.setOnClickPendingIntent(R.id.appwidget_button, pendingIntent);

            views.setOnClickPendingIntent(R.id.appwidget_button_brillo,
                    getPendingSelfIntent(context, myOnClick));

            views.setOnClickPendingIntent(R.id.appwidget_button_flash,
                    getPendingSelfIntent(context, myOnClick2));

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (myOnClick.equals(intent.getAction())) {
            Toast.makeText(context, "Brillo", Toast.LENGTH_SHORT).show();
            Log.e("onReceive", "Brillo");
        } else if (myOnClick2.equals(intent.getAction())) {

            PackageManager pm = context.getPackageManager();
            if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                Log.e("Flashlight", "Ha fallado la camara");
                Toast.makeText(context, "No se pudo :(", Toast.LENGTH_SHORT).show();
                return;
            }

            android.hardware.Camera camera = android.hardware.Camera.open();
            final Camera.Parameters p = camera.getParameters();
            p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(p);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.release();
            Toast.makeText(context, "Enjoy it", Toast.LENGTH_SHORT).show();
            Log.e("onReceive", "Brillo");
        }
    }


}

