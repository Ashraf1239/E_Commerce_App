package com.ecommerce.library.utils;

import java.util.Base64;

public class ImageUtil {
    public String getImgData(byte[] byteData) {
        System.out.println(byteData);
        return Base64.getEncoder().encodeToString(byteData);
    }
}
