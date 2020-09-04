package xin.marcher.framework.tuple;

/**
 * @author marcher
 */
public final class Tuple5<T1, T2, T3, T4, T5> implements ITuple {

    private final T1 item1;
    private final T2 item2;
    private final T3 item3;
    private final T4 item4;
    private final T5 item5;

    public Tuple5(T1 item1, T2 item2, T3 item3, T4 item4, T5 item5) {
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
        this.item5 = item5;
    }

    public T1 getItem1() {
        return this.item1;
    }

    public T2 getItem2() {
        return this.item2;
    }

    public T3 getItem3() {
        return this.item3;
    }

    public T4 getItem4() {
        return this.item4;
    }

    public T5 getItem5() {
        return this.item5;
    }

    @Override
    public int size() {
        return 5;
    }

    @Override
    public Object get(int index) {
        switch (index) {
            case 0:
                return this.item1;
            case 1:
                return this.item2;
            case 2:
                return this.item3;
            case 3:
                return this.item4;
            case 4:
                return this.item5;
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
        sb.append(", item3=").append(item3);
        sb.append(", item4=").append(item4);
        sb.append(", item5=").append(item5);
        sb.append("}");
        return sb.toString();
    }
}
