package edu.brown.cs.examples.moshiExample.server;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import edu.brown.cs.examples.moshiExample.soup.Soup;
import java.util.HashMap;
import java.util.Map;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Handler class for the soup ordering API endpoint.
 *
 * <p>This endpoint is similar to the endpoint(s) you'll need to create for Sprint 2. It takes a
 * basic GET request with no Json body, and returns a Json object in reply. The responses are more
 * complex, but this should serve as a reference.
 */
public class OrderHandler implements Route {
  private final Map<String, Soup> menu;

  /**
   * Constructor accepts some shared state
   *
   * @param menu the shared state (note: we *DON'T* want to make a defensive copy here!
   */
  public OrderHandler(Map<String, Soup> menu) {
    this.menu = menu;
  }

  /**
   * Pick a convenient soup and make it. the most "convenient" soup is the first recipe we find in
   * the unordered set of recipe cards.
   *
   * @param request the request to handle
   * @param response use to modify properties of the response
   * @return response content
   * @throws Exception This is part of the interface; we don't have to throw anything.
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    // TODO: 2) Right now, we only serialize the first soup, let's make it so you can choose which
    // soup you want!
    String soupname = request.queryParams("soupname");
    for (Soup soup : this.menu.values()) {
      // Just make the first soup
      Map<String, Soup> soupMap = new HashMap<>();
      soupMap.put(soup.getSoupName(), soup);

      // SOLUTION MIGHT LOOK LIKE
      //            Soup foundSoup = this.menu.
      return new SoupSuccessResponse(soupMap).serialize();
      //            return new SoupSuccessResponse(soupMap.put(soup.toString(),
      // soup.ingredients())).serialize();
    }
    return new SoupNoRecipesFailureResponse().serialize();

    // NOTE: beware this "return Object" and "throws Exception" idiom. We need to follow it because
    //   the library uses it, but in general this lowers the protection of the type system.
  }

  /** Response object to send, containing a soup with certain ingredients in it */
  public record SoupSuccessResponse(String response_type, Map<String, Soup> soupMap) {
    public SoupSuccessResponse(Map<String, Soup> soupMap) {
      this("success", soupMap);
    }
    /**
     * @return this response, serialized as Json
     */
    String serialize() {
      try {
        // Just like in SoupAPIUtilities.
        //   (How could we rearrange these similar methods better?)
        Moshi moshi = new Moshi.Builder().build();
        //                Type type = Types.newParameterizedType(List.class,
        // Types.newParameterizedType(List.class, String.class));
        //                Type newType =
        // Types.newParameterizedType(Map.class,String.class,Soup.class);
        JsonAdapter<SoupSuccessResponse> adapter = moshi.adapter(SoupSuccessResponse.class);
        return adapter.toJson(this);
      } catch (Exception e) {
        // For debugging purposes, show in the console _why_ this fails
        // Otherwise we'll just get an error 500 from the API in integration
        // testing.
        e.printStackTrace();
        throw e;
      }
    }
  }

  /** Response object to send if someone requested soup before any recipes were loaded */
  public record SoupNoRecipesFailureResponse(String response_type) {
    public SoupNoRecipesFailureResponse() {
      this("error");
    }

    /**
     * @return this response, serialized as Json
     */
    String serialize() {
      Moshi moshi = new Moshi.Builder().build();
      return moshi.adapter(SoupNoRecipesFailureResponse.class).toJson(this);
    }
  }
}
