package edu.brown.cs.student.main;

public class User {
  private int user_id;
  private int weight;
  private String bust_size;
  private String height;
  private int age;
  private String body_type;
  private String horoscope;

  public User(int user_id, int weight, String bust_size, String height, int age, String body_type,
              String horoscope){
    this.user_id = user_id;
    this.weight = weight;
    this.bust_size = bust_size;
    this.height = height;
    this.age = age;
    this.body_type = body_type;
    this.horoscope = horoscope;
  }
}
