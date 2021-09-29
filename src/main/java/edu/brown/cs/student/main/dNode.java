package edu.brown.cs.student.main;

public class dNode {
  private final int id;
  private final int weight;
  private final int height;
  private final int age;
  private final String horoscope;

  private dNode left;
  private dNode right;

  private int dimension;

  public dNode(int id, int weight, int height, int age, String horoscope) {
    this.id = id;
    this.weight = weight;
    this.height = height;
    this.age = age;
    this.horoscope = horoscope;
    this.left = null;
    this.right = null;
  }

  public void setDimension(int dim) {
    this.dimension = dim;
  }

  public void setLeft(dNode node) {
    this.left = node;
  }

  public void setRight(dNode node) {
    this.right = node;
  }

  public int getID() {
    return this.id;
  }

  public int getWeight() {
    return this.weight;
  }

  public int getHeight() {
    return this.height;
  }

  public int getAge() {
    return this.age;
  }

  public int getDimension() {
    return this.dimension;
  }

  public dNode getLeft() {
    return this.left;
  }

  public dNode getRight() {
    return this.right;
  }

  public String getHoroscope() {
    return this.horoscope;
  }

  public boolean checkDim(int value) {
    if (this.dimension == 0) {
      return (value < this.weight);
    } else if (this.dimension == 1) {
      return (value < this.height);
    } else {
      return (value < this.age);
    }
  }

  public double checkRecur(int value) {
    if (this.dimension == 0) {
      return (value - this.weight);
    } else if (this.dimension == 1) {
      return (value - this.height);
    } else {
      return (value - this.age);
    }
  }

  public double calcDistance(int weight, int height, int age) {
    double sum = Math.pow(this.weight - weight, 2) + Math.pow(this.height -  height, 2) + Math.pow(this.age - age, 2);
    return (java.lang.Math.sqrt(sum));
  }
}
