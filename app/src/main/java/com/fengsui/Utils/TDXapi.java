package com.fengsui.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TDXapi {
    public static void main(String[] args) throws Exception {
        URL tokenUrl = new URL("https://tdx.transportdata.tw/auth/realms/TDXConnect/protocol/openid-connect/token");
        URL tdxUrl = new URL("https://tdx.transportdata.tw/api/basic/v2/Air/FIDS/Airport/TPE?%24top=30&%24format=JSON");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "client_credentials");
        params.put("client_id", "yichun0701-81b0ddd8-facb-4da6");
        params.put("client_secret", "39abe0a6-3fe5-4858-8c2d-0c62415aab27");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String tokenInfo = postJsonString(tokenUrl);
        JsonNode tokenElem = objectMapper.readTree(tokenInfo);

        String accessToken = tokenElem.get("access_token").asText();
        //headers.put("authorization", String.format("Bearer %s", accessToken));
        String resultJson = getJsonString(tdxUrl);
        System.out.println(resultJson);
    }

    public static String getJsonString(URL tdxUrl) throws Exception {
        return "";
    }

    public static String postJsonString(URL tokenUrl) throws Exception {
        HttpURLConnection urlConnection = (HttpURLConnection) tokenUrl.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setUseCaches(false);
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.addRequestProperty("content-type", "application/x-www-form-urlencoded");

        int responseCode = urlConnection.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST request did not work.");
        }
        return "";
    }
}
