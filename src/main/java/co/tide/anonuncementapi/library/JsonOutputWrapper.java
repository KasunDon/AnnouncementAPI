package co.tide.announcementapi.library;

import java.util.HashMap;
import java.util.Map;

public class JsonOutputWrapper {

    public static <K, V> Map<K, V> wrap(
        K key,
        V value
    ) {

        Map<K, V> output = new HashMap<>();
        output.put(key, value);

        return output;
    }
}
