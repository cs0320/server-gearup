package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.activity.Activity;
import edu.brown.cs.student.main.activity.ActivityAPIUtilities;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import spark.Request;
import spark.Response;
import spark.Route;

// TODO: Complete TODO A followed by TODO B!

/**
 * This class is used to illustrate how to build and send a GET request then prints the response. It
 * will also demonstrate a simple Moshi deserialization from online data.
 */
// TODO B: Check out this Handler. How can we make it only get activities based on participant #?
// See Documentation here: http://cs0320-ci.cs.brown.edu:3333
public class ActivityHandler implements Route {
  /**
   * This handle method needs to be filled by any class implementing Route. When the path set in
   * edu.brown.cs.examples.moshiExample.server.Server gets accessed, it will fire the handle method.
   *
   * <p>NOTE: beware this "return Object" and "throws Exception" idiom. We need to follow it because
   * the library uses it, but in general this lowers the protection of the type system.
   *
   * @param request The request object providing information about the HTTP request
   * @param response The response object providing functionality for modifying the response
   */
  @Override
  public Object handle(Request request, Response response) {
    // If you are interested in how parameters are received, try printing out this set.
    // Notice that requesting a specific parameter requires that parameter to be fulfilled.
    // Query parameters are passed in the URL as ?key=value pairs, e.g.,
    // http://localhost:3232/activity?example=value

    Set<String> params = request.queryParams();
    // System.out.println("Received query parameters: " + params);



    // TODO B.1: Right now, we retrieve all query parameters using request.queryParams(),
    // but you can experiment by extracting specific attributes based on their keys.
    // Try printing params to see what’s available, then find a way to access a single value.
    // Hint: Look for a method that allows you to retrieve a single parameter by its key.
    // Try comparing it with how we retrieve all parameters!

    String participants = request.queryParams("participants");

    // Creates a hashmap to store the results of the request
    Map<String, Object> responseMap = new HashMap<>();
    try {

      // TODO B.2: Now that sendRequest needs to accept a parameter, what should we pass here?
      // Notice here we are trying to parse an Int, if what we get back doesn't make sense as an int
      // it will throw an exception, which would probably be good to check!
      String activityJson = this.sendRequest(Integer.parseInt(participants));

      // Deserializes JSON into an Activity
      Activity activity = ActivityAPIUtilities.deserializeActivity(activityJson);
      // Adds results to the responseMap
      responseMap.put("result", "success");
      responseMap.put("activity", activity);
      return responseMap;
    } catch (Exception e) {
      e.printStackTrace();
      // This is a relatively unhelpful exception message. An important part of this sprint will be
      // in learning to debug correctly by creating your own informative error messages where Spark
      // falls short.
      responseMap.put("result", "Exception");
    }
    return responseMap;
  }

  private String sendRequest(int participantNumber) throws URISyntaxException, IOException, InterruptedException {
    // Build a request to this BoredAPI. Try out this link in your browser, what do you see?

    // TODO A: Looking at the documentation, how can we add to the URI to query an activity based on participant number?

    /*
    TODO (HINT): We want a random activity for {num} participants. Which endpoint and query parameter(s) would the URI
    need to do that? Does sendRequest() need to take in any specific information?
    */

    // SOLUTION URL FOR DEMO: http://localhost:3233/activity?participants=7

    HttpRequest buildBoredApiRequest =
            HttpRequest.newBuilder()
                    .uri(new URI("http://cs0320-ci.cs.brown.edu:3333/api/activity?participants=" + participantNumber))
                    .GET()
                    .build();

    // Send that API request then store the response in this variable. Note the generic type.
    HttpResponse<String> sentBoredApiResponse =
            HttpClient.newBuilder()
                    .build()
                    .send(buildBoredApiRequest, HttpResponse.BodyHandlers.ofString());

    // What's the difference between these two lines? Why do we return the body? What is useful from
    // the raw response (hint: how can we use the status of response)?
    System.out.println(sentBoredApiResponse);
    System.out.println(sentBoredApiResponse.body());

    return sentBoredApiResponse.body();
  }
}