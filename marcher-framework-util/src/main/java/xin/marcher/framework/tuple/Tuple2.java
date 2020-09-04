package xin.marcher.framework.tuple;

/**
 * @author marcher
 */
public final class Tuple2<T1, T2> implements ITuple {

    private final T1 item1;
    private final T2 item2;

    public Tuple2(T1 item1, T2 item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    public T1 getItem1() {
        return this.item1;
    }

    public T2 getItem2() {
        return this.item2;
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public Object get(int index) {
        switch (index) {
            case 0:
                return this.item1;
            case 1:
                return this.item2;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{");
        sb.append("item1=").append(item1);
        sb.append(", item2=").append(item2);
        sb.append("}");
        return sb.toString();
    }
}
