import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Testing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			Scanner sc = new Scanner(new File("src/StateCapitals.txt"));

			AVLTree<String> test = new AVLTree<String>();
			while (sc.hasNextLine()) {
				test.insert(sc.nextLine());
			}

			System.out.println("Output State Capitals Using LevelOrder Traversal: ");
			test.levelOrderTraversal();
			System.out.println("");
			System.out.println("");

			System.out.println("Output State Capitals Using inOrder Traversal: ");
			test.inOrderTraversal();
			System.out.println("");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
