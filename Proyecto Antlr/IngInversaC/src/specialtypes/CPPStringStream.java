package specialtypes;

import generic.TypesController;
import mmclass.CppClass;
import mmclass.CppClassFile;
import mmclass.CppModel;
import mmclass.Method;
import mmclass.abs.CppTypeInterface;
import mmclass.enums.AccessSpecifier;
import mmclass.enums.ClassKey;
import mmclass.expression.TypeAccess;
import mmclass.primitivetypes.VoidType;

public class CPPStringStream extends SpecialTypes {
	
	private static CPPStringStream instance=null;
	private String lastType;
	
	private CPPStringStream(CppModel model, String lastType){
		this.model = model;
		this.lastType = lastType;
	}
	
	public static CPPStringStream getInstance(CppModel model, String lastType){
		if(instance == null)
			instance = new CPPStringStream(model, lastType);
		return instance;
	}

	@Override
	protected void generateClassFile() {
		this.ccf = new CppClassFile(this.lastType);
	}

	@Override
	protected void generateClass() {
		this.cc = new CppClass(ClassKey.CLASS, this.lastType);
		this.cc.setParent(this.ccf);
		this.addMethod("str", VoidType.getInstance(), AccessSpecifier.PUBLIC);
	}
}
