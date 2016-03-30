package mmclass.expression;

import java.io.Writer;
import java.util.Vector;

import mmclass.abs.CppExpression;
import mmclass.abs.CppTypeInterface;

public class TypeAccess extends CppExpression{	
	CppTypeInterface access = null;
	Vector<TypeAccess> parameterMapping = new Vector<TypeAccess>(); 
	
	String typeName = null;
			
	public void setAccess(CppTypeInterface access) {
		this.access = access;
		if(this.access != null)
			this.access.addRefTypeAccess(this);
	}
	
	public CppTypeInterface getAccess() {
		return this.access;
	}
	
	public void addParameter(TypeAccess parameter){
		this.parameterMapping.addElement(parameter);
	}
	
	public Vector<TypeAccess> getParameterMapping() {
		return this.parameterMapping;
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		if(this.access != null) fileOut.write(getTab(depth+1)+"access=\"//"+this.access.getReference()+"\"");
		fileOut.write(">");
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public String getTypeName() {
		return typeName;
	}
}
