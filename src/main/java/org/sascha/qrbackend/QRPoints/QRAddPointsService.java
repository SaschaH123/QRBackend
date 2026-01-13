package org.sascha.qrbackend.QRPoints;

import org.sascha.qrbackend.User.DTO.QRAddPointsIssuerResponse;
import org.sascha.qrbackend.User.DTO.QRRedeemCompanyUserResponse;
import org.sascha.qrbackend.User.DTO.QRRedeemUserCompanyResponse;
import org.sascha.qrbackend.User.DTO.QRReedemAddPointsFromCompanyResponse;

public interface QRAddPointsService {

    public QRAddPointsIssuerResponse qrAddPointsissue(String companyId, Double userPrice);
    public QRReedemAddPointsFromCompanyResponse qrRedeemScanFromUser(String token, String userId);
    public QRRedeemUserCompanyResponse qrStatus(String tokenHash, String userId);
}
