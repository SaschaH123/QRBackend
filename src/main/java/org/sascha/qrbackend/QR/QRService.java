package org.sascha.qrbackend.QR;

import org.sascha.qrbackend.User.DTO.QRIssuerResponse;

public interface QRService {

    public QRIssuerResponse issue(String userId, String offerId);
}
