import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileTest {

    public static void main(String[] args) throws IOException {
        String dirs = "D:\\mlpp\\com.adtec.starring_abf.busi.mlpp\\smartweb";
        traverseFile(dirs);
//		File f = new File(
//				"C:\\Users\\sunlu8719\\Desktop\\smartweb\\smartweb-tseq\\src\\main\\java\\com\\adtec\\comp\\tseq\\util\\SeqTran.java");
//		chkFile(f);
    }

    public static void traverseFile(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            File[] fileArray = file.listFiles();
            for (File f : fileArray) {
                if (f.isDirectory()) {
                    traverseFile(f.getAbsolutePath());
                } else {
//					System.out.println("文件:" + f.getAbsolutePath());
                    if (f.getAbsolutePath().endsWith("java")) {
                        chkFile(f);
                    }
                }
            }
        }
    }

    public static void chkFile(File f) throws IOException {
        int i = 0;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String line = null;
            boolean flg = false;
            boolean blank = false;
            while ((line = br.readLine()) != null) {
                i++;
                line = line.trim();
                if (line.startsWith("/")) {
                    continue;
                } else if (line.contains("catch")) {
//					System.out.println("i=" + i + ",flg=" + flg + ", blank=" + blank);
                    flg = true;
                    blank = true;
                } else if (flg) {
//					System.out.println("i=" + i + ",flg=" + flg + ", blank=" + blank);

                    if (line.trim().length() == 0) {
                        continue;
                    }
                    if (line.startsWith("{")) {
                        continue;
                    }

                    if (!line.contains("}")) {
                        blank = false;
                    } else {
                        if (line.replace("}", "").replace("{", "").replace("\n", "").replace("\r", "").trim()
                                .length() > 0) {
                            blank = false;
                        }
                    }

                    if (line.startsWith("}") || line.endsWith("}")) {
                        if (blank && flg) {
                            System.out.println("行号:" + (i - 1) + ",文件:" + f.getAbsolutePath() + "  有空catch");
                        }
                        flg = false;
                        blank = false;
                    }
//					System.out.println("end i=" + i + ",flg=" + flg + ", blank=" + blank);

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println("出现异常");
            }
        }

    }
}
