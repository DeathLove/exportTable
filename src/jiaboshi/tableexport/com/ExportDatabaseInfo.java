package jiaboshi.tableexport.com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class ExportDatabaseInfo {

    public Configuration configure = null;

    public ExportDatabaseInfo() {
        configure = new Configuration(Configuration.VERSION_2_3_22);
        configure.setDefaultEncoding("utf-8");
    }

    public void createDoc(Map<String, Object> dataMap, String downloadType, String savePath) {
        try {
            // 加载要装填的模板
            Template template = null;
            // 设置模板装置方法和路径，FreeMarker支持多种模板装载方法。可以重servlet，classpath,数据库装载
            // 加载模板文件，放在testDoc
            configure.setClassForTemplateLoading(this.getClass(), "");
            // 设置对象包装
            // configure.setObjectWrapper(new DefaultObjectWrapper());
            // 设置异常处理
            configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
            // 定义Template对象，注意模板类型名字与downloadType要一致
            template = configure.getTemplate(downloadType + ".xml");
            File outFile = new File(savePath);
            Writer out = null;
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
            template.process(dataMap, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
