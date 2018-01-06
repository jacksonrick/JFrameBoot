package com.jf;

import com.jf.file.FileUtil;

import java.io.*;

/**
 * 统计目录下所有.*的文件的行数
 * 
 * @author rick
 *
 */
public class CountLine {
	public static File src;// 源目录
	public static File dest = new File("/Users/xujunfei/Desktop/result-css.txt");// 存放记录的文件
	public static int counts = 0;// 总行数
	public static String fileType = ".css";// 搜索文件类型

	public static void main(String[] args) {
		// 需要统计的文件夹，不要输入文件
		src = new File("/Users/xujunfei/Desktop/Project/Idea/Mall/src/main/webapp/static/theme");
		try {
			System.out.println("开始搜索......");
			printAllFiles(src);

			writeToFile("\r\n" + fileType + "文件的总行数为：" + counts + "\r\n\r\n");
			System.out.println("搜索完毕,已将记录存放在文件：" + dest.getAbsolutePath() + "中...");
		} catch (IOException e) {
			System.out.println("发生了错误");
		}
	}

	/**
	 * 搜索目录下符合条件的所有文件
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static void printAllFiles(File file) throws IOException {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File temp : files) {
				// 是文件且后缀为???
				if (temp.isFile() && temp.getName().endsWith(fileType)) {
					countLine(temp);
				}
				printAllFiles(temp);// 回调
			}
		}
	}

	/**
	 * 将参数值以追加的方式写入到目标文件中
	 * 
	 * @param content
	 */
	public static void writeToFile(String content) {
		OutputStream os = null;
		try {

			os = new FileOutputStream(dest, true);

			byte[] data = content.getBytes();
			os.write(data, 0, data.length);
			os.flush();
		} catch (IOException e) {
			System.out.println("写入到文件时发生错误");
		}
	}

	/**
	 * 统计获取到的文件的行数
	 * 
	 * @param src
	 * @throws IOException
	 */
	public static void countLine(File src) throws IOException {
		// 字符缓冲流
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(src)));

		int count = 0;
		// 每读取一行计数器加1
		while (br.readLine() != null) {
			count++;
		}

		counts += count;// 每统计一次，总行数记录

		String msg = "文件：" + src.getAbsolutePath() + "-->行数：" + count + "\r\n";
		writeToFile(msg);

		FileUtil.close(br);// 关闭流方法
	}

}
