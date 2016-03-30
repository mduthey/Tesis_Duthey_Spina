package mmclass;

import generic.XmiReference;
import generic.XmiReferenceInterface;

import java.io.Writer;
import java.util.Vector;

import mmclass.abs.CppPathReferentiable;

public class CppPackage extends CppPathReferentiable implements XmiReferenceInterface {
	
	XmiReference xr = new XmiReference();
	
	Vector<CppPathReferentiable> childrenReferences = new Vector<CppPathReferentiable>();
	
	public CppPackage(String name) {
		this.setName(name);
	}
	
	public void addElement(CppPackage elem) {
		this.childrenReferences.add(elem);
		elem.setReference("childrenReferences", this.childrenReferences.size()-1, this);
	}
	
	public void addElement(CppClassFile elem) {
		this.childrenReferences.add(elem);
		elem.setReference("childrenReferences", this.childrenReferences.size()-1, this);
	}

	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppPackage\"");
		fileOut.write(getTab(depth+1)+"name=\""+this.getName()+"\">");
		for(CppPathReferentiable cpr: this.childrenReferences){
			cpr.writeToFile("childrenReferences", depth+1, fileOut);
		}
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
	private String getTab(int depth) {
		String out = "\n";
		for(int i=0; i < depth; i++){
			out += "\t";
		}
		return out;
	}

	@Override
	public void setReference(String container, int position, XmiReferenceInterface parent) {
		this.xr.setReference(container, position, parent);
	}

	@Override
	public String getReference() {
		return this.xr.getReference();
	}

	@Override
	public CppClass getClassByName(String name) {
		CppClass out = null;
		
		for(int i = 0; out == null && i < this.childrenReferences.size(); i++){
			out = this.childrenReferences.get(i).getClassByName(name);
		}
		
		return out;
	}
}