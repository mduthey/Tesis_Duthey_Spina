package specialtypes;

import mmclass.CppClass;
import mmclass.CppClassFile;
import mmclass.enums.ClassKey;

public class CPPGeneric extends SpecialTypes {

	private static CPPGeneric instance=null;

	private String name = null;
	private ClassKey classKey = null;
	
	private void reset(String name, ClassKey classKey){
		if( this.name == null || !this.name.equalsIgnoreCase(name) ){
			this.name = name;
			this.classKey = classKey;
			this.ccf = null;
			this.cc = null;
		}
	}
	
	private CPPGeneric(String name, ClassKey classKey){
		this.reset(name, classKey);
	}
	
	private static void initInstance(String name, ClassKey classKey){
		if(instance == null)
			instance = new CPPGeneric(name, classKey);
		else
			instance.reset(name, classKey);
	}
	
	public static CPPGeneric getInstance(String name, ClassKey classKey){
		initInstance(name, classKey);
		return instance;
	}
	
	public static CPPGeneric getInstance(String name){
		return getInstance(name, ClassKey.CLASS);
	}

	@Override
	protected void generateClassFile() {
		this.ccf = new CppClassFile(this.name);
	}

	@Override
	protected void generateClass() {
		this.cc = new CppClass(this.classKey, this.name);
		this.cc.setParent(this.ccf);
	}	

}
