package oblig6;

/**
 *
 * @author Frode Siem
 */
public class BinaryNode<AnyType> {
    
    AnyType element;
    int height;
    BinaryNode<AnyType> left;
    BinaryNode<AnyType> right;
    
   public BinaryNode(AnyType x) {
       this.element = x;
       left = null;
       right = null;
       height = 0;
   }
    
}
