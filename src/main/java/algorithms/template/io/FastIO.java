package algorithms.template.io;

import java.io.*;
import java.util.*;

public class FastIO {

        static void fast() throws Exception {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line = reader.readLine();
            String[] tokens = line.split(" ");
            int a = Integer.valueOf(tokens[0]);
            int b = Integer.valueOf(tokens[1]);
            System.out.println(a + b);
        }

        static void slow() {
            Scanner scan = new Scanner(System.in);
            int a = scan.nextInt();
            int b = scan.nextInt();
            System.out.println(a + b);
        }
        public static void main(String[] args) throws Exception {
            fast();
            slow();
        }
    }
