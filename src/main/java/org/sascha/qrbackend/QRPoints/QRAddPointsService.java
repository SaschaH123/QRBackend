package org.sascha.qrbackend.QRPoints;

import org.sascha.qrbackend.User.DTO.QRAddPointsIssuerResponse;

public interface QRAddPointsService {

    public QRAddPointsIssuerResponse qrAddPointsissue(String companyId, Double userPrice);
}
