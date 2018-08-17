package springboot.designpattern.bridging;

public abstract class AreaA {
    //引用桥接口
	BridgeService bridgeService;
    //来源地
	abstract void fromAreaA();
}