package springboot.designpattern.bridging;

public class BridgeTest {
    public static void main(String[] args) {
        AreaA a = new AreaA2();
        a.bridgeService = new AreaB3();
        a.fromAreaA();
        a.bridgeService.targetAreaB();
    }
}