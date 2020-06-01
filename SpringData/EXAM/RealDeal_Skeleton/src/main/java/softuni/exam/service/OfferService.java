package softuni.exam.service;

import softuni.exam.models.entities.Offer;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDateTime;

//ToDo - Before start App implement this Service and set areImported to return false
public interface OfferService {

    boolean areImported();

    String readOffersFileContent() throws IOException;
	
	String importOffers() throws IOException, JAXBException;

	Offer findUniqueOffer(String description, LocalDateTime addedOn);
}
