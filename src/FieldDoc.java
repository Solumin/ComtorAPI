import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;
import java.util.ArrayList;
import java.util.List;

public class FieldDoc implements MemberDoc {

	private String name;
	private int lineNumber;

	private PackageDoc pkgDoc;
	private CommentDoc comment;
	private ClassDoc classDoc;

	private ArrayList<String> modifiers;
	private String type;
	private String value;

	public FieldDoc(CommonTree root) {
		name = root.getChild(0).getText();
		lineNumber = root.getLine();

		modifiers = new ArrayList<String>();

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
				case "INITIAL_VALUE":
					value = att.getChild(0).getText();
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

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public String toString() {
		return getType() + " " + getName();
	}

}