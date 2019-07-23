package xyz.thloml.proj.portscaner;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import xyz.thloml.proj.portscaner.vo.IP;
import xyz.thloml.proj.portscaner.vo.Result;

/**
 * 端口扫描工具
 * @author zhangzh
 *
 */
public class PortScaner {
	
	//端口连接延时
	private static int connectTimeout = 200;
	
	private static int poolSize = 5000;
	
	private static String IP1;
	
	private static String IP2;
	
	public static boolean isEnd =false;
	
	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(poolSize, poolSize, 30,
            TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(),
            new ThreadPoolExecutor.CallerRunsPolicy());
	

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		long start = new Date().getTime();
		//IP1 开始
		String i1 = "192.168.2.1";
		//IP2 结束
		String i2 = "192.168.2.255";
		//每个IP扫描的端口范围
		String portRange = "443";
		
		if(args.length > 0) {
			i1 = args[0];
			i2 = args[1];
			portRange = args[2];
		}
		if(args.length>3) {
			connectTimeout = Integer.parseInt(args[3]);
		}
		if(args.length>4) {
			poolSize = Integer.parseInt(args[4]);
		}
		int[]ports = getPortRange(portRange);
		IP1 = i1;
		IP2 = i2;
		ArrayBlockingQueue<Future<Result>> queue = new ArrayBlockingQueue<>(50000);
		//5层循环
		new Thread(new Runnable() {
			@Override
			public void run() {
				IP ip1 = new IP(IP1);
				IP ip2 = new IP(IP2);
				//默认IP1大于IP2
				if(ip1.compareTo(ip2) <0) {
					IP ipt = ip1;
					ip1 = ip2;
					ip2 = ipt;
				}
				for(int a =ip2.getA();a<=ip1.getA();a++) {
					for(int b=ip2.getB();b<=ip1.getB();b++) {
						for(int c=ip2.getC();c<=ip1.getC();c++) {
							for(int d=ip2.getD();d<=ip1.getD();d++) {
								for(int port : ports) {
									IP ip = new IP(a,b,c,d);
									PortCheckProcess pcp = new PortCheckProcess(ip.toString(), port);
									Future<Result> future = executor.submit(pcp);
									try {
										queue.put(future);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
				isEnd = true;
			}
		}).start();
		
		while(true) {
			Future<Result> result = null;
			//还在提交任务
			if(!isEnd) {
				result = queue.take();
			}
			else {
				result = queue.poll();
				Thread.sleep(1);
				if(result == null) {
					break;
				}
			}
			if(result != null) {
				Result re = result.get();
				if(re.isOpen()) {
					System.out.println(re);
				}
				else {
				}
			}
		}
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.DAYS);
		long end = new Date().getTime();
		System.out.println("总耗时："+(end-start)/1000+"s");
	}
	
	public static int[] getPortRange(String portRange){
		String split = portRange.replaceAll("\\d", "");
		String[] ports = null;
		if("".equals(split)) {
			ports = new String[] {portRange};
		}
		else {
			ports = portRange.split(split);
		}
		if(ports.length == 1) {
			return new int[] {Integer.parseInt(ports[0])};
		}
		if(ports.length == 2) {
			Integer port1 = Integer.parseInt(ports[0]);
			Integer port2 = Integer.parseInt(ports[1]);
			if(port1 > port2) {
				Integer portT = port1;
				port1 = port2;
				port2 = portT;
			}
			int []p = new int[port2-port1+1];
			for(int i=port1;i<=port2;i++) {
				p[i-port1] = i;
			}
			return p;
		}
		throw new IllegalArgumentException("端口范围填写错误");
	}
	
	
	
	
	
	public static Result checkPort(String ip, int port){
		Result result = new Result();
		result.setIp(ip);
		result.setPort(port);
		try {
			InetAddress inet = InetAddress.getByName(ip);
			Socket socket = new Socket();
			long t1 = new Date().getTime();
			socket.connect(new InetSocketAddress(ip, port), connectTimeout);
			long t2 = new Date().getTime();
			result.setTimeTaken(t2-t1);
			result.setOpen(true);
			socket.close();
//			System.out.println("当前操作："+ip+":"+port);
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}
		catch (Exception e) {
		}
		return result;
	}
}
 class PortCheckProcess implements Callable<Result>{
	
	private String ip;
	
	private int port;
	
	public PortCheckProcess(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	@Override
	public Result call() throws Exception {
		return PortScaner.checkPort(ip, port);
	}
}
