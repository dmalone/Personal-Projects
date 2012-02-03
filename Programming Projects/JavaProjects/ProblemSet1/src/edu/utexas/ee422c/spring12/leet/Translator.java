package edu.utexas.ee422c.spring12.leet;

import java.util.Scanner;

public class Translator {
	// EE422C Spring 2012 Problem Set 1
	//
	// author: Thinh Lam
	public static void main(String args[]) {
		//Create a scanner object to take user inputs
		Scanner sc = new Scanner(System.in);
		
		//Prompt user for a string and call translate method to translate
		System.out.printf("Hello there, please type in a word to be translated (Type 'STOP' to terminate the program): ");
		String word = sc.nextLine();
		
		//Repeat prompt until the string 'STOP' is detected
		while (!word.equals("STOP")) {
			translate(word);
			System.out
					.printf("Please type in the next word to be translated: ");
			word = sc.nextLine();
		}
		System.out.println("Terminating program..");
	}

	//translate method that compares a given character to a list of Leet equivalents
	public static String translate(String str) {
		System.out.printf("Translated word: ");
		
		//Compare every character in the entire string for a possible Leet match
		for (int n = 0; n < str.length(); n++) {

			String x = str.substring(n, n + 1);
			//If there is a match, print out the Leet equivalent instead
			//If there is no match, print out the original character
			if (x.equals("A")) {
				System.out.printf("4");
			} else if (x.equals("a")) {
				System.out.printf("@");
			} else if (x.equals("B")) {
				System.out.printf("8");
			} else if (x.equals("C")) {
				System.out.printf("(");
			} else if (x.equals("D")) {
				System.out.printf("|)");
			} else if (x.equals("E")) {
				System.out.printf("3");
			} else if (x.equals("G")) {
				System.out.printf("6");
			} else if (x.equals("g")) {
				System.out.printf("9");
			} else if (x.equals("H")) {
				System.out.printf("#");
			} else if (x.equals("I")) {
				System.out.printf("1");
			} else if (x.equals("i")) {
				System.out.printf("!");
			} else if (x.equals("L")) {
				System.out.printf("1");
			} else if (x.equals("O")) {
				System.out.printf("0");
			} else if (x.equals("R")) {
				System.out.printf("12");
			} else if (x.equals("S")) {
				System.out.printf("5");
			} else if (x.equals("T")) {
				System.out.printf("7");
			} else if (x.equals("t")) {
				System.out.printf("\u2020");
			} else if (x.equals("X")) {
				System.out.printf("\u00D7");
			} else if (x.equals("Z")) {
				System.out.printf("2");
			} else {
				System.out.printf(x);
			}

		}
		//Add some spacing to make things look more orgaized
		System.out.printf("\n\n");
		return str;
	}
}