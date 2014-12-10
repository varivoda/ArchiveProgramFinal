package lzw;

import lzw.util.NotFullTableException;

import java.util.List;

public interface ArchiveByLZW {

    public List<String> archive(List<String> inputList);

    public List<String> dearchive(List<String> inputList) throws NotFullTableException;

}

