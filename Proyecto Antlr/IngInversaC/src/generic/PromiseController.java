package generic;

import java.util.HashMap;
import java.util.Vector;

import mmclass.CppClass;
import mmclass.CppClassFile;
import mmclass.CppModel;
import mmclass.CppPackage;
import mmclass.Method;
import mmclass.abs.CppMemberFunction;
import mmclass.abs.CppTypeInterface;
import mmclass.abs.CppTypedElementInterface;
import mmclass.abs.VariableDeclaration;
import mmclass.enums.AccessSpecifier;
import mmclass.enums.LinkageSpecifier;
import mmclass.expression.MethodInvocation;
import mmclass.expression.TypeAccess;
import mmclass.expression.VariableAccess;
import mmclass.primitivetypes.VoidType;
import specialtypes.CPPAutoGen;
import specialtypes.CPPGeneric;
import specialtypes.SpecialTypes;

public class PromiseController {
	
	private static PromiseController instance = null;
	
	private HashMap<String, Vector<VariableAccess>> promiseVars;
	private HashMap<String, Vector<MethodInvocation>> promiseMethods;
	private HashMap<String, Vector<TypeAccess>> promiseTypes;
	private HashMap<String, HashMap<String, Vector<MethodInvocation> > > promiseMethodsOnClasses;
		
	private PromiseController(){
		this.promiseVars = new HashMap<String, Vector<VariableAccess>>();
		this.promiseMethods = new HashMap<String, Vector<MethodInvocation>>();
		this.promiseTypes = new HashMap<String, Vector<TypeAccess>>();
		
		this.promiseMethodsOnClasses = new HashMap<String, HashMap<String, Vector<MethodInvocation> > >();
	}
	
	public static PromiseController getInstance() {
		if(instance == null)
			instance = new PromiseController();
		return instance;
	}
	
	public static void clear(){
		instance = null;
	}
	
	public void addPromise(String name, VariableAccess promise){
		String tmpName = name;//.toLowerCase();
		if(this.promiseVars.containsKey(tmpName)){
			( this.promiseVars.get(tmpName) ).add(promise);
		}else{
			Vector<VariableAccess> tmp = new Vector<VariableAccess>();
			tmp.addElement(promise);
			this.promiseVars.put(tmpName, tmp);
		}
	}
	
	public void addPromise(String name, MethodInvocation promise){
		String tmpName = name;//.toLowerCase();
		if(this.promiseMethods.containsKey(tmpName)){
			( this.promiseMethods.get(tmpName) ).add(promise);
		}else{
			Vector<MethodInvocation> tmp = new Vector<MethodInvocation>();
			tmp.addElement(promise);
			this.promiseMethods.put(tmpName, tmp);
		}
	}
	
	public void addPromise(String className, String methodName, MethodInvocation promise){
		if(this.promiseMethodsOnClasses.containsKey(className) ){
			HashMap<String, Vector<MethodInvocation>> tmpMap = this.promiseMethodsOnClasses.get(className);
			if( tmpMap.containsKey(methodName) ){
				( tmpMap.get(methodName) ).add(promise);
			}else{
				Vector<MethodInvocation> vtmp = new Vector<MethodInvocation>();
				vtmp.addElement(promise);
				tmpMap.put(methodName, vtmp);
			}
		}else{
			HashMap<String, Vector<MethodInvocation>> htmp = new HashMap<String, Vector<MethodInvocation>>();
			Vector<MethodInvocation> vtmp = new Vector<MethodInvocation>();
			vtmp.addElement(promise);
			htmp.put(methodName, vtmp);
			this.promiseMethodsOnClasses.put(className, htmp);
		}
	}
	
	public void addPromise(String name, TypeAccess promise){
		String tmpName = name;//.toLowerCase();
		if(this.promiseTypes.containsKey(tmpName)){
			( this.promiseTypes.get(tmpName) ).add(promise);
		}else{
			Vector<TypeAccess> tmp = new Vector<TypeAccess>();
			tmp.addElement(promise);
			this.promiseTypes.put(tmpName, tmp);
		}
	}
	
	public void updatePromises(CppMemberFunction method) {
		String name = method.getName();//.toLowerCase();
		if(this.promiseMethods.containsKey(name)){
			Vector<MethodInvocation> tmp = this.promiseMethods.remove(name);
			for(MethodInvocation m: tmp){
				m.setMethod(method);
			}
		}
	}
	
	public void updatePromises(CppClass ownClass, CppMemberFunction method){
		String className = ownClass.getName();
		String methodName = method.getName();
		if(this.promiseMethodsOnClasses.containsKey(className) ){
			HashMap<String, Vector<MethodInvocation>> tmpMap = this.promiseMethodsOnClasses.get(className);
			if( tmpMap.containsKey(methodName) ){
				Vector<MethodInvocation> tmp = tmpMap.remove(methodName);
				for(MethodInvocation m: tmp){
					m.setMethod(method);
				}
			}
			if( tmpMap.size() == 0 ){
				this.promiseMethodsOnClasses.remove(className);
			}
		}
	}

	public void updatePromises(VariableDeclaration variable){
		String name = variable.getName();//.toLowerCase();
		if(this.promiseVars.containsKey(name)){
			Vector<VariableAccess> tmp = this.promiseVars.remove(name);
			for(VariableAccess m: tmp){
				m.setVariable(variable);
			}
		}
	}
	
	public void updatePromises(String name, CppTypeInterface type){
		String tmpName = name;//.toLowerCase();
		if(this.promiseTypes.containsKey(tmpName)){
			Vector<TypeAccess> tmp = this.promiseTypes.remove(tmpName);
			for(TypeAccess ta: tmp){
				ta.setAccess(type);
			}
		}
	}
	
	public boolean existsPromises(){
		return (this.promiseMethods.size() > 0 || this.promiseVars.size() > 0 || this.promiseTypes.size() > 0 || this.promiseMethodsOnClasses.size() > 0);
	}
	
	public void completePromises(CppModel rootModel){
		if(this.existsPromises()){
			this.addNewClass(CPPAutoGen.getInstance(rootModel), rootModel);
		}
		String tmpName;
		while(this.promiseTypes.size() > 0 ){
			tmpName = (String) this.promiseTypes.keySet().toArray()[0];
			//tmp = Character.toUpperCase(tmp.charAt(0))+tmp.substring(1);
			this.addNewClass(CPPGeneric.getInstance( tmpName ), rootModel);
		}
		
		//private HashMap<String, HashMap<String, Vector<MethodInvocation> > > promiseMethodsOnClasses;
		
		while(this.promiseMethodsOnClasses.size() > 0){
			tmpName = (String) this.promiseMethodsOnClasses.keySet().toArray()[0];
			CppClass tmpClass = rootModel.getClassByName(tmpName);
			if(tmpClass != null){
				HashMap<String, Vector<MethodInvocation> > methodsHash = this.promiseMethodsOnClasses.get(tmpName);
				while( methodsHash.size() > 0 ){
					String methodName = (String) methodsHash.keySet().toArray()[0];
					Method currentMethod = this.generateVoidMethod(methodName, rootModel);
					
					tmpClass.addField(currentMethod);
					this.updatePromises( tmpClass, currentMethod );
					this.updatePromises( currentMethod );
					TypesController.getInstance().addMethod(tmpClass.getParent(), tmpClass, currentMethod);
				}
			}
		}
		
		
		while(this.promiseMethods.size() > 0){
			tmpName = (String) this.promiseMethods.keySet().toArray()[0];
			CPPAutoGen.getInstance(rootModel).generateMethod(tmpName);
		}
		while(this.promiseVars.size() > 0 ){
			tmpName = (String) this.promiseVars.keySet().toArray()[0];
			CPPAutoGen.getInstance(rootModel).generateVariable(tmpName);
		}
	}
	
	private void addNewClass(SpecialTypes type, CppModel rootModel){
		CppPackage pkg;
		if(!rootModel.existPackage("system")){
			pkg = new CppPackage("system");
			rootModel.addElement(pkg);
		}else{
			pkg = rootModel.getPackage("system");
		}
		CppClassFile classFile = type.getClassFile();
		pkg.addElement(classFile);
		CppClass genericClass = type.getCClass();
		classFile.addElement(genericClass);
		this.updatePromises(genericClass.getName(), genericClass);
	}
	
	public void removePromise(String name, VariableAccess va){
		if(this.promiseVars.containsKey(name)){
			Vector<VariableAccess> tmp = this.promiseVars.get(name);
			tmp.remove(va);
		}
	}
	
	private Method generateVoidMethod(String name, CppModel rootModel){
		Method currentMethod = new Method(null);
		currentMethod.setVirtual(false);
		currentMethod.setInline(false);
		TypeAccess ta = new TypeAccess();
		
		CppTypeInterface type = VoidType.getInstance();
		
		ta.setTypeName("void");

		ta.setAccess(type);
		currentMethod.setType(ta);
		rootModel.addElement(type);
		
		currentMethod.setName(name);
		
		currentMethod.setAccessSpecifier(AccessSpecifier.PUBLIC);
		
		return currentMethod;
	}
}
