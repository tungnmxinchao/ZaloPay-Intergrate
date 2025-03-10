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
import jakarta.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

/**
 *
 * @author TNO
 */
@WebServlet(name = "CallbackServlet", urlPatterns = {"/callback"})
public class CallbackServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CallbackServlet.class.getName());
    private static final String KEY2 = ZaloPayConfig.KEY2;
    private Mac HmacSHA256;

    @Override
    public void init() throws ServletException {
        try {
            HmacSHA256 = Mac.getInstance("HmacSHA256");
            HmacSHA256.init(new SecretKeySpec(KEY2.getBytes("UTF-8"), "HmacSHA256"));
        } catch (Exception e) {
            throw new ServletException("Failed to initialize HMAC", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JSONObject result = new JSONObject();

        try {
            // Đọc dữ liệu JSON từ request body
            StringBuilder jsonStr = new StringBuilder();
            String line;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jsonStr.append(line);
            }

            if (jsonStr.length() == 0) {
                throw new IOException("Request body rỗng");
            }

            // Parse JSON từ callback
            JSONObject cbdata = new JSONObject(jsonStr.toString());

            if (!cbdata.has("data") || !cbdata.has("mac")) {
                throw new IOException("Dữ liệu callback không hợp lệ");
            }

            String dataStr = cbdata.getString("data");
            String reqMac = cbdata.getString("mac");

            // Tạo MAC để kiểm tra
            byte[] hashBytes = HmacSHA256.doFinal(dataStr.getBytes(StandardCharsets.UTF_8));

            // Chuyển đổi byte array sang hex string
            StringBuilder macBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                macBuilder.append(String.format("%02x", b));
            }
            String mac = macBuilder.toString();

            // Kiểm tra callback hợp lệ
            if (!reqMac.equals(mac)) {
                result.put("return_code", -1);
                result.put("return_message", "MAC không khớp");
            } else {
                // Callback hợp lệ - Xử lý đơn hàng
                JSONObject data = new JSONObject(dataStr);
                String appTransId = data.optString("app_trans_id", ""); // Lấy mã giao dịch
                long zpTransId = data.optLong("zp_trans_id", 0);
                long amount = data.optLong("amount", 0);

                // TODO: Cập nhật trạng thái đơn hàng trong database
                logger.info("Cập nhật trạng thái đơn hàng thành công: app_trans_id = " + appTransId);
                logger.info("ZaloPay transaction id: " + zpTransId);
                logger.info("Số tiền: " + amount);

                result.put("return_code", 1);
                result.put("return_message", "Thành công");
            }
        } catch (Exception ex) {
            // Nếu có lỗi, để ZaloPay callback lại (tối đa 3 lần)
            result.put("return_code", 0);
            result.put("return_message", ex.getMessage());
            logger.severe("Lỗi callback: " + ex.getMessage());
        }

        // Trả kết quả về cho ZaloPay
        out.write(result.toString());
        out.flush();

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
