package com.amach.coreServices.request;

import com.amach.coreServices.utils.MultipartFileConverter;
import com.amach.coreServices.utils.csvParser.CsvFacade;
import com.amach.coreServices.utils.xmlParser.XmlFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@WebMvcTest(RequestRestController.class)
@DirtiesContext
public class RequestRestControllerIntegrationTest {

    private RequestDto requestDto;
    private RequestCreateDto requestCreateDto;
    private MockMultipartFile xmlFile;
    private MockMultipartFile csvFile;
    private List<RequestDto> allRequests;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RequestService requestServiceMock;

    @MockBean
    private CsvFacade csvFacadeMock;

    @MockBean
    private XmlFacade xmlFacadeMock;

    @MockBean
    private MultipartFileConverter mfcMock;

    @Before
    public void setUpRequest() {
        requestDto = RequestDto.create()
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
        RequestDto requestDto1 = RequestDto.create()
                .clientId(1L)
                .clientName("Lolek")
                .requestId(2L)
                .name("Beer")
                .uuid("467g36f2-5531-4e97-92ae-68dghyrfeswa")
                .price(new BigDecimal(7.11).
                        round(new MathContext(
                                3, RoundingMode.DOWN)))
                .quantity(91)
                .build();
        allRequests = Arrays.asList(requestDto, requestDto1);
        requestCreateDto = RequestCreateDto.create()
                .clientId(2L)
                .name("Tortilla")
                .quantity(33)
                .price(new BigDecimal(4.52).
                        round(new MathContext(
                                3, RoundingMode.DOWN)))
                .build();
        xmlFile = new MockMultipartFile("data", "xmlFile.xml",
                "text/plain", "some xml".getBytes());
        csvFile = new MockMultipartFile("data", "csvFile.csv",
                "text/plain", "some csv".getBytes());
    }

    @Before
    public void setUp() {
        this.mvc = standaloneSetup(new RequestRestController(requestServiceMock,
                csvFacadeMock, xmlFacadeMock, mfcMock))
                .alwaysExpect(MockMvcResultMatchers.content()
                        .contentType("application/json;charset=UTF-8"))
                .defaultRequest(get("/").accept(MediaType.APPLICATION_JSON))
                .alwaysExpect(status().isOk())
                .alwaysExpect(content()
                        .contentType("application/json;charset=UTF-8"))
                .build();
        this.mvc = MockMvcBuilders.webAppContextSetup(webContext)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void givenRequests_whenGetRequests_thenReturnJsonArray()
            throws Exception {
        given(requestServiceMock.findAll(0, 2))
                .willReturn(allRequests);
        assertThat(this.requestServiceMock).isNotNull();
        when(requestServiceMock.findAll(0, 2))
                .thenReturn(allRequests);
        mvc.perform(get("/api/requests")
                .requestAttr("from", 0)
                .requestAttr("to", 2))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[3]").doesNotExist())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name")
                        .value("Pizza"))
                .andExpect(jsonPath("$[1].name")
                        .value("Beer"))
                .andExpect(jsonPath("$[1].price")
                        .isNotEmpty())
                .andDo(print());
        verify(requestServiceMock, times(1))
                .findAll(0, 2);
        verifyNoMoreInteractions(requestServiceMock);
    }

    @Test
    public void giveneRequestCreateDto_whenPostRequestCreateDto_thenReturnRequestCreateDto()
            throws Exception {
        assertThat(this.requestServiceMock).isNotNull();
        when(requestServiceMock.create(any(RequestCreateDto.class)))
                .thenReturn(requestCreateDto);
        mvc.perform(post("/api/requests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestCreateDto)))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Tortilla"))
                .andExpect(jsonPath("$.quantity").value(33))
                .andExpect(jsonPath("$.price").value(04.51))
                .andExpect(jsonPath("$.clientId").isNotEmpty())
                .andReturn();
        verify(requestServiceMock, times(1))
                .create(any(RequestCreateDto.class));
        verifyNoMoreInteractions(requestServiceMock);
    }

    @Test
    public void givenRequestDtoUuid_whenGetReuestDtoByUuid_thenReturnFoundRequestDtoEntry()
            throws Exception {
        String reqUuid = "569026f2-5531-4e97-92ae-68f4bd11esdr";
        assertThat(this.requestServiceMock).isNotNull();
        when(requestServiceMock.findOneByUuid(reqUuid))
                .thenReturn(requestDto);
        MvcResult result = mvc.perform(get("/api/requests/{uuid}", reqUuid))
                .andExpect(jsonPath("$.uuid").value(reqUuid))
                .andExpect(jsonPath("$.name").value("Pizza"))
                .andReturn();
        verify(requestServiceMock, times(1)).findOneByUuid(reqUuid);
        verifyNoMoreInteractions(requestServiceMock);
        MockHttpServletResponse mockResponse = result.getResponse();
        assertThat(mockResponse.getContentType()).isEqualTo("application/json;charset=UTF-8");
    }

    @Test
    public void deleteRequestDtoTest() throws Exception {
        String reqUuid = "569026f2-5531-4e97-92ae-68f4bd11esdr";
        mvc.perform(delete("/api/requests/{uuid}", reqUuid))
                .andExpect(status().isOk());
        verify(requestServiceMock, times(1)).removeRequestByUuid(reqUuid);
        verifyNoMoreInteractions(requestServiceMock);
    }

    @Test
    public void uploadXmlFileTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(
                webContext).build();
        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/requests/upload-xml")
                .file(xmlFile))
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    public void uploadCsvFileTest() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/api/requests/upload-csv")
                .file(csvFile))
                .andExpect(status().is(200))
                .andDo(print());
    }
}
