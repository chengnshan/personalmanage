package com.cxp.personalmanage.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

@Component
public class SambaUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SambaUtil.class);
	
	private static final String samba_user="chengxp";
	private static final String samba_passwd="chengxp123456";
	private static final String samba_host="192.168.0.150";
	
	public static void deleteFile(SmbFile smbFile) {
		try {
			smbFile.delete();
		} catch (SmbException e) {
			e.printStackTrace();
		}	
	}
	
	public static void removeFile(SmbFile sourceFile,SmbFile descFile) {
		try {
			sourceFile.copyTo(descFile);
			deleteFile(sourceFile);
		} catch (SmbException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从samba服务器上下载指定的文件到本地目录
	 * 
	 * @param remoteSmbFile
	 *            Samba服务器远程文件
	 * @param localDir
	 *            本地目录的路径
	 * @throws SmbException
	 */

	public static void downloadFileFromSamba(SmbFile remoteSmbFile, String localDir) throws SmbException {

		if (!remoteSmbFile.exists()) {
			logger.info("Samba服务器远程文件不存在");
			return;
		}
		if ((localDir == null) || ("".equals(localDir.trim()))) {
			logger.info("本地目录路径不可以为空");
			return;
		}

		InputStream in = null;
		OutputStream out = null;

		try {
			// 获取远程文件的文件名,这个的目的是为了在本地的目录下创建一个同名文件
			String remoteSmbFileName = remoteSmbFile.getName();

			// 本地文件由本地目录，路径分隔符，文件名拼接而成
			File localFile = new File(localDir + File.separator + remoteSmbFileName);

			// 如果路径不存在,则创建
			File parentFile = localFile.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}

			// 打开文件输入流，指向远程的smb服务器上的文件，特别注意，这里流包装器包装了SmbFileInputStream
			in = new BufferedInputStream(new SmbFileInputStream(remoteSmbFile));
			// 打开文件输出流，指向新创建的本地文件，作为最终复制到的目的地
			out = new BufferedOutputStream(new FileOutputStream(localFile));

			// 缓冲内存
			byte[] buffer = new byte[1024];
			while (in.read(buffer) != -1) {
				out.write(buffer);
				buffer = new byte[1024];
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 上传本地文件到Samba服务器指定目录
	 * 
	 * @param url
	 * @param auth
	 * @param localFilePath
	 * @throws MalformedURLException
	 * @throws SmbException
	 */
	public static void uploadFileToSamba(String url, NtlmPasswordAuthentication auth, String localFilePath)
			throws MalformedURLException, SmbException {
		if ((localFilePath == null) || ("".equals(localFilePath.trim()))) {
			System.out.println("本地文件路径不可以为空");
			return;
		}

		// 检查远程父路径，不存在则创建
		SmbFile remoteSmbFile = new SmbFile(url, auth);
		String parent = remoteSmbFile.getParent();
		SmbFile parentSmbFile = new SmbFile(parent, auth);
		if (!parentSmbFile.exists()) {
			parentSmbFile.mkdirs();
		}

		InputStream in = null;
		OutputStream out = null;

		try {
			File localFile = new File(localFilePath);

			// 打开一个文件输入流执行本地文件，要从它读取内容
			in = new BufferedInputStream(new FileInputStream(localFile));

			// 打开一个远程Samba文件输出流，作为复制到的目的地
			out = new BufferedOutputStream(new SmbFileOutputStream(remoteSmbFile));

			// 缓冲内存
			byte[] buffer = new byte[1024];
			while (in.read(buffer) != -1) {
				out.write(buffer);
				buffer = new byte[1024];
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
