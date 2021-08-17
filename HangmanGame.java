package hangman;

import java.util.Random;
import java.util.Scanner;


public class HangmanGame {

	//just used common phrases as an example
	
	public String[] answers = {"back to the drawing board", "so far so good", "speak of the devil", 
			"you can say that again", "make a long story short", "get your act together", 
			"to make matters worse", "under the weather", "time flies when you're having fun", "call it a day"};
	
	public String[] blanks =  {"____ __ ___ _______ _____", "__ ___ __ ____", "_____ __ ___ _____", 
			"___ ___ ___ ____ _____", "____ _ ____ _____ _____", "___ ____ ___ ________", 
			"__ ____ _______ _____", "_____ ___ _______", "____ _____ ____ ___'__ ______ ___", "____ __ _ ___"};

	static int totalterms = 10; //change if amount of terms changes
	
	public String[] ansarr = new String[200];
	public String[] blankarr = new String[200];
	public String[] guessed = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 
								"", "", "", "", "", "", "", "", "", "", };
	public int guessedindex = 1;
	
	public static void main(String[] args) {

		System.out.println("Welcome to Hang Man");
		System.out.println("Choose specific phrase: 1-" + totalterms);
		System.out.println("Choose random phrase: 0");
		
		Scanner gamemode = new Scanner(System.in);
		int choice = gamemode.nextInt(); //choosing gamemode

		HangmanGame a = new HangmanGame(); //instance of actual game
		
		if(choice != 0) //if chosen
			a.hangman(choice - 1); //sending index of choice
		else
		{
			//gives random 1-total terms if not chosen
			Random rn = new Random();
			a.hangman(rn.nextInt(totalterms)); //gives index from 0-9
		}
		
		gamemode.close();

	}
	
	public void hangman(int choice)
	{
		int errors = 0;
		boolean solved = false; 
		
		//HangmanUtilities a = new HangmanUtilities();
		
		ansarr = answerarray(choice, false); //populates arrays with answer string
		blankarr = answerarray(choice, true);
		
		
		System.out.println("\n#" + (choice + 1) + " chosen");
		System.out.println(blanks[choice]);
		
		Scanner scan = new Scanner(System.in);
		String guess = "";
		

		while(errors < 11 && !solved )
		{
			System.out.println("\nEnter Guess, Already guessed: ");
			
			for(int i = 0; i < guessed.length; i++) //printing out already guessed nums
				if(guessed[i] != null && guessed[i].compareTo("") != 0)
					System.out.print(guessed[i] + ", ");
			
			System.out.println("\n*"+"\n*"+"\n*"+"\n*");
			
			//System.out.println();
			
			guess = scan.nextLine(); //takes next letter or full guess
			
			if(correctGuess(guess, choice) == 1) //if single letter correct
			{
				addGuess(guess);
				System.out.println();
				solved = isSolved();
			}
			else if(correctGuess(guess, choice) == 99) //if puzzle is fully solved
			{
				solved = true; //if skipping letters
			}
			else //incorrect guess
			{
				errors++;
				System.out.println("Wrong Guess, Guesses Left: " + (11-errors) + "\n");
				addGuess("zzzzzzzzz");
				solved = isSolved();
			}
		}
		
		scan.close();
		
		if(solved) //print for correct solution
		{
			System.out.println("*************************************");
			System.out.println("**********==================*********");
			System.out.println("*********|| Puzzle Solved ||*********");
			System.out.println("**********==================*********");
			System.out.println("*************************************");
		}
		else
			System.out.println("\n\n\n11 Errors Made, Puzzle Failed");
		
	}
	
	
	

	
	public String[] answerarray(int choice, boolean blank)
	{
		//used to put chosen words into unsolved or solved array
		
		String[] arr = null;
		if(blank)
			arr = blanks[choice].split("");
		else
			arr = answers[choice].split("");
		return arr;
	}	
	
	public void printCurrent(int choice)
	{
		//prints changing and originally unsolved array
		
		System.out.println("\n");
		
		for(int i = 0; i < blankarr.length; i++)
		{
			System.out.print(blankarr[i]);
		}
	}
	
	public int correctGuess(String guess, int choice)
	{
		//checks if input is a correct letter or entire
		
		if(guess.compareTo(answers[choice]) == 0)
			return 99;
		
		//checks answer array to see if given correct guess
		for(int i = 0; i < ansarr.length; i++)
			if(guess.compareTo(ansarr[i]) == 0)
				return 1;
		
		return -1;
	}
	
	public void addGuess(String guess)
	{
		//puts correct guess into guessed array and changes blank for progression
		for(int i = 0; i < ansarr.length; i++)
		{
			if(guess.compareTo(ansarr[i]) == 0)
			{
				blankarr[i] = guess;
				System.out.print(blankarr[i]);
				
				if(guess.compareTo(guessed[guessedindex-1]) != 0)
					{
					guessed[guessedindex] = guess;
					guessedindex++;
					}
			}
			else
				System.out.print(blankarr[i]);
		}
	}
	
	public boolean isSolved()
	{
		//checks if unsolved array has been fully changed
		for(int i = 0; i < blankarr.length; i++)
			{
			if(blankarr[i].compareTo("_") == 0)
				return false;
			}
		return true;
	}
}
