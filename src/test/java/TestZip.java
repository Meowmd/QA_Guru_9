import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class TestZip {

    ClassLoader classLoader = TestZip.class.getClassLoader();

    @DisplayName("csv")
    @Test
    void csvTest() throws Exception {

        ZipFile zipFile = new ZipFile(Objects.requireNonNull(classLoader.getResource("ZipFile.zip")).getFile());
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile.getName()));
        ZipEntry entry;

        while ((entry = zipIn.getNextEntry()) != null) {

            InputStream inputStream = zipFile.getInputStream(entry);
            if (entry.getName().contains("csv")) {
                CSVReader csvFileReader = new CSVReader(new InputStreamReader(inputStream, UTF_8));
                List<String[]> csvList = csvFileReader.readAll();
                assertThat(csvList).contains(new String[]{"Book;Author;Date"});
                assertThat(csvList).contains(new String[]{"The Body;King;1982"});
            }

            inputStream.close();
        }

        zipIn.close();
    }

    @DisplayName("pdf")
    @Test
    void pdfTest() throws Exception {

        ZipFile zipFile = new ZipFile(Objects.requireNonNull(classLoader.getResource("ZipFile.zip")).getFile());
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile.getName()));
        ZipEntry entry;

        while ((entry = zipIn.getNextEntry()) != null) {

            InputStream inputStream = zipFile.getInputStream(entry);
            if (entry.getName().contains("pdf")) {
                PDF pdfFile = new PDF(inputStream);
                assertThat(pdfFile.text).contains("A Simple PDF File");
            }

            inputStream.close();
        }

        zipIn.close();
    }

    @DisplayName("xls")
    @Test
    void checkXLSTest() throws Exception {

        ZipFile zipFile = new ZipFile(Objects.requireNonNull(classLoader.getResource("ZipFile.zip")).getFile());
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile.getName()));
        ZipEntry entry;

        while ((entry = zipIn.getNextEntry()) != null) {

            InputStream inputStream = zipFile.getInputStream(entry);
            if (entry.getName().contains("xls")) {
                XLS xls = new XLS(inputStream);
                assertThat(xls.excel.getSheetAt(0).getRow(8).getCell(3).getStringCellValue()).contains("Etta Hurn");
            }

            inputStream.close();
        }

        zipIn.close();
    }
}