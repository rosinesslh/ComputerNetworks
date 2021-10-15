package ip2as;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class ip2as {
	
	public static int LongestPrefix(String x, String y) {
	    int minLength = Math.min(x.length(), y.length());
	    for (int i = 0; i < minLength; i++) {
	        if (x.charAt(i) != y.charAt(i)) {
	            return i;
	        }
	    }
	    return -1;
	}

	public static void main(String[] args) {
		int inputcounter, databasecounter = 0;
		File file = new File("IPlist.txt");
		String[] inp = new String[10];
		
		String[] database = new String[155130];
		int counter = 0;
		
		Scanner scan;
		try {
			scan = new Scanner(file);
			while(scan.hasNext()){
	            inp[counter] = scan.nextLine().toString();
	            counter++;
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		inputcounter = counter;
		
		File DB = new File("DB_091803.txt");
		Scanner scan2;
		counter = 0;
		try {
			scan2 = new Scanner(DB);
			while(scan2.hasNext()){
	            database[counter] = scan2.nextLine().toString();
	            counter++;
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		databasecounter = counter;
				
		String delims = "[ .]+";
		String delims2 = "[ ]+";

		String[] longest = new String [10];
		String longest2 = "";
		int c = 0;
		for(int i = 0 ; i < inputcounter; i++)
		{
			int longestcounter = 0;

			for(int j = 0; j<databasecounter; j++) {
				if(longestcounter <= LongestPrefix(inp[i], database[j])) {
					longest2 = database[j];
					longestcounter = LongestPrefix(inp[i], database[j]);
				}
			}
			longest[c] = longest2;
			c++;

		}
		int m = 0;
		String[] newlongest = new String[100];
		for(int i =0; i < c; i++) {
			String [] token = longest[i].split(delims2);
			int min = token[0].length();
			for(int j = 0; j < databasecounter; j++) {
				if(min <= LongestPrefix(longest[i], database[j])){
					newlongest[m] = database[j];
					m++;
				}	
			}
		}
		
		
		for (int i = 0; i<c; i++) {
			newlongest[m] = longest[i];
			m++;
		}

		String longest3 = "";
		String[] final_output = new String [inputcounter];
		for(int i = 0 ; i < inputcounter; i++)
		{
			String [] input_token = inp[i].split(delims);
			int input_t = Integer.parseInt(input_token[3]);
			int longestcounter = 0;
			for(int j = 0; j<m; j++) {
				String [] database_token = newlongest[j].split(delims);
				int database_t = Integer.parseInt(database_token[3]);
				int degree = Integer.parseInt(database_token[4]);
				if(longestcounter <= LongestPrefix(inp[i], newlongest[j]) && input_t <= Math.pow(2, 32-degree)+ database_t - 1 ) {
					longest3 = newlongest[j];
					longestcounter = LongestPrefix(inp[i], newlongest[j]);
				}
			}
			final_output[i] = longest3;

		}
	      File ot = new File("output.txt");
	      String [][] final_ver = new String[inputcounter][3];
	      try {
	    	  ot.createNewFile(); 
				  FileWriter writer = new FileWriter("output.txt");
				  for(int i = 0; i < inputcounter; i++) {
					final_ver[i] = final_output[i].split(delims2);
					writer.write(final_ver[i][0] +"/" + final_ver[i][1] + " " + final_ver[i][2] + " "+ inp[i]+ "\n" );
					System.out.println(final_ver[i][0] +"/" + final_ver[i][1] + " " + final_ver[i][2] + " "+ inp[i]);	
				  }
						writer.close();

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	        
	}

}
