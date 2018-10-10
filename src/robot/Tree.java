package robot;

import java.util.*;

public class Tree implements Cloneable{
    TreeNode root = null;


    public class TreeNode {
        private List<TreeNode> childList;

        private int key;
        private char direction;
        //        private TreeNode child;
        private TreeNode parent;
        private boolean close;
        Game myGame;

        public TreeNode(int key, char direction, boolean close, List<TreeNode> childList,
                        TreeNode parent, Game myGame) {
            this.key = key;
            this.childList = childList;
            this.parent = parent;
            this.close = close;
            this.direction = direction;
            this.myGame = myGame;
        }

        public int getKey() {
            return key;
        }

        public Game getMyGame() {
            return myGame;
        }

        public void setClose() {
            close = true;
        }

        public TreeNode getParent() {
            return parent;
        }

        public char getDirection() {
            return direction;
        }

    }

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
        Min = 2147483647;
        minNode = null;
        if (root.childList != null) {
            for (int i = 0; i < root.childList.size(); i++) {
                ci(root.childList.get(i));
            }
        }
        return minNode;
    }

    public void ci(TreeNode newNode) {
        if (newNode.close == false && newNode.key < Min) {
            Min = newNode.key;
            minNode = newNode;
        }
        if (newNode.childList != null) {
            for (int i = 0; i < newNode.childList.size(); i++) {
                ci(newNode.childList.get(i));
            }
        }

    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}