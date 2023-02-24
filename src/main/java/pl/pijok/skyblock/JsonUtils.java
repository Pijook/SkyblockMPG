package pl.pijok.skyblock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.io.*;

public class JsonUtils {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static Object loadObject(File file, Class c){
        try {
            FileReader fileReader = new FileReader(file);
            String jsonString = JsonParser.parseReader(fileReader).toString();
            fileReader.close();
            return gson.fromJson(jsonString, c);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void saveObject(File file, Object object){
        try {
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();

            FileWriter fileWriter = new FileWriter(file);
            gson.toJson(object, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
