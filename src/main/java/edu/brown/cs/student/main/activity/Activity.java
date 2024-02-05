package edu.brown.cs.student.main.activity;

public class Activity {
  private String activity;
  private String type;
  private int participants;
  private double price;
  private int accessibility;
  public Activity(){

  }

  @Override
  public String toString() {
    return this.activity + " with " + this.participants + " people.";
  }
}
