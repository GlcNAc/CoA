package prj.coa.tools.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.DecoderException;

/**
 * Servlet implementation class DisplayFile
 */
public class DisplayFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String defaultRoot = "";
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String defaultPath = "/activex/help";
		File currDir = null;
		List<File> files = new ArrayList<File>();
		List<File> dirs = new ArrayList<File>();
		
		String[] paths = null;

		String currPath = request.getParameter("currPath");
		if (currPath == null) {
			currDir = new File(defaultRoot + File.separator + defaultPath);
		} else {
			try {
				currPath = new String(
						org.apache.commons.codec.binary.Hex.decodeHex(currPath
								.toCharArray()), "utf-8");
			} catch (DecoderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			currDir = new File(defaultRoot + File.separator + currPath);
		}
		paths = currDir.list();


		if (paths != null) {
			for (String path : paths) {
				File tempFile = new File(currDir.getAbsolutePath()
						+ File.separatorChar + path);

				if (tempFile.isDirectory()) {
					dirs.add(tempFile);
				} else {
					files.add(tempFile);
				}
			}
		}
		request.setAttribute("currDir", currPath);
		request.setAttribute("files", files);
		request.setAttribute("dirs", dirs);
		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher("/activex/helpFilelist.jsp");
		requestDispatcher.forward(request, response);
	}

}
