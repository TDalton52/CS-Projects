package evaluator;

import parser.ArithParser;
import stack.LinkedStack;

public class PostfixEvaluator extends Evaluator 
{
	
	private LinkedStack<Integer> stack = new LinkedStack<Integer>();
	
	/** return stack object (for testing purpose) */
	public LinkedStack<Integer> getStack() { return stack; }
	
	/** This method performs one step of evaluation of a postfix expression.
	 *  The input is a token. Follow the postfix evaluation algorithm
	 *  to implement this method. If the expression is invalid, throw an
	 *  exception with the corresponding exception message.
	 */
	public void evaluate_step(String token) throws Exception 
	{
		if(isOperand(token)) 
		{
			int num = Integer.parseInt(token);
			stack.push(num);
		} 
		else 
		{
			boolean isValid = false;
			String[] validTokens = {"+","-", "*", "/", "!", "(", ")"};
			for(int i = 0; i < validTokens.length; i++)
			{
				if(token.equals(validTokens[i]))
				{
					isValid = true;
				}
			}
			if(!isValid)
			{
				throw new Exception("invalid operator");
			}
			Integer num1 = stack.pop();
			Integer num2 = 0;
			if(!token.equals("!"))
			{
				num2 = stack.pop();
			}
			if (num1 == null || (num2 == null && !token.equals("!")))
			{
				throw new Exception("too few operands");
			}
			if(token.equals("+"))
			{
				stack.push(num2 + num1);
			}
			if(token.equals("-"))
			{
				stack.push(num2 - num1);
			}
			if(token.equals("*"))
			{
				stack.push(num2 * num1);
			}
			if(token.equals("/"))
			{
				if(num1 == 0)
				{
					throw new Exception("division by zero");
				}
				stack.push(num2 / num1);
			}
			if(token.equals("!"))
			{
				stack.push(num1 * -1);
			}
		}		
	}
	/** This method evaluates a postfix expression (defined by expr)
	 *  and returns the evaluation result. It throws an Exception
	 *  if the postfix expression is invalid.
	 */
	public Integer evaluate(String expr) throws Exception {
		
		for(String token : ArithParser.parse(expr)) {
			evaluate_step(token);
		}
		// The stack should have exactly one operand after evaluation is done
		if(stack.size()>1) {
			throw new Exception("too many operands");
		} else if (stack.size()<1) {
			throw new Exception("too few operands");
		}
		return stack.pop(); 
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(new PostfixEvaluator().evaluate("50 25 ! / 3 +"));
	}
}