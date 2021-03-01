package springbook.learningtest.oxm;

import com.epril.sqlmap.SqlType;
import com.epril.sqlmap.Sqlmap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.test.context.ContextConfiguration;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(locations = "/OxmTest-context.xml")
public class OxmTest {
    @Autowired
    Unmarshaller unmarshaller;

    @Test
    public void unmarshallSqlMap() throws XmlMappingException, IOException {
        Source xmlSource = new StreamSource(getClass().getResourceAsStream("/sqlmapForTest.xml"));
        Sqlmap sqlmap = (Sqlmap) this.unmarshaller.unmarshal(xmlSource);

        List<SqlType> sqlList = sqlmap.getSql();

        assertEquals(sqlList.size(), 3);
        assertEquals(sqlList.get(0).getKey(), "add");
        assertEquals(sqlList.get(0).getValue(), "insert");
        assertEquals(sqlList.get(1).getKey(), "get");
        assertEquals(sqlList.get(1).getValue(), "select");
        assertEquals(sqlList.get(2).getKey(), "delete");
        assertEquals(sqlList.get(2).getValue(), "delete");
    }
}
