package com.java.springBoot.app.RestController;

import com.java.springBoot.app.Class.Response;
import com.java.springBoot.app.Exception.NoDataFoundException;
import com.java.springBoot.app.Model.Patron;
import com.java.springBoot.app.Service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    @Autowired
    private PatronService patronService;

    @GetMapping
    public ResponseEntity<Response<List<Patron>>> getAllPatrons() {
        List<Patron> patrons = patronService.getAllPatrons();
        return ResponseEntity.ok(Response.success(patrons));
    }

    @GetMapping("/{id}")
    public Response<Patron> getPatronById(@PathVariable Long id) {
        Patron patron = patronService.getPatron(id);
        if (patron == null) {
            return Response.error(404, "Patron not found");
        } else {
            Response<Patron> response = new Response<>();
            response.setResultCode(200);
            response.setResultDescription("Success");
            response.setMessage("Patron details retrieved successfully");
            response.setData(patron);
            return response;
        }
    }

    @PostMapping
    public ResponseEntity<Response<Patron>> addPatron(@RequestBody Patron patron) {
        Patron savedPatron = patronService.addPatron(patron);
        Response<Patron> response = Response.success(savedPatron);
        response.setResultCode(201);
        response.setMessage("Patron created successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/{id}")
    public Response<Patron> updatePatron(@PathVariable Long id, @Valid @RequestBody Patron patronDetails) {
        Patron existingPatron = patronService.getPatron(id);

        if (existingPatron == null) {
            return Response.error(404, "Patron not found");
        }

        // Update Patron fields
        existingPatron.setName(patronDetails.getName());
        existingPatron.setContactInfo(patronDetails.getContactInfo());
        existingPatron.setEmail(patronDetails.getEmail());
        existingPatron.setPhoneNumber(patronDetails.getPhoneNumber());
        existingPatron.setContactInfo(patronDetails.getContactInfo());


        // Save updated Patron in database
        Patron updatedPatron = patronService.updatePatron(existingPatron);

        // Return response with updated Patron
        Response<Patron> response = new Response<>();
        response.setResultCode(200);
        response.setResultDescription("Success");
        response.setMessage("Patron updated successfully");
        response.setData(updatedPatron);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deletePatron(@PathVariable Long id) {
        try {
            Patron patron = patronService.getPatron(id);

            if (patron == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Response.error(404, "Error deleting Patron: Patron not found"));
            }

            patronService.deletePatron(id);
            return ResponseEntity.ok(Response.success());

        } catch (NoDataFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Response.error(404, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.error(500, "Error deleting Patron"));
        }
    }
}
