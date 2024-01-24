package com.repositories;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.domain.Parcel;
import com.domain.TrackingCS;
import com.constants.BagStatus;
import com.constants.LocationType;
import com.constants.PStatus;
import com.constants.Status;
import com.domain.TrackingDetails;
import com.domain.User;


public interface TrackingDetailsRepository extends JpaRepository<TrackingDetails, Long> {
	List<TrackingDetails> findByObjParcel(Parcel parcel);

	List<TrackingDetails> findByBagIdAndStatus(String bagId, Status active);

	List<TrackingDetails> findByObjParcelOrderByUpdatedOnDesc(Parcel trackingId);
	List<TrackingDetails> findByBagIdOrderByUpdatedOnDesc(String bagId);
	List<TrackingDetails> findByBagStatusInAndUpdatedOnBetweenAndUpdatedByIn(List<BagStatus> pstatus, Timestamp startDate, Timestamp endDate, List<User> user);
	List<TrackingDetails> findByUpdatedOnBetweenAndBagStatusAndUpdatedByIn(Timestamp ts1, Timestamp ts2, BagStatus bagStatus, List<User> user);

	List<TrackingDetails> findBypStatusAndUpdatedOnBetweenAndUpdatedByIn(PStatus parcelStatus, Timestamp ts,Timestamp ts1, List<User> userRMSselected);

	List<TrackingDetails> findByUpdatedByInAndBagStatusAndUpdatedOnBetween(List<User> users, BagStatus bagStatus,Date fromdate, Date todate);

	List<TrackingDetails> findAllByBagStatusInAndUpdatedOnBetweenAndUpdatedByIn(List<BagStatus> pstatus1, Timestamp ts,	Timestamp ts1, List<User> users);

	List<TrackingDetails> findAllByBagStatusAndUpdatedOnBetweenAndUpdatedByIn(BagStatus bagStatus , Timestamp ts,Timestamp ts1, List<User> users);

	TrackingDetails findByStatusAndObjParcel(Status pStatus, Parcel parcel);

	TrackingDetails findByObjParcelAndStatus(Parcel parcel, Status active);

	List<TrackingDetails> findByUpdatedByInAndBagStatusIn(List<User> users, List<BagStatus> status);
	
	//List<TrackingDetails> findAllByBagIdOrderByUpdatedOnDesc(String bagId);
	
	//List<TrackingDetails> findAllByBagIdAndStatusAndBagStatusOrderByUpdatedOnDesc(String bagId, Status status, BagStatus bagStatus);
	List<TrackingDetails> findAllByBagIdAndStatusOrderByUpdatedOnDesc(String bagId, Status status);
}

