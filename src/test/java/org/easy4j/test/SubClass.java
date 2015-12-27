package org.easy4j.test;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 15/12/22 16:32
 */
public class SubClass extends SuperClass {
    public void stop() {
        super.stop();
        System.out.println("subClass stop()");
    }

    public static void main(String[] args) {
        SuperClass sc = new SubClass();
        sc.stop("tag");
    }
}
