import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.ArrayList;

public class ClassDoc implements ElementDoc {

	private String className;
	private int lineNumber;

	private String modifiers;
	private PackageDoc pkgDoc;
	private CommentDoc comment;

	private ArrayList<String> imports;
	private ArrayList<MethodDoc> methods;
	private ArrayList<ConstructorDoc> constructors;
	private ArrayList<FieldDoc> fields;

	// Creates a basic "null" class with only really basic info.
	private ClassDoc(String name, int ln) {
		this.className = name;
		this.lineNumber = ln;
	}

	// Parses an ANTLR tree to create the class documentation
	private ClassDoc(CommonTree root) {

	}

	public String getName() {
		return className;
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

	public ArrayList<String> getImports() {
		return imports;
	}

	public ArrayList<MemberDoc> getMembers() {
		ArrayList<MemberDoc> members = new ArrayList<MemberDoc>(methods);
		members.addAll(constructors);
		members.addAll(fields);
		return members;
	}

	public ArrayList<ConstructorDoc> getConstructors() {
		return constructors;
	}

	public ArrayList<MethodDoc> getMethods() {
		return methods;
	}

	public ArrayList<FieldDoc> getFields() {
		return fields;
	}

}