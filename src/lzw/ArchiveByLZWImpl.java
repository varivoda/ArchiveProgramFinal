package lzw;

        import java.util.ArrayList;
        import java.util.LinkedList;
        import java.util.List;
        import java.util.StringTokenizer;

public class ArchiveByLZWImpl implements ArchiveByLZW {

    final private ArrayList<String> INIT_TABLE;
    final static private char FIRST_SYMBOL = 32;
    final static private char LAST_SYMBOL = 127;

    private ArrayList<String> initUNICODE() {
        ArrayList<String> Unicode = new ArrayList<>();
        for (char i = FIRST_SYMBOL; i <= LAST_SYMBOL; i++) {
            Unicode.add(String.valueOf(i));
        }
        return Unicode;
    }

    public ArchiveByLZWImpl() {
        INIT_TABLE = initUNICODE();
    }

    private String ReadStringAndEncode(final String inputString, final ArrayList<String> table) {
        String outputString = "";
        int stringLength = inputString.length();
        int i = 0;
        while (i < stringLength - 1 ) {
            String newSequence = String.valueOf(inputString.charAt(i));
            int positionOfNewSequence = table.indexOf(newSequence);
            if (positionOfNewSequence == -1){
                positionOfNewSequence = table.size();
            }
            int positionOfCurrentSequence = positionOfNewSequence;
            i++;
            newSequence += inputString.charAt(i);
            i++;
            while ((positionOfNewSequence = table.indexOf(newSequence)) > 0 && (i <= stringLength)) {
                if (i == stringLength){
                    positionOfCurrentSequence = positionOfNewSequence;
                    return outputString + String.valueOf(positionOfCurrentSequence);
                }
                newSequence += inputString.charAt(i);
                i++;
                positionOfCurrentSequence = positionOfNewSequence;
            }
            i--;
            table.add(newSequence);
            outputString += String.valueOf(positionOfCurrentSequence) + " ";
        }
        if (table.indexOf(String.valueOf(inputString.charAt(i))) > 0){
            return outputString + table.indexOf(String.valueOf(inputString.charAt(i)));
        } else {
            return outputString + table.size();
        }
    }

    private String ReadStringAndDecode(final String inputString,final ArrayList table) {
        String outputString = "";
        StringTokenizer tokenizer = new StringTokenizer(inputString);
        int code = Integer.parseInt(tokenizer.nextToken());
        outputString += table.get(code).toString();
        while (tokenizer.hasMoreTokens()) {
            String newSequence = table.get(code).toString(); //??
            if (tokenizer.hasMoreTokens()) {
                code = Integer.parseInt(tokenizer.nextToken());
                newSequence += table.get(code).toString();
                table.add(newSequence);
                outputString += table.get(code).toString();
            } else {
                outputString += table.get(code).toString();
            }
        }
        return outputString;
    }

    @Override
    public List<String> archive(final List<String> stringList) {
        List<String> encodedOutput = new LinkedList<>();
        ArrayList<String> table = new ArrayList<>();
        table.addAll(INIT_TABLE);
        for (String decodedString : stringList) {
            encodedOutput.add(ReadStringAndEncode(decodedString, table));
        }
        return encodedOutput;
    }

    @Override
    public List<String> dearchive(final List<String> encodedStringList) {
        List<String> decodedOutput = new LinkedList<>();
        ArrayList<String> table = new ArrayList<>();
        table.addAll(INIT_TABLE);
        for (String encodedString : encodedStringList) {
            decodedOutput.add(ReadStringAndDecode(encodedString, table));
        }
        return decodedOutput;
    }
}

