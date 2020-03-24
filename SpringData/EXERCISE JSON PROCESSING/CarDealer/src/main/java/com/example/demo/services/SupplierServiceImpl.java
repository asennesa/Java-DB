package com.example.demo.services;

import com.example.demo.entities.Supplier;
import com.example.demo.models.dtos.SupplierSeedDto;
import com.example.demo.models.dtos.SupplierWriteDto;
import com.example.demo.repositories.SupplierRepository;
import com.example.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedSuppliers(SupplierSeedDto[] supplierSeedDtos) {
        if(this.supplierRepository.count() != 0){
            return;
        }
        Arrays.stream(supplierSeedDtos)
                .forEach(categorySeedDto ->{
                    if(this.validationUtil.isValid(categorySeedDto)){
                        Supplier supplier=this.modelMapper.map(categorySeedDto,Supplier.class);
                        supplier.setIsImporter(categorySeedDto.isImporter());

                        supplierRepository.saveAndFlush(supplier);

                    }else{
                        this.validationUtil.getViolations(categorySeedDto).stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                } );

    }

    @Override
    public Supplier getRandomSupplier() {
        Random random = new Random();
        long randomId = random
                .nextInt((int) this.supplierRepository.count())+1;
        return this.supplierRepository.getOne(randomId);
    }

    @Override
    public List<SupplierWriteDto> findAllLocalSuppliers() {
        return this.supplierRepository.findAllByIsImporterFalse().stream()
                .map(e->{
                    SupplierWriteDto supplierWriteDto =
                            this.modelMapper.map(e,SupplierWriteDto.class);

                    supplierWriteDto.setPartsCount(e.getParts().size());
                    return supplierWriteDto;

                }).collect(Collectors.toList());
    }
}

