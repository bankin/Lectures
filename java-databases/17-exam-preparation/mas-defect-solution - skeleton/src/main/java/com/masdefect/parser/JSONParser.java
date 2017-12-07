package com.masdefect.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.masdefect.parser.interfaces.FileParser;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Component(value = "JSONParser")
public class JSONParser implements FileParser {

    private Gson gson;

    public JSONParser() {
        this.gson = new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .create();
    }

    @Override
    public <T> T read(Class<T> objectClass, String fileContent) throws IOException, JAXBException {
        return this.gson.fromJson(fileContent, objectClass);
    }

    @Override
    public <T> String write(T object, String fileContent) throws IOException, JAXBException {
        return null;
    }
}
