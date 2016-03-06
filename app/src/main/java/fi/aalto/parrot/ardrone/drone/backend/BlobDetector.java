package fi.aalto.parrot.ardrone.drone.backend;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sshehata on 3/5/16.
 */
public class BlobDetector {

    private Mat mHsvImg = new Mat();
    private Mat mBlobMask = new Mat();
    private Mat mDBMask = new Mat();
    private Mat mHierarchy = new Mat();

    // HSV values: 0..179 0..255 0..255
    private Scalar mLowerBound;
    private Scalar mUpperBound;

    private double mMinArea = 0.1;

    private List<MatOfPoint> mContours = new ArrayList<MatOfPoint>();

    public BlobDetector() {
        // supposed to be a default red blob detector
        // TODO: find a better formula for red detection
        this(new Scalar(0, 130, 50, 0), new Scalar(5, 255, 130, 255));
    }

    public BlobDetector(Scalar lbound, Scalar ubound) {
        mLowerBound = lbound;
        mUpperBound = ubound;
    }

    /* Process an RGBA image to get blobs of interest.
     * Stores a list of contours of the found blobs.
     */
    public void process(Mat rgbaImage) {
        Imgproc.cvtColor(rgbaImage, mHsvImg, Imgproc.COLOR_RGB2HSV);
        Core.inRange(mHsvImg, mLowerBound, mUpperBound, mBlobMask);
        Imgproc.dilate(mBlobMask, mDBMask, new Mat());

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        mContours.clear();
        Imgproc.findContours(mDBMask, mContours, mHierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);
    }

    public List<MatOfPoint> getContours() {
        return mContours;
    }
}
