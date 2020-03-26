import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class e {
    public static String dateToWeek(String datetime) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        datet = f.parse(datetime);
        cal.setTime(datet);
        int w = cal.get(Calendar.DAY_OF_WEEK) + 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static void main(String[] args) throws ParseException {
        String s = dateToWeek("2019-3-28");
        System.out.println(s);
        String string = "123,1234,22345";
        String []str = string.split(",");
        System.out.println(str[0].equals("123"));
        if(str[0]=="123"){
            System.out.println(Integer.parseInt("123"));
        }
    }
}
