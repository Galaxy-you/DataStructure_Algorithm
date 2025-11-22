package tree.huffmantree;

import java.io.*;
import java.util.Map;

/**
 * author: Galaxy Violet
 * date: 2025/10/26, 12:02
 */
public class CompressFile {
    public static void main(String[] args) {
//        String srcFile = "src/tree/huffman/violet.png";     // png 不太好压缩
//        String destFile = "src/tree/huffman/violet.dat";
//        zipFile(srcFile,destFile);

        String datFile = "src/tree/huffman/violet.dat";
        String destFile = "src/tree/huffman/violet(2).png";
        decompressFile(datFile, destFile);

    }


    public static void  zipFile(String srcFile, String destFile){
        try(FileInputStream fileInputStream = new FileInputStream(srcFile); // 用分号间隔
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){

            // available() 返回一个预估值，表示当前可以从输入流中读取或者跳过的字节数
            // 由于是读取本地文件，available()方法通常会返回文件中未读取的字节数，第一次调用即返回文件的大小。
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);    // 将文件数据读入到byte数组

            HuffmanCode huffmanCode = new HuffmanCode(bytes);
            byte[] huffmanBytes = huffmanCode.zip(bytes);

            // 以对像流的方式写入Huffman字节数组和编码，便于之后文件的恢复
            objectOutputStream.writeObject(huffmanBytes);
            objectOutputStream.writeObject(huffmanCode.huffmanCodes);

            // 写入总位数
            objectOutputStream.writeObject(huffmanCode.totalBits);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void decompressFile(String datFile, String destFile) {
        try (FileInputStream fileInputStream = new FileInputStream(datFile);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
             FileOutputStream fileOutputStream = new FileOutputStream(destFile)) {

            // 读取压缩数据
            byte[] bytes = (byte[]) objectInputStream.readObject();
            System.out.println("读取到的压缩数据长度: " + bytes.length);  // 调试信息

            // 读取编码表
            HuffmanCode huffmanCode = new HuffmanCode();
            huffmanCode.huffmanCodes = (Map<Byte, String>) objectInputStream.readObject();
            System.out.println("编码表大小: " + huffmanCode.huffmanCodes.size());  // 调试信息

            // 读取总位数
            huffmanCode.totalBits = (Integer)objectInputStream.readObject();

            // 解码
            byte[] srcBytes = huffmanCode.decode(bytes);
            System.out.println("解码后的数据长度: " + srcBytes.length);  // 调试信息

            // 写入文件
            fileOutputStream.write(srcBytes);
            System.out.println("文件写入成功!");

        } catch (Exception e) {
            System.out.println("错误信息: " + e.getMessage());
            e.printStackTrace();  // ⭐ 打印完整的堆栈跟踪
        }
    }
}
