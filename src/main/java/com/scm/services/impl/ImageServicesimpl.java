package com.scm.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scm.services.ImageServices;

@Service
public class ImageServicesimpl implements ImageServices {

    @Override
    public String uploadimage(MultipartFile contactImage) {
        return "";
        
    }

}
