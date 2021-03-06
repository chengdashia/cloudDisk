package com.example.cloudDisk.utils.hdfs;

import com.example.cloudDisk.common.result.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author 成大事
 * @since 2022/4/20 10:10
 * hdfs平台接口类，批量上传和下载文件
 */
@Slf4j
@Component
public class HdfsUtil {

    private FileSystem fs = null;


    @Value("${hdfs.url}")
    private String url;

    /**操作用户*/
    @Value("${hdfs.userName}")
    private String userName;

    /**操作存储节点路径*/
    @Value("${hdfs.port}")
    private String port;


    /**
     * 初始化相关配置
     * @throws URISyntaxException
     */
    public HdfsUtil() throws URISyntaxException {
        Configuration conf = new Configuration();
        String url = "hdfs://10.111.43.13:8020";
        URI uri = new URI(url);
        try {
            fs = FileSystem.get(uri, conf, "root");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*--------------------文件表面的操作------------------------------------*/

    /**
     * 循环删除某一文件夹下的所有文件
     * @param path hdfs 的路径
     */
    public void deleteAllFilesInThisFolder(String path) {
        File f = new File(path);
        File[] f1 = f.listFiles();
        for (File f2 : f1) {
            if (f2.isFile() && f2.getName().endsWith(".crc")) {
                f2.delete();
            } else {
                if (f2.isDirectory()) {
                    deleteAllFilesInThisFolder(f2.getAbsolutePath());
                }

            }

        }

    }

    /**
     * 上传文件到hdfs,sc11本地文件目录，scr2 hdfs文件目录
     * @param localPath      本地路径
     * @param hdfsPath        hdfs 路径
     * @return    boolean
     */
    public boolean upload(String localPath, String hdfsPath) {
        if (!checkFileExist(hdfsPath)){
            createFolder(hdfsPath);
        }
        try {
        // init();
            localPath = "file:///" + localPath;
            fs.copyFromLocalFile(true,false,new Path(localPath), new Path(hdfsPath));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从hdfs上传 文件 通过流的形式
     * @param file      MultipartFile  文件
     * @param hdfsPath        hdfs 路径
     */
    public boolean upload(@Email MultipartFile file, String hdfsPath) throws IOException {
        InputStream in = file.getInputStream();
        FSDataOutputStream out = fs.create(new Path(hdfsPath));
        try {
            IOUtils.copyBytes(in,out,new Configuration());
            return true;
        }catch (Exception e){
            throw new BaseException(e.getMessage());
        }
    }

    /**
     * 从hdfs下载文件，到本地路径
     * @param localPath      本地路径
     * @param hdfsPath        hdfs 路径
     */
    public void download(String hdfsPath, String localPath) {
        try {
            fs.copyToLocalFile(new Path(hdfsPath), new Path(localPath));
            deleteAllFilesInThisFolder(localPath);
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * 删除hdfs的某个文件或者目录
     * @param hdfsPath hdfs 路径
     */
    public void deleteFileOrFolder(String hdfsPath) {
        try {
            fs.delete(new Path(hdfsPath), true);
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 文件改名
     * @param hdfsPath            hdfs的路径
     * @param newName             文件的新的名字
     * @return                boolean
     */
    public boolean fileRename(String hdfsPath, String newName){
        try{
            String path = hdfsPath.substring(0,hdfsPath.lastIndexOf("/") + 1);
            String suffix = hdfsPath.substring(hdfsPath.lastIndexOf(".")+1);
            String changePath = path+newName+"."+suffix;
            // 文件名、文件夹名更改
            fs.rename(new Path(hdfsPath),new Path(changePath));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 文件夹改名
     * @param hdfsPath            hdfs的路径
     * @param newName             文件的新的名字
     * @return                boolean
     */
    public boolean folderRename(String hdfsPath, String newName){
        try{
            String path = hdfsPath.substring(0,hdfsPath.lastIndexOf("/") + 1);
            String changePath = path + newName;
            // 文件名、文件夹名更改
            fs.rename(new Path(hdfsPath),new Path(changePath));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 检查文件或者文件夹是否存在
     *
     * @param filename  文件或者文件夹的完整路径
     * @return true 存在 false 不存在
     */
    public boolean checkFileExist(String filename) {
        try {
            Path f = new Path(filename);
            return fs.exists(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 创建文件目录
     * @param hdfsPath    hdfs 路径
     * @return     boolean
     */
    public boolean createFolder(String hdfsPath) {
        if(checkFileExist(hdfsPath)){
            return false;
        }
        try {
            fs.mkdirs(new Path(hdfsPath));
            return true;
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 创建文件
     * @param src     路径
     */
    public void creatFile(String src) {
        try {
            FSDataOutputStream fis = fs.create(new Path(src), new Progressable() {
                @Override
                public void progress() {
                    System.out.println(".");
                }
            });
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 移动文件或者文件夹
     * @param src     初始路径
     * @param dst     移动结束路径
     */
    public void moveFile(String src, String dst) throws Exception {
        Path p1 = new Path(src);
        Path p2 = new Path(dst);
        fs.rename(p1, p2);
    }

    /**
     * 查看某个目录下的文件，并打印出其详细信息
     * @param hdfsPath  hdfs 路径
     */
    public void getFilesByFolder(String hdfsPath) {
        FileStatus[] file1;
        try {

            file1 = fs.listStatus(new Path(hdfsPath));
            for(FileStatus s:file1) {
                System.out.println("time:"+s.getAccessTime());
                System.out.println("group:"+s.getGroup());
                System.out.println("blocksize:"+s.getBlockSize());
                System.out.println("owner:"+s.getOwner());
            }
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }
    }






    /*--------------------文件本身的的操作------------------------------------*/

    /**
     *
     * @param hdfsPath hdfs path
     */
    public void catFile(String hdfsPath)
    {
        try {
            RemoteIterator<LocatedFileStatus> ri= fs.listFiles(new Path(hdfsPath), false);
            while(ri.hasNext())
            {
                LocatedFileStatus file=ri.next();
                BlockLocation[] blc=file.getBlockLocations();

                for(BlockLocation b:blc)
                {

                    System.out.println("length--"+b.getLength());
                    System.out.println("name--");
                    String[] ss=b.getNames();
                    for(String str:ss)
                    {
                        System.out.println(ss);
                    }
                    System.out.println("offset--"+b.getOffset());
                    String hos[]=b.getHosts();
                    System.out.println("hosname:");
                    for(String s:hos)
                    {
                        System.out.println(s);

                    }

                }
            }

        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }

    }
}
