import java.util.ArrayList;

public interface ElementDoc extends Doc {

	public ArrayList<String> getModifiers();

	public PackageDoc getPackage();

	public CommentDoc getComment();

}