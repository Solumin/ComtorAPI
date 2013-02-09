import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

public class TestDoclet extends Doclet {
	public Boolean start(RootDoc rootDoc) {
		System.out.println("Succesfully passed rootDoc to TestDoclet.start.");
		System.out.println("It has " + rootDoc.getClasses().size() + " class(es)!");
		return true;
	}
}