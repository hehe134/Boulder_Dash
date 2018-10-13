package robot;

import java.util.List;

public class TreeNode implements Cloneable {
    List<TreeNode> childList;

    int key;
    char direction;
    //        private TreeNode child;
    TreeNode parent;
    boolean close;
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        TreeNode newTreeNode=(TreeNode) super.clone();
        newTreeNode.myGame=(Game)this.myGame.clone();
        return super.clone();
    }
}
