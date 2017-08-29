package com.yuyu.soft.util.word;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class WordUtil {

    public static File createWord(Map<String, Object> dataMap, String templateName,
                                  String filePath, String fileName) {

        File outFile = new File(filePath + File.separator + fileName);
        try {
            //创建配置实例 
            Configuration configuration = new Configuration();
            configuration.setDefaultEncoding("UTF-8");
            configuration.setClassForTemplateLoading(WordUtil.class,
                "/com/yuyu/soft/util/word/template/");
            Template template = configuration.getTemplate(templateName);
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),
                "UTF-8"));
            template.process(dataMap, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outFile;
    }
}