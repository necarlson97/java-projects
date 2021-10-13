package evaluator.arith;

import language.Operand;
import language.Operator;
import parser.IllegalPostfixExpressionException;
import parser.PostfixParser.Type;
import parser.Token;
import parser.arith.ArithPostfixParser;
import stack.LinkedStack;
import stack.StackInterface;
import evaluator.PostfixEvaluator;

/**
 * An {@link ArithPostfixEvaluator} is a postfix evaluator over simple arithmetic expressions.
 *
 */
public class ArithPostfixEvaluator implements PostfixEvaluator<Integer> {

	private final StackInterface<Operand<Integer>> stack;
	
	/**
	 * Constructs an {@link ArithPostfixEvaluator}
	 */
	public ArithPostfixEvaluator(){
		this.stack = new LinkedStack(); //TODO initialize your LinkedStack
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer evaluate(String expr) throws IllegalPostfixExpressionException {
		// TODO use all of the things you've built so far to 
		// implement the algorithm for postfix expression evaluation
		
		ArithPostfixParser parser = new ArithPostfixParser(expr);
		for (Token<Integer> token : parser) {
			Type type = token.getType();
			switch(type){ 
			case OPERAND:
				//TODO What do we do when we see an operand?
				stack.push(token.getOperand());
				break;
			case OPERATOR:
				//TODO What do we do when we see an operator?
				
				int opNumber = token.getOperator().getNumberOfArguments();
				for(int i = 0; i < opNumber; i++){ 
					token.getOperator().setOperand(opNumber-i-1, stack.pop());	//runs for every operand required by operator (1 or 2)
				}
				stack.push(token.getOperator().performOperation());
				break;
			default:
				throw new IllegalStateException("Parser returned an invalid Type: " + type);
			}			
		}
		
		if(stack.size() > 1) throw new IllegalPostfixExpressionException();
		
		//TODO What do we return?
		return stack.pop().getValue();
	}

}
