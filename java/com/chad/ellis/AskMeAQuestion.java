package com.chad.ellis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Set up a list of Questions, providing the "right" answer in the Question constructor, and
 * the String to output for the "right" answer and the "wrong" answer.
 * 
 * @author Chad Ellis (ellistrator@yahoo.com)
 * @version 1.0-102512
 */
public class AskMeAQuestion 
{
	// Our list of questions of various types. Well, the required input type varies...
	private List<Question<?>> questions = new ArrayList<Question<?>>();
	// Just one of these per use, save some time
	private BufferedReader in;
	
	/**
	 * Set it all up, create your questions too..
	 */
	public AskMeAQuestion()
	{
		InputStreamReader converter = new InputStreamReader(System.in);
		in = new BufferedReader(converter);
		
		questions.add(new Question<String>("What is your favorite color?", String.class, "yellow", 
				"You may pass...", "WRONG!! Into the pit of death with you!!!"));
		questions.add(new IntegerQuestion("How many fingers am I holding up?", 3, 
				"Obviously a wise person", "DRUNK AGAIN????"));
		questions.add(new BooleanQuestion("Are you happy?", true, 
				"Remain ignorant!", "Here, have a lollipop :)"));
	}
	
	/**
	 * Run the code, provide the list of questions.
	 */
	public void questionPrompt()
	{
		System.out.println("Here are your questions, pick one");
		System.out.println("===================");
		for (int i = 0; i < questions.size(); i++)
		{
			System.out.println(i+1 + ". " + questions.get(i).getQuestion());
		}

		int choice = 0;
		while ( choice < 1 || choice > questions.size() )
		{
			try {
				System.out.print("\nPick one ===> ");
				choice = Integer.parseInt(in.readLine());
				System.out.println("Your choice is: " + choice);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		actOnChoice(questions.get(choice - 1));
	}
	
	/**
	 * A valid question has been selected, ask for an answer of the right type, and output
	 *  the result.
	 * @param question the selected question
	 */
	private void actOnChoice(Question<?> question)
	{
		System.out.print("Enter your answer as a/an " + question.getAnswerType().getSimpleName() + ": ");
		
		String input = "";
		
		// Loop and read from standard input, until an answer of the right type is provided
		boolean done = false;
		while (!done)
		{
			try {
				input = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			question.setAnswer(input);
			
			// WRONG! A wrong type == null answer, try again
			if ( question.getAnswer() == null )
			{
				System.out.print("Invalid answer you DIMWIT!, try again: ");
			}
			else
			{
				done = true;
			}
		}
		
		// All done, output the result
		System.out.println("\n" + question.getAnswerResponse() +"\n");
	}
	
	/**
	 * Internal class using Java Generics. The type provided here is the answer type.
	 *
	 * @param <T> the expected answer type
	 */
	private class Question<T> 
	{
		// What is the question to present?
		private String question;
		// What is the expected answer type? This is used in the prompt.
		private final Class<T> answerType;
		// What answer was provided?
		protected T answer;
		// What is the "good" answer?
		private T goodAnswer;
		// String to display on "good" answer
		private String goodAnswerResponse;
		// String to display on "bad" answer... heh, my choice!
		private String badAnswerResponse;
		
		/**
		 * Construct a question.
		 * 
		 * @param question The String to prompt with
		 * @param answerType What answer type is expected?
		 * @param goodAnswer What is the "good" answer?
		 * @param goodAnswerResponse The String to display when the good answer is given 
		 * @param badAnswerResponse The String to display when the bad answer is given
		 */
		public Question(String question, Class<T> answerType, T goodAnswer, String goodAnswerResponse, String badAnswerResponse)
		{
			this.question = question;
			this.answerType = answerType;
			this.goodAnswer = goodAnswer;
			this.goodAnswerResponse = goodAnswerResponse;
			this.badAnswerResponse = badAnswerResponse;
		}
		
		public String getQuestion()
		{
			return question;
		}
		
		/**
		 * I found a good quote on stackoverflow.com today ...
		 *  "Casting doesn't convert, it merely disambiguates"
		 *  
		 * @param answer
		 */
		public void setAnswer(Object answer)
		{
			try 
			{
				// The cast here doesn't convert the answer to the expected type. It however will
				// throw an exception if the types are incompatible. For example, String to Integer or
				// Boolean are fine. But Integer to String will throw. This is why we need specialized
				// versions like IntegerQuestion and BooleanQuestion.
				this.answer = answerType.cast(answer);
			}
			catch(ClassCastException e)
			{
				// Bogus input!!
				this.answer = null;
			}
		}
		
		public Class<T> getAnswerType()
		{
			return this.answerType;
		}
		
		public T getAnswer()
		{
			return this.answer;
		}
		
		public String getAnswerResponse()
		{
			// Provide the correct response, comparing the expected answer against the given one
			if ( this.goodAnswer.equals(this.answer) )
				return this.goodAnswerResponse;
			else
				return this.badAnswerResponse;
		}
	}
	
	/**
	 * A specialized Question type. It's only real job is in the setAnswer method,
	 * where we specifically try to convert the given Object to an Integer. Here we
	 * will be able to specifically detect a conversion error (versus the simple
	 * casting in the base).
	 */
	private class IntegerQuestion extends Question<Integer>
	{
		public IntegerQuestion(String question, 
				Integer goodAnswer, String goodAnswerResponse,
				String badAnswerResponse) {
			super(question, Integer.class, goodAnswer, goodAnswerResponse, badAnswerResponse);
		}

		public void setAnswer(Object answer)
		{
			try
			{
				this.answer = Integer.parseInt((String) answer);
			}
			catch(NumberFormatException e)
			{
				// Reset!!
				this.answer = null;
			}
		}
	}
	
	/**
	 * A specialized Question type. It's only real job is in the setAnswer method,
	 * where we specifically try to convert the given Object to a Boolean. Here we
	 * will be able to specifically detect a conversion error (versus the simple
	 * casting in the base).
	 */
	private class BooleanQuestion extends Question<Boolean>
	{
		public BooleanQuestion(String question, 
				Boolean goodAnswer, String goodAnswerResponse,
				String badAnswerResponse) {
			super(question, Boolean.class, goodAnswer, goodAnswerResponse, badAnswerResponse);
		}

		public void setAnswer(Object answer)
		{
			String answerString = (String) answer;
			if (answerString.equalsIgnoreCase("true") || answerString.equalsIgnoreCase("false")) 
			{
				this.answer = Boolean.parseBoolean((String) answer);
			}
			else
			{
				this.answer = null;
			}
		}
	}	
	
	/**
	 * Run the dern thing!!
	 * @param args
	 */
	public static void main(String [] args)
	{
		AskMeAQuestion chadjava = new AskMeAQuestion();
		while(true)
			chadjava.questionPrompt();
	}
}

