/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

/**
 *
 * @author TNO
 */
public class RedirectServlet extends HttpServlet {

    private static final String KEY_2 = "Iyz2habzyr7AG8SgvoBCbKwKi3UzlLi3";
    private static final Logger logger = Logger.getLogger(RedirectServlet.class.getName());
    private Mac HmacSHA256;

    public RedirectServlet() throws Exception {
        HmacSHA256 = Mac.getInstance("HmacSHA256");
        HmacSHA256.init(new SecretKeySpec(KEY_2.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        JSONObject jsonResponse = new JSONObject();

        try {
            String checksumData = parameterMap.get("appid")[0] + "|"
                    + parameterMap.get("apptransid")[0] + "|"
                    + parameterMap.get("pmcid")[0] + "|"
                    + parameterMap.get("bankcode")[0] + "|"
                    + parameterMap.get("amount")[0] + "|"
                    + parameterMap.get("discountamount")[0] + "|"
                    + parameterMap.get("status")[0];

            byte[] checksumBytes = HmacSHA256.doFinal(checksumData.getBytes(StandardCharsets.UTF_8));
            String checksum = DatatypeConverter.printHexBinary(checksumBytes).toLowerCase();

            if (!checksum.equals(parameterMap.get("checksum")[0])) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Bad Request - Invalid checksum");
                return;
            }

            jsonResponse.put("message", "Payment verified successfully");
            response.setContentType("application/json");
            response.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {
            logger.severe("Error processing ZaloPay redirect: " + e.getMessage());
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

}
