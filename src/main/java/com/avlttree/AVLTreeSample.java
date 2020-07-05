package com.avlttree;

/**
 * 平衡二叉树， 参考类
 * Created by bella on 2017/12/6.
 */
public class AVLTreeSample<T extends Comparable<T>> {

    //树节点
    static class AVLTreeNode<T extends Comparable<T>>{
        T item;
        int height;//高度
        AVLTreeNode<T> left;//左子树
        AVLTreeNode<T> right;//右子树

        public AVLTreeNode(T item, AVLTreeNode<T> left, AVLTreeNode<T> right) {
            this.item = item;
            this.left = left;
            this.right = right;
            this.height=0;
        }
    }

    private AVLTreeNode<T> root; // 根节点

    /**
     * 添加数据
     * @param item
     */
    public void add(T item){
        if (root == null) {
            root=new AVLTreeNode<>(item,null,null);
        }
        root=innert(root,item);
    }

    /**
     * 删除数据
     * @param item
     */
    public void remove(T item){
        if(root==null){
            throw new IllegalArgumentException("没有数据！");
        }
        root=delete(root,item);
    }


    /**
     * 获取高度
     * @param node
     * @return
     */
    public int height(AVLTreeNode<T> node){
      return node!=null?node.height:0;
    }

    /**
     * 查询最小值
     * @param node
     * @return
     */
    private T findMin(AVLTreeNode<T> node){
        return node.left!=null?findMin(node.left):node.item;
    }



    /**
     * LL旋转
     * @param unbalanceNode
     * @return
     */
    private AVLTreeNode<T> leftLeftRotation(AVLTreeNode<T> unbalanceNode){
        AVLTreeNode<T> left=unbalanceNode.left;
        AVLTreeNode<T> right=left.right;
        left.right=unbalanceNode;
        unbalanceNode.left=right;
        unbalanceNode.height=Math.max(unbalanceNode.right.height,unbalanceNode.left.height)+1;
        left.height=Math.max(left.right.height,left.right.height)+1;
        return left;
    }

    /**
     * RR旋转
     * @param unbalanceNode
     * @return
     */
    private AVLTreeNode<T> rightRightRotation(AVLTreeNode<T> unbalanceNode){
      AVLTreeNode<T> right=unbalanceNode.right;
      AVLTreeNode<T> left=right.left;
      right.left=unbalanceNode;
      unbalanceNode.right=left;
      unbalanceNode.height=Math.max(unbalanceNode.right.height,unbalanceNode.left.height)+1;
      right.height=Math.max(right.right.height,right.left.height);
      return right;
    }

    /**
     * LR旋转
     * @param unbalanceNode
     */
    private AVLTreeNode<T> leftRightRotation(AVLTreeNode<T> unbalanceNode){
        unbalanceNode.left=rightRightRotation(unbalanceNode.left);
        return leftLeftRotation(unbalanceNode);
    }

    /**
     * RL旋转
     * @param unbalanceNode
     * @return
     */
    private AVLTreeNode<T> rightLeftRotation(AVLTreeNode<T> unbalanceNode){
        unbalanceNode.right=leftLeftRotation(unbalanceNode.right);
        return rightRightRotation(unbalanceNode);
    }

    /**
     * 插入
     * @param item
     * @return
     */
    private AVLTreeNode<T> innert(AVLTreeNode<T> parentNode,T item){
        int tempVale=parentNode.item.compareTo(item);
        if(tempVale<0){ //left 节点
            parentNode.left=parentNode.left!=null?innert(parentNode.left,item):new AVLTreeNode<>(item,null,null);
            if(height(parentNode.left)-height(parentNode.right)==2){
                if(height(parentNode.left.right)>height(parentNode.left.left)){
                  parentNode=leftLeftRotation(parentNode);
                }else{
                    parentNode=leftRightRotation(parentNode);
                }
            }
        }else if(tempVale>0){
            parentNode.right=parentNode.right!=null?innert(parentNode.right,item):new AVLTreeNode<>(item,null,null);
            if(height(parentNode.right) - height(parentNode.left) == 2){
                if(height(parentNode.right.right) > height(parentNode.right.left)){
                    parentNode=rightRightRotation(parentNode);
                }else{
                    parentNode=rightLeftRotation(parentNode);
                }
            }
        }else{
            // throw new IllegalArgumentException("已有相同节点，"+item);
        }
        parentNode.height=Math.max(height(parentNode.left),height(parentNode.left))+1;
        return parentNode;
    }

    /**
     * 删除节点
     * @param parentNode
     * @param item
     * @return
     */
     private AVLTreeNode<T> delete(AVLTreeNode<T> parentNode,T item){
        int tempVale=parentNode.item.compareTo(item);
        if(tempVale<0&&parentNode.left!=null){
            parentNode= delete(parentNode.left,item);
            if(height(parentNode.left)-height(parentNode.right)==2){
                if(height(parentNode.left.right)>height(parentNode.left.left)){
                    parentNode=leftLeftRotation(parentNode);
                }else{
                    parentNode=leftRightRotation(parentNode);
                }
            }
        }else if(tempVale>0&&parentNode.right!=null){
            parentNode= delete(parentNode.right,item);
            if(height(parentNode.right)-height(parentNode.left)==2){
                if(height(parentNode.right.right)>height(parentNode.right.left)){
                    parentNode=rightRightRotation(parentNode);
                }else{
                    parentNode=rightLeftRotation(parentNode);
                }
            }
        }else if(tempVale==0&&parentNode.left!=null&&parentNode.right!=null){
            T tempItem=findMin(parentNode.right);
            parentNode.item=tempItem;
            parentNode.right=delete(parentNode.right,tempItem);
        }else if(tempVale==0){
            parentNode=parentNode.left!=null?parentNode.left:parentNode.right;
        }else{
            throw new IllegalArgumentException("未找到删除节点，"+item);
        }
        parentNode.height=Math.max(parentNode.left.height,parentNode.left.height)+1;
        return parentNode;
    }


}
