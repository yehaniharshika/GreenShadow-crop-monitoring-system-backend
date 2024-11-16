package lk.ijse.greenshadowcropmonitoringsystembackend.util;

import java.util.Base64;

public class AppUtil {
    public static String fieldImageToBase64(byte[] fieldImage){
        //fieldImage convert to base64 format
        return Base64.getEncoder().encodeToString(fieldImage);
    }

    public static String cropImageToBase64(byte[] cropImage){
        //cropImage convert to base64 format
        return Base64.getEncoder().encodeToString(cropImage);
    }

    public static String LogObservedImageToBase64(byte[] observedImage){
        //cropImage convert to base64 format
        return Base64.getEncoder().encodeToString(observedImage);
    }

}
