package com.device;

public class Device {
	static {
		try {
			System.loadLibrary("Device");
		} catch (UnsatisfiedLinkError e) {
			System.out.println(e.getMessage());
		} 
	}

	/**
	 * 获取指纹图像
	 * @param timeout   超时时间    10000
	 * @param finger    指纹图像数据 长度 2000+152*200
	 * @param message   返回错误信息 长度 200
	 * @return	0 成功  其他失败
	 */
	public native static int getImage(int timeout, byte[] finger, byte[] message);


	public native static int getFinger(int timeout, byte[] finger, byte[] message);
	public native static int setParam(byte[] message);

	/**
	 * 图像转特征
	 * @param image	指纹图像数据 长度 2000+152*200
	 * @param feature	指纹特征数据 长度 513
	 * @param message	返回错误信息 长度 200
	 * @return	0 成功  其他失败
	 */
	public native static int ImageToFeature(byte[] image, byte[] feature, byte[] message);

	/**
	 * 特征合成模板
	 * @param tz1	指纹特征数据 长度 513
	 * @param tz2	指纹特征数据 长度 513
	 * @param tz3	指纹特征数据 长度 513
	 * @param mb	指纹模板数据 长度 513
	 * @param message	返回错误信息 长度 200
	 * @return
	 */
	public native static int FeatureToTemp(byte[] tz1, byte[] tz2, 
			byte[] tz3, byte[] mb, byte[] message);

	/**
	 * 指纹比对
	 * @param mbFinger	指纹模板
	 * @param tzFinger 指纹特征
	 * @param level 比对级别 建议 3
	 * @return	0 成功  其他失败
	 */
	public native static int verifyFinger(String mbFinger, String tzFinger, int level);
//	public native static int verifyBinFinger(byte[] mbFinger, byte[] tzFinger, int level);

	/**
	 *
	 * @param timeout 超时时间 1000
	 * @param tid		tid  长度 20000
	 * @param epcid	epcid  长度 20000
	 * @param message	返回错误信息 长度 200
	 * @return	0 成功  其他失败
	 */
	public native static int getRfid(int timeout, byte[] tid, byte[] epcid, byte[] message);

	/**
	 * 取消采集
	 * @return	0 成功  其他失败
	 */
	public native static int cancel();
	
	public native static int readIdCard(int timeout, byte[] textData, 
			byte[] photoData, byte[] message);

	/**
	 * 打开RFID模块电源
	 * @param message	返回错误信息 长度 200
	 * @return	0 成功  其他失败
	 */
	public native static int openRfid(byte[] message);

	/**
	 * 关闭RFID模块电源
	 * @param message
	 * @return	0 成功  其他失败
	 */
	public native static int closeRfid(byte[] message);

	/**
	 * 打开指纹模块电源
	 * @param message	返回错误信息 长度 200
	 * @return	0 成功  其他失败
	 */
	public native static int openFinger(byte[] message);

	/**
	 * 关闭指纹模块电源
	 * @param message	返回错误信息 长度 200
	 * @return	0 成功  其他失败
	 */
	public native static int closeFinger(byte[] message);
}
