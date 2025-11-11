package org.sascha.qrbackend.Offer;

import org.sascha.qrbackend.User.DTO.CreateOfferRequest;
import org.sascha.qrbackend.User.DTO.CreateOfferResponse;
import org.sascha.qrbackend.User.DTO.GetCompanyListResponse;
import org.sascha.qrbackend.User.DTO.GetSingleCompanyListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/offer")
public class OfferController {
    private final OfferService offerService;

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
        @PathVariable String companyId) {

        System.out.println("Suche alle Angebote einer Company");

        List<GetSingleCompanyListResponse> singleCompanyOffers = offerService.getSingleOffersByCompanyId(companyId);

        return ResponseEntity.ok(singleCompanyOffers);

    }


    @GetMapping("/myoffers")
    public ResponseEntity<List<CreateOfferResponse>> getUserOffers(Authentication authentication) {

        String companyId = (String) authentication.getPrincipal();

        System.out.println("Fetching offers for userId = " + companyId);

        List<CreateOfferResponse> offers = offerService.getOffersbyUserId(companyId);

        return ResponseEntity.ok(offers);
    }

}
