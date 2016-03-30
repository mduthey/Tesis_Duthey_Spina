package mmclass;

import generic.XmiReference;
import generic.XmiReferenceInterface;

import java.io.Writer;
import java.util.Vector;

import mmclass.abs.CppPathReferentiable;
import mmclass.abs.CppType; 
import mmclass.abs.CppTypeInterface;

public class CppClassFile extends CppPathReferentiable implements XmiReferenceInterface {
	String pathFile = null;
	XmiReference xr = new XmiReference();
	
	// From MetaModel
	Vector<CppTypeInterface> elements = new Vector<CppTypeInterface>();
	Vector<CppImportDeclaration> imports = new Vector<CppImportDeclaration>();
	
	@Override
	public void setName(String name) {
		String capitalName = Character.toUpperCase(name.charAt(0))+name.substring(1);
		super.setName(capitalName);
	}
	
	public CppClassFile(String name) {
		this.setName(name);
	}
	
	public CppClassFile(String name, String pathFile) {
		this.setName(name);
		this.pathFile = pathFile;
	}
	
	public String getPathFile() {
		return this.pathFile;
	}
	
	public void addElement(CppTypeInterface element){
		this.elements.addElement(element);
		element.setReference("elements", this.elements.size()-1, this);
	}
	
	public boolean exist(String name){
		boolean found=false;
		
		for(int index=0; !found && index < this.elements.size(); index++){
			if(this.elements.get(index) instanceof CppClass){
				CppClass tmp = (CppClass)this.elements.get(index);
				if(tmp.getName().equals(name))
					found=true;
			}
		}
		
		return found;
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppClassFile\"");
		fileOut.write(getTab(depth+1)+"name=\""+this.getName()+"\">");
		//TODO: Agregar imports
		/*for(CppImportDeclaration cpr: this.imports){
			cpr.writeToFile("imports", depth+1);
		}*/
		for(CppTypeInterface cpr: this.elements){
			cpr.writeToFile("elements", depth+1, fileOut);
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

	@Override
	public CppClass getClassByName(String name) {
		CppClass out = null;
		
		for(int i = 0; out == null && i < this.elements.size(); i++){
			out = this.elements.get(i).getClassByName(name);
		}
		
		return out;
	}
}