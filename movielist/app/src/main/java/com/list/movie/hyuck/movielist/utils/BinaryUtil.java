package com.list.movie.hyuck.movielist.utils;

public class BinaryUtil {
    public static String binaryArrayToString(String binaryArray[]) {
        StringBuilder encodingBuilder = new StringBuilder();
        for(String target : binaryArray) {
            int binary = Integer.parseInt(target, 2);
            char ASCIICode = (char) binary;
            encodingBuilder.append(ASCIICode);
        }

        return encodingBuilder.toString();
    }
}
