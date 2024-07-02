package com.demo.file.utils;

import org.springframework.web.multipart.MultipartFile;

public class ImageUtils {
    public static String IMAGE_LINK = "https://product.hstatic.net/1000360022/product/id-005327_08c355529f9a4202a9061fea36a560ab.jpg";
    public static boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType != null) {
            return contentType.startsWith("image/");
        }
        return false;
    }
}
