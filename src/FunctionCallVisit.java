

import java.util.ArrayList;

import org.mozilla.javascript.Token;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.FunctionCall;
import org.mozilla.javascript.ast.FunctionNode;
import org.mozilla.javascript.ast.NodeVisitor;

public class FunctionCallVisit implements NodeVisitor {
    ArrayList<FunctionCall> functionCalls = new ArrayList<FunctionCall>();
    ArrayList<FunctionNode> functionNodes = new ArrayList<FunctionNode>();

    public boolean visit(AstNode node) {
        if(node instanceof FunctionCall){
            functionCalls.add((FunctionCall) node);

        }
        if(node.getType() ==  Token.FUNCTION  && node instanceof FunctionNode){
            functionNodes.add((FunctionNode) node);
        }
        return true;
    }
    
    public ArrayList<FunctionCall> getFunctionCalls(){
        return functionCalls;
    }

    public ArrayList<FunctionNode> getFunctionNode(){
        return functionNodes;
    }

}
