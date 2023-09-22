package edu.brown.cs32.examples.moshiExample.server;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * This class is used to illustrate how to build and send a GET request then prints the response.
 *
 * Check out the rest of the gearup for an exercise on how to parse the response and deserialize
 * it into an object.
 */
public class MockHandler implements Route {

  /**
   * This handle method needs to be filled by any class implementing Route. When the path set in
   * Server gets accessed, it will fire the handle method.
   * @param request  The request object providing information about the HTTP request
   * @param response The response object providing functionality for modifying the response
   * @return
   * @throws Exception
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    // If you are interested in how parameters are received, try commenting out and
    // printing these lines! Notice that requesting a specific parameter requires that parameter
    // to be fulfilled.
    // If you specify a queryParam, you can access it by appending ?parameterName=name to the endpoint
    // ex. http://localhost:3232/mock?test=name

    Set<String> params = request.queryParams();
    String testParam = request.queryParams("test");


    this.buildArbitraryRequest();

    // Where does this String lead? Note that you might not always want to print a success message.
    // Spark's error handling is pretty constrained... An important part of this sprint will be in
    // learning to debug correctly by creating your own informative error messages where Spark falls short
    return "Fulfilled";
  }

  private void buildArbitraryRequest() throws URISyntaxException, IOException, InterruptedException {
    // Build a request to this BoredAPI. Try out this link in your browser, what do you see?
    HttpRequest buildBoredApiRequest = HttpRequest.newBuilder()
        .uri(new URI("http://www.boredapi.com/api/activity/"))
        .GET()
        .build();

    // Send that API request then store the response in this variable. Note the generic type.
    HttpResponse<String> sentBoredApiResponse = HttpClient.newBuilder()
        .build().
        send(buildBoredApiRequest, HttpResponse.BodyHandlers.ofString());

    // What's the difference between these two lines?
    System.out.println(sentBoredApiResponse);
    System.out.println(sentBoredApiResponse.body());
  }

}
