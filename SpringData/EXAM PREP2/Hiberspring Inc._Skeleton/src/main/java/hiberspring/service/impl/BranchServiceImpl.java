package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.common.GlobalConstants;
import hiberspring.domain.dtos.BranchSeedDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Town;
import hiberspring.repository.BranchRepository;
import hiberspring.service.BranchService;
import hiberspring.service.TownService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final TownService townService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;
    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, TownService townService, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.branchRepository = branchRepository;
        this.townService = townService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public Boolean branchesAreImported() {
         return this.branchRepository.count() > 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return Files.readString(Path.of(GlobalConstants.BRANCHES_FILE_PATH));
    }

    @Override
    public String importBranches(String branchesFileContent) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        BranchSeedDto[] branchSeedDtos =
                this.gson.fromJson(new FileReader(GlobalConstants.BRANCHES_FILE_PATH), BranchSeedDto[].class);

        Arrays.stream(branchSeedDtos).forEach(branchSeedDto -> {
            if (this.validationUtil.isValid(branchSeedDto)) {
                if (this.branchRepository.findByName(branchSeedDto.getName()) == null) {
                    Branch branch = this.modelMapper.map(branchSeedDto, Branch.class);
                    if (this.townService.findByName(branchSeedDto.getTown()) == null) {
                        sb.append(GlobalConstants.INCORRECT_DATA_MESSAGE);
                    }else{
                        Town town = this.townService.findByName(branchSeedDto.getTown());
                        branch.setTown(town);
                        sb.append(String.format("Successfully imported town - %s ",branchSeedDto.getName()));
                        this.branchRepository.saveAndFlush(branch);
                    }

                } else {
                    sb.append("Already in DB.");

                }

            } else {
                sb.append(GlobalConstants.INCORRECT_DATA_MESSAGE);
            }
            sb.append((System.lineSeparator()));
        });


        return sb.toString();
    }
}