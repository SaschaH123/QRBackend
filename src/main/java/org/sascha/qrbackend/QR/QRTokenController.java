package org.sascha.qrbackend.QR;

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
public class QRTokenController {

    private final QRService qrService;

    public QRTokenController(QRService qrService) {this.qrService = qrService;}

    @PostMapping("/issue")
    public ResponseEntity<QRIssuerResponse> issueQR(@RequestBody QRIssuerDTORequest request, Authentication authentication) {

        String userId = (String) authentication.getPrincipal();

        QRIssuerResponse response = qrService.issue(userId, request.getOfferId());

        return ResponseEntity.ok(response);



    }

}
