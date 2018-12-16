package com.list.movie.hyuck.movielist.utils;

public class BinaryUtil {

    public static String binaryArrayToString(String binaryArray[]) {
        StringBuilder encodingBuilder = new StringBuilder();
        int targetRadix = 2;
        for(String target : binaryArray) {
            int binary = Integer.parseInt(target, targetRadix);
            char ASCIICode = (char) binary;
            encodingBuilder.append(ASCIICode);
        }

        return encodingBuilder.toString();
    }

}
