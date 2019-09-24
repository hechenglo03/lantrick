package com.hcl.lantrick.server;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * hechenglong
 * 2019/9/24
 */
public class Test {
    public static void main(String[] args) throws URISyntaxException {
        URI uri = new URI("https://www.baidu.com/one/two");
        System.out.println(uri.getPath());
    }
}
