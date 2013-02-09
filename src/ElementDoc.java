import java.util.ArrayList;

public interface ElementDoc extends Doc {

	public ArrayList<String> getModifiers();

	public PackageDoc getPackageDoc();
	public void setPackageDoc(PackageDoc pkg);

	public CommentDoc getComment();

}