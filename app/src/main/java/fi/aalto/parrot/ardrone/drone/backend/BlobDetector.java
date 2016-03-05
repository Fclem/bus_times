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

    private Scalar mLowerBound = new Scalar(0, 155, 155, 0);
    private Scalar mUpperBound = new Scalar(100, 255, 255, 255);

    private List<MatOfPoint> mContours = new ArrayList<MatOfPoint>();

    /* Process an RGBA image to get blobs of interest.
     * Stores a list of contours of the found blobs.
     */
    public void process(Mat rbgaImage) {

        //TODO: do some downsampling here to reduce noise.

        Imgproc.cvtColor(rbgaImage, mHsvImg, Imgproc.COLOR_RGB2HSV);
        Core.inRange(mHsvImg, mLowerBound, mUpperBound, mBlobMask);
        Imgproc.dilate(mBlobMask, mDBMask, new Mat());

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(mDBMask, contours, mHierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        //TODO: do some contour filtering here
        mContours.clear();
        Iterator<MatOfPoint> each = contours.iterator();
        while(each.hasNext())
            mContours.add(each.next());
    }

    public List<MatOfPoint> getContours() {
        return mContours;
    }
}
