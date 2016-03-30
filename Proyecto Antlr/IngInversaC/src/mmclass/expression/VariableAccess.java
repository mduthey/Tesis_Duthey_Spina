package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

import mmclass.abs.CppExpression;
import mmclass.abs.VariableDeclaration;

public class VariableAccess extends CppExpression {
	// property variable#usageInVariableAccess : VariableDeclaration { !resolve };
	VariableDeclaration variable = null;
	String varName = null;
	
	public void setVariable(VariableDeclaration variable) {
		this.variable = variable;
		this.variable.addVariableAccess(this);
	}
	
	public VariableDeclaration getVariable() {
		return variable;
	}
	
	public String getVarName() {
		return varName;
	}
	
	public void setVarName(String varName) {
		this.varName = varName;
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppVariableAccess\"");
		if(this.variable != null)
			fileOut.write(getTab(depth+1)+"variable=\"//"+this.variable.getReference()+"\">");
		else
			fileOut.write(">");
		fileOut.write(getTab(depth)+"</"+container+">");
	}

}
