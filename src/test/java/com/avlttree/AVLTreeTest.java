package com.avlttree;

public class AVLTreeTest {
    public static void main(String[] args) {
        // 模拟RR
        AVLTreeExample<Integer> avlTree = new AVLTreeExample<>();
        avlTree.add(6);
        avlTree.add(5);
        avlTree.add(8);
        avlTree.add(7);
        avlTree.add(9);
        avlTree.add(10);
        System.out.println(avlTree);

        // 模拟LR
        avlTree = new AVLTreeExample<>();
        avlTree.add(6);
        avlTree.add(7);
        avlTree.add(3);
        avlTree.add(2);
        avlTree.add(5);
        avlTree.add(4);
        System.out.println(avlTree);
    }
}
