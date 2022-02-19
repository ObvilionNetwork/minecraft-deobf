package ru.obvilion.mine_deobf;

import org.benf.cfr.reader.Main;

import java.io.File;

public class Decompiler {
    public static void start() {
        Vars.DECOMP_DIR = new File(Vars.SELECTED_FILE.getParentFile(), "decompiled-sources/" + Vars.SELECTED_FILE_NAME);
        Vars.DECOMP_DIR.mkdirs();

        if (Vars.SELECTED_ENCODING == null) {
            Main.main(new String[]{ Vars.SELECTED_FILE.getPath(), "--outputdir", Vars.DECOMP_DIR.getPath(), "--comments", "false", });
        } else {
            Main.main(new String[]{ Vars.SELECTED_FILE.getPath(), "--outputdir", Vars.DECOMP_DIR.getPath(), "--comments", "false", "--outputencoding", Vars.SELECTED_ENCODING });
        }
    }
}
