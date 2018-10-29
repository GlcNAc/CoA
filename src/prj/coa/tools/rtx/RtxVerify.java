package prj.coa.tools.rtx;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RtxVerify {
	/** 
     * �����½ʱ��ͨ��ͻ��˷��������û�ǩ����û�����֤�����û��Ƿ�Ϸ� 
     * ʵ�ַ�ʽ������RTX�������ϵ�SignAuth.cgi �����Ժ��䷵��ֵΪ��failed! �� success! 
     */  
    @SuppressWarnings("deprecation")
	public boolean checkUser(String userid, String cgi,String ServerIp) {  
        String query = "";  
        try {  
            URL u = new URL("http://"+ServerIp+":8012/SignAuth.cgi?user="  
                    + userid + "&sign=" + cgi + "");  
            URLConnection urlc = u.openConnection();  
            urlc.setDoOutput(true);  
            urlc.setDoInput(true);  
            urlc.setAllowUserInteraction(false);  
//           DataOutputStream server = new DataOutputStream(urlc  
//           .getOutputStream());  
//           // Send the data  
//           server.writeBytes(query);  
//           server.close();  
            DataInputStream in = new DataInputStream(urlc.getInputStream());  
            String s;  
            while ((s = in.readLine()) != null) {  
                query += s;  
            }  
            in.close();  
        } catch (MalformedURLException e) {  
            return false;  
        } catch (IOException e) {  
            return false;  
        }  
        if (query.indexOf("success") > -1) {  
            return true;  
        } else {  
            return false;  
        }  
    }  
}
