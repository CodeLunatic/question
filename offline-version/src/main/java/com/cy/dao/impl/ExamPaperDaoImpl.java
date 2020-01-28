package com.cy.dao.impl;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.cy.dao.IExamPaperDao;
import com.cy.pojo.ExamPaper;
import com.cy.utils.SerializationUtil;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 试题操作数据层【已测试成功】
 * 此类的文件不需要考虑用户的问题，因为Id是唯一的
 * 本类使用了阿里巴巴的EasyExcel开源工具进行Excel的解析
 * 【状态：已测试2018年12月1日09:06:42】
 *
 * @author CY
 */
@Repository
public class ExamPaperDaoImpl implements IExamPaperDao {

    // 规定Excel的两种格式
    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";

    // 运行环境目录
    private static final String userDir = System.getProperty("user.dir");

    /**
     * 读入Excel文件，封装到试题操作的实体类
     * 【状态：已测试2018年11月27日23:00:38】
     *
     * @param fileDir 文件的路径
     * @throws IOException 如果读取文件或者路径什么的有问题的话
     */
    @Override
    public List<ExamPaper> getExamPaperList(@NotNull String fileDir) throws IOException {
        //先要检查文件的正确性!!!
        checkFile(fileDir);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<ExamPaper> list = new ArrayList<>();
        // 输入流
        FilterInputStream bis = new BufferedInputStream(new FileInputStream(fileDir));
        // 解析事件监听器
        AnalysisEventListener<ExamPaper> eventListener = new AnalysisEventListener<ExamPaper>() {
            // 进行数据解析的处理
            public void invoke(ExamPaper examPaper, AnalysisContext context) {
                // 设置试题的Id
                String questionId = DigestUtils.md5DigestAsHex(examPaper.toString().getBytes(StandardCharsets.UTF_8));
                examPaper.setQuestionId(questionId);
                // 设置试题的来源
                examPaper.setFrom(fileDir);
                // 装进集合
                list.add(examPaper);// 数据存储到list，供批量处理，或后续自己业务逻辑处理。
            }

            // 数据全部解析完毕后的处理
            public void doAfterAllAnalysed(AnalysisContext context) {
            }
        };
        // Excel解析对象
        ExcelReader excelReader = new ExcelReader(bis, null, eventListener, true);
        // 开始Excel的解析，解析第1个工作表，从第二行开始
        excelReader.read(new Sheet(1, 1, ExamPaper.class));
        // 输入流关闭
        bis.close();
        // 将这份试卷序列化到硬盘上，准备批卷使用，无论何时都要序列化
        saveExamPaperToDisc(list, fileDir);
        return list;
    }

    /**
     * 将这份试卷序列化到硬盘上，准备批卷使用，无论何时都要序列化，以保证数据的实时性
     * <p>
     * 【状态：已测试2018年11月29日23:00:28】
     *
     * @param list    要序列化的试卷
     * @param fileDir 要序列化文件的绝对路径，用来获取MD5
     */
    private void saveExamPaperToDisc(List<ExamPaper> list, String fileDir) {
        SerializationUtil.serialize(list, userDir + File.separator + "数据" + File.separator + "临时" + File.separator + DigestUtils.md5DigestAsHex(fileDir.getBytes(StandardCharsets.UTF_8)) + ".bin");
    }

    /**
     * 从硬盘的序列化文件中找到试题列表,用来批卷
     * 【状态：已测试2018年11月29日23:22:37】
     *
     * @param fileId 文件的Id
     * @return 试题列表
     */
    @SuppressWarnings("all")
    @Override
    public List<ExamPaper> getExamPaperListFromDisc(String fileId) {
        //从硬盘上得到试卷的答案
        List<ExamPaper> list = SerializationUtil.deserialize(userDir + File.separator + "数据" + File.separator + "临时" + File.separator + fileId + ".bin", List.class);
        if (list == null) return new ArrayList<>();
        return list;
    }

    /**
     * 检查文件的正确性
     * 【状态：已测试2018年11月27日23:00:53】
     *
     * @param fileDir 操作文件的路径
     * @throws IOException 如果路径什么的有问题的话
     */
    private void checkFile(@NotNull String fileDir) throws IOException {
        if (fileDir == null) throw new FileNotFoundException("文件已经不存在了！");
        // 根据路径名得到这个文件的对象
        File file = getFile(fileDir);
        //判断文件是否存在
        if (!file.exists()) throw new FileNotFoundException("文件不存在！");
        //获得文件名
        String fileName = file.getName();
        //判断文件是否是excel文件
        if (!fileName.endsWith(XLS) && !fileName.endsWith(XLSX)) throw new IOException(fileName + "不是Excel文件！");
    }

    /**
     * 得到文件的对象
     * 【状态：已测试2018年11月27日23:01:00】
     *
     * @param fileDir 文件的路径
     * @return 文件的对象
     */
    private File getFile(String fileDir) {
        //获得文件名
        File file = new File(fileDir);
        if (file.isDirectory()) throw new IllegalArgumentException("参数必须是一个具体的文件，而不是文件夹");
        return file;
    }
}