package com.cloud.file.service;

import org.springframework.web.multipart.MultipartFile;

import com.cloud.file.model.FileInfo;

public interface FileService {

    FileInfo upload(MultipartFile file) throws Exception;

    void delete(FileInfo fileInfo);
}
