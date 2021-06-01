import org.apache.commons.lang3.text.StrSubstitutor;
import org.docx4j.XmlUtils;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tr;

import javax.xml.bind.JAXBException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TableTemplate implements CommonTrait
{

    private final Tbl table;
    private final String headerRowAsString;
    private final String dataRow;

    TableTemplate(Tbl table) throws Exception
    {
        this.table = table;
        List<Object> rows = getAllElementFromObject(this.table, Tr.class);
        headerRowAsString = XmlUtils.marshaltoString(prepareVariables(rows.get(0)));
        dataRow = XmlUtils.marshaltoString(prepareVariables(rows.get(rows.size() - 1)));
    }

    Tbl fillWithData(List<GasStation> gasStations, List<Driver> drivers) throws Exception
    {
        Tbl localTable = table;
        localTable.getContent().clear();

        localTable.getContent().add(getFilledHeaderRow(drivers));

        for (GasStation gs : gasStations)
        {
            localTable.getContent().add(getFilledDataRow(drivers, gs));
        }

        return localTable;
    }

    Tbl fillWithDataMy(List<String> data) throws Exception
    {
        Tbl localTable = table;
        localTable.getContent().clear();

        localTable.getContent().add(getFilledHeaderRowMy(data));

        //localTable.getContent().add(getFilledDataRowMy());

        //localTable.getContent().add(getFilledDataRowMyNew());

        return localTable;
    }

    private Object getFilledDataRow(List<Driver> drivers, GasStation gs) throws JAXBException
    {
        Map<String, String> mappings = new HashMap<>();
        mappings.put("st", gs.getName());
        for (int i = 1; i <= drivers.size(); i++)
        {
            mappings.put("ds" + i, drivers.get(i - 1).getGasStations().contains(gs) ? "YES" : "");
        }

        StrSubstitutor strSubstitutor = new StrSubstitutor(mappings);

        return XmlUtils.unmarshalString(strSubstitutor.replace(dataRow));
    }

    private Object getFilledDataRowMy() throws JAXBException
    {
        Map<String, String> mappings = new HashMap<>();
        mappings.put("1", "new");
        mappings.put("2", "new");
        mappings.put("3", "new");
        mappings.put("4", "new");
        mappings.put("5", "new");

        StrSubstitutor strSubstitutor = new StrSubstitutor(mappings);

        return XmlUtils.unmarshalString(strSubstitutor.replace(dataRow));
    }

    private Object getFilledDataRowMyNew() throws JAXBException
    {
        Map<String, String> mappings = new HashMap<>();
        mappings.put("1", "new");
        mappings.put("2", "new");
        mappings.put("3", "new");
        mappings.put("4", "new");
        mappings.put("5", "new");

        StrSubstitutor strSubstitutor = new StrSubstitutor(mappings);

        return XmlUtils.unmarshalString(strSubstitutor.replace(dataRow));
    }

    private Object getFilledHeaderRow(List<Driver> drivers) throws JAXBException
    {
        Map<String, String> mappings = new HashMap<>(drivers.size());
        for (int i = 1; i <= drivers.size(); i++)
        {
            mappings.put("d" + i, drivers.get(i - 1).getName());
        }
        StrSubstitutor strSubstitutor = new StrSubstitutor(mappings);
        return XmlUtils.unmarshalString(strSubstitutor.replace(headerRowAsString));
    }

    private Object getFilledHeaderRowMy(List<String> data) throws JAXBException
    {
        Map<String, String> mappings = new HashMap<>(5);
        mappings.put("nb", data.get(0));
        mappings.put("type", data.get(1));
        mappings.put("value", data.get(2));
        mappings.put("secret", data.get(3));
        mappings.put("nb_doc", data.get(4));
        StrSubstitutor strSubstitutor = new StrSubstitutor(mappings);
        return XmlUtils.unmarshalString(strSubstitutor.replace(headerRowAsString));
    }
}
