package edu.brown.cs.student.main.television;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * This class shows a possible implementation of deserializing JSON from the TVMaze API into an
 * TVShow.
 */
public class TVShowAPIUtilities {

  /**
   * Deserializes JSON from the TVMaze into an TVShow object.
   *
   * @param jsonTVShow
   * @return
   */
  public static TVShow deserializeTVShow(String jsonTVShow) {
    try {
      // Initializes Moshi
      Moshi moshi = new Moshi.Builder().build();

      //Looking at the JSON response, we notice that the structure is a JSON array. In order to parse with moshi,
      //we need to create a new parameterized type to deal with this list format. This is not necessary when the
      //response is not a list of JSONs.
      Type listType = Types.newParameterizedType(List.class, TVShowResponse.class);

      // Create an adapter for the list of TVShowResponse
      JsonAdapter<List<TVShowResponse>> adapter = moshi.adapter(listType);

      // Parse the JSON into a list of TVShowResponse objects
      List<TVShowResponse> showResponses = adapter.fromJson(jsonTVShow);

      TVShow show = showResponses.get(0).show;
      return show;
    }
    // Returns an empty TVShow... Probably not the best handling of this error case...
    // Notice an alternative error throwing case to the one done in OrderHandler. This catches
    // the error instead of pushing it up.
    catch (IOException e) {
      e.printStackTrace();
      return new TVShow();
    }
  }
}
