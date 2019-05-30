package com.cloud.appmanage.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



import java.io.*;

import java.util.Map;

@Slf4j
@Component
public class FreemakerUtil {

    @Value("${web.upload-path}")
    private  String templatePath;

    public static String DOKERFILE_TEMPLETE = "Dockerfile.ftl";

    public static String SH_TEMPLETE = "sh.ftl";

    public static String DEPLOYMENT_TEMPLETE = "deployment.ftl";

    public static String DOKERFILE_NAME = "Dockerfile";

    public static String SH_NAME = "build.sh";

    public static String DEPLOYMENT_NAME = "deployment.yaml";

    public  void createFile(Map map, String template, String dir) {
       /* FreeMarkerConfigurer configurer = new FreeMarkerConfigurer() ;*/
        Writer writer = null;
        //模板加载
        try {

            Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);

          //  File file = ResourceUtils.getFile("com/cloud/appmanage/templates");

           // cfg.setDirectoryForTemplateLoading(ResourceUtils.getFile("classpath:templates"));
            cfg.setDirectoryForTemplateLoading(new File(templatePath+"/templates"));

            Template temp = cfg.getTemplate(template);


            //显示生成数据
            if (DOKERFILE_TEMPLETE.equals(template)) {
                writer = new OutputStreamWriter(new FileOutputStream(dir + "/" + DOKERFILE_NAME), "UTF-8");
                log.info("生成文件===================================================>"+ dir + "/" + DOKERFILE_NAME);
                temp.process(map, writer);
            }
            if (DEPLOYMENT_TEMPLETE.equals(template)){
                writer = new OutputStreamWriter(new FileOutputStream(dir + "/" + DEPLOYMENT_NAME), "UTF-8");
                log.info("生成文件===================================================>"+ dir + "/" + DEPLOYMENT_NAME);
                temp.process(map, writer);
            }
            if (SH_TEMPLETE.equals(template)){
                writer = new OutputStreamWriter(new FileOutputStream(dir + "/" + SH_NAME), "UTF-8");
                log.info("生成文件===================================================>"+ dir + "/" + SH_NAME);

                temp.process(map, writer);
            }
        } catch (Exception e) {
            log.info("异常===================================================>"+ e.getMessage());
            e.printStackTrace();

        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public  void createFile(Map map, String dir) {
        createFile(map, DOKERFILE_TEMPLETE,dir);
        createFile(map, SH_TEMPLETE,dir);
        createFile(map, DEPLOYMENT_TEMPLETE,dir);

    }
}
