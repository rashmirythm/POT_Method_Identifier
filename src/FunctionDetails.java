import java.util.List;

import org.mozilla.javascript.ast.AstNode;

public class FunctionDetails {

	public String FileName;
	public String FunctionName;
	public List<AstNode> Params;
	public List<AstNode> getParams() {
		return Params;
	}
	public void setParams(List<AstNode> params) {
		Params = params;
	}
	public int FunctionStartPos;
	public int FunctionEndPos;
	
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getFunctionName() {
		return FunctionName;
	}
	public void setFunctionName(String functionName) {
		FunctionName = functionName;
	}
	public int getFunctionStartPos() {
		return FunctionStartPos;
	}
	public void setFunctionStartPos(int functionStartPos) {
		FunctionStartPos = functionStartPos;
	}
	public int getFunctionEndPos() {
		return FunctionEndPos;
	}
	public void setFunctionEndPos(int functionEndPos) {
		FunctionEndPos = functionEndPos;
	}
	
	
	
	
}
