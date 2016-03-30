package generic;

import java.util.HashMap;
import java.util.Vector;

import mmclass.CppClass;
import mmclass.CppClassFile;
import mmclass.CppEnum;
import mmclass.CppEnumConstructor;
import mmclass.CppVariable;
import mmclass.abs.CppMemberFunction;
import mmclass.abs.VariableDeclaration;

public class TypesController {

	private static TypesController instance = null;
	
	public static TypesController getInstance(){
		if(instance == null)
			instance = new TypesController();
		return instance;
	}
	
	public static void clear(){
		instance = null;
	}
	
	HashMap<String, Vector<XmiReferenceInterface>> typesMap;
	Vector<String> classFiles;
	Vector<String> classNames;
	Vector<String> classMethods;
	Vector<String> enumNames;
	
	private TypesController(){
		this.typesMap = new HashMap<String, Vector<XmiReferenceInterface>>();
		this.classFiles = new Vector<String>();
		this.classNames = new Vector<String>();
		this.classMethods = new Vector<String>();
		this.enumNames = new Vector<String>();
	}
	
	public void addClassFile(CppClassFile cppClassFile){
		this.addToHashMap(cppClassFile.getName(), cppClassFile);
		this.classFiles.addElement(cppClassFile.getName());
	}
	
	public void addVariable(CppClassFile cppClassFile, CppClass cppClass, CppVariable variable){
		this.addToHashMap( this.nameSearch(cppClassFile.getName(), cppClass.getName(), variable.getName()) , variable);
	}
	
	public void addVariable(CppClassFile cppClassFile, CppVariable variable){ //TypeDef only
		this.addToHashMap( this.nameSearch(cppClassFile.getName(), variable.getName()) , variable);
	}
	
	public void addMethod(CppClassFile cppClassFile, CppClass currentClass, CppMemberFunction currentMethod) {
		String tmp = this.nameSearch(cppClassFile.getName(), currentClass.getName(), currentMethod.getName()+"()");
		this.addToHashMap( tmp , currentMethod);
		this.classMethods.addElement(tmp);
	}
	
	public void addMethodVar(CppClassFile cppClassFile, CppClass currentClass, CppMemberFunction currentMethod, VariableDeclaration variable) {
		String tmp = this.nameSearch(cppClassFile.getName(), currentClass.getName(), currentMethod.getName()+"()", variable.getName());
		this.addToHashMap( tmp , variable);
	}
	
	public void addForVar(CppClassFile cppClassFile, CppClass currentClass, CppMemberFunction currentMethod,
			VariableDeclaration variable) {
		String tmp = this.nameSearch(cppClassFile.getName(), currentClass.getName(), currentMethod.getName()+"()"+"for", variable.getName());
		this.addToHashMap( tmp , variable);
		if(this.classMethods.indexOf(currentMethod.getName()+"()"+"for") < 0){
			this.classMethods.addElement(currentMethod.getName()+"()"+"for");
		}
	}
	
	public void addClass(CppClassFile cppClassFile, CppClass cppClass){
		String tmp = this.nameSearch(cppClassFile.getName(), cppClass.getName());
		this.addToHashMap(tmp, cppClass);
		this.classNames.addElement(tmp);
	}
	
	public void addEnum(CppClassFile cppClassFile, CppEnum cppEnum){
		String tmp = this.nameSearch(cppClassFile.getName(), cppEnum.getName());
		this.addToHashMap(tmp, cppEnum);
		this.enumNames.addElement(tmp);
	}
	
	public void addEnumLiteral(CppClassFile cppClassFile, CppEnum cppEnum, CppEnumConstructor cppEnumConstructor){
		String tmp = this.nameSearch(cppClassFile.getName(), cppEnum.getName(), cppEnumConstructor.getName());
		this.addToHashMap(tmp, cppEnumConstructor);
	}	

	public XmiReferenceInterface findObject(String name, String toCompare){
		Vector<XmiReferenceInterface> found = null;
		
		for(int idx=0; found == null && idx < this.classMethods.size(); idx++){
			found = this.typesMap.get( nameSearch( this.classMethods.get(idx), name ) );
		}
		
		if(found == null)
			for(int idx=0; found == null && idx < this.classNames.size(); idx++){
				found = this.typesMap.get( nameSearch( this.classNames.get(idx), name ) );
			}
		
		if(found == null)
			for(int idx=0; found == null && idx < this.enumNames.size(); idx++){
				found = this.typesMap.get( nameSearch( this.enumNames.get(idx), name ) );
			}
		
		if(found == null)
			for(int idx=0; found == null && idx < this.classFiles.size(); idx++){
				found = this.typesMap.get( nameSearch( this.classFiles.get(idx), name ) );
			}
		
		XmiReferenceInterface out = null;
		
		if(found != null && toCompare != null) {
			for(int k = 0; out == null && k < found.size(); k++)
				if( found.get(k).equals(toCompare) )
					out = found.get(k);
		}else if(found != null){
			out = found.lastElement();
		}
		return out;
	}
	
	public XmiReferenceInterface findClassObject(String classFile, String name){
		Vector<XmiReferenceInterface> found = this.typesMap.get( nameSearch( classFile, name ) );
		return (found == null) ? null : found.lastElement();
	}
	public XmiReferenceInterface findTypeObject(String name){
		Vector<XmiReferenceInterface> found = null;
		
		for(int idx=0; found == null && idx < this.classFiles.size(); idx++){
			found = this.typesMap.get( nameSearch( this.classFiles.get(idx), name ) );
		}
		
		if(found == null)
			for(int idx=0; found == null && idx < this.classNames.size(); idx++){
				found = this.typesMap.get( nameSearch( this.classNames.get(idx), name ) );
			}
		
		if(found == null)
			for(int idx=0; found == null && idx < this.enumNames.size(); idx++){
				found = this.typesMap.get( nameSearch( this.enumNames.get(idx), name ) );
			}

		XmiReferenceInterface out = null;
		
		if(found != null)
			out = found.lastElement();
		
		return out;
	}
	
	public XmiReferenceInterface findObject(String className, String name, String toCompare){
		return this.findObject( nameSearch(className, name), toCompare);
	}
	
	public XmiReferenceInterface findObject(String className, String methodName, String name, String toCompare){
		XmiReferenceInterface out =  this.findObject( nameSearch(className, methodName, name), toCompare );
		if(out == null)
			out = this.findObject( nameSearch(className, methodName+"for", name), toCompare );
		return out;
	}
	
	private String nameSearch(String classFile, String other){
		return classFile+"=>"+other;
		//return classFile.toLowerCase()+"=>"+other.toLowerCase();
	}
	
	private String nameSearch(String classFile, String className, String other){
		return classFile+"=>"+className+"=>"+other;
		//return classFile.toLowerCase()+"=>"+className.toLowerCase()+"=>"+other.toLowerCase();
	}
	
	private String nameSearch(String classFile, String className, String method, String other){
		return classFile+"=>"+className+"=>"+method+"=>"+other;
		//return classFile.toLowerCase()+"=>"+className.toLowerCase()+"=>"+method.toLowerCase()+"=>"+other.toLowerCase();
	}

	
	private void addToHashMap(String name, XmiReferenceInterface reference){
		if(this.typesMap.containsKey(name)){
			( this.typesMap.get(name) ).addElement(reference);
		}else{
			Vector<XmiReferenceInterface> tmp = new Vector<XmiReferenceInterface>();
			tmp.addElement(reference);
			this.typesMap.put(name, tmp);
		}
	}
	
}
