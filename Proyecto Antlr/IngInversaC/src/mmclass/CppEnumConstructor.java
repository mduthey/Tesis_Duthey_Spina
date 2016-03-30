package mmclass;

import java.io.Writer;

import generic.XmiReference;
import generic.XmiReferenceInterface;
import mmclass.abs.CppExpression;
import mmclass.abs.CppNamedElement;

public class CppEnumConstructor extends CppNamedElement implements XmiReferenceInterface{

	CppExpression expression = null;
	XmiReference xr = new XmiReference();
	
	@Override
	public String getReference() {
		return this.xr.getReference();
	}
	
	@Override
	public void setReference(String container, int position, XmiReferenceInterface parent) {
		this.xr.setReference(container, position, parent);
	}
	
	protected String getTab(int depth) {
		String out = "\n";
		for(int i=0; i < depth; i++){
			out += "\t";
		}
		return out;
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppEnumConstructor\"");
		fileOut.write(getTab(depth+1)+"name=\""+this.getName()+"\"");
		fileOut.write(">\r\n");
		if(this.expression != null) this.expression.writeToFile("expression", depth+1, fileOut);
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
	public void setExpression(CppExpression expression) {
		this.expression = expression;
	}
	
	public CppExpression getExpression() {
		return expression;
	}
}
