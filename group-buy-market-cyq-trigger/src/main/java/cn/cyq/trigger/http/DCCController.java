package cn.cyq.trigger.http;

import cn.cyq.api.IDCCService;
import cn.cyq.api.response.Response;
import cn.cyq.types.enums.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/gbm/dcc/")
public class DCCController implements IDCCService {

    @Resource
    private RTopic topic;

    @RequestMapping(value = "updateConfig", method = RequestMethod.GET)
    @Override
    public Response<Boolean> updateConfig(@RequestParam String key, @RequestParam String value) {
        try {

            topic.publish(key + "," + value);

            return Response.success(true);
        } catch (Exception e) {
            return Response.error(ResponseCode.UN_ERROR);
        }
    }
}
