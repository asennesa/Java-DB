package com.example.demo.services;

import com.example.demo.entities.Supplier;
import com.example.demo.models.dtos.SupplierSeedDto;
import com.example.demo.repositories.SupplierRepository;
import com.example.demo.utils.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.Arrays;

@Service
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
}

