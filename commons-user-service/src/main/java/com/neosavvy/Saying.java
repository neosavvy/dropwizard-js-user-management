package com.neosavvy;

/**
 * Created by IntelliJ IDEA.
 * User: waparrish
 * Date: 4/20/12
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class Saying {
    
    
    private final long id;
    private final String content;

    public Saying(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
