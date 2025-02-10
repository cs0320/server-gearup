package edu.brown.cs.student.main.activity;

/**
 * This is a class that models an Activity received from the BoredAPI. It doesn't have a lot but
 * there are a few fields that you could filter on if you wanted!
 */
public class Activity {
  private String activity;
  private String type;
  private int participants;


  public Activity() {}

  @Override
  public String toString() {
    // If the activity involves only 1 person, we use "1 person" instead of "1 people" for correct grammar
    if (this.participants == 1) {
      return this.activity + " with 1 person.";
    } else {
      // For multiple participants, we use "X people" to reflect the total number of participants
      return this.activity + " with " + this.participants + " people.";
    }
  }



}