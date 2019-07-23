package xyz.thloml.proj.portscaner.vo;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import xyz.thloml.proj.portscaner.util.IPUtil;

public class IP implements Comparable<IP>{

	private int a;
	
	private int b;
	
	private int c;
	
	private int d;
	
	public IP(String ip) {
		assert IPUtil.ipCheck(ip);
		String[] ipSplit = ip.split("\\.");
		this.a = Integer.parseInt(ipSplit[0]);
		this.b = Integer.parseInt(ipSplit[1]);
		this.c = Integer.parseInt(ipSplit[2]);
		this.d = Integer.parseInt(ipSplit[3]);
	}

	public IP(int a, int b, int c, int d) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}
	
	public String toString() {
		return ""+a+"."+b+"."+c+"."+d;
	}

	@Override
	public int compareTo(IP o) {
		if(this.a == o.a && this.b == o.b && this.c == o.c && this.d == o.d) {
			return 0;
		}
		if(this.a > o.a) {
			return 1;
		}
		if(this.b > o.b) {
			return 1;
		}
		if(this.c > o.c) {
			return 1;
		}
		if(this.d > o.d) {
			return 1;
		}
		return -1;
	}
	
	public static void main(String[] args) {
		IP ip1 = new IP("192.168.123.133");
		IP ip2 = new IP("192.168.123.134");
		SortedSet<IP> sIP = new TreeSet<>();
		sIP.add(new IP("192.168.1.134"));
		sIP.add(ip1);
		sIP.add(ip2);
		System.out.println(Arrays.toString(sIP.toArray(new IP[2])));
	}
}
