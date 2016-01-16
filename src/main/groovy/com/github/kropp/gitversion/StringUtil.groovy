package com.github.kropp.gitversion

/**
 * @author Victor Kropp
 */
class StringUtil {
    static String strip(String s, String what) {
        return s.startsWith(what) ? s.substring(what.length()) : s
    }
}
