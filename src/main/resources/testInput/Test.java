import test.test.Test;

public class Test extends Test2 implements IFace1, IFace2
{
    private String field1;
    private int field2 = 0;
    public void foo(Test param){
        int x = bar(1,2);
        System.out.println(x);
    }

    public int bar(int x, int y){
        return 0;
    }
}