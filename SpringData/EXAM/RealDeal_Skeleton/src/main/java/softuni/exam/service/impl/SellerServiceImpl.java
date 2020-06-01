package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.models.dtos.SellerSeedDto;
import softuni.exam.models.dtos.SellerSeedRootDto;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
@Service
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(Path.of(GlobalConstants.SELLERS_XML_PATH));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        SellerSeedRootDto sellerSeedRootDto =
                this.xmlParser.
                        unmarshalFromFile(GlobalConstants.SELLERS_XML_PATH, SellerSeedRootDto.class);

        sellerSeedRootDto.getSellerSeedDtos().forEach(sellerSeedDto -> {
            if (this.validationUtil.isValid(sellerSeedDto)) {
                if (this.sellerRepository.findByEmail(sellerSeedDto.getEmail()) == null) {
                    Seller seller = this.modelMapper.map(sellerSeedDto, Seller.class);
                    sb.append(String.format("Successfully import seller %s -%s",seller.getLastName(),seller.getEmail()));
                    this.sellerRepository.saveAndFlush(seller);
                } else {
                    sb.append("Already in DB.");

                }

            } else {
                sb.append("Invalid seller");
            }
            sb.append((System.lineSeparator()));
        });


        return sb.toString();
    }


    @Override
    public Seller findByEmail(String email) {
        return this.sellerRepository.findByEmail(email);
    }

    @Override
    public Seller findById(Integer id) {
        return this.sellerRepository.findById(id);
    }
}
