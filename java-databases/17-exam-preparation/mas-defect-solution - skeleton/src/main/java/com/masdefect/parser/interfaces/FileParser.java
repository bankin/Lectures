package com.masdefect.parser.interfaces;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface FileParser {

    <T> T read(Class<T> objectClass, String fileContent) throws IOException, JAXBException;

    <T> String write(T object, String fileContent) throws IOException, JAXBException;
}
