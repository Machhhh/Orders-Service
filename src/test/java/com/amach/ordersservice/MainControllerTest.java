package com.amach.ordersservice;


import com.amach.ordersservice.client.ClientFacade;
import com.amach.ordersservice.report.ReportFacade;
import com.amach.ordersservice.request.RequestDto;
import com.amach.ordersservice.request.RequestFacade;
import com.amach.ordersservice.utils.MultipartFileConverter;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = MainController.class)
@DirtiesContext
public class MainControllerTest {

    private RequestDto reqDto1;
    private RequestDto reqDto2;
    private List<RequestDto> allRequests;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    ReportFacade reportFacade;

    @MockBean
    RequestFacade requestFacade;

    @MockBean
    MultipartFileConverter mfc;

    @MockBean
    ClientFacade clientFacade;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysExpect(MockMvcResultMatchers.content()
                        .contentType("text/html;charset=UTF-8"))
                .defaultRequest(get("/").accept(MediaType.ALL))
                .alwaysExpect(status().isOk())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Before
    public void setUpReport() {
        reqDto1 = RequestDto.create()
                .clientId(1L)
                .clientName("Bolek")
                .requestId(1L)
                .name("Pizza")
                .uuid("569026f2-5531-4e97-92ae-68f4bd11esdr")
                .price(new BigDecimal(11.35).
                        round(new MathContext(
                                3, RoundingMode.DOWN)))
                .quantity(246)
                .build();
        reqDto2 = RequestDto.create()
                .clientId(2L)
                .clientName("Lolek")
                .requestId(2L)
                .name("Beer")
                .uuid("467g36f2-5531-4e97-92ae-68dghyrfeswa")
                .price(new BigDecimal(7.11).
                        round(new MathContext(
                                3, RoundingMode.DOWN)))
                .quantity(91)
                .build();
        allRequests = Arrays.asList(reqDto1, reqDto2);
    }

    @Test
    public void testIndex() throws Exception {
        assertThat(this.reportFacade).isNotNull();
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("index"))
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(content().string(Matchers.containsString("Orders Service")))
                .andDo(print());
    }

    @Test
    public void testRequests() throws Exception {
        assertThat(this.reportFacade).isNotNull();
        when(reportFacade.getAllRequests()).thenReturn(allRequests);

        MvcResult result = mockMvc.perform(get("/requests"))
                .andExpect(status().isOk())
                .andExpect(view().name("requests"))
                .andExpect(content().string(Matchers
                        .containsString("Orders Service")))
                .andExpect(model().attribute("requests", hasSize(2)))
                .andExpect(model().attribute("requests", hasItem(
                        allOf(
                                hasProperty("requestId", is(1L)),
                                hasProperty("name", is("Pizza")),
                                hasProperty("quantity", is(246)),
                                hasProperty("price", is(reqDto1.getPrice())),
                                hasProperty("clientId", is(1L)),
                                hasProperty("clientName", is("Bolek"))
                        ))))
                .andExpect(model().attribute("requests", hasItem(
                        allOf(
                                hasProperty("requestId", is(2L)),
                                hasProperty("name", is("Beer")),
                                hasProperty("quantity", is(91)),
                                hasProperty("price", is(reqDto2.getPrice())),
                                hasProperty("clientId", is(2L)),
                                hasProperty("clientName", is("Lolek"))
                        ))))
                .andReturn();

        MockHttpServletResponse mockResponse = result.getResponse();
        assertThat(mockResponse.getContentType())
                .isEqualTo("text/html;charset=UTF-8");

        Collection<String> responseHeaders = mockResponse.getHeaderNames();
        assertNotNull(responseHeaders);
        assertEquals(1, responseHeaders.size());
        assertEquals("Check for Content-Type header", "Content-Type",
                responseHeaders.iterator().next());
        String responseAsString = mockResponse.getContentAsString();
        assertTrue(responseAsString.contains("Orders Service"));

        verify(reportFacade, times(1)).getAllRequests();
        verifyNoMoreInteractions(reportFacade);
    }

    @Test
    public void testRequestsByClientId() throws Exception {
        assertThat(this.reportFacade).isNotNull();
        reqDto2.setClientId(1L);
        List<RequestDto> requestsByClientId = Arrays.asList(reqDto1, reqDto2);
        BigDecimal avgPrice = new BigDecimal(9.23)
                .round(new MathContext(3, RoundingMode.DOWN));
        BigDecimal totalPrice = new BigDecimal(18.46)
                .round(new MathContext(3, RoundingMode.DOWN));

        when(reportFacade
                .getAllRequestByClientId(1L))
                .thenReturn(requestsByClientId);
        when(requestFacade
                .getRequestCountByClientId(1L))
                .thenReturn(2);
        when(requestFacade.getAverageValueOfClientRequests(1L))
                .thenReturn(avgPrice);
        when(requestFacade.getTotalPriceOfClientRequests(1L))
                .thenReturn(totalPrice);

        MvcResult result = mockMvc.perform(get("/requests/clients/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("clientRequests"))
                .andExpect(content().string(Matchers
                        .containsString("Orders Service")))
                .andExpect(model().attribute("requestsById", hasSize(2)))
                .andExpect(model().attribute("requestsById", hasItem(
                        allOf(
                                hasProperty("requestId", is(1L)),
                                hasProperty("name", is("Pizza")),
                                hasProperty("quantity", is(246)),
                                hasProperty("price", is(reqDto1.getPrice())),
                                hasProperty("clientId", is(1L)),
                                hasProperty("clientName", is("Bolek"))
                        ))))
                .andExpect(model().attribute("requestsById", hasItem(
                        allOf(
                                hasProperty("requestId", is(2L)),
                                hasProperty("name", is("Beer")),
                                hasProperty("quantity", is(91)),
                                hasProperty("price", is(reqDto2.getPrice())),
                                hasProperty("clientId", is(1L)),
                                hasProperty("clientName", is("Lolek"))
                        )
                )))
                .andExpect(model().attribute("requestsCountById", is(2)))
                .andExpect(model().attribute("totalPriceById", is(totalPrice)))
                .andExpect(model().attribute("avgValueById", is(avgPrice)))
                .andReturn();

        MockHttpServletResponse mockResponse = result.getResponse();
        assertThat(mockResponse.getContentType())
                .isEqualTo("text/html;charset=UTF-8");
        Collection<String> responseHeaders = mockResponse.getHeaderNames();
        assertNotNull(responseHeaders);
        assertEquals(1, responseHeaders.size());
        assertEquals("Check for Content-Type header", "Content-Type",
                responseHeaders.iterator().next());
        String responseAsString = mockResponse.getContentAsString();
        assertTrue(responseAsString.contains("Orders Service"));
    }
}
