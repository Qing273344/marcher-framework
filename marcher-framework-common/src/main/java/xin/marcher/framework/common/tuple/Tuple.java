package xin.marcher.framework.common.tuple;

public class Tuple {

    private Tuple() {
    }

    public static <T1> Tuple1<T1> create(T1 item1) {
        return new Tuple1<>(item1);
    }

    public static <T1, T2> Tuple2<T1, T2> create(T1 item1, T2 item2) {
        return new Tuple2<>(item1, item2);
    }

    public static <T1, T2, T3> Tuple3<T1, T2, T3> create(T1 item1, T2 item2, T3 item3) {
        return new Tuple3<>(item1, item2, item3);
    }

    public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> create(T1 item1, T2 item2, T3 item3, T4 item4) {
        return new Tuple4<>(item1, item2, item3, item4);
    }

    public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> create(T1 item1, T2 item2, T3 item3, T4 item4, T5 item5) {
        return new Tuple5<>(item1, item2, item3, item4, item5);
    }
}
