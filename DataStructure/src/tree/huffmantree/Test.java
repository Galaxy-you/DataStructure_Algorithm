package tree.huffmantree;

/**
 * author: Galaxy Violet
 * date: 2025/10/25, 21:45
 */
//public class Test {
//    public static void main(String[] args) {
//        String strByte = "10101000";
//        System.out.println((byte)Integer.parseInt(strByte,2));
//    }
//}

public class Test {
    public static void main(String[] args) {
        // 方法1：通过 Character.SIZE
        System.out.println("char 占用字节数: " + (Character.SIZE / 8));

        // 方法2：通过 ByteBuffer 检查
        char c = 'a';
        byte[] bytes = java.nio.ByteBuffer.allocate(2).putChar(c).array();
        System.out.println("存储 'a' 的字节长度: " + bytes.length);
    }
}

