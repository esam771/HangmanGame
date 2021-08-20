package hangman;

import java.util.Random;
import java.util.Scanner;


public class HangmanGame {

	//just used common phrases as an example
	
	public String[] answers = {"back to the drawing board", "so far so good", "speak of the devil", 
			"you can say that again", "make a long story short", "get your act together", 
			"to make matters worse", "under the weather", "time flies when you're having fun", "call it a day"};
	
	static int totalterms = 10; //change if amount of terms changes
	
	public String[] ansarr = new String[200];
	public String[] blankarr = new String[200];
	public String[] guessed = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 
								"", "", "", "", "", "", "", "", "", "","", "", "", "", "", ""}; //not null because .compareTo
	
	public int guessedindex = 1; //starting a 1, not 0, to compare a value to whats before it
	
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
		
		answerArray(choice); //populates arrays with answer string
		blankArray(choice);
		
		
		System.out.println("\n#" + (choice + 1) + " chosen"); //tells you which term chosen
		printCurrent(choice); //shows blanks of chosen term
		
		Scanner scan = new Scanner(System.in); //scanner that accepts guesses and puts into 'String guess'
		String guess = "";
		

		while(errors < 11 && !solved ) //loops until ends
		{
			System.out.println("\nEnter Full Guess or Single Letter");
			System.out.println("Already guessed: ");
			
			for(int i = 1; i < guessed.length; i++) //printing out past guesses, doesn't look at unused index 0
			{
				if(guessed[i] == "") //after last filled index reached
					break;
				else if(guessed[i].compareTo("") != 0) //to print previous guesses
					System.out.print(guessed[i] + ", ");
				
				if((i + 1) % 7 == 0) //formatting a new line if many guesses
					System.out.println();
			}
			
			System.out.println("\n*"+"\n*"+"\n*"+"\n*"); 
			
			guess = scan.nextLine(); //takes next letter or full guess
			System.out.println();
			
			if(correctGuess(guess, choice) == 1) //if single letter correct
			{
				addGuess(guess);
				solved = isSolved();
			}
			else if(correctGuess(guess, choice) == 99) //if puzzle is fully solved
			{
				solved = true; //if skipping letters
			}
			else //incorrect guess
			{
				errors++;
				System.out.print("Wrong Guess, Guesses Left: " + (11-errors) + "\n\n");
				addGuess(guess);
				solved = isSolved();
			}
			
			if(!solved) //prints progress if not solved
				printCurrent(choice); //printing after guess is checked
		}
		
		scan.close(); //no more need to accept guesses
		
		if(solved){ //print for correct solution
			System.out.println("*************************************");
			System.out.println("**********==================*********");
			System.out.println("*********|| Puzzle Solved ||*********");
			System.out.println("**********==================*********");
			System.out.println("*************************************");
		} else { //prints for puzzle failure
			System.out.println("\n\n\n11 Errors Made, Puzzle Failed");
			System.out.println("Correct Answer: \n\"" + answers[choice] + "\"");
		}
	}
	

	
	public void answerArray(int choice)
	{
		//used to put chosen words into globalsolved array, required for blankArray()
		ansarr = answers[choice].split("");
	}	
	
	public void blankArray(int choice)
	{
			//returning blank array from answer array, needs answerArray() to be used first
			for(int i = 0; i < ansarr.length; i++)
			{
				if(ansarr[i].compareTo(" ") == 0)
					blankarr[i] = " ";
				else if(ansarr[i].compareTo("'") == 0)
					blankarr[i] = "'";
				else
					blankarr[i] = "_";
			}
		}
	
	public void printCurrent(int choice)
	{
		//prints changing and originally unsolved array
		
		System.out.println("\n"); //formatting to print progress on new line
		
		for(int i = 0; i < blankarr.length; i++)
		{
			if(blankarr[i] != null)
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
			if(guess.compareTo(ansarr[i]) == 0) //when guess is correct at index of i
			{
				blankarr[i] = guess; //changes blankarr to include correct guess
			}
			else
			{
				if(guess.compareTo(guessed[guessedindex-1]) != 0) //adding incorrect guesses
				{
					guessed[guessedindex] = guess;
					guessedindex++;
				}
			}
		}
	}
	
	public boolean isSolved()
	{
		//checks if unsolved array has been fully changed
		for(int i = 0; i < blankarr.length; i++)
			{
			if(blankarr[i] == null) //breaks out of checking null indexes
				break;
			
			if(blankarr[i].compareTo(ansarr[i]) != 0)
				return false; //if a difference is found before nulls, not solved
			}
		return true; //if not stopped before here, then puzzle is solved
	}
}
