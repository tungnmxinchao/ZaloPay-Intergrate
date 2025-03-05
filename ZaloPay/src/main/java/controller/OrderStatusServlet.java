/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

/**
 *
 * @author TNO
 */
@WebServlet(name = "OrderStatusServlet", urlPatterns = {"/order-status"})
public class OrderStatusServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(OrderStatusServlet.class.getName());
    private static final String APP_ID = ZaloPayConfig.APP_ID;
    private static final String KEY1 = ZaloPayConfig.KEY1;
    private static final String ENDPOINT = "https://sb-openapi.zalopay.vn/v2/query";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String appTransId = request.getParameter("app_trans_id");
        if (appTransId == null || appTransId.isEmpty()) {
            response.getWriter().write(new JSONObject().put("error", "Missing app_trans_id").toString());
            return;
        }

        try {
            // Tạo dữ liệu MAC
            String data = APP_ID + "|" + appTransId + "|" + KEY1;
            String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, KEY1, data);

            // Tạo danh sách tham số
            List<BasicNameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("app_id", APP_ID));
            params.add(new BasicNameValuePair("app_trans_id", appTransId));
            params.add(new BasicNameValuePair("mac", mac));

            // Gửi yêu cầu HTTP POST
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpPost post = new HttpPost(ENDPOINT);
                post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

                try (CloseableHttpResponse res = client.execute(post); BufferedReader reader = new BufferedReader(new InputStreamReader(res.getEntity().getContent()))) {

                    StringBuilder resultJsonStr = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        resultJsonStr.append(line);
                    }

                    JSONObject result = new JSONObject(resultJsonStr.toString());
                    response.getWriter().write(result.toString());
                }
            }
        } catch (Exception e) {
            logger.severe("Error fetching order status: " + e.getMessage());
            response.getWriter().write(new JSONObject().put("error", "Internal server error").toString());
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
