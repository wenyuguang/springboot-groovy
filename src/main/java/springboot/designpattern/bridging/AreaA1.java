package springboot.designpattern.bridging;

/**
 * 来源地A1
 */
public class AreaA1 extends AreaA {

    @Override
    void fromAreaA() {
        System.out.println("我来自A1");
    }
    
}