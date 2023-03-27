
public class AVLTree<T extends Comparable> {

	private AVLNode<T> root;

	public AVLTree(AVLNode root) {
		this.root = root;
	}

	public AVLTree() {
		this.root = null;
	}

	public void setRoot(AVLNode root) {
		this.root = root;
	}

	public boolean isEmpty() {
		if (root == null) {
			return true;
		} else {
			return false;
		}
	}

	public int getNumberOfNodes() {
		if (this.root.getLeftChild().equals(null) && this.root.getRightChild().equals(null)) {
			return 1;
		} else if (this.root.getRightChild().equals(null)) {
			AVLTree leftTree = new AVLTree(this.root.getLeftChild());
			return leftTree.getNumberOfNodes() + 1;
		} else if (this.root.getLeftChild().equals(null)) {
			AVLTree rightTree = new AVLTree(this.root.getRightChild());
			return rightTree.getNumberOfNodes() + 1;
		} else {
			AVLTree leftTree = new AVLTree(this.root.getLeftChild());
			AVLTree rightTree = new AVLTree(this.root.getRightChild());
			return leftTree.getNumberOfNodes() + rightTree.getNumberOfNodes() + 1;
		}
	}

	public int getHeight() {
		return this.root.getHeight();
	}

	public void clear() {
		this.root = null;
	}

	public boolean contains(T entry) {
		AVLNode temp = this.root;
		while (!temp.getData().equals(entry)) {
			if (temp.getData().compareTo(entry) < 0) {
				if (!temp.getRightChild().equals(null)) {
					temp = temp.getRightChild();
				} else {
					return false;
				}
			} else {
				if (!temp.getLeftChild().equals(null)) {
					temp = temp.getLeftChild();
				} else {
					return false;
				}
			}
		}
		return true;
	}

	public void insert(T entry) {
		AVLNode newEntry = new AVLNode(entry);

		if (this.isEmpty()) {
			this.root = newEntry;
		} else {
			AVLNode temp = this.root;
			while (newEntry.getParent() == null) {
				if (temp.getData().compareTo(entry) <= 0) {
					if (temp.getRightChild() != null) {
						temp = temp.getRightChild();
					} else {
						temp.setRightChild(newEntry);
						newEntry.setParent(temp);
					}
				} else {
					if (temp.getLeftChild() != null) {
						temp = temp.getLeftChild();
					} else {
						temp.setLeftChild(newEntry);
						newEntry.setParent(temp);
					}
				}
			}
		}

		// update heights & find rotation point
		int balance = 0;
		int oldHeight;
		AVLNode rotation = null;
		while (newEntry.getParent() != null) {
			oldHeight = newEntry.getParent().getHeight();
			newEntry.getParent().computeHeight();
			if (oldHeight == newEntry.getParent().getHeight()) {
				break;
			} else {
				newEntry = newEntry.getParent();
				balance = newEntry.getBalance();
				if ((balance == 2 || balance == -2) && rotation == null) {
					rotation = newEntry;
				}
			}
		}

		if (rotation != null) {
			if (rotation.getBalance() == 2) {
				if (rotation.getLeftChild().getData().compareTo(entry) > 0) {
					this.rrRotate(rotation);
				} else {
					this.lrRotate(rotation);
				}
			} else {
				if (rotation.getRightChild().getData().compareTo(entry) > 0) {
					this.rlRotate(rotation);
				} else {
					this.llRotate(rotation);
				}
			}
		}

	}

	private void llRotate(AVLNode r) {

		if (r.equals(this.root)) {
			this.setRoot(r.getRightChild());
			if (this.root.getLeftChild() != null) {
				r.setRightChild(this.root.getLeftChild());
				this.root.getLeftChild().setParent(r);
			} else {
				r.setRightChild(null);
			}
			this.root.setLeftChild(r);
			this.root.setParent(null);
			r.setParent(this.root);
		} else {
			AVLNode p = r.getParent();
			AVLNode c = r.getRightChild();
			if (r.equals(p.getLeftChild())) {
				p.setLeftChild(c);
			} else {
				p.setRightChild(c);
			}
			c.setParent(p);
			if (c.getLeftChild() != null) {
				r.setRightChild(c.getLeftChild());
				c.getLeftChild().setParent(r);
			} else {
				r.setRightChild(null);
			}
			c.setLeftChild(r);
			r.setParent(c);
		}

		r.computeHeight();
		while (r.getParent() != null) {
			r = r.getParent();
			r.computeHeight();
		}
	}

	private void rrRotate(AVLNode r) {
		if (r.equals(this.root)) {
			this.setRoot(r.getLeftChild());
			if (this.root.getRightChild() != null) {
				r.setLeftChild(this.root.getRightChild());
				this.root.getRightChild().setParent(r);
			} else {
				r.setLeftChild(null);
			}
			this.root.setRightChild(r);
			this.root.setParent(null);
			r.setParent(this.root);
		} else {
			AVLNode p = r.getParent();
			AVLNode c = r.getLeftChild();
			if (r.equals(p.getLeftChild())) {
				p.setLeftChild(c);
			} else {
				p.setRightChild(c);
			}
			c.setParent(p);
			if (c.getRightChild() != null) {
				r.setLeftChild(c.getRightChild());
				c.getRightChild().setParent(r);
			} else {
				r.setLeftChild(null);
			}
			c.setRightChild(r);
			r.setParent(c);
		}
		r.computeHeight();
		while (r.getParent() != null) {
			r = r.getParent();
			r.computeHeight();
		}
	}

	private void lrRotate(AVLNode r) {
		this.llRotate(r.getLeftChild());
		this.rrRotate(r);
		// left rotation of left subtree
		// right rotation of tree
		r = r.getParent();
		r.getLeftChild().computeHeight();
		r.getRightChild().computeHeight();
		r.computeHeight();
		while (r.getParent() != null) {
			r = r.getParent();
			r.computeHeight();
		}
	}

	private void rlRotate(AVLNode r) {
		this.rrRotate(r.getRightChild());
		this.llRotate(r);
		// right rotation of right subtree
		// left rotation of tree
		r = r.getParent();
		r.getLeftChild().computeHeight();
		r.getRightChild().computeHeight();
		r.computeHeight();
		while (r.getParent() != null) {
			r = r.getParent();
			r.computeHeight();
		}
	}

	private void inOrderTraversal(AVLNode n) {

		if (n == null) {
			return;
		}

		// Recursively traverse the left child of node "n"
		inOrderTraversal(n.getLeftChild());

		// print node "n"
		System.out.println(n.getData() + " ");

		// Recursively traverse the right child of node "n"
		inOrderTraversal(n.getRightChild());

	}

	public void inOrderTraversal() {
		inOrderTraversal(this.root);
	}

	public void levelOrderTraversal() {

		// Obtain height of AVLtree
		int treeHeight = root.getHeight();

		// Iterate through each level of AVLtree (beginning with first level) and print
		// the nodes in each level to the console
		for (int i = 1; i <= treeHeight; i++) {
			System.out.println("");
			System.out.print("Level " + "(" + i + ")" + " ");

			printLevel(this.root, i);
		}
	}

	private void printLevel(AVLNode n, int level) {

		if (n == null) {
			return;
		}

		// print the root
		if (level == 1) {
			System.out.print(n.getData() + " ");
		}

		// print the left subtrees and right subtrees
		else {
			printLevel(n.getLeftChild(), level - 1);
			printLevel(n.getRightChild(), level - 1);
		}
	}

	private class AVLNode<T extends Comparable> {

		private T data;
		private AVLNode parent;
		private AVLNode leftChild;
		private AVLNode rightChild;
		private int leftHeight;
		private int rightHeight;
		private int height;

		public AVLNode(T data) {
			this.data = data;
			this.height = 1;
			this.leftHeight = 0;
			this.rightHeight = 0;
		}

		public T getData() {
			return this.data;
		}

		public void setData(T data) {
			this.data = data;
		}

		public AVLNode getParent() {
			return this.parent;
		}

		public void setParent(AVLNode parent) {
			this.parent = parent;
		}

		public AVLNode getLeftChild() {
			return this.leftChild;
		}

		public void setLeftChild(AVLNode child) {
			this.leftChild = child;
		}

		public AVLNode getRightChild() {
			return this.rightChild;
		}

		public void setRightChild(AVLNode child) {
			this.rightChild = child;
		}

		public int getLeftHeight() {
			return this.leftHeight;
		}

		public int getRightHeight() {
			return this.rightHeight;
		}

		public int getHeight() {
			return this.height;
		}

		private void resetHeights() {
			if (this.getLeftChild() == null) {
				this.leftHeight = 0;
			} else {
				this.leftHeight = this.getLeftChild().getHeight();
			}
			if (this.getRightChild() == null) {
				this.rightHeight = 0;
			} else {
				this.rightHeight = this.getRightChild().getHeight();
			}
		}

		public void computeHeight() {
			this.resetHeights();
			if (this.leftHeight > this.rightHeight) {
				this.height = this.leftHeight + 1;
			} else {
				this.height = this.rightHeight + 1;
			}
		}

		public int getBalance() {
			return this.leftHeight - this.rightHeight;
		}

		public int getRealHeight() {
			if (this.leftChild.equals(null) && this.rightChild.equals(null)) {
				return 1;
			} else if (this.rightChild.equals(null)) {
				return this.leftChild.getRealHeight() + 1;
			} else if (this.leftChild.equals(null)) {
				return this.rightChild.getRealHeight() + 1;
			} else {
				if (this.rightChild.getRealHeight() > this.leftChild.getRealHeight()) {
					return this.rightChild.getRealHeight() + 1;
				} else {
					return this.leftChild.getRealHeight() + 1;
				}
			}
		}

	}

}
