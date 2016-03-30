package specialtypes;

import generic.PromiseController;
import generic.TypesController;
import mmclass.CppClass;
import mmclass.CppClassFile;
import mmclass.CppModel;
import mmclass.CppVariable;
import mmclass.Method;
import mmclass.abs.CppTypeInterface;
import mmclass.enums.AccessSpecifier;
import mmclass.enums.ClassKey;
import mmclass.enums.LinkageSpecifier;
import mmclass.enums.StorageType;
import mmclass.expression.TypeAccess;
import mmclass.primitivetypes.VoidType;

public class CPPAutoGen extends SpecialTypes {
	
	private static CPPAutoGen instance=null;

	private CPPAutoGen(CppModel model){
		this.model = model;
	}
	
	public static CPPAutoGen getInstance(CppModel model){
		if(instance == null)
			instance = new CPPAutoGen(model);
		return instance;
	}
	
	@Override
	protected void generateClassFile() {
		this.ccf = new CppClassFile("CPPAautogen");
	}

	@Override
	protected void generateClass() {
		this.cc = new CppClass(ClassKey.CLASS, "CPPAutoGen");
		this.cc.setParent(this.ccf);
	}
	
	public void generateMethod(String name){
		this.addMethod(name, VoidType.getInstance(), AccessSpecifier.PUBLIC, LinkageSpecifier.STATIC);
		/*Method currentMethod = new Method();

		TypeAccess ta = new TypeAccess();

		CppTypeInterface type = VoidType.getInstance();
		
		ta.setAccess(type);
		currentMethod.setType(ta);
		this.model.addElement(type);
	
		currentMethod.setName(name);
		currentMethod.setAccessSpecifier(AccessSpecifier.PUBLIC);
		currentMethod.setLinkage(LinkageSpecifier.STATIC);
		this.cc.addField(currentMethod);
		PromiseController.getInstance().updatePromises( currentMethod );*/
	}
	
	public void generateVariable(String name){
		this.addVariable(name, VoidType.getInstance(), AccessSpecifier.PUBLIC, StorageType.STATIC);
		/*CppVariable tmp = new CppVariable();
		
		TypeAccess ta = new TypeAccess();
		CppTypeInterface type = VoidType.getInstance();
		
		tmp.setName(name);
		
		this.model.addElement(type);
		ta.setAccess(type);
		tmp.setType(ta);
		tmp.setAccessSpecifier(AccessSpecifier.PUBLIC);
		this.cc.addField(tmp);
		
		PromiseController.getInstance().updatePromises( tmp );*/
	}

}
