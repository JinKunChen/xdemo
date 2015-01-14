package com.topsem.common.utils;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * 文件格式转换器（首先要开启OpenOffice服务）
 * 命令：soffice  -headless -accept="socket,host=127.0.0.1,port=8100;urp;"
 * @author Chen on 14/12/19.
 */
public class FileConverter {

    /**
     * 将一个Excel流转换成PDF流
     *
     * @param inputStream
     * @return
     */
    public static void excelToPDF(InputStream inputStream, OutputStream outputStream) {
        try {
            OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
            connection.connect();
            DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();
            DocumentFormat pdfFormat = formatReg.getFormatByFileExtension(FileFormat.PDF.name());
            DocumentFormat docFormat = formatReg.getFormatByFileExtension(FileFormat.XLS.name());
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(inputStream, docFormat, outputStream, pdfFormat);
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        InputStream inputStream = FileUtils.openInputStream(new File("/Users/Chen/Downloads/用户信息.xls"));
        OutputStream outputStream = FileUtils.openOutputStream(new File("/Users/Chen/Downloads/用户信息.pdf"));
        excelToPDF(inputStream, outputStream);
    }

}
