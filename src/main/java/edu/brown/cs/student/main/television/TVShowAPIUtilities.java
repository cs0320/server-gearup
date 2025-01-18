package edu.brown.cs.student.main.television;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

/**
 * This class shows a possible implementation of deserializing JSON from the TVMaze API into an
 * TVShow.
 */
public class TVShowAPIUtilities {

  /**
   * Deserializes JSON from the TVMaze into an TVShow object.
   *
   * @param jsonShow
   * @return
   */
  public static TVShow deserializeTVShow(String jsonTVShow) {
    try {
      // Initializes Moshi
      Moshi moshi = new Moshi.Builder().build();

      // Initializes an adapter to TVShow class then uses it to parse the JSON.
      JsonAdapter<TVShow> adapter = moshi.adapter(TVShow.class);

      TVShow show = adapter.fromJson(jsonTVShow);

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
