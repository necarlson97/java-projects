package language.arith;

import language.BinaryOperator;
import language.Operand;
import language.Operator;

/**
 * The {@code NegateOperator} is an operator that performs negation on a single integer
 * @author jcollard
 *
 */
public class NegateOperator extends UnaryOperator<Integer> {

	// TODO Have you taken a look at the PlusOperator yet?
	// You will notice that it extends the abstract class BinaryOperator.
	// You should take a moment and review those classes and how they
	// relate to before trying to
	// implement this one. Although it is not required,
	// it might be a good idea to first write
	// an abstract class called UnaryOperator, paralleling 
	// BinaryOperator, that abstracts out all the bits common
	// across UnaryOperators.	
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Operand<Integer> performOperation() {
		if(op == null)
			throw new IllegalStateException("Could not perform operation prior to operands being set.");
		Integer result = op.getValue() * -1;
		return new Operand<Integer>(result);
	}

	public String toString() {
		return "!";
	}


}
