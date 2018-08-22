

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.AstRoot;
import org.mozilla.javascript.ast.FunctionNode;

public class method{
	
	

	public static List<FunctionDetails> getFunctionDetails(String filename,String ChangedFileName) throws IOException
	{
		List<FunctionDetails> FD = new ArrayList<FunctionDetails>();
		
		CompilerEnvirons compilerEnvirons = new CompilerEnvirons();
		/*compilerEnvirons.setRecordingComments(true);
		compilerEnvirons.setRecordingLocalJsDocComments(true);
		compilerEnvirons.setStrictMode(true);
		compilerEnvirons.setRecoverFromErrors(true);*/
		//ErrorReporter errorReporter = compilerEnvirons.getErrorReporter();
		
		
		//File testScript = new File("C:\\Users\\I338008\\Documents\\BITS notes\\2nd practicalphase\\TRIAL\\myJavascriptFile.js");
		//File testScript = new File("C:\\Users\\I338008\\Documents\\BITS notes\\2nd practicalphase\\TRIAL\\empty.js");
		//File testScript = new File("C:\\Users\\I338008\\Documents\\BITS notes\\2nd practicalphase\\TRIAL\\myJavaFile.java");
		File testScript = new File(filename);

		
		FileReader fileReader = new FileReader(testScript);
		AstRoot astRoot = new Parser(compilerEnvirons).parse(fileReader, null, 1);


		FunctionCallVisit functionCallVisit = new FunctionCallVisit();
		astRoot.visit(functionCallVisit);



		ArrayList<FunctionNode> functionNodes = functionCallVisit.getFunctionNode();
		for(FunctionNode functionNode : functionNodes)
		{
			FunctionDetails fd = new FunctionDetails();

			/*System.out.println("function name:"+functionNode.getName()
			+" param count:"+functionNode.getParamCount()
			+" js doc:"+functionNode.getJsDoc());*/
			
			/*System.out.println("\n\n");
			System.out.println(functionNode.getName()+"()			"+functionNode.getLineno()+"-"+functionNode.getEndLineno());
			
			
			//get a list of all the parameters for this function
			List<AstNode> params = functionNode.getParams();
			for(AstNode paramNod : params)
			{
				System.out.println("name of parameter:"+paramNod.getString()
				+" type:"+paramNod.getType());
			}*/
						
			fd.setFileName(ChangedFileName);
			fd.setFunctionName(functionNode.getName());
			fd.setParams(functionNode.getParams());
			fd.setFunctionStartPos(functionNode.getLineno());
			fd.setFunctionEndPos(functionNode.getEndLineno());
			
			FD.add(fd);
		}

		PrintWriter writer = new PrintWriter(testScript);
		writer.print("");
		writer.close();
		
		return FD;

	}
}
