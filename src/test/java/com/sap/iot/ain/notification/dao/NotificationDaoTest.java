package com.sap.iot.ain.notification.dao;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.sap.iot.ain.notification.entities.NotificationHeaderEntity;
import com.sap.iot.ain.notification.payload.NotificationPOST;
import com.sap.iot.ain.security.AINUserDetails;
import com.sap.iot.ain.security.AuthenticatedUserDetails;
import com.sap.iot.ain.util.model.DaoHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import  org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.Locale;


/**
 * @author i311136
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PowerMockIgnore("javax.management.*")
@ContextConfiguration("classpath*:testContext/persistenceConfig.xml")
@PrepareForTest({DaoHelper.class})
public class NotificationDaoTest {

    @InjectMocks
    @Spy
    private NotificationDao notificationDao = new NotificationDao();

    @Mock
    private AuthenticatedUserDetails aud;

    @Mock
    private AINUserDetails userDetails;

    private boolean mockInitialized = false;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static File NOTIFICATION_POST = null;

    private static File NOTIFICATION_HEADER_ENTITY = null;


    @BeforeClass
    public static void init() {
        NOTIFICATION_POST = new File("src/test/resources/json/NotificationPOST.json");
        NOTIFICATION_HEADER_ENTITY = new File("src/test/resources/json/NotificationHeaderEntity.json");
    }

    @Before
    public void setUp() throws Exception {
        if (!mockInitialized) {
            MockitoAnnotations.initMocks(this);
            mockInitialized = true;
        }
    }


    @Test
    public void notificationPayloadToEntityTest () throws IOException {
        objectMapper.registerModule(new JodaModule());
        NotificationPOST post = objectMapper.readValue(NOTIFICATION_POST, NotificationPOST.class);
        PowerMockito.mockStatic(DaoHelper.class);
        PowerMockito.when(DaoHelper.getUUID()).thenReturn("06a6c08e0320491d86ad4f1d4a2b636e");
        Mockito.doReturn("NT.SAPMAN.161").when(notificationDao).generateInternalID();
        Mockito.doReturn(userDetails).when(aud).getUserDetails();
        Locale currentLocale = Locale.getDefault();
        currentLocale.setDefault(new Locale("en", "GB"));
        Mockito.doReturn(currentLocale).when(userDetails).getLocale();
        NotificationHeaderEntity entity = notificationDao.notificationPayloadToEntity(post);
        String notificationEntity = objectMapper.writeValueAsString(entity);
        NotificationHeaderEntity notificationHeaderEntity = objectMapper.readValue(NOTIFICATION_HEADER_ENTITY,NotificationHeaderEntity.class);
        String expectedEntity = objectMapper.writeValueAsString(notificationHeaderEntity);
        Assert.assertEquals(expectedEntity, notificationEntity);
    }


}