import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.ArrayList;

public class ConstructorDoc implements CallableMemberDoc {

	private String name;
	private int lineNumber;

	private String modifiers;
	private PackageDoc pkgDoc;
	private CommentDoc comment;

	private ClassDoc classDoc;

	private ArrayList<String> params;

	public String getName() {
		return name;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public String getModifiers() {
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

	public ArrayList<String> getParams() {
		return params;
	}
}