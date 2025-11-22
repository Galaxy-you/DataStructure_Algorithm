package recursion;

/**
 * author: galaxy-violet
 * date: 2025/10/14,16:48
 */

//maze n.迷宫，错综复杂，综合交错    v.使困惑

public class Maze {
    public static void main(String[] args) {
        // 使用二维数组模拟迷宫
        // 二维数组属于引用类型，所以会被默认初始化，int-0
        int[][] map = new int[8][7];

        // 初始化迷宫    1-表示墙（障碍） 0-表示可以通过
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 7; j++){
                if(i == 0 || i == 7 || j == 0 || j == 6){
                    map[i][j] = 1;
                }
            }
        }
        // 设置障碍
        map[3][1] = 1;
        map[3][2] = 1;

        printMap(map);

        // 调用递归探路函数
        if(setWay(map,1,1)){
            System.out.println("The route is:");
            printMap(map);
        }else {
            System.out.println("There is not route to the destination!");
        }


    }

    public static void printMap(int[][] map){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 7; j++){
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     *
     * @param map   二维数组地图
     * @param i     出发位置
     * @param j     出发位置
     * @return
     */
    // 使用递归回溯解决迷宫问题
    // 递归结束机制 map[6][5] == 2
    // map[i][j] == 0 => 该点没有走过
    // map[i][j] == 1 => 障碍，无法通过
    // map[i][j] == 2 => 该位置已经走过，并且可以走通
    // map[i][j] == 3 => 该位置已经走过，但是走不通
    // 探路策略：下->右->上->左，如果走不通，则回溯

    public static boolean setWay(int[][] map, int i, int j){
        // 递归出口
        if(map[6][5] == 2){
            return true;
        }

        // 根据当前map[i][j]分情况讨论
        if(map[i][j] == 0){
            map[i][j] = 2;  // 假定可以走通

            // 探路
            if(setWay(map,i + 1, j)){
                return true;
            }else if(setWay(map, i, j + 1)){
                return true;
            }else if(setWay(map,i - 1, j)){
                return true;
            }else if(setWay(map, i , j -1)){
                return true;
            }else{
                // 运行到这里说明该点不能到达终点，赋值为3
                map[i][j] = 3;
                return false;
            }
        }else {
            return false;
        }
    }
}

