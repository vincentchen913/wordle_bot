import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
	
	static int[] scores = new int[26];
	static ArrayList<Word> words;
	static String correct;
	
	public static void main(String[] args) throws IOException{
		File f = new File("words.txt");
		Scanner sc = new Scanner(f);
		Scanner in = new Scanner(System.in);
		//Scanner in = new Scanner(System.in);
		words = new ArrayList<>();
		correct = "";
		fillScores();
		while(sc.hasNext()) {
			String curr = sc.next();
			Word temp = new Word(curr);
			words.add(temp);
			
		}
		sc.close();
		Collections.sort(words);
		Word curr = words.get(words.size() - 1);
		System.out.println("First Word: " + curr.name);
		System.out.println();
		
		for(int i = 0; i < 6; i++) {
			System.out.println("Enter sequence: ");
			String seq = in.next();
			Word temp = getNextWord(curr, seq);
			System.out.println("Next word: " + temp.name);
			System.out.println();
			curr = temp;
		}
		in.close();
	}
	
	public static Word getNextWord(Word w, String seq) {
		//check for greens
		for(int i = 0; i < seq.length(); i++) {
			if(seq.charAt(i) == 'g') {
				if(correct.indexOf(w.name.charAt(i)) == -1) {
					correct += (w.name.charAt(i));
				}
				Iterator<Word> it = words.iterator();
				while(it.hasNext()) {
					Word curr = it.next();
					if(w.name.charAt(i) != curr.name.charAt(i)) {
						it.remove();
					}
				}
			}
		}
		//check for blacks
		for(int i = 0; i < seq.length(); i++) {
			if(seq.charAt(i) == 'b') {
				char c = w.name.charAt(i);
				Iterator<Word> it = words.iterator();
				while(it.hasNext()) {
					Word curr = it.next();
					if(curr.name.indexOf(c) != -1 && correct.indexOf(c) == -1) {
						it.remove();
					}
				}
			}
		}
		//check for yellows
		for(int i = 0; i < seq.length(); i++) {
			if(seq.charAt(i) == 'y') {
				Iterator<Word> it = words.iterator();
				while(it.hasNext()) {
					Word curr = it.next();
					if(curr.name.indexOf(w.name.charAt(i)) == -1 || curr.name.charAt(i) == w.name.charAt(i)) {
						it.remove();
					}
				}
			}
		}
		Collections.sort(words);
		return words.size() == 0 ? null : words.get(words.size() - 1);
	}
	
	public static boolean contains(int[] a, int[] b) {
		for(int i = 0; i < 26; i++) {
			if(a[i] < b[i]) {
				return false;
			}
		}
		return true;
	}
	
	public static double getAverage(File f) throws IOException{
		System.out.println("working");
		double result = 0;
		int n = 0;
		
		Scanner sc = new Scanner(f);
		
		while(sc.hasNextLine()) {
			String date = sc.next();
			System.out.println(date);
			int score = sc.nextInt();
			System.out.println(score);
			n++;
			result += score;
			for(int i = 0; i < score; i++) {
				sc.next();
			}
		}
		System.out.println(n);
		sc.close();
		return result/n;
	}
	
	public static void fillScores() {
		scores[0] = 405; //a
		scores[1] = 115;
		scores[2] = 157;
		scores[3] = 181;
		scores[4] = 448; //e
		scores[5] = 79;
		scores[6] = 118;
		scores[7] = 133;
		scores[8] = 281; //i
		scores[9] = 21;
		scores[10] = 102;
		scores[11] = 250;
		scores[12] = 142;
		scores[13] = 214;
		scores[14] = 295; //o
		scores[15] = 146;
		scores[16] = 9;
		scores[17] = 309;
		scores[18] = 461;
		scores[19] = 240;
		scores[20] = 186; //u
		scores[21] = 52;
		scores[22] = 77;
		scores[23] = 24;
		scores[24] = 154; //y
		scores[25] = 25;
	}
	
	public static class Word implements Comparable<Word>{
		private String name;
		private int[] inventory;
		private int val;
		
		public Word(String name) {
			this.name = name;
			inventory = new int[26];
			for(int i = 0; i < name.length(); i++) {
				char c = name.charAt(i);
				int index = (int)c - (int)'a';
				inventory[index]++;
			}
			for(int i = 0; i < 26; i++) {
				if(inventory[i] > 0) {
					val += scores[i];
				}
			}
		}
		
		public int indexOf(char c) {
			// TODO Auto-generated method stub
			return 0;
		}

		public int compareTo(Word other) {
			return this.val - other.val;
		}
		
		public String getName() {
			return name;
		}
		
		public int getVal() {
			return val;
		}
		
		public boolean containsDuplicates() {
			for(int i = 0; i < 26; i++) {
				if(inventory[i] > 1) {
					return false;
				}
			}
			return true;
		}
	}
	
	/*
	 *  FastScanner developed by Matt Fontaine
	 */
	public static class FastScanner{
		   private InputStream stream;                                                                                         
		   private byte[] buf = new byte[1024];                                                                                
		   private int curChar;                                                                                                
		   private int numChars;                                                                                               

		   public FastScanner(InputStream stream)
		   {
		      this.stream = stream;                                                                                            
		   }

		   int read()
		   {
		      if (numChars == -1)
		         throw new InputMismatchException();                                                                           
		      if (curChar >= numChars){
		         curChar = 0;                                                                                                  
		         try{
		            numChars = stream.read(buf);                                                                               
		         } catch (IOException e) {
		            throw new InputMismatchException();                                                                        
		         }
		         if (numChars <= 0)
		            return -1;                                                                                                 
		      }
		      return buf[curChar++];                                                                                           
		   }

		   boolean isSpaceChar(int c)
		   {
		      return c==' '||c=='\n'||c=='\r'||c=='\t'||c==-1;                                                                 
		   }

		   boolean isEndline(int c)
		   {
		      return c=='\n'||c=='\r'||c==-1;                                                                                  
		   }

		   int nextInt()
		   {
		      return Integer.parseInt(next());                                                                                 
		   }

		   long nextLong()
		   {
		      return Long.parseLong(next());                                                                                   
		   }

		   double nextDouble()
		   {
		      return Double.parseDouble(next());                                                                               
		   }

		   String next(){
		      int c = read();                                                                                                  
		      while (isSpaceChar(c))
		         c = read();                                                                                                   
		      StringBuilder res = new StringBuilder();                                                                         
		      do{
		         res.appendCodePoint(c);                                                                                       
		         c = read();                                                                                                   
		      }while(!isSpaceChar(c));                                                                                         
		      return res.toString();                                                                                           
		   }

		   String nextLine(){
		      int c = read();                                                                                                  
		      while (isEndline(c))
		         c = read();                                                                                                   
		      StringBuilder res = new StringBuilder();                                                                         
		      do{
		         res.appendCodePoint(c);                                                                                       
		         c = read();                                                                                                   
		      }while(!isEndline(c));                                                                                           
		      return res.toString();                                                                                           
		   }
	}

}


