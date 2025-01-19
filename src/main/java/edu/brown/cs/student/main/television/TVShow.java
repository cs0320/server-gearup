package edu.brown.cs.student.main.television;

/**
 * This is a class that models a TVShow from our API. It doesn't have a lot but
 * there are a few fields that you could filter on if you wanted!
 */
// Main TVShow class
public class TVShow {

  // Using Postman, examine the response JSon. When looking at the raw response, notice that there are many additional fields beyond name. 
  // Moshi allows us to pick just the fields we want to examine from our JSON and omit the rest.

  // TODO 1.2: Include a summary of the TVShow in addition to the name. Update toString() to display this summary.

  public String name;

  public TVShow() {}

  @Override
  public String toString() {
    return "The TV Show is " + this.name;
  }
}