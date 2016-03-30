package mmclass.expression.abs;

import java.io.Writer;
import java.util.Vector;

import mmclass.abs.CppExpression;
import mmclass.abs.CppMemberFunction;

public abstract class AbstractMethodInvocation extends CppExpression {
	
	CppMemberFunction method=null;
	Vector<CppExpression> arguments = new Vector<CppExpression>();
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		if(this.method != null) fileOut.write(getTab(depth)+"method=\"//"+this.method.getReference()+"\"");
		fileOut.write(">");
		
		for(CppExpression exp: this.arguments){
			exp.writeToFile("arguments", depth, fileOut);
		}
	}

	public CppMemberFunction getMethod() {
		return method;
	}

	public void setMethod(CppMemberFunction method) {
		this.method = method;
	}

	public void addArgument(CppExpression argument) {
		this.arguments.addElement(argument);
		argument.setReference("arguments", this.arguments.size()-1, this);
	}
	
	

}
