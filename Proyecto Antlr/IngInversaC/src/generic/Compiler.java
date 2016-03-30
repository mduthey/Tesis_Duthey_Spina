package generic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.util.Vector;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import antlr4.cparser.CLexer;
import antlr4.cparser.CParser;
import antlr4.cppparser.CPP14Lexer;
import antlr4.cppparser.CPP14Parser;
import mmclass.CppClassFile;
import mmclass.CppModel;
import mmclass.CppPackage;

public class Compiler extends Thread {	
	String projectPath = null;
	CppModel rootModel;
	Vector<CppClassFile> classFiles = new Vector<CppClassFile>();
	Vector<String> readedFiles = new Vector<String>();
	
	public Compiler(String projectName, String projectPath) {
		this.setProjectPath(projectPath);
		this.rootModel = new CppModel(projectName);
	}
	
	public void execute() {
		File project = new File(this.getProjectPath());
		this.getPackages(project, null);
		try {
			this.parseFiles();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getPackages(File current, final CppPackage parent){
		current.list(new FilenameFilter() {
			public boolean accept(File current, String name) {
				File nextCurrent = new File(current, name);
				if ( nextCurrent.isDirectory() ) {
					CppPackage next = new CppPackage(name);
					if(parent == null) {
						rootModel.addElement(next);
					} else {
						parent.addElement(next);
					}
					getPackages(nextCurrent, next);
					return true;
				}else if( nextCurrent.isFile() ) {
					if(parent == null){
						if(!rootModel.existPackage("defaultpkg")){
							CppPackage tmp = new CppPackage("defaultpkg");
							rootModel.addElement(tmp);
							getPackages(current, tmp);
						}
					}else{
						if(name.endsWith(".h") || name.endsWith(".hpp") || name.endsWith(".c") || name.endsWith(".cpp")){
							String tmp = name.substring(0, name.lastIndexOf("."));
							if(!readedFiles.contains(parent.getName()+tmp)) {
								String path = nextCurrent.getPath();
								path = path.substring(0, path.lastIndexOf("."));
								CppClassFile next = new CppClassFile(tmp, path);
								parent.addElement(next);
								classFiles.add(next);
								readedFiles.add(parent.getName()+tmp);
							}
						}
					}
				}
				return false;
			}
		});
	}
	
	private void parseFiles() throws Exception {
		for(CppClassFile ccf: this.classFiles){
			TypesController.getInstance().addClassFile(ccf);
			String[] checkNames = {
				ccf.getPathFile()+".h",
				ccf.getPathFile()+".hpp",
				ccf.getPathFile()+".c",
				ccf.getPathFile()+".cpp"
			};
			for(String path: checkNames) {
				if( new File(path).exists() ){
					parseCPPFile(ccf, path);
				}
			}
		}
		
		PromiseController.getInstance().completePromises(this.rootModel);
		
	}
	
	private void parseCFile(CppClassFile ccf) throws Exception {
		ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(ccf.getPathFile()));
		CLexer lexer = new CLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CParser parser = new CParser(tokens);
		parser.setBuildParseTree(true);
		RuleContext tree = parser.compilationUnit();
		//tree.inspect(parser);
		
		ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker
        ExtractListenerC extractor = new ExtractListenerC(ccf, this.rootModel);
        walker.walk(extractor, tree); // initiate walk of tree with listener
	}
	
	private void parseCPPFile(CppClassFile ccf, String path) throws Exception {
		ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(path));
		CPP14Lexer lexer = new CPP14Lexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CPP14Parser parser = new CPP14Parser(tokens);
		parser.setBuildParseTree(true);
		RuleContext tree = parser.translationunit();
		//tree.inspect(parser);
		
		ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker
        ExtractListenerCPP extractor = new ExtractListenerCPP(ccf, this.rootModel);
        walker.walk(extractor, tree); // initiate walk of tree with listener
	}
	
	public String getProjectPath() {
		return this.projectPath;
	}
	
	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}
	
	public void saveToFile(String path) throws Exception{
		if(!path.endsWith(".xmi")) path += ".xmi";
		Writer out = null;
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
		this.rootModel.writeToFile(null, 0, out);
		out.close();
	}
	
	@Override
	public void run() {
		this.execute();
	}
	
}
