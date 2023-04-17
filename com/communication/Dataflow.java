package com.communication;
import java.io.Serializable;
import java.util.ArrayList;

public class Dataflow implements Serializable {
    private Instruct instruct;
    private ArrayList<Object> data;
    int index;

    public Dataflow(Instruct in) {
        instruct = in;
        index = 0;
        data = new ArrayList<Object>();
    }

    public Instruct getInstruct() {
        return instruct;
    }
    public void setInstruct(Instruct instruct) {
        this.instruct = instruct;
    }

    public void add(Object a) {
        data.add(a);
    }

    public Object getNext() {
        if (data.size() == 0)
            return null;
        if (index == data.size())
            index = 0;
        return data.get(index++);
    }

    public Object getData(int i) {
        if (i >= data.size() || i < 0)
            return null;
        return data.get(i);
    }
}
