package com.example.PruebaCRUD.Services;

import com.example.PruebaCRUD.Repositories.SalonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalonService {
    private final SalonRepository salonRepository;

    @Autowired
    public SalonService(SalonRepository salonRepository) {
        this.salonRepository = salonRepository;
    }

    public List<?> getSalonesToETS() {
        return this.salonRepository.findAllBy();
    }

}
