package lk.ijse.greenshadowcropmonitoringsystembackend.util;

import java.util.Base64;

public class AppUtil {
    public static String fieldImageToBase64(byte[] fieldImage){
        //fieldImage convert to base64 format
        return Base64.getEncoder().encodeToString(fieldImage);
    }

}
