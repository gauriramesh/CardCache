package com.google.android.gms.samples.vision.ocrreader;

/**
 * Created by Troy on 10/15/2017.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Troy on 10/14/2017.
 */

public class DataParse {
    private static String sanitize(String string) {
        try {
            return string.trim().toLowerCase();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static JSONObject makeCard(String firstName, String lastName, String email, String company, String position) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("firstName", sanitize(firstName));
            jsonObject.put("lastName", sanitize(lastName));
            jsonObject.put("email", sanitize(email));
            jsonObject.put("company", sanitize(company));
            jsonObject.put("position", sanitize(position));
        } catch(JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static JSONObject extractCardInfo(ArrayList<String> strings) {
        String name = ParseUtils.getName(strings);
        String[] names = name.split(" ");

        String email = ParseUtils.getEmail(strings);
        //String company = ParseUtils.getCompany(email);
        String phone = ParseUtils.getPhone(strings);
        String position = ParseUtils.getPosition(strings);

        return makeCard(names[0], names[1], email, phone, position);
    }

    public static String getJSONString(List<JSONObject> cards) {
        return new JSONArray(cards).toString();
    }

    public static ArrayList<JSONObject> parseCardJSON(String jsonString) {
        ArrayList<JSONObject> cards = new ArrayList<JSONObject>();

        JSONArray tempCards = null;
        try {
            tempCards = new JSONArray(jsonString);
        } catch(JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < tempCards.length(); i++) {
            try {
                cards.add(tempCards.getJSONObject(i));
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }

        return cards;
    }

    static class CustomAdapter extends ArrayAdapter<JSONObject> {

        public CustomAdapter(Context context, ArrayList<JSONObject> businessCards) {
            super(context, R.layout.custom_rows, businessCards);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater cardInflator = LayoutInflater.from(getContext());
            View customView = cardInflator.inflate(R.layout.custom_rows, parent, false);

            JSONObject current = getItem(position);

            String company = null;
            String job = null;
            String job2 = null;
            try {
                company = (String)current.get("company");
                job = (String)current.get("position");
                job2 = company;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String companyf = company.substring(0, 1).toUpperCase() + company.substring(1) + " - " + job.substring(0, 1).toUpperCase() + job.substring(1);

            String fname = null;
            String lname = null;
            try {
                fname = (String)current.get("firstName");
                lname = (String)current.get("lastName");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String name = fname.substring(0, 1).toUpperCase() + fname.substring(1) + " " + lname.substring(0, 1).toUpperCase() + lname.substring(1);

            TextView cardText = (TextView) customView.findViewById(R.id.name);
            TextView cardText2 = (TextView) customView.findViewById(R.id.company_and_position);
            ImageView logo = (ImageView) customView.findViewById(R.id.logo);
            /*
            switch (job2) {
                case "atlassian":
                    logo.setImageResource(R.mipmap.atlassian);
                    break;
                case "blackrock":
                    logo.setImageResource(R.mipmap.blackrock);
                    break;
                case "capital one":
                    logo.setImageResource(R.mipmap.capitalone);
                    break;
                case "delta":
                    logo.setImageResource(R.mipmap.delta);
                    break;
                case "dice":
                    logo.setImageResource(R.mipmap.dice);
                    break;
                case "finra":
                    logo.setImageResource(R.mipmap.finra);
                    break;
                case "ge digital":
                    logo.setImageResource(R.mipmap.gedigital);
                    break;
                case "general motors":
                    logo.setImageResource(R.mipmap.generalmotors);
                    break;
                case "georgia tech":
                    logo.setImageResource(R.mipmap.georgiatech);
                    break;
                case "goldman sachs":
                    logo.setImageResource(R.mipmap.goldmansachs);
                    break;
                case "honeywell":
                    logo.setImageResource(R.mipmap.honeywell);
                    break;
                case "manhattan associates":
                    logo.setImageResource(R.mipmap.manhattanassociates);
                    break;
                case "microsoft":
                    logo.setImageResource(R.mipmap.microsoft);
                    break;
                case "mlh":
                    logo.setImageResource(R.mipmap.mlh);
                    break;
                case "ncr":
                    logo.setImageResource(R.mipmap.ncr);
                    break;
                case "qualtrics":
                    logo.setImageResource(R.mipmap.qualtrics);
                    break;
                case "siemens":
                    logo.setImageResource(R.mipmap.siemens);
                    break;
                case "suntrust":
                    logo.setImageResource(R.mipmap.suntrust);
                    break;
                default:
                    logo.setImageResource(R.mipmap.dice);
                    break;
            }*/

            cardText.setText(name);
            cardText2.setText(companyf);

            return customView;
        }
    }
}
