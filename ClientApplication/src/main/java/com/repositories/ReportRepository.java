package com.repositories;

import com.domain.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/*
 * Name : Nirmal Sharma
 * Date :15/10/2019
 */
@Repository
public interface ReportRepository extends JpaRepository<Parcel, Long> {
    @Query("SELECT p FROM Parcel p WHERE p.createdDate BETWEEN ?1 AND ?2")
    public List<Parcel> findsummaryDaily(Timestamp reset,Timestamp todate);

    @Query("SELECT  p FROM Parcel p WHERE p.receiverAddress.division=?3 AND p.createdDate BETWEEN ?1 AND ?2")
    public List<Parcel> findsummaryDailyByDivision(Timestamp reset,Timestamp todate ,String division);

    @Query("SELECT  p FROM Parcel p WHERE p.receiverAddress.division=?3 AND p.createdDate BETWEEN ?1 AND ?2")
    public List<Parcel> findsummaryDailyByDivisionAndDate(Timestamp fdate,Timestamp todate ,String division);

	public List<Parcel> findByCreatedDateBetween(Date date, Date date_end);


	public List<Parcel> findBySenderAddress_ZoneAndSenderAddress_DivisionAndSenderAddress_DistrictAndSenderAddress_ThanaAndSenderAddress_SubOfficeAndCreatedDateBetweenAndStatusIn(
			String zone, String division, String district, String thana, String subOffice, Date date, Date date_end,
			List<String> parcelStatus);

	public List<Parcel> findBySenderAddress_ZoneAndSenderAddress_DivisionAndSenderAddress_DistrictAndSenderAddress_ThanaAndCreatedDateBetweenAndStatusIn(
			String zone, String division, String district, String thana, Date date, Date date_end,
			List<String> parcelStatus);

	public List<Parcel> findBySenderAddress_ZoneAndSenderAddress_DivisionAndSenderAddress_DistrictAndCreatedDateBetweenAndStatusIn(
			String zone, String division, String district, Date date, Date date_end, List<String> parcelStatus);

	public List<Parcel> findBySenderAddress_ZoneAndSenderAddress_DivisionAndCreatedDateBetweenAndStatusIn(String zone,
			String division, Date date, Date date_end, List<String> parcelStatus);

	public List<Parcel> findBySenderAddress_ZoneAndCreatedDateBetweenAndStatusIn(String zone, Date date, Date date_end,
			List<String> parcelStatus);

	public List<Parcel> findByCreatedDateBetweenAndStatusIn(Date date, Date date_end, List<String> parcelStatus);

	public List<Parcel> findBySenderAddress_PostalCodeInAndCreatedDateBetweenAndStatusIn(List<Integer> zonePostalCode, Date date, Date date_end, List<String> parcelStatus);

}
