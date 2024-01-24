package Tariff.API.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import Tariff.API.domain.TariffRequestVO;
import Tariff.API.domain.TariffResponseVO;

@Service
public class CalculateBaseAndSerImpl implements CalculateBaseAndSer {


    public List<TariffResponseVO> baseAndServiceTariff(List<TariffRequestVO> input) {
        List<TariffResponseVO> outputList = new ArrayList<>();

        for (TariffRequestVO inp : input) {

            if (inp.getService()!=null && inp.getService().equals("BP")) {


                if (inp.getSourcepin()==null||inp.getLength()==null||inp.getWeight()==null||inp.getHeight()==null
                        ||inp.getDestinationpin()==null||inp.getBreadth()==null||inp.getSourcepin().isBlank()||
                        inp.getDestinationpin().isBlank()||inp.getLength().isBlank()||inp.getWeight().isBlank()||inp.getHeight().isBlank()||
                        inp.getBreadth().isBlank()){
                    outputList.add(new TariffResponseVO("Invalid VPP Value / Article Type", "0", "0", "0", "0", "0", "0",
                            "0", "0", "0", "0"));

                }
                else {

                    outputList.add(new TariffResponseVO("Valid Input", inp.getWeight(), "115", "0", "0", "0",inp.getINS_Value()> 0 ? String.valueOf(inp.getINS_Value()/20):"0.00", "0.00",
                            "26.82", String.valueOf((inp.getCOD_Value()/20) + (inp.getINS_Value()/20)+115+26.82),inp.getCOD_Value()>0 ? String.valueOf(inp.getCOD_Value()/20) : "0.00"));
                }
            }
            else if (inp.getService()==null||inp.getService().isBlank()) {
                outputList.add(new TariffResponseVO("Invalid VPP Value / Article Type", "0", "0", "0", "0", "0", "0",
                        "0", "0", "0", "0"));

            }
            else{
                outputList.add(new TariffResponseVO("Invalid VPP Value / Article Type", "0", "0", "0", "0", "0", "0",
                        "0", "0", "0", "0"));
            }

        }
        return outputList;
    }
}
