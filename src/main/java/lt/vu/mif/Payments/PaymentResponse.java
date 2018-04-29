package lt.vu.mif.Payments;

import lombok.Getter;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

public class PaymentResponse {

    public enum PaymentStatus {
        Success,
        Error
    }

    @Getter
    private PaymentError error = null;
    @Getter
    private PaymentStatus status;

    PaymentResponse(int responseCode, JSONObject responseBody) {
        switch (responseCode) {
            case 201:
                status = PaymentStatus.Success;
                break;
            case 400:
            case 402:
            case 404:
                status = PaymentStatus.Error;
                try {
                    error = new PaymentError(responseBody.getString("error"),
                        responseBody.getString("message"));
                } catch (JSONException ignored) {
                }
                break;
            case 401:
            default:
                status = PaymentStatus.Error;
                error = new PaymentError("Unknown error", "Unknown error has occured.");
                break;
        }
    }

    public boolean isSuccess() {
        return status == PaymentStatus.Success;
    }
}
