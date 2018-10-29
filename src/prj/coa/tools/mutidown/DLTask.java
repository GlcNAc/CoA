/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package prj.coa.tools.mutidown;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 鏈被瀵瑰簲涓�涓笅杞戒换鍔★紝姣忎釜涓嬭浇浠诲姟鍖呭惈澶氫釜涓嬭浇绾跨▼锛岄粯璁ゆ渶澶氬寘鍚崄涓笅杞界嚎绋�
 * @author RexCJ
 */
public class DLTask extends Thread implements Serializable {

	private static final long serialVersionUID = 126148287461276024L;
	protected final static int MAX_DLTHREAD_QUT = 10;  //鏈�澶т笅杞界嚎绋嬫暟閲�
	/**
	 * 涓嬭浇涓存椂鏂囦欢鍚庣紑锛屼笅杞藉畬鎴愬悗灏嗚嚜鍔ㄨ鍒犻櫎
	 */
    public final static String FILE_POSTFIX = ".tmp";
    private URL url;									
    private File file;
    private String filename;
    private int id;
    private int Level;
    private int threadQut;								//涓嬭浇绾跨▼鏁伴噺锛岀敤鎴峰彲瀹氬埗							
    private int contentLen;							//涓嬭浇鏂囦欢闀垮害
    private long completedTot;							//褰撳墠涓嬭浇瀹屾垚鎬绘暟
    private int costTime;								//涓嬭浇鏃堕棿璁℃暟锛岃褰曚笅杞借�楄垂鐨勬椂闂�
    private String curPercent;							//涓嬭浇鐧惧垎姣�
    private boolean isNewTask;						//鏄惁鏂板缓涓嬭浇浠诲姟锛屽彲鑳芥槸鏂偣缁紶浠诲姟
    
    private DLThread[] dlThreads;						//淇濆瓨褰撳墠浠诲姟鐨勭嚎绋�

    transient private DLListener listener;			//褰撳墠浠诲姟鐨勭洃鍚櫒锛岀敤浜庡嵆鏃惰幏鍙栫浉鍏充笅杞戒俊鎭�

    public DLTask(int threadQut, String url, String filename) {
        this.threadQut = threadQut;
        this.filename = filename;
        costTime = 0;
        curPercent = "0";
        isNewTask = true;
        this.dlThreads = new DLThread[threadQut];
        this.listener = new DLListener(this);
        try {
            this.url = new URL(url);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void run() {
        if (isNewTask) {
            newTask();
            return;
        }
        resumeTask();
    }

    /**
     * 鎭㈠浠诲姟鏃惰璋冪敤锛岀敤浜庢柇鐐圭画浼犳椂鎭㈠鍚勪釜绾跨▼銆�
     */
    private void resumeTask() {
        listener = new DLListener(this);
        file = new File(filename + FILE_POSTFIX);
        for (int i = 0; i < threadQut; i++) {
            dlThreads[i].setDlTask(this);
            QSEngine.pool.execute(dlThreads[i]);
        }
        QSEngine.pool.execute(listener);
    }

    /**
     * 鏂板缓浠诲姟鏃惰璋冪敤锛岄�氳繃杩炴帴璧勬簮鑾峰彇璧勬簮鐩稿叧淇℃伅锛屽苟鏍规嵁鍏蜂綋闀垮害鍒涘缓绾跨▼鍧楋紝
     * 绾跨▼鍒涘缓瀹屾瘯鍚庯紝鍗冲埢閫氳繃绾跨▼姹犺繘琛岃皟搴�
     * @throws RuntimeException
     */
    private void newTask() throws RuntimeException {
        try {
            isNewTask = false;
            URLConnection con = url.openConnection();
            Map<String, List<String>> map = con.getHeaderFields();
            Set<String> set = map.keySet();
            for(String key : set){
            	System.out.println(key + " : " + map.get(key));
            }
            contentLen = con.getContentLength();
            if (contentLen <= 0) {
                System.out.println("鏃犳硶鑾峰彇璧勬簮闀垮害锛屼腑鏂笅杞借繘绋�");
                return;
            }
            file = new File(filename + FILE_POSTFIX);
            int fileCnt = 1;
            while (file.exists()) {
                file = new File(filename += (fileCnt + FILE_POSTFIX));
                fileCnt++;
            }
//            long freespace = file.getFreeSpace();
//            if (contentLen < freespace) {
//                System.out.println("纾佺洏绌洪棿涓嶅銆�");
//                return;
//            }
            long subLen = contentLen / threadQut;

            for (int i = 0; i < threadQut; i++) {
                DLThread thread = new DLThread(this, i + 1, subLen * i, subLen * (i + 1) - 1);
                dlThreads[i] = thread;
                QSEngine.pool.execute(dlThreads[i]);
            }

            QSEngine.pool.execute(listener);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

//    private String percent() {
//    	this.completeTot();
//        curPercent = new BigDecimal(completedTot).divide(new BigDecimal(this.contentLen), 2, BigDecimal.ROUND_HALF_EVEN).divide(new BigDecimal(0.01), 0, BigDecimal.ROUND_HALF_EVEN).toString();
//        return curPercent;
//    }
    
    /**
     * 璁＄畻褰撳墠宸茬粡瀹屾垚鐨勯暱搴﹀苟杩斿洖涓嬭浇鐧惧垎姣旂殑瀛楃涓茶〃绀猴紝鐩墠鐧惧垎姣斿潎涓烘暣鏁�
     * @return
     */
    public String getCurPercent() {
    	this.completeTot();
        curPercent = new BigDecimal(completedTot).divide(new BigDecimal(this.contentLen), 2, BigDecimal.ROUND_HALF_EVEN).divide(new BigDecimal(0.01), 0, BigDecimal.ROUND_HALF_EVEN).toString();
        return curPercent;
    }
    
    private void completeTot(){
    	completedTot = 0;
        for (DLThread t : dlThreads) {
            completedTot += t.getReadByte();
        }
    }

    /**
     * 鍒ゆ柇鍏ㄩ儴绾跨▼鏄惁宸茬粡涓嬭浇瀹屾垚锛屽鏋滃畬鎴愬垯杩斿洖true锛岀浉鍙嶅垯杩斿洖false
     * @return
     */
    public boolean isComplete() {
        boolean completed = true;
        for (DLThread t : dlThreads) {
            completed = t.isFinished();
            if (!completed) {
                break;
            }
        }
        return completed;
    }

//    public boolean percentChanged() {
//        percent();
//        if (curPercent.equals(prevPercent)) {
//            return false;
//        }
//        prevPercent = curPercent;
//        return true;
//    }
    
    public void rename(){
        this.file.renameTo(new File(filename));
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int Level) {
        this.Level = Level;
    }

    public DLThread[] getDlThreads() {
        return dlThreads;
    }

    public void setDlThreads(DLThread[] dlThreads) {
        this.dlThreads = dlThreads;
    }

    public int getTaskId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public File getFile() {
        return file;
    }

    public URL getUrl() {
        return url;
    }

    public int getContentLen() {
        return contentLen;
    }

    public String getFilename() {
        return filename;
    }


    public int getThreadQut() {
        return threadQut;
    }

	public long getCompletedTot() {
		return completedTot;
	}

	public int getCostTime() {
		return costTime;
	}

	public void setCostTime(int costTime) {
		this.costTime = costTime;
	}
   
}
