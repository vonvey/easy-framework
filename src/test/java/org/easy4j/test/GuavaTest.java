package org.easy4j.test;

import com.google.common.base.Optional;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 15/12/10 16:41
 */
public class GuavaTest {
    public static void main(String[] args) {
        Optional<Integer> optional = Optional.of(100);
        System.out.println(optional.get());
    }
}
