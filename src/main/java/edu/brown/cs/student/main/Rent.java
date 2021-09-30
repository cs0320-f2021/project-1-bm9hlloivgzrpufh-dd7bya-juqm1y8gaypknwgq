package edu.brown.cs.student.main;

public class Rent {
  private String fit;
  private int user_id;
  private int item_id;
  private int rating;
  private String rented_for;
  private String category;
  private String size;
  private int id;

  public Rent(String fit, int user_id, int item_id, int rating, String rented_for, String category,
              String size, int id){
    this.fit = fit;
    this.user_id = user_id;
    this.item_id = item_id;
    this.rating = rating;
    this.rented_for = rented_for;
    this.category = category;
    this.size = size;
    this.id = id;
  }
}
