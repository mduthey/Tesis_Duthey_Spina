package mmclass.abs;

import generic.XmiReference;
import generic.XmiReferenceInterface;

import java.io.Writer;
import java.util.Vector;

import mmclass.enums.VarType;
import mmclass.expression.VariableAccess;

public abstract class VariableDeclaration extends CppNamedElement implements XmiReferenceInterface{
	//property usageInVariableAccess#variable : VariableAccess[*] { readonly transient volatile !resolve };
	
	CppExpression initializer = null;
	boolean isArray = false;
	VarType vartype = null;
	Vector<CppExpression> dimensions = new Vector<CppExpression>();
	Vector<VariableAccess> usageInVariableAccess = new Vector<VariableAccess>();
	
	XmiReference xr = new XmiReference();
	
	@Override
	public String getReference() {
		return this.xr.getReference();
	}
	
	@Override
	public void setReference(String container, int position, XmiReferenceInterface parent) {
		this.xr.setReference(container, position, parent);
	}
	
	public void setInitializer(CppExpression initializer) {
		this.initializer = initializer;
		this.initializer.setReference("initializer", -1, this);
	}
	
	public CppExpression getInitializer() {
		return initializer;
	}
	
	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}
	
	public boolean isArray(){
		return this.isArray;
	}
	
	public void setVartype(VarType vartype) {
		this.vartype = vartype;
	}
	
	public VarType getVartype() {
		return vartype;
	}
	
	public void addDimension(CppExpression cppExpression){
		this.dimensions.addElement(cppExpression);
		cppExpression.setReference("dimensions", this.dimensions.size()-1, this);
	}
	
	public void addVariableAccess(VariableAccess va){
		this.usageInVariableAccess.addElement(va);
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		if(this.isArray() == true){
			fileOut.write(getTab(depth)+"isArray=\""+this.isArray()+"\"");
		}
		if(this.vartype != null){
			fileOut.write(getTab(depth)+"vartype=\""+this.vartype.toString()+"\"");
		}
		fileOut.write(getTab(depth)+"name=\""+this.getName()+"\">");
		if(this.initializer != null){
			this.initializer.writeToFile("initializer", depth+1, fileOut);
		}
		for(CppExpression exp: this.dimensions){
			exp.writeToFile("dimensions", depth+1, fileOut);
		}
	}
	
	protected String getTab(int depth) {
		String out = "\n";
		for(int i=0; i < depth; i++){
			out += "\t";
		}
		return out;
	}
	
	String original = null;
	public String getOriginal() {
		return original;
	}
	
	public void setOriginal(String original) {
		this.original = original;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof String){
			String tmp = (String) obj;
			return (this.getOriginal() == null) ? true : this.getOriginal().equals(tmp);
		}else{
			return super.equals(obj);
		}
	}
	
	public Vector<CppExpression> getDimensions() {
		return dimensions;
	}
	
	public void setDimensions(Vector<CppExpression> dimensions) {
		this.dimensions = dimensions;
	}
}
