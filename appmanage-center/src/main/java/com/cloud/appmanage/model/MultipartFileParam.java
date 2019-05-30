package com.cloud.appmanage.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;


public class MultipartFileParam {


    //任务ID
    @ApiModelProperty("任务ID")
    private String id;
    //总分片数量
    @ApiModelProperty("总分片数量")
    private int chunks;
    //当前为第几块分片
    @ApiModelProperty("当前为第几块分片")
    private int chunk;
    //当前分片大小
    @ApiModelProperty("当前分片大小")
    private long size = 0L;
    //文件数据
    @ApiModelProperty("文件数据")
    private MultipartFile file;
    //文件名称
    @ApiModelProperty("文件名称")
    private String name;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getChunks() {
        return chunks;
    }

    public void setChunks(int chunks) {
        this.chunks = chunks;
    }

    public int getChunk() {
        return chunk;
    }

    public void setChunk(int chunk) {
        this.chunk = chunk;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MultipartFileParam{" +
                "id='" + id + '\'' +
                ", chunks=" + chunks +
                ", chunk=" + chunk +
                ", size=" + size +
                ", file=" + file +
                ", name='" + name + '\'' +
                '}';
    }
}