package fi.aalto.parrot.ardrone.drone.backend;

import android.graphics.Color;
import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fi.aalto.parrot.ardrone.drone.R;

/**
 * Created by sshehata on 3/5/16.
 */
public class BlobDetector {

    private CascadeClassifier mCascadeDetector;
    private MatOfRect balls;


    public BlobDetector(String cascadeFile) {
        mCascadeDetector = new CascadeClassifier(cascadeFile);
        if (mCascadeDetector.empty())
            Log.w("Detector", "EMPTYYYYYYYYYYYYYYYYYYYY!");

    }

    /* Process an RGBA image to get blobs of interest.
     * Stores a list of contours of the found blobs.
     */
    public void detectAndDisplay(Mat grayscale) {
        balls = new MatOfRect();
        mCascadeDetector.detectMultiScale(grayscale, balls);
    }

    public Rect[] getBalls() {
        return balls.toArray();
    }
}
