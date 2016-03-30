package mmclass;

import generic.XmiReference;
import generic.XmiReferenceInterface;

import java.io.Writer;
import java.util.Vector;

import mmclass.abs.CppTypedElement;
import mmclass.abs.CppTypedElementInterface;
import mmclass.expression.DeclarationExpression;
import mmclass.expression.TypeAccess;

public class VariableDeclarationGroup implements CppTypedElementInterface, XmiReferenceInterface{

	CppTypedElement cppTypedElement = new CppTypedElement();
	XmiReference xr = new XmiReference();
	
	DeclarationExpression container = null;
	Vector<VariableDeclarationFragment> fragments = new Vector<VariableDeclarationFragment>();
	
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
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppVariableDeclarationGroup\"");
		if(this.container != null) fileOut.write(getTab(depth+1)+"container=\"//"+this.container.getReference()+"\"");
		fileOut.write(">");
		this.cppTypedElement.writeToFile("type", depth+1, fileOut);
		for(VariableDeclarationFragment var: this.fragments){
			var.writeToFile("fragments", depth+1, fileOut);
		}
		fileOut.write(getTab(depth)+"</"+container+">");
	}

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
	public String getReference() {
		return this.xr.getReference();
	}
	
	@Override
	public void setReference(String container, int position, XmiReferenceInterface parent) {
		this.xr.setReference(container, position, parent);
	}
	
	public void setContainer(DeclarationExpression container) {
		this.container = container;
	}
	
	public void addFragment(VariableDeclarationFragment vdf){
		this.fragments.addElement(vdf);
		vdf.setReference("fragments", this.fragments.size()-1, this);
		vdf.setVariablesContainer(this);
	}
}
