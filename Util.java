import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Util {

    private static final String validNumbers = "0123456789";
    private static final String allowedCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_- ";

    //Client side. Pulled from https://stackoverflow.com/a/11009612
    public static String sha256(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) 
                  hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception e){
           throw new RuntimeException(e);
        }
    }

    //Pulled from https://stackoverflow.com/a/52839327
    public static List<?> convertObjectToList(Object obj) {
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Object[])obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>)obj);
        }
        return list;
    }
    public static boolean isCollection(Object obj) {
        return obj.getClass().isArray() || obj instanceof Collection;
    }

    public static void sleep(double seconds) {
        try {
            TimeUnit.MILLISECONDS.sleep((int)(seconds*1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static boolean validNumber(String str) {
        if (str.length() > 100)
            return false;
        for (int i = 0; i < str.length(); i++) {
            if (validNumbers.indexOf(str.charAt(i)) == -1)
                return false;
        }
        return true;
    }

    public static boolean validString(String str) {
        if (str.length() > 100)
            return false;
        for (int i = 0; i < str.length(); i++) {
            if (allowedCharacters.indexOf(str.charAt(i)) == -1)
                return false;
        }
        return true;
    }
}