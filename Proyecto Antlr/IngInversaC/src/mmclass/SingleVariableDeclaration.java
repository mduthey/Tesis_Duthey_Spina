package mmclass;

import java.io.Writer;

import mmclass.abs.CppTypedElement;
import mmclass.abs.CppTypedElementInterface;
import mmclass.abs.CppVariableType;
import mmclass.abs.VariableDeclaration;
import mmclass.expression.CatchClause;
import mmclass.expression.TypeAccess;

public class SingleVariableDeclaration extends VariableDeclaration implements CppTypedElementInterface,CppVariableType {

	CppTypedElement cppTypedElement = new CppTypedElement();
	CatchClause catchClause = null;
	
	@Override
	public void setType(TypeAccess type) {
		this.cppTypedElement.setType(type);
		type.setReference("type", -1, this);
	}

	@Override
	public TypeAccess getType() {
		return this.cppTypedElement.getType();
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppSingleVariableDeclaration\"");
		if(this.catchClause != null) fileOut.write(getTab(depth+1)+"catchClause=\"//"+this.catchClause.getReference()+"\"");
		super.writeToFile(container, depth+1, fileOut); // End with name.
		
		this.cppTypedElement.writeToFile("type", depth+1, fileOut);
		fileOut.write(getTab(depth)+"</"+container+">");
	}

	@Override
	public String getTypeName() {
		return this.getType().getTypeName();
	}

}
