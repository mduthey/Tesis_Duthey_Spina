package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;
import java.util.Vector;

import mmclass.abs.CppExpression;

public class CppBlock extends CppExpression {
	
	boolean usedNull = false;
	
	Vector<CppExpression> statements = new Vector<CppExpression>();
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppBlock\">");
		for(CppExpression exp: this.statements){
			exp.writeToFile("statements", depth+1, fileOut);
		}
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
	public void addExpression(CppExpression expression){
		this.statements.addElement(expression);
		expression.setReference("statements", this.statements.size()-1, this);
	}

	public void setNull(boolean val){
		this.usedNull = val;
	}
	
	public boolean isNull(){
		return this.usedNull;
	}
}
