package com.cy.utils;

import javax.validation.constraints.NotNull;
import java.io.*;

/**
 * 一个对象序列化和反序列化的工具类
 * 已加入synchronized关键字
 * 防止高并发导致后台因为共用了同一个流对象而导致的异常发生
 */
public class SerializationUtil {

    /**
     * 将一个对象序列化到硬盘中
     * 【状态：修复2018年12月22日10:42:04】
     *
     * @param t        将要序列化的对象
     * @param filePath 序列化文件保存的路径
     */
    public synchronized static <T> void serialize(@NotNull T t, @NotNull String filePath) {

        if (t == null) return; // 如果对象为null的话，就不参与序列化

        mkdirs(filePath); // 先创建该目录，以防报错

        // ========== 开始进行序列化

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            serialize(t, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("目标文件夹并不存在，请先创建!", e);
        }
    }

    /**
     * 将一个对象序列化到硬盘中
     * 【状态：修复2018年12月22日10:42:04】
     *
     * @param t            将要序列化的对象
     * @param outputStream 序列化文件的输出流
     */
    @SuppressWarnings("all")
    public synchronized static <T> void serialize(@NotNull T t, @NotNull OutputStream outputStream) {
        // 如果对象为null的话，就不参与序列化
        if (t == null) return;
        if (outputStream == null) return;
        // 开始进行序列化
        BufferedOutputStream bufferedOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            // 使用了缓冲流
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
            objectOutputStream.writeObject(t);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("序列化对象失败!", e);
        } finally {
            // 关流
            try {
                if (objectOutputStream != null) objectOutputStream.close();
                if (bufferedOutputStream != null) bufferedOutputStream.close();
                if (outputStream != null) outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("关闭字节输出流失败!", e);
            }
        }
    }

    /**
     * 创建文件夹
     *
     * @param filePath 文件的路径
     */
    @SuppressWarnings("all")
    private synchronized static void mkdirs(@NotNull String filePath) {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
    }

    /**
     * 反序列化一个文件，使其恢复为一个对象
     * 【状态：修复2018年12月22日10:42:15】
     *
     * @return 反序列化后的对象
     */
    public synchronized static <T> T deserialize(@NotNull String fileDir, Class<T> clazz) {
        // 判断文件是否存在
        File file = new File(fileDir);
        if (!file.exists()) return null;
        try {
            FileInputStream fileInputStream = new FileInputStream(fileDir);
            return deserialize(fileInputStream, clazz);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("文件不存在无法反序列化!", e);
        }
    }

    /**
     * 反序列化一个文件，使其恢复为一个对象
     * 【状态：修复2018年12月22日10:42:15】
     *
     * @return 反序列化后的对象
     */
    @SuppressWarnings("all")
    public synchronized static <T> T deserialize(@NotNull InputStream inputStream, Class<T> clazz) {
        // 判断文件是否存在
        BufferedInputStream bufferedInputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(inputStream);
            objectInputStream = new ObjectInputStream(bufferedInputStream);
            return (T) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("反序列化失败，检查文件路径是否错误!", e);
        } finally {
            try {
                if (objectInputStream != null) objectInputStream.close();
                if (bufferedInputStream != null) bufferedInputStream.close();
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("关流字节输入流失败!", e);
            }
        }
    }
}