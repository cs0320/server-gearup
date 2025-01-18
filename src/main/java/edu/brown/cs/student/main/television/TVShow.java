package edu.brown.cs.student.main.television;

/**
 * This is a class that models an Activity received from the BoredAPI. It doesn't have a lot but
 * there are a few fields that you could filter on if you wanted!
 */
// Main TVShow class
public class TVShow {

  //When looking at the raw response, notice that there are many additional fields beyond name and
  //summary. Moshi allows us to pick just the fields we want to examine from our JSON, in this case,
  //just name and summary.

  public String name;
  public String summary;

  public TVShow() {}
}