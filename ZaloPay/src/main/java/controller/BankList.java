/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

/**
 *
 * @author TNO
 */
public class BankList extends HttpServlet {

    private static final String APP_ID = "2553";
    private static final String KEY_1 = "PcY4iZIKFCIdgZvA6ueMcMHHUbRLYjPL";
    private static final String BANK_LIST_URL = "https://sbgateway.zalopay.vn/api/getlistmerchantbanks";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String reqtime = String.valueOf(System.currentTimeMillis() / 1000);
            String data = APP_ID + "|" + reqtime;
            String mac = hmacSHA256(KEY_1, data);

            // Tạo request parameters
            TreeMap<String, String> params = new TreeMap<>();
            params.put("appid", APP_ID);
            params.put("reqtime", reqtime);
            params.put("mac", mac);

            // Gửi request đến ZaloPay API
            JSONObject result = sendPostRequest(BANK_LIST_URL, params);

            response.setContentType("application/json");
            response.getWriter().write(result.toString());
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private JSONObject sendPostRequest(String urlString, TreeMap<String, String> params) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        StringBuilder postData = new StringBuilder();
        for (String key : params.keySet()) {
            if (postData.length() > 0) {
                postData.append("&");
            }
            postData.append(key).append("=").append(params.get(key));
        }

        try (OutputStream os = conn.getOutputStream()) {
            os.write(postData.toString().getBytes(StandardCharsets.UTF_8));
        }

        Scanner scanner = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8);
        String responseText = scanner.useDelimiter("\\A").next();
        scanner.close();

        return new JSONObject(responseText);
    }

    private String hmacSHA256(String key, String data) throws Exception {
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256Hmac.init(secretKey);
        byte[] hash = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

}
