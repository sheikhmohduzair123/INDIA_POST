package com.services;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.Application;
import com.constants.Status;
import com.domain.Address;
import com.domain.Client;
import com.domain.Control;
import com.domain.InvoiceBreakup;
import com.domain.Parcel;
import com.domain.SyncTable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repositories.ControlRepository;
import com.repositories.ParcelRepository;
import com.repositories.SyncTableRepository;
import com.services.impl.ParcelSyncServiceImpl;
import com.vo.ParcelVo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class ParcelSynServiceTest {

	protected Logger log = LoggerFactory.getLogger(ParcelSynServiceTest.class);

	@Mock
	ParcelSyncService parcelSyncService;

	@InjectMocks
	ParcelSyncServiceImpl parcelSyncServiceImpl;

	@Mock
	Client client;

	@Mock
	Control control;

	@Mock
	ControlRepository controlRepository;

	@Mock
	SyncTableRepository syncTableRepository;

	@Mock
	SyncTable syncTable;

	@Mock
	ParcelRepository parcelRepo;

	DateFormat dateFormat;
	java.sql.Timestamp timestamp;

	@Before
	public void init() {

		control = new Control();
		control.setId(1L);
		control.setStatus(Status.ACTIVE);
		control.setUpdatedOn(new Date());

		syncTable = new SyncTable();
		syncTable.setSyncId(1L);
		syncTable.setClientId("11001581656985");
		syncTable.setSyncType("master");
		syncTable.setNoOfRecords(6);
		syncTable.setNoOfRecordsUpdated(6);
		syncTable.setSynceStatus("pending");

		dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		timestamp = java.sql.Timestamp.valueOf("2020-02-25 10:10:10.0");
	}

	@Test
	public void getClientControlIfConditionTest() {

		List<Control> controlList = new ArrayList<Control>();
		controlList.add(control);

		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Assert.assertEquals(controlList.get(0), parcelSyncServiceImpl.getClientControl());
	}


	@Test
	public void getClientControlElseConditionTest() {

		List<Control> controlList = new ArrayList<Control>();

		Mockito.when(controlRepository.findAll()).thenReturn(controlList);
		Assert.assertNotEquals(null, parcelSyncServiceImpl.getClientControl());
	}


	@Test
	public void syncClientDataTest() throws IOException {

		Address addr = new Address();
		addr.setAddressLine1("saket");
		addr.setAddressLine2("new delhi");
		addr.setCountry("bangla");
		addr.setPostalCode(1100);

		InvoiceBreakup invBrkp = new InvoiceBreakup();
		invBrkp.setName("Guaranteed Express Post");
		invBrkp.setSubTotal(10);

		List<Long> service = new ArrayList<Long>();
		service.add(1L);

		ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
		List<ParcelVo> ListParcel = new ArrayList<ParcelVo>();
		Map<String, Object> map =new HashMap<String, Object>();

		ParcelVo p1 = new ParcelVo();
		p1.setSenderAddress(addr);
		p1.setSenderMobileNumber(96385274102L);
		p1.setSenderName("senderName");
		p1.setActWt("2000");
		p1.setLabelCode("6450202003180001");
		p1.setParcelContent("book");
		p1.setParcelDeadWeight("2000");
		p1.setParcelDeclerationValue("101");
		p1.setParcelHeight("2");
		p1.setParcelLength("2");
		p1.setParcelVolumeWeight("20");
		p1.setParcelWidth("10");
		p1.setPrintCount("4");
		p1.setReceiverAddress(addr);
		p1.setRecipientMobileNumber(12345678912L);
		p1.setRecipientName("ABC");
		p1.setStatus("booked");
		p1.setSubServices(service);
		p1.setTrackId("30933704114");

		ListParcel.add(p1);

		map.put("parcels", ListParcel);

		byte[] bytes = null;
		try {
			bytes = objectMapper.writeValueAsBytes(map);
		} catch (JsonProcessingException e) {
			log.error("Error occurred in test case::",e);
		}

		List<Parcel> ListParcel1 = new ArrayList<Parcel>();
		Parcel p = new Parcel();
			p.setSenderAddress(addr);
			p.setSenderMobileNumber(96385274102L);
			p.setSenderName("senderName");
			p.setActWt(2000);
			p.setLabelCode("6450202003180001");
			p.setParcelContent("book");
			p.setParcelDeadWeight(2000);
			p.setParcelDeclerationValue(101);
			p.setParcelHeight(2);
			p.setParcelLength(2);
			p.setParcelVolumeWeight(20);
			p.setParcelWidth(10);
			p.setPrintCount(4);
			p.setReceiverAddress(addr);
			p.setRecipientMobileNumber(12345678912L);
			p.setRecipientName("ABC");
			p.setStatus("booked");
			p.setSubServices(service);
			p.setTrackId("30933704114");

			ListParcel1.add(p);

		Mockito.when(parcelRepo.saveAll(ListParcel1)).thenReturn(ListParcel1);

		Assert.assertEquals(1, parcelSyncServiceImpl.syncClientData(bytes));
	}

	//parcelVo list is empty
	@Test
	public void syncClientDataElseConditinTest() throws IOException {

		ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
		List<ParcelVo> ListParcel = new ArrayList<ParcelVo>();
		Map<String, Object> map =new HashMap<String, Object>();

		map.put("parcels", ListParcel);

		byte[] bytes = null;
		try {
			bytes = objectMapper.writeValueAsBytes(map);
		} catch (JsonProcessingException e) {
			log.error("Error occurred in test case::",e);
		}

		List<Parcel> ListParcel1 = new ArrayList<Parcel>();

		Mockito.when(parcelRepo.saveAll(ListParcel1)).thenReturn(ListParcel1);

		Assert.assertEquals(0, parcelSyncServiceImpl.syncClientData(bytes));
	}


	@Test
	public void saveUpdateSyncTableTest() {

		Mockito.when(syncTableRepository.save(syncTable)).thenReturn(syncTable);
		Assert.assertEquals(syncTable, parcelSyncServiceImpl.saveUpdateSyncTable(syncTable));
	}


	@Test
	public void syncDataSuccessTestIfCondition() {

		SyncTable syncTable = new SyncTable();
		syncTable.setSyncId(3L);
		syncTable.setClientId("11001581656985");
		syncTable.setSyncType("master");
		syncTable.setSyncUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		syncTable.setNoOfRecords(6);
		syncTable.setNoOfRecordsUpdated(6);
		syncTable.setSynceStatus("pending");

		List<SyncTable> ListSyncTables = new ArrayList<SyncTable>();

		ListSyncTables.add(syncTable);

		Mockito.when(syncTableRepository.findBySyncTypeAndClientIdOrderBySyncUpdatedTimestampDesc(syncTable.getSyncType(), syncTable.getClientId(), PageRequest.of(0, 1))).thenReturn(ListSyncTables);
		Mockito.when(syncTableRepository.save(ListSyncTables.get(0))).thenReturn(syncTable);

		SyncTable result = parcelSyncServiceImpl.syncDataSuccess(syncTable);

		Assert.assertEquals("synced successfully", result.getSynceStatus());
		Assert.assertEquals(dateFormat.format(syncTable.getSyncUpdatedTimestamp()).toString(),
				dateFormat.format(result.getSyncUpdatedTimestamp()).toString());
		Assert.assertEquals(6, result.getNoOfRecordsUpdated());
	}

	@Test
	public void syncDataSuccessTestElseIfSecondCondition() {

		SyncTable syncTable = new SyncTable();
		syncTable.setSyncId(3L);
		syncTable.setClientId("11001581656985");
		syncTable.setSyncType("master");
		syncTable.setNoOfRecordsUpdated(0);
		syncTable.setNoOfRecords(0);
		syncTable.setSynceStatus("synced successfully");

		List<SyncTable> ListSyncTables = new ArrayList<SyncTable>();

		ListSyncTables.add(syncTable);

		Mockito.when(syncTableRepository.findBySyncTypeAndClientIdOrderBySyncUpdatedTimestampDesc(syncTable.getSyncType(), syncTable.getClientId(), PageRequest.of(0, 1))).thenReturn(ListSyncTables);
		Mockito.when(syncTableRepository.save(ListSyncTables.get(0))).thenReturn(syncTable);

		SyncTable result = parcelSyncServiceImpl.syncDataSuccess(syncTable);

		Assert.assertEquals("already synced", result.getSynceStatus());
		Assert.assertEquals(dateFormat.format(syncTable.getSyncUpdatedTimestamp()).toString(),
				dateFormat.format(result.getSyncUpdatedTimestamp()).toString());
		Assert.assertEquals(0, result.getNoOfRecordsUpdated());
	}


	@Test
	public void syncDataSuccessTestElseIfThirdCondition() {

		SyncTable syncTable = new SyncTable();
		syncTable.setSyncId(3L);
		syncTable.setClientId("11001581656985");
		syncTable.setSyncType("master");
		syncTable.setNoOfRecordsUpdated(4);
		syncTable.setNoOfRecords(6);
		syncTable.setSynceStatus("pending");

		List<SyncTable> ListSyncTables = new ArrayList<SyncTable>();

		ListSyncTables.add(syncTable);

		Mockito.when(syncTableRepository.findBySyncTypeAndClientIdOrderBySyncUpdatedTimestampDesc(syncTable.getSyncType(), syncTable.getClientId(), PageRequest.of(0, 1))).thenReturn(ListSyncTables);
		Mockito.when(syncTableRepository.save(ListSyncTables.get(0))).thenReturn(syncTable);

		SyncTable result = parcelSyncServiceImpl.syncDataSuccess(syncTable);

		Assert.assertEquals("syncing failed", result.getSynceStatus());
		Assert.assertEquals(dateFormat.format(syncTable.getSyncUpdatedTimestamp()).toString(),
				dateFormat.format(result.getSyncUpdatedTimestamp()).toString());
	}

}

