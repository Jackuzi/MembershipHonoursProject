
import com.membershipApp.DatabaseConnectionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class manageTest {


  @Test
  public void firstTest() {
    new DatabaseConnectionHandler().getConn();
    System.out.println("hello");
    String hello = "hello";
    Assertions.assertEquals("hello", hello);


  }
}

