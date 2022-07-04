package com.example.cloudDisk.utils.file;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaMetadataKeys;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 成大事
 * @date 2021-12-20
 */
public class FileTypeJudge {
    /**
     * 获取类型
     * @param file
     * @return
     */
    public static String getMimeType(MultipartFile file) {
        //创建自动检测解析器
        AutoDetectParser parser = new AutoDetectParser();
        parser.setParsers(new HashMap<MediaType, Parser>());
        Metadata metadata = new Metadata();
        metadata.add(TikaMetadataKeys.RESOURCE_NAME_KEY, file.getName());
        try (InputStream stream = file.getInputStream()) {
            parser.parse(stream, new DefaultHandler(), metadata, new ParseContext());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return metadata.get(HttpHeaders.CONTENT_TYPE);
    }

    /**
     * 判断是否是图片
     * @param file
     * @return
     */
    public static boolean isImage(MultipartFile file){
        String type = getMimeType(file);
        //对比对应的文件类型的mime就好了至于不知道对应的是什么的话就百度,百度一定会知道
        Pattern pattern = Pattern.compile("image/.*");
        Matcher m = pattern.matcher(type);
        return m.matches();
    }

    /**
     * 获取类型
     * @param multipartFile  文件
     * @return mineType
     */
    public static String getMimeTypeFile(MultipartFile multipartFile) throws Exception {
        File file = MultipartFileToFile.multipartFileToFile(multipartFile);
        if (file.isDirectory()) {
            return "the target is a directory";
        }

        AutoDetectParser parser = new AutoDetectParser();
        parser.setParsers(new HashMap<MediaType, Parser>());

        Metadata metadata = new Metadata();
        metadata.add(TikaMetadataKeys.RESOURCE_NAME_KEY, file.getName());

        InputStream stream;
        try {
            stream = new FileInputStream(file);
            parser.parse(stream, new DefaultHandler(), metadata, new ParseContext());
            stream.close();
        } catch (TikaException | SAXException | IOException e) {
            e.printStackTrace();
        }
        MultipartFileToFile.deleteTempFile(file);
        return metadata.get(HttpHeaders.CONTENT_TYPE);
    }


}
