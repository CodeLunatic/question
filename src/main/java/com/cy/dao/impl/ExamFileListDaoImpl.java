package com.cy.dao.impl;

import com.cy.dao.IExamFileListDao;
import com.cy.pojo.ExamFileInfo;
import com.cy.utils.SerializationUtil;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 题库中文件列表操作
 * 此类序列化的文件不需要考虑用户的问题，因为文件的位置仅保存在题库中
 * 【状态：已测试2018年12月1日08:56:22】
 * By CY
 * Date 2018/11/27 15:41
 */
@Repository
public class ExamFileListDaoImpl implements IExamFileListDao {

    // 运行时数据要使用的文件夹
    private static final String userDir = System.getProperty("user.dir");

    /**
     * 读取题库文件夹中Excel文件的信息列表
     * 【状态：已测试2018年11月27日18:54:43】
     *
     * @return 返回考试文件的信息列表
     */
    @SuppressWarnings("all")
    private List<ExamFileInfo> getFileList() {
        // 题库的路径
        File file = new File(userDir + File.separator + "题库");
        // 如果没有改文件夹，默认会创建文件夹
        if (!file.exists()) file.mkdirs();
        // 将文件信息装入这个List中
        List<ExamFileInfo> examFileInfos = new ArrayList<>();
        // 递归遍历这个文件夹中的所有的Excel文件
        this.listAllFiles(file, examFileInfos);
        // 将文件列表序列化到硬盘
        this.saveFileListToDisc(examFileInfos);
        // 返回这个列表到页面上
        return examFileInfos;
    }

    /**
     * 通过文件名进行文件的搜索
     * 【状态：已测试2018年11月29日22:40:38】
     *
     * @param fileName 文件的名字
     * @return 文件的查询结果
     */
    @Override
    public List<ExamFileInfo> queryListByFileName(String fileName) {
        // 查询出所有的列表
        List<ExamFileInfo> fileList = this.getFileList();
        // 如果需要查询关键字
        if (fileName != null && !"".equals(fileName.trim())) {
            // 进行模糊查询
            ArrayList<ExamFileInfo> queryList = new ArrayList<>();
            for (ExamFileInfo examFileInfo : fileList)
                if (examFileInfo.getFileName().contains(fileName)) queryList.add(examFileInfo);
            return queryList;
        }
        return fileList;
    }

    /**
     * 列出所有的xls和xlsx文件
     * 【状态：已测试2018年11月27日18:54:59】
     *
     * @param file          要列出文件的目录
     * @param examFileInfos 考试文件的信息列表
     */
    private void listAllFiles(@NotNull File file, @NotNull List<ExamFileInfo> examFileInfos) {
        if (file.isDirectory()) { //如果传入的file是一个文件路径
            File[] list = file.listFiles(); //列出该路径下的所有的文件和文件夹
            //如果列表不为空
            if (list != null) for (File file2 : list) listAllFiles(file2, examFileInfos); //递归继续列出，直到不是一个文件夹为止
        }
        if (file.getName().endsWith(".xls") || file.getName().endsWith(".xlsx")) { //如果文件的后缀是xls和xlsx
            ExamFileInfo examFileInfo = new ExamFileInfo(); // 获得文件的信息
            // 获取文件的名字
            String fileName = file.getName();
            examFileInfo.setFileName(fileName.substring(0, fileName.lastIndexOf(".")));
            String absolutePath = file.getAbsolutePath(); // 得到绝对的路径
            examFileInfo.setRealPath(absolutePath);
            examFileInfo.setFileId(DigestUtils.md5DigestAsHex(absolutePath.getBytes(StandardCharsets.UTF_8))); // 获取文件名所对应的MD5
            examFileInfos.add(examFileInfo); // 把文件信息添加到文件列表中
        }
    }

    /**
     * 记录题库的文件【将题库列表放入硬盘中】
     * 【状态：已测试2018年11月27日23:01:26】
     *
     * @param list 将要序列化的列表
     */
    private void saveFileListToDisc(@NotNull List<ExamFileInfo> list) {
        // 序列化文件保存的位置
        String filePath = userDir + File.separator + "数据" + File.separator + "临时" + File.separator + "FileList.bin";
        // 开始序列化文件列表
        SerializationUtil.serialize(list, filePath);
    }

    /**
     * 通过文件Id获取文件的真实的路径，该方法可能返回null
     * 【状态：已测试2018年11月29日22:55:12】
     *
     * @param fileId 文件的Id
     * @return 文件的真实的路径
     */
    @Override
    public ExamFileInfo getFileInfoByFileId(@NotNull String fileId) {
        if (fileId == null) return null;
        // 从硬盘上得到文件列表
        List<ExamFileInfo> fileList = getFileListFormDisc();
        // 通过fileId查找到真实路径
        for (ExamFileInfo examFileInfo : fileList) {
            // 使用绝对路径的MD5值进行对比得到绝对路径
            String realPath = examFileInfo.getRealPath();
            if (fileId.equals(DigestUtils.md5DigestAsHex(realPath.getBytes(StandardCharsets.UTF_8))))
                return examFileInfo;
        }
        return null;
    }

    /**
     * 从硬盘中的序列化文件中获得文件的信息列表
     * 【状态：已测试2018年11月27日23:01:39】
     *
     * @return 文件的信息列表
     */
    @SuppressWarnings("all")
    private List<ExamFileInfo> getFileListFormDisc() {
        List<ExamFileInfo> list = SerializationUtil.deserialize(userDir + File.separator + "数据" + File.separator + "临时" + File.separator + "FileList.bin", List.class);
        if (list == null) return new ArrayList<>();
        return list;
    }
}
