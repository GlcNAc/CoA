package prj.coa.tools.file;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileDownloadServlet
 */
public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String defaultRoot = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String path = request.getParameter("filepath");
		path = defaultRoot + File.separator	+ java.net.URLDecoder.decode(path, "utf-8");
		String displayname = request.getParameter("displayname");
		if (displayname == null) {
			displayname = "";
		}
		displayname = java.net.URLDecoder.decode(displayname, "utf-8");

		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ displayname + "\"");
		response.setHeader("Content_Length",
				String.valueOf(new File(path).length()));
		response.setContentType("application/OCTET-STREAM");

		java.io.InputStream input = null;
		java.io.OutputStream out = response.getOutputStream();
		try {
			input = new java.io.FileInputStream(path);
			byte[] buf = new byte[512];
			int readCount = 0;
			do {
				readCount = input.read(buf);
				if (readCount > 0) {
					out.write(buf, 0, readCount);
				}
			} while (readCount > 0);
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}

			try {
				input.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
