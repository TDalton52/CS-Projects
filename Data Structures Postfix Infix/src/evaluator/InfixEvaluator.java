package evaluator;

import parser.ArithParser;
import stack.LinkedStack;

public class InfixEvaluator extends Evaluator 
{
	
	private LinkedStack<String> operators = new LinkedStack<String>();
	private LinkedStack<Integer> operands  = new LinkedStack<Integer>();
	
	/** return stack object (for testing purpose) */
	public LinkedStack<String> getOperatorStack() { return operators; }
	public LinkedStack<Integer> getOperandStack()  { return operands;  }
	
	
	/** This method performs one step of evaluation of a infix expression.
	 *  The input is a token. Follow the infix evaluation algorithm
	 *  to implement this method. If the expression is invalid, throw an
	 *  exception with the corresponding exception message.
	 */	
	public void evaluate_step(String token) throws Exception
	{
		if(isOperand(token)) 
		{
			int num = Integer.parseInt(token);
			operands.push(num);
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
			if(token.equals("(") || operators.isEmpty() || 
					precedence(token) > precedence(operators.top()))
			{
				operators.push(token);
			}
			else if(token.equals(")"))
			{
				while(!operators.top().equals("("))
				{
					process_operator(operators.pop());
					if(operators.isEmpty())
					{
						throw new Exception("missing (");
					}
				}
				if(operators.top().equals("("))
				{
					operators.pop();	
				}
			}
			else
			{
				while(!operators.isEmpty() &&
						precedence(operators.top()) >= precedence(token))
				{
					process_operator(operators.pop());
				}
				operators.push(token);
			}
		}	
	}
	
	private void process_operator(String token) throws Exception
	{
		Integer num1 = operands.pop();
		Integer num2 = 0;
		if(!token.equals("!"))
		{
			num2 = operands.pop();
		}
		if (num1 == null || num2 == null)
		{
			throw new Exception("too few operands");
		}
		if(token.equals("+"))
		{
			operands.push(num2 + num1);
		}
		if(token.equals("-"))
		{
			operands.push(num2 - num1);
		}
		if(token.equals("*"))
		{
			operands.push(num2 * num1);
		}
		if(token.equals("/"))
		{
			if(num1 == 0)
			{
				throw new Exception("division by zero");
			}
			operands.push(num2 / num1);
		}
		if(token.equals("!"))
		{
			operands.push(num1 * -1);
		}
		
	}
	/** This method evaluates an infix expression (defined by expr)
	 *  and returns the evaluation result. It throws an Exception object
	 *  if the infix expression is invalid.
	 */
	public Integer evaluate(String expr) throws Exception {
		
		for(String token : ArithParser.parse(expr)) 
		{
			evaluate_step(token);
		}
		
		/* TODO: what do we do after all tokens have been processed? */
		
		while(!operators.isEmpty())
		{
			process_operator(operators.pop());
		}
		
		// The operand stack should have exactly one operand after the evaluation is done
		if(operands.size()>1)
			throw new Exception("too many operands");
		else if(operands.size()<1)
			throw new Exception("too few operands");
		
		return operands.pop();
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(new InfixEvaluator().evaluate("5+(5+2*(5+9))/!8"));
	}
}
