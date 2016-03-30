package mmclass.abs;

import mmclass.enums.AccessSpecifier;
import generic.Class2XMI;
import generic.XmiReferenceInterface;

public interface CppFieldInterface extends Class2XMI, XmiReferenceInterface{
	public void setContainer(CppFieldContainer fieldContainer);
	public CppFieldContainer getContainer();
	
	public void setAccessSpecifier(AccessSpecifier accessSpecifier);
	public AccessSpecifier getAccessSpecifier();
	
	public void setName(String name);
	public String getName();
}
