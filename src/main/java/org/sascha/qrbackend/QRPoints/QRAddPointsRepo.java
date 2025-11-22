package org.sascha.qrbackend.QRPoints;

import org.sascha.qrbackend.QR.QRTokenEntitiy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QRAddPointsRepo extends JpaRepository<QRAddPointsEntity, String> {

    Optional<QRAddPointsEntity> findByTokenHash(String tokenHash);

}
