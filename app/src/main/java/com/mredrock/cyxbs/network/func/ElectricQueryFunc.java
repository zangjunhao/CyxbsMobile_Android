package com.mredrock.cyxbs.network.func;

import com.redrock.common.config.Const;
import com.mredrock.cyxbs.model.ElectricCharge;
import com.redrock.common.network.RedrockApiException;

import io.reactivex.functions.Function;


/**
 * Created by ：AceMurder
 * Created on ：2017/2/28
 * Created for : CyxbsMobile_Android.
 * Enjoy it !!!
 */

public class ElectricQueryFunc implements Function<ElectricCharge.ElectricChargeWrapper,ElectricCharge>{

    @Override
    public ElectricCharge apply(ElectricCharge.ElectricChargeWrapper electricChargeWrapper) throws Exception {
        if (electricChargeWrapper.getStatus() != Const.RED_ROCK_API_STATUS_SUCCESS || electricChargeWrapper.getElectricCharge() == null){
            throw new RedrockApiException("some thing wrong");
        }
        return electricChargeWrapper.getElectricCharge();
    }
}
