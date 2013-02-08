import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.ArrayList;

public class FieldDoc implements MemberDoc {

	private String name;
	private int lineNumber;

	private ArrayList<String> modifiers;
	private PackageDoc pkgDoc;
	private CommentDoc comment;
	private ClassDoc classDoc;

	private String type;

	public FieldDoc(CommonTree root) {
		
	}

	public String getName() {
		return name;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public ArrayList<String> getModifiers() {
		return modifiers;
	}

	public PackageDoc getPackage() {
		return pkgDoc;
	}

	public CommentDoc getComment() {
		return comment;
	}

	public ClassDoc getClassDoc() {
		return classDoc;
	}

	public String getType() {
		return type;
	}

	public String toString() {
		return null;
	}

}