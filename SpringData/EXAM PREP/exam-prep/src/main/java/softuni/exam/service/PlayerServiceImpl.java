package softuni.exam.service;


import com.google.gson.Gson;
import org.apache.tomcat.jni.Global;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.domain.dtos.PictureSeedRootDto;
import softuni.exam.domain.dtos.PlayerSeedDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;


import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;


@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;
    private final TeamService teamService;
    private final PictureService pictureService;
    private final Gson gson;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, XmlParser xmlParser, TeamService teamService, PictureService pictureService, Gson gson) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
        this.teamService = teamService;
        this.pictureService = pictureService;
        this.gson = gson;
    }

    @Override
    public String importPlayers() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        PlayerSeedDto[] playerSeedDtos =
                this.gson.fromJson(new FileReader(GlobalConstants.PLAYERS_JSON_PATH), PlayerSeedDto[].class);
        System.out.println();

        Arrays.stream(playerSeedDtos).forEach(playerSeedDto -> {
            if (this.validatorUtil.isValid(playerSeedDto)) {
                if (this.playerRepository.findByFirstNameAndLastName
                        (playerSeedDto.getFirstName(),playerSeedDto.getLastName()) == null) {
                    Player player = this.modelMapper.map(playerSeedDto, Player.class);
                    Team team = this.teamService.getByName(playerSeedDto.getTeam().getName());
                    Picture picture = this.pictureService.getPictureByName(playerSeedDto.getPicture().getUrl());
                    player.setTeam(team);
                    player.setPicture(picture);
                    sb.append(String.format("Successfully imported picture - %s %s",playerSeedDto.getFirstName()
                    ,playerSeedDto.getLastName()));
                    this.playerRepository.saveAndFlush(player);
                } else {
                    sb.append("Already in DB.");

                }

            } else {
                sb.append("Invalid player");
            }
            sb.append((System.lineSeparator()));
        });


        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return Files.readString(Path.of(GlobalConstants.PLAYERS_JSON_PATH));
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder sb = new StringBuilder();
        this.playerRepository.findAllBySalaryGreaterThanOrderBySalaryDesc(BigDecimal.valueOf(100000))
                .forEach(p->{sb.append(String.format("Player name: %s %s\n\tNumber: %d\n\tSalary: %.2f\n\tTeam: %s\n"
                        ,p.getFirstName()
                        ,p.getLastName()
                        ,p.getNumber()
                        ,p.getSalary()
                        ,p.getTeam().getName())).append(System.lineSeparator());

        });
        return sb.toString();
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder sb = new StringBuilder();
        this.playerRepository.findAllByTeamName("North Hub")
                .forEach(p->{sb.append(String.format("Player name: %s %s - %s\nNumber: %d\n"
                        ,p.getFirstName()
                        ,p.getLastName()
                        ,p.getPosition()
                        ,p.getNumber())).append(System.lineSeparator());

                });
        return sb.toString();
    }


}
