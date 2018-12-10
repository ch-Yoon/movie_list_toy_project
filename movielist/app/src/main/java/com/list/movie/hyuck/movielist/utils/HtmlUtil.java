package com.list.movie.hyuck.movielist.utils;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

public class HtmlUtil {
    static public SpannableStringBuilder applyBoldToText(String text) {
        SpannableStringBuilder applyBoldBuilder = new SpannableStringBuilder();
        if(text == null || text.length() == 0) {
            return applyBoldBuilder.append("");
        } else {
            String boldTagHead = "<b>";
            String boldTagTail = "</b>";

            StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
            String remainingText = text;
            int remainingTextStart = 0;
            while(remainingText.contains(boldTagHead) && remainingText.contains(boldTagTail)) {
                int frontOfBoldTagHead = remainingText.indexOf(boldTagHead);
                int backOfBoldTagHead = frontOfBoldTagHead + boldTagHead.length();
                int fontOfBoldTagTail = remainingText.indexOf(boldTagTail);
                int backOfBoldTagTail = fontOfBoldTagTail + boldTagTail.length();

                String frontOfBoldText = remainingText.substring(0, frontOfBoldTagHead);
                String boldText = remainingText.substring(backOfBoldTagHead, fontOfBoldTagTail);
                String backOfBoldText = remainingText.substring(backOfBoldTagTail);

                int boldTextStart = remainingTextStart + frontOfBoldText.length();
                int boldTextEnd = boldTextStart + boldText.length();

                applyBoldBuilder.append(frontOfBoldText);
                applyBoldBuilder.append(boldText);
                applyBoldBuilder.setSpan(boldSpan, boldTextStart, boldTextEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                remainingTextStart = boldTextEnd;
                remainingText = backOfBoldText;
            }

            return applyBoldBuilder.append(remainingText);
        }
    }
}
