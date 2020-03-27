package softuni.exam.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.domain.dtos.TeamSeedRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;


import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
@Transactional
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, PictureService pictureService, ModelMapper modelMapper, ValidatorUtil validatorUtil, XmlParser xmlParser) {
        this.teamRepository = teamRepository;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
    }

    @Override

    public String importTeams() throws JAXBException, FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        TeamSeedRootDto teamSeedRootDto =
                this.xmlParser.
                        unmarshalFromFile(GlobalConstants.TEAMS_XML_PATH, TeamSeedRootDto.class);
        teamSeedRootDto.getTeams().forEach(teamSeedDto -> {
            if (this.validatorUtil.isValid(teamSeedDto)) {
                //VALIDATE DTO
                if (this.teamRepository.findByName(teamSeedDto.getName()) == null) {
                    //CHECK IF THERE IS SUCH TEAM IN DB
                    Team team = this.modelMapper.map(teamSeedDto, Team.class);
                    if (this.pictureService.getPictureByName(team.getPicture().getUrl())==null){
                        //CHECK IF THERE IS SUCH PICTURE IN DB BEFORE SETTING IT TO TEAM USING SERVICE.
                        sb.append("Invalid team");
                    }else{
                        Picture picture = this.pictureService.getPictureByName(team.getPicture().getUrl());
                        team.setPicture(picture);
                        sb.append("Successfully imported team - ").append(team.getName());
                        System.out.println();
                        this.teamRepository.saveAndFlush(team);
                    }
                } else {
                    sb.append("Already in DB.");
                }
            } else {
                sb.append("Invalid team");
            }
            sb.append((System.lineSeparator()));
        });


        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return Files.readString(Path.of(GlobalConstants.TEAMS_XML_PATH));
    }

    @Override
    public Team getByName(String name) {
        return this.teamRepository.findByName(name);
    }


}
