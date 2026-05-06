package com.adtec.comp.tseq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 平台流水处理类
 * 
 */
public class PlatSeq implements Runnable {
	private static int seqNo = 0;
	private boolean flag = true;
	private static final Lock flock = new ReentrantLock();
	private static final Lock slock = new ReentrantLock();
	private static String fname = System.getenv("JAVAWORKDIR") + "/config/plat_seq";

	public PlatSeq() {
		// 初始化时获取对应的流水文件路径
		fname = System.getenv("JAVAWORKDIR") + "/config/plat_seq";
	}

	public static int getPltSeq() {
		int seq = 0;

		slock.lock();
		try {
			seqNo += 1;

			if (seqNo > 99999999)
				seqNo = 1;
			seq = seqNo;
		} finally {
			slock.unlock();
		}

		return seq;
	}

	/**
	 * 获取全局流水号，主要用于日志跟踪 yyyyMMddHHmmss+10位流水序号(如果集群部署，该序号可以换成通过zookeeper中获取生成)
	 * 
	 * @return
	 */
	public static String getGlobalSeq() {
		return DateUtils.getNumNowDate() + DateUtils.getNumNowTime()
				+ DataUtil.fix0BeforeNumber(PlatSeq.getPltSeq(), 10);
	}

	public void saveSeq() throws Exception {
		FileOutputStream fo = null;
		byte[] buf = new byte[10];
		try {
			fname = fname.replace("/", File.separator).replace("%","").replaceAll("\\\\",File.separator);
			fo = new FileOutputStream(fname);
			byte[] bSeq = String.valueOf(seqNo).getBytes();
			int len = (bSeq.length > buf.length) ? buf.length : bSeq.length;

			System.arraycopy(bSeq, 0, buf, 0, len);

			flock.lock();
			try {
				fo.write(buf);
				fo.flush();
			} finally {
				flock.unlock();
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("不存在文件：" + fname);
		} catch (SecurityException e) {
			throw new RuntimeException("访问文件失败：" + fname);
		} catch (IOException e) {
			throw new RuntimeException("写文件失败：" + fname + " , " + Integer.valueOf(buf.length));
		} finally {
			try {
				if (fo != null)
					fo.close();
			} catch (IOException e) {
                System.out.println("出现异常");
            }
		}
	}

	public void restoreSeq() throws Exception {
		System.out.println("装载流水文件--------" + fname);
		FileInputStream fi = null;
		byte[] buf = new byte[10];
		try {
			fname = fname.replace("/", File.separator).replace("%","").replace("\\",File.separator);
			if (createFile(fname)) {
				// 不存在流水文件新创建流水文件，并写入初始流水号0
				saveSeq();
			}

			fi = new FileInputStream(fname);

			flock.lock();
			try {
				fi.read(buf);
				seqNo = Integer.parseInt(new String(buf).trim());
			} catch (Exception e) {
				throw new RuntimeException("从平台状态文件中解析流水号失败");
			}
			System.out.println("恢复流水--------" + seqNo);
		} catch (FileNotFoundException e) {
			return;
		} catch (IOException e) {
			throw new RuntimeException("读取文件失败：" + fname);
		} finally {
			flock.unlock();
			try {
				if (fi != null)
					fi.close();
			} catch (Exception localException2) {
				System.out.println("操作失败");
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("-------------------- 流水号自动保存线程启动成功 --------------------");
		Thread.currentThread().setName("SmartwebSeq is : " + Thread.currentThread().getId());

		int scanTimeInt = 0;
		String platSeqTimeOut = "";
		try {
			//platSeqTimeOut = ParamUtil.getConfigParam().get("fs.platseq.timeout");
			platSeqTimeOut = "30";
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (null != platSeqTimeOut && !"".equals(platSeqTimeOut)) {
			scanTimeInt = Integer.parseInt(platSeqTimeOut);
		}
		if (scanTimeInt <= 0)
			scanTimeInt = 3000;
		else
			scanTimeInt *= 1000;
		while (this.flag) {
			try {
				PlatSeq.sleep(scanTimeInt);
				saveSeq();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void sleep(int time) {
		Object sleep = new Object();
		long elapsedTime = 0L;
		long begin = System.currentTimeMillis();

		if (time < 0) {
			time = 0;
		}

		synchronized (sleep) {
			while (time > elapsedTime)
				try {
					sleep.wait(time - elapsedTime);

					elapsedTime = System.currentTimeMillis() - begin;
				} catch (InterruptedException e) {
					elapsedTime = System.currentTimeMillis() - begin;
				}
		}
	}

	public synchronized void stop() {
		this.flag = false;
		super.notifyAll();
	}

	/**
	 * 创建单个文件
	 * 
	 * @param descFileName
	 *            文件名，包含路径
	 * @return 如果创建成功，则返回true，否则返回false
	 */
	public static boolean createFile(String descFileName) {
		descFileName = descFileName.replace("/", File.separator).replace("%","").replace("\\",File.separator);
		File file = new File(descFileName);
		if (file.exists()) {
			return false;
		}
		if (descFileName.endsWith(File.separator)) {
			return false;
		}
		if (!file.getParentFile().exists()) {
			// 如果文件所在的目录不存在，则创建目录
			if (!file.getParentFile().mkdirs()) {
				return false;
			}
		}

		// 创建文件
		try {
			if (file.createNewFile()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 获取勾兑流水:yyyyMMdd+8位序号
	 * 
	 * @return
	 */
	public static String getGDSeq() {
		return DateUtils.getDate() + DataUtil.fix0BeforeString("" + PlatSeq.getPltSeq(), 8);
	}

	/**
	 * 获取中间业务云平台流水(22位):999000+yyyyMMdd+8位序号
	 * 
	 * @return
	 * @throws Exception 
	 */
	public static String getSeq() throws Exception {
		return "999888" + DateUtils.getDate() + DataUtil.fix0BeforeString("" + PlatSeq.getPltSeq(), 8);
	}
}
