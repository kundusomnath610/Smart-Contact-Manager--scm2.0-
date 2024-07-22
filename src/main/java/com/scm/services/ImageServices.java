package com.scm.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageServices {

    String uploadimage(MultipartFile contactImage);

    String getUrlFromPublicId(String publicId);

}
