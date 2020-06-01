package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.models.dtos.OfferSeedRootDto;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Offer;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.OfferService;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final CarService carService;
    private final SellerService sellerService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, CarService carService, SellerService sellerService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.offerRepository = offerRepository;
        this.carService = carService;
        this.sellerService = sellerService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(GlobalConstants.OFFERS_XML_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        OfferSeedRootDto offerSeedRootDto =
                this.xmlParser.
                        unmarshalFromFile(GlobalConstants.OFFERS_XML_PATH, OfferSeedRootDto.class);
        System.out.println();
        offerSeedRootDto.getOffers().forEach(offerSeedDto -> {
            if (this.validationUtil.isValid(offerSeedDto)) {
                if (this.offerRepository.findByDescriptionAndAddedOn(offerSeedDto.getDescription(),offerSeedDto.getAddedOn()) == null) {
                    if (this.carService.findById(offerSeedDto.getCar().getId()) == null &&
                            this.sellerService.findById(offerSeedDto.getSeller().getId()) == null) {
                        sb.append("Invalid offer");
                    }else{
                        Offer offer = this.modelMapper.map(offerSeedDto, Offer.class);
                        Car car = carService.findById(offerSeedDto.getCar().getId());
                        Seller seller = sellerService.findById(offerSeedDto.getSeller().getId());
                        offer.setCar(car);
                        offer.setSeller(seller);
                        sb.append(String.format("Successfully import offer  %s -%b",offer.getAddedOn().toString(),offer.isHasGoldStatus()));
                        this.offerRepository.saveAndFlush(offer);

                    }

                } else {
                    sb.append("Already in DB.");

                }

            } else {
                sb.append("Invalid offer");
            }
            sb.append((System.lineSeparator()));
        });


        return sb.toString();
    }

    @Override
    public Offer findUniqueOffer(String description, LocalDateTime addedOn) {
        return this.offerRepository.findByDescriptionAndAddedOn(description,addedOn);
    }
}
