public class testA {
    private String time = "202404021500";
    public String date = "20240402";

    public testA(){

    }

    public void buildDate(){
        System.out.println(time);
        time = time + "123";
        System.out.println(time);

    }

    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
