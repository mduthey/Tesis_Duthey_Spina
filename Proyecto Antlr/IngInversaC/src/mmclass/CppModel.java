package mmclass;

import generic.Class2XMI;

import java.io.Writer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import mmclass.abs.CppPathReferentiable;
import mmclass.abs.CppTypeInterface;

public class CppModel implements Class2XMI{
	String name = null;
	CppClass mainClass = null;
	Vector<CppPathReferentiable> elements = new Vector<CppPathReferentiable>();
	HashSet<CppTypeInterface> orphanTypes = new HashSet<CppTypeInterface>();
	
	public static String NS_PREFIX = "org.eclipse.emf.ecore.metamodelo_cpp";
	public static String NS_URI = "http://metamodelo_cpp.unicen.edu.ar";
	
	public CppModel(String name){
		this.setName(name);
	}
	
	public void addElement(CppPackage elem){
		this.elements.add(elem);
		elem.setReference("elements", this.elements.size()-1, null);
	}
	
	public boolean existPackage(String name) {
		boolean out = false; 
		for(int i = 0; !out && i < this.elements.size();i++) {
			out = this.elements.get(i).getName().equals(name);
		}
			
		return out;
	}
	
	public CppPackage getPackage(String name) {
		CppPackage out = null; 
		for(int i = 0; out == null && i < this.elements.size();i++) {
			if( this.elements.get(i).getName().equals(name) )
				out = (CppPackage) this.elements.get(i);
		}
		return out;
	}
	
	public void addElement(CppTypeInterface ptype){
		if( !this.orphanTypes.contains(ptype) ){
			this.orphanTypes.add(ptype);
			ptype.setReference("orphanTypes", this.orphanTypes.size()-1, null);	
		}
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setMainClass(CppClass mainClass) {
		this.mainClass = mainClass;
	}
	
	public CppClass getMainClass() {
		return mainClass;
	}
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\r\n");
		fileOut.write("<"+NS_PREFIX+":CppModel\r\n");
		fileOut.write("\txmi:version=\"2.0\"\r\n");
		fileOut.write("\txmlns:xmi=\"http://www.omg.org/XMI\"\r\n");
		fileOut.write("\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n");
		fileOut.write("\txmlns:"+NS_PREFIX+"=\""+NS_URI+"\"\r\n");
		fileOut.write("\txsi:schemaLocation=\""+NS_URI+" cpp.ecore\"\r\n");
		fileOut.write("\tname=\""+this.getName()+"\"");
		if(this.mainClass != null)
			fileOut.write("\r\n\tmainClass=\"//"+this.mainClass.getReference()+"\"");
		fileOut.write(">");
		for(CppPathReferentiable cpr: this.elements){
			cpr.writeToFile("elements", depth+1, fileOut);
		}
		for(CppTypeInterface ctype: this.orphanTypes){
			ctype.writeToFile("orphanTypes", depth+1, fileOut);
		}
		fileOut.write("\n</"+NS_PREFIX+":CppModel>");
	}
	
	public CppClass getClassByName(String name){
		CppClass out = null;
		for(int i=0; out == null && i < this.elements.size(); i++){
			out = (this.elements.get(i)).getClassByName(name);
		}
		if(out == null){
			Iterator<CppTypeInterface> it = this.orphanTypes.iterator();
			while(out == null && it.hasNext()){
				out = it.next().getClassByName(name);
			}
		}
		return out;
	}
}
