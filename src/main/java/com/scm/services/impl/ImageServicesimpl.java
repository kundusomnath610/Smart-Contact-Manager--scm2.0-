package com.scm.services.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.services.ImageServices;

@Service
public class ImageServicesimpl implements ImageServices {

    private Cloudinary cloudinary;



    public ImageServicesimpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadimage(MultipartFile contactImage) {

        String filename = UUID.randomUUID().toString();
        

        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                "public_id", contactImage.getOriginalFilename()
            ));
            return this.getUrlFromPublicId(filename);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String getUrlFromPublicId(String publicId) {

        return cloudinary
        .url()
        .transformation(
            new Transformation<>()
            .width(500)
            .height(500)
            .crop("fill")
        )
        .generate(publicId);

    }
}
