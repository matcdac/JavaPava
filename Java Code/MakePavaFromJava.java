
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Stack;

public class MakePavaFromJava {
	
	private static Stack<Boolean> getBinaryDataFromUnicodeInteger(int decimal) {
		
		Stack<Boolean> stack = new Stack<Boolean>();
		
		while(decimal>0) {
			if(decimal%2 == 0)
				stack.push(false);
			else
				stack.push(true);
			decimal=decimal/2;
		}
		
		return stack;
		
	}

	private static int tellMeHowManyToFetch(Stack<Boolean> binaries) {
		
		int i = (binaries.size()>4) ? (binaries.size()%4) : ( (binaries.size()==4) ? 4 : binaries.size() ) ;
		return i;
		
	}
	
	private static String getHexadecimalStringFromUnicadeBinary(Stack<Boolean> binaries) {
		String result = new String();

		while(!binaries.empty()) {
			int currentResult = 0;
			Boolean twoPowerZero=false, twoPowerOne=false, twoPowerTwo=false, twoPowerThree=false;
			
			switch( tellMeHowManyToFetch(binaries) ) {
				case 4:
					twoPowerThree = binaries.pop();
					if(twoPowerThree) {
						currentResult+=8;
					}
				case 3:
					twoPowerTwo = binaries.pop();
					if(twoPowerTwo) {
						currentResult+=4;
					}
				case 2:
					twoPowerOne = binaries.pop();
					if(twoPowerOne) {
						currentResult+=2;
					}
				case 1:
					twoPowerZero = binaries.pop();
					if(twoPowerZero) {
						currentResult+=1;
					}
			}

			switch(currentResult) {
				case 15: result = result.concat("F"); break;
				case 14: result = result.concat("E"); break;
				case 13: result = result.concat("D"); break;
				case 12: result = result.concat("C"); break;
				case 11: result = result.concat("B"); break;
				case 10: result = result.concat("A"); break;
				default: result = result+currentResult;
			}
		}
		
		while(result.length()!=4)
			result="0".concat(result);
		
		return result;
	}

	private static String escapseSequenceFor(char character) {
		int decimal = (int)character;
		Stack<Boolean> binaries = getBinaryDataFromUnicodeInteger(decimal);
		String hexadecimal = getHexadecimalStringFromUnicadeBinary(binaries);
		
		return "\\u"+hexadecimal;
	}
	
	private static void startEncodeProcess(String input, String output) throws Exception {
		
		FileReader fr=new FileReader(input);
		FileWriter fw=new FileWriter(output);
		
		BufferedReader br=new BufferedReader(fr);
		
		String currentLine;
		
		while( (currentLine=br.readLine())!=null ) {
			
			for(char character:currentLine.toCharArray()) {
				String EscapseSequenceForEachUnicodeCharacterInFile = escapseSequenceFor(character);
				fw.write(EscapseSequenceForEachUnicodeCharacterInFile);
			}
			
		}
		
		br.close();
		fw.close();
		fr.close();
		
	}
	
	private static void deleteOldFile(String o, String n) {
		File of = new File(o);
		
		if( of.delete() ) {
			System.out.println("Old File Deleted Successfully");
			renameNewFileWithSameName(o,n);
		}
	}
	
	private static void renameNewFileWithSameName(String o, String n) {
		File of = new File(o);
		File nf = new File(n);
		
		if( nf.renameTo(of) ) {
			System.out.println("New File Renamed To Old File Successfully");
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		String path = "D:\\REVEL\\workspace\\z-debryan\\src\\z\\";
		String filename = "debryan.java";
		
		System.out.println("Enter the directory path where your file recides. for eg:");
		System.out.println(path);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		path = br.readLine();
		
		System.out.println("Enter the file name. for eg:");
		System.out.println(filename);
		
		br = new BufferedReader(new InputStreamReader(System.in));
		filename = br.readLine();
		
		String inputFileName = path + filename;
		String tempOutputFileName = path + "temp.java";
		
		startEncodeProcess(inputFileName,tempOutputFileName);
		
		deleteOldFile(inputFileName,tempOutputFileName);
		
	}

}
