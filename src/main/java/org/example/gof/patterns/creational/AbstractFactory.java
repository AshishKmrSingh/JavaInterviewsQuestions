package org.example.gof.patterns.creational;


interface ComputerAbstractFactory {
    TestComputer createComputer();
}


class PCFactory implements ComputerAbstractFactory {

    private String ram;
    private String hdd;
    private String cpu;

    public PCFactory(String ram, String hdd, String cpu){
        this.ram=ram;
        this.hdd=hdd;
        this.cpu=cpu;
    }
    @Override
    public TestComputer createComputer() {
        return new PC(ram,hdd,cpu);
    }

}

class ServerFactory implements ComputerAbstractFactory {

    private String ram;
    private String hdd;
    private String cpu;

    public ServerFactory(String ram, String hdd, String cpu){
        this.ram=ram;
        this.hdd=hdd;
        this.cpu=cpu;
    }

    @Override
    public TestComputer createComputer() {
        return new Server(ram,hdd,cpu);
    }

}

class ComputerFactory {

    public static TestComputer getComputer(ComputerAbstractFactory factory){
        return factory.createComputer();
    }
}

public class AbstractFactory {

    public static void main(String[] args) {
        testAbstractFactory();
    }

    private static void testAbstractFactory() {
        TestComputer pc = ComputerFactory.getComputer(new PCFactory("2 GB","500 GB","2.4 GHz"));
        TestComputer server = ComputerFactory.getComputer(new ServerFactory("16 GB","1 TB","2.9 GHz"));
        System.out.println("AbstractFactory PC Config::" + pc);
        System.out.println("AbstractFactory Server Config::" + server);
    }

}
