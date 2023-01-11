package algorithms.template.bits;

public class GospersHack {

        public static void main(String[] args) {
            // 6!/3!/3! =（6 * 5 * 4）/ (3 * 2 * 1) = 20
            GospersHack(3, 6);
        }

        // http://programmingforinsomniacs.blogspot.com/2018/03/gospers-hack-explained.html
        static void GospersHack(int k, int n) {
            int set = (1 << k) - 1;
            int limit = (1 << n);
            while (set < limit) {
                System.out.println(String.format("%6s", Integer.toBinaryString(set)).replace(' ', '0'));

                // Gosper's hack:
                int c = set & - set;
                int r = set + c;
                set = (((r ^ set) >> 2) / c) | r;
            }
        }
    }

/*
int state = (1 << k) - 1;
while (state < (1 << m))
{
    doSomething(state);

    int c = state & - state;
    int r = state + c;
    state = (((r ^ state) >> 2) / c) | r;
}
*/
