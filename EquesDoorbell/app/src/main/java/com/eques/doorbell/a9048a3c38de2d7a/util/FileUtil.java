package com.eques.doorbell.a9048a3c38de2d7a.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtil {
    public static final void saveObject(String path, Object saveObject) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        File f = new File(path);
        try {
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(saveObject);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static final Object restoreObject(String path) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object object = null;
        File f = new File(path);
        if (!f.exists()) {
            return null;
        }
        try {
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            object = ois.readObject();
            return object;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    public static boolean isFileExists(String path) {
        final File file = new File(path);
        return file.exists();
    }

    public static File getFileDir(String dir) {
        File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir;
    }
    public static boolean createNewFile(String fileName){
        try {
            boolean isSuccess = false;
            File file = new File(fileName);
            file.setWritable(true);
            file.setReadable(true);
            isSuccess = file.createNewFile();
            return isSuccess;
        }catch (Exception e){

        }
        return false;
    }
    public static void deleteWholeDirectory(File dir) throws IOException {

        if (!dir.exists() || !dir.isDirectory()) {
            throw new IOException("Directory to be deleted not exist, directory path : " + dir.getAbsolutePath());
        }

        File[] children = dir.listFiles();
        for (File child : children) {
            if (child.isDirectory()) {
                deleteWholeDirectory(child);
            } else {
                child.delete();
            }
        }

        dir.delete();
    }

    public static void deleteFile(File file) throws IOException {
        if (file.exists())
            file.delete();
    }

    public static void copyFile(File sourceFile, File targetFile) {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } catch (Exception e) {

        } finally {
            // 关闭流
            try {
                if (inBuff != null)
                    inBuff.close();
                if (outBuff != null)
                    outBuff.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static File getDir(String dir) {
        File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir;
    }

    public static void unzip(String zipPath, String descDir) throws IOException {
        File zipFile = new File(zipPath);
        File pathFile = new File(descDir + "/" + zipFile.getName().replace(".zip", ""));
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zip = new ZipFile(zipFile);
        Enumeration entries = zip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            String outPath = (pathFile + "/" + zipEntryName).replaceAll("\\*", "/");
            ;
            //判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if (!file.exists()) {
                file.mkdirs();
            }
            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if (new File(outPath).isDirectory()) {
                continue;
            }
            //输出文件路径信息
            System.out.println(outPath);
            OutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024 * 10];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }
            in.close();
            out.close();
        }
    }
    public static boolean writeFile(byte[] content,String filePath){
        return writeFile(content,filePath,false);
    }
    public static boolean writeFile(String content,String filePath){
        return writeFile(content,filePath,false);
    }
    public static boolean writeFile(String content,String filePath,boolean isAppend){
        return writeFile(content.getBytes(),filePath,isAppend);
    }
    public static boolean writeFile(byte[] content,String filePath,boolean isAppend){
        try {
            FileOutputStream output = new FileOutputStream(new File(filePath),isAppend);
            BufferedOutputStream bufferOutput = new BufferedOutputStream(output);
            bufferOutput.write(content);
            bufferOutput.flush();
            bufferOutput.close();
            output.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static byte[] readBytesFromInputStream(String filePath){
        try {
            return readBytesFromInputStream(new FileInputStream(filePath));
        }catch (Exception e){
        }
        return null;
    }
    public static String readFromFile(String filePath) {
        try {
            return readInputStream(new FileInputStream(filePath));
        }catch (Exception e){
        }
        return null;
    }
    public static String readInputStream(InputStream inputStream) {
        try {
            return new String(readBytesFromInputStream(inputStream));
        }catch (Exception e){

        }
        return null;
    }
    public static byte[] readBytesFromInputStream(InputStream inputStream) {
        BufferedInputStream inBuff = null;
        ByteArrayOutputStream outBuff = new ByteArrayOutputStream();
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(inputStream);
            // 新建文件输出流并对它进行缓冲
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
            return outBuff.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            // 关闭流
            try {
                if (inBuff != null)
                    inBuff.close();
                if (outBuff != null)
                    outBuff.close();
            }catch(Exception e2){
                e2.printStackTrace();
            }
        }
        return null;
    }
}
