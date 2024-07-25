package com.scm.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageServices {

    String uploadimage(MultipartFile contactImage, String filename);

    String getUrlFromPublicId(String publicId);

}
