package list;

/**
 * author: galaxy-violet
 * date: 2025/10/10,08:41
 */
/**
 * 在 Java 中，当一个对象的 实例字段（Instance Field）（即类中定义的变量，如 name、next）被创建时：
 * 如果它是引用类型（如 String、Node），并且您在构造函数中没有显式赋值，那么它会被自动赋予 默认值 null。
 * 如果它是基本类型（如 int、boolean），它也会被赋予默认值（如 int 默认是 0，boolean 默认是 false）。
 */
public class Node{
    public int id;
    public String name;
    public String nickname;
    public Node next;       // next 可以不用赋初值

    public Node(){
        this.next = null;   // 可以不用赋初值，引用类型默认为null
    }

    public Node(int id, String name, String nickname){
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.next = null;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
