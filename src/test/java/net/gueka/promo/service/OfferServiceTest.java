package net.gueka.promo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.gueka.promo.schema.Data;
import net.gueka.promo.service.OfferService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferServiceTest {

    @Autowired
    OfferService service;

    @Test
    public void testNewUserPromotion() throws Exception {
        // Given
        Data data = generateDummyRequest(-1);
        
        // When
        List<String> offers = service.getOffers(data);
        
        // Then
        String message = String.format(OfferService.NEW_USER_DISCOUNT_MESSAGE, 153, "20%");
        assertTrue("Has to return at leas a message", offers.size() > 0);
        assertEquals("Has to return first message as " + message, message, offers.get(0));
        assertEquals("Has only 1 message", 1, offers.size());
    }

    @Test
    public void testNoPromotion() throws Exception {
        // Given
        Data data = generateDummyRequest(-7);
        
        // When
        List<String> offers = service.getOffers(data);
        
        // Then
        assertEquals("Shouldn't have any promotion message", 0, offers.size());
    }

    @Test
    public void testMemberNewYear() throws Exception {
        // Given
        Data data = generateDummyRequest(-12);
        
        // When
        List<String> offers = service.getOffers(data);
        
        // Then
        String message = OfferService.NEW_YEAR_MEMBER_DISCOUNT_MESSAGE;
        assertTrue("Has to return at leas a message", offers.size() > 0);
        assertEquals("Has to return first message as " + message, message, offers.get(0));
        assertEquals("Has only 1 message", 1, offers.size());
    }

    private Data generateDummyRequest(Integer months) throws DatatypeConfigurationException {
        GregorianCalendar gcal = new GregorianCalendar();
        gcal.add(Calendar.MONTH, months);
        XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
        Data data = new Data();
        data.setUserId("007");
        data.setInitDate(date);
        return data;
    }

}