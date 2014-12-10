package huffman;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by GODLIKE on 03.12.2014.
 */
public class MyHuffman implements ICoder {

    private int alphabetSize = 256;
    private int shift = 128;
    /*
    private String[] testcode;
    private byte[] testbytes;
    private String teststring;
    */

    @Override
    public byte[] encode(MyFile file){

        int[] weights = new int[alphabetSize];

        for (int i = 0; i < alphabetSize; i++) {
            weights[i] = 0;
        }

        //подсчет частоты вхождений
        for (int i = 0; i < file.bytes.length; i++) {
            weights[file.bytes[i] + shift]++;
        }

        //делаем кучу на минимум
        Comparator comparator = new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if (o1.weight < o2.weight) {
                    return -1;
                }

                if (o1.weight > o2.weight) {
                    return 1;
                }
                return 0;
            }
        };

        PriorityQueue<Node> q = new PriorityQueue<Node>(256, comparator);

        for (int i = 0; i < alphabetSize; i++) {
            if (weights[i] > 0) {
                q.add(new Node(i - shift, weights[i]));
            }
        }
        while (q.size() > 1) {
            Node n1 = q.poll();
            Node n2 = q.poll();
            int w = n1.weight + n2.weight;
            Node n = new Node(128, w);
            n.left = n1;
            n.right = n2;
            q.add(n);
        }
        Node n = q.poll();

        //считаем коды символов
        String[] codes = new String[alphabetSize];
        for (int i = 0; i < alphabetSize; i++) {
            codes[i] = "";
        }

        getCodes(n, codes, new StringBuilder(""));

        //собираем все вместе.
        StringBuilder st = new StringBuilder();


        //Занесем в 1-256 биты информацию о реальных длинах кодовых слов

        for(int i=0;i<alphabetSize;i++){
            String s;
            if (codes[i].length()==256) {
                s = "00000000";
            } else {
                s = Integer.toBinaryString(codes[i].length());
                while (s.length() < 8) {
                    s = '0' + s;
                }
            }
            st.append(s);
        }

        //Потом - информацию о том, какие буквы были
        for (int i = 0; i < alphabetSize; i++) {
            if (codes[i].length() > 0) {
                st.append("00000001");
            } else {
                st.append("00000000");
            }
        }

        //После - информация о кодах символов алфавита

        for (int i = 0; i < alphabetSize; i++) {
            String s = codes[i];
            while (s.length() < 256){
                s = '0' + s;
            }
            st.append(s);
        }

        //Теперь непосредственно сам файл.

        for (int i = 0; i < file.bytes.length; i++) {
            st.append(codes[file.bytes[i] + shift]);
        }

        int size = st.length() / 8 + 1;

        if (st.length() % 8 != 0) {
            size++;
        }

        byte[] bytes = new byte[size];

        bytes[0] = (byte) (st.length() % 8);


        //Собираем полученный код в байты
        int counter = 1;
        for (int i = 0; i < st.length(); i += 8) {
            if (i + 7 < st.length()) {

                String s = st.substring(i, i + 8);  //не включительно!
                bytes[counter] = (byte) (Integer.parseInt(s, 2) - shift);
                counter++;

            } else {

                String s = "";
                int k = 0;
                while (i + 7 >= st.length() + k) {
                    k++;
                    s = "0" + s;
                }
                s = s + st.substring(i);
                // System.out.println(s);
                bytes[counter] = (byte) (Integer.parseInt(s, 2) - shift);
                counter++;

            }
        }

        if ((counter != size)) throw new AssertionError("size error");

       /*
        //test
        testcode = codes;
        testbytes = bytes;
        teststring = st.substring(alphabetSize * 16 + 256 * 256);
                                */
        return bytes;
    }

    private void getCodes(Node cur, String[] codes, StringBuilder st) {
        if (cur.symbol < 128) {
            codes[cur.symbol + shift] = st.toString();
        } else {
            if (cur != null) {
                st.append('0');
                getCodes(cur.left, codes, st);
                st.deleteCharAt(st.length() - 1);
                st.append('1');
                getCodes(cur.right, codes, st);
                st.deleteCharAt(st.length() - 1);
            }
        }
    }

    @Override
    public byte[] decode(MyFile file) {

        /*Структура заархивированного файла:
         Байт №0: количество реальных битов в последнем байте закодированного файла (число от 0 до 7. причем 0 соответствует 8-ми реальным битам)
         Байт №1--256: количество реальных битов в коде k-го символа алфавита (число от -128 до 127 (нужно брать +shift=128) причем 0=-128+shift соответствует 256-ти реальным битам)
         Байт №257-512: информация о том, входил ли k-ый символ алфавита в сообщение(1 - входил, 0 - нет)
         Байт №513--513+32*256-1: информация о кодовых словах для k-го символа алфавита
         Так как кодовое слово может быть длиной до 256-ти символов, то на каждый символ выделено 32 байта.

                                                           */

        //Восстанавливаем таблицу кодов из начала файла

        String[] codes = new String[alphabetSize];

        boolean[] isAppeared = new boolean[alphabetSize];
        int[] realLength = new int[alphabetSize];

        for (int i = 0; i < alphabetSize; i++) {
            if (file.bytes[i + alphabetSize + 1] == -128) {
                isAppeared[i] = false;
            } else {
                isAppeared[i] = true;
            }
        }

        //Узнаем реальную длину кодовых слов
        for(int i=0;i<alphabetSize;i++){
            if (isAppeared[i]){
                int len = file.bytes[i+1] + shift;
                if (len==0){
                    realLength[i] = 256;
                }
                else{
                    realLength[i] = len;
                }
            }
        }

        for (int i = 0; i < alphabetSize; i++) {
            if (isAppeared[i]) {
                StringBuilder st = new StringBuilder();

                //Обрабатываем группы по 32 байта(256 бит) для каждого символа
                for (int j = 32 * i + alphabetSize + alphabetSize + 1; j <= 32 * (i + 1) + alphabetSize + alphabetSize - 1 + 1; j++) {
                    String s = Integer.toBinaryString(file.bytes[j] + 128);
                    while (s.length() < 8) {
                        s = "0" + s;
                    }
                    st.append(s);
                }
                int k = 0;
            /*    while (k < st.length() - 1 && st.charAt(k) == '0') {
                    k++;
                }
                codes[i] = st.substring(k);       */
                codes[i] = st.substring(st.length()-realLength[i]);
            } else {
                codes[i] = "";
            }
        }

       /*
        //test check
        for (int i = 0; i < alphabetSize; i++) {
            if ((testcode[i].compareTo(codes[i]) != 0)) throw new AssertionError(testcode[i] + " != " + codes[i]);
        }    */

        //Восстанавливаем закодированную строку
        StringBuilder st = new StringBuilder();

        for (int i = 32 * 256 + alphabetSize +alphabetSize + 1; i < file.bytes.length; i++) {
            String s = Integer.toBinaryString(file.bytes[i] + 128);
            if (i == file.bytes.length - 1 && file.bytes[0] > 0) {
                if (s.length() < file.bytes[0]) {
                    s = "0" + s;
                }
            } else {
                while (s.length() < 8) {
                    s = "0" + s;
                }
            }
            st.append(s);
        }

        String s = st.toString();

        //if ((s.compareTo(teststring) != 0)) throw new AssertionError();

        List<Byte> bytes = new ArrayList<Byte>();
        String temp = "";

        for (int i = 0; i < s.length(); i++) {
            temp = temp + s.charAt(i);
            int k = 0;
            while (k < alphabetSize) {
                if (codes[k].compareTo(temp) == 0 && isAppeared[k]) {
                    bytes.add((byte) (k - 128));
                    temp = "";
                    k = alphabetSize;
                }
                k++;
            }
        }

        if ((temp != "")) throw new AssertionError("temp string is not null");

        byte[] nbytes = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            nbytes[i] = bytes.get(i);
        }

        return nbytes;
    }

    private class Node {
        private Node left;
        private Node right;
        private int weight;
        private int symbol;  //на самом деле byte

        public Node(int Symbol, int Weight) {
            symbol = Symbol;
            weight = Weight;
        }
    }
}
