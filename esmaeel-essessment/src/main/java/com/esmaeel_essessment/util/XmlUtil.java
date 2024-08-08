package com.esmaeel_essessment.util;

import com.esmaeel_essessment.model.Car;
import com.esmaeel_essessment.model.CarXmlWrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.StringWriter;
import java.util.List;

public class XmlUtil {

    public static String convertToXml(List<Car> cars) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(CarXmlWrapper.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        CarXmlWrapper wrapper = new CarXmlWrapper();
        wrapper.setCars(cars);

        StringWriter writer = new StringWriter();
        marshaller.marshal(wrapper, writer);
        return writer.toString();
    }

}
