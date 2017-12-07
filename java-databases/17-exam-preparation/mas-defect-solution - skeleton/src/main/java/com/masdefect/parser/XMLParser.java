package com.masdefect.parser;

import com.masdefect.parser.interfaces.FileParser;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.*;

@Component(value = "XMLParser")
public class XMLParser implements FileParser {

    @Override
    public <T> T read(Class<T> objectClass, String fileContent) throws IOException, JAXBException {
        return null;
    }

    @Override
    public <T> String write(T object, String fileContent) throws IOException, JAXBException {
        return null;
    }
}
