package mmclass;

import java.io.Writer;
import java.util.Vector;

import generic.XmiReferenceInterface;
import mmclass.abs.CppField;
import mmclass.abs.CppFieldContainer;
import mmclass.abs.CppFieldInterface;
import mmclass.abs.CppType;
import mmclass.abs.CppTypeInterface;
import mmclass.abs.CppTypedElement;
import mmclass.abs.CppTypedElementInterface;
import mmclass.abs.CppVariableType;
import mmclass.abs.VariableDeclaration;
import mmclass.enums.AccessSpecifier;
import mmclass.enums.StorageType;
import mmclass.expression.TypeAccess;

public class CppVariable extends VariableDeclaration implements CppFieldInterface,CppTypedElementInterface,CppVariableType,CppTypeInterface {
	
	//Agregation
	CppField cppField = new CppField();
	CppTypedElement cppTypedElement = new CppTypedElement();
	CppType cppType = new CppType();
	
	boolean isConst = false;
	StorageType storage = StorageType.AUTO;
	
	@Override
	public void writeToFile(String container, int depth, Writer fileOut) throws Exception {
		fileOut.write(getTab(depth)+"<"+container);
		fileOut.write(getTab(depth+1)+"xsi:type=\""+CppModel.NS_PREFIX+":CppVariable\"");
		this.cppField.writeToFile(container, depth+1, fileOut);
		fileOut.write(getTab(depth+1)+"isConst=\""+this.isConst+"\"");
		fileOut.write(getTab(depth+1)+"storage=\""+this.storage.toString()+"\"");
		fileOut.write(getTab(depth+1)+"usagesInTypeAccess=\""+this.cppType.getRefList()+"\"");
		super.writeToFile(container, depth+1, fileOut); // End with name.
		
		this.cppTypedElement.writeToFile("type", depth+1,fileOut);
		fileOut.write(getTab(depth)+"</"+container+">");
	}
		
	@Override
	public void setContainer(CppFieldContainer fieldContainer) {
		this.cppField.setContainer(fieldContainer);
	}

	@Override
	public CppFieldContainer getContainer() {
		return this.cppField.getContainer();
	}

	@Override
	public void setType(TypeAccess type) {
		this.cppTypedElement.setType(type);
		type.setReference("type", -1, this);
	}

	@Override
	public TypeAccess getType() {
		return this.cppTypedElement.getType();
	}

	@Override
	public void setReference(String container, int position, XmiReferenceInterface parent) {
		this.cppField.setReference(container, position, parent);
	}

	@Override
	public String getReference() {
		return this.cppField.getReference();
	}

	@Override
	public void setAccessSpecifier(AccessSpecifier accessSpecifier) {
		this.cppField.setAccessSpecifier(accessSpecifier);
	}

	@Override
	public AccessSpecifier getAccessSpecifier() {
		return this.cppField.getAccessSpecifier();
	}
	
	public void setStorage(StorageType storage) {
		this.storage = storage;
	}
	
	public StorageType getStorage() {
		return storage;
	}

	@Override
	public String getTypeName() {
		return this.getType().getTypeName();
	}
	
	public boolean isConst() {
		return isConst;
	}
	
	public void setConst(boolean isConst) {
		this.isConst = isConst;
	}

	@Override
	public void addRefTypeAccess(TypeAccess ta) {
		this.cppType.addRefTypeAccess(ta);
	}

	@Override
	public Vector<TypeAccess> getRefTypeAccess() {
		return this.cppType.getRefTypeAccess();
	}

	@Override
	public CppClass getClassByName(String name) {
		return this.cppType.getClassByName(name);
	}
	
}
