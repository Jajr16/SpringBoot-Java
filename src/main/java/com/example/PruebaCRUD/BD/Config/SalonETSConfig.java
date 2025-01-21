package com.example.PruebaCRUD.BD.Config;

import com.example.PruebaCRUD.BD.ETS;
import com.example.PruebaCRUD.BD.PKCompuesta.SalonETSPK;
import com.example.PruebaCRUD.BD.Salon;
import com.example.PruebaCRUD.BD.SalonETS;
import com.example.PruebaCRUD.Repositories.ETSRepository;
import com.example.PruebaCRUD.Repositories.SalonETSRepository;
import com.example.PruebaCRUD.Repositories.SalonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class SalonETSConfig {
    @Bean
    @Order(16)
    CommandLineRunner initDataSalonETS(SalonRepository salonRepository, ETSRepository etsRepository,
                                       SalonETSRepository salonETSRepository) {
        return args -> {
            Salon S4108 = salonRepository.findByNumSalon(4108).orElse(null);
            Salon S3210 = salonRepository.findByNumSalon(3210).orElse(null);
            Salon S1111 = salonRepository.findByNumSalon(1111).orElse(null);

            ETS ets1 = etsRepository.findById(1).orElse(null);
            ETS ets2 = etsRepository.findById(2).orElse(null);
            ETS ets3 = etsRepository.findById(3).orElse(null);

            if (salonETSRepository.count() == 0) {
                SalonETSPK  sets1 = new SalonETSPK();
                assert S4108 != null;
                sets1.setNumSalon(S4108.getNumSalon());
                assert ets1 != null;
                sets1.setIdETS(ets1.getIdETS());

                SalonETS salon1 = new SalonETS();
                salon1.setId(sets1);
                salon1.setNumSalon(S4108);
                salon1.setIdETS(ets1);

                salonETSRepository.save(salon1);

                SalonETSPK sets2 = new SalonETSPK();
                assert S3210 != null;
                sets2.setNumSalon(S3210.getNumSalon());
                assert ets2 != null;
                sets2.setIdETS(ets2.getIdETS());

                SalonETS salon2 = new SalonETS();
                salon2.setId(sets2);
                salon2.setNumSalon(S3210);
                salon2.setIdETS(ets2);

                salonETSRepository.save(salon2);

                SalonETSPK sets3 = new SalonETSPK();
                assert S1111 != null;
                sets3.setNumSalon(S1111.getNumSalon());
                assert ets3 != null;
                sets3.setIdETS(ets3.getIdETS());

                SalonETS salon3 = new SalonETS();
                salon3.setId(sets3);
                salon3.setNumSalon(S1111);
                salon3.setIdETS(ets3);

                salonETSRepository.save(salon3);
            }


        };
    }
}
