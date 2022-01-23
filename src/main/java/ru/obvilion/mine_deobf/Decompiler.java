package ru.obvilion.mine_deobf;

import org.benf.cfr.reader.Main;

import java.io.File;

public class Decompiler {
    public static void start() {
        Vars.DECOMP_DIR = new File(Vars.SELECTED_FILE.getParentFile(), "decompiled-sources");
        Vars.DECOMP_DIR.mkdir();

        Main.main(new String[]{ Vars.SELECTED_FILE.getPath(), "--outputdir", Vars.DECOMP_DIR.getPath(), "--outputencoding", "utf-8" });
    }
}
