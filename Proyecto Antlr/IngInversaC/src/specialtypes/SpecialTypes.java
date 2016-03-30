package specialtypes;

import antlr4.cparser.CParser.StorageClassSpecifierContext;
import generic.PromiseController;
import generic.TypesController;
import mmclass.CppClass;
import mmclass.CppClassFile;
import mmclass.CppModel;
import mmclass.CppVariable;
import mmclass.Method;
import mmclass.abs.CppTypeInterface;
import mmclass.enums.AccessSpecifier;
import mmclass.enums.LinkageSpecifier;
import mmclass.enums.StorageType;
import mmclass.expression.TypeAccess;

public abstract class SpecialTypes {
	
	protected CppClassFile ccf = null;
	protected CppClass cc = null;
	protected CppModel model = null;
	
	public CppClassFile getClassFile(){
		if(this.ccf == null)
			this.generateClassFile();
		return this.ccf;
	}
	
	public CppClass getCClass(){
		if(this.cc == null)
			this.generateClass();
		return this.cc;
	}
	
	protected abstract void generateClass();

	protected abstract void generateClassFile();
	
	protected void addMethod(String name, CppTypeInterface type, AccessSpecifier specifier) {
		this.addMethod(name, type, specifier, null);
	}
	
	protected void addMethod(String name, CppTypeInterface type, AccessSpecifier specifier, LinkageSpecifier linkSpecifier) {
		Method m = new Method(null);
		TypeAccess ta = new TypeAccess();
		
		if(type != null){
			ta.setAccess(type);
			m.setName(name);
			m.setType(ta);
			m.setAccessSpecifier(specifier);
			this.model.addElement(type);
			
			if(linkSpecifier != null){
				m.setLinkage(linkSpecifier);
			}
			
			this.cc.addField(m);
			TypesController.getInstance().addMethod(this.ccf, this.cc, m);
			PromiseController.getInstance().updatePromises( m );
		}
	}
	
	protected void addVariable(String name, CppTypeInterface type, AccessSpecifier specifier){
		this.addVariable(name, type, specifier, null);
	}
	
	protected void addVariable(String name, CppTypeInterface type, AccessSpecifier specifier, StorageType storageType){
		CppVariable tmp = new CppVariable();
		TypeAccess ta = new TypeAccess();
		
		if(type != null){			
			ta.setAccess(type);
			
			tmp.setName(name);
			tmp.setType(ta);
			tmp.setAccessSpecifier(specifier);
			
			if(storageType != null)
				tmp.setStorage(storageType);
			
			this.model.addElement(type);
			this.cc.addField(tmp);
			
			TypesController.getInstance().addVariable(this.ccf, this.cc, tmp);
			PromiseController.getInstance().updatePromises( tmp );
		}
	}
	
}
