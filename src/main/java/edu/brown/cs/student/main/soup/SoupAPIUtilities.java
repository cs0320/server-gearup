package edu.brown.cs.student.main.soup;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains utility methods for handling soup objects and Json-encoded soup objects.
 * Primarily this means _serializing_ Soup objects to Json and _deserializing_ Soup objects from
 * Json
 *
 * <p>Use this as a reference for polymorphic serialization/deserialization. It shouldn't be
 * necessary on Sprint 2 functionality, and Sprint 2 testing only requires Maps for response types.
 *
 * This class shows how to deserialize into complex types.
 */
public class SoupAPIUtilities {
  private SoupAPIUtilities() {}

  /**
   * Mixes up a fresh bowl of soup, based on a JSON object that says what ingredients are in it.
   * Yum! The recipe book doesn't say whether or not the chef is timid, and so this method needs to
   * be told before it can actually make the soup.
   *
   * This is not used in the Gearup, but this might be how one could deserialize a single Soup from
   * JSON into a Soup object.
   *
   * @param jsonList the ingredients as a JSON list
   * @throws JsonDataException if the given JSON doesn't conform to the soup specification (we're
   *     re-using Moshi's JsonDataException here; this may or may not be a good choice in reality)
   * @throws IOException if the given string isn't valid JSON
   * @return the Soup object
   */
  public static Soup deserializeSoup(String jsonList)
      throws JsonDataException, IOException {

    // Create an adapter to read the json string (hopefully) into a Soup object.
    Moshi moshi = new Moshi.Builder().build();

    // One more step is needed. We can't just use an adapter like this:
    // JsonAdapter<Ingredient> ingredientAdapter = moshi.adapter(Ingredient.class).nonNull();
    // ...because we're expecting a JSON *array* of ingredients. So we need a layer that handles the
    // enclosing list.


    Type listOfIngredientsType = Types.newParameterizedType(List.class, String.class);
    // ...and pass it instead of List.class:
    JsonAdapter<List<String>> recipeAdapter = moshi.adapter(listOfIngredientsType);
    // ...and finally read the json string:
    try {
      List<String> recipe = recipeAdapter.fromJson(jsonList);
      // In the beginning, the soup is empty. There's nothing in the pot.
      Soup result = new Soup("new soup",recipe, true);

      // I suppose we'd better actually add the ingredients to the soup, too.
      for (String ingredient : recipe) {
        result.stirIn(ingredient);
      }
      return result;
    }

    catch (IOException e) {
      // In a real system, we wouldn't println like this, but it's useful for demonstration:
      System.err.println("SoupHandler: string wasn't valid JSON.");
      throw e;
    } catch (JsonDataException e) {
      // In a real system, we wouldn't println like this, but it's useful for demonstration:
      System.err.println("SoupHandler: JSON wasn't an ingredient.");
      throw e;
    }
  }

  /**
   * Serializes the ingredients in a bowl soup into a Json object for sending across the 'net. The
   * result does not include any  soup properties---we're just sending the ingredients.
   *
   * @param soup The soup to serialize into a String
   * @return the serialized Json list representing the ingredients
   */
  public static String serializeSoup(Soup soup) {
    // as in fromJson, we need to work with arbitrary Ingredients.
    // The polymorphic factory will automatically _insert_ the "type" field

    Moshi moshi = new Moshi.Builder().build();

    // Although the Java object contains a set, there's no analogue in Json.
    // Moshi will serialize this set as a list.
    Type setOfIngredientsType = Types.newParameterizedType(List.class, String.class);
    JsonAdapter<List<String>> adapter = moshi.adapter(setOfIngredientsType);
    return adapter.toJson(soup.getIngredients());
  }
  public static List<Soup> deserializeMenu(String jsonList) throws IOException {
    List<Soup> menu = new ArrayList<>();
    try{
      Moshi moshi = new Moshi.Builder().build();

      // notice the type and JSONAdapter parameterized type match the return type of the method
      // Since List is generic, we shouldn't just pass List.class to the adapter factory.
      // Instead, let's be more precise. Java has built-in classes for talking about generic types
      // programmatically.
      // Building libraries that use them is outside the scope of this class, but we'll follow the
      // Moshi docs'
      // template by creating a Type object corresponding to List<Ingredient>:
      Type listType = Types.newParameterizedType(List.class, Soup.class);
      JsonAdapter<List<Soup>> adapter = moshi.adapter(listType);

      List<Soup> deserializedMenu = adapter.fromJson(jsonList);

      return deserializedMenu;

    }
    // From the Moshi Docs (https://github.com/square/moshi):
    //   "Moshi always throws a standard java.io.IOException if there is an error reading the JSON
    // document, or if it is malformed. It throws a JsonDataException if the JSON document is
    // well-formed, but doesn't match the expected format."
    catch(IOException e){
      // In a real system, we wouldn't println like this, but it's useful for demonstration:
      System.err.println("OrderHandler: string wasn't valid JSON.");
      throw e;
    }
    catch (JsonDataException e) {
      // In a real system, we wouldn't println like this, but it's useful for demonstration:
      System.err.println("OrderHandler: JSON wasn't in the right format.");
      throw e;
    }
  }

  /**
   *
   * @param menu
   * @return
   */
//  public static String serializeMenu(List<String> menu){
//    Moshi moshi = new Moshi.Builder().build();
//    JsonAdapter<Menu> adapter = moshi.adapter(Menu.class);
//    String ret = adapter.toJson(menu);
//    return ret;
//  }

  /**
   * You will likely not need to do this in Sprint 2 as the JSON / data will come from your
   * HTTP Request... Think about how you can combine these patterns to deserialize the data
   * you will need for Sprint 2.
   * @param filepath
   * @return
   * @throws IOException
   */
  public static String readInJson(String filepath) {
    try{
      return new String(Files.readAllBytes(Paths.get(filepath)));
    }catch(IOException e) {
      return "Error in reading JSON";
    }
  }
}
