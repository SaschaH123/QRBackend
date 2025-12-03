package org.sascha.qrbackend.Image;

import org.sascha.qrbackend.Offer.Offer;
import org.sascha.qrbackend.Offer.OfferRepo;
import org.sascha.qrbackend.User.DTO.UploadImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/images")
public class ImageUploadController {

    private final ImageService imageService;

    private final OfferRepo offerRepo;

    public ImageUploadController(ImageService imageService, OfferRepo offerRepo) {
        this.imageService = imageService;
        this.offerRepo = offerRepo;
    }

    @PostMapping("/upload/offer/{offerId}")
    public ResponseEntity<?> uploadOfferImage(
            @PathVariable String offerId,
            @RequestParam("file") MultipartFile file) {

        try {
            // Bild speichern
            String imageUrl = imageService.saveOfferImage(offerId, file);

            // Offer aktualisieren
            Offer offer = offerRepo.findById(offerId)
                    .orElseThrow(() -> new IllegalArgumentException("Offer existiert nicht"));

            offer.setImageURL(imageUrl);
            offerRepo.save(offer);

            UploadImageResponse response = new UploadImageResponse(imageUrl, "Bild hochgeladen",true );
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            UploadImageResponse response = new UploadImageResponse(null, e.getMessage(), false);
            return ResponseEntity.status(500).body(response);
        }
    }
    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        try {
            System.out.println("📥 Bild angefordert: " + fileName);

            byte[] imageBytes = imageService.loadImage(fileName);

            System.out.println("✅ Bild geladen: " + imageBytes.length + " bytes");

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageBytes);

        } catch (Exception e) {
            System.err.println("❌ Bild nicht gefunden: " + fileName);
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
