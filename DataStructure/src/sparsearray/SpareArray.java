package sparsearray;

/**
 * author: galaxy-violet
 * date: 2025/9/23,21:52
 */
public class SpareArray {
    // 类常量，需要定义在main函数之外
    public static final int ROW = 11;

    public static void main(String[] args) {
        // 创建原始二维数组
        // 0 -> 没有棋子，1 -> 黑子， 2 -> 白子

        // final 位于方法内部为局部常量，位于类定义中为实例常量
        final int COLUMN = 11;

        // java 中数组的声明是 int[][] array_name
        // 而cpp中的声明是 int array_name[][]
        int[][] chessArr1 = new int[ROW][COLUMN];
        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;
        chessArr1[6][6] = 2;

        /** 增强for循环（for-each循环）
         * for(数据类型 变量名：要遍历的数组或者结合)
         * 1、数据类型必须与要遍历的数组或者集合中元素的类型兼容，注意是元素
         * 2、变量名：临时的局部变量，保存了当前迭代元素的副本，用于存储循环中每次迭代取出的元素
         * 3、遍历对象必须实现Iterable接口（例如ArrayList,Set,Array等）
         * 4、无法直接获取或操作元素的索引
         * 5、在循环内部无法修改数组或者集合的结构（比如添加或删除元素）
         * 6、基本数据类型的值无法修改元素的值，但是引用数据类型可以修改对象内部的属性（副本指向了实际的存储空间），但是改变指向是无效的
         */
        System.out.println("original array:");
        for (int[] row : chessArr1) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        // 将二维数组转化为sparse array
        // 1、遍历二维数组，得到有效数据的个数
        int sum = 0;
        for(int[] row : chessArr1){
            for(int data : row){
                if (data != 0) {
                    sum++;
                }
            }
        }
        int sparseArray[][] = new int[sum + 1][3];

        // 2、给稀疏数组赋值
        sparseArray[0][0] = 11; // 赋值row
        sparseArray[0][1] = 11; // 赋值column
        sparseArray[0][2] = sum; // 赋值valid value

        // 遍历二维数组将有效值放入稀疏数组
        int count = 0;     // 稀疏数组的行数
        for(int i = 0; i < ROW; i++){
            for(int j = 0; j < COLUMN;j++){
                if(chessArr1[i][j] != 0){
                    count++;
                    sparseArray[count][0] = i;
                    sparseArray[count][1] = j;
                    sparseArray[count][2] = chessArr1[i][j];
                }
            }
        }

        System.out.println("sparse array:");
        for (int[] row : sparseArray) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        // 将稀疏数组恢复成原始数组
        int original_row = sparseArray[0][0];
        int original_column = sparseArray[0][1];
        int[][] chessArr2 = new int[original_row][original_column];

        // 下面的错误写法，不是从稀疏数组的第一行开始赋值，而是从第二行开始赋值，增强for循环无法实现
//        for (int[] row : sparseArray) {
//            chessArr2[row[0]][row[1]] = row[2];
//        }
        for (int i = 1; i < sparseArray.length; i++) {
            chessArr2[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }

        System.out.println("restored array");
        for (int[] row : chessArr2) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
    }
}
