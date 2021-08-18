package com.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TestHelper {

    public static File genOutputFile() {
        File desktopDir = new File(System.getProperty("user.home"), "/Desktop");
        return new File(desktopDir, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmmss")) + ".docx");
    }

    public static List<Profile> loadProfiles() throws IOException {
        InputStream data = ClassLoader.getSystemResourceAsStream("data.json");
        assert data != null;

        try (InputStreamReader reader = new InputStreamReader(data)) {
            Type type = new TypeToken<ArrayList<Item>>(){}.getType();
            List<Item> items = new Gson().fromJson(reader, type);
            return items.stream()
                    .map(TestHelper::toProfile)
                    .toList();
        }
    }

    private static Profile toProfile(Item item) {
        String file = "pics/" + item.pic;
        URL res = ClassLoader.getSystemResource(file);
        return new Profile(item.name, res.toString().substring(5));
    }

    private TestHelper() {
    }

    private static class Item {
        private String name;
        private String pic;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }

}
