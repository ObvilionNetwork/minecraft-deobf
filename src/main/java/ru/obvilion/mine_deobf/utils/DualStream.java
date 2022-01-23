package ru.obvilion.mine_deobf.utils;

import ru.obvilion.mine_deobf.Vars;

import java.io.OutputStream;
import java.io.PrintStream;

public class DualStream extends PrintStream {
    public static String prefix = "[Main thread] ";

    public DualStream(OutputStream out1) {
        super(out1);
    }

    public void write(byte[] buf, int off, int len) {
        try {
            super.write(buf, off, len);

            String s = new String(buf, off, len);
            if (s.equals("\r\n")) return;

            s = s.replaceAll("\n", "\n" + prefix);

            Vars.FRAME.logs.append("\n");
            Vars.FRAME.logs.setCaretPosition(Vars.FRAME.logs.getText().length());
            Vars.FRAME.logs.append(prefix + s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void flush() {
        super.flush();
    }
}
