package ru.obvilion.mine_deobf;

import ru.obvilion.mine_deobf.gui.Frame;
import ru.obvilion.mine_deobf.utils.DualStream;

import java.io.IOException;

public class LauncherWrapper {

    public static void main(String[] args) throws IOException {
        update_out();

        Vars.FRAME = new Frame();
        Vars.FRAME.setVisible(true);

        System.out.println(" - Please specify source folder with .java files for deobfuscation or .jar file for decompilation and");
        System.out.println(" deobfuscation");
    }

    public static void update_out() {
        System.setErr(new DualStream(System.err));
        System.setOut(new DualStream(System.out));
    }
}
