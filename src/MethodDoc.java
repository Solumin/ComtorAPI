import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.ArrayList;
import java.util.List;

public class MethodDoc implements CallableMemberDoc {


	private String name;
	private int lineNumber;

	private PackageDoc pkgDoc;
	private ClassDoc classDoc;

	private ArrayList<String> modifiers;
	private ArrayList<String> params;
	private ArrayList<String> exceptions;
	private String type;

	private CommentDoc comment;
	private CommonTree body;

	public MethodDoc(CommonTree root) {
		name = root.getChild(0).getText();
		lineNumber = root.getLine();

		modifiers = new ArrayList<String>();
		params = new ArrayList<String>();
		exceptions = new ArrayList<String>();

		List atts = root.getChildren();

		for (int i = 0; i < atts.size(); i++) {
			CommonTree att = (CommonTree)atts.get(i);

			switch (att.getText()) {
				case "ACCESS_MODIFIER":
					List accMods = att.getChildren();
					for (int j = 0; j < accMods.size(); j++) {
						CommonTree acc = (CommonTree)accMods.get(j);
						modifiers.add(acc.getText());
					}
					break;
				case "TYPE":
					type = att.getChild(0).getText();
					break;
				case "TYPE_PARAMS":
					name += "<";
					List typeParams = att.getChildren();
					for (int j = 0; j < typeParams.size(); j++) {
						CommonTree type = (CommonTree)typeParams.get(j);
						name += type.getText();
					}
					name = name.substring(0, -2) + ">";
					break;
				case "PARAMS":
					List paramTree = att.getChildren();
					for (int j = 0; j < paramTree.size(); j+=2) {
						CommonTree param = (CommonTree)paramTree.get(j);
						CommonTree paramType = (CommonTree)paramTree.get(j+1);
						paramType = (CommonTree)paramType.getChild(0);
						params.add(paramType.getText() + " " + param.getText());
					}
					break;
				case "THROWS":
					List excepts = att.getChildren();
					for (int j = 0; j < excepts.size(); j++) {
						CommonTree except = (CommonTree)excepts.get(j);
						exceptions.add(except.getText());
					}
					break;
				case "BODY":
					body = att;
					break;
				case "COMMENT_STATEMENT":
					comment = new CommentDoc(att);
					break;
			}
		}
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

	public PackageDoc getPackageDoc() {
		return pkgDoc;
	}

	public void setPackageDoc(PackageDoc pkg) {
		pkgDoc = pkg;
	}

	public CommentDoc getComment() {
		return comment;
	}

	public ClassDoc getClassDoc() {
		return classDoc;
	}

	public void setClassDoc(ClassDoc cls) {
		classDoc = cls;
	}

	public ArrayList<String> getParams() {
		return params;
	}

	public String getReturnType() {
		return type;
	}

	public ArrayList<String> getExceptions() {
		return exceptions;
	}
}