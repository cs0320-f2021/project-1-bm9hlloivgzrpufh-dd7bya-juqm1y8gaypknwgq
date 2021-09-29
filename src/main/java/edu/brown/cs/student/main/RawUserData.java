package edu.brown.cs.student.main;

public class RawUserData {
  private final int id;
  private final String weight;
  private final String height;
  private final int age;
  private final String horoscope;

  public RawUserData(int id, String weight, String height, int age, String horoscope) {
    this.id = id;
    this.weight = weight;
    this.height = height;
    this.age = age;
    this.horoscope = horoscope;
  }

  public int getID() {
    return this.id;
  }

  public String getWeight() {
    return this.weight;
  }

  public String getHeight() {
    return this.height;
  }

  public int getAge() {
    return this.age;
  }

  public String getHoroscope() {
    return this.horoscope;
  }

  public int cleanWeight() {
    String strWeight = this.weight.replace("lbs", "");
    return (Integer.parseInt(strWeight));
  }

  public int cleanHeight() {
    String[] heightParts = this.height.split("\' ");
    heightParts[1] = heightParts[1].replace("\"", "");

    int ft = Integer.parseInt(heightParts[0]);
    int in = Integer.parseInt(heightParts[1]);

    return (ft * 12 + in);
  }
}
