package com.fanc.wxx;

interface  Perpro{
    void peoLeList();
}
class  Student implements  Perpro{

    @Override
    public void peoLeList() {
        System.out.println("student");
    }
}
class  Terchar implements Perpro{

    @Override
    public void peoLeList() {
        System.out.println("Tercher");
    }
}
public class MyClass {
    public static void main(String[] gar){
    Perpro perpro;
        perpro=new Student();
        perpro.peoLeList();
        perpro=new Terchar();
        perpro.peoLeList();

    }
}
