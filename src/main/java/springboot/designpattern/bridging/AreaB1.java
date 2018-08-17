package springboot.designpattern.bridging;

/**
 * 目的地B1
 */
public class AreaB1 implements BridgeService {

    @Override
    public void targetAreaB() {
        System.out.println("我要去B1");
    }

}
