import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.lang.Class;

import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

public abstract class COMTOR {
	// **** FIELDS *****
	private static int errorCount = 0;
	private static int fileCount = 0;
	private static int COMMENT_STATEMENT=33; //token type for comments

	private static FilenameFilter javaFilter = new FilenameFilter() { 
		public boolean accept(File dir, String name) {
			return name.endsWith(".java");
		}
	};

	private static  FilenameFilter dirFilter = new FilenameFilter() {
		public boolean accept(File dir, String name) {
			return dir.isDirectory();
		}
	};

	// Main Functions
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println("Requires 2 arguments: A doclet to run and a file or");
			System.out.println("directory to process.");
			return;
		}

		CommonTree rootTree;
		Object docObj;
		Doclet doclet;

		try {
			docObj = Class.forName(args[0]).newInstance();
			doclet = (Doclet)docObj;
		} catch (Exception e) {
			System.out.println("The doclet class could not be loaded.");
			System.out.println("The fully-qualified name of the doclet class must");
			System.out.println("be passed as an arg to COMTOR.");
			return;
		}

		try {
			rootTree = genRootTree(args[1]);
		} catch (Exception e) {
			System.out.println("There was an error while parsing.");
			System.out.println("Please ensure that the source files are compilable Java files.");
			return;
		}

		if (rootTree == null) {
			System.out.println("Either the source file(s) or source directory does not exist.");
			return;
		}

		RootDoc rootDoc = new RootDoc(rootTree);

		Boolean success = doclet.start(rootDoc);
		if (!success) {
			System.out.println("The doclet did not execute successfully.");
		}
	}

	// Helper Functions //

	private static CommonTree genRootTree(String fileName) throws Exception {
		File dir;
		CommonTree root = new CommonTree(new CommonToken(-1, "RootDoc"));

		try {
			dir = new File(fileName);
		} catch (Exception e) {
			return null;
		}

		if (!dir.exists())
			return null;

		if (dir.isFile()) {
			root.addChild(processFile(dir.getPath()));
			return root;
		}

		root = processDirectory(root, dir);
		return root;
	}

	private static CommonTree runParser(String file) throws Exception {
		ANTLRFileStream input = new ANTLRFileStream(file);
		
		COMTORLexer lexer = new COMTORLexer(input);
		
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		
		COMTORParser parser = new COMTORParser(tokens);
		
		try {
            COMTORParser.start_return result = parser.start();
			return (CommonTree)result.getTree();
		} catch (RecognitionException|RewriteEmptyStreamException e) {
            throw e;
        } /*catch (RewriteEmtpyExceptionStream r)*/
	}

	private static String[] getJavaFiles(File dir) {
		return dir.list(javaFilter);
	}
	
	private static boolean hasChild(CommonTree tree, String text) {
		List children = tree.getChildren();
		if (children == null) return false;
		CommonTree child;
		for (int i = 0; i < children.size(); i++) {
			child = (CommonTree)children.get(i);
			if (text.equals(child.getText()))
				return true;
		}
		return false;
	}

	private static CommonTree runCommentParser(String file) throws Exception {
		ANTLRFileStream input = new ANTLRFileStream(file);
		
		JavaCommentsLexer lexer = new JavaCommentsLexer(input);
		
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		
		JavaCommentsParser parser = new JavaCommentsParser(tokens);
		
		try {
            JavaCommentsParser.start_return result = parser.start();
			return (CommonTree)result.getTree();
        } catch (Exception e) {
            throw new Exception();
        }
	}

	private static CommonTree addComments(CommonTree root, CommonTree comments) {
		// Returns modified root. (Java passes by value, so no side-effects.)

		CommonTree temp;
		CommonTree comm;
	
		if (comments.getChildCount() > 0) {
			comm = (CommonTree)comments.getChild(0);

			if (comm.getLine() == 1) {
				root.addChild(getCommentChild(comm));
				comments.deleteChild(0);
			}

			for (int i = 0; i < root.getChildCount() && comments.getChildCount() > 0; i++) {
				temp = (CommonTree)root.getChild(i);
				comm = (CommonTree)comments.getChild(0);
				if (comm.getLine() <= temp.getLine()) {
					temp.addChild(getCommentChild(comm));
					comments.deleteChild(0);
				}
			}

			List ch = root.getChildren();
			if (ch != null) {
				for (int i = 0; i < ch.size(); i++) {
					addComments((CommonTree)ch.get(i), comments);
				}
			}
		}
		return root;
	}

	public static CommonTree processDirectory(CommonTree root, File dir) throws Exception {
		if (dir.isDirectory()) {
			String[] files = getJavaFiles(dir);
			CommonTree temp;// = new CommonTree();
			CommonTree comments;// = new CommonTree();
			CommonTree child;
			String packageName;


			for (int i = 0; i < files.length; i++) {
				packageName = "";

				//System.out.println(dir.getPath()+"\\"+files[i]);
				
				//Run the parser on the child file.
				try {
					temp = processFile(dir.getPath()+"\\"+files[i]);//runParser(dir.getPath()+"\\"+files[i]);
				} catch (RecognitionException e) {
					errorCount++;
					continue;
				}

				// Mix comments into tree
				try {
					comments = runCommentParser(dir.getPath()+"\\"+files[i]);
				} catch (Exception e) {
					errorCount++;
					continue;
				}
				temp = addComments(temp, comments);
				fileCount++;

				if (hasChild(root, temp.getText())) {
					for (int j = 0; j < root.getChildCount(); j++) {
						CommonTree c = (CommonTree)root.getChild(j);
						if (temp.getText().equals(c.getText())) {
							c.addChildren(temp.getChildren());
							break;
						}
					}
				} else {
					root.addChild(temp);
				}
			}

			String[] subdirs = dir.list();
			for (int i=0; i < subdirs.length; i++) {
				root = processDirectory(root, new File(dir, subdirs[i]));
			}
		}
		return root;
	}

	private static CommonTree processFile(String file) throws Exception {
		CommonTree root = new CommonTree();
		try {
			root = runParser(file);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// Run comment parser on file
		CommonTree comments;
		try {
			comments = runCommentParser(file);
		} catch (Exception e) {
			return null;
		}
		// Mix in comments
		root = addComments(root, comments);

		// Return tree.
		return root;
	}

	private static CommonTree getCommentChild(CommonTree comm) {
		CommonToken tempToken = new CommonToken(COMMENT_STATEMENT, "COMMENT_STATEMENT");
		tempToken.setLine(comm.getLine());
		CommonTree tempTree = new CommonTree(tempToken);
		tempTree.addChild(comm);
		return tempTree;
	}
}