package br.com.rebeca.ToDoList.Util;

import java.util.Optional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.context.annotation.Configuration;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Configuration
public class ConverterUtil {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0");
    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public  List<String> converterStrigEmLista(String tiposRetirada){
        List<String> lista = Stream.of(tiposRetirada.split(",", -1)).collect(Collectors.toList());

        return lista;
    }

    public Long toLong(final Object object) {
        return Long.parseLong(
                Optional.ofNullable(object)
                        .map(Object::toString)
                        .orElse("0")
        );
    }

    public Integer toInt(final Object object) {
        return Integer.parseInt(
                Optional.ofNullable(object)
                        .map(Object::toString)
                        .orElse("0")
        );
    }


    public Integer toInteger(final Object object) {
        return Integer.parseInt(String.valueOf(object));
    }

    public Double toDouble(final Object object) {
        return Double.parseDouble(String.valueOf(object));
    }

    public Boolean toBoolean(final Object object) {
        return Boolean.parseBoolean(String.valueOf(object));
    }

    public LocalDateTime formatDate(String data) {
        return LocalDateTime.parse(data, formatter);
    }

    public String toString(final Object object) {
        return object != null ? object.toString() : "";
    }

    public String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public long converterCelulaParaLong(Cell cell) {
        if (cell == null) {
            return 0L;
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return (long) cell.getNumericCellValue();
        } else {
            throw new IllegalArgumentException("A célula não contém um valor numérico.");
        }
    }

    public int converterCelulaParaInt(Cell cell) {
        if (cell == null) {
            return 0;
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        } else {
            throw new IllegalArgumentException("A célula não contém um valor numérico.");
        }
    }

    public boolean converterCelulaParaBoolean(Cell cell) {
        if (cell == null) {
            return false;
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            return  cell.getBooleanCellValue();
        } else {
            return  Boolean.parseBoolean(cell.getStringCellValue());
        }
    }

    public Double converterCelulaParaDouble(Cell cell) {
        if (cell == null) {
            return 0D;
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else {
            throw new IllegalArgumentException("A célula não contém um valor numérico.");
        }
    }

    public static byte[] decodeArrayBytes(String encode){
        return Base64.getDecoder().decode(encode);
    }

    public LocalDateTime formatarData(String data) {
        return LocalDateTime.parse(data, formatterDate);
    }

    public String localDateTimeParaString(LocalDateTime data) {
        return formatterDate.format(data);
    }

    public LocalDateTime dataAjustadaMongo(LocalDateTime data) {
        return  data.plusHours(3);
    }
}