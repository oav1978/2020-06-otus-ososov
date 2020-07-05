import com.google.common.base.Optional;
import java.util.Date;


/**
 * @author Ososov
 * Created 04.07.2020
 */
public class App {

    public static Optional< String > getStringByTimeDiv2() {
        Optional< String > returnValue = Optional.absent();
        if (new Date().getTime() % 2 == 0) {
            returnValue = Optional.of("ok");
        }
        return returnValue;
    }

    public static void main(String[] args) {
        System.out.println("Lesson01: test guava by gradle.");
        Optional< String > str  = getStringByTimeDiv2();
        System.out.println("str = "+str.or("default value"));
    }

}
