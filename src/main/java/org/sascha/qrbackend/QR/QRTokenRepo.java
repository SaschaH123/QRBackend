package org.sascha.qrbackend.QR;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QRTokenRepo extends JpaRepository<QRTokenEntitiy, String> {

    Optional<QRTokenEntitiy> findByTokenHash(String tokenHash);

}
