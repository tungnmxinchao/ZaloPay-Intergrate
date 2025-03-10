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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.http.NameValuePair;
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
@WebServlet(name = "CreateOrderServlet", urlPatterns = {"/createOrder"})
public class CreateOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Random rand = new Random();
            int randomId = rand.nextInt(1000000);

            // Tạo dữ liệu đơn hàng
            Map<String, Object> order = new HashMap<>();
            order.put("app_id", ZaloPayConfig.APP_ID);
            order.put("app_trans_id", ZaloPayConfig.getCurrentTimeString("yyMMdd") + "_" + randomId);
            order.put("app_time", System.currentTimeMillis());
            order.put("app_user", "user123");
            order.put("amount", 50000); // Số tiền cố định, bạn có thể lấy từ form
            order.put("description", "Payment for order #" + randomId);
            order.put("bank_code", "SBIS");
            order.put("callback_url", ZaloPayConfig.CALLBACK_URL);
            order.put("item", "[]");
            order.put("embed_data", "{}");

            // Tạo chuỗi hmac_input
            String data = order.get("app_id") + "|"
                    + order.get("app_trans_id") + "|"
                    + order.get("app_user") + "|"
                    + order.get("amount") + "|"
                    + order.get("app_time") + "|"
                    + order.get("embed_data") + "|"
                    + order.get("item");

            // Tạo mã MAC
            String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, ZaloPayConfig.KEY1, data);
            order.put("mac", mac);

            // Gửi request tới ZaloPay
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(ZaloPayConfig.ENDPOINT);

            List<NameValuePair> params = new ArrayList<>();
            for (Map.Entry<String, Object> e : order.entrySet()) {
                params.add(new BasicNameValuePair(e.getKey(), e.getValue().toString()));
            }

            post.setEntity(new UrlEncodedFormEntity(params));
            CloseableHttpResponse res = client.execute(post);

            // Đọc response
            BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
            StringBuilder resultJsonStr = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                resultJsonStr.append(line);
            }

            // Parse JSON response
            JSONObject result = new JSONObject(resultJsonStr.toString());

            if (result.getInt("return_code") == 1) {
                // Lưu thông tin đơn hàng vào database (nếu cần) trước khi redirect
                String appTransId = (String) order.get("app_trans_id");
                // TODO: Thêm logic lưu vào database: OrderService.saveOrder(appTransId, amount, "PENDING");

                request.setAttribute("result", result.toString());
                request.setAttribute("orderUrl", result.getString("order_url"));
                request.getRequestDispatcher("/paymentResult.jsp").forward(request, response);
            } else {
                // Xử lý lỗi từ ZaloPay
                request.setAttribute("error", result.getString("return_message"));
                request.getRequestDispatcher("/paymentError.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
