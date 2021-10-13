package language.arith;

import language.Operand;
import language.Operator;

public abstract class UnaryOperator<T> implements Operator<T> {

	protected Operand<T> op;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int getNumberOfArguments() {
		return 1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setOperand(int i, Operand<T> operand) {
		if(operand == null)
			throw new NullPointerException("Could not set null operand.");
		if(i > 0)
			throw new IllegalArgumentException("Binary operator only accepts one opperand (at 0) but recieved " + i + ".");
		else{
			if(op != null)
				throw new IllegalStateException("The only position " + i + " has been previously set.");
			op = operand;
		} 
	}
}