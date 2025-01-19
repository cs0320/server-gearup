package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.television.TVShow;
import edu.brown.cs.student.main.television.TVShowAPIUtilities;

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

/**
 * This class is used to illustrate how to build and send a GET request then prints the response. It
 * will also demonstrate a simple Moshi deserialization from online data.
 */
// TODO 1.1: Check out this Handler. The current logic will return the top result of searching "squid". 

public class TVShowHandler implements Route {
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
    // If you are interested in how parameters are received, try commenting out and
    // printing these lines! Notice that requesting a specific parameter requires that parameter
    // to be fulfilled.
    // If you specify a queryParam, you can access it by appending ?parameterName=name to the
    // endpoint
    // ex. http://localhost:3232/show?keyword=squid
    Set<String> params = request.queryParams();
    //     System.out.println(params);
    String keyword = request.queryParams("keyword");
    //     System.out.println(keyword);

    // Creates a hashmap to store the results of the request
    Map<String, Object> responseMap = new HashMap<>();
    try {
      // Sends a request to the API and receives JSON back
      String showJson = this.sendRequest(keyword);
      // Deserializes JSON into an TVShow
      TVShow show = TVShowAPIUtilities.deserializeTVShow(showJson);
      // Adds the data we care about to the responseMap
      responseMap.put("result", "success");
      responseMap.put("name", show.name);
      responseMap.put("summary", show.summary);
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

  private String sendRequest(String keyword) throws URISyntaxException, IOException, InterruptedException {
    // Build a request to this TVMaze API. Try out this link in your browser, what do you see?
    // TODO 1.1: Looking at the documentation, how can we modify the URI to search for shows based on a specific keyword that we specify?
    // See Documentation here: https://www.tvmaze.com/api
    String uri = "https://api.tvmaze.com/search/shows?q=" + keyword;

    // TODO 1.2: complete the TODO in TVShow.java
    HttpRequest buildTVShowApiRequest =
            HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .GET()
                    .build();

    // Send that API request then store the response in this variable. Note the generic type.
    HttpResponse<String> sentTVShowApiResponse =
        HttpClient.newBuilder()
            .build()
            .send(buildTVShowApiRequest, HttpResponse.BodyHandlers.ofString());

    // What's the difference between these two lines? Why do we return the body? What is useful from
    // the raw response (hint: how can we use the status of response)?
    System.out.println(sentTVShowApiResponse);
    System.out.println(sentTVShowApiResponse.body());

    return sentTVShowApiResponse.body();
  }
}
