
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;
import org.opencv.core.CvException;
import org.opencv.face.*;

/**
*@Author: Hamdaoui Tayeb, tmimi443@gmail.com
*/

public class test {
    public static void main(String[] args) {
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	//Read the CSV file
    	Imagedataset CSV = new Imagedataset();
    	CSV.ReadCSV("resources\\att_faces\\DatasetCVS.txt");
    	
    	//read image
    	for(int i=0; i<CSV.getLabels().size(); i++) {
    		System.out.println();
    	}
    	
    	ArrayList<Mat> Imgs = new ArrayList<Mat>();
    	ArrayList<Integer> Labels = CSV.getLabels();
    	
    	try {
    		read_csv_generate_Mat(Imgs, CSV.getFileImage());
		} catch (CvException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
    	
    	//not enough data to do face recognition
    	if(Imgs.size() < 2) {
    		System.err.println("this application needs more than two images to work");
    		System.exit(1);
    	}
    	
    	//get the height of the first image, it will be needed later in the code
    	int Height = Imgs.get(0).rows();
    	//System.out.println(Height + "size = " + Imgs.size());
    	/*
    	 * this part now is only for testing the algorithm
    	 * essentially we take the last images so
    	 * that we can test the algo later and for the training data
    	 * to not include already existing images
    	 * */ 
    	Mat lastimage_testsample = Imgs.get(0);
    	int lastlabel = Labels.get(0);
    	Imgs.remove(0);
    	Labels.remove(0);
    	
    	//create face recognation model
    	EigenFaceRecognizer model = EigenFaceRecognizer.create(500);
    	//convert ArrayList<integer> into Mat
    	
    	
    	Mat labelsmat = Converters.vector_int_to_Mat(Labels);
    	//System.out.println(labelsmat.getClass().getPackageName());
    	//System.exit(1);
    	model.train(Imgs, labelsmat);
    	
    	//predict the label of the image
    	int predictedLabel = model.predict_label(lastimage_testsample);
    	
    	//to get the confidence we use:
    	//int predictedLabel = -1;
    	//double confidence = 0.0;
    	//model.predict(lastimage_testsample, predictedLabel, confidence);
    	
    	System.out.println("Predicted class = "+predictedLabel + ", Actual class = "+ lastlabel);
    	
    }
    
    //read CSV and generate matrices
    //add image to list of images
    public static void read_csv_generate_Mat(ArrayList<Mat> Imgs, ArrayList<String> imagespath) {
    	for(int i=0; i<imagespath.size(); i++) {
    		Imgs.add(Imgcodecs.imread(imagespath.get(i), Imgcodecs.IMREAD_GRAYSCALE));
    	}
    }
    
    

    
    
    
    //?? why normalize the image
    public Mat Normalize0_256(Mat src) {

    	int channel = src.channels();
    	switch(channel) {
    	case 1:
    		Core.normalize(src, src, 0, 255, Core.NORM_MINMAX, CvType.CV_8UC1);
    		break;
    	case 2:
    		Core.normalize(src, src, 0, 255, Core.NORM_MINMAX, CvType.CV_8UC2);
    		break;
    	case 3:
    		Core.normalize(src, src, 0, 255, Core.NORM_MINMAX, CvType.CV_8UC2);
    		break;
    	default:
    		return src;
    	}
    	return src;
    }
    

    
}
