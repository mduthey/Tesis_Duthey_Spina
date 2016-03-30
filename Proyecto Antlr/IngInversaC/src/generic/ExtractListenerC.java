package generic;

import org.antlr.v4.runtime.tree.ParseTree;

import antlr4.cparser.CBaseListener;
import antlr4.cparser.CParser.DirectDeclaratorContext;
import antlr4.cparser.CParser.SpecifierQualifierListContext;
import antlr4.cparser.CParser.StructDeclarationContext;
import antlr4.cparser.CParser.StructDeclaratorListContext;
import antlr4.cparser.CParser.StructOrUnionSpecifierContext;
import antlr4.cparser.CParser.TypeSpecifierContext;
import antlr4.cparser.CParser.TypedefNameContext;
import mmclass.CppClass;
import mmclass.CppClassFile;
import mmclass.CppModel;
import mmclass.enums.ClassKey;

public class ExtractListenerC extends CBaseListener {
	CppClassFile cppClassFile = null;
	CppClass currentClass = null;
	boolean currentClassFinal = false;
	String lastNode = null;
	String lastType = null;
	boolean typeDeclaration=false;
	int totalVarsDeclaration=0;
	
	public ExtractListenerC(CppClassFile cppClassFile, CppModel rootModel) {
		this.cppClassFile = cppClassFile; 
	}
	
	@Override
	public void enterStructOrUnionSpecifier(StructOrUnionSpecifierContext ctx) {
		// Case 1: structOrUnion Identifier? '{' structDeclarationList '}'
		// Case 2: structOrUnion Identifier
		super.enterStructOrUnionSpecifier(ctx);
		if(ctx.children.size() > 2){ // First Case
			if(ctx.Identifier() != null){
				// Name ctx.Identifier()
				if(this.currentClass == null){
					this.currentClass = new CppClass(ClassKey.STRUCT, ctx.Identifier().getText());
					this.currentClassFinal = true;
				}
			}
		}else{ // Second case
			this.currentClassFinal = false;
			if(this.typeDeclaration == true){
				this.lastType = ctx.Identifier().getText();
			}
		}
	}
	
	@Override
	public void exitStructOrUnionSpecifier(StructOrUnionSpecifierContext ctx) {
		super.exitStructOrUnionSpecifier(ctx);
		if(this.currentClass != null){
			if(!this.currentClassFinal) {
				this.currentClassFinal = true;
			}else{
				this.cppClassFile.addElement(this.currentClass);
				this.currentClass = null;
				this.currentClassFinal = false;
			}
			
		}
	}
	
	@Override
	public void enterStructDeclaration(StructDeclarationContext ctx) {
		// Case 1: specifierQualifierList structDeclaratorList? ';'
		// Case 2: staticAssertDeclaration
		super.enterStructDeclaration(ctx);
		if(this.currentClass != null){
			if(ctx.getChildCount() == 3){ // First Case with structDeclaratorList
				lastNode = "StructDeclaration";
			}else if(ctx.getChildCount() == 2){ // First Case without structDeclaratorList
			}
		}
	}
	
	@Override
	public void exitStructDeclaration(StructDeclarationContext ctx) {
		super.exitStructDeclaration(ctx);
		if(lastNode != null && lastNode.equals("StructDeclaration"))
			lastNode = null;
	}
	
	@Override
	public void enterSpecifierQualifierList(SpecifierQualifierListContext ctx) {
		super.enterSpecifierQualifierList(ctx);
		if(lastNode == null)
			lastNode = "SpecifierQualifierList";
	}
	
	@Override
	public void exitSpecifierQualifierList(SpecifierQualifierListContext ctx) {
		super.exitSpecifierQualifierList(ctx);
		if(lastNode != null && lastNode.equals("SpecifierQualifierList"))
			lastNode = null;
	}
	
	@Override
	public void enterTypeSpecifier(TypeSpecifierContext ctx) {
		super.enterTypeSpecifier(ctx);
		if(lastNode != null){
			if(ctx.getChildCount() == 1){
				this.totalVarsDeclaration=1;
				ParseTree child = ctx.getChild(0);
				if(child instanceof TypedefNameContext){
				}
				else if(child instanceof StructOrUnionSpecifierContext){
					this.typeDeclaration = true;
				}
				else
					this.lastType = ctx.getText();
			}
		}
	}
	
	@Override
	public void enterTypedefName(TypedefNameContext ctx) {
		super.enterTypedefName(ctx);
		if(this.lastType != null){
			System.out.println(ctx.getText() + " De tipo " + this.lastType);
			this.clearLastType();
		}		
	}
	
	@Override
	public void enterDirectDeclarator(DirectDeclaratorContext ctx) {
		// Case 1: Identifier
		// Case 2: '(' declarator ')'
		// Case 3: directDeclarator '[' typeQualifierList? assignmentExpression? ']'
		// Case 4: directDeclarator '[' 'static' typeQualifierList? assignmentExpression ']'
		// Case 5: directDeclarator '[' typeQualifierList 'static' assignmentExpression ']'
		// Case 6: directDeclarator '[' typeQualifierList? '*' ']'
		// Case 7: directDeclarator '(' parameterTypeList ')'
		// Case 8: directDeclarator '(' identifierList? ')'
		super.enterDirectDeclarator(ctx);
		if(this.lastType != null){
			if(ctx.getChildCount() == 1){ // Case 1
				if(ctx.Identifier() != null){
					System.out.println(ctx.Identifier().getText() + " De tipo " + this.lastType);
				}
			}
			this.clearLastType();
		}
		
	}

	@Override
	public void enterStructDeclaratorList(StructDeclaratorListContext ctx) {
		// Case 1: structDeclarator
	    // Case 2: structDeclaratorList ',' structDeclarator
		if(ctx.getChildCount() == 3){
			this.totalVarsDeclaration++;
		}
			
	}
	
	private void clearLastType(){
		this.totalVarsDeclaration--;
		if(this.totalVarsDeclaration <= 0)
			this.lastType = null;
	}
}
