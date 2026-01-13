package org.sascha.qrbackend.EnterOfferUser;

import org.apache.coyote.Request;
import org.sascha.qrbackend.Employee.Employee;
import org.sascha.qrbackend.Employee.EmployeeRepo;
import org.sascha.qrbackend.Offer.Offer;
import org.sascha.qrbackend.User.DTO.DeleteOfferResponse;
import org.sascha.qrbackend.User.DTO.EnterOfferUserResponse;
import org.sascha.qrbackend.User.DTO.FirstEntranceUserOfferRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


@RestController
@RequestMapping("/api/enter")
public class EnterOfferUserController {


    private final EnterOfferUserService enterOfferUserService;

    public EnterOfferUserController(EnterOfferUserService enterOfferUserService) {
        this.enterOfferUserService = enterOfferUserService;
    }


    @PostMapping("/user/first-entrance")
    public ResponseEntity<EnterOfferUserResponse> enterOfferUser(@RequestBody FirstEntranceUserOfferRequest request, Authentication authentication) {

        System.out.println("Springe in First entrance Controller");
        String userId = authentication.getName();
        System.out.println("User Id:" + userId);
        String companyId = request.getCompanyId();

        EnterOfferUserResponse response = enterOfferUserService.searchFirstEntryUserOffer(userId, companyId);

        return ResponseEntity.ok(response);

    }




}
