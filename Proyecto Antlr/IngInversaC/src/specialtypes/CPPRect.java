package specialtypes;

import mmclass.CppClass;
import mmclass.CppClassFile;
import mmclass.CppModel;
import mmclass.enums.AccessSpecifier;
import mmclass.enums.ClassKey;
import mmclass.primitivetypes.LongType;
import mmclass.primitivetypes.VoidType;

public class CPPRect extends SpecialTypes {

	private static CPPRect instance=null;
	private String lastType;

	private CPPRect(CppModel model, String lastType){
		this.model = model;
		this.lastType = lastType;
	}
	
	public static CPPRect getInstance(CppModel model, String lastType){
		if(instance == null)
			instance = new CPPRect(model, lastType);
		return instance;
	}
	
	@Override
	protected void generateClassFile() {
		this.ccf = new CppClassFile(this.lastType);
	}

	@Override
	protected void generateClass() {
		this.cc = new CppClass(ClassKey.STRUCT, this.lastType);
		this.cc.setParent(this.ccf);
		this.addVariable("left", LongType.getInstance(), AccessSpecifier.PUBLIC);
		this.addVariable("top", LongType.getInstance(), AccessSpecifier.PUBLIC);
		this.addVariable("right", LongType.getInstance(), AccessSpecifier.PUBLIC);
		this.addVariable("bottom", LongType.getInstance(), AccessSpecifier.PUBLIC);
	}

}
