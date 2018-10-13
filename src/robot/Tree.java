package robot;

import java.util.*;

public class Tree {
    TreeNode root = null;

    public void insert(int key, char direction, boolean close, TreeNode parent, Game myGame) {
        TreeNode newNode = new TreeNode(key, direction, close, null, parent, myGame);
        if (root == null) {
            root = newNode;
            return;
        } else {
            if (newNode.parent.childList == null) {
                newNode.parent.childList = new ArrayList<TreeNode>();
            }
            newNode.parent = parent;
            newNode.parent.childList.add(newNode);
        }
    }

    int Min;
    TreeNode minNode;

    public TreeNode MinNode() {
        Min = 100000;
        minNode = null;
        if (root.childList != null) {
            for (int i = 0; i < root.childList.size(); i++) {
                getNodes(root.childList.get(i));
            }
        }
        return minNode;
    }

    public void getNodes(TreeNode newNode) {
        if (newNode.close == false && newNode.key < Min) {
            Min = newNode.key;
            minNode = newNode;
        }
        if (newNode.childList != null) {
            for (int i = 0; i < newNode.childList.size(); i++) {
                getNodes(newNode.childList.get(i));
            }
        }

    }

}