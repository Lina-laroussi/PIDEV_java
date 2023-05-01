//package GUI;
//
//import com.stripe.Stripe;
//import com.stripe.exception.StripeException;
//import com.stripe.model.Charge;
//import com.stripe.model.Token;
//import com.stripe.param.ChargeCreateParams;
//import com.stripe.param.TokenCreateParams;
//
//public class StripePayment {
//    public static boolean processPayment(float amount, String token) {
//        // Initialize the Stripe secret key
//        Stripe.apiKey = "sk_test_51MexHOEHtgKqVnqGoOdGOXC554rKgAtVYgc7j1kv2ZyAPAbozfHaRCjDObfqfn1s5iIa8Cv4mS8DA7ktXYb5yqj900h06MM6Yl";
//
//        try {
//            // Create a ChargeCreateParams object with the amount and token
//            ChargeCreateParams chargeParams = ChargeCreateParams.builder()
//                    .setAmount((long) (amount * 100))
//                    .setCurrency("usd")
//                    .setSource(token)
//                    .build();
//
//            // Create a Charge object using the ChargeCreateParams object
//            Charge charge = Charge.create(chargeParams);
//
//            // Check if the payment was successful
//            if (charge.getPaid()) {
//                System.out.println("Payment processed successfully!");
//                return true;
//            } else {
//                System.out.println("Payment processing failed.");
//                return false;
//            }
//        } catch (StripeException e) {
//            System.out.println("Error processing payment: " + e.getMessage());
//            return false;
//        }
//    }
//}
