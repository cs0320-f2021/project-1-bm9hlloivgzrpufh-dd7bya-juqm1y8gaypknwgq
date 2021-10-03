package edu.brown.cs.student.main;

public class users {
  private int user_id;
  private int weight;
  private String bust_size;

  public users(int user_id, int weight, String bust_size, String height, int age,
               String body_type, String horoscope) {
    this.user_id = user_id;
    this.weight = weight;
    this.bust_size = bust_size;
    this.height = height;
    this.age = age;
    this.body_type = body_type;
    this.horoscope = horoscope;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public String getBust_size() {
    return bust_size;
  }

  public void setBust_size(String bust_size) {
    this.bust_size = bust_size;
  }

  public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getBody_type() {
    return body_type;
  }

  public void setBody_type(String body_type) {
    this.body_type = body_type;
  }

  public String getHoroscope() {
    return horoscope;
  }

  public void setHoroscope(String horoscope) {
    this.horoscope = horoscope;
  }

  private String height;
  private int age;
  private String body_type;
  private String horoscope;

  public users(){
  }
}
