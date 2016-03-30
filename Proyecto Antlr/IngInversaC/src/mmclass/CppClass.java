package mmclass;

import java.io.Writer;
import java.util.Vector;

import mmclass.abs.CppClassifier;
import mmclass.abs.CppFieldInterface;
import mmclass.enums.ClassKey;

public class CppClass extends CppClassifier
{	
	CppClassFile parent = null;
	ClassKey classKey = ClassKey.CLASS;
	boolean isAbstract;
	Vector<CppClass> superClass = new Vector<CppClass>();
	boolean isGeneric;
	boolean isFinal;
	
	public CppClass(ClassKey classKey, String name){
		this.setClassKey(classKey);
		String capitalName = Character.toUpperCase(name.charAt(0))+name.substring(1);
		this.setName(capitalName);
	}
	
	public CppClass(ClassKey classKey){
		this.setClassKey(classKey);
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppClass\"");
		fileOut.write(getTab(depth+1)+"name=\""+this.getName()+"\"");
		if(this.superClass.size() > 0)
			fileOut.write(getTab(depth+1)+"superClass=\""+this.superClass.firstElement().getReference()+"\"");
		fileOut.write(getTab(depth+1)+"isAbstract=\""+this.isAbstract+"\"");
		fileOut.write(getTab(depth+1)+"usagesInTypeAccess=\""+this.getRefList()+"\"");
		fileOut.write(getTab(depth+1)+"classkey=\""+this.classKey.toString()+"\">");
		for(CppFieldInterface cpr: this.getCppFields()){
			cpr.writeToFile("cppFields", depth+1, fileOut);
		}
		fileOut.write(getTab(depth)+"</"+container+">");
	}
	
	public void setClassKey(ClassKey classKey) {
		this.classKey = classKey;
	}
	
	public ClassKey getClassKey() {
		return classKey;
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
		if(this.getName().equals(name))
			return this;
		return null;
	}
	
	public void setParent(CppClassFile parent) {
		this.parent = parent;
	}
	
	public CppClassFile getParent() {
		return parent;
	}
}