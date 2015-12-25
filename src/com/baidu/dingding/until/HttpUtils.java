package com.baidu.dingding.until;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpStatus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.util.Log;
import android.widget.ImageView;

public class HttpUtils {
	public static HttpURLConnection connection;

	// ��ȡsession
	// ��ȡ���ص�����
	public static String httpContentYZM(String yANZHENG) {
		// TODO Auto-generated method stub
		try {
			URL url = new URL(yANZHENG);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("POST");
			int responseCode = connection.getResponseCode();
			// ��ӡ����ͷ��Ϣ
			LogUtil.i("��֤��code", "responseCode=" + responseCode);
			Map hfs = connection.getHeaderFields();
			Set<String> keys = hfs.keySet();
			for (String str : keys) {
				List<String> vs = (List) hfs.get(str);
				System.out.print(str + ":");
				for (String v : vs) {
					System.out.print(v + "\t");
				}
				System.out.println();
			}
			System.out.println("-----------------------");
			String cookieValue = connection.getHeaderField("Set-Cookie");
			System.out.println("cookie value:" + cookieValue);
			String sessionId = cookieValue.substring(0,
					cookieValue.indexOf(";"));
			return sessionId;

		} catch (Exception e) {
			// TODO: handle exception
			ExceptionUtil.handleException(e);
		}

		return "";

	}

	// ����get����
	public static String getContent(String url_path) {
		try {
			URL url = new URL(url_path);
			connection = (HttpURLConnection) url.openConnection();
			// ��������ĳ�ʱʱ��
			connection.setReadTimeout(5000);
			connection.setConnectTimeout(5000);
			// ���������ͷ
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestMethod("GET");
			connection.connect();
			// ��ȡ��Ӧ��200
			int Code = connection.getResponseCode();
			LogUtil.i("ע��������", "Code=" + Code);
			if (Code == HttpStatus.SC_OK) {
				return chageInputStream(connection.getInputStream());
			}
		} catch (Exception e) {
			ExceptionUtil.handleException(e);
		} finally {
			connection.disconnect();
		}
		return "";
	}

	private static  String chageInputStream(InputStream is) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		int len = 0;
		byte[] data = new byte[1024];
		String jsonString="";
		try {
			while ((len = is.read(data)) != -1) {
				stream.write(data, 0, len);
			}
			jsonString = new String(stream.toByteArray(), "GBK");
			
			return jsonString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LogUtil.i("����������", "error");
			ExceptionUtil.handleException(e);
		} finally{
			try {
				LogUtil.i("���յ�������", jsonString);
				stream.flush();
				stream.close();
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

	static OutputStream is;

	// ����post����
	public static String getContentPost(String url_post) {
		try {
			URL url = new URL(url_post);
			connection = (HttpURLConnection) url.openConnection();
			connection.setReadTimeout(10 * 1000);
			connection.setConnectTimeout(10 * 1000);
			connection.setRequestMethod("POST");
			// ���������ͷ
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) "
							+ "Gecko/20100101 Firefox/27.0");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-type",
					"application/x-www-form-urlencoded");
			// ���ӷ�����
			// connection.connect();
			is = connection.getOutputStream();
			is.flush();
			int Code = connection.getResponseCode();
			if (Code == HttpStatus.SC_OK) {
				return chageInputStream(connection.getInputStream());
			}
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionUtil.handleException(e);
		} finally {
			connection.disconnect();
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return "";
	}

	/*
	 * ע��ӿ�
	 */
	public static String getContentPost(String EGIS, String userNumber,
			String password, String passed, String code) {
		// TODO Auto-generated method stub
		try {
			URL url = new URL(EGIS);
			connection = (HttpURLConnection) url.openConnection();
			connection.setReadTimeout(10 * 1000);
			connection.setConnectTimeout(10 * 1000);
			connection.setRequestMethod("POST");
			String data = "userNumber=" + URLEncoder.encode(userNumber, "GBK")
					+ "&password=" + URLEncoder.encode(password, "GBk")
					+ "&repassword=" + URLEncoder.encode(passed, "GBK")
					+ "&verifyCode=" + URLEncoder.encode(code, "GBK");
			// ���������ͷ
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) "
							+ "Gecko/20100101 Firefox/27.0");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-type",
					"application/x-www-form-urlencoded");
			// �������ݳ���
			connection.setRequestProperty("Content-Length",
					String.valueOf(data.getBytes().length));
			// ���ӷ�����
			// connection.connect();
			// ��ȡ�����
			 is = connection.getOutputStream();
			is.write(data.getBytes());
			is.flush();
			int Code = connection.getResponseCode();
			if (Code == HttpStatus.SC_OK) {
				return chageInputStream(connection.getInputStream());
			}
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionUtil.handleException(e);
		} finally {
			connection.disconnect();

		}

		return "";
	}

	/*
	 * ͼƬ�ӿ�
	 */
	public static Bitmap getBitmap(String path) {
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == HttpStatus.SC_OK) {
				InputStream inputStream = conn.getInputStream();
				Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
				return bitmap;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionUtil.handleException(e);
		}
		return null;
	}

	/*
	 * �һ�����ӿ�
	 */
	public static String getContentPost(String ZHAOHUI_URL, String senderContent) {
		// TODO Auto-generated method stub
		try {
			URL url = new URL(ZHAOHUI_URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setReadTimeout(10 * 1000);
			connection.setConnectTimeout(10 * 1000);
			connection.setRequestMethod("POST");
			// ���������ͷ
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) "
							+ "Gecko/20100101 Firefox/27.0");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-type",
					"application/x-www-form-urlencoded");
			String data = "senderContent="
					+ URLEncoder.encode(senderContent, "GBK");
			// �������ݳ���
			connection.setRequestProperty("Content-Length",
					String.valueOf(data.getBytes().length));
			// ���ӷ�����
			// connection.connect();
			is = connection.getOutputStream();
			is.write(data.getBytes());
			is.flush();
			int Code = connection.getResponseCode();
			if (Code == HttpStatus.SC_OK) {
				return chageInputStream(connection.getInputStream());
			}
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionUtil.handleException(e);
		} finally {
			connection.disconnect();
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ExceptionUtil.handleException(e);
			}
		}

		return "";
	}

	/*
	 * ��������ӿ�
	 */
	public static String getZhPost(String CHONGZHI_URL, String senderContent,
			String zHAOHUI_CODE, String zHAOHUI_PWD, String zHAOHUI_PED) {
		// TODO Auto-generated method stub
		try {
			URL url = new URL(CHONGZHI_URL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setReadTimeout(10 * 1000);
			connection.setConnectTimeout(10 * 1000);
			connection.setRequestMethod("POST");
			// ���������ͷ
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) "
							+ "Gecko/20100101 Firefox/27.0");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-type",
					"application/x-www-form-urlencoded");
			String data = "verifyCode="
					+ URLEncoder.encode(zHAOHUI_CODE, "GBK") + "&password="
					+ URLEncoder.encode(zHAOHUI_PWD, "GBK") + "&repassword="
					+ URLEncoder.encode(zHAOHUI_PED, "GBK") + "&senderContent="
					+ URLEncoder.encode(senderContent, "GBK");
			;
			// �������ݳ���
			connection.setRequestProperty("Content-Length",
					String.valueOf(data.getBytes().length));
			// ���ӷ�����
			// connection.connect();
			is = connection.getOutputStream();
			is.write(data.getBytes());
			is.flush();
			int Code = connection.getResponseCode();
			if (Code == HttpStatus.SC_OK) {
				return chageInputStream(connection.getInputStream());
			}
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionUtil.handleException(e);
		} finally {
			connection.disconnect();
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ExceptionUtil.handleException(e);
			}
		}

		return "";
	}

	/*
	 * ��ȡͼƬbitmap
	 */
	public static Bitmap postImag(String yzm_logPath, String sessionid) {
		// TODO Auto-generated method stub
		try {
			URL url = new URL(yzm_logPath);
			connection = (HttpURLConnection) url.openConnection();
			if (sessionid != null) {
				connection.setRequestProperty("Cookie", ";" + sessionid);
			}
			connection.setReadTimeout(10 * 1000);
			connection.setConnectTimeout(10 * 1000);
			connection.setRequestMethod("POST");
			// ���������ͷ
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:27.0) "
							+ "Gecko/20100101 Firefox/27.0");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-type",
					"application/x-www-form-urlencoded");
			// ���ӷ�����
			int Code = connection.getResponseCode();
			if (Code == HttpStatus.SC_OK) {
				InputStream in = connection.getInputStream();
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				int len = 0;
				byte[] data = new byte[1024];
				try {
					while ((len = in.read(data)) != -1) {
						stream.write(data, 0, len);
					}
					// ����֤��ͼƬ����
					return BitmapFactory.decodeByteArray(stream.toByteArray(),
							0, stream.toByteArray().length);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					ExceptionUtil.handleException(e);
				} finally {
					try {
						stream.flush();
						stream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						ExceptionUtil.handleException(e);
					}
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			ExceptionUtil.handleException(e);
		} finally {
			connection.disconnect();
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ExceptionUtil.handleException(e);
			}
		}
		return null;

	}

	/**
	 * ��ҳ����
	 * */
	public  static String postSeek(String sOUYE_SOUSHUO, String operationType,
			String pageSize, String searchCondition, int currentPage) {
		// TODO Auto-generated method stub
		try {
			URL url = new URL(sOUYE_SOUSHUO);
			connection = (HttpURLConnection) url.openConnection();
			connection.setReadTimeout( 10* 1000);
			connection.setConnectTimeout(10 * 1000);
			connection.setRequestMethod("POST");
			// ���������ͷ
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			
			connection.setRequestProperty("Content-type",
					"application/x-www-form-urlencoded");
			
			String data ="operationType="
					+ URLEncoder.encode(String.valueOf(operationType), "GBK")
					+"&pageSize="+URLEncoder.encode(pageSize, "GBK")
					+"&searchCondition="+URLEncoder.encode(searchCondition, "GBK")
					+"&currentPage="+ URLEncoder.encode(String.valueOf(currentPage), "GBK");
			// �������ݳ���
			connection.setRequestProperty("Content-Length",
					String.valueOf(data.getBytes().length));
			// ���ӷ�����
			is = connection.getOutputStream();
			is.write(data.getBytes());
			is.flush();
			
			//connection.connect();
			int Code = connection.getResponseCode();
			LogUtil.i("��������Code", Code+"");
			if (Code == HttpStatus.SC_OK) {
				
				return chageInputStream(connection.getInputStream());
			}
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.i("��������", "UNSUUESS");
			ExceptionUtil.handleException(e);
		} finally {
			connection.disconnect();
			
		}

		return "";
	}

	

}
