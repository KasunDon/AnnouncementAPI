package co.tide.announcementapi.infrastructure.sparkjava;

import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonResponseTransformer implements ResponseTransformer {

    private final static Gson GSON = new Gson();

    public String render(Object model) {
        return GSON.toJson(model);
    }
}
