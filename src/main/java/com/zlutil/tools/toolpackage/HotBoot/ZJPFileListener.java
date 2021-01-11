package com.zlutil.tools.toolpackage.HotBoot;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;


/**
 * 监控指定文件夹内文件以及文件夹的变动
 * 以文件为单位进行监控
 */
@Slf4j
@Service
public class ZJPFileListener implements FileAlterationListener, ApplicationContextAware {

    HotBootUtil hotBootUtil;

    private static ZJPFileListener zjpFileListener;

    ZJPFileMonitor monitor = null;

    /**
     * 通过@PostConstruct实现初始化bean之前进行的操作
     */
    @PostConstruct
    public void init() {
        zjpFileListener = this;
    }

    /**
     * @param fileAlterationObserver
     */
    @Override
    public void onStart(FileAlterationObserver fileAlterationObserver) {
        log.info("开始监控");
    }

    /**
     * 监控目录中创建一个目录时触发
     *
     * @param file
     */
    @Override
    public void onDirectoryCreate(File file) {
        log.info("监控文件夹创建，路径: " + file.getPath());
        //目录创建暂时不做处理
        //System.out.println("监控目录创建");
    }

    /**
     * 监控目录中目录发生改变触发
     *
     * @param file
     */
    @Override
    public void onDirectoryChange(File file) {
        log.info("监控文件夹修改，路径: " + file.getPath());
    }

    /**
     * 监控目录中目录发生删除触发
     *
     * @param file
     */
    @Override
    public void onDirectoryDelete(File file) {
        log.info("监控文件夹删除，路径: " + file.getPath());
    }

    /**
     * 监控目录中创建文件时触发
     *
     * @param file
     */
    @SneakyThrows
    @Override
    public void onFileCreate(File file) {
        log.info("监控文件创建，路径: " + file.getPath());
        this.hotBootUtil=applicationContext.getBean(HotBootUtil.class);
        hotBootUtil.fileOnCreateUtil(file);
    }

    /**
     * 监控目录中改变文件时触发
     *
     * @param file
     */
    @SneakyThrows
    @Override
    public void onFileChange(File file) {
        log.info("监控文件修改，路径: " + file.getPath());
        this.hotBootUtil=applicationContext.getBean(HotBootUtil.class);
        hotBootUtil.fileOnChangeUtil(file);
    }

    /**
     * 监控目录中文件删除时触发
     *
     * @param file
     */
    @SneakyThrows
    @Override
    public void onFileDelete(File file) {
        log.info("监控文件删除，路径: " + file.getPath());
        //监听器无法自动注入Bean，手动获取
        this.hotBootUtil=applicationContext.getBean(HotBootUtil.class);
        hotBootUtil.fileOnDeleteUtil(file.getPath());
    }

    /**
     * @param fileAlterationObserver
     */
    @Override
    public void onStop(FileAlterationObserver fileAlterationObserver) {
        log.info("监控停止");
    }

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContextParam) throws BeansException {
        applicationContext=applicationContextParam;
    }
}
