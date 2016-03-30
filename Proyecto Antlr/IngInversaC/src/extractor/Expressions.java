package extractor;

import generic.ExtractListenerCPP;
import generic.PromiseController;
import generic.TypesController;
import generic.XmiReferenceInterface;

import java.util.Vector;

import mmclass.SingleVariableDeclaration;
import mmclass.VariableDeclarationFragment;
import mmclass.VariableDeclarationGroup;
import mmclass.abs.CppExpression;
import mmclass.abs.CppMemberFunction;
import mmclass.abs.CppTypeInterface;
import mmclass.abs.CppTypedElementInterface;
import mmclass.abs.VariableDeclaration;
import mmclass.enums.Operator;
import mmclass.enums.UnaryOperator;
import mmclass.enums.VarType;
import mmclass.enums.AssignmentOperator;
import mmclass.expression.ArrayInitializer;
import mmclass.expression.AssignamentStatement;
import mmclass.expression.CppParenthizedExpression;
import mmclass.expression.DeclarationExpression;
import mmclass.expression.FieldAccess;
import mmclass.expression.InfixExpression;
import mmclass.expression.MethodInvocation;
import mmclass.expression.PostfixExpression;
import mmclass.expression.PrefixExpression;
import mmclass.expression.TypeAccess;
import mmclass.expression.VariableAccess;
import mmclass.expression.abs.BinaryExpression;
import mmclass.expression.literal.BooleanLiteral;
import mmclass.expression.literal.CharacterLiteral;
import mmclass.expression.literal.NullLiteral;
import mmclass.expression.literal.NumberLiteral;
import mmclass.expression.literal.StringLiteral;
import antlr4.cppparser.CPP14BaseListener;
import antlr4.cppparser.CPP14Parser.AdditiveexpressionContext;
import antlr4.cppparser.CPP14Parser.AndexpressionContext;
import antlr4.cppparser.CPP14Parser.AssignmentexpressionContext;
import antlr4.cppparser.CPP14Parser.BracedinitlistContext;
import antlr4.cppparser.CPP14Parser.CastexpressionContext;
import antlr4.cppparser.CPP14Parser.ConditionalexpressionContext;
import antlr4.cppparser.CPP14Parser.ConstantexpressionContext;
import antlr4.cppparser.CPP14Parser.EqualityexpressionContext;
import antlr4.cppparser.CPP14Parser.ExclusiveorexpressionContext;
import antlr4.cppparser.CPP14Parser.ExpressionContext;
import antlr4.cppparser.CPP14Parser.InclusiveorexpressionContext;
import antlr4.cppparser.CPP14Parser.InitializerclauseContext;
import antlr4.cppparser.CPP14Parser.LiteralContext;
import antlr4.cppparser.CPP14Parser.LogicalandexpressionContext;
import antlr4.cppparser.CPP14Parser.LogicalorexpressionContext;
import antlr4.cppparser.CPP14Parser.MultiplicativeexpressionContext;
import antlr4.cppparser.CPP14Parser.PmexpressionContext;
import antlr4.cppparser.CPP14Parser.PostfixexpressionContext;
import antlr4.cppparser.CPP14Parser.PrimaryexpressionContext;
import antlr4.cppparser.CPP14Parser.RelationalexpressionContext;
import antlr4.cppparser.CPP14Parser.ShiftexpressionContext;
import antlr4.cppparser.CPP14Parser.UnaryexpressionContext;
import antlr4.cppparser.CPP14Parser.UnqualifiedidContext;

public class Expressions extends CPP14BaseListener {
	Vector<CppExpression> cppExpressions = new Vector<CppExpression>();
	ExtractListenerCPP parent;
	private boolean arrayInitializer;
	private boolean makeFieldAccess;
	private boolean firstAccess;
	private boolean onAssignment;
	private VariableDeclarationFragment tmpFragment = null;
	private int totalBracedInit;
	
	public Expressions(ExtractListenerCPP parent){
		this.parent = parent;
	}
	
	public CppExpression getFirstExpression() {
		if(this.cppExpressions.size() > 0)
			return this.cppExpressions.firstElement();
		return null;
	}
	
	public CppExpression getLastExpression() {
		if(this.cppExpressions.size() > 0)
			return this.cppExpressions.lastElement();
		return null;
	}
	
	public CppExpression removeFirstExpression() {
		CppExpression out = null;
		if(this.cppExpressions.size() > 0){
			out = this.getFirstExpression();
			this.cppExpressions.remove( 0 );
		}
		return out;
	}
	
	public CppExpression removeLastExpression() {
		CppExpression out = null;
		if(this.cppExpressions.size() > 0){
			out = this.getLastExpression();
			this.cppExpressions.remove( this.cppExpressions.size()-1 );
		}
		return out;
	}
	
	public void addFirst(CppExpression exp) {
		this.cppExpressions.add(0, exp);
	}
	
	public void addElement(CppExpression exp){
		this.cppExpressions.addElement(exp);
	}
	
	public Vector<CppExpression> getCppExpressions() {
		return cppExpressions;
	}
	
	public void clearExpressions(){
		this.cppExpressions.clear();
	}
	
	@Override
	public void enterExpression(ExpressionContext ctx) {
		// assignmentexpression
		// expression ',' assignmentexpression
		super.enterExpression(ctx);
	}
	
	@Override
	public void exitExpression(ExpressionContext ctx) {
		super.exitExpression(ctx);
	}
	
	@Override
	public void enterConstantexpression(ConstantexpressionContext ctx) {
		// Case 1: conditionalexpression
		super.enterConstantexpression(ctx);
	}
	
	@Override
	public void exitConstantexpression(ConstantexpressionContext ctx) {
		super.exitConstantexpression(ctx);
	}
	
	@Override
	public void enterConditionalexpression(ConditionalexpressionContext ctx) {
		// Case 1: logicalorexpression
		// Case 2: logicalorexpression '?' expression ':' assignmentexpression
		super.enterConditionalexpression(ctx);
	}
	
	@Override
	public void exitConditionalexpression(ConditionalexpressionContext ctx) {
		super.exitConditionalexpression(ctx);
	}
	
	@Override
	public void enterLogicalorexpression(LogicalorexpressionContext ctx) {
		// logicalandexpression
		// logicalorexpression '||' logicalandexpression
		super.enterLogicalorexpression(ctx);
	}
	
	@Override
	public void exitLogicalorexpression(LogicalorexpressionContext ctx) {
		super.exitLogicalorexpression(ctx);
		if(ctx.getChildCount() == 3){ // + or -
			this.generateInfix(ctx.getChild(1).getText());
		}
	}
	
	@Override
	public void enterLogicalandexpression(LogicalandexpressionContext ctx) {
		// inclusiveorexpression
		// logicalandexpression '&&' inclusiveorexpression
		super.enterLogicalandexpression(ctx);
	}
	
	@Override
	public void exitLogicalandexpression(LogicalandexpressionContext ctx) {
		super.exitLogicalandexpression(ctx);
		if(ctx.getChildCount() == 3){ // + or -
			this.generateInfix(ctx.getChild(1).getText());
		}
	}
	
	@Override
	public void enterInclusiveorexpression(InclusiveorexpressionContext ctx) {
		// exclusiveorexpression
		// inclusiveorexpression '|' exclusiveorexpression
		super.enterInclusiveorexpression(ctx);
	}
	
	@Override
	public void exitInclusiveorexpression(InclusiveorexpressionContext ctx) {
		super.exitInclusiveorexpression(ctx);
		if(ctx.getChildCount() == 3){ // + or -
			this.generateInfix(ctx.getChild(1).getText());
		}
	}
	
	@Override
	public void enterExclusiveorexpression(ExclusiveorexpressionContext ctx) {
		// andexpression
		// exclusiveorexpression '^' andexpression
		super.enterExclusiveorexpression(ctx);
	}
	
	@Override
	public void exitExclusiveorexpression(ExclusiveorexpressionContext ctx) {
		super.exitExclusiveorexpression(ctx);
		if(ctx.getChildCount() == 3){ // + or -
			this.generateInfix(ctx.getChild(1).getText());
		}
	}
	
	@Override
	public void enterAndexpression(AndexpressionContext ctx) {
		// equalityexpression
		// andexpression '&' equalityexpression
		super.enterAndexpression(ctx);
	}
	
	@Override
	public void exitAndexpression(AndexpressionContext ctx) {
		super.exitAndexpression(ctx);
		if(ctx.getChildCount() == 3){ // + or -
			this.generateInfix(ctx.getChild(1).getText());
		}
	}
	
	@Override
	public void enterEqualityexpression(EqualityexpressionContext ctx) {
		// relationalexpression
		// equalityexpression '==' relationalexpression
		// equalityexpression '!=' relationalexpression
		super.enterEqualityexpression(ctx);
	}
	
	@Override
	public void exitEqualityexpression(EqualityexpressionContext ctx) {
		super.exitEqualityexpression(ctx);
		if(ctx.getChildCount() == 3){ // + or -
			this.generateInfix(ctx.getChild(1).getText());
		}
		
	}
	
	@Override
	public void enterRelationalexpression(RelationalexpressionContext ctx) {
		// shiftexpression
		// relationalexpression '<' shiftexpression
		// relationalexpression '>' shiftexpression
		// relationalexpression '<=' shiftexpression
		// relationalexpression '>=' shiftexpression
		super.enterRelationalexpression(ctx);
	}
	
	@Override
	public void exitRelationalexpression(RelationalexpressionContext ctx) {
		super.exitRelationalexpression(ctx);
		if(ctx.getChildCount() == 3){ // + or -
			this.generateInfix(ctx.getChild(1).getText());
		}
	}
	
	@Override
	public void enterShiftexpression(ShiftexpressionContext ctx) {
		// additiveexpression
		// shiftexpression '<<' additiveexpression
		// shiftexpression rightShift additiveexpression
		super.enterShiftexpression(ctx);
	}
	
	@Override
	public void exitShiftexpression(ShiftexpressionContext ctx) {
		super.exitShiftexpression(ctx);
		if(ctx.getChildCount() == 3){ // + or -
			if( ctx.getChild(1).getText().equals("<<"))
				this.generateInfix("<<");
			else
				this.generateInfix(">>");
		}
	}
	
	@Override
	public void enterAdditiveexpression(AdditiveexpressionContext ctx) {
		// multiplicativeexpression
		// additiveexpression '+' multiplicativeexpression
		// additiveexpression '-' multiplicativeexpression
		super.enterAdditiveexpression(ctx);
	}
	
	@Override
	public void exitAdditiveexpression(AdditiveexpressionContext ctx) {
		super.exitAdditiveexpression(ctx);
		if(ctx.getChildCount() == 3){ // + or -
			this.generateInfix(ctx.getChild(1).getText());
		}
	}
	
	@Override
	public void enterMultiplicativeexpression(MultiplicativeexpressionContext ctx) {
		// pmexpression
		// multiplicativeexpression '*' pmexpression
		// multiplicativeexpression '/' pmexpression
		// multiplicativeexpression '%' pmexpression
		super.enterMultiplicativeexpression(ctx);
	}
	
	public void exitMultiplicativeexpression(MultiplicativeexpressionContext ctx) {
		super.exitMultiplicativeexpression(ctx);
		if(ctx.getChildCount() == 3){ // + or -
			this.generateInfix(ctx.getChild(1).getText());
		}
	}
	
	@Override
	public void enterPmexpression(PmexpressionContext ctx) {
		// castexpression
		// pmexpression '.*' castexpression
		// pmexpression '->*' castexpression
		super.enterPmexpression(ctx);
	}
	
	@Override
	public void exitPmexpression(PmexpressionContext ctx) {
		super.exitPmexpression(ctx);
	}
	
	@Override
	public void enterCastexpression(CastexpressionContext ctx) {
		// unaryexpression
		// '(' typeid ')' castexpression
		super.enterCastexpression(ctx);
	}
	
	@Override
	public void exitCastexpression(CastexpressionContext ctx) {
		super.exitCastexpression(ctx);
	}
	
	@Override
	public void enterUnaryexpression(UnaryexpressionContext ctx) {
		// postfixexpression
		// '++' castexpression
		// '--' castexpression
		// unaryoperator castexpression
		// Sizeof unaryexpression
		// Sizeof '(' typeid ')'
		// Sizeof '...' '(' Identifier ')'
		// Alignof '(' typeid ')'
		// noexceptexpression
		// newexpression
		// deleteexpression
		super.enterUnaryexpression(ctx);
	}
	
	@Override
	public void exitUnaryexpression(UnaryexpressionContext ctx) {
		// postfixexpression
		// '++' castexpression
		// '--' castexpression
		// unaryoperator castexpression
		// Sizeof unaryexpression
		// Sizeof '(' typeid ')'
		// Sizeof '...' '(' Identifier ')'
		// Alignof '(' typeid ')'
		// noexceptexpression
		// newexpression
		// deleteexpression
		super.exitUnaryexpression(ctx);
		if( ctx.getChildCount() == 2 && ctx.castexpression() != null ){
			// '++' castexpression
			// '--' castexpression
			// unaryoperator castexpression
			CppExpression tmp = this.removeLastExpression();
			PrefixExpression result = new PrefixExpression();
			if(ctx.unaryoperator() != null){
				result.setOperator( UnaryOperator.fromLiteral( ctx.unaryoperator().getText() ) );
			}else{
				result.setOperator( UnaryOperator.fromLiteral( ctx.getChild(0).getText() ) );
			}
			result.setExpression(tmp);
			this.cppExpressions.add(result);
		}
		if( ctx.Sizeof() != null ){
			CppMemberFunction cmf = (CppMemberFunction) TypesController.getInstance().findObject("sizeof", null);
			MethodInvocation method = new MethodInvocation();
			if(cmf != null) {
				method.setMethod( cmf );
			}else{
				PromiseController.getInstance().addPromise("sizeof", method);
			}
			method.addArgument(this.removeLastExpression());
			this.cppExpressions.addElement(method);
		}
	}
	
	@Override
	public void enterPostfixexpression(PostfixexpressionContext ctx) {
		// primaryexpression
		// postfixexpression '[' expression ']'
		// postfixexpression '[' bracedinitlist ']'
		// postfixexpression '(' expressionlist? ')'
		// simpletypespecifier '(' expressionlist? ')'
		// typenamespecifier '(' expressionlist? ')'
		// simpletypespecifier bracedinitlist
		// typenamespecifier bracedinitlist
		// postfixexpression '.' Template? idexpression
		// postfixexpression '->' Template? idexpression
		// postfixexpression '.' pseudodestructorname
		// postfixexpression '->' pseudodestructorname
		// postfixexpression '++'
		// postfixexpression '--'
		// Dynamic_cast '<' typeid '>' '(' expression ')'
		// Static_cast '<' typeid '>' '(' expression ')'
		// Reinterpret_cast '<' typeid '>' '(' expression ')'
		// Const_cast '<' typeid '>' '(' expression ')'
		// Typeid '(' expression ')'
		// Typeid '(' typeid ')'
		super.enterPostfixexpression(ctx);
	}
	
	@Override
	public void exitPostfixexpression(PostfixexpressionContext ctx) {
		super.exitPostfixexpression(ctx);
		if(ctx.getChildCount() == 2 && (ctx.getChild(1).getText().equalsIgnoreCase("++") || ctx.getChild(1).getText().equalsIgnoreCase("--"))){
			PostfixExpression pe = new PostfixExpression();
			pe.setOperator(ctx.getChild(1).getText());
			pe.setExpression( this.removeLastExpression() );
			this.cppExpressions.addElement(pe);
		}else if( ctx.getChildCount() >= 3 && ctx.postfixexpression() != null && ctx.idexpression() != null ){
			// postfixexpression '.' Template? idexpression
			// postfixexpression '->' Template? idexpression
			if(this.cppExpressions.size() >= 2){
				FieldAccess fa = new FieldAccess();
				fa.setField( (VariableAccess) this.removeLastExpression() );
				fa.setExpression( this.removeLastExpression() );
				this.cppExpressions.addElement( fa );
			}
		}
	}
	
	@Override
	public void enterPrimaryexpression(PrimaryexpressionContext ctx) {
		// literal
		// This
		// '(' expression ')'
		// idexpression
		// lambdaexpression
		super.enterPrimaryexpression(ctx);
	}
	
	@Override
	public void exitPrimaryexpression(PrimaryexpressionContext ctx) {
		super.exitPrimaryexpression(ctx);
		if(ctx.getChildCount() == 3){
			// '(' expression ')'
			CppParenthizedExpression tmp = new CppParenthizedExpression();
			tmp.setExpression(this.removeLastExpression());
			this.cppExpressions.addElement(tmp);
		}
	}
	
	@Override
	public void enterLiteral(LiteralContext ctx) {
		// Integerliteral
		// Characterliteral
		// Floatingliteral
		// Stringliteral
		// booleanliteral
		// pointerliteral
		// userdefinedliteral
		super.enterLiteral(ctx);
		if(ctx.Integerliteral() != null){
			this.cppExpressions.addElement( new NumberLiteral( Integer.parseInt( ctx.Integerliteral().getText() ) ) );
		}else if(ctx.Characterliteral() != null){
			this.cppExpressions.addElement( new CharacterLiteral( ctx.Characterliteral().getText().charAt(1)) );
		}else if(ctx.Floatingliteral() != null){
			this.cppExpressions.addElement( new NumberLiteral( Float.parseFloat(ctx.Floatingliteral().getText()) ) );
		}else if(ctx.Stringliteral() != null){
			this.cppExpressions.addElement( new StringLiteral( ctx.Stringliteral().getText() ) );
		}else if(ctx.booleanliteral() != null){
			this.cppExpressions.addElement( new BooleanLiteral( ctx.booleanliteral().getText().equalsIgnoreCase("True") ) );
		}
	}
	
	@Override
	public void exitLiteral(LiteralContext ctx) {
		super.exitLiteral(ctx);
	}
	
	@Override
	public void enterUnqualifiedid(UnqualifiedidContext ctx) {
		super.enterUnqualifiedid(ctx);
		
		if(ctx.getText().equalsIgnoreCase("null")){
			this.cppExpressions.addElement( new NullLiteral() );
		}else if( ctx.getText().equalsIgnoreCase("FALSE") || ctx.getText().equalsIgnoreCase("TRUE") ){
			this.cppExpressions.addElement( new BooleanLiteral( ctx.getText().equalsIgnoreCase("TRUE") ) );
		}else{
			VariableAccess tmp = new VariableAccess();
			String currentMethod = this.parent.getCurrentMethodName();
			String currentClass = this.parent.getCurrentClassName();
			XmiReferenceInterface toSearch = null;
			if(currentMethod != null && currentClass != null){
				toSearch = TypesController.getInstance().findObject(currentClass, currentMethod+"()", ctx.getText(), null);
			}else if(currentClass != null){
				toSearch = TypesController.getInstance().findObject(currentClass, ctx.getText(), null);
			}
			
			if(toSearch == null){
				toSearch = TypesController.getInstance().findObject(ctx.getText(), null);
			}
			
			if(toSearch == null) {
				PromiseController.getInstance().addPromise(ctx.getText(), tmp);
				tmp.setVarName(ctx.getText());
			}else{
				if(toSearch instanceof VariableDeclaration){
					tmp.setVariable( (VariableDeclaration)toSearch );
					tmp.setVarName( ((VariableDeclaration)toSearch).getName());
				}else{
					tmp.setVarName(ctx.getText());
					//TODO: Si llego aca es un caso de RECT * rect tomado como Infix
				}
			}/*
			if(this.makeFieldAccess && !this.firstAccess){
				FieldAccess fa = new FieldAccess();
				fa.setField( tmp );
				fa.setExpression( this.removeLastExpression() );
				this.cppExpressions.addElement( fa );
				
			}else{
				this.firstAccess = false;
				this.cppExpressions.addElement( tmp );
			}*/
			this.cppExpressions.addElement( tmp );
		}
	}
		
	public void exitUnqualifiedid(UnqualifiedidContext ctx) {};
	
	@Override
	public void enterAssignmentexpression(AssignmentexpressionContext ctx) {
		// conditionalexpression
		// logicalorexpression assignmentoperator initializerclause
		// throwexpression
		super.enterAssignmentexpression(ctx);
		if(ctx.getChildCount() == 3){
			this.onAssignment = true;
		}
	}
	
	@Override
	public void exitAssignmentexpression(AssignmentexpressionContext ctx) {
		super.exitAssignmentexpression(ctx);
		if(ctx.getChildCount() == 3){
			this.onAssignment = false;
			// logicalorexpression assignmentoperator initializerclause
			if(this.tmpFragment != null){
				this.tmpFragment.setInitializer( this.removeLastExpression() );
				this.tmpFragment = null;
			}else{
				String op = ctx.assignmentoperator().getText();
				BinaryExpression tmp = new AssignamentStatement( AssignmentOperator.fromLiteral(op) );
				tmp.setRightOperand(this.removeLastExpression());
				tmp.setLeftOperand(this.removeLastExpression());
				this.cppExpressions.addElement(tmp);
			}
		}
	}
	
	@Override
	public void enterBracedinitlist(BracedinitlistContext ctx) {
		super.enterBracedinitlist(ctx);
		this.arrayInitializer = true;
		this.addFirst( new ArrayInitializer() );
		this.totalBracedInit++;
	}
	
	@Override
	public void exitBracedinitlist(BracedinitlistContext ctx) {
		super.exitBracedinitlist(ctx);
		this.totalBracedInit--;
		if(this.totalBracedInit == 0 && this.arrayInitializer == true){
			this.arrayInitializer = false;
			CppExpression tmp = this.removeFirstExpression();
			this.cppExpressions.addElement(tmp);
		}
	}
	
	@Override
	public void exitInitializerclause(InitializerclauseContext ctx) {
		// assignmentexpression
		// bracedinitlist
		super.exitInitializerclause(ctx);
		if( ctx.bracedinitlist() == null && this.cppExpressions.size() > 1 && this.arrayInitializer ){
			( (ArrayInitializer)this.cppExpressions.firstElement() ).addExpression( removeLastExpression() );
		}else if( ctx.assignmentexpression() == null && this.cppExpressions.size() > 1 && this.arrayInitializer ){
			CppExpression exp = this.removeFirstExpression();
			( (ArrayInitializer)this.cppExpressions.firstElement() ).addExpression( exp );
		}
	}
	
	private void generateInfix(String op){
		if(op.equals("*") && this.onAssignment && this.cppExpressions.size() == 0){
			this.generatePointerVar();
		}else{
			BinaryExpression tmp = new InfixExpression( Operator.fromLiteral(op) );
			tmp.setRightOperand(this.removeLastExpression());
			tmp.setLeftOperand(this.removeLastExpression());
			this.cppExpressions.addElement(tmp);
		}
	}
	
	private void generatePointerVar(){
		VariableAccess rightOperand = (VariableAccess)this.removeLastExpression(); 
		VariableAccess leftOperand = (VariableAccess) this.removeLastExpression();
		
		PromiseController.getInstance().removePromise(rightOperand.getVarName(), rightOperand);
		PromiseController.getInstance().removePromise(leftOperand.getVarName(), leftOperand);
		
		String typeName = leftOperand.getVarName();
		TypeAccess ta = new TypeAccess();
		CppTypeInterface type = this.parent.getSpecificType(typeName);
		ta.setTypeName(typeName);
		if(type != null){
			ta.setAccess(type);
		}else{
			PromiseController.getInstance().addPromise(typeName, ta);
		}
		
		VariableDeclarationFragment tmp = new VariableDeclarationFragment();
		tmp.setVartype(VarType.POINTER);
		tmp.setName(rightOperand.getVarName());
		
		DeclarationExpression decExp = new DeclarationExpression();
		VariableDeclarationGroup vdg = new VariableDeclarationGroup();
		decExp.addGroup(vdg);
		vdg.setType(ta);
		vdg.addFragment( (VariableDeclarationFragment) tmp);
		TypesController.getInstance().addMethodVar(this.parent.getCppClassFile(), this.parent.getCurrentClass(), this.parent.getCurrentMethod(), tmp);
		this.cppExpressions.addElement(decExp);
		this.tmpFragment = tmp;
	}

	public void setMakeFieldAccess(boolean b) {
		this.firstAccess = b;
		this.makeFieldAccess = b;
	}
}
