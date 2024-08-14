package com.nchowf.tutorlinking.utils;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;
    @Transactional
    public String uploadFile(final MultipartFile file, final String fileName) {
        try {
            final Map    result   = this.cloudinary.uploader()
                    .upload(file.getBytes(),
                            Map.of("public_id",
                                    "tutor_linking/tutor_img/"
                                            + fileName));
            return (String) result.get("secure_url");
        } catch (final Exception e) {
            throw new RuntimeException("Tải ảnh thất bại. Hãy thử lại sau");
        }
    }
}
