import org.apache.commons.lang3.text.StrSubstitutor;
import org.docx4j.XmlUtils;
import org.docx4j.wml.*;

import javax.swing.text.ParagraphView;
import javax.xml.bind.JAXBException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TextTemplate implements CommonTrait
{
    private final String textAsString;
    private final P p;
    private final Object text;

    TextTemplate(P p) throws Exception
    {
        this.p = p;
        String PAsString = XmlUtils.marshaltoString(prepareVariables(p));
        System.out.println(PAsString);
        text = getAllElementFromObject(this.p, Text.class).get(0);
        textAsString = XmlUtils.marshaltoString(prepareVariables(text));
        System.out.println(textAsString);
    }

    P fillWithDataMy(String str) throws Exception
    {
        //Object localText = text.get(0);
        //localText..replace("numb", str);
        org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();
        P localP = p;
        R run = wmlObjectFactory.createR();
        localP.getContent().clear();
        run.getContent().add(getFilledHeaderRowMy(str));
        localP.getContent().add(run);
        //Text e = localText.getValue().replace();
        //String newText = localText.getValue().replace("${numb}", str);
        //localText.setValue(newText);
        //System.out.println(e);
        //Object f = getFilledHeaderRowMy();
        //System.out.println(f);
        //localText.;getContent().add(getFilledHeaderRowMy());

        //localTable.getContent().add(getFilledDataRowMyNew());

        return localP;
    }

    private Object getFilledHeaderRowMy(String str) throws JAXBException
    {
        Map<String, String> mappings = new HashMap<>(1);
        mappings.put("numb", str);
        StrSubstitutor strSubstitutor = new StrSubstitutor(mappings);
        return XmlUtils.unmarshalString(strSubstitutor.replace(textAsString));
    }
}

