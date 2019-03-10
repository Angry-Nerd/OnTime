package com.akshit.ontime;

public class TodoItem {
    private String object,ref,mem;
    public TodoItem(String object,String ref,String member1)
    {
        this.object=object;
        this.ref=ref;
        this.mem=member1;
    }

    public String getObject() {
        return object;
    }

    public String getRef() {
        return ref;
    }

    public String getRef1() {
        return mem;
    }
}
