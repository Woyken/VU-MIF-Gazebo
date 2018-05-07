package lt.vu.mif.payment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final String baseUrl = "https://mock-payment-processor.appspot.com/";
    private static final String RESTApiVersion = "v1/";

    @Value("${services.paymentService.serviceAccount.username}")
    private String serviceAccountUsername;
    @Value("${services.paymentService.serviceAccount.password}")
    private String serviceAccountPassword;

    public PaymentResponse MakePayment(int amount, String cardNumber, String holder,
        int expirationYear,
        int expirationMonth, String cvv) {
        final String requestUrl = "payment";
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("amount", amount);
            requestBody.put("number", cardNumber);
            requestBody.put("holder", holder);
            requestBody.put("exp_year", expirationYear);
            requestBody.put("exp_month", expirationMonth);
            requestBody.put("cvv", cvv);
        } catch (JSONException ignored) {
        }

        String authorizationToken = Base64
            .encodeBase64String((serviceAccountUsername + ":" + serviceAccountPassword).getBytes());

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost httpPost = new HttpPost(baseUrl + RESTApiVersion + requestUrl);
            httpPost.setEntity(new StringEntity(requestBody.toString()));
            httpPost.addHeader("content-type", "application/x-www-form-urlencoded");
            httpPost.setHeader("Authorization", "Basic " + authorizationToken);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            InputStream inputStream = responseEntity.getContent();
            Reader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = reader.read()) >= 0; ) {
                sb.append((char) c);
            }
            String responseStrJson = sb.toString();
            JSONObject responseJson = new JSONObject(responseStrJson);

            int statusCode = response.getStatusLine().getStatusCode();
            return new PaymentResponse(statusCode, responseJson);

        } catch (IOException | JSONException ignored) {
        }
        return new PaymentResponse(-1, null);
    }

}
