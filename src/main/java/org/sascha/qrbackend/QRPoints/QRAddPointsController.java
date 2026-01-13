package org.sascha.qrbackend.QRPoints;

import org.sascha.qrbackend.QR.QRService;
import org.sascha.qrbackend.User.DTO.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/qr")
public class QRAddPointsController {

    private final QRAddPointsService qrAddPointsService;

    public QRAddPointsController(QRAddPointsService qrAddPointsService) {
        this.qrAddPointsService = qrAddPointsService;
    }


    @PostMapping("/add")
    public ResponseEntity<QRAddPointsIssuerResponse> issueAddPoints(@RequestBody QRAddPointsIssueRequest request, Authentication authentication) {

        String userId = (String) authentication.getPrincipal();

        QRAddPointsIssuerResponse response = qrAddPointsService.qrAddPointsissue(userId, request.getUserPrice());

        return ResponseEntity.ok(response);

    }

    @PostMapping("/add/user-points")
    public ResponseEntity<QRReedemAddPointsFromCompanyResponse> reedemUserQR(@RequestBody QRReedemAddPointsFromCompanyRequest request, Authentication authentication) {

        String userId = authentication.getName();
        String token = request.getTokenhash();

        QRReedemAddPointsFromCompanyResponse response = qrAddPointsService.qrRedeemScanFromUser(token, userId);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/check-company-qr/{tokenHash}")
    public ResponseEntity<QRRedeemUserCompanyResponse> qrStatus(
            @PathVariable String tokenHash,
            Authentication authentication) {

        System.out.println("Token: " + tokenHash);

        String userId =(String) authentication.getPrincipal();

        QRRedeemUserCompanyResponse redeemUserStatus = qrAddPointsService.qrStatus(tokenHash,userId);



        return ResponseEntity.ok(redeemUserStatus);
    }

}
