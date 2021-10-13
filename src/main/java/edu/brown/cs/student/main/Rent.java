package edu.brown.cs.student.main;

import java.util.Map;

public class Rent {
  private String fit;
  private String user_id;
  private String item_id;
  private String rating;
  private String rented_for;
  private String category;
  private String size;
  private String id;

  public Rent(){};

  public Rent(Map<String, String> attributes){
    this.fit = attributes.get("fit");
    this.user_id = attributes.get("user_id");
    this.item_id = attributes.get("item_id");
    this.rating = attributes.get("rating");
    this.rented_for = attributes.get("rented_for");
    this.category = attributes.get("category");
    this.size = attributes.get("size");
    this.id = attributes.get("id");
  }

  public String getFit() {
    return this.fit;
  }

  public String getUser_id() {
    return this.user_id;
  }

  public String getItem_id() {
    return this.item_id;
  }

  public String getRating() {
    return this.rating;
  }

  public String getRented_for() {
    return this.rented_for;
  }

  public String getCategory() {
    return this.category;
  }

  public String getSize() {
    return this.size;
  }

  public String getId() {
    return this.id;
  }
}
