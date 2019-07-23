package xyz.thloml.proj.portscaner.util;

import java.util.ArrayList;
import java.util.List;

public class IPUtil {

	public List<String> getIPAround(String ip1, String ip2){
		assert ipCheck(ip1);
		assert ipCheck(ip2);
		List<String> ips = new ArrayList<String>();
		//取a位比较大小
		String[] ip1Split = ip1.split(".");
		String[] ip2Split = ip2.split(".");
		int ip1A = Integer.parseInt(ip1Split[0]);
		int ip2A = Integer.parseInt(ip2Split[0]);
		//默认IP1比IP2大
		if(ip1A<ip2A) {
			
		}
		return ips;
	}
	/**
	 * 校验IP地址是否正确
	 * @param ip
	 * @return
	 */
	public static boolean ipCheck(String ip) {
        if (ip != null && ip.trim().length() > 0) {
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."+
                      "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
                      "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
                      "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            if (ip.matches(regex)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
