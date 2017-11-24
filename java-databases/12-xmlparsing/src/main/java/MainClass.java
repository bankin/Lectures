import dtos.AddressDto;
import dtos.PomDto;
import dtos.UserDto;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;

public class MainClass {

    public static void main(String[] args) throws JAXBException {
        AddressDto address = new AddressDto();
        address.setCity("Sofia");
        address.setCountry("Bulgaria");

        AddressDto address2 = new AddressDto();
        address2.setCity("Pernik");
        address2.setCountry("Bulgaria");

        UserDto user = new UserDto();
        user.setFirstName("Pesho");
        user.setLastName("Peshov");
        user.setAddresses(Arrays.asList(address, address2));

        JAXBContext addressContext = JAXBContext.newInstance(AddressDto.class);
        Marshaller marshaller = addressContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(address, new File("address.xml"));

        JAXBContext userContext = JAXBContext.newInstance(UserDto.class);
        Marshaller userMarshaller = userContext.createMarshaller();
        userMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        userMarshaller.marshal(user, new File("user.xml"));

        JAXBContext pomContext = JAXBContext.newInstance(PomDto.class);
        Unmarshaller unmarshaller = pomContext.createUnmarshaller();

        PomDto pomDto = (PomDto) unmarshaller.unmarshal(new File("pom.xml"));

        System.out.println(pomDto.getModelVersion());
    }
}
