package ipAddress;

import java.net.*;
import javax.swing.DefaultListModel;

public class NetworkPing {

    public void showConnectedComputers(final DefaultListModel model) throws Exception{
        final String[] ipAddresses = new GetMyIpAddress().ipAddress();
        InetAddress inetAddress = InetAddress.getByName(ipAddresses[0]);
        byte[] ip = inetAddress.getAddress();
        for (int i = 1; i < 255; i++) {
            ip[3] = (byte) i;
            inetAddress = InetAddress.getByAddress(ip);
            if (inetAddress.isReachable(1000)) {
                System.out.println(inetAddress + " machine is turned on and can be pinged");
                model.addElement(inetAddress + " " + inetAddress.getCanonicalHostName());
            }
        }
        if((ipAddresses[1] != null) && (ipAddresses[1].length() > 1)) {
            new Thread(() -> {
                try {
                    InetAddress inetAddress1 = InetAddress.getByName(ipAddresses[1]);
                    byte[] ip1 = inetAddress1.getAddress();
                    for (int i = 1; i < 255; i++) {
                        ip1[3] = (byte) i;
                        inetAddress1 = InetAddress.getByAddress(ip1);
                        if (inetAddress1.isReachable(1000)) {
                            System.out.println(inetAddress1 + " machine is turned on and can be pinged");
                            model.addElement(inetAddress1 + " " + inetAddress1.getHostName());
                        }
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}