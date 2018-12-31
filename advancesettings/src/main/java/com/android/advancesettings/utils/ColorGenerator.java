package com.android.advancesettings.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ColorGenerator {

    public static ColorGenerator DEFAULT;

    public static ColorGenerator MATERIAL;

    static {
        DEFAULT = create(Arrays.asList(
                0xfff16364,
                0xfff58559,
                0xfff9a43e,
                0xffe4c62e,
                0xff67bf74,
                0xff59a2be,
                0xff2093cd,
                0xffad62a7,
                0xff805781
        ));
        MATERIAL = create(Arrays.asList(
                0xffe57373,
                0xfff06292,
                0xffba68c8,
                0xff9575cd,
                0xff7986cb,
                0xff64b5f6,
                0xff4fc3f7,
                0xff4dd0e1,
                0xff4db6ac,
                0xff81c784,
                0xffaed581,
                0xffff8a65,
                0xffd4e157,
                0xffffd54f,
                0xffffb74d,
                0xffa1887f,
                0xff90a4ae
        ));
    }

    private final List<Integer> mColors;
    private final Random mRandom;

    public static ColorGenerator create(List<Integer> colorList) {
        return new ColorGenerator(colorList);
    }

    private ColorGenerator(List<Integer> colorList) {
        mColors = colorList;
        mRandom = new Random(System.currentTimeMillis());
    }

    public static void setViewTextByFilterColor(TextView v, String text, String prefix, boolean isDisable) {
        if (!TextUtils.isEmpty(prefix) && !TextUtils.isEmpty(text)) {
            String texts;
            String prefixs;
            texts = text.trim();
            prefixs = prefix.trim();
            if (prefix.length() <= text.length()) {
                String textString = texts.toUpperCase();
                String prefixString = prefixs.toUpperCase();
                if (textString.contains(prefixString) && !TextUtils.isEmpty(textString) &&
                        !TextUtils.isEmpty(prefixString)) {
                    v.setText(colorText(texts, textString, prefixString, isDisable));
                    return;
                }
            }

        }
        v.setText(isDisable ? getDisableText(new SpannableString(text), text) : text);
    }

    private static CharSequence colorText(String text, String textString, String prefixString, boolean isDisable) {
        int start = textString.indexOf(prefixString);
        int end = start + prefixString.length();
        SpannableString ss = new SpannableString(text);
        if (isDisable) {
            ss = getDisableText(ss, text);
        }
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#0980F7")),
                start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return ss;
    }

    private static SpannableString getDisableText(SpannableString spannableString, String text) {
        StrikethroughSpan span = new StrikethroughSpan();
        spannableString.setSpan(span, 0, text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

}