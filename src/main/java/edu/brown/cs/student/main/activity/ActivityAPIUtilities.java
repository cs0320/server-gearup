package edu.brown.cs.student.main.activity;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.io.IOException;

public class ActivityAPIUtilities {
  // Notice an alternative error throwing case. This catches the error instead of pushing it up
  public static Activity deserializeActivity(String jsonActivity) {
    try{
      Moshi moshi = new Moshi.Builder().build();

      JsonAdapter<Activity> adapter = moshi.adapter(Activity.class);

      Activity activity = adapter.fromJson(jsonActivity);

      return activity;
    }
    // Returns an empty activity...
    catch(IOException e){
      System.err.println(e);
      return new Activity();
    }
  }
  public static String serializeActivity(){
    return "";
  }
}
