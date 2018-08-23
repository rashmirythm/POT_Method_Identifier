import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.mozilla.javascript.ast.AstNode;

public class Mediate {

	public static String CurrentCommit;
	public static String ParentCommit;
	public static Git git;
	public static Repository repository;

	public static List<FunctionDetails> FullCurRevFD = new ArrayList<FunctionDetails>();
	public static List<FunctionDetails> FullParRevFD = new ArrayList<FunctionDetails>();


	public static void init(String CurrentCommit) throws IOException, ParseException, GitAPIException
	{
		File gitWorkDir = new File("C:/Users/I338008/git/JSParser");
		git = Git.open(gitWorkDir);
		Repository repo = git.getRepository();
		ObjectId commitId = ObjectId.fromString(CurrentCommit);
		RevWalk revWalk = new RevWalk(repo);
		RevCommit commit = revWalk.parseCommit(commitId);
		RevTree tree = commit.getTree();


		//GETTING CURRENT AND PARENT COMMIT ID
		//========================================================================================================
		ParentCommit = getParentCommitID(CurrentCommit);
		System.out.println("Current Commit ID: "+CurrentCommit);
		System.out.println("Parent Commit ID: "+ParentCommit);
		//========================================================================================================

		//GETTING LIST OF ALL FILES IN GIT AT CURRENT COMMIT AND PARENT COMMIT
		//========================================================================================================
		List<String> CurrentAllFiles = new ArrayList<String>();
		List<String> ParentAllFiles = new ArrayList<String>();

		CurrentAllFiles = getAllFiles(CurrentCommit);
		ParentAllFiles = getAllFiles(ParentCommit);

		//UnComment
		/*System.out.println("\n\nLIST OF ALL FILES IN GIT AT CURRENT COMMIT:");
		System.out.println("*********************************************");
		for(int i=0;i<CurrentAllFiles.size();i++)
		{
			System.out.println(CurrentAllFiles.get(i));
		}
		System.out.println("\n\nLIST OF ALL FILES IN GIT AT PARENT COMMIT:");
		System.out.println("********************************************");
		for(int i=0;i<ParentAllFiles.size();i++)
		{
			System.out.println(ParentAllFiles.get(i));
		}*/
		//UnComment
		//========================================================================================================



		//GETTING LIST OF FILES CHANGED IN CURRENT COMMIT
		//========================================================================================================
		String[] CurrentCommitFilesChanged = getAllFilesChanged(CurrentCommit);
		System.out.println("\n\nLIST OF FILES CHANGED IN CURRENT COMMIT:");
		System.out.println("******************************************");
		for(String path:CurrentCommitFilesChanged) {
			System.out.println(path);
		}
		System.out.println("\n");
		//========================================================================================================



		//my logic for splitting diff data
		//============================================================================
		
		//=====================
		//ArrayList<FileLineDetails[]> LineChanges = new ArrayList<FileLineDetails[]>();
		//LineChanges = trydiffsplit.difftool( ParentCommit, CurrentCommit,CurrentCommitFilesChanged);
		//=====================
		
		//System.out.println(LineChanges.get(0));
		
		/*FileLineDetails[] LineChangesArray = LineChanges.toArray(new FileLineDetails[LineChanges.size()]);

		FileLineDetails[] newfilelinedetails = LineChangesArray[0];
		FileLineDetails[] oldfilelinedetails = LineChangesArray[1];
		

		System.out.println("Old Revision Line Details:");
		System.out.println("**************************");
		for(int j=0;j<oldfilelinedetails.length;j++)
		{
			System.out.println(oldfilelinedetails[j].filename+"			"+oldfilelinedetails[j].lineno+"			"+oldfilelinedetails[j].changetype);
		}
		System.out.println("\nNew Revision Line Details:");
		System.out.println("****************************");
		for(int i=0;i<newfilelinedetails.length;i++)
		{
			System.out.println(newfilelinedetails[i].filename+"			"+newfilelinedetails[i].lineno+"			"+newfilelinedetails[i].changetype);
		}*/

		//=============================================================================


		//GETTING LIST OF FUNCTION NAME + STARTLINE + ENDLINE FOR CURRENT COMMIT AND PARENT COMMIT
		//========================================================================================================

		Repository repository = git.getRepository();
		ObjectId treeId = ObjectId.fromString(CurrentCommit);
		RevWalk CurrentrevWalk = new RevWalk(repository);
		RevCommit Currentcommit = CurrentrevWalk.parseCommit(treeId);
		RevTree CurrentTree = Currentcommit.getTree();

		/*List<FunctionDetails> FDCurrentRev = new ArrayList<FunctionDetails>();
				List<FunctionDetails> FDParentRev = new ArrayList<FunctionDetails>();
		 */

		for (String filename:CurrentCommitFilesChanged) {

			if(filename.contains(".js"))
			{
				String file1 = "C:\\Users\\I338008\\Documents\\GIT Documents\\JSCurrentFile.txt";
				String file2 = "C:\\Users\\I338008\\Documents\\GIT Documents\\JSParentFile.txt";
				//File CurrentTestScript = new File("C:\\Users\\I338008\\Documents\\GIT Documents\\JSCurrentFile.txt");
				//File ParentTestScript = new File("C:\\Users\\I338008\\Documents\\GIT Documents\\JSParentFile.txt");

				MethodIdentifier.createFile(CurrentTree,file1,filename);
				RevCommit[] Parents = commit.getParents();
				for(RevCommit currentparent:Parents) {
					RevCommit ParentCommit = revWalk.parseCommit(currentparent.getId());
					RevTree ParentTree = ParentCommit.getTree();
					MethodIdentifier.createFile(ParentTree,file2,filename);
					//System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHhh");
				}

				/*ScriptOrFnNode CurrentastRoot = MethodIdentifier.getASTnode(CurrentTestScript);
						ScriptOrFnNode ParentastRoot = MethodIdentifier.getASTnode(ParentTestScript);
				 */


				//System.out.println("Current Revision Function Details:");
				List<FunctionDetails> CurRevFD = new ArrayList<FunctionDetails>();
				CurRevFD=method.getFunctionDetails(file1,filename);
				FunctionDetails[] CurRevFDArray = CurRevFD.toArray(new FunctionDetails[CurRevFD.size()]);

				//System.out.println("Parent Revision Function Details:");
				List<FunctionDetails> ParRevFD = new ArrayList<FunctionDetails>();
				ParRevFD=method.getFunctionDetails(file2,filename);
				FunctionDetails[] ParRevFDArray = ParRevFD.toArray(new FunctionDetails[ParRevFD.size()]);


				for(int a=0;a<CurRevFDArray.length;a++)
				{
					FullCurRevFD.add(CurRevFDArray[a]);
				}
				for(int b=0;b<ParRevFDArray.length;b++)
				{
					FullParRevFD.add(ParRevFDArray[b]);
				}


			}


		}

		FunctionDetails[] FullCurRevFDArray = FullCurRevFD.toArray(new FunctionDetails[FullCurRevFD.size()]);
		FunctionDetails[] FullParRevFDArray = FullParRevFD.toArray(new FunctionDetails[FullParRevFD.size()]);

		System.out.println("\nParent Revision Function Details:");
		System.out.println("************************************");
		for(int i=0;i<FullParRevFDArray.length;i++)
		{
			String parameter="";

			List<AstNode> params = FullParRevFDArray[i].getParams();
			int size=params.size();
			int s=0;
			for(AstNode paramNod : params)
			{
				s++;
				if(s==size)
				{
					parameter = parameter + paramNod.getString();
				}
				else
				{
					parameter = parameter + paramNod.getString()+",";
				}
				//parameter = parameter + paramNod.getString()+",";
				/*System.out.println("name of parameter:"+paramNod.getString()
				+" type:"+paramNod.getType());*/
			}
			System.out.println(FullParRevFDArray[i].getFileName()+"		"+FullParRevFDArray[i].getFunctionName()+"("+parameter+")	"+FullParRevFDArray[i].getFunctionStartPos()+"-"+FullParRevFDArray[i].getFunctionEndPos());

		}
		
		
		
		System.out.println("\nCurrent Revision Function Details:");
		System.out.println("************************************");
		for(int i=0;i<FullCurRevFDArray.length;i++)
		{
			String parameter="";
			List<AstNode> params = FullCurRevFDArray[i].getParams();
			int size=params.size();
			int s=0;
			for(AstNode paramNod : params)
			{
				s++;
				if(s==size)
				{
					parameter = parameter + paramNod.getString();
				}
				else
				{
					parameter = parameter + paramNod.getString()+",";
				}
				
				//parameter = parameter + paramNod.getString()+",";
				/*System.out.println("name of parameter:"+paramNod.getString()
				+" type:"+paramNod.getType());*/
			}
			System.out.println(FullCurRevFDArray[i].getFileName()+"		"+FullCurRevFDArray[i].getFunctionName()+"("+parameter+")	"+FullCurRevFDArray[i].getFunctionStartPos()+"-"+FullCurRevFDArray[i].getFunctionEndPos());


		}
		

		
		
		
		
		//ArrayList<FileLineDetails[]> LineChanges = new ArrayList<FileLineDetails[]>();
		/*LineChanges = */
		List<MethodsAffected> methodsaff = new ArrayList<MethodsAffected>();
		
		methodsaff = trydiffsplit.difftool( ParentCommit, CurrentCommit,CurrentCommitFilesChanged,FullCurRevFDArray,FullParRevFDArray);
		
		HashSet<MethodsAffected> methodsaffset = new HashSet(methodsaff);
		
		for(MethodsAffected ch:methodsaffset)
		{
			System.out.println(ch.FileName+"	"+ch.FunctionName+"("+ch.FunctionParameter+")");
		}
		

	}
	public static String getParentCommitID(String CurrentCommit) throws IOException
	{
		//GETTING CURRENT AND PARENT COMMIT ID
		//========================================================================================================
		File gitWorkDir = new File("C:/Users/I338008/git/JSParser");
		git = Git.open(gitWorkDir);
		Repository repo = git.getRepository();
		ObjectId commitId = ObjectId.fromString(CurrentCommit);
		RevWalk revWalk = new RevWalk(repo);
		RevCommit commit = revWalk.parseCommit(commitId);
		RevTree tree = commit.getTree();
		RevCommit pcommit = commit.getParent(0);
		String parent = pcommit.getId().toString();
		String[] parentObjectDetailsSplit = parent.split("\\s+");
		String Parentcommit = parentObjectDetailsSplit[1];
		ParentCommit = Parentcommit;

		//System.out.println("Current Commit ID: "+CurrentCommit);
		//System.out.println("Parent Commit ID: "+ParentCommit);
		return ParentCommit;
		//========================================================================================================
	}

	public static List<String> getAllFiles(String commit) throws IOException
	{
		//GETTING LIST OF ALL FILES IN GIT AT CURRENT COMMIT AND PARENT COMMIT
		//========================================================================================================

		File gitWorkDir = new File("C:/Users/I338008/git/JSParser");
		git = Git.open(gitWorkDir);
		Repository repo = git.getRepository();

		List<String> AllFiles = new ArrayList<String>();

		Process procCurrent=Runtime.getRuntime().exec("git ls-tree -r "+commit+" --name-only", null, gitWorkDir);
		InputStream isCurr = (InputStream) procCurrent.getInputStream();
		InputStreamReader isrCurr = new InputStreamReader(isCurr);
		BufferedReader brCurr = new BufferedReader(isrCurr);
		String CurrFileline;
		while ((CurrFileline = brCurr.readLine()) != null) {
			AllFiles.add(CurrFileline);
		}
		return AllFiles;
		//========================================================================================================

	}

	public static String[] getAllFilesChanged(String CurrentCommit) throws IOException
	{
		//GETTING LIST OF FILES CHANGED IN CURRENT COMMIT AND PARENT COMMIT
		//========================================================================================================

		String[] CurrentCommitFilesChanged = MethodIdentifier.FilesChangedCurrent(CurrentCommit);

		//this following information is not required
		/*String[] ParentCommitFilesChanged=MethodIdentifier.FindGitCommitFiles(ParentCommit);
						System.out.println("\nLIST OF FILES CHANGED IN CURRENT COMMIT:");
						for(String path:ParentCommitFilesChanged) {
							System.out.println(path);
						}
						System.out.println("\n");*/

		return CurrentCommitFilesChanged;
		//========================================================================================================
	}
}
