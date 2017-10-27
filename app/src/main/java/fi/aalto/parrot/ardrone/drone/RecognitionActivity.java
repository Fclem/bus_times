package fi.aalto.parrot.ardrone.drone;

import android.content.Context;
import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.support.v4.content.ContextCompat;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import fi.aalto.parrot.ardrone.drone.backend.BlobDetector;

public class RecognitionActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private CameraBridgeViewBase mOpenCvCameraView;
    private String face_cascade_name = "haarcascade_frontalface_alt.xml";
    private CascadeClassifier face;
    //private RNG rng(12345);

    private BlobDetector blobDetector;
    private static final Scalar BALL_COLOR = new Scalar(0,255,0);

    private static final String TAG = "RecognitionActivity";
    private static final Scalar CONTOUR_COLOUR = new Scalar(255, 255, 255, 255);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);

        try {
            InputStream is = getResources().openRawResource(R.raw.detector);
            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
            File cascadeFile = new File(cascadeDir, "detector.xml");
            FileOutputStream os = new FileOutputStream(cascadeFile);

            byte[] buffer = new byte[4096];
            int bytesRead = 0;
            while((bytesRead = is.read(buffer)) != -1)
                os.write(buffer, 0, bytesRead);
            is.close();
            os.close();

            blobDetector = new BlobDetector(cascadeFile.getAbsolutePath());
        } catch (IOException e) {
            Log.w("RecognitionActivity", "Something went wrong!");
        }


        setContentView(R.layout.activity_recognition);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.HelloOpenCvView);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

    }

    @Override
    public void onResume()
    {
        super.onResume();
        mOpenCvCameraView.enableView();
        mOpenCvCameraView.enableFpsMeter();

        super.onResume();
        //OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_6, this, mLoaderCallback);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
    }

    public void onCameraViewStopped() {
    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        Mat rgbaImg = inputFrame.rgba();
        if (inputFrame.gray().empty())
            return rgbaImg;
        /*
         blobDetector.detectAndDisplay(inputFrame.gray());

        //Rect[] ballsArray = blobDetector.getBalls();
        for(int i = 0; i < ballsArray.length; i++)
            Imgproc.rectangle(rgbaImg, ballsArray[i].tl(), ballsArray[i].br(), BALL_COLOR, 3);
        */
        return rgbaImg;
    }
}
