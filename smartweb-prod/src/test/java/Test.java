import com.adtec.framework.common.util.DateUtil;

public class Test {
    public static void main(String[] args) {
        String date = "20240611";
        String date1 = "20240301";
        System.out.println(DateUtil.addDate(date,-1));
        System.out.println(DateUtil.addDate(date1,-1));
    }
}
