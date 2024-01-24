package Tariff.API.controller;


import Tariff.API.domain.TariffRequestVO;
import Tariff.API.domain.TariffResponseVO;
import Tariff.API.service.CalculateBaseAndSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BaseTariffAndServiceTax {

    @Autowired
    private CalculateBaseAndSer calculateBaseAndSer;

     @PostMapping(value = "/bulkGetTariff")
    public List<TariffResponseVO> getBaseTariffAndServiceTax(@RequestBody List<TariffRequestVO> input){
      return this.calculateBaseAndSer.baseAndServiceTariff(input);
    }
}
