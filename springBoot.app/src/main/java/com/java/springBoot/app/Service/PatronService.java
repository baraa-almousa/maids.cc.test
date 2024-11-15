package com.java.springBoot.app.Service;

import com.java.springBoot.app.Exception.NoDataFoundException;
import com.java.springBoot.app.Model.Patron;
import com.java.springBoot.app.Repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public Patron getPatron(Long id) {
        return patronRepository.findById(id).orElse(null);
    }

    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    public void deletePatron(Long id) {
        if (!patronRepository.existsById(id)) {
            throw new NoDataFoundException("Patron with ID " + id + " not found");
        }
        patronRepository.deleteById(id);
    }

    public Patron updatePatron(Patron patron) {
        return patronRepository.save(patron);
    }
}
