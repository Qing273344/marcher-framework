package xin.marcher.framework.tuple;

/**
 * Tuple1
 *
 * @author marcher
 */
public final class Tuple1<T1> implements ITuple {

    private final T1 item1;

    public Tuple1(T1 item1) {
        this.item1 = item1;
    }

    public T1 getItem1() {
        return this.item1;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public Object get(int index) {
        if (index == 0) {
            return this.item1;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{");
        sb.append("item1=").append(item1);
        sb.append("}");
        return sb.toString();
    }
}
