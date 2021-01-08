

import java.io.PrintWriter;

public class CMDPrompt {
    public static void main(String[] args) {
        runCMD("D");
    }

    public static void runCMD(String Cmdstr) {
        String[] command =
                        {
                                        "cmd",
                        };
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);

            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println(Cmdstr.substring(1, 3));

            stdin.println(Cmdstr);


            stdin.close();
            p.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
