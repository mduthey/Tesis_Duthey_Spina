package mmclass;

public class CppImportDeclaration {
	CppClassFile pathImport = null;
	
	public CppImportDeclaration() {}
	public CppImportDeclaration(CppClassFile pathImport) {
		this.setPathImport(pathImport);
	}
	
	public void setPathImport(CppClassFile pathImport) {
		this.pathImport = pathImport;
	}
	
	public CppClassFile getPathImport() {
		return this.pathImport;
	}
}
