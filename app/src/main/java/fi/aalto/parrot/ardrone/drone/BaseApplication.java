package fi.aalto.parrot.ardrone.drone;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;

/**
 * Created by sshehata on 3/4/16.
 */
public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        // TODO: find a way to use async init method
        if(!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Opencv library not found. Exiting");
            Toast.makeText(this, "Opencv library not found. Application will not work properly!", Toast.LENGTH_SHORT);
        } else {
            Log.d(TAG, "Opencv library loaded.");
        }
    }
}
