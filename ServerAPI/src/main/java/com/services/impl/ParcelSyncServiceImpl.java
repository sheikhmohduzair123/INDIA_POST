package com.services.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.domain.Control;
import com.domain.Parcel;
import com.domain.SyncTable;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repositories.ControlRepository;
import com.repositories.ParcelRepository;
import com.repositories.SyncTableRepository;
import com.services.ParcelSyncService;
import com.vo.ParcelVo;

@Service
public class ParcelSyncServiceImpl implements ParcelSyncService {

	@Autowired
	ParcelRepository parcelRepository;

	@Autowired
	ControlRepository controlRepository;

	@Autowired
	SyncTableRepository syncTableRepository;

	public Control getClientControl() {
		List<Control> controlList = controlRepository.findAll();
		if (controlList.size() > 0)
			return controlList.get(0);
		else
			return new Control();
	}

	@Transactional(rollbackFor = Exception.class)
	public long syncClientData(byte[] bytes) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
		Map<String, Object> deserialized = objectMapper.readValue(bytes, new TypeReference<Map<String, Object>>() {
		});

		List<ParcelVo> parcelVoList = objectMapper.convertValue(deserialized.get("parcels"),
				new TypeReference<List<ParcelVo>>() {
				});
		List<Parcel> parcelDomainList = new ArrayList<Parcel>();
		long updatedRowsClient = 0;
		try {
			if (parcelVoList.size() > 0) {
				for (ParcelVo parcelVo : parcelVoList) {
					Parcel parcel = new Parcel();
					parcel.setSenderAddress(parcelVo.getSenderAddress());
					parcel.setSenderMobileNumber(parcelVo.getSenderMobileNumber());
					parcel.setSenderName(parcelVo.getSenderName());
					parcel.setClient(parcelVo.getClient());
					parcel.setActWt(Float.valueOf(parcelVo.getActWt()));
					parcel.setCod(parcelVo.isCod());
					parcel.setToPay(parcelVo.isToPay());
					parcel.setCreatedDate(parcelVo.getCreatedDate());
					parcel.setInvoiceBreakup(parcelVo.getInvoiceBreakup());
					parcel.setLabelCode(parcelVo.getLabelCode());
					parcel.setLastPrintDate(parcelVo.getLastPrintDate());
					parcel.setParcelContent(parcelVo.getParcelContent());
					parcel.setParcelDeadWeight(Float.valueOf(parcelVo.getParcelDeadWeight()));
					parcel.setParcelDeclerationValue(Float.valueOf(parcelVo.getParcelDeclerationValue()));
					parcel.setParcelHeight(Float.valueOf(parcelVo.getParcelHeight()));
					parcel.setParcelLength(Float.valueOf(parcelVo.getParcelLength()));
					parcel.setParcelVolumeWeight(Float.valueOf(parcelVo.getParcelVolumeWeight()));
					parcel.setParcelWidth(Float.valueOf(parcelVo.getParcelWidth()));
					parcel.setPrintCount(Integer.parseInt(parcelVo.getPrintCount()));
					parcel.setPrintOption(parcelVo.getPrintOption());
					parcel.setpStatus(parcelVo.getpStatus());
					parcel.setRateCalculation(parcelVo.getRateCalculation());
					parcel.setRateCalculationJSON(parcelVo.getRateCalculationJSON());
					parcel.setService(parcelVo.getService());
					parcel.setReceiverAddress(parcelVo.getReceiverAddress());
					parcel.setRecipientMobileNumber(parcelVo.getRecipientMobileNumber());
					parcel.setRecipientName(parcelVo.getRecipientName());
					parcel.setStatus(parcelVo.getStatus());
					parcel.setSubServices(parcelVo.getSubServices());
					parcel.setTrackId(parcelVo.getTrackId());
					parcel.setCreatedBy(parcelVo.getCreatedBy());
					parcel.setParcelimage(parcelVo.getParcelimage());
					parcel.setScanedBarcode(parcelVo.getScanedBarcode());
					parcel.setParcelCount(parcelVo.getParcelCount());
					parcelDomainList.add(parcel);
				}

				List<Parcel> parcelList1 = (List<Parcel>) parcelRepository.saveAll(parcelDomainList);
				updatedRowsClient = parcelList1.size();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updatedRowsClient;
	}

	public SyncTable saveUpdateSyncTable(SyncTable syncTable) {
		return syncTableRepository.save(syncTable);
	}

	public SyncTable syncDataSuccess(SyncTable syncTable) {
		List<SyncTable> syncTables = syncTableRepository.findBySyncTypeAndClientIdOrderBySyncUpdatedTimestampDesc(
				syncTable.getSyncType(), syncTable.getClientId(), PageRequest.of(0, 1));
		SyncTable syncTable1 = syncTables.get(0);
		syncTable1.setNoOfRecordsUpdated(syncTable.getNoOfRecordsUpdated());

		if (syncTable1.getNoOfRecords() == syncTable.getNoOfRecordsUpdated()
				&& syncTable1.getSynceStatus().equals("pending")) {
			syncTable1.setSynceStatus("synced successfully");
			syncTable1.setSyncUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		} else if (syncTable1.getNoOfRecords() == syncTable.getNoOfRecordsUpdated()
				&& syncTable1.getNoOfRecords() == 0) {
			syncTable1.setSynceStatus("already synced");
			syncTable1.setSyncUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		} else {
			syncTable1.setSynceStatus("syncing failed");
			syncTable1.setSyncUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		}
		syncTable = syncTableRepository.save(syncTable1);
		return syncTable;
	}

}
