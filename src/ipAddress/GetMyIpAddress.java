package ipAddress;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;
public class GetMyIpAddress {

    String[] ipAddresses = new String[5];
    String temp;
    int j = 0;

    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public static boolean validateIP(final String ip) {
        return PATTERN.matcher(ip).matches();
    }

    public boolean validatePort(String portNumber) {
        if ((portNumber != null) && (portNumber.length() == 4) && (portNumber.matches(".*\\d.*"))) {
            return Integer.parseInt(portNumber) > 1023;
        }
        else
            return false;
    }

    public String[] ipAddress() {
        Enumeration e = null;
        try {
            e = NetworkInterface.getNetworkInterfaces();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        while (true) {
            assert e != null;
            if (!e.hasMoreElements()) break;
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration<InetAddress> ee = n.getInetAddresses();
            while (ee.hasMoreElements()) {
                InetAddress i = ee.nextElement();
                temp = i.getHostAddress();
                System.out.println(temp);
                if((temp.charAt(1) == '7' || temp.charAt(1) == '9') && (temp.charAt(2) == '2')) {
                    ipAddresses[j] = temp;
                    j++;
                }
            }
        }
        if (ipAddresses[0] == null) {
            ipAddresses[0] = "127.0.0.1";
            ipAddresses[1] = "";
        } else if (ipAddresses[1] == null) {
            ipAddresses[1] = " ";
        }
        return ipAddresses;
    }

    public static void main(String[] args) {
        new GetMyIpAddress().ipAddress();
    }

}