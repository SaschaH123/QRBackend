package org.sascha.qrbackend.Image;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    public String saveOfferImage(String offerId, MultipartFile file) throws IOException;
    public byte[] loadImage(String fileName) throws IOException;
}
