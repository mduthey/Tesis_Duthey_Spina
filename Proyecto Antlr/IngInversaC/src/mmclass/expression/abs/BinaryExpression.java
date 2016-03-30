package mmclass.expression.abs;

import java.io.Writer;

import mmclass.abs.CppExpression;

public abstract class BinaryExpression extends CppExpression {
	//property leftOperand : CppExpression { composes };
	//property rightOperand : CppExpression { composes };
	
	CppExpression leftOperand = null;
	CppExpression rightOperand = null;
	
	public void setLeftOperand(CppExpression leftOperand) {
		this.leftOperand = leftOperand;
	}
	public CppExpression getLeftOperand() {
		return leftOperand;
	}
	
	public void setRightOperand(CppExpression rightOperand) {
		this.rightOperand = rightOperand;
		this.rightOperand.setReference("rightOperand", -1, this);
	}
	public CppExpression getRightOperand() {
		return rightOperand;
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		if(this.leftOperand != null)
			this.leftOperand.writeToFile("leftOperand", depth+1, fileOut);
		if(this.rightOperand != null)
			this.rightOperand.writeToFile("rightOperand", depth+1, fileOut);
	}
}
