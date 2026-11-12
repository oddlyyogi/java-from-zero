package shelf;

public interface Renewable {
    int maxRenewals();

    default String dueLabel() {
        return "renewable item";
    }
}
