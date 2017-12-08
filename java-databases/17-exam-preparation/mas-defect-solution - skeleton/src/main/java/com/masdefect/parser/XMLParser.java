package com.masdefect.parser;

import com.masdefect.parser.interfaces.FileParser;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component(value = "XMLParser")
public class XMLParser implements FileParser {

    @Override
    public <T> T read(Class<T> objectClass, String fileContent) throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(objectClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        T dto = (T) unmarshaller.unmarshal(new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8.name())));

        return dto;
    }

    @Override
    public <T> String write(T object, String fileContent) throws IOException, JAXBException {
        return null;
    }
}
