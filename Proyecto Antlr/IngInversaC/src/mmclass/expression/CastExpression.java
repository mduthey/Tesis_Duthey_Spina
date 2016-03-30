package mmclass.expression;
import mmclass.CppModel;

import java.io.Writer;

import generic.XmiReferenceInterface;
import mmclass.abs.CppExpression;

public class CastExpression extends CppExpression {

	TypeAccess typeAccess=null; // type
	CppExpression expression=null;
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppCastExpression\"");
		fileOut.write(">");
		if(this.typeAccess != null) this.typeAccess.writeToFile("type", depth+1, fileOut);
		if(this.expression != null) this.expression.writeToFile("expression", depth+1, fileOut);
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
	public void setTypeAccess(TypeAccess typeAccess) {
		this.typeAccess = typeAccess;
		//this.typeAccess.setReference("type", -1, this);
	}
	
	public void setExpression(CppExpression expression) {
		this.expression = expression;
		//this.expression.setReference("expression", -1, this);
	}
	
	@Override
	public void setReference(String container, int position, XmiReferenceInterface parent) {
		super.setReference(container, position, parent);
		if( this.typeAccess != null )
			this.typeAccess.setReference("type", -1, this);
	}

}
