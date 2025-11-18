package org.sascha.qrbackend.QRPoints;

import org.sascha.qrbackend.QR.QRService;
import org.sascha.qrbackend.User.DTO.QRAddPointsIssueRequest;
import org.sascha.qrbackend.User.DTO.QRAddPointsIssuerResponse;
import org.sascha.qrbackend.User.DTO.QRIssuerDTORequest;
import org.sascha.qrbackend.User.DTO.QRIssuerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
