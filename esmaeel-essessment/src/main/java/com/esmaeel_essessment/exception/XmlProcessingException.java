package com.esmaeel_essessment.exception;

import jakarta.xml.bind.JAXBException;

public class XmlProcessingException extends JAXBException {
    public XmlProcessingException(String message){
        super(message);
    }
}
