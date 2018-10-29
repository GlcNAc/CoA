package prj.coa.tools.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

public class FileUpServlet extends HttpServlet {

	private static final long serialVersionUID = -3586305894284496108L;
	Logger logger = Logger.getLogger(FileUpServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		Map<String,Object> rsMap = new HashMap<String,Object>();
		String upfileid = String.valueOf(request.getParameter("upfileid"));
		String barid = String.valueOf(request.getParameter("barid"));
	    HttpSession session=request.getSession();
	    String bar_key = "progressBar_"+barid;
	    session.setAttribute(bar_key,0);      
		// �õ��ϴ��ļ��ı���Ŀ¼�����ϴ����ļ������WEB-INFĿ¼��
		String savePath = this.getServletContext().getRealPath("/WEB-INF/upload/"+upfileid);
		File file = new File(savePath);
		// �ж��ϴ��ļ��ı���Ŀ¼�Ƿ����
		if (!file.exists() && !file.isDirectory()) {
			logger.debug(savePath + "Ŀ¼�����ڣ���Ҫ����");
			// ����Ŀ¼
			file.mkdir();
		}
		//logger.info("server::::::::" + server);
		// ����һ��DiskFileItemFactory����
		DiskFileItemFactory factory = new DiskFileItemFactory();
		try {
			// ����һ���ļ��ϴ�������
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");
			// �ж��ύ�����������Ƿ����ϴ���������
			if (!ServletFileUpload.isMultipartContent(request)) {
				// ���մ�ͳ��ʽ��ȡ����
				return;
			}
			//System.out.println("isM = " + ServletFileUpload.isMultipartContent(request));
			// ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form��������
			List<FileItem> list = upload.parseRequest(request);
			String filetype = "";
			List<FileItem> fileItems = new ArrayList<FileItem>();
			for (FileItem item : list) {
				// ���fileitem�з�װ������ͨ�����������
				if (item.isFormField()) {
					String name = item.getFieldName();
					// ת��
					String value = item.getString("UTF-8");
					//logger.debug(name + "=======" + value);
					if(name.equals("filetype")){
						filetype = value;
					}
				} else {
					// ���fileitem�з�װ�����ϴ��ļ�
					// �õ��ļ���
					String filename = item.getName();
					//logger.info("filename=" + filename);
					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					// �����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
					filename = filename.substring(filename.lastIndexOf("\\") + 1);
					if(filetype.length() > 0){
						boolean isMatch = false;
						String ext = FilenameUtils.getExtension(filename);
						//logger.info("ext=" + ext);
						String[] filetypes = filetype.split(",");
						for (String ft : filetypes) {
							if(ft.equalsIgnoreCase("."+ext)){
								isMatch = true;
							}
						}
						if(!isMatch){
							rsMap.put("error", "�ϴ��ļ���ʽ���ԣ�");
							break;
						}
						fileItems.add(item);
					}
				}
			}
			double allPercent = 0d;
			int itemSize = fileItems.size();
			int k = 0;
			for (FileItem item : fileItems) {
				String filename = item.getName();
				filename = filename.substring(filename.lastIndexOf("\\") + 1);
				// ��ȡitem�е��ϴ��ļ���������
				InputStream in = item.getInputStream();
				// ����������
				byte buffer[] = new byte[1024];
				// ����������������ڽ������������ݶ���������·��
				
				String saveFile = savePath + "\\" + filename;
				FileOutputStream output = new FileOutputStream(saveFile);
				// �ж��������е������Ƿ��Ѿ�����
				int len = 0;
				// ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾ�������л�������
				while ((len = in.read(buffer)) > 0) {
					// ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\"
					// + filename)����
					output.write(buffer, 0, len);
				}
				in.close();
				output.close();
				logger.info(saveFile);
				k++;
				allPercent = (double)k/(double)itemSize *100D;
				//logger.info("allPercent:::::::"+allPercent);
				request.getSession().setAttribute(bar_key,Math.round(allPercent)); 
			}
		} catch (Exception e) {
			e.printStackTrace();
			rsMap.put("error", e.getMessage());
		}
		response.getWriter().write(JSON.toJSONString(rsMap));
	}

}
