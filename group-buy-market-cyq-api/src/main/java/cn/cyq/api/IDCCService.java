package cn.cyq.api;

import cn.cyq.api.response.Response;

public interface IDCCService {

    Response<Boolean> updateConfig(String key, String value);
}
