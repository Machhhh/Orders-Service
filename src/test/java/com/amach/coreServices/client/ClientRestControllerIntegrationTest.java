package com.amach.coreServices.client;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientRestController.class)
@DirtiesContext
public class ClientRestControllerIntegrationTest {

    private ClientDto clientDto1;
    private ClientDto clientDto2;
    private ClientCreateDto clientCreateDto;
    private List<ClientDto> allClients;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientService clientServiceMock;

    @Before
    public void setUpClient() {
        clientDto1 = ClientDto.create()
                .clientId(1L)
                .name("Bolek")
                .uuid("534426f2-5531-4e97-92ae-68f4bd11ec2b")
                .build();
        clientDto2 = ClientDto.create()
                .clientId(2L)
                .name("Lolek")
                .uuid("d0b449a2-c79d-492a-be92-f929ad7b5a33")
                .build();
        allClients = Arrays.asList(clientDto1, clientDto2);
        clientCreateDto = ClientCreateDto.create()
                .name("SpongeBob")
                .build();
    }

    @Before
    public void setUp() {
        this.mvc = standaloneSetup(new ClientRestController(clientServiceMock))
                .alwaysExpect(MockMvcResultMatchers.content()
                        .contentType("application/json;charset=UTF-8"))
                .defaultRequest(get("/").accept(MediaType.APPLICATION_JSON))
                .alwaysExpect(status().isOk())
                .build();
        this.mvc = MockMvcBuilders.webAppContextSetup(webContext)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void givenClientsDto_whenGetClientsDto_thenReturnJsonArray()
            throws Exception {
        given(clientServiceMock.findAll()).willReturn(allClients);
        mvc.perform(get("/api/clients"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].clientId").isNotEmpty())
                .andExpect(jsonPath("$[0].clientId")
                        .value(clientDto1.getClientId()))
                .andExpect(jsonPath("$[0].name")
                        .value(clientDto1.getName()))
                .andExpect(jsonPath("$[1].name").isNotEmpty())
                .andExpect(jsonPath("$[1].clientId")
                        .value(clientDto2.getClientId()))
                .andExpect(jsonPath("$[1].requestDtoList").isEmpty())
                .andDo(print());
        verify(clientServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(clientServiceMock);
    }

    @Test
    public void givenClientDtoUuid_whenGetClientDtoByUuid_thenReturnFoundClientDtoEntry()
            throws Exception {
        assertThat(this.clientServiceMock).isNotNull();
        when(clientServiceMock.findOneByUuid("534426f2-5531-4e97-92ae-68f4bd11ec2b"))
                .thenReturn(clientDto1);
        MvcResult result = mvc.perform(get("/api/clients/{uuid}",
                "534426f2-5531-4e97-92ae-68f4bd11ec2b"))
                .andExpect(jsonPath("$.uuid")
                        .value("534426f2-5531-4e97-92ae-68f4bd11ec2b"))
                .andExpect(jsonPath("$.name")
                        .value("Bolek"))
                .andReturn();
        verify(clientServiceMock, times(1))
                .findOneByUuid("534426f2-5531-4e97-92ae-68f4bd11ec2b");
        verifyNoMoreInteractions(clientServiceMock);
        MockHttpServletResponse mockResponse = result.getResponse();
        assertThat(mockResponse.getContentType())
                .isEqualTo("application/json;charset=UTF-8");
    }

    @Test
    public void deleteClientDtoTest() throws Exception {
        String reqUuid = "534426f2-5531-4e97-92ae-68f4bd11ec2b";
        mvc.perform(delete("/api/clients/{uuid}", reqUuid))
                .andExpect(status().isOk());
        verify(clientServiceMock, times(1))
                .removeByUuid(reqUuid);
        verifyNoMoreInteractions(clientServiceMock);
    }

    @Test
    public void givenClientDto_whenCreateClientDto_thenReturnJsonArray()
            throws Exception {
        assertThat(this.clientServiceMock).isNotNull();
        when(clientServiceMock.create(any(ClientCreateDto.class)))
                .thenReturn(clientCreateDto);
        mvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clientCreateDto)))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.name")
                        .value("SpongeBob"))
                .andReturn();
        verify(clientServiceMock, times(1))
                .create(any(ClientCreateDto.class));
        verifyNoMoreInteractions(clientServiceMock);
    }
}
