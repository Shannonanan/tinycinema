package co.za.tinycinema.data.local;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;


 class ObjectConverter {

        static Gson gson = new Gson();

        @TypeConverter
        public static List<MovieResultEntity> stringToSomeObjectList(String data) {
            if (data == null) {
                return Collections.emptyList();
            }

            Type listType = new TypeToken<List<MovieResultEntity>>() {}.getType();

            return gson.fromJson(data, listType);
        }

        @TypeConverter
        public static String someObjectListToString(List<MovieResultEntity> someObjects) {
            return gson.toJson(someObjects);
        }

}
