package com.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.ClientAccount;
import com.domain.Parcel;
import com.repositories.ClientAccountRepository;
import com.repositories.ParcelRepository;
import com.services.MeterBalanceSyncService;
import com.util.AuthTokenUtils;
import com.util.MeterBalanceUtils;
import com.vo.MeterBalanceResponseVo;

@Service
public class MeterBalanceSyncServiceImpl implements MeterBalanceSyncService {

    @Autowired
    ParcelRepository parcelRepository;

    @Autowired
    ClientAccountRepository clientAccountRepository;

    private static String accessToken = AuthTokenUtils.oauthToken;
    Logger log = LoggerFactory.getLogger(MeterBalanceSyncServiceImpl.class);

    public float unsynced_balance = 0.0f;
    ClientAccount clientAccount = null;

    public boolean syncBalance(String ngpId) {
        if (accessToken == null) {
            accessToken = AuthTokenUtils.getAccessToken();
        }

        MeterBalanceResponseVo meterResponse = MeterBalanceUtils.getMeterBalance(ngpId);

        // MeterBalanceResponseVo meterResponse = new MeterBalanceResponseVo();
        // meterResponse.setBalance(4000.0);

        List<ClientAccount> listOfClientAccount = clientAccountRepository.findAll();
        if (listOfClientAccount.size() >= 1) {
            clientAccount = listOfClientAccount.get(0);

        }

        if (meterResponse != null && clientAccount != null) {
            if ((float)meterResponse.getBalance() != clientAccount.getTotalBalance()) {

                log.info("total balance :" + clientAccount.getTotalBalance() + " & meter balance :"
                        + meterResponse.getBalance());
                
                clientAccount.setTotalBalance((float) meterResponse.getBalance());

                List<Parcel> listOfParcel = parcelRepository.findAllByStatusAndPaid("booked", false);
                unsynced_balance = 0.0f;
                for (Parcel parcel : listOfParcel) {
                    unsynced_balance += parcel.getInvoiceBreakup().getPayableAmnt();
                }
                // log.info("unsynced balance: {}",unsynced_balance);
                clientAccount.setAvalBalance(clientAccount.getTotalBalance() - unsynced_balance);
                // log.info("aval balance: {}",clientAccount.getAvalBalance());
                clientAccountRepository.save(clientAccount);

                return true;
            }
            
        }
        return false;

    }

}
