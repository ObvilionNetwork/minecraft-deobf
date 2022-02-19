package ru.obvilion.mine_deobf;

import ru.obvilion.mine_deobf.gui.Frame;

import java.io.File;

public class Vars {
    public static int WINDOW_WIDTH = 700;
    public static int WINDOW_HEIGHT = 450;

    public static String WINDOW_NAME = "Obvilion Decompiler";

    public static Frame FRAME = null;

    public static File SELECTED_FILE = null;
    public static File DECOMP_DIR = null;
    public static File DEOBF_DIR = null;

    public static String SELECTED_ENCODING = null;
    public static String SELECTED_VERSION = "1.7.10";

    public static final File USER_HOME = new File(System.getProperty("user.home"));
    public static final File LAUNCHER_HOME = new File(USER_HOME, ".ObvilionNetwork");
    public static final File CONFIG_FILE = new File(LAUNCHER_HOME, "mc-decomp.properties");
}
