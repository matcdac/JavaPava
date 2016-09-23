
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class MakeJavaFromPava {
	
	private static int HexadecimalToDecimal(String hexa) {
		
		int result = 0;
		
		for(int i=1;i<=4;i++) {
			char currentChar = hexa.charAt(i-1);

			int current = 0;
			switch(currentChar) {
				case 'F': current=15; break;
				case 'E': current=14; break;
				case 'D': current=13; break;
				case 'C': current=12; break;
				case 'B': current=11; break;
				case 'A': current=10; break;
				default: current=Integer.parseInt(currentChar+""); break;
			}
			
			result = current*(int)Math.pow(16,4-i) + result;
		}

		return result;
		
	}
	
	private static char unicodeCharacterFor(String escape) {
		
		int value = HexadecimalToDecimal(escape.substring(2));
		char unicodeCharacter = (char)((short)value);

		return unicodeCharacter;
	}
	
	private static void startDecodeProcess(String input, String output) throws Exception {
		
		FileReader fr=new FileReader(input);
		FileWriter fw=new FileWriter(output);
		
		BufferedReader br=new BufferedReader(fr);
		
		String currentLine;
		
		while( (currentLine=br.readLine())!=null ) {
			
			for(int i=0;i<currentLine.length();i=i+6) {
				String escapeSequenceString = currentLine.substring(i, i+6);
				char unicodeCharacterForEachEscapeSequenceInFile = unicodeCharacterFor(escapeSequenceString);
				
				fw.write(unicodeCharacterForEachEscapeSequenceInFile);
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
		
		String inputFileName = path + filename ;
		String tempOutputFileName = path + "temp.java";
		
		startDecodeProcess(inputFileName,tempOutputFileName);
		
		deleteOldFile(inputFileName,tempOutputFileName);
		
	}

}
