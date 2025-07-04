@DeprecatedEx(message = "newService")
public class OldSevice {
    @DeprecatedEx(message = "newMethod")
    public void oldMethod() {
        System.out.println("oldMethod");
    }

    public void newMethod() {
        System.out.println("newMethod");
    }
}
