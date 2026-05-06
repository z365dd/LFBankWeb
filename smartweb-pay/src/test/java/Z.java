public class Z extends X{
    P p = new P();
    public Z(){
        System.out.println("Z");
    }
    public static void main(String[] args) {
        new Z();
    }
}
class X{
    P p = new P();
    public X(){
        System.out.println("X");
    }
}
class P{
    public P(){
        System.out.println("P");
    }
}
