package org.sascha.qrbackend.QR;

import org.sascha.qrbackend.User.DTO.QRIssuerResponse;
import org.sascha.qrbackend.User.DTO.QRRedeemCompanyUserResponse;
import org.sascha.qrbackend.User.DTO.QRRedeemPointsFromUserResponse;

public interface QRService {

    public QRIssuerResponse issue(String userId, String offerId);
    public QRRedeemPointsFromUserResponse qrRedeemPointsFromUser(String token, String companyId, String userId);
    public QRRedeemCompanyUserResponse qrStatus(String token, String userId, String companyId);
}
