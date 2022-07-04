package com.example.cloudDisk.utils.file;


import com.example.cloudDisk.utils.Constants.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 成大事
 * @date 2021-12-20
 */
public class FileTypeUtil {

    public final static Map<String,String> FILE_TYPE_MAP = new HashMap<>();


    public final static Map<String,Integer> FILE_TYPE = new HashMap<>();

    static{

        //一些常见文件后缀
        FILE_TYPE_MAP.put("application/msword","doc" );
        FILE_TYPE_MAP.put("application/pdf","pdf" );
        FILE_TYPE_MAP.put("application/rtf","rtf" );
        FILE_TYPE_MAP.put("application/x-rar-compressed","rar" );
        FILE_TYPE_MAP.put("application/zip","zip" );
        FILE_TYPE_MAP.put("text/plain","txt" );

        //一些常见图片后缀
        FILE_TYPE_MAP.put("image/bmp","bmp" );
        FILE_TYPE_MAP.put("image/png","png" );
        FILE_TYPE_MAP.put("image/cis-cod","cod" );
        FILE_TYPE_MAP.put("image/gif","gif" );
        FILE_TYPE_MAP.put("image/ief","ief" );
        FILE_TYPE_MAP.put("image/jpeg","jpg" );
        FILE_TYPE_MAP.put("image/pipeg","jfif" );
        FILE_TYPE_MAP.put("image/svg+xml","svg" );
        FILE_TYPE_MAP.put("image/tiff","tiff" );
        FILE_TYPE_MAP.put("image/x-cmu-raster","ras" );
        FILE_TYPE_MAP.put("image/x-cmx","cmx" );
        FILE_TYPE_MAP.put("image/x-icon","ico" );
        FILE_TYPE_MAP.put("image/x-portable-anymap","pnm" );
        FILE_TYPE_MAP.put("image/x-portable-bitmap","pbm" );
        FILE_TYPE_MAP.put("image/x-portable-graymap","pgm" );
        FILE_TYPE_MAP.put("image/x-portable-pixmap","ppm" );
        FILE_TYPE_MAP.put("image/x-rgb","rgb" );
        FILE_TYPE_MAP.put("image/x-xbitmap","xbm" );
        FILE_TYPE_MAP.put("image/x-xpixmap","xpm" );
        FILE_TYPE_MAP.put("image/x-xwindowdump","xwd" );

        //一些常见音频后缀
        FILE_TYPE_MAP.put("audio/basic","au" );
        FILE_TYPE_MAP.put("audio/midi","mid" );
        FILE_TYPE_MAP.put("audio/mpeg","mp3" );
        FILE_TYPE_MAP.put("audio/x-aiff","aif" );
        FILE_TYPE_MAP.put("audio/x-mpegurl","m3u" );
        FILE_TYPE_MAP.put("audio/x-pn-realaudio","ra" );
        FILE_TYPE_MAP.put("audio/x-wav","wav" );

        //一些常见视频后缀
        FILE_TYPE_MAP.put("video/mpeg","mpg" );
        FILE_TYPE_MAP.put("video/quicktime","mov" );
        FILE_TYPE_MAP.put("video/x-msvideo","avi" );
        FILE_TYPE_MAP.put("video/x-sgi-movie","movie" );

        //一些常见压缩文件后缀
        FILE_TYPE_MAP.put("application/x-compressed","zip" );
        FILE_TYPE_MAP.put("application/x-zip-compressed","zip" );
        FILE_TYPE_MAP.put("multipart/x-zip","zip" );

        //一些常见办公文档后缀
        FILE_TYPE_MAP.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document","docx" );
        FILE_TYPE_MAP.put("application/vnd.ms-excel","xls" );
        FILE_TYPE_MAP.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet","xlsx" );
        FILE_TYPE_MAP.put("application/vnd.ms-powerpoint","ppt" );
        FILE_TYPE_MAP.put("application/vnd.openxmlformats-officedocument.presentationml.presentation","pptx" );
        FILE_TYPE_MAP.put("application/x-tika-ooxml","pptx" );
        FILE_TYPE_MAP.put("application/x-tika-msoffice","ppt" );

        //一些常见音频后缀
        FILE_TYPE_MAP.put("audio/mid","mid" );
        FILE_TYPE_MAP.put("audio/x-ms-wma","wma" );
        FILE_TYPE_MAP.put("audio/x-ms-wax","wax" );
        FILE_TYPE_MAP.put("audio/x-aac","aac" );
        FILE_TYPE_MAP.put("audio/mp4","mp4" );
        FILE_TYPE_MAP.put("audio/x-m4a","m4a" );
        FILE_TYPE_MAP.put("audio/ogg","ogg" );
        FILE_TYPE_MAP.put("audio/flac","flac" );
        FILE_TYPE_MAP.put("audio/aiff","aiff" );


        //一些常见办公文档后缀
        FILE_TYPE_MAP.put("application/vnd.ms-excel.addin.macroEnabled.12","xlam" );
        FILE_TYPE_MAP.put("application/vnd.ms-excel.sheet.binary.macroEnabled.12","xlsb" );
        FILE_TYPE_MAP.put("application/vnd.ms-excel.sheet.macroEnabled.12","xlsm" );
        FILE_TYPE_MAP.put("application/vnd.ms-excel.template.macroEnabled.12","xltm" );
        FILE_TYPE_MAP.put("application/vnd.openxmlformats-officedocument.spreadsheetml.template","xltx" );
        FILE_TYPE_MAP.put("application/x-gzip","gz" );
        FILE_TYPE_MAP.put("application/x-tar","tar" );
        FILE_TYPE_MAP.put("application/x-stuffit","sit" );




        //文档
        FILE_TYPE.put("txt", Constant.WORD_TYPE);
        FILE_TYPE.put("doc",Constant.WORD_TYPE);
        FILE_TYPE.put("xls",Constant.WORD_TYPE);
        FILE_TYPE.put("ppt",Constant.WORD_TYPE);
        FILE_TYPE.put("docx",Constant.WORD_TYPE);
        FILE_TYPE.put("xlsx",Constant.WORD_TYPE);
        FILE_TYPE.put("pptx",Constant.WORD_TYPE);
        FILE_TYPE.put("pdf",Constant.WORD_TYPE);

        //图片
        FILE_TYPE.put("bmp",Constant.IMAGE_TYPE);
        FILE_TYPE.put("png",Constant.IMAGE_TYPE);
        FILE_TYPE.put("cod",Constant.IMAGE_TYPE);
        FILE_TYPE.put("gif",Constant.IMAGE_TYPE);
        FILE_TYPE.put("ief",Constant.IMAGE_TYPE);
        FILE_TYPE.put("jfif",Constant.IMAGE_TYPE);
        FILE_TYPE.put("jpg",Constant.IMAGE_TYPE);
        FILE_TYPE.put("svg",Constant.IMAGE_TYPE);
        FILE_TYPE.put("tiff",Constant.IMAGE_TYPE);
        FILE_TYPE.put("ras",Constant.IMAGE_TYPE);
        FILE_TYPE.put("cmx",Constant.IMAGE_TYPE);
        FILE_TYPE.put("ico",Constant.IMAGE_TYPE);
        FILE_TYPE.put("rgb",Constant.IMAGE_TYPE);
        FILE_TYPE.put("xbm",Constant.IMAGE_TYPE);
        FILE_TYPE.put("xpm",Constant.IMAGE_TYPE);
        FILE_TYPE.put("xwd",Constant.IMAGE_TYPE);

        //视频
        FILE_TYPE.put("mp4",Constant.VIDEO_TYPE);
        FILE_TYPE.put("avi",Constant.VIDEO_TYPE);
        FILE_TYPE.put("rmvb",Constant.VIDEO_TYPE);
        FILE_TYPE.put("mkv",Constant.VIDEO_TYPE);
        FILE_TYPE.put("wmv",Constant.VIDEO_TYPE);

        //音频
        FILE_TYPE.put("mp3",Constant.AUDIO_TYPE);
        FILE_TYPE.put("wav",Constant.AUDIO_TYPE);
        FILE_TYPE.put("mid",Constant.AUDIO_TYPE);
        FILE_TYPE.put("ogg",Constant.AUDIO_TYPE);
        FILE_TYPE.put("ape",Constant.AUDIO_TYPE);
        FILE_TYPE.put("flac",Constant.AUDIO_TYPE);

        //其他文件
        FILE_TYPE.put("rar",Constant.OTHER_TYPE);
        FILE_TYPE.put("zip",Constant.OTHER_TYPE);
        FILE_TYPE.put("7z",Constant.OTHER_TYPE);
        FILE_TYPE.put("tar",Constant.OTHER_TYPE);
        FILE_TYPE.put("gz",Constant.OTHER_TYPE);
        FILE_TYPE.put("bz2",Constant.OTHER_TYPE);
        FILE_TYPE.put("iso",Constant.OTHER_TYPE);
        FILE_TYPE.put("jar",Constant.OTHER_TYPE);
        FILE_TYPE.put("war",Constant.OTHER_TYPE);
        FILE_TYPE.put("cab",Constant.OTHER_TYPE);
        FILE_TYPE.put("arj",Constant.OTHER_TYPE);
        FILE_TYPE.put("lzh",Constant.OTHER_TYPE);
        FILE_TYPE.put("ace",Constant.OTHER_TYPE);



    }
}
