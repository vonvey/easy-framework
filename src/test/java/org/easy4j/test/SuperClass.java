package org.easy4j.test;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 15/12/22 16:32
 */
public class SuperClass {
    public void stop() {
        System.out.println("superClass stop()");
    }

    public void stop(String tag) {
        System.out.println("superClass stop(tag)");
        stop();
    }

}
