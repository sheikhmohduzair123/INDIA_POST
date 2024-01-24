package com.jobs;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.constants.Status;
import com.controllers.ParcelController;
import com.domain.Client;
import com.domain.Config;
import com.domain.District;
import com.domain.Division;
import com.domain.MasterAddress;
import com.domain.Parcel;
import com.domain.RateTable;
import com.domain.Thana;
import com.domain.TrackingCS;
import com.domain.TrackingDetails;
import com.domain.Zone;
import com.repositories.ClientRepository;
import com.repositories.ConfigRepository;
import com.repositories.DistrictAddressRepo;
import com.repositories.DivisionAddressRepo;
import com.repositories.MasterAddressRepository;
import com.repositories.ParcelRepository;
import com.repositories.RateTableRepository;
import com.repositories.SUserRepository;
import com.repositories.ThanaAddressRepo;
import com.repositories.TrackingCSRepository;
import com.repositories.TrackingDetailsRepository;
import com.repositories.ZoneAddressRepo;

@Component
@DependsOn("applicationConfigurations")
public class CleanupDb {

	protected static Logger log = LoggerFactory.getLogger(CleanupDb.class);

	@Value("${cleanupdurationinmonth}")
	private String monthAgo;	

	@Autowired
	private ParcelRepository parcelRepository;

	@Autowired
	private MasterAddressRepository masterAddressRepository;

	@Autowired
	private RateTableRepository rateTableRepository;


	@Autowired
	private ClientRepository clientRepository;
	
	
	@Scheduled(cron = "${schedulertimepattern}", zone = "Asia/Calcutta")
	@Transactional
	public void dbCleanUpActivity() {
		log.info("Runnig db cleanup scheduler...");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -(Integer.parseInt(monthAgo)));
		Date fromDate = cal.getTime();
		// date to timestamp
		Timestamp tsFromDate = new Timestamp(fromDate.getTime());

		// cleanup activity on parcel
		List<Parcel> cleanupParcel = parcelRepository.findAllByCreatedDateBefore(tsFromDate);
		if (cleanupParcel.size() > 0) {
			log.info("Parcel row deleting...");
			for (Parcel parcelToDelete : cleanupParcel) {
				parcelRepository.delete(parcelToDelete);
			}
		}

		// cleanup activity on rate table
		List<RateTable> cleanupRateTable = rateTableRepository.findAllByCreatedOnBeforeAndStatus(tsFromDate,
				Status.DISABLED);

		if (cleanupRateTable.size() > 0) {
			log.info("RateTable row deleting...");
			for (RateTable rateToDelete : cleanupRateTable) {
				rateTableRepository.delete(rateToDelete);
			}
		}

		// cleanup activity on client table
		List<Client> cleanupClient = clientRepository.findAllByCreatedOnBeforeAndStatus(tsFromDate, Status.DISABLED);

		if (cleanupClient.size() > 0) {
			log.info(cleanupClient.size()+"Client row deleting...");
			for (Client clientToDelete : cleanupClient) {
				clientRepository.delete(clientToDelete);
			}
		}


		// cleanup activity on master-address table
		List<MasterAddress> cleanupMasterAddress = masterAddressRepository.findAllByCreatedOnBeforeAndStatus(tsFromDate,
				Status.DISABLED);
		if (cleanupMasterAddress.size() > 0) {
			log.info(cleanupMasterAddress.size()+"MasterAddress row deleting...");
			for (MasterAddress masterToDelete : cleanupMasterAddress) {
				masterAddressRepository.delete(masterToDelete);

			}
		}
		

		log.info("No record available for delete");

	}
}
