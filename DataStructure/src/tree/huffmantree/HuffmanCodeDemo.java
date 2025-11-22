package tree.huffmantree;

import java.util.*;

/**
 * author: Galaxy Violet
 * date: 2025/10/25, 17:53
 */
public class HuffmanCodeDemo {
    public static void main(String[] args) {
        String content = "i like like like java do you like a java";
        System.out.println("string：" + content);
        System.out.println("string bytes(uft-8):" + Arrays.toString(content.getBytes()));
        System.out.println("length =" + content.getBytes().length);
        // 字符串的长度是40，按理来说应该占据80个字节(一个字符占据两个字节 - UTF-16编码)
        // 但是这里只有40个字节，问题是getBytes()这个函数,使用的编码不太一样，基本的ASCll字符只占据一个字节
        // 换一种输出方法
        System.out.println("string bytes(utf-16):" + Arrays.toString(content.getBytes(java.nio.charset.StandardCharsets.UTF_16)));
        System.out.println("length = " + content.getBytes(java.nio.charset.StandardCharsets.UTF_16).length);


        HuffmanCode huffmanCode = new HuffmanCode(content);

        System.out.println("preOrderTraversal of huffmanTree: ");
        huffmanCode.huffmanTree.preOrderTraversal();

        System.out.println("\nhuffmanCode Map:");
        System.out.println(huffmanCode.huffmanCodes);

        byte[] huffmanCodeBytes = huffmanCode.zip(content.getBytes());
        System.out.println("huffmanCodeBytes = " + Arrays.toString(huffmanCodeBytes));
        System.out.println("length = " + huffmanCodeBytes.length);

        byte[] sourceBytes = huffmanCode.decode(huffmanCodeBytes);
        System.out.println("source bytes: " + new String(sourceBytes));


    }
}



class HuffmanCode{
    HuffmanTree huffmanTree;
    public Map<Byte, String> huffmanCodes;
    public int totalBits;

    public HuffmanCode(){}

    public HuffmanCode(String content){
        // 获得byte数组
        byte[] bytes = getBytes(content);

        // 获得Node数组
        List<Node> nodes = getNodes(bytes);

        // 构建Huffman树
        huffmanTree = new HuffmanTree(nodes);

        // 获得对应的Huffman编码
        huffmanCodes = new HashMap<Byte, String>();
        getCodes(huffmanTree.root, "");
    }

    public HuffmanCode(byte[] bytes){
        // 获得Node数组
        List<Node> nodes = getNodes(bytes);

        // 构建Huffman树
        huffmanTree = new HuffmanTree(nodes);

        // 获得对应的Huffman编码
        huffmanCodes = new HashMap<Byte, String>();
        getCodes(huffmanTree.root, "");
    }

    // 获得字byte数组
    public  byte[] getBytes(String content){
//        char[] chars = new char[content.length()];
//        content.getChars(0,content.length(),chars,0)
//        or return content.toCharArray();
// 数据类型是Byte可以直接获得Byte[]
//byte[] bytes = content.getBytes();
        return content.getBytes();
    }

    // 将字byte符数组中的元素包装成Node.并计算其weight,最后返回Nodes数组
    public  List<Node> getNodes(byte[] bytes){

        ArrayList<Node> nodes = new ArrayList<>();

        // 使用map[key,value]来存储每个元素及其出现的次数
        // 泛型使用char,int报错：Type argument cannot be of a primitive type       primitive adj.原始的，远古的，落后的，简单的
        // HashMap只能存储对象类型（非原始类型，non-primitive types）作为键和值
        // 解决方式：使用相关原始类型（primitive type） 的包装类（wrapper class），分别是 Character和Integer
        HashMap<Byte, Integer> counts = new HashMap<>();

        for(byte data: bytes){
            Integer count = counts.get(data);  // 自动装箱
            if(count == null){
                counts.put(data, 1);
            }else{
                counts.put(data, count + 1);
            }
        }

        // 将键值对转换为Node对象，将加入nodes
        /*遍历map
        1、使用entrySet()，可以同时访问key和value，而且只需要遍历一次map     entry n.进入，加入，参加；门，入口；项目，条目，参赛作品
            ①Map接口内部定义了一个嵌套接口Entry，其作用是表示Map中的一个键值对（一个条目）
            Entry中含有两个重要的方法 K getKey() 和 V getValue()
            ②下面的代码是forEach，for(Type element: iterable){...}
        for(Map.Entry(K,V) entry: map.entrySet()){
            K key = entry.getKey();
            V value = entry.getValue();
            // ...使用key和value
        }

        2、使用keySet()遍历键
        for(K key: map.keySet()){
            V value = map.get(key); // 通过键获得值
            // ...可以使用key和value
        }

        3、使用java8的Lambda表达式
        map.forEach((key,value) -> {
           System.out.println("key = " + key + ", value = " + value)
        });

         */
        for(Map.Entry<Byte,Integer> entry : counts.entrySet()){
            nodes.add(new Node(entry.getKey(), entry.getValue()));
        }
        return nodes;
    }

    // 生成Huffman树对应的Huffman编码
    public  void getCodes(Node node,String path){
        if(node == null){
            return;
        }

        // 只要包装类Byte不为null 则说明node为叶子节点
        if(node.data != null){
            huffmanCodes.put(node.data, path);
        }else {
            // 向左递归
            getCodes(node.left, path + "0");

            // 向右递归
            getCodes(node.right, path + "1");
        }
    }
    public void getCodes(Node node, StringBuilder pathBuilder){
        if(node == null){
            return;
        }

        if(node.data != '\u0000'){
            huffmanCodes.put(node.data, pathBuilder.toString());
            return;
        }

        int currentLength =  pathBuilder.length();

        // 向左
        pathBuilder.append("0");
        getCodes(node.left, pathBuilder);
        pathBuilder.setLength(currentLength);   //回溯

        // 向右
        pathBuilder.append("1");
        getCodes(node.right,pathBuilder);
        pathBuilder.setLength(currentLength);   //同样需要回溯
    }
    /*
    有关 String 和 StringBuilder 的参数调用以及拼接问题

    1、String + String
    当你执行 str = str + "suffix" 时，Java 实际上做了三件事：
        (1)创建一个新的 StringBuilder (或内部优化)。
        (2)将原始字符串 str 的内容复制到新的 StringBuilder 中。
        (3)追加 "suffix"。
        (4)将新的 StringBuilder 转换回新的 String 对象。(toString())
    效率低，平方，不过String是不可变的，同时也是线程安全的。
    2、StringBuilder.append()
    builder是可变的，在内部修改自己的内容，不创建新的对象。
    效率高，线性，不过线程不安全

    参数传递的问题：
    String参数为（path + "0")每次递归调用时都会创建一个新的String对象，不需要手动回溯
    StringBuilder参数传递的时候传递的是引用，所以老韩会重新创建一个StringBuilder2,然后上面的做法是回溯，这些都是为了在递归时避免出现参数的“污染”
     */


    // 压缩
    /**
     *
     * @param bytes         原始字节数组
     * @return              压缩后的字节数组
     */
    public byte[] zip(byte[] bytes){
        // 1、利用huffmanCodes 将 bytes 转换成对应的 二进制字符串
        StringBuilder stringBuilder = new StringBuilder();

        // 遍历byte[],获得压缩后的字符串
        for(byte b: bytes){
            stringBuilder.append(huffmanCodes.get(b));
        }

//        System.out.println("after compress the binary string is :");
//        System.out.println(stringBuilder + "    length = " + stringBuilder.length());

        totalBits = stringBuilder.length();

        // 将压缩后的字符串转化成byte数组（8位一个字节）
        int length;     //压缩后的字节数组的长度
        if(totalBits % 8 == 0){
            length = totalBits / 8;
        }else {
            length = totalBits / 8 + 1;
        }

        byte[] huffmanCodeBytes = new byte[length];
        int index = 0;  // 字节数组的下标

        // 按字节处理压缩后的二进制字符串
        for (int i = 0; i < stringBuilder.length(); i += 8){
            String stringByte;

            if(i + 8 >= stringBuilder.length()){
                stringByte = stringBuilder.substring(i);   // 不写end会直接取到字符串末尾
            }else {
                stringByte = stringBuilder.substring(i, i + 8);
            }

            // huffmanCodeBytes[index++] = Byte.parseByte(stringByte,2);   // 注意这里radix需要填2
            // 不能使用Byte.parseByte(stringByte,2)进行转换，它会将二进制字符串转成一个无符号整数，但是byte是8为有符号整数，所以会报错：Value out of range. Value:"10101000" Radix:2
            // 正确的做法是先使用Integer.parseInt(stringByte,2)将其转换为int，这个是绝对不会溢出的，然后使用(byte)强制转换
            // byte()强转会自动截断掉int的高24位，只保留低位的8位"10101000",然后这个会被视为补码，确定其数值为-88
            huffmanCodeBytes[index++] = (byte)Integer.parseInt(stringByte,2);   // 这里不写radix = 2，这会将"10101000"当作一个十进制数来处理，然后强转的时候截断就会出现问题
            // 这里huffmanCodeBytes[]中存储的是-128~127的各个数字，每个数字占据一个字节,所以会出现一个问题最后一个字节"00001011" "01011"等都是11.所以需要一个总位数来确定最后一个字节究竟有多少个字节
        }
        return huffmanCodeBytes;
    }

    /**
     *
     * @param b     byte
     * @param isLastByte  表示是否需要补齐到8位
     * @return      对应的二进制字符串（补码）
     */
    public String byteToBitString(byte b, boolean isLastByte, int lastByteBits){
        int temp = b | 256; // 所有数都填充到9位，然后最后一个字节根据lastByteBits来确定要具体的位数，其余的字节保留8位

        // Integer.toBinaryString()方法会自动省略前导0（只有正数才有前导的零）
        // 返回的是temp对应的32位二进制补码（负数的补码会显示32位，但是正数的源码=补码，所以不会显示32位）
        String string = Integer.toBinaryString(temp);

        // 如果需要 8 位补齐，并且是前 n-1 个字节（flag=true），则截取后 8 位
        if(!isLastByte){
            return string.substring(string.length() - 8);   // 截取低位的8位
        }else {
            // 最后一个字节，返回完整的字符串
            // 这里有问题，如果说最后一个字节的二进制表示为"01011",然后转化为字节数组是11，然后再转化为字符串是"1011"
            // 所以应该lastByteBits来确定最后一个字节有多少位，来确定是否需要在前面补充前导的零
            return string.substring(string.length() - lastByteBits);
        }
    }

    public byte[] decode(byte[] huffmanBytes){
        // 1、得到huffmanBytes对应的二进制字符串
        StringBuilder stringBuilder = new StringBuilder();
        boolean isLastByte;   // 是否是最后一个字节
        int lastByteBits = totalBits % 8;   // 最后一个字节的总位数
        for(int i = 0; i < huffmanBytes.length; i++){
            isLastByte = i == huffmanBytes.length - 1;
            stringBuilder.append(byteToBitString(huffmanBytes[i], isLastByte,lastByteBits));
        }

        // 2、把字符串按照编码表进行解码
        // 翻转map 1000 -> a
        HashMap<String, Byte> map = new HashMap<>();
        for(Map.Entry<Byte, String>entry: huffmanCodes.entrySet()){     // 这里是huffmanCodes.entrySet()而不是huffmanCodes
            map.put(entry.getValue(), entry.getKey());
        }

        // 3、创建集合，存放byte
        ArrayList<Byte> list = new ArrayList<Byte>();

        int i = 0;  // 当前位置
        while (i < stringBuilder.length()) {
            int keyLength = 1;
            Byte b = null;

            // 从当前位置开始，逐步增加长度查找匹配的编码
            while (i + keyLength <= stringBuilder.length()) {  // ⭐ 加上边界检查
                String key = stringBuilder.substring(i, i + keyLength);
                b = map.get(key);

                if (b != null) {  // 找到匹配的编码
                    list.add(b);
                    i += keyLength;  // 移动到下一个位置
                    break;
                }
                keyLength++;  // 增加查找长度
            }

            // 如果没找到匹配（理论上不应该发生）
            if (b == null) {
                System.err.println("解码错误：在位置 " + i + " 找不到匹配的编码");
                break;
            }
        }

        // 把list中的数据放入contentByte
        byte[] contentBytes = new byte[list.size()];
        for (int j = 0; j < contentBytes.length; j++) {
            contentBytes[j] = list.get(j);
        }
        return contentBytes;
    }
}
