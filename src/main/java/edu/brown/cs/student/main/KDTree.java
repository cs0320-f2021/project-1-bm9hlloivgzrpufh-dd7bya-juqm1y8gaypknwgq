package edu.brown.cs.student.main;

import java.util.ArrayList;
import java.util.List;

public class KDTree {
  private dNode root;

  public KDTree(dNode root) {
    root.setDimension(0);
    this.root = root;
  }

  public dNode getRoot() {
    return (this.root);
  }

  public void buildTree(ArrayList<dNode> nodeList) {
    ArrayList<dNode> priorityQ = new ArrayList<dNode>();
    priorityQ.add(this.root);
    nodeList.remove(this.root);

    for (dNode node : nodeList) {
      int weight = node.getWeight();
      int height = node.getHeight();
      int age = node.getAge();
      for (dNode treeNode : priorityQ) {
        int checkWeight = treeNode.getWeight();
        int checkHeight = treeNode.getHeight();
        int checkAge = treeNode.getAge();
        int dim = treeNode.getDimension();
        //System.out.println(dim);
        if (dim == 0) {
          if (weight < checkWeight && treeNode.getLeft() == null) {
            treeNode.setLeft(node);
            node.setDimension(nextDim(dim));

            int treeNodeIndex = priorityQ.indexOf(treeNode);
            priorityQ.add(treeNodeIndex + 1, node);

            // remove tree node from priority queue if both left and right are filled
            removeFromQ(treeNode, priorityQ);
            break;
          } else if (weight >= checkWeight && treeNode.getRight() == null) {
            treeNode.setRight(node);
            node.setDimension(nextDim(dim));

            int treeNodeIndex = priorityQ.indexOf(treeNode);
            priorityQ.add(treeNodeIndex + 1, node);

            // remove tree node from priority queue if both left and right are filled
            removeFromQ(treeNode, priorityQ);
            break;
          }
        } else if (dim == 1) {
          if (height < checkHeight && treeNode.getLeft() == null) {
            treeNode.setLeft(node);
            node.setDimension(nextDim(dim));

            int treeNodeIndex = priorityQ.indexOf(treeNode);
            priorityQ.add(treeNodeIndex + 1, node);

            // remove tree node from priority queue if both left and right are filled
            removeFromQ(treeNode, priorityQ);
            break;
          } else if (height >= checkHeight && treeNode.getRight() == null) {
            treeNode.setRight(node);
            node.setDimension(nextDim(dim));

            int treeNodeIndex = priorityQ.indexOf(treeNode);
            priorityQ.add(treeNodeIndex + 1, node);

            // remove tree node from priority queue if both left and right are filled
            removeFromQ(treeNode, priorityQ);
            break;
          }
        } else if (dim == 2) {
            if (age < checkAge && treeNode.getLeft() == null) {
              treeNode.setLeft(node);
              node.setDimension(nextDim(dim));

              int treeNodeIndex = priorityQ.indexOf(treeNode);
              priorityQ.add(treeNodeIndex + 1, node);

              // remove tree node from priority queue if both left and right are filled
              removeFromQ(treeNode, priorityQ);
              break;
            } else if (age >= checkAge && treeNode.getRight() == null) {
              treeNode.setRight(node);
              node.setDimension(nextDim(dim));

              int treeNodeIndex = priorityQ.indexOf(treeNode);
              priorityQ.add(treeNodeIndex + 1, node);

              // remove tree node from priority queue if both left and right are filled
              removeFromQ(treeNode, priorityQ);
              break;
            }
        }
      }
    }
    //System.out.println(priorityQ);
  }

  public int nextDim(int dim) {
    if (dim == 2) {
      return (0);
    }
    else {
      return (dim + 1);
    }
  }

  public void removeFromQ(dNode treeNode, ArrayList<dNode> priorityQ) {
    if (treeNode.getLeft() != null && treeNode.getRight() != null) {
      priorityQ.remove(treeNode);
    }
  }

  // used for debugging
  public void check(int line, dNode node) {
    System.out.println("      " + line);
    System.out.println(node.getDimension());
    System.out.println(node.getWeight());
    System.out.println(node.getHeight());
    System.out.println(node.getAge());

    if (node.getLeft() != null) {
      check(line + 1, node.getLeft());
    }
    if (node.getRight() != null) {
      check(line + 1, node.getRight());
    }
  }

  public void search(int neighbors, dNode checkNode, ArrayList<dNode> neighborsList,
                                 ArrayList<Integer> target) {

    if (checkNode == null) {
      return;
    } else {
      //System.out.println(checkNode.getWeight());
      //System.out.println(checkNode.calcDistance(target.get(0), target.get(1), target.get(2)));
    }

    if (neighborsList.size() == 0) {
      neighborsList.add(checkNode);
    } else if (neighborsList.size() < neighbors) {
      int size = neighborsList.size();
      double checkDist = checkNode.calcDistance(target.get(0), target.get(1), target.get(2));
      for (dNode neighbor : neighborsList) {
        double dist = neighbor.calcDistance(target.get(0), target.get(1), target.get(2));
        if (checkDist <= dist) {
          int index = neighborsList.indexOf(neighbor);
          neighborsList.add(index, checkNode);
          break;
        }
      }
      if (neighborsList.size() == size) {
        neighborsList.add(checkNode);
      }
    } else {
      double checkDist = checkNode.calcDistance(target.get(0), target.get(1), target.get(2));
      for (dNode neighbor : neighborsList) {
        double dist = neighbor.calcDistance(target.get(0), target.get(1), target.get(2));
        if (checkDist <= dist) {
          int index = neighborsList.indexOf(neighbor);
          neighborsList.remove(neighbors - 1);
          neighborsList.add(index, checkNode);
          //System.out.println("removed a neighbor");
          break;
        }
      }
    }

    int dim = checkNode.getDimension();
    int targetValue = target.get(dim);
    dNode left = checkNode.getLeft();
    dNode right = checkNode.getRight();

    dNode worstNeighbor = neighborsList.get(neighborsList.size() - 1);
    double bound = worstNeighbor.calcDistance(target.get(0), target.get(1), target.get(2));

    if (checkNode.checkDim(targetValue)) {
      if (checkNode.checkRecur(targetValue) <= bound) {
        search(neighbors, left, neighborsList, target);
        search(neighbors, right, neighborsList, target);
      } else {
        search(neighbors, left, neighborsList, target);
      }

    } else {
      if (checkNode.checkRecur(targetValue) <= bound) {
        search(neighbors, left, neighborsList, target);
      }
      search(neighbors, right, neighborsList, target);
    }
  }
}
