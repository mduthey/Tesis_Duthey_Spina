package mmclass.abs;

public abstract class CppNamedElement implements CppModelElement {
	String name=null;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
