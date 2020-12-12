import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Scanner;


//format
/*
 * -DATASET
 * --------s1
 * 			|_1.pmg
 * 			|_2.pmg
 * 			|
 * 			|
 * 			|_n.pmg
 * --------s2
 * 			|_1.pmg
 * 			|_2.pmg
 * 			|
 * 			|
 * 			|_n.pmg  			
 * */



public class Imagedataset {
	private final String FilePath = "resources\\DATASETCSV.txt"; //where you wanna put the csv file
	private int NumberOfIndividuals;
	private int NumberOfImages;//each individual must have the same number of photos
	private ArrayList<String> FileImage = new ArrayList<String>(); //an array of image paths
	private ArrayList<Integer> Labels = new ArrayList<Integer>(); //each person has a label that identifies him


	
	//constructor
	// filePath is the location of csv file
	public Imagedataset(int numberOfIndividuals, int numberOfImages) {
		NumberOfIndividuals = numberOfIndividuals;
		NumberOfImages = numberOfImages;
	}
	
	public Imagedataset() {

	}
	
	public ArrayList<String> getFileImage() {
		return FileImage;
	}
	
	public ArrayList<Integer> getLabels() {
		return Labels;
	}

	//read the DATASET we provided
	public void ReadCSV() {

	    try (BufferedReader br = new BufferedReader(new FileReader(this.FilePath))) {
	        
	        String s = null;
	        while((s = br.readLine()) != null){
	            String[] str =  s.split(";");
	            this.FileImage.add(str[0]);
	            this.Labels.add(Integer.parseInt(str[1]));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	}
	//read the DATASET we provided
	public void ReadCSV(String file) {

	    try (BufferedReader br = new BufferedReader(new FileReader(this.FilePath))) {
	        
	        String s = null;
	        while((s = br.readLine()) != null){
	        	if(s != null) {
		            String[] str =  s.split(";");
		            this.FileImage.add(str[0]);
		            this.Labels.add(Integer.parseInt(str[1]));
	        	}
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	}


    //Create write a DATASETfile for a data set with PATH;LABEL
	//Directory where DATASET is stored, user can add his own DATASET or use the default one in resources
    public void WriteCSV(String Directory) {

    	System.out.println("S'il vous plait la format d'image doit etre pmg avec un nom par example s1/1.pmg");
    	
    	Scanner keyb = new Scanner(System.in);
    	
    	System.out.println("entrez le nombre des individus");
    	this.NumberOfIndividuals = keyb.nextInt();
    	
    	System.out.println("entrez le nombre des images pour chaque individus");
    	this.NumberOfImages = keyb.nextInt();
        
    	try (FileWriter fw = new FileWriter(this.FilePath);
             BufferedWriter bw = new BufferedWriter(fw)
        ) {
            for (int i = 0; i < this.NumberOfIndividuals; i++) {
                for(int j = 0; j < this.NumberOfImages; j++){
                    bw.write(Directory+"\\s"+(i+1)+"\\"+(j+1)+".pgm;"+i+"\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    	keyb.close();
    }
}


