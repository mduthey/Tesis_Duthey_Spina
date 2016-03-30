package generic;

import extractor.Expressions;
import mmclass.Constructor;
import mmclass.CppClass;
import mmclass.CppClassFile;
import mmclass.CppEnum;
import mmclass.CppEnumConstructor;
import mmclass.CppModel;
import mmclass.CppPackage;
import mmclass.CppVariable;
import mmclass.Destructor;
import mmclass.Method;
import mmclass.SingleVariableDeclaration;
import mmclass.VariableDeclarationFragment;
import mmclass.VariableDeclarationGroup;
import mmclass.abs.CppExpression;
import mmclass.abs.CppMemberFunction;
import mmclass.abs.CppTypeInterface;
import mmclass.abs.CppVariableType;
import mmclass.abs.VariableDeclaration;
import mmclass.enums.AccessSpecifier;
import mmclass.enums.ClassKey;
import mmclass.enums.LinkageSpecifier;
import mmclass.enums.StorageType;
import mmclass.enums.VarType;
import mmclass.expression.ArrayAccess;
import mmclass.expression.BreakStatement;
import mmclass.expression.CastExpression;
import mmclass.expression.CppBlock;
import mmclass.expression.CppCase;
import mmclass.expression.DeclarationExpression;
import mmclass.expression.DoWhileStatement;
import mmclass.expression.ForStatement;
import mmclass.expression.IfElseStatement;
import mmclass.expression.IfStatement;
import mmclass.expression.MethodInvocation;
import mmclass.expression.ReturnStatement;
import mmclass.expression.SwitchExpression;
import mmclass.expression.TypeAccess;
import mmclass.expression.VariableAccess;
import mmclass.expression.WhileStatement;
import mmclass.expression.abs.IterationStatement;
import mmclass.expression.abs.SelectionStatement;
import mmclass.primitivetypes.BooleanType;
import mmclass.primitivetypes.CharType;
import mmclass.primitivetypes.DoubleType;
import mmclass.primitivetypes.FloatType;
import mmclass.primitivetypes.IntType;
import mmclass.primitivetypes.LongType;
import mmclass.primitivetypes.ShortType;
import mmclass.primitivetypes.VoidType;
import java.util.Vector;

import antlr4.cppparser.CPP14BaseListener;
import antlr4.cppparser.CPP14Parser.AdditiveexpressionContext;
import antlr4.cppparser.CPP14Parser.AndexpressionContext;
import antlr4.cppparser.CPP14Parser.AssignmentexpressionContext;
import antlr4.cppparser.CPP14Parser.BracedinitlistContext;
import antlr4.cppparser.CPP14Parser.CastexpressionContext;
import antlr4.cppparser.CPP14Parser.ClassnameContext;
import antlr4.cppparser.CPP14Parser.ClassspecifierContext;
import antlr4.cppparser.CPP14Parser.CompoundstatementContext;
import antlr4.cppparser.CPP14Parser.ConditionContext;
import antlr4.cppparser.CPP14Parser.ConditionalexpressionContext;
import antlr4.cppparser.CPP14Parser.ConstantexpressionContext;
import antlr4.cppparser.CPP14Parser.CvqualifierContext;
import antlr4.cppparser.CPP14Parser.DeclarationContext;
import antlr4.cppparser.CPP14Parser.DeclspecifierContext;
import antlr4.cppparser.CPP14Parser.ElaboratedtypespecifierContext;
import antlr4.cppparser.CPP14Parser.EnumeratordefinitionContext;
import antlr4.cppparser.CPP14Parser.EnumheadContext;
import antlr4.cppparser.CPP14Parser.EnumspecifierContext;
import antlr4.cppparser.CPP14Parser.EqualityexpressionContext;
import antlr4.cppparser.CPP14Parser.ExclusiveorexpressionContext;
import antlr4.cppparser.CPP14Parser.ExpressionContext;
import antlr4.cppparser.CPP14Parser.ExpressionlistContext;
import antlr4.cppparser.CPP14Parser.ExpressionstatementContext;
import antlr4.cppparser.CPP14Parser.FunctionbodyContext;
import antlr4.cppparser.CPP14Parser.FunctiondefinitionContext;
import antlr4.cppparser.CPP14Parser.FunctionspecifierContext;
import antlr4.cppparser.CPP14Parser.InclusiveorexpressionContext;
import antlr4.cppparser.CPP14Parser.InitdeclaratorContext;
import antlr4.cppparser.CPP14Parser.InitdeclaratorlistContext;
import antlr4.cppparser.CPP14Parser.InitializerContext;
import antlr4.cppparser.CPP14Parser.InitializerclauseContext;
import antlr4.cppparser.CPP14Parser.InitializerlistContext;
import antlr4.cppparser.CPP14Parser.IterationstatementContext;
import antlr4.cppparser.CPP14Parser.JumpstatementContext;
import antlr4.cppparser.CPP14Parser.LabeledstatementContext;
import antlr4.cppparser.CPP14Parser.LiteralContext;
import antlr4.cppparser.CPP14Parser.LogicalandexpressionContext;
import antlr4.cppparser.CPP14Parser.LogicalorexpressionContext;
import antlr4.cppparser.CPP14Parser.MemberdeclarationContext;
import antlr4.cppparser.CPP14Parser.MemberdeclaratorContext;
import antlr4.cppparser.CPP14Parser.MemberdeclaratorlistContext;
import antlr4.cppparser.CPP14Parser.MemberspecificationContext;
import antlr4.cppparser.CPP14Parser.MultiplicativeexpressionContext;
import antlr4.cppparser.CPP14Parser.NestednamespecifierContext;
import antlr4.cppparser.CPP14Parser.NoptrdeclaratorContext;
import antlr4.cppparser.CPP14Parser.ParameterdeclarationContext;
import antlr4.cppparser.CPP14Parser.ParameterdeclarationclauseContext;
import antlr4.cppparser.CPP14Parser.ParametersandqualifiersContext;
import antlr4.cppparser.CPP14Parser.PmexpressionContext;
import antlr4.cppparser.CPP14Parser.PostfixexpressionContext;
import antlr4.cppparser.CPP14Parser.PrimaryexpressionContext;
import antlr4.cppparser.CPP14Parser.PtrdeclaratorContext;
import antlr4.cppparser.CPP14Parser.QualifiedidContext;
import antlr4.cppparser.CPP14Parser.RelationalexpressionContext;
import antlr4.cppparser.CPP14Parser.SelectionelseContext;
import antlr4.cppparser.CPP14Parser.SelectionstatementContext;
import antlr4.cppparser.CPP14Parser.ShiftexpressionContext;
import antlr4.cppparser.CPP14Parser.SimpledeclarationContext;
import antlr4.cppparser.CPP14Parser.SimpletypespecifierContext;
import antlr4.cppparser.CPP14Parser.StatementContext;
import antlr4.cppparser.CPP14Parser.StorageclassspecifierContext;
import antlr4.cppparser.CPP14Parser.UnaryexpressionContext;
import antlr4.cppparser.CPP14Parser.UnqualifiedidContext;

public class ExtractListenerCPP extends CPP14BaseListener {
	private static int anonClassID = 1;
	private static String STATEMENT_LITERAL= "IfStatement";
	private static String ITERATION_LITERAL= "IterationStatement";
	private static String SWITCH_LITERAL = "SwitchExpression";
	private static String ONLABEL_LITERAL = "labeled";
	
	Expressions extractExpressions = new Expressions(this);
	
	CppModel rootModel = null;
	CppClassFile cppClassFile = null;
	CppClass currentClass = null;
	CppClass defaultClass = null;
	String lastClassName = null; 
	
	VariableDeclaration lastVariable = null;
	
	String lastType = null;
	boolean isLong = false;
	boolean isShort = false;
	boolean typeDeclaration=false;
	int totalVarsDeclaration=0;
	boolean isPrimitive = false;
	String varName = null;
	
	boolean isExpression = false;
	
	boolean isArray = false;
	
	boolean simpleDeclaration = false;
	boolean memberDeclaration = false;
	
	boolean methodDeclaration = false;
	
	int iterationDeclaration = 0;
	
	AccessSpecifier accessSpecifier = null;
	
	CppMemberFunction currentMethod = null;
	
	Vector<IterationStatement> currentIteration = new Vector<IterationStatement>(); 
	Vector<CppExpression> currentSelection = new Vector<CppExpression>();
	
	CppEnum currentEnum = null;
	
	Vector<MethodInvocation> methodInvocation = new Vector<MethodInvocation>();
	boolean searchMethodForInvocation = false;
	String firstMethodInvocation = null;
	
	boolean fBodyDeclaration = false;
	
	boolean hasInitializer = false;
	
	boolean methodConstructor = false;
	boolean methodDestructor = false;
	
	boolean specInLine = false;
	boolean specVirtual = false;
	boolean specExplicit = false;
	
	boolean searchMethod = false;
	String methodToSearch = "";
	String functionName = "";

	boolean isReturnStatement;

	boolean waitCondition;

	Vector<Integer> waitStatement = new Vector<Integer>();
	
	Vector<CppBlock> lastBlock = new Vector<CppBlock>();

	private int selectionDeclaration;
	
	private Vector<String> currentStatement = new Vector<String>();
	private Vector<String> currentLabel = new Vector<String>();
	private boolean waitAssignment;
	private boolean isParameterDeclaration;
	private VarType nextVarType = VarType.OBJECT; // Default;
	private StorageType nextStorageType = StorageType.AUTO; // Default
	private boolean isCastExpression;
	private boolean waitConstantExpression;
	private String lastCastType;
	private boolean isCastLong;
	private boolean isCastShort;
	private boolean isCastPrimitive;
	private boolean isVarConst;
	private boolean methodCheckConst;
	private String ctxLast;
	
	public ExtractListenerCPP(CppClassFile cppClassFile, CppModel rootModel) {
		this.cppClassFile = cppClassFile;
		this.rootModel = rootModel;
		
		String tmp = Character.toUpperCase(cppClassFile.getName().charAt(0))+cppClassFile.getName().substring(1);
		this.defaultClass = (CppClass) TypesController.getInstance().findClassObject(cppClassFile.getName(), tmp);
		if(this.defaultClass == null){
			this.defaultClass = new CppClass(ClassKey.CLASS, tmp);
			this.defaultClass.setParent(cppClassFile);
			this.cppClassFile.addElement(this.defaultClass);
			TypesController.getInstance().addClass(this.cppClassFile, this.defaultClass);
			PromiseController.getInstance().updatePromises(tmp, defaultClass);
		}
		this.currentClass = this.defaultClass;
	}
	
	public String getCurrentMethodName(){
		if(this.currentMethod == null)
			return null;
		return this.currentMethod.getName();
	}
	
	public String getCurrentClassName(){
		if(this.currentClass == null)
			return null;
		return this.currentClass.getName();
	}
	
	@Override
	public void enterClassspecifier(ClassspecifierContext ctx) {
		// classhead '{' memberspecification? '}'
		super.enterClassspecifier(ctx);
		if( this.currentClass == this.defaultClass ) 
			this.currentClass = null;
		if( this.currentClass == null ){
			String classkey = ctx.classhead().classkey().getText().toUpperCase();
			String classname = null;
			if(ctx.classhead().classheadname() == null)
				classname = "Anonymous"+(ExtractListenerCPP.anonClassID++);
			else
				classname = ctx.classhead().classheadname().classname().getText();
			
			this.currentClass = (CppClass) TypesController.getInstance().findClassObject(this.cppClassFile.getName(), classname);
			if(this.currentClass == null){
				this.currentClass = new CppClass(ClassKey.valueOf(classkey), classname);
				this.currentClass.setParent(cppClassFile);
				TypesController.getInstance().addClass(cppClassFile, currentClass);
				PromiseController.getInstance().updatePromises(classname, currentClass);
			}
		}
	}
	
	@Override
	public void exitClassspecifier(ClassspecifierContext ctx) {
		super.exitClassspecifier(ctx);
		if(this.currentClass != null){
			this.cppClassFile.addElement(this.currentClass);
			this.currentClass = this.defaultClass;
		}
	}
	
	@Override
	public void enterMemberspecification(MemberspecificationContext ctx) {
		// memberdeclaration memberspecification?
		// accessspecifier ':' memberspecification?
		super.enterMemberspecification(ctx);
		if(ctx.getChild(1) != null && ctx.getChild(1).getText().equals(":") ){ // accessspecifier ':' memberspecification?
			this.accessSpecifier = AccessSpecifier.valueOf( ctx.getChild(0).getText().toUpperCase() );
		}
	}
	
	@Override
	public void enterMemberdeclaration(MemberdeclarationContext ctx) {
		// Case 1: attributespecifierseq? declspecifierseq? memberdeclaratorlist? ';'
		// Case 2: functiondefinition
		// Case 3: usingdeclaration
		// Case 4: static_assertdeclaration
		// Case 5: templatedeclaration
		// Case 6: aliasdeclaration
		// Case 7: emptydeclaration
		super.enterMemberdeclaration(ctx);
		if( (ctx.getChildCount() == 3 && ctx.attributespecifierseq() == null ) || ctx.functiondefinition() != null ){ 
			// declspecifierseq? memberdeclaratorlist? ';'
			// functiondefinition
			this.typeDeclaration = true;
			this.totalVarsDeclaration = 1;
			this.memberDeclaration = true;
		}
	}
	
	@Override
	public void exitMemberdeclaration(MemberdeclarationContext ctx) {
		super.exitMemberdeclaration(ctx);
		this.methodConstructor = false;
		this.methodDestructor = false;
		this.memberDeclaration = false;
		this.specExplicit = false;
		this.specInLine = false;
		this.specVirtual = false;
		this.currentMethod = null;
		this.endDeclaration();
	}
	
	@Override
	public void enterMemberdeclaratorlist(MemberdeclaratorlistContext ctx) {
		// Case 1: memberdeclarator
		// Case 2: memberdeclaratorlist ',' memberdeclarator
		super.enterMemberdeclaratorlist(ctx);
		if(ctx.getChildCount() == 3) // memberdeclaratorlist ',' memberdeclarator
			this.totalVarsDeclaration++;
	}
	
	@Override
	public void exitMemberdeclarator(MemberdeclaratorContext ctx) {
		super.exitMemberdeclarator(ctx);
		this.generateCppVariable();
	}
	
	@Override
	public void enterSimpletypespecifier(SimpletypespecifierContext ctx) {
		super.enterSimpletypespecifier(ctx);
		String tmp = ctx.getText();
		if(this.typeDeclaration == true ){
			if(ctx.getChildCount() == 1){
				if (this.isCastExpression){
					this.lastCastType = ctx.getText();
					if( this.lastCastType.toLowerCase().equals("long") )
						this.isCastLong = true;
					if( this.lastCastType.toLowerCase().equals("short"))
						this.isCastShort = true;
					this.isCastPrimitive = true;
				}else{
					this.lastType = ctx.getText();
					if( this.lastType.toLowerCase().equals("long") )
						this.isLong = true;
					if( this.lastType.toLowerCase().equals("short"))
						this.isShort = true;
					this.isPrimitive = true;
				}
			}
		}
		if (this.isCastExpression){
			this.lastCastType = ctx.getText();
			if( this.lastCastType.toLowerCase().equals("long") )
				this.isCastLong = true;
			if( this.lastCastType.toLowerCase().equals("short"))
				this.isCastShort = true;
			this.isCastPrimitive = true;
		}
	}
	
	@Override
	public void enterNoptrdeclarator(NoptrdeclaratorContext ctx) {
		// Case 1: declaratorid attributespecifierseq?
		// Case 2: noptrdeclarator parametersandqualifiers
		// Case 3: noptrdeclarator '[' constantexpression? ']' attributespecifierseq?
		// Case 4: '(' ptrdeclarator ')'
		super.enterNoptrdeclarator(ctx);
		if( ctx.getChildCount() == 2 && ctx.noptrdeclarator() != null ){
			//noptrdeclarator parametersandqualifiers
			this.methodDeclaration = true;
			this.totalVarsDeclaration--;
		}
		
		if( ctx.getChildCount() >= 3 && !ctx.getChild(0).getText().equals('(') ){
			//noptrdeclarator '[' constantexpression? ']' attributespecifierseq?
			this.isArray = true;
		}
	}
	
	@Override
	public void exitNoptrdeclarator(NoptrdeclaratorContext ctx) {
		super.exitNoptrdeclarator(ctx);
		if( ctx.getChildCount() == 2 && ctx.noptrdeclarator() != null ){
			//noptrdeclarator parametersandqualifiers
			this.methodDeclaration = false;
			this.totalVarsDeclaration--;
		}
	}
	
	@Override
	public void enterPtrdeclarator(PtrdeclaratorContext ctx) {
		//noptrdeclarator
		// ptroperator ptrdeclarator
		super.enterPtrdeclarator(ctx);
		if(ctx.ptroperator() != null){
			switch ( ctx.ptroperator().getChild(0).toString() ){
				case "*":
					this.nextVarType = VarType.POINTER;
				break;
				case "&":
					this.nextVarType = VarType.REFERENCE;
				break;
			}
		}
	}
	
	@Override
	public void enterUnqualifiedid(UnqualifiedidContext ctx) {
		// Identifier
		// operatorfunctionid
		// conversionfunctionid
		// literaloperatorid
		// '~' classname
		// '~' decltypespecifier
		// templateid
		//System.out.println(ctx.getText());
		super.enterUnqualifiedid(ctx);
		if(this.searchMethodForInvocation == true && ctx.Identifier() != null){
			if(!this.searchMethod) {
				CppMemberFunction cmf = (CppMemberFunction) TypesController.getInstance().findObject(this.currentClass.getName(), ctx.Identifier().getText()+"()", null);
				if(cmf == null ) {
					cmf = (CppMemberFunction) TypesController.getInstance().findObject(ctx.Identifier().getText()+"()", null);
				}
				if(cmf != null) {
					this.methodInvocation.lastElement().setMethod( cmf );
				}else{
					PromiseController.getInstance().addPromise(ctx.Identifier().getText(), this.methodInvocation.lastElement());
				}
				this.searchMethodForInvocation = false;
			}else{ // Case postfix (Class.Method)
				if(this.methodToSearch.equals("")){
					CppVariableType tmp = (CppVariableType) TypesController.getInstance().findObject(ctx.Identifier().toString(), null);
					if(tmp != null){
						this.methodToSearch = tmp.getTypeName()+"::";
						if(this.lastVariable == null)
							this.lastVariable = (VariableDeclaration)tmp;
					}
				}else if (this.methodToSearch.endsWith("::")) {
					this.methodToSearch += ctx.getText();
					String[] search = this.methodToSearch.split("::");
					CppMemberFunction cmf = (CppMemberFunction) TypesController.getInstance().findObject(search[0], search[1]+"()", null);
					if(cmf != null) {
						this.methodInvocation.lastElement().setMethod( cmf );
					}else{
						//PromiseController.getInstance().addPromise(search[1], this.methodInvocation.lastElement());
						PromiseController.getInstance().addPromise(search[0], search[1], this.methodInvocation.lastElement());
					}
					if(this.lastVariable != null){
						VariableAccess vatmp = new VariableAccess();
						vatmp.setVariable(this.lastVariable);
						vatmp.setVarName(this.lastVariable.getName());
						this.methodInvocation.lastElement().setExpression( vatmp );
						this.lastVariable = null;
					}
					this.searchMethodForInvocation = false;
					this.searchMethod = false;
					this.methodToSearch = "";
				}
			}
		}else if(this.isExpression){
			extractExpressions.enterUnqualifiedid(ctx);
		}else if(this.searchMethod == true){
			this.methodToSearch += ctx.getText();
		}else{
			this.varName = ctx.getText();
			if(this.methodDeclaration == true){
				if( this.currentMethod == null ){
					if(ctx.getChildCount() == 2){
						if( ctx.classname() != null && ctx.classname().getText().equals(this.currentClass.getName()) )
							this.methodDestructor = true;
						//this.varName = this.varName.substring(1, this.varName.length());
					}else if( this.varName.equals( this.currentClass.getName() ) ){
						this.methodConstructor = true;
					}
					this.generateCurrentMethod(this.ctxLast);
				}else{
					if( !this.isParameterDeclaration )
						if( ! this.currentMethod.getName().equalsIgnoreCase(this.varName) ){
							this.generateCurrentMethod(this.ctxLast);
						}
				}
			}
			
		}
	}
	
	@Override
	public void enterClassname(ClassnameContext ctx) {
		super.enterClassname(ctx);
		String tmp = ctx.getText();
		if(this.typeDeclaration){
			if(this.isCastExpression){
				if(ctx.Identifier() == null)
					this.lastCastType = ctx.simpletemplateid().templatename().Identifier().getText();
				else
					this.lastCastType = ctx.getText();
				this.isCastPrimitive=false;
			}else{
				if(ctx.Identifier() == null)
					this.lastType = ctx.simpletemplateid().templatename().Identifier().getText();
				else
					this.lastType = ctx.getText();
				this.lastClassName = this.lastType;
				this.isPrimitive=false;
			}
		}
		if(this.isCastExpression){
			if(ctx.Identifier() == null)
				this.lastCastType = ctx.simpletemplateid().templatename().Identifier().getText();
			else
				this.lastCastType = ctx.getText();
			this.isCastPrimitive=false;
		}
	}
	
	@Override
	public void enterElaboratedtypespecifier(ElaboratedtypespecifierContext ctx) {
		super.enterElaboratedtypespecifier(ctx);
		String tmp = ctx.getText();
		if(this.typeDeclaration){
			if (this.isCastExpression){
				this.lastCastType = ctx.Identifier().getText();
				this.isCastPrimitive=false;
			}else{
				this.lastType = ctx.Identifier().getText();
				this.lastClassName = this.lastType;
				this.isPrimitive=false;
			}
		}
		if (this.isCastExpression){
			this.lastCastType = ctx.Identifier().getText();
			this.isCastPrimitive=false;
		}
	}
	
	private void generateCppVariable(){
		if(this.totalVarsDeclaration > 0){
			VariableDeclaration tmp;
			if(this.fBodyDeclaration){
				tmp = new VariableDeclarationFragment();
			}else if(this.methodDeclaration == true){
				tmp = new SingleVariableDeclaration();
			}else{
				tmp = new CppVariable();
			}
			TypeAccess ta = new TypeAccess();
			
			tmp.setVartype(this.nextVarType);
			
			this.nextVarType = VarType.OBJECT;
			
			if(this.lastClassName != null && this.simpleDeclaration && !this.memberDeclaration){
				this.lastType = this.lastClassName;
				this.isPrimitive = false;
				this.lastClassName = null;
			}
			
			CppTypeInterface type = (this.isPrimitive) ? getPrimitiveType() : getSpecificType();
			
			ta.setTypeName(this.lastType);
			
			if(this.hasInitializer == true){
				tmp.setInitializer( this.extractExpressions.removeLastExpression() );
			}
			
			if(this.isArray == true){
				tmp.setArray(true);
				for(CppExpression exp: this.extractExpressions.getCppExpressions()){
					tmp.addDimension(exp);
				}
				this.extractExpressions.clearExpressions();
				this.isExpression = false;
				this.isArray = false;
			}
			
			//if(type != null){
			//	ta.setAccess(type);
			//	if(this.isPrimitive)
			//		this.rootModel.addElement(type);
			//}else{
			//	PromiseController.getInstance().addPromise(this.lastType, ta);
			//}
			
				tmp.setName(this.varName);
				if(this.fBodyDeclaration == true){
					DeclarationExpression decExp = new DeclarationExpression();
					VariableDeclarationGroup vdg = new VariableDeclarationGroup();
					decExp.addGroup(vdg);
					defineTypeAccess(ta, type);
					vdg.setType(ta);
					vdg.addFragment( (VariableDeclarationFragment) tmp);
					
					if(this.iterationDeclaration == 3){
						( (ForStatement)this.currentIteration.lastElement() ).setInitializer(decExp);
						TypesController.getInstance().addForVar(this.cppClassFile, this.currentClass, this.currentMethod, tmp);
						this.iterationDeclaration++;
					}else{
						if(this.lastBlock.size() == 0 || this.lastBlock.lastElement() == null) {
							if(this.currentStatement.size() > 0){
								if(this.currentStatement.lastElement().equals(ITERATION_LITERAL) && this.currentIteration.size() > 0){
									this.currentIteration.lastElement().setTheBody(decExp);
									TypesController.getInstance().addForVar(this.cppClassFile, this.currentClass, this.currentMethod, tmp);
								}else if(this.currentStatement.lastElement().equals(STATEMENT_LITERAL) && this.currentSelection.size() > 0){
									( (SelectionStatement) this.currentSelection.lastElement() ).setStatement(decExp);
									TypesController.getInstance().addForVar(this.cppClassFile, this.currentClass, this.currentMethod, tmp);
								}else if(this.currentStatement.lastElement().equals(SWITCH_LITERAL) && this.currentSelection.size() > 0){
									( (SwitchExpression) this.currentSelection.lastElement() ).setStatement(decExp);
									TypesController.getInstance().addForVar(this.cppClassFile, this.currentClass, this.currentMethod, tmp);
								}
							}else{
								this.currentMethod.getFunctionBody().addExpression(decExp);
								TypesController.getInstance().addMethodVar(this.cppClassFile, this.currentClass, this.currentMethod, tmp);
							}
						}else{
							this.lastBlock.lastElement().addExpression(decExp);
							TypesController.getInstance().addForVar(this.cppClassFile, this.currentClass, this.currentMethod, tmp);							
						}
					}
				}else if(this.methodDeclaration == true){
					if(TypesController.getInstance().findObject(this.currentClass.getName(), this.currentMethod.getName()+"()", tmp.getName(), null) == null){
						if(this.currentMethod.hasIncompleteParameters()){
							SingleVariableDeclaration param = this.currentMethod.getParameter(this.lastType);
							if(tmp.getInitializer() != null) param.setInitializer( tmp.getInitializer() );
							param.setVartype( tmp.getVartype() );
							if(tmp.isArray()){
								param.setArray( tmp.isArray() );
								param.setDimensions( tmp.getDimensions() );
							}
							param.setName( tmp.getName() );
							TypesController.getInstance().addMethodVar(this.cppClassFile, this.currentClass, this.currentMethod, param);
							tmp = param;
						}else{
							SingleVariableDeclaration tmpCast = (SingleVariableDeclaration)tmp;
							defineTypeAccess(ta, type);
							tmpCast.setType(ta);
							this.currentMethod.addParameter(tmpCast);
							TypesController.getInstance().addMethodVar(this.cppClassFile, this.currentClass, this.currentMethod, tmpCast);
						}
					}
				}else{
					CppVariable tmpCast = (CppVariable)tmp;
					defineTypeAccess(ta, type);
					tmpCast.setType(ta);
					tmpCast.setAccessSpecifier(this.accessSpecifier);
					tmpCast.setStorage(this.nextStorageType);
					tmpCast.setConst(this.isVarConst);
					if(this.nextStorageType == StorageType.TYPEDEF){
						this.cppClassFile.addElement(tmpCast);
						TypesController.getInstance().addVariable(this.cppClassFile, tmpCast);
					}else{
						this.currentClass.addField(tmpCast);
						TypesController.getInstance().addVariable(this.cppClassFile, this.currentClass, tmpCast);
					}
					this.nextStorageType = StorageType.AUTO;
				}
				PromiseController.getInstance().updatePromises( tmp );				
			//}
			this.totalVarsDeclaration--;
		}
	}
	
	private void defineTypeAccess(TypeAccess ta, CppTypeInterface type){
		if(type != null){
			ta.setAccess(type);
			if(this.isPrimitive)
				this.rootModel.addElement(type);
		}else{
			PromiseController.getInstance().addPromise(this.lastType, ta);
		}
	}
	
	private void generateCurrentMethod(String ctxMethod){
		if(this.methodDeclaration == true){
			if(this.methodConstructor == true || this.methodDestructor == true){
				if(this.methodConstructor == true){
					this.currentMethod = new Constructor(ctxMethod);
					this.currentMethod.setName( this.currentClass.getName() );
				}else if(this.methodDestructor == true){
					this.currentMethod = new Destructor(ctxMethod);
					( (Destructor)this.currentMethod ).setVirtual(this.specVirtual);
					this.currentMethod.setName( "~"+this.currentClass.getName() );
				}
				this.currentMethod.setInline(this.specInLine);
				this.currentMethod.setAccessSpecifier(this.accessSpecifier);
				this.currentClass.addField(this.currentMethod);
				PromiseController.getInstance().updatePromises( this.currentClass, this.currentMethod );
				PromiseController.getInstance().updatePromises( this.currentMethod );
				TypesController.getInstance().addMethod(this.cppClassFile, this.currentClass, this.currentMethod);
			}else{
				this.currentMethod = new Method(ctxMethod);
				( (Method)this.currentMethod ).setVirtual(this.specVirtual);
				( (Method)this.currentMethod ).setConst(this.isVarConst);
				this.currentMethod.setInline(this.specInLine);
				TypeAccess ta = new TypeAccess();
	
				if(this.lastClassName != null && this.simpleDeclaration && !this.memberDeclaration){
					this.lastType = this.lastClassName;
					this.isPrimitive = false;
					this.lastClassName = null;
				}
				
				CppTypeInterface type = (this.isPrimitive) ? getPrimitiveType() : getSpecificType();
				
				ta.setTypeName(this.lastType);
				
				if(type != null){
					ta.setAccess(type);
					( (Method) this.currentMethod ).setType(ta);
					if(this.isPrimitive)
						this.rootModel.addElement(type);
				}
				
				if(this.defaultClass == this.currentClass){
					this.accessSpecifier = AccessSpecifier.PUBLIC;
					this.currentMethod.setLinkage(LinkageSpecifier.STATIC);
				}
				
				this.currentMethod.setName(this.varName);
				if(this.varName.equalsIgnoreCase("main")){
					this.rootModel.setMainClass(this.currentClass);
				}
				this.currentMethod.setAccessSpecifier(this.accessSpecifier);
				
				this.currentClass.addField(this.currentMethod);
				PromiseController.getInstance().updatePromises( this.currentClass, this.currentMethod );
				PromiseController.getInstance().updatePromises( this.currentMethod );
				TypesController.getInstance().addMethod(this.cppClassFile, this.currentClass, this.currentMethod);
			}
		}
	}
	
	private CppTypeInterface getPrimitiveType() {
		CppTypeInterface out = null;
		if(this.isLong && !this.lastType.toLowerCase().equals("double")){
			out = LongType.getInstance();
		}else if(this.isShort){
			out = ShortType.getInstance();
		}else {
			switch( this.lastType.toLowerCase() ){
				case "bool": out = BooleanType.getInstance(); break;
				case "char": out = CharType.getInstance(); break;
				case "double": out = DoubleType.getInstance(); break;
				case "float": out = FloatType.getInstance(); break;
				case "int": out = IntType.getInstance(); break;
				case "short": out = ShortType.getInstance(); break;
				case "void": out = VoidType.getInstance(); break;
				//Signed
				//Unsigned
			}
		}
		return out;
	}
	
	private CppTypeInterface getSpecificType() {
		CppTypeInterface out = (CppTypeInterface) TypesController.getInstance().findTypeObject(this.lastType);
		if(out == null){
			switch(this.lastType.toLowerCase()) {
				case "string":
					out = createSystemType(specialtypes.CPPGeneric.getInstance(this.lastType));
				break;
				case "stringstream":
					out = createSystemType(specialtypes.CPPStringStream.getInstance(this.rootModel, this.lastType));
				break;
				case "ostream":
					out = createSystemType(specialtypes.CPPGeneric.getInstance(this.lastType));
				break;
				case "ofstream":
					out = createSystemType(specialtypes.CPPGeneric.getInstance(this.lastType));
				break;
				case "rect":
					out = createSystemType(specialtypes.CPPRect.getInstance(this.rootModel, this.lastType));
				break;
			}
		}
		return out;
	}
	
	public CppTypeInterface getSpecificType(String typeName){
		String tmp = this.lastType;
		this.lastType = typeName;
		CppTypeInterface out = this.getSpecificType();
		this.lastType = tmp;
		return out;
	}
	
	private CppTypeInterface createSystemType(specialtypes.SpecialTypes type) {
		CppPackage pkg;
		if(!rootModel.existPackage("system")){
			pkg = new CppPackage("system");
			rootModel.addElement(pkg);
		}else{
			pkg = rootModel.getPackage("system");
		}
		CppClassFile classFile = type.getClassFile();
		pkg.addElement(classFile);
		TypesController.getInstance().addClassFile(classFile);
		CppClass stringClass = type.getCClass();
		stringClass.setParent(classFile);
		classFile.addElement(stringClass);
		TypesController.getInstance().addClass(classFile, stringClass);
		return stringClass;
	}
	
	private void endDeclaration(){
		this.extractExpressions.clearExpressions();
		this.isArray = false;
		this.isLong = false;
		this.isShort = false;
		this.isPrimitive = false;
		this.isVarConst = false;
		if(!this.simpleDeclaration)
			this.totalVarsDeclaration=0;
		else
			this.totalVarsDeclaration=1;
		this.typeDeclaration = false;
		this.isExpression = false;
		this.methodDeclaration = false;
		this.lastClassName = null;
		
		this.specExplicit = false;
		this.specInLine = false;
		this.specVirtual = false;
		
		this.hasInitializer = false;
	}

	
	/**
	 * 
	 * Expressions Extractor
	 * 
	 */
	
	@Override
	public void enterExpression(ExpressionContext ctx) {
		this.isExpression = true;
		extractExpressions.enterExpression(ctx);
	}
	
	@Override
	public void exitExpression(ExpressionContext ctx) {

		super.exitExpression(ctx);
		if( this.iterationDeclaration > 0 && this.waitStatement.lastElement() == 0) {
			if(this.iterationDeclaration == 1 ){
				//DoWhile
				this.currentIteration.lastElement().setCondition(this.extractExpressions.removeLastExpression() );
			}else if(this.iterationDeclaration >= 3 && !this.waitCondition) {
				//For
				( (ForStatement) this.currentIteration.lastElement() ).setUpdater( this.extractExpressions.removeLastExpression() );
			}
		}
	}
	
	@Override
	public void enterCondition(ConditionContext ctx) {

		super.enterCondition(ctx);
		this.isExpression = true;
		this.waitCondition = true;
	}
	
	@Override
	public void exitCondition(ConditionContext ctx) {

		super.exitCondition(ctx);
		this.isExpression = false;
		this.waitCondition = false;
		if(this.currentStatement.size() > 0){
			if ( this.currentStatement.lastElement().equals(ITERATION_LITERAL) ) {
				this.currentIteration.lastElement().setCondition( this.extractExpressions.removeLastExpression() );
			}else if ( this.currentStatement.lastElement().equals(STATEMENT_LITERAL) ) {
				( (SelectionStatement) this.currentSelection.lastElement()).setCondition( this.extractExpressions.removeLastExpression() );
			}else if(this.currentStatement.lastElement().equals(SWITCH_LITERAL) && this.currentSelection.size() > 0){
				( (SwitchExpression) this.currentSelection.lastElement() ).setCondition( this.extractExpressions.removeLastExpression() );
			} 
		}
	}
	
	@Override
	public void enterInitializer(InitializerContext ctx) {

		super.enterInitializer(ctx);
		this.isExpression = true; // Siempre tendre una expresion.
	}
	
	@Override
	public void exitInitializer(InitializerContext ctx) {

		super.exitInitializer(ctx);
		this.isExpression = false; // Siempre tendre una expresion.
	}
	
	@Override
	public void enterConstantexpression(ConstantexpressionContext ctx) {
		this.isExpression = true;
		extractExpressions.enterConstantexpression(ctx);
	}
	
	@Override
	public void exitConstantexpression(ConstantexpressionContext ctx) {
		extractExpressions.exitConstantexpression(ctx);
		if(this.waitConstantExpression){
			this.waitConstantExpression = false;
			( (SwitchExpression) this.currentSelection.lastElement() ).getLastCase().addValues(this.extractExpressions.removeLastExpression()); 
		}
	}
	
	@Override
	public void enterConditionalexpression(ConditionalexpressionContext ctx) {
		extractExpressions.enterConditionalexpression(ctx);
	}
	
	@Override
	public void exitConditionalexpression(ConditionalexpressionContext ctx) {
		extractExpressions.exitConditionalexpression(ctx);
	}
	
	@Override
	public void enterLogicalorexpression(LogicalorexpressionContext ctx) {
		extractExpressions.enterLogicalorexpression(ctx);
	}
	
	@Override
	public void exitLogicalorexpression(LogicalorexpressionContext ctx) {
		extractExpressions.exitLogicalorexpression(ctx);
	}
	
	@Override
	public void enterLogicalandexpression(LogicalandexpressionContext ctx) {
		extractExpressions.enterLogicalandexpression(ctx);
	}
	
	@Override
	public void exitLogicalandexpression(LogicalandexpressionContext ctx) {
		extractExpressions.exitLogicalandexpression(ctx);
	}
	
	@Override
	public void enterInclusiveorexpression(InclusiveorexpressionContext ctx) {
		extractExpressions.enterInclusiveorexpression(ctx);
	}
	
	@Override
	public void exitInclusiveorexpression(InclusiveorexpressionContext ctx) {
		extractExpressions.exitInclusiveorexpression(ctx);
	}
	
	@Override
	public void enterExclusiveorexpression(ExclusiveorexpressionContext ctx) {
		extractExpressions.enterExclusiveorexpression(ctx);
	}
	
	@Override
	public void exitExclusiveorexpression(ExclusiveorexpressionContext ctx) {
		extractExpressions.exitExclusiveorexpression(ctx);
	}
	
	@Override
	public void enterAndexpression(AndexpressionContext ctx) {
		extractExpressions.enterAndexpression(ctx);
	}
	
	@Override
	public void exitAndexpression(AndexpressionContext ctx) {
		extractExpressions.exitAndexpression(ctx);
	}
	
	@Override
	public void enterEqualityexpression(EqualityexpressionContext ctx) {
		extractExpressions.enterEqualityexpression(ctx);
	}
	
	@Override
	public void exitEqualityexpression(EqualityexpressionContext ctx) {
		extractExpressions.exitEqualityexpression(ctx);
	}
	
	@Override
	public void enterRelationalexpression(RelationalexpressionContext ctx) {
		extractExpressions.enterRelationalexpression(ctx);
	}
	
	@Override
	public void exitRelationalexpression(RelationalexpressionContext ctx) {
		extractExpressions.exitRelationalexpression(ctx);
	}
	
	@Override
	public void enterShiftexpression(ShiftexpressionContext ctx) {
		if(ctx.getChildCount() == 3) this.waitAssignment = true;
		extractExpressions.enterShiftexpression(ctx);
	}
	
	@Override
	public void exitShiftexpression(ShiftexpressionContext ctx) {
		if(ctx.getChildCount() == 3) this.waitAssignment = false;
		extractExpressions.exitShiftexpression(ctx);
	}
	
	@Override
	public void enterAdditiveexpression(AdditiveexpressionContext ctx) {
		extractExpressions.enterAdditiveexpression(ctx);
	}
	
	@Override
	public void exitAdditiveexpression(AdditiveexpressionContext ctx) {
		extractExpressions.exitAdditiveexpression(ctx);
	}
	
	@Override
	public void enterMultiplicativeexpression(MultiplicativeexpressionContext ctx) {
		extractExpressions.enterMultiplicativeexpression(ctx);
	}
	
	public void exitMultiplicativeexpression(MultiplicativeexpressionContext ctx) {
		extractExpressions.exitMultiplicativeexpression(ctx);
	}
	
	@Override
	public void enterPmexpression(PmexpressionContext ctx) {
		extractExpressions.enterPmexpression(ctx);
	}
	
	@Override
	public void exitPmexpression(PmexpressionContext ctx) {
		extractExpressions.exitPmexpression(ctx);
	}
	
	@Override
	public void enterCastexpression(CastexpressionContext ctx) {
		if(ctx.unaryexpression() == null){
			this.isCastExpression = true;
		}
		extractExpressions.enterCastexpression(ctx);
	}
	
	@Override
	public void exitCastexpression(CastexpressionContext ctx) {
		// unaryexpression
		// '(' typeid ')' castexpression
		if(ctx.unaryexpression() == null){
			this.isCastExpression = false;
			this.generateCastExpression();
		}
		extractExpressions.exitCastexpression(ctx);
	}
	
	@Override
	public void enterUnaryexpression(UnaryexpressionContext ctx) {
		extractExpressions.enterUnaryexpression(ctx);
	}
	
	@Override
	public void exitUnaryexpression(UnaryexpressionContext ctx) {
		extractExpressions.exitUnaryexpression(ctx);
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
		//System.out.println(ctx.getText().toString());
		if(ctx.getChildCount() >= 3 && ctx.postfixexpression() != null && ctx.getChild(1).toString().equals("(")){
			//postfixexpression '(' expressionlist? ')'
			this.methodInvocation.addElement(new MethodInvocation());
			if(this.firstMethodInvocation == null) {
				this.firstMethodInvocation = ctx.getText();
			}
			this.searchMethodForInvocation = true;
		}else if( ctx.getChildCount() >= 3 && ctx.postfixexpression() != null && ctx.idexpression() != null ){
			if(this.searchMethodForInvocation)
				this.searchMethod = true;
			else // Var.field
				this.extractExpressions.setMakeFieldAccess(true);
		}else if( ctx.getChildCount() >= 3 && ctx.simpletypespecifier() != null && ctx.getChild(1).toString().equals("(")){
			//simpletypespecifier '(' expressionlist? ')'
			this.isCastExpression = true;
		}else{
			extractExpressions.enterPostfixexpression(ctx);
		}
		
	}
	
	@Override
	public void exitPostfixexpression(PostfixexpressionContext ctx) {
		//System.out.println(ctx.getText().toString());
		if(ctx.getChildCount() >= 3 && ctx.postfixexpression() != null && ctx.getChild(1).toString().equals("(")){
			//if(this.currentMethod != null && this.fBodyDeclaration){
				if(this.firstMethodInvocation != null && this.firstMethodInvocation.equals(ctx.getText())) {
					this.firstMethodInvocation = null;
					this.searchMethodForInvocation = false;
					if(!this.isReturnStatement){
						if(this.hasInitializer || this.waitAssignment || this.waitCondition)
							this.extractExpressions.addElement(this.methodInvocation.firstElement());
							//this.extractExpressions.addFirst(this.methodInvocation.firstElement());
						else
							this.addExpression(this.methodInvocation.firstElement());
					}else{
						this.extractExpressions.addElement(this.methodInvocation.firstElement());
						//this.extractExpressions.addFirst(this.methodInvocation.firstElement());
					}
					this.methodInvocation.clear();
				}else if(this.methodInvocation.size() > 1){
					MethodInvocation tmp = this.methodInvocation.remove( this.methodInvocation.size() - 1 );
					if(this.isCastExpression){
						this.extractExpressions.addElement(tmp);
					}else{
						this.methodInvocation.lastElement().addArgument(tmp);
					}
				}
			/*}else{
				if(this.firstMethodInvocation != null && this.firstMethodInvocation.equals(ctx.getText())) {
					this.firstMethodInvocation = null;
					this.searchMethodForInvocation = false;
					if(!this.isReturnStatement){
						if(this.hasInitializer || this.waitAssignment || this.waitCondition)
							this.extractExpressions.addElement(this.methodInvocation.firstElement());
							//this.extractExpressions.addFirst(this.methodInvocation.firstElement());
						else
							this.addExpression(this.methodInvocation.firstElement());
					}else{
						this.extractExpressions.addElement(this.methodInvocation.firstElement());
						//this.extractExpressions.addFirst(this.methodInvocation.firstElement());
					}
					this.methodInvocation.clear();
				}
			}*/
		}else if( ctx.getChildCount() >= 3 && ctx.postfixexpression() != null && ctx.idexpression() != null ){
			if(this.searchMethodForInvocation)
				this.searchMethod = false;
			else{
				extractExpressions.exitPostfixexpression(ctx);
				this.extractExpressions.setMakeFieldAccess(false);
			}
		}else if( ctx.getChildCount() >= 3 && ctx.simpletypespecifier() != null && ctx.getChild(1).toString().equals("(")){
			//simpletypespecifier '(' expressionlist? ')'
			this.isCastExpression = false;
			this.generateCastExpression();
		}else if( ctx.getChildCount() == 4 && ctx.expression() != null && ctx.getChild(1).toString().equals("[")){
			//postfixexpression '[' expression ']'
			ArrayAccess aa = new ArrayAccess();
			aa.setIndex(this.extractExpressions.removeLastExpression());
			aa.setArray(this.extractExpressions.removeLastExpression());
			this.extractExpressions.addElement(aa);
		}else{
			extractExpressions.exitPostfixexpression(ctx);
		}
	}
	
	@Override
	public void exitExpressionlist(ExpressionlistContext ctx) {
		super.exitExpressionlist(ctx);
		if(this.currentMethod != null && this.fBodyDeclaration){
			if(this.methodInvocation.size() > 0) {
				/*for(CppExpression exp: this.extractExpressions.getCppExpressions()) {
					this.methodInvocation.lastElement().addArgument(exp);
				}
				this.extractExpressions.clearExpressions();*/
				//this.isExpression = false;
			}
		}
	}
	
	public void exitInitializerlist(InitializerlistContext ctx) {
		if(!this.isCastExpression){
			if(this.currentMethod != null && this.fBodyDeclaration){
				if(this.methodInvocation.size() > 0) {
					if(this.extractExpressions.getCppExpressions().size() > 0)
						this.methodInvocation.lastElement().addArgument( this.extractExpressions.removeLastExpression() );
				}
			}else{
				if(this.methodInvocation.size() > 0) {
					if(this.extractExpressions.getCppExpressions().size() > 0)
						this.methodInvocation.lastElement().addArgument( this.extractExpressions.removeLastExpression() );
				}
			}
		}
	}
	
	@Override
	public void exitExpressionstatement(ExpressionstatementContext ctx) {
		super.exitExpressionstatement(ctx);
		if(this.currentMethod != null && this.fBodyDeclaration){
			for(CppExpression exp: this.extractExpressions.getCppExpressions()) {
				addExpression(exp);
			}
			this.extractExpressions.clearExpressions();
			this.isExpression = false;
		}
	}
	
	@Override
	public void enterPrimaryexpression(PrimaryexpressionContext ctx) {
		extractExpressions.enterPrimaryexpression(ctx);
	}
	
	@Override
	public void exitPrimaryexpression(PrimaryexpressionContext ctx) {
		extractExpressions.exitPrimaryexpression(ctx);
	}
	
	@Override
	public void enterLiteral(LiteralContext ctx) {
		extractExpressions.enterLiteral(ctx);
	}
	
	public void exitLiteral(LiteralContext ctx) {
		extractExpressions.exitLiteral(ctx);
	};
	
	@Override
	public void enterAssignmentexpression(AssignmentexpressionContext ctx) {
		if(ctx.getChildCount() == 3)
			this.waitAssignment = true;
		extractExpressions.enterAssignmentexpression(ctx);
	}
	
	@Override
	public void exitAssignmentexpression(AssignmentexpressionContext ctx) {
		extractExpressions.exitAssignmentexpression(ctx);
		if(this.currentMethod != null && this.fBodyDeclaration){
			if(ctx.getChildCount() == 3){
				this.waitAssignment = false;
				//System.out.println(ctx.getText().toString());
				CppExpression exp = extractExpressions.removeLastExpression();
				addExpression(exp);
				extractExpressions.clearExpressions();
				this.isExpression = false;
			}
		}
	}
	
	/*
	 * 
	 * Declarations
	 * 
	 */
	
	@Override
	public void enterSimpledeclaration(SimpledeclarationContext ctx) {
		// Case 1: declspecifierseq? initdeclaratorlist? ';'
		// Case 2: attributespecifierseq declspecifierseq? initdeclaratorlist ';'
		super.enterSimpledeclaration(ctx);
		if(ctx.getChildCount() == 3 && ctx.attributespecifierseq() == null){
			// declspecifierseq? initdeclaratorlist? ';'
			this.typeDeclaration = true;
			this.totalVarsDeclaration = 1;
			this.simpleDeclaration = true;
		}
	}
	
	@Override
	public void exitSimpledeclaration(SimpledeclarationContext ctx) {
		super.exitSimpledeclaration(ctx);
		this.simpleDeclaration = false;
		this.endDeclaration();
		
	}
	
	@Override
	public void enterInitdeclaratorlist(InitdeclaratorlistContext ctx) {
		// initdeclarator
		// initdeclaratorlist ',' initdeclarator
		super.enterInitdeclaratorlist(ctx);
		if(ctx.getChildCount() == 3) // initdeclaratorlist ',' initdeclarator
			this.totalVarsDeclaration++;
	}	
	
	@Override
	public void enterInitdeclarator(InitdeclaratorContext ctx) {
		// declarator initializer?
		super.enterInitdeclarator(ctx);
		if(ctx.initializer() != null){
			this.hasInitializer = true;
		}
	}
	
	@Override
	public void exitInitdeclarator(InitdeclaratorContext ctx) {
		super.exitInitdeclarator(ctx);
		this.generateCppVariable();
	}
	
	/*
	 * 
	 * Method vars
	 * 
	 */
	
	@Override
	public void enterParameterdeclarationclause(ParameterdeclarationclauseContext ctx) {
		// Case 1: parameterdeclarationlist? '...'?
		// Case 2: parameterdeclarationlist ',' '...'
		super.enterParameterdeclarationclause(ctx);
		if(ctx.getChildCount() == 1 && ctx.parameterdeclarationlist() != null){
			this.typeDeclaration = true;
			this.totalVarsDeclaration = 0;
			this.memberDeclaration = true;
			this.isParameterDeclaration = true;
		}
	}
	
	@Override
	public void exitParameterdeclarationclause( ParameterdeclarationclauseContext ctx) {
		super.exitParameterdeclarationclause(ctx);
		if(ctx.getChildCount() == 1 && ctx.parameterdeclarationlist() != null){
			this.memberDeclaration = false;
			this.isParameterDeclaration = false;
			this.endDeclaration();
		}
	}
	
	@Override
	public void enterParametersandqualifiers(ParametersandqualifiersContext ctx) {
		// '(' parameterdeclarationclause ')' cvqualifierseq? refqualifier?
		// exceptionspecification? attributespecifierseq?
		super.enterParametersandqualifiers(ctx);
		if(ctx.cvqualifierseq() != null){
			this.methodCheckConst = true;
		}
	}
	
	@Override
	public void exitParametersandqualifiers(ParametersandqualifiersContext ctx) {
		//'(' parameterdeclarationclause ')' cvqualifierseq? refqualifier?
		// exceptionspecification? attributespecifierseq?
		super.exitParametersandqualifiers(ctx);
		if(ctx.cvqualifierseq() != null){
			this.methodCheckConst = false;
		}
	}
	
	@Override
	public void enterParameterdeclaration(ParameterdeclarationContext ctx) {
		// Case 1: attributespecifierseq? declspecifierseq declarator
		// Case 2: attributespecifierseq? declspecifierseq declarator '=' initializerclause
		// Case 3: attributespecifierseq? declspecifierseq abstractdeclarator?
		// Case 4: attributespecifierseq? declspecifierseq abstractdeclarator? '='
		// Case 5: initializerclause
		super.enterParameterdeclaration(ctx);
		if(ctx.getChildCount() == 2 && ctx.declarator() != null){
			this.totalVarsDeclaration++;
		}
	}
	
	public void exitParameterdeclaration(ParameterdeclarationContext ctx) {
		if(ctx.getChildCount() == 2 && ctx.declarator() != null){
			this.generateCppVariable();
		}else if(ctx.getChildCount() == 1 ) {
			this.currentMethod.setIncompleteParameters(true);
			SingleVariableDeclaration tmp = new SingleVariableDeclaration();
			TypeAccess ta = new TypeAccess();
			
			tmp.setVartype(this.nextVarType);
			
			this.nextVarType = VarType.OBJECT;
			
			if(this.lastClassName != null && this.simpleDeclaration && !this.memberDeclaration){
				this.lastType = this.lastClassName;
				this.isPrimitive = false;
				this.lastClassName = null;
			}
			
			CppTypeInterface type = (this.isPrimitive) ? getPrimitiveType() : getSpecificType();
			ta.setTypeName(this.lastType);
			defineTypeAccess(ta, type);
			tmp.setType(ta);
			this.currentMethod.addParameter(tmp);
		}
	}
	
	@Override
	public void exitDeclaration(DeclarationContext ctx) {
		super.exitDeclaration(ctx);
		this.lastClassName = null;
	}
	
	/*
	 * 
	 * Method Body
	 * 
	 */
	@Override
	public void enterFunctionbody(FunctionbodyContext ctx) {
		// ctorinitializer? compoundstatement
		// functiontryblock
		// '=' Default ';'
		// '=' Delete ';'
		super.enterFunctionbody(ctx);
		this.fBodyDeclaration = true;
	}
	
	@Override
	public void exitFunctionbody(FunctionbodyContext ctx) {
		super.exitFunctionbody(ctx);
		this.fBodyDeclaration = false;
		// this.isExpression = false;
	}
	
	@Override
	public void enterCompoundstatement(CompoundstatementContext ctx) {
		// '{' statementseq? '}'
		super.enterCompoundstatement(ctx);
		if(ctx.statementseq() != null){
			if(this.waitStatement.size() > 0 && this.waitStatement.lastElement() > 0){
				if( this.lastBlock.lastElement() == null ){
					this.lastBlock.remove( this.lastBlock.size() - 1 );
					this.lastBlock.addElement(new CppBlock());
					if(this.currentStatement.lastElement().equals(ITERATION_LITERAL))
						this.currentIteration.lastElement().setTheBody(this.lastBlock.lastElement());
					else if (this.currentStatement.lastElement().equals(STATEMENT_LITERAL)){
						SelectionStatement tmp = (SelectionStatement) this.currentSelection.lastElement();
						//if(tmp.getStatement() != null){
						//	( (IfElseStatement)tmp ).setElseStatement(this.lastBlock.lastElement());
						//}else
							tmp.setStatement(this.lastBlock.lastElement());
					}else if(this.currentStatement.lastElement().equals(SWITCH_LITERAL) ){
						SwitchExpression tmp = (SwitchExpression) this.currentSelection.lastElement();
						if( tmp.hasCases() && !tmp.isWaitDefault() ){
							tmp.setStatement( this.lastBlock.lastElement() );
						}else{
							this.lastBlock.remove( this.lastBlock.size() - 1 );
							this.lastBlock.addElement(null);
						}
					} 
				}
			}
			else
				this.currentMethod.setFunctionBody(new CppBlock()); 
		}
	}
	
	@Override
	public void exitCompoundstatement(CompoundstatementContext ctx) {

		super.exitCompoundstatement(ctx);
		if(ctx.statementseq() != null){
			if(this.waitStatement.size() > 0 && this.waitStatement.lastElement() > 0){
				//if(this.currentLabel.lastElement().equals(ONLABEL_LITERAL)){
					this.lastBlock.remove( this.lastBlock.size() - 1 );
					this.lastBlock.addElement(null);
				//}
			} 
		}
	}
	
	@Override
	public void enterStatement(StatementContext ctx) {
		// labeledstatement
		// attributespecifierseq? expressionstatement
		// attributespecifierseq? compoundstatement
		// attributespecifierseq? selectionstatement
		// attributespecifierseq? iterationstatement
		// attributespecifierseq? jumpstatement
		// declarationstatement
		// attributespecifierseq? tryblock
		super.enterStatement(ctx);
		
		if(this.iterationDeclaration > 0 || this.selectionDeclaration > 0) {
			this.isExpression = false; //revisar
			int k = this.waitStatement.lastElement();
			k++;
			this.waitStatement.remove(  this.waitStatement.size() - 1 );
			this.waitStatement.addElement(k);
		}
	}
	
	@Override
	public void exitStatement(StatementContext ctx) {

		super.exitStatement(ctx);
		
		if(this.iterationDeclaration > 0 || this.selectionDeclaration > 0) {
			int k = this.waitStatement.lastElement();
			k--;
			this.waitStatement.remove(  this.waitStatement.size() - 1 );
			this.waitStatement.addElement(k);
		}
	}
	
	@Override
	public void enterLabeledstatement(LabeledstatementContext ctx) {
		// attributespecifierseq? Identifier ':' statement
		// attributespecifierseq? Case constantexpression ':' statement
		// attributespecifierseq? Default ':' statement
		super.enterLabeledstatement(ctx);
		
		if(ctx.Case() != null){
			CppCase tmp = new CppCase();
			this.waitConstantExpression = true;
			((SwitchExpression) this.currentSelection.lastElement()).addCase(tmp);
			this.currentLabel.addElement(ONLABEL_LITERAL);
		}else if(ctx.Default() != null){
			((SwitchExpression) this.currentSelection.lastElement()).setWaitDefault(true);
			this.currentLabel.addElement(ONLABEL_LITERAL);
		}
	}
	
	@Override
	public void exitLabeledstatement(LabeledstatementContext ctx) {
		super.exitLabeledstatement(ctx);
		if(ctx.Case() != null || ctx.Default() != null){
			this.currentLabel.remove( this.currentLabel.size() - 1 );
		}
	}
	
	
	
	@Override
	public void enterSelectionstatement(SelectionstatementContext ctx) {
		// If '(' condition ')' statement
		// If '(' condition ')' statement selectionelse
		// Switch '(' condition ')' statement
		super.enterSelectionstatement(ctx);
		this.selectionDeclaration = 1;
		if(ctx.getChildCount() == 5) {
			if(ctx.getChild(0).getText().equalsIgnoreCase("switch")){
				// Switch '(' condition ')' statement
				this.currentSelection.addElement( new SwitchExpression() );
				this.currentStatement.addElement(SWITCH_LITERAL);
				this.currentLabel.addElement(SWITCH_LITERAL);
			}else{
				// If '(' condition ')' statement
				this.currentSelection.addElement( new IfStatement() );
				this.currentStatement.addElement(STATEMENT_LITERAL);
			}
		}else{
			// If '(' condition ')' statement Else statement
			this.currentSelection.addElement( new IfElseStatement() );
			this.currentStatement.addElement(STATEMENT_LITERAL);
		}
		this.waitStatement.addElement(0);
		this.lastBlock.addElement(null);
	}
	
	@Override
	public void exitSelectionstatement(SelectionstatementContext ctx) {
		super.exitSelectionstatement(ctx);
		
		
		if(this.currentStatement.lastElement().equals(SWITCH_LITERAL)){
			this.currentLabel.remove( this.currentLabel.size() - 1 );
		}
		this.currentStatement.remove( this.currentStatement.size() - 1 );
		if(this.currentStatement.size() >= 1) {
			if(this.currentStatement.lastElement().equals(STATEMENT_LITERAL)){
				CppExpression tmp = this.currentSelection.remove( this.currentSelection.size() - 1 );
				SelectionStatement last = (SelectionStatement) this.currentSelection.lastElement();
				if( last.getStatement() == null )
					last.setStatement( tmp );
				else{
					CppExpression bodytmp = last.getStatement();
					if( bodytmp instanceof CppBlock ){
						( (CppBlock)bodytmp ).addExpression( tmp );
					}else{
						CppBlock blocktmp = new CppBlock();
						blocktmp.addExpression( last.getStatement() );
						blocktmp.addExpression(tmp);
						last.setStatement(blocktmp);
					}
				}
			}else if( this.currentStatement.lastElement().equals(SWITCH_LITERAL) ){
				CppExpression tmp = this.currentSelection.remove( this.currentSelection.size() - 1 );
				SwitchExpression last = (SwitchExpression)this.currentSelection.lastElement();
				last.setStatement( tmp );
			}
			else{
				CppExpression tmp = this.currentSelection.remove( this.currentSelection.size() - 1 );
				IterationStatement last = this.currentIteration.lastElement();
				if( last.getTheBody() == null )
					last.setTheBody( tmp );
				else{
					CppExpression bodytmp = last.getTheBody();
					if( bodytmp instanceof CppBlock ){
						( (CppBlock)bodytmp ).addExpression( tmp );
					}else{
						CppBlock blocktmp = new CppBlock();
						blocktmp.addExpression( last.getTheBody() );
						blocktmp.addExpression(tmp);
						last.setTheBody(blocktmp);
					}
				}
				if(!this.currentStatement.contains(STATEMENT_LITERAL)){
					this.selectionDeclaration = 0;
				}
			}
		}else{
			CppExpression tmp = this.currentSelection.remove( this.currentSelection.size() - 1 );
			this.currentMethod.getFunctionBody().addExpression(tmp); 
			this.selectionDeclaration = 0;
		}
		this.waitStatement.remove( this.waitStatement.size() - 1 );
		this.lastBlock.remove( this.lastBlock.size() - 1 );
	}
	
	@Override
	public void enterSelectionelse(SelectionelseContext ctx) {
		// Else statement
		super.enterSelectionelse(ctx);
		( (IfElseStatement) this.currentSelection.lastElement() ).setElseBranch(true);
	}
	
	@Override
	public void exitSelectionelse(SelectionelseContext ctx) {
		super.exitSelectionelse(ctx);
	}
	
	@Override
	public void enterJumpstatement(JumpstatementContext ctx) {
		// Break ';'
		// Continue ';'
		// Return expression? ';'
		// Return bracedinitlist ';'
		// Goto Identifier ';'
		super.enterJumpstatement(ctx);
		if(ctx.getChildCount() == 3 && ctx.expression() != null){
			// Return expression? ';'
			this.isReturnStatement = true;	
		}
	}
	
	@Override
	public void exitJumpstatement(JumpstatementContext ctx) {
		// Break ';'
		// Continue ';'
		// Return expression? ';'
		// Return bracedinitlist ';'
		// Goto Identifier ';'
		super.exitJumpstatement(ctx);
		if(ctx.getChildCount() == 3 && ctx.expression() != null){
			// Return expression? ';'
			ReturnStatement rs = new ReturnStatement();
			rs.setReturnExpression( this.extractExpressions.removeLastExpression() );
			this.extractExpressions.clearExpressions();
			this.isExpression = false;
			if(this.fBodyDeclaration){
				/*if(this.lastBlock.size() == 0 || this.lastBlock.lastElement() == null){
					if(this.iterationDeclaration > 0 )
						this.currentIteration.lastElement().setTheBody(rs);
					else
						this.currentMethod.getFunctionBody().addExpression(rs);
				}else
					this.lastBlock.lastElement().addExpression(rs);*/
				this.addExpression(rs);
			}
			this.isReturnStatement = false;
		}else if(ctx.Break() != null){
			BreakStatement bs = new BreakStatement();
			this.addExpression(bs);
		}
	}
	
	@Override
	public void enterIterationstatement(IterationstatementContext ctx) {
		// While '(' condition ')' statement
		// Do statement While '(' expression ')' ';'
		// For '(' forinitstatement condition? ';' expression? ')' statement
		// For '(' forrangedeclaration ':' forrangeinitializer ')' statement
		super.enterIterationstatement(ctx);
		this.iterationDeclaration = 1;
		if( ctx.getChild(0).getText().equalsIgnoreCase( "while" ) ){
			this.currentIteration.addElement(new WhileStatement());
		}else if( ctx.getChild(0).getText().equalsIgnoreCase( "do" ) ){
			this.currentIteration.addElement(new DoWhileStatement());
		}else if( ctx.getChild(0).getText().equalsIgnoreCase( "for" ) ){
			this.currentIteration.addElement(new ForStatement());
		}
		this.iterationDeclaration = this.currentIteration.lastElement().getType();
		this.currentStatement.addElement(ITERATION_LITERAL);
		this.waitStatement.addElement(0);
		this.lastBlock.addElement(null);
	}
	
	@Override
	public void exitIterationstatement(IterationstatementContext ctx) {
		// While '(' condition ')' statement
		// Do statement While '(' expression ')' ';'
		// For '(' forinitstatement condition? ';' expression? ')' statement
		// For '(' forrangedeclaration ':' forrangeinitializer ')' statement
		super.exitIterationstatement(ctx);
		this.currentStatement.remove( this.currentStatement.size() - 1 );
		if(this.currentStatement.size() >= 1) {
			if(this.currentStatement.lastElement().equals(ITERATION_LITERAL)){
				IterationStatement tmp = this.currentIteration.remove( this.currentIteration.size() - 1 );
				IterationStatement last = this.currentIteration.lastElement();
				if( last.getTheBody() == null )
					last.setTheBody( tmp );
				else{
					CppExpression bodytmp = last.getTheBody();
					if( bodytmp instanceof CppBlock ){
						( (CppBlock)bodytmp ).addExpression( tmp );
					}else{
						CppBlock blocktmp = new CppBlock();
						blocktmp.addExpression( last.getTheBody() );
						blocktmp.addExpression(tmp);
						last.setTheBody(blocktmp);
					}
				}
				this.iterationDeclaration = (last.getType() == 3 ) ? 4 : last.getType();
			}else{
				IterationStatement tmp = this.currentIteration.remove( this.currentIteration.size() - 1 );
				if(this.currentStatement.lastElement().equals(SWITCH_LITERAL)){
					SwitchExpression last = (SwitchExpression)this.currentSelection.lastElement();
					last.setStatement( tmp );
				}else if(this.currentStatement.lastElement().equals(STATEMENT_LITERAL) ){
					SelectionStatement last = (SelectionStatement)this.currentSelection.lastElement();
					if( last.getStatement() == null )
						last.setStatement( tmp );
					else{
						CppExpression bodytmp = last.getStatement();
						if( bodytmp instanceof CppBlock ){
							( (CppBlock)bodytmp ).addExpression( tmp );
						}else{
							CppBlock blocktmp = new CppBlock();
							blocktmp.addExpression( last.getStatement() );
							blocktmp.addExpression(tmp);
							last.setStatement(blocktmp);
						}
					}
				}
				
				if(!this.currentStatement.contains(ITERATION_LITERAL)){
					this.iterationDeclaration = 0;
				}else{
					this.iterationDeclaration = (this.currentIteration.lastElement().getType() == 3 ) ? 4 : this.currentIteration.lastElement().getType();
				}
			}
		}else{
			IterationStatement tmp = this.currentIteration.remove( this.currentIteration.size() - 1 );
			this.currentMethod.getFunctionBody().addExpression(tmp); 
			this.iterationDeclaration = 0;
		}
		this.waitStatement.remove( this.waitStatement.size() - 1 );
		this.lastBlock.remove( this.lastBlock.size() - 1 );
	}
	/*
	 *
	 * Declaration specifier
	 * 
	 */
	
	@Override
	public void enterDeclspecifier(DeclspecifierContext ctx) {
		// storageclassspecifier
		// typespecifier
		// functionspecifier
		// Friend
		// Typedef
		// Constexpr
		super.enterDeclspecifier(ctx);
		if(ctx.Typedef() != null){
			this.nextStorageType = StorageType.TYPEDEF;
		}
		if(ctx.Constexpr() != null){
			this.isVarConst = true;
		}
	}
	
	@Override
	public void enterCvqualifier(CvqualifierContext ctx) {
		// Const
		// Volatile
		super.enterCvqualifier(ctx);
		if(ctx.Const() != null){
			if(this.methodCheckConst){
				( (Method) this.currentMethod ).setConst(true);
			}else
				this.isVarConst = true;
		}
	}
	
	@Override
	public void enterStorageclassspecifier(StorageclassspecifierContext ctx) {
		// Register
		// Static
		// Thread_local
		// Extern
		// Mutable		
		super.enterStorageclassspecifier(ctx);
		this.nextStorageType = StorageType.fromLiteral(ctx.getText());
	}
	
	@Override
	public void enterFunctionspecifier(FunctionspecifierContext ctx) {
		super.enterFunctionspecifier(ctx);
		if(ctx.Inline() != null)
			this.specInLine = true;
		if(ctx.Virtual() != null)
			this.specVirtual = true;
		if(ctx.Explicit() != null)
			this.specExplicit = true;
	}
	
	@Override
	public void enterFunctiondefinition(FunctiondefinitionContext ctx) {
		// attributespecifierseq? declspecifierseq? declarator virtspecifierseq? functionbody
		super.enterFunctiondefinition(ctx);
		//System.out.println(ctx.getText());
		if(this.memberDeclaration == false) { // Type: ClassName::Function(){};
			if(	this.currentMethod == null ){
				
				//this.searchMethod = true;
				String declarator = ctx.declarator().getText();
				declarator = declarator.substring(0, declarator.indexOf("("));
				if(declarator.startsWith("&")) declarator = declarator.substring(1);
				CppMemberFunction functionTmp = null;
				if(declarator.contains("::")) {
					if(declarator.startsWith("::")) {
						String tmp = declarator.substring(2);
						if(tmp.startsWith("~")) tmp = tmp.substring(1);
						declarator = tmp+declarator;
					}
					String[] search = declarator.split("::");
					functionTmp = (CppMemberFunction) TypesController.getInstance().findObject(search[0], search[1]+"()", ctx.getText());
					if (this.currentClass == this.defaultClass) {
						this.currentClass = (CppClass) TypesController.getInstance().findTypeObject(search[0]);
					}
				} else {
					//functionTmp = (CppMemberFunction) TypesController.getInstance().findObject(declarator, ctx.getText());
					try {
						functionTmp = (CppMemberFunction) TypesController.getInstance().findObject(declarator+"()", ctx.getText());
						if (this.currentClass != this.defaultClass) {
							this.currentClass = this.defaultClass;
						}
					} catch (Exception e) {
						System.out.println("Error con: "+declarator);
						e.printStackTrace();
						
					}
					
				}
				
				this.ctxLast = ctx.getText();
				
				if(functionTmp == null) {
					this.functionName = declarator;
					this.searchMethod = true;
					this.typeDeclaration = true;
				} else {
					this.currentMethod = functionTmp;
				}
				
			}
		}
	}
	
	@Override
	public void exitFunctiondefinition(FunctiondefinitionContext ctx) {
		super.exitFunctiondefinition(ctx);
		if(this.memberDeclaration == false) {
			this.currentClass = this.defaultClass;
		}
		this.searchMethod = false; 
		this.currentMethod = null;
		this.typeDeclaration = false;
	}
	
	@Override
	public void enterNestednamespecifier(NestednamespecifierContext ctx) {
		// '::'
		// typename '::'
		// namespacename '::'
		// decltypespecifier '::'
		// nestednamespecifier Identifier '::'
		// nestednamespecifier Template? simpletemplateid '::'
		super.enterNestednamespecifier(ctx);
		if(this.searchMethod == true){
			this.methodToSearch = ctx.getText();
		}
	}
	
	@Override
	public void exitQualifiedid(QualifiedidContext ctx) {
		super.exitQualifiedid(ctx);
		if(this.searchMethod == true) {
			if(this.methodToSearch.startsWith("::")) {
				String tmp = this.methodToSearch.substring(2);
				if(tmp.startsWith("~")) tmp = tmp.substring(1);
				this.methodToSearch = tmp+this.methodToSearch;
			}
			String[] search = this.methodToSearch.split("::");
			CppMemberFunction tmp = (CppMemberFunction) TypesController.getInstance().findObject(search[0], search[1]+"()", null);
			this.currentMethod = tmp;
			
			if (this.currentClass == this.defaultClass) {
				this.currentClass = (CppClass) TypesController.getInstance().findObject(search[0], null);
			}
			this.methodToSearch = "";
			this.searchMethod = false;
		}
	}
	
	@Override
	public void exitUnqualifiedid(UnqualifiedidContext ctx) {
		super.exitUnqualifiedid(ctx);
		if(this.searchMethod){
			//if(!this.methodToSearch.contains("::") || !this.functionName.equals("")) {
			if(!functionName.equals("")){
				//CppMemberFunction tmp = (CppMemberFunction) TypesController.getInstance().findObject(this.methodToSearch+"()", null);
				CppMemberFunction tmp = (CppMemberFunction) TypesController.getInstance().findObject(this.methodToSearch+"()", this.ctxLast);
				this.currentMethod = tmp;
				
				if(this.currentMethod == null) {
					this.methodDeclaration = true;
					this.varName = this.functionName;
					this.generateCurrentMethod(this.ctxLast);
					//this.methodDeclaration = false;
				}
				if (this.currentClass != this.defaultClass) {
					this.currentClass = this.defaultClass;
				}
				this.methodToSearch = "";
				this.functionName = "";
				this.searchMethod = false;
			}
		}
	}
	
	@Override
	public void enterEnumspecifier(EnumspecifierContext ctx) {
		// enumhead '{' enumeratorlist? '}'
		// enumhead '{' enumeratorlist ',' '}'
		super.enterEnumspecifier(ctx);
		if ( this.currentEnum == null ) {
			this.currentEnum = new CppEnum();
		}
	}
	
	@Override
	public void enterEnumhead(EnumheadContext ctx) {
		// enumkey attributespecifierseq? Identifier? enumbase?
		// enumkey attributespecifierseq? nestednamespecifier Identifier enumbase?
		super.enterEnumhead(ctx);
		//Only catch the name.
		if( ctx.Identifier() != null ){
			this.currentEnum.setName( ctx.Identifier().getText() );
		}
		TypesController.getInstance().addEnum(this.cppClassFile, this.currentEnum);
		this.cppClassFile.addElement(this.currentEnum);
	}
	
	@Override
	public void exitEnumeratordefinition(EnumeratordefinitionContext ctx) {
		// enumerator
		// enumerator '=' constantexpression
		super.exitEnumeratordefinition(ctx);
		CppEnumConstructor tmp = new CppEnumConstructor();
		tmp.setName( ctx.enumerator().Identifier().getText() );
		if(ctx.getChildCount() == 3){
			tmp.setExpression( this.extractExpressions.removeLastExpression() );
		}
		this.currentEnum.addLiterals(tmp);
		TypesController.getInstance().addEnumLiteral(this.cppClassFile, this.currentEnum, tmp);
	}
	
	@Override
	public void exitEnumspecifier(EnumspecifierContext ctx) {
		super.exitEnumspecifier(ctx);
		if ( this.currentEnum != null ) {
			this.currentEnum = null;
		}
	}
	
	@Override
	public void enterBracedinitlist(BracedinitlistContext ctx) {
		super.enterBracedinitlist(ctx);
		this.extractExpressions.enterBracedinitlist(ctx);
		this.hasInitializer = true;
	}
	
	@Override
	public void exitBracedinitlist(BracedinitlistContext ctx) {
		super.exitBracedinitlist(ctx);
		this.extractExpressions.exitBracedinitlist(ctx);
	}
	
	@Override
	public void exitInitializerclause(InitializerclauseContext ctx) {
		// assignmentexpression
		// bracedinitlist
		super.exitInitializerclause(ctx);
		if(this.firstMethodInvocation == null)
			this.extractExpressions.exitInitializerclause(ctx);
	}
	
	private void addExpression(CppExpression exp){
		if(this.lastBlock.size() == 0 || this.lastBlock.lastElement() == null){
			if(this.currentStatement.size() > 0){
				if ( this.currentStatement.lastElement().equals(ITERATION_LITERAL) ){
					this.currentIteration.lastElement().setTheBody(exp);
				}else if ( this.currentStatement.lastElement().equals(STATEMENT_LITERAL)){
					((SelectionStatement)this.currentSelection.lastElement()).setStatement(exp);
				}else if ( this.currentStatement.lastElement().equals(SWITCH_LITERAL) ){
					( (SwitchExpression)this.currentSelection.lastElement() ).setStatement(exp);
				}
			}else
				this.currentMethod.getFunctionBody().addExpression(exp);
		}
		else
			this.lastBlock.lastElement().addExpression(exp);
	}
	
	public CppClassFile getCppClassFile() {
		return cppClassFile;
	}
	
	public CppClass getCurrentClass() {
		return currentClass;
	}
	
	public CppMemberFunction getCurrentMethod() {
		return currentMethod;
	}
	
	private void generateCastExpression(){
		CastExpression ce = new CastExpression();
		
		String tmpLastType = this.lastType;
		boolean tmpisLong = this.isLong;
		boolean tmpisShort = this.isShort;
		
		this.lastType = this.lastCastType; 
		this.isShort = this.isCastShort;
		this.isLong = this.isCastLong;
		
		CppTypeInterface type = (this.isCastPrimitive) ? getPrimitiveType() : getSpecificType();
		
		if(type != null && this.isCastPrimitive) {
			this.rootModel.addElement(type);
		}
		
		TypeAccess ta = new TypeAccess();
		ta.setTypeName(this.lastCastType);
		defineTypeAccess(ta, type);
		ce.setTypeAccess(ta);
		ce.setExpression(this.extractExpressions.removeLastExpression());
		this.extractExpressions.addElement(ce);
		this.lastCastType = null;
		this.lastType = tmpLastType;
		this.isShort = tmpisShort;
		this.isLong = tmpisLong;
	}
}
