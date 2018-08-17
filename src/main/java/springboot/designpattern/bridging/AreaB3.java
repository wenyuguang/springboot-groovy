package springboot.designpattern.bridging;


/**
 * 目的地B3
 */
public class AreaB3 implements BridgeService {

    @Override
    public void targetAreaB() {
        System.out.println("我要去B3");
    }

}