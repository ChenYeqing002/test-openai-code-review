package cn.cyq.api;

import cn.cyq.api.dto.LockMarketPayOrderRequestDTO;
import cn.cyq.api.dto.LockMarketPayOrderResponseDTO;
import cn.cyq.api.response.Response;

public interface IMarketTradeService {

    Response<LockMarketPayOrderResponseDTO> lockMarketPayOrder(LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO);
}
