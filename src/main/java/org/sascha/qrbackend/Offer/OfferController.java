package org.sascha.qrbackend.Offer;

import org.sascha.qrbackend.User.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController()
@RequestMapping("api/offer")
public class OfferController {
    private final OfferService offerService;

    @Autowired
    OfferRepo offerRepo;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOffer(@RequestBody CreateOfferRequest request, Authentication authentication) {

        System.out.println("✅ Offer-Request empfangen: " + request.getOfferName());
        System.out.println("🔑 Token Authentication: " + authentication);

        if (authentication == null) {
            System.out.println("❌ Kein JWT gefunden!");
            return ResponseEntity.status(401).body("Unauthorized");
        }


        String companyId = (String) authentication.getPrincipal();
        System.out.println("👤 UserId aus Token: " + companyId);

    try {
        Offer offer = offerService.createOffer(
                companyId,
                request.getOfferName(),
                request.getOfferDesc(),
                request.getOfferPoints(),
                request.getOfferStatus());

    System.out.println("💾 Angebot gespeichert: " + offer.getOfferName());
        return ResponseEntity.ok(offer);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError()
                .body("Fehler beim Erstellen des Angebots: " + e.getMessage());
    }

    }

    @GetMapping("/single/{companyId}")
    public ResponseEntity<List<GetSingleCompanyListResponse>> getSingleOffers(
        @PathVariable String companyId, Authentication authentication) {

        String userId = (String) authentication.getPrincipal();

        System.out.println("Suche alle Angebote einer Company");

        List<GetSingleCompanyListResponse> singleCompanyOffers = offerService.getSingleOffersByCompanyId(companyId, userId);

        return ResponseEntity.ok(singleCompanyOffers);

    }

    @PutMapping("/update-offer/{offerId}")
    public ResponseEntity<UpdateExistingOfferResponse> updateExistingOffer(
            @PathVariable String offerId,
            @RequestBody UpdateExistingOfferRequest request,
            Authentication authentication) {

        String userId = authentication.getName();

        Offer offer = offerRepo.findByOfferId(offerId)
                        .orElseThrow(() -> new RuntimeException("Kein offer gefunden"));

        LocalDateTime createdAt = offer.getCreatedAt();
        LocalDateTime now = LocalDateTime.now();
        long hoursSinceCreation = ChronoUnit.HOURS.between(createdAt, now);

        System.out.println("🕐 Erstellt am: " + createdAt);
        System.out.println("🕐 Jetzt: " + now);
        System.out.println("🕐 Stunden vergangen: " + hoursSinceCreation);

        if (hoursSinceCreation >= 1) {
            return ResponseEntity.status(403)
                    .body(new UpdateExistingOfferResponse(
                            false,
                            "Angebot kann nur innerhalb 1 Stunde bearbeitet werden"
                    ));
        }

        offer.setOfferName(request.getOfferName());
        offer.setOfferDesc(request.getOfferDesc());
        offer.setOfferPoints(request.getOfferPoints());
        offer.setUpdatedAt(LocalDateTime.now());

        offerRepo.save(offer);

        return ResponseEntity.ok(
                new UpdateExistingOfferResponse(
                        true,
                        "Angebot aktualisiert"
                )
        );

    }

    @DeleteMapping("/delete-offer/{offerId}")
    public ResponseEntity<DeleteOfferResponse> deleteOffer(@PathVariable String offerId, Authentication authentication) {
        String userId = authentication.getName();

        Offer offer = offerRepo.findByOfferId(offerId)
                .orElseThrow(() -> new RuntimeException("Kein Offer gefunden"));

        LocalDateTime createdAt = offer.getCreatedAt();
        LocalDateTime now = LocalDateTime.now();
        long hoursSinceCreation = ChronoUnit.HOURS.between(createdAt, now);

        System.out.println("🕐 Erstellt am: " + createdAt);
        System.out.println("🕐 Jetzt: " + now);
        System.out.println("🕐 Stunden vergangen: " + hoursSinceCreation);

        if (hoursSinceCreation >= 1) {
            return ResponseEntity.status(403)
                    .body(new DeleteOfferResponse(
                            false,
                            "Angebot kann nur innerhalb 1 Stunde gelöscht werden"
                    ));
        }

        System.out.println("Lösche Offer");
        offerRepo.delete(offer);

        return ResponseEntity.ok(
                new DeleteOfferResponse(
                        true,
                        "Angebot wurde erfolgreich gelöscht"
                )
        );


    }




    @GetMapping("/myoffers")
    public ResponseEntity<List<CreateOfferResponse>> getUserOffers(Authentication authentication) {

        String companyId = (String) authentication.getPrincipal();

        System.out.println("Fetching offers for userId = " + companyId);

        List<CreateOfferResponse> offers = offerService.getOffersbyUserId(companyId);

        return ResponseEntity.ok(offers);
    }

}
