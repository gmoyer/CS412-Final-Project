import java.io.Serializable;
import java.util.ArrayList;

public class Dataflow implements Serializable {
    private Instruct instruct;
    private ArrayList<Object> data;
    private ReqResult result;
    private static final long serialVersionUID = -7470943069077697341L;
    int index;

    public Dataflow(Instruct in) {
        instruct = in;
        index = 0;
        result = ReqResult.DEFAULT;
        data = new ArrayList<Object>();
    }

    public Instruct getInstruct() {
        return instruct;
    }
    public void setInstruct(Instruct instruct) {
        this.instruct = instruct;
    }

    public ReqResult getResult() {
        return result;
    }
    public void setResult(ReqResult r) {
        result = r;
    }

    public void add(Object a) {
        data.add(a);
    }

    public Object getNext() {
        if (data.size() == 0)
            return null;
        if (index >= data.size())
            return null;
        return data.get(index++);
    }

    public Object getData(int i) {
        if (i >= data.size() || i < 0)
            return null;
        return data.get(i);
    }

}
