package org.sascha.qrbackend.Image;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private final String IMAGE_DIR = "images/";

    public ImageServiceImpl() throws IOException {
        Files.createDirectories(Paths.get(IMAGE_DIR));
    }

    public String saveOfferImage(String offerId, MultipartFile file) throws IOException {
        String fileName = "offer_" + offerId + ".png";
        Path filePath = Paths.get(IMAGE_DIR + fileName);

        Files.write(filePath, file.getBytes());

        return "api/images/" + fileName;
    }

    public byte[] loadImage(String fileName) throws IOException {
        Path filePath = Paths.get(IMAGE_DIR + fileName);

        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("Bild nicht gefunden");
        }

        return Files.readAllBytes(filePath);
    }

    public String saveCompanyImage(String companyId, MultipartFile file) throws IOException {

        var companyUUID = UUID.fromString(companyId);

        String fileName = "company_" + companyUUID + ".png";
        Path filePath = Paths.get(IMAGE_DIR + fileName);

        Files.write(filePath, file.getBytes());

        return "api/images/" + fileName;
    }

    public byte[] loadCompanyImage(String fileName) throws IOException {
        Path filePath = Paths.get(IMAGE_DIR + fileName);

        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("Bild nicht gefunden");
        }

        return Files.readAllBytes(filePath);
    }


}
