package com.management_asset.api.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management_asset.api.model.Parts;
import com.management_asset.api.repository.PartsRepository;
import com.management_asset.api.service.IPartsService;

@Service
public class PartsService implements IPartsService {

    private PartsRepository partsRepository;
    
    @Autowired
    public PartsService(PartsRepository partsRepository) {
        this.partsRepository = partsRepository;
    }

    @Override
    public List<Parts> findAll() {
        return partsRepository.findAll();
    }

    @Override
    public Parts findById(Integer id) {
        return partsRepository.findById(id).orElse(null);
    }

    public Parts save(Parts parts) {
        return partsRepository.save(parts);
    }
}
