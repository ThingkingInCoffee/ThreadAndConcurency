import java.util.Date;

public class DemoTest {

    public static void main(String[] args) {
        try {
            Object o = Date.class.newInstance();
            if (o instanceof Date) {
                System.out.println("true");
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

}
