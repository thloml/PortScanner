package xyz.thloml.proj.portscaner.vo;

public class Result {
	
	private String ip;

	private int port;
	
	private long timeTaken = -1;
	
	private boolean isOpen = false;

	public int getPort() {
		return port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public long getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(long timeTaken) {
		this.timeTaken = timeTaken;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	public String toString() {
		return this.ip+":"+this.port+" "+timeTaken+"ms";
	}
}
