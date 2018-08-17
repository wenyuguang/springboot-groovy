package springboot.designpattern.bridging;


/**
 * 目的地B2
 */
public class AreaB2 implements BridgeService {

    @Override
    public void targetAreaB() {
        System.out.println("我要去B2");
    }

}
