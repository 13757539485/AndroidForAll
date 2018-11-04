//
// Decompiled by Jadx - 837ms
//
package com.android.advancesettings.utils;

import android.content.Context;
import android.os.Build.VERSION;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadWifi {
    private ArrayList<Map<String, String>> list = new ArrayList<>();

    public ReadWifi(String str) throws Throwable {
        DataOutputStream dataOutputStream;
        BufferedReader bufferedReader;
        DataOutputStream dataOutputStream2;
        String str2;
        StringBuilder str3 = null;
        Throwable th;
        String str4 = "";
        try {
            Process exec = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(exec.getOutputStream());
            try {
                dataOutputStream.writeBytes("cat " + str + "\n");
                dataOutputStream.writeBytes("exit\n");
                dataOutputStream.flush();
                bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
            } catch (IOException e2) {
                dataOutputStream2 = dataOutputStream;
                str2 = str4;
                try {
                    str3 = new StringBuilder(str2);
                    dataOutputStream = dataOutputStream2;
                    bufferedReader = null;
                    dataOutputStream.close();
                    pattern(str3.toString());
                } catch (Throwable th2) {
                    th = th2;
                    dataOutputStream = dataOutputStream2;
                    dataOutputStream.close();
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                dataOutputStream.close();
                throw th;
            }
            try {
                String str5 = null;
                str3 = new StringBuilder(str4);
                while (true) {
                    try {
                        if (bufferedReader != null) {
                            str5 = bufferedReader.readLine();
                        }
                        if (str5 == null) {
                            break;
                        }
                        str3.append(str5.trim()).append("\n");
                    } catch (Throwable ignored) {
                    }
                }
            } catch (Throwable ignored) {
            }
        } catch (IOException e5) {
            str2 = str4;
            str3 = new StringBuilder(str2);
            dataOutputStream = null;
            bufferedReader = null;
            pattern(str3.toString());
        } catch (Throwable th5) {
            th = th5;
            throw th;
        }
        if (dataOutputStream != null) {
            dataOutputStream.close();
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        if (str3 != null) {
            pattern(str3.toString());
        }
    }

    private void add(String str, int i, int i2) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("view", str);
        hashMap.put("pos", String.valueOf(i) + "," + i2);
        String[] split = str.substring(9, str.length() - 2).split("\\n");
        for (String str2 : split) {
            int indexOf = str2.indexOf("=");
            if (indexOf > -1) {
                String substring = str2.substring(0, indexOf);
                String substring2 = str2.substring(indexOf + 1);
                if ("ssid".equals(substring)) {
                    substring2 = substring2.charAt(0) == '\"' ? substring2.substring(1, substring2.length() - 1) : toUTF8(substring2);
                } else if ("psk".equals(substring)) {
                    substring2 = substring2.substring(1, substring2.length() - 1);
                }
                if (substring2 != null) {
                    hashMap.put(substring, substring2);
                }
            }
        }
        this.list.add(hashMap);
    }

    private void addOreo(String str, int i, int i2) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("pos", String.valueOf(i) + "," + i2);
        String[] split = str.split("\\n");
        for (String str2 : split) {
            if (str2.contains("name=\"SSID\">")) {
                hashMap.put("ssid", getSubUtilSimple(str2, "name=\"SSID\">&quot;(.*?)&quot;"));
            } else if (str2.contains("name=\"PreSharedKey\"") && !str2.contains("null")) {
                hashMap.put("psk", getSubUtilSimple(str2, "name=\"PreSharedKey\">&quot;(.*?)&quot;"));
            }
        }
        hashMap.put("view", str);
        this.list.add(hashMap);
    }

    private static String getSubUtilSimple(String str, String str2) {
        Matcher matcher = Pattern.compile(str2).matcher(str);
        return !matcher.find() ? "" : matcher.group(1);
    }

    private void pattern(String str) {
        Matcher matcher;
        if (VERSION.SDK_INT >= 26) {
            matcher = Pattern.compile("<Network>\\n([\\s\\S]+?)\\n\\</Network>").matcher(str);
            while (matcher.find()) {
                addOreo(matcher.group(), matcher.start(), matcher.end());
            }
            return;
        }
        matcher = Pattern.compile("network=\\{\\n([\\s\\S]+?)\\n\\}").matcher(str);
        while (matcher.find()) {
            add(matcher.group(), matcher.start(), matcher.end());
        }
    }

    private static String toUTF8(String str) {
        int i = 0;
        if (str == null || str.equals("")) {
            return null;
        }
        try {
            String toUpperCase = str.toUpperCase();
            int length = toUpperCase.length() / 2;
            byte[] bArr = new byte[length];
            int i2 = 0;
            while (i < length) {
                int i3 = i * 2;
                bArr[i] = (byte) Integer.parseInt(toUpperCase.substring(i3, i3 + 2), 16);
                i2++;
                i++;
            }
            return new String(bArr, 0, i2, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public ArrayList<Map<String, String>> getList(Context context) {
        ArrayList<Map<String, String>> arrayList = new ArrayList<>();
        for (Map<String, String> map : this.list) {
            if (map.containsKey("ssid") && map.containsKey("psk")) {
                arrayList.add(map);
            }
        }
        return arrayList;
    }
}