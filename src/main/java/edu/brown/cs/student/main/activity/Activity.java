package edu.brown.cs.student.main.activity;

/**
 * This is a class that models an Activity received from the BoredAPI. It doesn't have a lot but
 * there are a few fields that you could filter on if you wanted!
 */
public class Activity {
  private String temperature;
  private String wind;
  private String description;
  private Object forecast;

  public Activity() {}

  @Override
  public String toString() {
    return "The temperature is " + this.temperature;
  }

  // TODO 1.1: Replace the top function with this one below once you have modified the URI in TODO 1

  //  @Override
  //  public String toString() {
  //  return "key " + this.key + " corresponds to this activity: " + this.activity;
  //  }
}
