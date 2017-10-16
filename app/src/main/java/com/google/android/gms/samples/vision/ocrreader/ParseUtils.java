package com.google.android.gms.samples.vision.ocrreader;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseUtils {

    public static ArrayList<String> parseArgs(ArrayList<String> rawStr){
        ArrayList<String> parsedStr = new ArrayList<>();
        for(int i = 0 ; i < rawStr.size() ; i++){
            String si = rawStr.get(i);
            Log.d("ADOMAS", si);
            String[] splited = si.split("\\s+");
            if (splited == null) continue;
            for(int j = 0 ; j < splited.length; j++){
                Log.d("ADOMAS", splited[j]);
                parsedStr.add(splited[j]);
            }
        }
        return parsedStr;
    }

    public static String getEmail(ArrayList<String> parts) {
        for(String part : parts) {
            if(part.contains("@")){
                return part;
            }
        }
        return null;
    }

    public static String getPhone(ArrayList<String> parts) {
        Pattern phoneRegex = Pattern.compile("\\(*\\d{3}\\)*[. -]*\\d{3}[. -]*\\d{4}");

        for (String part : parts) {
            Matcher match = phoneRegex.matcher(part);

            if (match.find()) {
                return match.group(0);
            }
        }

        return null;
    }

    public static String getCompany(String email) {
        Map<String, String> m = new HashMap<String, String>();
        m.put("gmail", "Google");
        m.put("outlook", "Microsoft");
        m.put("fb", "Facebook");

        String [] firstSplits = email.split("@");
        String [] a = firstSplits[1].split("\\.");

        if(m.containsKey(a[a.length-2])) {
            return m.get(a[a.length-2]);
        }
        return a[a.length - 2];
    }

    public static String getName(ArrayList<String> parts) {
        Pattern nameRegex = Pattern.compile("[A-Z][a-z]+ [A-Z][a-z]+");

        for (String part : parts) {
            Matcher match = nameRegex.matcher(part);

            if (match.find()) {
                return match.group(0);
            }
        }

        return null;
    }

    public static String getPosition(ArrayList<String> parts) {
        Pattern nameRegex = Pattern.compile("[A-Z][a-z]+ [A-Z][a-z]+");

        for (int i = 0; i < parts.size(); i++) {
            Matcher match = nameRegex.matcher(parts.get(i));

            if (match.find()) {
                return parts.get(i + 1);
            }
        }

        return null;
    }
}

