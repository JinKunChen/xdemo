package com.topsem.common.utils;

/**
 * 文件格式
 *
 * @author Chen on 14/12/19.
 */
public enum FileFormat {

    TXT("文本文件"),

    DOC("Word文档"),

    DOCX("Word文档"),

    XLS("Excel文档"),

    XLSX("Excel文档"),

    PPT("POWERPOINT文档"),

    DAT("数据文档"),

    PDF("PDF文档"),

    RAR("压缩文档"),

    ZIP("压缩文档"),

    RTF("写字板文档"),

    HTM("网页文档"),

    HTML("网页文档"),

    MP3("音频文档"),

    MP4("MP4视频文档"),

    AVI("AVI视频文档"),

    RMVB("RMVB视频文档"),

    PSD("PhotoShop文档");

    public final String name;

    FileFormat(String name) {
        this.name = name;
    }

    public String getExtension() {
        return "." + this.name().toLowerCase();
    }


}
