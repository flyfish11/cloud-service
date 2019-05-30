package com.cloud.file.utils;

import com.cloud.common.enums.ResultEnum;
import com.cloud.file.exception.FileCenterException;
import com.cloud.file.model.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

@Slf4j
public class FileUtil {

    public static FileInfo getFileInfo(MultipartFile file) throws Exception {
        String md5 = fileMd5(file.getInputStream());

        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(md5);// 将文件的md5设置为文件表的id
        fileInfo.setName(file.getOriginalFilename());
        fileInfo.setContentType(file.getContentType());
        fileInfo.setIsImg(fileInfo.getContentType().startsWith("image/"));
        fileInfo.setSize(file.getSize());
        fileInfo.setCreateTime(new Date());

        return fileInfo;
    }

    /**
     * 文件的md5
     *
     * @param inputStream
     * @return
     */
    public static String fileMd5(InputStream inputStream) {
        try {
            return DigestUtils.md5Hex(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String saveFile(MultipartFile file, String path) {
        try {
            File targetFile = new File(path);
            if (targetFile.exists()) {
                return path;
            }

            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            file.transferTo(targetFile);

            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean deleteFile(String pathname) {
        File file = new File(pathname);
        if (file.exists()) {
            boolean flag = file.delete();

            if (flag) {
                File[] files = file.getParentFile().listFiles();
                if (files == null || files.length == 0) {
                    file.getParentFile().delete();
                }
            }

            return flag;
        }

        return false;
    }

    public static byte[] toByteArray(String filename) {

        File f = new File(filename);
        if (!f.exists()) {
            log.error("文件未找到！" + filename);
            throw new FileCenterException(ResultEnum.FILE_NOT_FOUND);
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            throw new FileCenterException(ResultEnum.FILE_READING_ERROR);
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                throw new FileCenterException(ResultEnum.FILE_READING_ERROR);
            }
            try {
                fs.close();
            } catch (IOException e) {
                throw new FileCenterException(ResultEnum.FILE_READING_ERROR);
            }
        }
    }
}
