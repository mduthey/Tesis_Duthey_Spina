package mmclass.abs;

import generic.Class2XMI;
import mmclass.CppClass;

public abstract class CppPathReference implements Class2XMI{
	CppPathReferentiable referencedTerminal = null; //opposite: referencedIn
	
	public abstract CppClass getClass(String name);
}