package mmclass.abs;

import generic.XmiReferenceInterface;

import java.io.Writer;
import java.util.Vector;

import mmclass.CppClass;
import mmclass.CppModel;
import mmclass.expression.TypeAccess;

public abstract class PrimitiveType implements CppTypeInterface{
	protected String classType = null;
	private int references=0;
	protected CppType type = new CppType();
	
	public void incCounter(){
		this.references++;
	}
	
	public int getCounter(){
		return this.references;
	}
	
	@Override
	public void addRefTypeAccess(TypeAccess ta) {
		this.type.addRefTypeAccess(ta);
	}
	
	@Override
	public Vector<TypeAccess> getRefTypeAccess() {
		return this.type.getRefTypeAccess();
	}
	
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		if(this.getRefTypeAccess().size() > 0) {
			fileOut.write(getTab(depth)+"<"+container);
			fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":"+this.classType+"\"");
			fileOut.write(getTab(depth+1)+"usagesInTypeAccess=\""+this.type.getRefList()+"\">");
			fileOut.write(getTab(depth)+"</"+container+">");
		}
	}
	
	protected String getTab(int depth) {
		String out = "\n";
		for(int i=0; i < depth; i++){
			out += "\t";
		}
		return out;
	}
	
	@Override
	public String getReference() {
		return this.type.getReference();
	}
	
	@Override
	public void setReference(String container, int position, XmiReferenceInterface parent) {
		this.type.setReference(container, position, parent);
	}	
	
	@Override
	public CppClass getClassByName(String name) {
		return null;
	}
	
}
