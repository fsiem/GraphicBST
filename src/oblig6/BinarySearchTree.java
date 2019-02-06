package oblig6;

/**
 *
 * @author Frode Siem 151332
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {

    protected BinaryNode<AnyType> root;

    public BinarySearchTree() {
        root = null;
    }

    public void insert(AnyType x) {

        root = insert(x, root);
    }

    public void remove(AnyType x) {
        root = remove(x, root);
    }

    public void removeMin() {
        root = removeMin(root);
    }

    public AnyType findMin() {
        return elementAt(findMin(root));
    }

    public AnyType findMax() {
        return elementAt(findMax(root));
    }

    public AnyType find(AnyType x) {
        return elementAt(find(x, root));
    }

    public void makeEmpty() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    private AnyType elementAt(BinaryNode<AnyType> t) {
        return t == null ? null : t.element;
    }

    private BinaryNode<AnyType> find(AnyType x, BinaryNode<AnyType> t) {
        while (t != null) {
            if (x.compareTo(t.element) < 0) {
                t = t.left;
            } else if (x.compareTo(t.element) > 0) {
                t = t.right;
            } else {
                return t; // Match
            }
        }
        return null; // Not found
    }

    protected BinaryNode<AnyType> findMin(BinaryNode<AnyType> t) {
        if (t != null) {
            while (t.left != null) {
                t = t.left;
            }
        }
        return t;
    }

    protected BinaryNode<AnyType> findMax(BinaryNode<AnyType> t) {
        if (t != null) {
            while (t.right != null) {
                t = t.right;
            }
        }

        return t;
    }

    protected BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t) {

        if (t == null) {
            t = new BinaryNode<>(x);
        } else if (x.compareTo(t.element) < 0) {
            t.left = insert(x, t.left);

            if (height(t.left) - height(t.right) > 1) {
                if (x.compareTo(t.left.element) < 0) {
                    t = rotateWithLeftChild(t);
                } else {
                    t = doubleWithLeftChild(t);
                }
            }

        } else if (x.compareTo(t.element) > 0) {
            t.right = insert(x, t.right);

            if (height(t.right) - height(t.left) > 1) {
                if (x.compareTo(t.right.element) > 0) {
                    t = rotateWithRightChild(t);
                } else {
                    t = doubleWithRightChild(t);
                }
            }

        } else {

            return t;
        }
        t.height = max(height(t.left), height(t.right)) + 1;
        return t;
    }

    protected BinaryNode<AnyType> removeMin(BinaryNode<AnyType> t) {
        if (t == null) {
            throw new IllegalArgumentException("Null");
        } else if (t.left != null) {
            t.left = removeMin(t.left);
            return t;
        } else {
            return t.right;
        }
    }

    protected BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t) {
        if (t == null) {
            throw new IllegalArgumentException("Null");
        }

        if (x.compareTo(t.element) < 0) {
            t.left = remove(x, t.left);

            if (height(t.left) - height(t.right) > 1) {
                if (x.compareTo(t.left.element) < 0) {
                    t = rotateWithLeftChild(t);
                } else {
                    t = doubleWithLeftChild(t);
                }
            }

        } else if (x.compareTo(t.element) > 0) {
            t.right = remove(x, t.right);

            if (height(t.right) - height(t.left) > 1) {
                if (x.compareTo(t.right.element) > 0) {
                    t = rotateWithRightChild(t);
                } else {
                    t = doubleWithRightChild(t);
                }
            }

        } else if (t.left != null && t.right != null) { //Two children
            t.element = findMin(t.right).element;
            t.right = removeMin(t.right);
        } else {
            t = (t.left != null) ? t.left : t.right;
        }
        return t;
    }

    /* Roterer binær tre node med venstre barn */
    private BinaryNode rotateWithLeftChild(BinaryNode k2) {
        BinaryNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = max(height(k2.left), height(k2.right)) + 1;
        k1.height = max(height(k1.left), k2.height) + 1;
        return k1;
    }

    /* Roterer binær tre node med høyre barn */
    private BinaryNode rotateWithRightChild(BinaryNode k1) {
        BinaryNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = max(height(k1.left), height(k1.right)) + 1;
        k2.height = max(height(k2.right), k1.height) + 1;
        return k2;
    }
    
        private int height(BinaryNode t) {
        return t == null ? -1 : t.height;
    }

    private int max(int lhs, int rhs) {
        return lhs > rhs ? lhs : rhs;
    }

    /**
     * Dobbel roterer binær tree node: først venstre barn med sitt høyre barn så
     * node k3 med den nye venstre barn noden.
     */
    private BinaryNode doubleWithLeftChild(BinaryNode k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    /**
     * Dobbel roterer binær tree node: først høyre barn med sitt venstre barn så
     * node k1 med den nye høyre barn noden.
     *
     */
    private BinaryNode doubleWithRightChild(BinaryNode k1) {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }

}
