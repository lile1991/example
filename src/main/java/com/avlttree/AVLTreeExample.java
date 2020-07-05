package com.avlttree;

/**
 * 二叉平衡树, 实践类
 * @param <T>
 */
public class AVLTreeExample<T extends Comparable<T>> {
    private AVLNode<T> root;

    public static class AVLNode<T> {
        T value;
        AVLNode<T> left, right;
        int height = 1;

        public AVLNode(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "value=[" + value + "], height=[" + height + "]";
        }
    }

    public void add(T value) {
        AVLNode<T> newNode = new AVLNode<>(value);
        if(root == null) {
            root = newNode;
        }

        insert(null, root, newNode);
    }

    private void insert(AVLNode<T> parentParent, AVLNode<T> parent, AVLNode<T> newNode) {
        int compare = newNode.value.compareTo(parent.value);
        if(compare < 0) {
            // 新节点挂左边
            if(parent.left == null) {
                parent.left = newNode;
            } else {
                insert(parent, parent.left, newNode);
            }

            if(height(parent.left) - height(parent.right) == 2) {
                // 左高右低， 一次旋转即可（LL）
                if(height(parent.left.right) < height(parent.left.left)) {
                    // LL调整, 往右旋转
                    ll(parentParent, parent);
                } else {
                    // LR调整, 先把right左旋再右旋
                    lr(parentParent, parent);
                }
            }
        } else if(compare > 0) {
            // 新节点挂右边
            if(parent.right == null) {
                parent.right = newNode;
            } else {
                insert(parent, parent.right, newNode);
            }

            if(height(parent.right) - height(parent.left) == 2) {
                // 左低右高， 一次旋转即可（RR）
                if(height(parent.right.left) < height(parent.right.right)) {
                    rr(parentParent, parent);
                } else {
                    rl(parentParent, parent);
                }
            }
        } else {
            System.out.printf("节点值%s已存在", newNode);
        }

        parent.height = Math.max(height(parent.left), height(parent.right)) + 1;
    }

    /**
     * RR调整， 向左旋转
     * @param parentParent parent的上级节点
     * @param parent 要调整的上级节点
     */
    private void rr(AVLNode<T> parentParent, AVLNode<T> parent) {
        // right就是新的父节点
        AVLNode<T> right = parent.right;

        // right.left节点移动到parent.right
        parent.right = right.left;
        // parent变成right.left子节点
        right.left = parent;

        if(parentParent == null) {
            // right取代root
            root = right;
        } else {
            // right取代parentParent的[left/right]位置
            if (parentParent.left == parent) {
                parentParent.left = right;
            } else {
                parentParent.right = right;
            }
        }

        // 重算子树的高度
        resetHeight(right.right);
        resetHeight(right.left);
        resetHeight(right);
    }

    /**
     * LL调整, 向右旋转
     * @param parentParent parent的上级节点
     * @param parent 要调整的节点
     */
    private void ll(AVLNode<T> parentParent, AVLNode<T> parent) {
        // left就是新的父节点
        AVLNode<T> left = parent.left;

        // left.right节点移动到parent.left上
        parent.left = left.right;
        // parent变成left.right子节点
        left.right = parent;

        if(parentParent == null) {
            // left取代root
            root = left;
        } else {
            // left取代parentParent的[left/right]位置
            if (parentParent.left == parent) {
                parentParent.left = left;
            } else {
                parentParent.right = left;
            }
        }

        // 重算子树的高度
        resetHeight(left.right);
        resetHeight(left.left);
        resetHeight(left);
    }

    private void resetHeight(AVLNode<T> node) {
        if(node == null) {
            return;
        }
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    private void lr(AVLNode<T> parentParent, AVLNode<T> parent) {
        rr(parent, parent.left);
        ll(parentParent, parent);
    }
    private void rl(AVLNode<T> parentParent, AVLNode<T> parent) {
        ll(parent, parent.right);
        rr(parentParent, parent);
    }

    private int height(AVLNode<T> node) {
        return node == null ? 0 : node.height;
    }
}
