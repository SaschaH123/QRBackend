package org.sascha.qrbackend.QR;

import org.sascha.qrbackend.User.DTO.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/qr")
public class QRTokenController {

    private final QRService qrService;

    public QRTokenController(QRService qrService) {this.qrService = qrService;}

    @PostMapping("/issue")
    public ResponseEntity<QRIssuerResponse> issueQR(@RequestBody QRIssuerDTORequest request, Authentication authentication) {

        String userId = (String) authentication.getPrincipal();

        QRIssuerResponse response = qrService.issue(userId, request.getOfferId());

        return ResponseEntity.ok(response);



    }
    @GetMapping("/check-qr/{tokenHash}/{companyId}")
    public ResponseEntity<QRRedeemCompanyUserResponse> qrStatus(
            @PathVariable String tokenHash,
            @PathVariable String companyId,
            Authentication authentication) {

        System.out.println("Token: " + tokenHash);

        String userId =(String) authentication.getPrincipal();

        QRRedeemCompanyUserResponse redeemUserStatus = qrService.qrStatus(tokenHash,userId, companyId);




        return ResponseEntity.ok(redeemUserStatus);
    }

    @PostMapping("/company-user-scan")
    public ResponseEntity<QRRedeemPointsFromUserResponse> userScan(@RequestBody QRRedeemPointsFromUserRequest request, Authentication authentication) {
        String companyId = (String) authentication.getPrincipal();
        String userId = (String) authentication.getPrincipal();
        QRRedeemPointsFromUserResponse response = qrService.qrRedeemPointsFromUser(request.getTokenhash(), companyId, userId);

        return ResponseEntity.ok(response);

    }

}
