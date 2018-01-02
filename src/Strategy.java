public interface Strategy {

    default void apply(Process P[], int H[], int nproc, int nholes, int flag[]) {
        System.out.println("ERROR! NO ALLOCATION METHOD! ");
    }
}
