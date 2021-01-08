import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

public class WindowsRegistrySnippet {
    public static void main(String[] args) {
        // Read a string
        String productName = Advapi32Util.registryGetStringValue(
                                                                 WinReg.HKEY_LOCAL_MACHINE,
                                                                 "SOFTWARE\\Image-Line\\Shared\\Paths",
                                                                 "FL Studio");
        System.out.printf("Product Name: %s\n", productName);


    }

    public static String readRegistry(String Location, String Key) {
        String s = Advapi32Util.registryGetStringValue(
                                                       WinReg.HKEY_LOCAL_MACHINE,
                                                       Location,
                                                       Key);
        return s;
    }
}
