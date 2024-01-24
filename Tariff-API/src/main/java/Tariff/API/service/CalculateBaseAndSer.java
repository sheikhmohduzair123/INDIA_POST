package Tariff.API.service;

import Tariff.API.domain.TariffRequestVO;
import Tariff.API.domain.TariffResponseVO;

import java.util.List;


public interface CalculateBaseAndSer {

     List<TariffResponseVO> baseAndServiceTariff(List<TariffRequestVO> input);
}
