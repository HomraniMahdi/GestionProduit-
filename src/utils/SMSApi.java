package utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 *
 * @author Mahdi
 */
public class SMSApi {

    public SMSApi() {
    }
    public static final String ACCOUNT_SID = "AC8a77b4e0bf1695f5624ba67221be44a0";
    public static final String AUTH_TOKEN = "1e5ad0ed2a32350f2f8c7f1a96a64fe1";

    public void sendSMS(String num, String msg) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber("+21620071775"),
                new PhoneNumber("+13253087963"),
                "Produit Créer avec succes, " + msg).create();

        System.out.println(message.getSid());

    }
}
