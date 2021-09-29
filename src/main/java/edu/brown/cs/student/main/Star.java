package edu.brown.cs.student.main;

public class Star {
  private int id;
  private String name;
  private double x;
  private double y;
  private double z;

  public Star(int id, String name, double x, double y, double z) {
    this.id = id;
    this.name = name;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public double distance(double targetX, double targetY, double targetZ) {
    double xCoord = java.lang.Math.pow(x - targetX, 2);
    double yCoord = java.lang.Math.pow(y - targetY, 2);
    double zCoord = java.lang.Math.pow(z - targetZ, 2);

    return java.lang.Math.sqrt(xCoord + yCoord + zCoord);
  }

  public int getID() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public double getX() {
    return this.x;
  }

  public double getY() {
    return this.y;
  }

  public double getZ() {
    return this.z;
  }
}
